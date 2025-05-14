package ph.edu.usc.jaidar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView nameDisplay, emailDisplay;
    String profileUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        profileUid = getIntent().getStringExtra("profileUid");
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        ViewPageAdapter adapter = new ViewPageAdapter(this, profileUid);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "About" : "Reviews")
        ).attach();

        nameDisplay = findViewById(R.id.nameDisplay);
        emailDisplay = findViewById(R.id.emailDisplay);
        if (profileUid != null) {
            db.collection("users").document(profileUid).get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            nameDisplay.setText(snapshot.getString("name"));
                            emailDisplay.setText(snapshot.getString("email")); // use Firestore email
                        } else {
                            nameDisplay.setText("Unknown User");
                            emailDisplay.setText("No email");
                        }
                    })
                    .addOnFailureListener(e -> {
                        nameDisplay.setText("Error loading user");
                        emailDisplay.setText("Error");
                    });
        }
        navigation(mAuth);

    }

    private void navigation(FirebaseAuth mAuth){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        String currentUid = mAuth.getUid();
        if (currentUid != null && currentUid.equals(profileUid)) {
            bottomNavigationView.setSelectedItemId(R.id.profile);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                startActivity(new Intent(this, HomePageActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.activity) {
                startActivity(new Intent(this, ActivitySectionActivity.class));
                overridePendingTransition(0, 0);
                return true;
//            } else if (id == R.id.message) {
//                startActivity(new Intent(this, MessageActivity.class));
//                overridePendingTransition(0, 0);
//                return true;
            } else if (id == R.id.profile) {
                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.putExtra("profileUid", currentUid);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
