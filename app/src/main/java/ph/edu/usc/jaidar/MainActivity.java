package ph.edu.usc.jaidar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView greets;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String testEmail = "23102463@usc.edu.ph";
        String testPassword = "test1234";
        greets = findViewById(R.id.greet);
        mAuth.createUserWithEmailAndPassword(testEmail, testPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        greets.setText("Successfully inserted");
                        Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Exception e = task.getException();
                        if (e != null) {
                            String message = e.getMessage();
                            if (message != null && message.contains("email address is already in use")) {
                                greets.setText("Email already registered");
                                Toast.makeText(this, "Email already registered.", Toast.LENGTH_SHORT).show();
                            } else {
                                greets.setText("Error: " + message + "");
                                Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


    }
}
