package app.kosanworks.smsgateway;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ghost on 05/06/17.
 */

public class SendFragment extends Fragment {

    public static SendFragment newInstance() {
        return new SendFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_send, container, false);
        final EditText etTo = (EditText) v.findViewById(R.id.et_number);
        final EditText etMessage = (EditText) v.findViewById(R.id.et_message);
        Button btn = (Button) v.findViewById(R.id.btn_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTo.getText().toString().isEmpty() || etMessage.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Masukan nomer dan pesan anda!", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(etTo.getText().toString(), null, etMessage.getText().toString(), null, null);
                        Toast.makeText(getContext(), "SMS terkirim!", Toast.LENGTH_SHORT).show();
                        etTo.setText("");
                        etMessage.setText("");
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Gagal mengirim sms, coba lagi!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

            }
        });
        return v;

    }
}
