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
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "About" : "Posts")
        ).attach();

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
        bottomNavigationView.setSelectedItemId(R.id.profile);

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
}
