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
                AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
                Log.d(TAG, "Volume MAX");
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 1000, 300000);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "VSService 중지", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy()");
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "VSService 시작", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart()");
        return super.onStartCommand(intent, flags, startId);
    }

}
