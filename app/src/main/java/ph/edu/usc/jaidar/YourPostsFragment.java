package ph.edu.usc.jaidar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;


public class YourPostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView emptyView;
    private FirebaseFirestore db;
    private List<JobPost> postList;
    private YourPostsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_posts, container, false);

        recyclerView = view.findViewById(R.id.postList);
        emptyView = view.findViewById(R.id.empty_post);

        db = FirebaseFirestore.getInstance();
        postList = new ArrayList<>();
        adapter = new YourPostsAdapter(postList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("job_recruitments")
                .whereEqualTo("user_post", uid)
                .orderBy("posted_at", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if(!querySnapshot.isEmpty()) {
                        emptyView.setVisibility(View.GONE);
                        postList.clear();
                        for (QueryDocumentSnapshot doc : querySnapshot) {
                            Log.d("Firestore", "Got post: " + doc.getString("title"));
                            JobPost jobPost = new JobPost(
                                    doc.getId(),
                                    doc.getString("title"),
                                    doc.getString("description"),
                                    doc.getLong("headcount").intValue(),
                                    doc.getDouble("rate"),
                                    doc.getString("user_post")
                            );
                            postList.add(jobPost);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        return view;
    }
}
