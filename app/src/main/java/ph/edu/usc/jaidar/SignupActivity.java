package ph.edu.usc.jaidar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput, confirmPasswordInput;
    TextView loginLink;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameInput = (EditText) findViewById(R.id.nameInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        confirmPasswordInput = (EditText) findViewById(R.id.confirmPasswordInput);

        loginLink = (TextView) findViewById(R.id.alreadyAccount);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        signupBtn = (Button) findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupAttempt();
            }
        });
    }

    public void signupAttempt(){
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmation = confirmPasswordInput.getText().toString();

        if(validateInput(name, email, password, confirmation)){
            //Save input data in database, and automatically login. Nothing for now.
            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
            startActivity(intent);
        }
    }

    public boolean validateInput(String name, String email, String password, String confirmation){
        if(name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Missing Name input field.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(email.isEmpty()){
            Toast.makeText(getApplicationContext(), "Missing email input field.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!isEmail(email)){
            Toast.makeText(getApplicationContext(), "Invalid Email Address.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Missing password input field.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(confirmation.isEmpty()){
            Toast.makeText(getApplicationContext(), "Missing confirmation input field.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!password.equals(confirmation)){
            Toast.makeText(getApplicationContext(), "Password confirmation does not match.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isEmail(String email){
        //Code to detect valid Email format. code later.
        return true;
    }


//    TO MYK: I moved those lines of code from MainActivity to here cuz I had to code stuff on MainActivity
//    FirebaseAuth mAuth;
//    TextView greets;
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    String testEmail = "23102463@usc.edu.ph";
//    String testPassword = "test1234";
//    greets = findViewById(R.id.greet);
//        mAuth.createUserWithEmailAndPassword(testEmail, testPassword)
//            .addOnCompleteListener(task -> {
//        if (task.isSuccessful()) {
//            greets.setText("Successfully inserted");
//            Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show();
//        } else {
//            Exception e = task.getException();
//            if (e != null) {
//                String message = e.getMessage();
//                if (message != null && message.contains("email address is already in use")) {
//                    greets.setText("Email already registered");
//                    Toast.makeText(this, "Email already registered.", Toast.LENGTH_SHORT).show();
//                } else {
//                    greets.setText("Error: " + message + "");
//                    Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    });

}