package ph.edu.usc.jaidar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class InboundAdapter extends RecyclerView.Adapter<InboundAdapter.ViewHolder> {
    private final List<Offer> offerList;
    private final Context context;
    private final OffersFragment fragment;
    public InboundAdapter(OffersFragment fragment, Context context, List<Offer> list) {
        this.context = context;
        this.fragment = fragment;
        this.offerList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hirer_name, offer_title, current_status;
        Button accept_button, reject_button;
        View applicant_click;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hirer_name = itemView.findViewById(R.id.hirer_name);
            offer_title = itemView.findViewById(R.id.offer_title);
            current_status = itemView.findViewById(R.id.current_status);
            accept_button = itemView.findViewById(R.id.accept_button);
            reject_button = itemView.findViewById(R.id.reject_button);
            applicant_click = itemView.findViewById(R.id.applicant_click);
        }
    }

    @NonNull
    @Override
    public InboundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("OFFERS", "\noffer(from Adapter) : " + offerList.get(position).toString());
        Offer offer = offerList.get(position);

        if (offer.getHirer() != null) {
            holder.hirer_name.setText(offer.getHirer().getName());
        } else {
            holder.hirer_name.setText("Loading...");
            Log.w("INBOUND", "Hirer is null for offer: " + offer.getJobListingId());
        }

        holder.offer_title.setText(offer.getTitle() != null ? offer.getTitle() : "No Title");

        String status = offer.getStatus();
        holder.current_status.setText(status != null ? status : "Unknown");

        holder.accept_button.setVisibility(View.GONE);
        holder.reject_button.setVisibility(View.GONE);
        holder.current_status.setVisibility(View.VISIBLE);

        if ("pending".equals(status)) {
            holder.accept_button.setVisibility(View.VISIBLE);
            holder.reject_button.setVisibility(View.VISIBLE);
            holder.current_status.setVisibility(View.GONE);
        }

        holder.applicant_click.setOnClickListener(v -> {
            // TODO: handle full item click here
        });

        // Optional: accept/reject actions
        holder.accept_button.setOnClickListener(v -> {
            // TODO: handle accept
        });

        holder.reject_button.setOnClickListener(v -> {
            // TODO: handle reject
        });
    }


    @Override
    public int getItemCount() {
        return offerList.size();
    }
}
