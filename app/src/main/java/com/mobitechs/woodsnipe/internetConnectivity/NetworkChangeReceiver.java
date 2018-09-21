package com.mobitechs.woodsnipe.internetConnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mobitechs.woodsnipe.dialogBox.NotifyNetworkConnectionMessage;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetworkInfo.State mState;
    public NetworkChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
      //  throw new UnsupportedOperationException("Not yet implemented");
        boolean noConnectivity =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

        if (noConnectivity) {
            mState = NetworkInfo.State.DISCONNECTED;
            Intent networkReciever = new Intent(context, NotifyNetworkConnectionMessage.class);
            networkReciever.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            networkReciever.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(networkReciever);
        } else {
            mState = NetworkInfo.State.CONNECTED;
        }
    }
}
