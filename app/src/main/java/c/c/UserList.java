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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by xargie on 6/15/2017.
 */

public class UserList extends AppCompatActivity
{
    private static final String NAMESPACE = "http://ws.server/";
    private static final String REGISTER = "getUserList";
    private static final String SOAP_ACTION = "http://egfyz29u.xyz:9999/ws/hello";
    private static String URL = "http://egfyz29u.xyz:9999/ws/hello?wsdl";

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
            SoapObject request = new SoapObject(NAMESPACE, REGISTER);

            //set method parameter properties
            PropertyInfo p1 = new PropertyInfo();
            p1.name = "arg0";
            p1.type = PropertyInfo.STRING_CLASS;
            p1.setValue(urls[0]);

            //add param to object
            request.addProperty(p1);

            //create envolope
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try
            {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                return resultsRequestSOAP.toString();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String feed)
        {
            editUserName.setText(feed);
        }
    }
}
