package com.example.notificationalarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private JobScheduler mScheduler;
    private static final int JOB_ID = 0;
    private static final int NOTIFICATION_ID = 0;
    public ArrayList<String> arr;
    TextView txt;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.quote);

        arr = new ArrayList<String>();
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle==null) {
            scheduleJob();
        }
        if(bundle!=null) {
            String data = (String) bundle.get("data");
            try {
                JSONArray jsonArray = new JSONArray(data);
                ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        listdata.add((JSONObject) jsonArray.get(i));
                    }
                    Log.d("EMA", String.valueOf(listdata));
                    for(int i=0; i<listdata.size(); i++) {
                        Log.d("QUOTES", String.valueOf(listdata.get(i).get("quote")));
                        arr.add(String.valueOf(listdata.get(i).get("quote")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent notifyIntent = new Intent(this,MyReceiver.class);
            notifyIntent.putExtra("array",arr);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                    1000*60*60*24, pendingIntent); //repeat everyday
            }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        Log.i("EMA","se povika scheduleJob()");
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresCharging(true);
        JobInfo myJobInfo = builder.build();
        mScheduler.schedule(myJobInfo);
    }
}