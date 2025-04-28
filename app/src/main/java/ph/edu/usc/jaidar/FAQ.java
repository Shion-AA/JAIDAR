package ph.edu.usc.jaidar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class FAQ extends AppCompatActivity {

        private ListView listViewFAQ;
        private FAQAdapter faqAdapter;
        private ArrayList<FAQItem> faqList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_faq);

            listViewFAQ = findViewById(R.id.listViewFAQ);
            faqList = new ArrayList<>();

            // Sample Questions and Answers
            faqList.add(new FAQItem("How do I send my resume/cover letter?", "You can return items within 30 days."));
            faqList.add(new FAQItem("How do I track my job applications?", "Shipping usually takes 3-5 business days."));
            faqList.add(new FAQItem("How do I search for jobs or internships?", "You will receive a tracking link by email."));
            faqList.add(new FAQItem("How do I reset my password?", "You will receive a tracking link by email."));
            faqList.add(new FAQItem("How can I contact support for help?", "You will receive a tracking link by email."));
            faqList.add(new FAQItem("How do I delete my account?", "You will receive a tracking link by email."));


            faqAdapter = new FAQAdapter(this, faqList);
            listViewFAQ.setAdapter(faqAdapter);
        }
    }
