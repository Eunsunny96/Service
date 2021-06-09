package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    public void onClick(View v){
        AlarmManager am=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent=null;
        PendingIntent sender=null;
        switch(v.getId()){
            case R.id.button1:
                //리시버 호출
                intent=new Intent(this, AlarmReceiver.class);
                //예약 작업
                sender=PendingIntent.getBroadcast(this, 0,
                        intent, 0);
                //현재 시각
                Calendar cal= Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                long currentTime=cal.getTimeInMillis();
                //5초 후
                cal.add(Calendar.SECOND, 5);
                long alarmTime=cal.getTimeInMillis();
                if(alarmTime>currentTime){
                    //알람 호출, 리시버에서 메시지 수신
                    am.set(AlarmManager.RTC, cal.getTimeInMillis(), sender);
                }
                break;
            case R.id.button2:
                cal=Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                intent=new Intent(this, AlarmReceiver.class);
                sender=PendingIntent.getBroadcast(this, 0,
                        intent, 0);
                //반복 알람, 5초 후 시작, 30분 간격
                am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime()+1000*5,
                        AlarmManager.INTERVAL_HOUR, sender);
                break;
            case R.id.button3:
                intent=new Intent(this, AlarmReceiver.class);
                sender=PendingIntent.getBroadcast(this, 0,
                        intent, 0);
                //알람 취소
                am.cancel(sender);
                Toast.makeText(this, "알람이 취소되었습니다.",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}