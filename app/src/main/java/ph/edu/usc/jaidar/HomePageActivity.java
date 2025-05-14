package ph.edu.usc.jaidar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ph.edu.usc.jaidar.messaging.UserListActivity;
import ph.edu.usc.jaidar.profile.UserProfileActivity;
import ph.edu.usc.jaidar.worker.WorkerJob;
import ph.edu.usc.jaidar.worker.WorkerJobAdapter;

public class HomePageActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button logoutBtn;
    Button TEMPEDITOR, worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page); // Shows your XML layout

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        String[] categories = { //later on get from db firestore
                "Choose","Electrician", "Plumber", "Carpenter", "Welding", "Roofer", "Mechanic", "Caretaker", "Ironworker"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );

        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = categories[position];
                Toast.makeText(HomePageActivity.this, "Selected: " + selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Logout Button
        logoutBtn = findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            SharedPreferences sharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            startActivity(intent);
            finish();
        });
        //TEMPORARY BUTTON FOR INTENT. DELETE LATER
        TEMPEDITOR = findViewById(R.id.btnHiring);
        worker = findViewById(R.id.btnWorker);
        TEMPEDITOR.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RecruitmentEditorActivity.class);
            intent.putExtra(RecruitmentEditorActivity.USER_ROLE, RecruitmentEditorActivity.HIRER); //WORKER or HIRER
            startActivity(intent);
        });
        worker.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RecruitmentEditorActivity.class);
            intent.putExtra(RecruitmentEditorActivity.USER_ROLE, RecruitmentEditorActivity.WORKER); //WORKER or HIRER
            startActivity(intent);
        });


        Bottomnavigation(mAuth);
        Jobpostings(db);
        loadWorkerJobs(db);

    }

    public void Jobpostings(FirebaseFirestore db){
        RecyclerView jobRecycler = findViewById(R.id.jobPreviewRecycler);
        jobRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<JobPost> jobList = new ArrayList<>();
        JobPreviewAdapter adapter = new JobPreviewAdapter(this, jobList);
        jobRecycler.setAdapter(adapter);

        db.collection("job_recruitments")
//                .orderBy("posted_at", Query.Direction.DESCENDING) // Optional
                .get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query) {
                        String id = doc.getId();
                        String title = doc.getString("title");
                        String description = doc.getString("description");
                        Long headcount = doc.getLong("headcount");
                        Double rate = doc.getDouble("rate");
                        String userPost = doc.getString("user_post");

                        JobPost job = new JobPost(
                                id,
                                title,
                                description,
                                headcount != null ? headcount.intValue() : 0,
                                rate != null ? rate : 0,
                                userPost
                        );
                        jobList.add(job);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load job offers: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    public void loadWorkerJobs(FirebaseFirestore db){
        RecyclerView recycler = findViewById(R.id.workerRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<WorkerJob> jobList = new ArrayList<>();
        WorkerJobAdapter adapter = new WorkerJobAdapter(this, jobList);
        recycler.setAdapter(adapter);

        db.collection("job_listing")
                .whereEqualTo("status", "active")
                .get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query) {
                        WorkerJob job = new WorkerJob(
                                doc.getId(),
                                doc.getString("title"),
                                doc.getString("description"),
                                doc.getLong("rate").intValue(),
                                doc.getString("tag"),
                                doc.getString("user_post"),
                                doc.getString("status")
                        );
                        jobList.add(job);
                    }
                    adapter.notifyDataSetChanged();
                });
    }


    private void Bottomnavigation(FirebaseAuth mAuth) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home); // ✅ Always highlight Home in homepage

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                return true; // already here
            } else if (id == R.id.activity) {
                startActivity(new Intent(HomePageActivity.this, ActivitySectionActivity.class));
                return true;
            } else if (id == R.id.message) {
                startActivity(new Intent(HomePageActivity.this, UserListActivity.class));
                return true;       
            } else if (id == R.id.profile) {
                String currentUid = mAuth.getUid();
                Intent intent = new Intent(HomePageActivity.this, UserProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("profileUid", currentUid);
                startActivity(intent);
                return true;
            }

            return false;
        });
    }
}
