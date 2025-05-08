package ph.edu.usc.jaidar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecruitmentViewActivity extends AppCompatActivity {


    private ImageView backBtn;
    private TextView rateValue, titleText;
    private TextView descriptionInput;
    private TextView fullNameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_view);

        backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(v -> goBack());

        rateValue = findViewById(R.id.rate_value);
        titleText = findViewById(R.id.title);
        descriptionInput = findViewById(R.id.description_input);

        fullNameView = findViewById(R.id.user_whole_name);

        //get intent here for job ID and pass it there
        loadJobDetails("oIBOigR5k4rLGhT60taz");
    }

    private void goBack(){
        //imma replace this previousPage with variable passed from intent from last page.
        Class previousPage = HomePageActivity.class;
        Intent intent = new Intent(this, previousPage);
        this.startActivity(intent);
    }

    private void loadJobDetails(String jobRecruitmentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("job_recruitments").document(jobRecruitmentId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        double rate = documentSnapshot.getDouble("rate");
                        String description = documentSnapshot.getString("description");
                        String title = documentSnapshot.getString("title");
                        String authorUid = documentSnapshot.getString("user_post");

                        titleText.setText(title);
                        rateValue.setText(rate + "");
                        descriptionInput.setText(description);

                        db.collection("users").document(authorUid).get()
                                .addOnSuccessListener(snapshot -> {
                                    fullNameView.setText(snapshot.getString("name"));
                                })
                                .addOnFailureListener(e -> {
                                    fullNameView.setText("");
                                });
                    } else {
                        Toast.makeText(this, "Job not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load job details.", Toast.LENGTH_SHORT).show();
                });
    }

}