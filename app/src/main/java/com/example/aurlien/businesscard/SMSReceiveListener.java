package com.example.aurlien.businesscard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aurélien on 18/04/2017.
 */

public class SMSReceiveListener  extends BroadcastReceiver {
    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private BusinessCardDAO bcardDao;
    private Intent mIntent;

    public void onReceive(Context context, Intent intent) {
        mIntent = intent;
        String action = intent.getAction();

        if (action.equals(ACTION_SMS_RECEIVED)) {
            String address = null, str = "";
            SmsMessage[] msgs = getMessagesFromIntent(mIntent);

            if (msgs != null) {
                for (int i = 0; i < msgs.length; i++) {
                    address = msgs[i].getOriginatingAddress();
                    str += msgs[i].getDisplayMessageBody();
                    str += "\n";
                    str = str.replace("(", "{");
                    str = str.replace(")","}");
                }
            }

            if (address != null) {
                bcardDao = new BusinessCardDAO(context);
                bcardDao.open();

                try {
                    JSONObject sms = new JSONObject(str);
                    String numero = sms.getString("numero").replace("{", "(");
                    Log.d("TAG", "sms : " + String.valueOf(sms));
                    numero = numero.replace("}", ")");
                    BusinessCard bcard = new BusinessCard(sms.getString("nom"), numero , sms.getString("email"), sms.getString("adresse"), sms.getString("longitude"), sms.getString("latitude"));
                    bcardDao.ajouter(bcard);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "Vous avez reçu une carte par SMS", duration);
                    toast.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }

        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];

        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }

        return msgs;
    }
}
