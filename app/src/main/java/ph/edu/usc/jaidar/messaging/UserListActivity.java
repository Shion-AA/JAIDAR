package ph.edu.usc.jaidar.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import ph.edu.usc.jaidar.R;
import ph.edu.usc.jaidar.UserAdapter;
import ph.edu.usc.jaidar.UserModel;

public class UserListActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {

    RecyclerView userRecyclerView;
    ProgressBar progressBar;
    ArrayList<UserModel> userList;
    UserAdapter adapter;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userRecyclerView = findViewById(R.id.userRecyclerView);
        progressBar = findViewById(R.id.userListProgressBar);
        userList = new ArrayList<>();
        adapter = new UserAdapter(userList, this);

        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadUsers();
    }

    private void loadUsers() {
        progressBar.setVisibility(View.VISIBLE);

        String currentEmail = mAuth.getCurrentUser().getEmail();
        CollectionReference usersRef = db.collection("users");

        usersRef.get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                userList.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    String name = doc.getString("name");
                    String email = doc.getString("email");

                    // Avoid current user
                    if (!email.equals(currentEmail)) {
                        userList.add(new UserModel(name, email));
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(UserListActivity.this, "Failed to load users.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUserClick(UserModel user) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("receiverEmail", user.getEmail());
        intent.putExtra("receiverName", user.getName());
        startActivity(intent);
    }
}
