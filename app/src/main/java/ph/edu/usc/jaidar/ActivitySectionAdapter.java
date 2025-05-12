package ph.edu.usc.jaidar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ActivitySectionAdapter extends FragmentStateAdapter {
    public ActivitySectionAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new OffersFragment();    //ill make this later
        } else {
            fragment = new YourPostsFragment();
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
