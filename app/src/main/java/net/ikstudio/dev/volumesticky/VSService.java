package net.ikstudio.dev.volumesticky;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class VSService extends Service{
    private static final String TAG = "VSService";
    private TimerTask mTask;
    private Timer mTimer;
    private int powerswitch = 0;

    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");

        mTask = new TimerTask() {
            @Override
            public void run() {

                //서비스가 활성화 상태일 때만 실행하는 코드를 임시로 추가. (powerswitch 값이 0이 되더라도 주기적으로 체크함)
                if (powerswitch == 1) {
                    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                    //소리 설정이 노말모드 상태일 때만 볼륨을 최대로 설정
                    switch (audioManager.getRingerMode()) {
                        case AudioManager.RINGER_MODE_VIBRATE:
                            // 진동
                            Log.d(TAG, "Checked Status: VIBRATE");
                            break;
                        case AudioManager.RINGER_MODE_NORMAL:
                            //소리
                            audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
                            Log.d(TAG, "Checked Status: RING");
                            Log.d(TAG, "Volume MAX");
                            break;
                        case AudioManager.RINGER_MODE_SILENT:
                            // 무음
                            Log.d(TAG, "Checked Status: SILENT");
                            break;
                    }
                }
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 1000, 60000);  //서비스 실행 시 1초 후 체크. 이후 1분마다 체크
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "VSService 중지", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        powerswitch = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "VSService 시작", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart()");
        powerswitch = 1;
        return super.onStartCommand(intent, flags, startId);
    }

}
