package ph.edu.usc.jaidar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class JobListingDetailsActivity extends AppCompatActivity {

    private ImageButton backBtn, companyLogo;
    private TextView poster, jobTitle, subtitle, aboutDescription;
    private Button applyBtn, saveBtn;
    private ImageView waveBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing_details);

        initialization();
        jobTitle.setText(getIntent().getStringExtra("jobTitle"));
        subtitle.setText(getIntent().getStringExtra("jobSubtitle"));
        aboutDescription.setText(getIntent().getStringExtra("about"));
        String uid = getIntent().getStringExtra("posterUid");
        Log.d("POSTER_UID", "Received UID: [" + uid + "]");

        getPoster(uid);

        backBtn.setOnClickListener(view -> finish());

        applyBtn.setOnClickListener(view -> {
            Toast.makeText(JobListingDetailsActivity.this, "Applied successfully!", Toast.LENGTH_SHORT).show();
        });

        saveBtn.setOnClickListener(view -> {
            Toast.makeText(JobListingDetailsActivity.this, "Job saved!", Toast.LENGTH_SHORT).show();
        });
    }

    public void getPoster(String uid){
        if (uid != null) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        String name = snapshot.getString("name");
                        poster.setText(name != null ? name : "Job Poster");
                    })
                    .addOnFailureListener(e -> {
                        poster.setText("Error loading poster");
                    });
        }
    }
    public void initialization(){
        backBtn = findViewById(R.id.backBtn);
        companyLogo = findViewById(R.id.companyLogo);
        poster = findViewById(R.id.poster);
        jobTitle = findViewById(R.id.jobTitle);
        subtitle = findViewById(R.id.subtitle);
        applyBtn = findViewById(R.id.applyBtn);
        saveBtn = findViewById(R.id.saveBtn);
        waveBg = findViewById(R.id.waveBg);
        aboutDescription = findViewById(R.id.aboutDescription);
    }
}
