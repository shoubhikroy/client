package c.c;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserList extends AppCompatActivity
{
    private static final String NAMESPACE = "http://ws.server/";
    private static final String REGISTER = "getUserList";
    private static final String SOAP_ACTION = "http://egfyz29u.xyz:9999/ws/RemoteCalls";
    private static String URL = "http://egfyz29u.xyz:9999/ws/RemoteCalls?wsdl";

    private static Context context;

    EditText editUserName;

    public static Context getAppContext()
    {
        return UserList.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        editUserName = (EditText) findViewById(R.id.editText2);

        //set up backend
        UserList.context = getApplicationContext();

        if (getIntent().getExtras() != null)
        {
            for (String key : getIntent().getExtras().keySet())
            {
                Object value = getIntent().getExtras().get(key);
                //Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        sendSOAPRequest();

    }


    public void sendSOAPRequest()
    {
        new UserList.SendMsg().execute(FirebaseInstanceId.getInstance().getToken());

    }

    public class SendMsg extends AsyncTask<String, Void, String>
    {

        private Exception exception;

        protected String doInBackground(String... urls)
        {
            return null;
        }

        protected void onPostExecute(String feed)
        {
            editUserName.setText("");
        }
    }
}
