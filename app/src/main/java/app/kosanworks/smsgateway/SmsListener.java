package app.kosanworks.smsgateway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ghost on 05/06/17.
 */

public class SmsListener extends BroadcastReceiver {

    Context c;
    private String messageBody;
    private String messageFrom;
    String[] split;

    @Override
    public void onReceive(Context context, Intent intent) {
        c = context;
        try {
            Toast.makeText(context, "Sms Received..", Toast.LENGTH_SHORT).show();
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    messageBody = smsMessage.getMessageBody().toUpperCase();
                    messageFrom = smsMessage.getOriginatingAddress();
                    Log.e("message", messageBody);
                    Log.e("from", messageFrom);

                    split = messageBody.split("\\s+");

                    switch (split[0]){
                        case "NILAI":
                            request("http://192.168.88.34/sms/api/getNilai?id="+split[1]+"&kdmp="+split[2]);
                            break;
                        case "INFO":
                            request("http://192.168.88.34/sms/api/getSiswa/"+split[1]);
                            break;
                        case "HELP":
                            sendMessage(messageFrom, "-Ketik (info no_induk) untuk mengetahui informasi siswa, contoh : info 123. \n" +
                                    "-Ketik (nilai no_induk kdmp) untuk mengetahui nilai siswa, contoh : nilai 123 mtk51. \n" +
                                    "### SEKOLAHKU SMS CENTER ###");
                            break;
                        default:
                            sendMessage(messageFrom, "Mohon maaf, format SMS tidak dikenali. Silakan ketik help untuk bantuan. \n" +
                                    "### SEKOLAHKU SMS CENTER ###");
                            break;
                    }

                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                    String tanggal = df.format(Calendar.getInstance().getTime());

                    DatabaseHandler db = new DatabaseHandler(context);
                    Pesan p = new Pesan();
                    p.setMessage(messageBody);
                    p.setFrom(messageFrom);
                    p.setDate(tanggal);
                    db.add(p);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void request(String url){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("null")){
                    sendMessage(messageFrom, "Mohon maaf, format SMS tidak dikenali. Silakan ketik help untuk bantuan. " +
                            "### SEKOLAHKU SMS CENTER ###");
                }else{
                    try {
                        JSONObject object = new JSONObject(response);
                        String pesan = "";
                        if (split[0].equals("NILAI")) {
                            pesan = "- no induk : " + object.getString("no_induk") + "\n" +
                                    "- kdmp : " + object.getString("kdmp") + "\n" +
                                    "- nilai : " + object.getString("nilai") + "\n" +
                                    "### SEKOLAHKU SMS CENTER ###";
                        }else{
                            pesan = "- no induk : " + object.getString("no_induk") + "\n" +
                                    "- nama : " + object.getString("nama") + "\n" +
                                    "- jekel : " + object.getString("jekel") + "\n" +
                                    "- alamat : " + object.getString("alamat") + "\n" +
                                    "- tanggal lahir : " + object.getString("ttl") + "\n" +
                                    "### SEKOLAHKU SMS CENTER ###";
                        }
                        sendMessage(messageFrom, pesan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sendMessage(messageFrom, "Mohon maaf, server sedang bermasalah. Silahkan coba beberapa saat lagi. " +
                        "### SEKOLAHKU SMS CENTER ###");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(request);
    }

    void sendMessage(String to, String msg){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(to, null, msg, null, null);
            Toast.makeText(c, "SMS terkirim!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(c, "Gagal mengirim sms, coba lagi!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
