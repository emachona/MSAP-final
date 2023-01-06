package com.example.notificationalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver(){
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MIntentService.class);
        ArrayList<String> niza = (ArrayList<String>) intent.getSerializableExtra("array");
        intent1.putExtra("quotes", niza);
        context.startService(intent1);
    }
}
