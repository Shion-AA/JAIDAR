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

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);

        loginBtn = (Button) findViewById(R.id.loginBtn);

        signupLink = (TextView) findViewById(R.id.signUpLink);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAttempt();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });


    }

    public void loginAttempt(){
        String emailStr = email.getText().toString();
        String password = this.password.getText().toString();

        if(validateInput(emailStr, password)){
            //database whatever. Static for now.
            if(emailStr.equals("johndoe@gmail.com") && password.equals("JohnDoe123!")){
                //database login call. Static intent for now.
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Successfully Logged-in", Toast.LENGTH_SHORT).show();
            } else {
                this.password.setText("");
                Toast.makeText(getApplicationContext(), "Wrong credentials, please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid data input. Please try again with different data.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validateInput(String emailStr, String password){
        if(!emailStr.isEmpty() && isEmail(emailStr) && !password.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmail(String emailStr){
        //Code to detect valid Email format. code later.
        return true;
    }
}