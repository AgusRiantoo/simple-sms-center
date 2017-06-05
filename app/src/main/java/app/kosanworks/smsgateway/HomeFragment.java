package app.kosanworks.smsgateway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ghost on 05/06/17.
 */

public class HomeFragment extends Fragment{
    SwipeRefreshLayout refreshLayout;
    DatabaseHandler db;
    private ListView listView;
    TextView textView;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, parent, false);

        listView = (ListView) view.findViewById(R.id.listview);
        textView = (TextView) view.findViewById(R.id.tvMessage);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTransaksi();
                refreshLayout.setRefreshing(false);
            }
        });

        db = new DatabaseHandler(getActivity());
        getTransaksi();

        return view;
    }

    private void getTransaksi() {
        List<Pesan> list = db.getAll();
        if (list.isEmpty())textView.setVisibility(View.VISIBLE);

        AdapterPesan adapter = new AdapterPesan(getActivity(),R.layout.layout_message, list);
        listView.setAdapter(adapter);
    }
}
