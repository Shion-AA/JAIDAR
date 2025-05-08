package ph.edu.usc.jaidar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page); // Shows your XML layout

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes
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
