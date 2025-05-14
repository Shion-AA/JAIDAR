package ph.edu.usc.jaidar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OffersFragment extends Fragment {
    Context context;
    private RecyclerView outboundList, inboundList;
    private TextView outboundEmpty, inboundEmpty;
    public OffersFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        outboundList = view.findViewById(R.id.outbound_list);
        inboundList = view.findViewById(R.id.inbound_list);
        outboundEmpty = view.findViewById(R.id.outbound_empty);
        inboundEmpty = view.findViewById(R.id.inbound_empty);

        loadOffers();

    }
    private void loadOffers() {

    }

}
