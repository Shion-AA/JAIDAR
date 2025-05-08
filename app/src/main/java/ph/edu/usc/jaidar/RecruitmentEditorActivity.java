package ph.edu.usc.jaidar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RecruitmentEditorActivity extends AppCompatActivity {

    ConstraintLayout main;
    ImageView backBtn;
    TextView userWholeNameText, userNameText;
    EditText titleInput, rateInput, descriptionInput, headcountCustomInput;
    RadioButton headcountOption1, headcountOption2, headcountOption3;
    Button postButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment_editor);

        main = findViewById(R.id.main);

        backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(v -> goBack());

        userNameText = findViewById(R.id.user_username);
        userWholeNameText = findViewById(R.id.user_whole_name);
        titleInput = findViewById(R.id.title_input);
        rateInput = findViewById(R.id.rate_input);
        descriptionInput = findViewById(R.id.description_input);
        headcountCustomInput = findViewById(R.id.headcount_custom_input);
        headcountOption1 = findViewById(R.id.headcount_option1);
        headcountOption2 = findViewById(R.id.headcount_option2);
        headcountOption3 = findViewById(R.id.headcount_option3);
        postButton = findViewById(R.id.post_button);

        db.collection("users").document(userId).get().addOnSuccessListener(snapshot ->{
            if(snapshot.exists()) {
                String wholeName = snapshot.getString("name");
                userWholeNameText.setText(wholeName);
            }
        }).addOnFailureListener(e -> {
            userWholeNameText.setText("");
        });

        // Enable or disable custom headcount input
        headcountOption3.setOnClickListener(v -> {
            headcountCustomInput.setEnabled(true);
            headcountCustomInput.setFocusable(true);
            headcountCustomInput.setFocusableInTouchMode(true);
        });

        headcountOption1.setOnClickListener(v -> disableCustomHeadcount());
        headcountOption2.setOnClickListener(v -> disableCustomHeadcount());

        postButton.setOnClickListener(v -> saveJobOffer());
    }
    private void goBack(){
        //imma replace this previousPage with variable passed from intent from last page.
        Class previousPage = HomePageActivity.class;
        Intent intent = new Intent(this, previousPage);
        this.startActivity(intent);
    }

    private void disableCustomHeadcount() {
        headcountCustomInput.setEnabled(false);
        headcountCustomInput.setFocusable(false);
        headcountCustomInput.setText("");
    }

    private void saveJobOffer() {
        main.setFocusable(false);
        main.setClickable(false);   //hopefully this works to prevent user from editing form while in loading of upload.

        String title = titleInput.getText().toString().trim();
        String rateStr = rateInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String headcountStr = "0";

        if (TextUtils.isEmpty(title)) {
            titleInput.setError("Title is required");
            return;
        }

        if (TextUtils.isEmpty(rateStr)) {
            rateInput.setError("Rate is required");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionInput.setError("Description is required");
            return;
        }

        if (headcountOption3.isChecked()) {
            headcountStr = headcountCustomInput.getText().toString().trim();
            if (TextUtils.isEmpty(headcountStr)) {
                headcountCustomInput.setError("Enter headcount");
                return;
            }
        } else if (headcountOption1.isChecked()) {
            headcountStr = "1";
        } else if (headcountOption2.isChecked()) {
            headcountStr = "2";
        } else {
            Toast.makeText(this, "Select a headcount", Toast.LENGTH_SHORT).show();
            return;
        }

        int rate = Integer.parseInt(rateStr);
        int headcount = Integer.parseInt(headcountStr);

        Map<String, Object> jobData = new HashMap<>();
        jobData.put("user_post", userId);
        jobData.put("title", title);
        jobData.put("rate", rate);
        jobData.put("description", description);
        jobData.put("headcount", headcount);

        db.collection("job_recruitments")
                .add(jobData)
                .addOnSuccessListener(docRef -> {
                    Toast.makeText(this, "Job offer posted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to post", Toast.LENGTH_SHORT).show();
                }).addOnCompleteListener(e -> {
                        main.setClickable(true);
                        main.setFocusable(true);
                });
    }
}
