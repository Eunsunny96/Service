package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyService extends Service {
    boolean isQuit; //서비스 실행 여부 flag 변수
    //서비스가 바인딩될 때 호출
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    //서비스가 생성될 때
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        isQuit=false;
        MyThread th=new MyThread(this); //백그라운드 스레드
        th.start(); //시작
        return Service.START_REDELIVER_INTENT;
    }

    //서비스가 종료될 때
    @Override
    public void onDestroy() {
        super.onDestroy();
        isQuit=true;
        Toast.makeText(getApplicationContext(), "종료되었습니다.",
                Toast.LENGTH_SHORT).show();
    }
    //화면 처리 작업
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==0){
                String item=(String)msg.obj; //메시지 내용
                Toast.makeText(getApplicationContext(),item,
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    class MyThread extends Thread {
        String[] item={"사과","포도","바나나","살구","레몬"};
        public MyThread(MyService parent){

        }
        @Override
        public void run() {
            for(int i=0; isQuit==false; i++){
                Message msg=new Message();
                msg.what=0; //메시지 코드
                msg.obj=item[i % item.length]; //메시지 내용
                handler.sendMessage(msg); //핸들러에게 화면 처리 요청
                try {
                    Thread.sleep(1000); //1초 멈춤
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}