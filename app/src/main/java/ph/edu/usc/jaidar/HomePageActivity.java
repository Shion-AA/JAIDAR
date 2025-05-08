package ph.edu.usc.jaidar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePageActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button logoutBtn;
    Button TEMPEDITOR, TEMPVIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page); // Shows your XML layout

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        String uid = mAuth.getCurrentUser().getUid();
//        TextView nameTextView = findViewById(R.id.name);
//        db.collection("users").document(uid).get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        String name = documentSnapshot.getString("name");
//                        nameTextView.setText(name != null ? name : "User");
//                    } else {
//                        nameTextView.setText("User");
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    nameTextView.setText("User"); // fallback
//                });

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
        TEMPEDITOR = findViewById(R.id.TEMP_EDITOR);
        TEMPEDITOR.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RecruitmentEditorActivity.class);
            startActivity(intent);
        });
        TEMPVIEW = findViewById(R.id.TEMP_VIEW);
        TEMPVIEW.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RecruitmentViewActivity.class);
            startActivity(intent);
        });
        //END TEMPORARY INTENT

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home); // Highlight Home icon

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                // Already on Home - do nothing
                return true;
            }
//            else if (id == R.id.message) {
//                startActivity(new Intent(HomePageActivity.this, MessageActivity.class));
//                return true;
//            }
            else if (id == R.id.profile) {
                startActivity(new Intent(HomePageActivity.this, UserProfileActivity.class));
                return true;
            }

            return false;
        });
    }
}
