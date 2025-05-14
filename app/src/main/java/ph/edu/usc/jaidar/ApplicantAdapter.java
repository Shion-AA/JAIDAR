package ph.edu.usc.jaidar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ViewHolder> {

    private List<User> applicants;
    private Context context;

    public ApplicantAdapter(Context context, List<User> applicants) {
        this.applicants = applicants;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, location;
        ImageView chatButton;
        Button actionButton;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.applicant_name);
            email = view.findViewById(R.id.applicant_email);
            location = view.findViewById(R.id.applicant_location);
            chatButton = view.findViewById(R.id.chat_button);
            actionButton = view.findViewById(R.id.action_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_applicant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(applicants.get(position).getName());
        holder.email.setText(applicants.get(position).getEmail());
        holder.location.setText(applicants.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        if(this.applicants != null)
            return applicants.size();
        return 0;
    }
}
