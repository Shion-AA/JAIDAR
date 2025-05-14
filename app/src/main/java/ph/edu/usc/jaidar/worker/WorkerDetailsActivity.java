package ph.edu.usc.jaidar.worker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ph.edu.usc.jaidar.R;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class WorkerDetailsActivity extends AppCompatActivity {

    private TextView titleView, descriptionView, rateView, tagView, posterNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details);

        titleView = findViewById(R.id.workerTitle);
        descriptionView = findViewById(R.id.workerDescription);
        rateView = findViewById(R.id.workerRate);
        tagView = findViewById(R.id.workerTag);
        posterNameView = findViewById(R.id.posterName);

        titleView.setText(getIntent().getStringExtra("title"));
        descriptionView.setText(getIntent().getStringExtra("description"));
        rateView.setText("₱" + getIntent().getIntExtra("rate", 0));
        tagView.setText(getIntent().getStringExtra("tag"));

        // Fetch poster name
        String posterUid = getIntent().getStringExtra("posterUid");
        FirebaseFirestore.getInstance().collection("users")
                .document(posterUid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    posterNameView.setText(snapshot.getString("name"));
                })
                .addOnFailureListener(e -> {
                    posterNameView.setText("Unknown Poster");
                });
    }
}
