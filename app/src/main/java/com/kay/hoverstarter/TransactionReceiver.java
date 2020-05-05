package com.kay.hoverstarter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

public class TransactionReceiver extends BroadcastReceiver {
    public TransactionReceiver() { }
    @Override
    public void onReceive(Context context, Intent intent) {

        String uuid = intent.getStringExtra("uuid");

        Log.d("sms_response", uuid);

        if(uuid!= null){
        if (intent.hasExtra("balance")) {
            HashMap t_extras = (HashMap) intent
                    .getSerializableExtra("balance");
            Log.d("sms_response", t_extras.toString());

        }else{
            Log.d("sms_response", "not found");
        }
}
    }
}
