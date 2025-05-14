package ph.edu.usc.jaidar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class YourPostsAdapter extends RecyclerView.Adapter<YourPostsAdapter.ViewHolder> {
    private final List<JobPost> postList;
    private final Context context;

    public YourPostsAdapter(Context context, List<JobPost> list) {
        this.context = context;
        this.postList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, applicantNumber, headcount;
        RecyclerView applicantList;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            applicantNumber = view.findViewById(R.id.applicant_number);
            headcount = view.findViewById(R.id.headcount);
            applicantList = view.findViewById(R.id.applicant_list);
        }
    }

    @NonNull
    @Override
    public YourPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_your_posts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("MYDEBUG", "postList added: " + postList.get(position).getTitle());
        Log.d("MYDEBUG", "applicants: " + postList.get(position).getAllApplicant());

        JobPost jobPost = postList.get(position);
        holder.title.setText(jobPost.getTitle());

        List<User> applicants = jobPost.getAllApplicant(); // Add this to JobPost model
        int applicantCount = applicants != null ? applicants.size() : 0;

        holder.applicantNumber.setText(String.valueOf(applicantCount));
        holder.headcount.setText(String.valueOf(jobPost.getHeadcount())); // assuming it's in JobPost

        // Set up nested RecyclerView
        ApplicantAdapter adapter = new ApplicantAdapter(this.context, postList.get(position));
        holder.applicantList.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.applicantList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
