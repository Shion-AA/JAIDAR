package ph.edu.usc.jaidar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JobListingDetailsActivity extends AppCompatActivity {

    private ImageButton backBtn, companyLogo;
    private TextView companyName, jobTitle, subtitle, aboutTitle, aboutDescription, overviewTitle, overviewDetails;
    private Button applyBtn, saveBtn;
    private ImageView waveBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing_details);

        backBtn = findViewById(R.id.backBtn);
        companyLogo = findViewById(R.id.companyLogo);
        companyName = findViewById(R.id.companyName);
        jobTitle = findViewById(R.id.jobTitle);
        subtitle = findViewById(R.id.subtitle);
        applyBtn = findViewById(R.id.applyBtn);
        saveBtn = findViewById(R.id.saveBtn);
        waveBg = findViewById(R.id.waveBg);

        aboutTitle = findViewById(R.id.aboutTitle);
        aboutDescription = findViewById(R.id.aboutDescription);


        overviewTitle = findViewById(R.id.overviewTitle);
        overviewDetails = findViewById(R.id.overviewDetails);

        backBtn.setOnClickListener(view -> finish());

        applyBtn.setOnClickListener(view -> {
            Toast.makeText(JobListingDetailsActivity.this, "Applied successfully!", Toast.LENGTH_SHORT).show();
        });

        saveBtn.setOnClickListener(view -> {
            Toast.makeText(JobListingDetailsActivity.this, "Job saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
