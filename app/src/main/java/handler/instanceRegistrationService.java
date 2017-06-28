package handler;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import handler.intentService.NetstatService;


public class instanceRegistrationService extends FirebaseInstanceIdService
{

    private static final String TAG = "instanceRegistrationService";

    @Override
    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //  Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        startService(new Intent(this, NetstatService.class));
    }
}