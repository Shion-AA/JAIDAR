package ph.edu.usc.jaidar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    EditText locationField, backgroundField, experienceField;
    Button editBtn, saveBtn;
    TextView nameDisplay, emailDisplay;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        locationField = findViewById(R.id.locationInput);
        backgroundField = findViewById(R.id.backgroundInput);
        experienceField = findViewById(R.id.experienceInput);
        editBtn = findViewById(R.id.editBtn);
        saveBtn = findViewById(R.id.saveBtn);

        // Initially disable fields
        setEditingEnabled(false);
        loadUserData();

        editBtn.setOnClickListener(v -> setEditingEnabled(true));

        saveBtn.setOnClickListener(v -> {
            String loc = locationField.getText().toString().trim();
            String bg = backgroundField.getText().toString().trim();
            String exp = experienceField.getText().toString().trim();

            Map<String, Object> updates = new HashMap<>();
            updates.put("location", loc);
            updates.put("background", bg);
            updates.put("experience", exp);

            db.collection("users").document(uid).update(updates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                        setEditingEnabled(false);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
        nameDisplay = findViewById(R.id.nameDisplay);
        emailDisplay = findViewById(R.id.emailDisplay);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            emailDisplay.setText(user.getEmail());

            db.collection("users").document(user.getUid()).get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            nameDisplay.setText(snapshot.getString("name"));
                        }
                    });
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile); // ✅ This line must be inside a method

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(this, HomePageActivity.class));
                overridePendingTransition(0, 0);
                return true;
//            } else if (id == R.id.message) {
//                startActivity(new Intent(this, MessageActivity.class));
//                overridePendingTransition(0, 0);
//                return true;
            } else if (id == R.id.profile) {
                return true;
            }
            return false;
        });

    }

    private void loadUserData() {
        db.collection("users").document(uid).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        locationField.setText(snapshot.getString("location"));
                        backgroundField.setText(snapshot.getString("background"));
                        experienceField.setText(snapshot.getString("experience"));
                    }
                });
    }

    private void setEditingEnabled(boolean enabled) {
        locationField.setEnabled(enabled);
        backgroundField.setEnabled(enabled);
        experienceField.setEnabled(enabled);
        saveBtn.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }
}
