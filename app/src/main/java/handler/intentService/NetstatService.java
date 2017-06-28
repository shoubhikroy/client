package handler.intentService;


import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Service for sending this device's registrationToken to your server to remember it.
 *
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class NetstatService extends IntentService
{

    public NetstatService()
    {
        super("NetstatService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        final String regToken = FirebaseInstanceId.getInstance().getToken();

        //send netstat

    }


}