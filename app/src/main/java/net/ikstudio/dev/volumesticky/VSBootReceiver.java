package net.ikstudio.dev.volumesticky;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class VSBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, VSService.class);
        context.startService(i);
    }
}
