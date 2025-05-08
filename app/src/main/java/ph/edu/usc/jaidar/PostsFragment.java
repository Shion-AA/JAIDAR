package ph.edu.usc.jaidar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PostsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts_fragment, container, false);

        TextView comingSoonText = view.findViewById(R.id.comingSoonText);
        comingSoonText.setText("🚧 Posts feature coming soon!");

        return view;
    }
}
