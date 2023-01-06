package com.example.notificationalarm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class RESTapi extends AsyncTask<Void, Void, String> {
    public Context context;
    public RESTapi(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.d("EMA", "se povika doInBackground");
        String json = NetworkUtils.getInfo();
//        Log.d("EMA", "podatoci:" + json);
        return json;
    }
    @Override
    protected void onPostExecute(String data){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }
}
