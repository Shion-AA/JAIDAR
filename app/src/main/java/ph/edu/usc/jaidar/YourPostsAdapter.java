package ph.edu.usc.jaidar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kotlinx.coroutines.Job;

public class YourPostsAdapter extends RecyclerView.Adapter<YourPostsAdapter.ViewHolder> {
    private final List<JobPost> postList;

    public YourPostsAdapter(List<JobPost> list) {
        this.postList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
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
        JobPost jobPost = postList.get(position);
        holder.title.setText(jobPost.getTitle());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
