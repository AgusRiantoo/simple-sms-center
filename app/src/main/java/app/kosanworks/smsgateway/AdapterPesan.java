package app.kosanworks.smsgateway;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ghost on 05/06/17.
 */

public class AdapterPesan extends ArrayAdapter<Pesan> {
    public AdapterPesan(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pesan> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.layout_message, null);
        }

        Pesan p = getItem(position);
        if (p != null){
            TextView tv1 = (TextView) v.findViewById(R.id.tvFrom);
            TextView tv2 = (TextView) v.findViewById(R.id.tvMessage);
            TextView tv3 = (TextView) v.findViewById(R.id.tvDate);

            tv1.setText(p.getFrom());
            tv2.setText(p.getMessage());
            tv3.setText(p.getDate());
        }

        return v;
    }
}
