package ph.edu.usc.jaidar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ViewHolder> {

    private List<User> applicants;

    public ApplicantAdapter(List<User> applicants) {
        this.applicants = applicants;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, location;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.applicant_name);
            email = view.findViewById(R.id.applicant_email);
            location = view.findViewById(R.id.applicant_location);
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
