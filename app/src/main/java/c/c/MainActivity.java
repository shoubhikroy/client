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

public class MainActivity extends AppCompatActivity
{
    private static final String NAMESPACE = "http://ws.server/";
    private static final String REGISTER = "registerLogin";
    private static final String SOAP_ACTION = "http://egfyz29u.xyz:9999/ws/hello";
    private static String URL = "http://egfyz29u.xyz:9999/ws/hello?wsdl";

    private static Context context;

    EditText editUserName;
    EditText editPassword;

    public static Context getAppContext()
    {
        return MainActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editText);

        //set up backend
        MainActivity.context = getApplicationContext();

        if (getIntent().getExtras() != null)
        {
            for (String key : getIntent().getExtras().keySet())
            {
                Object value = getIntent().getExtras().get(key);
                //Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendSOAPRequest();

            }
        });
    }


    public void sendSOAPRequest()
    {
        new SendMsg().execute(FirebaseInstanceId.getInstance().getToken(),
                editUserName.getText().toString().isEmpty() ? "empty" : editUserName.getText().toString(),
                editPassword.getText().toString().isEmpty() ? "empty" : editPassword.getText().toString());

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

            PropertyInfo p2 = new PropertyInfo();
            p2.name = "arg1";
            p2.type = PropertyInfo.STRING_CLASS;
            p2.setValue(urls[1]);

            PropertyInfo p3 = new PropertyInfo();
            p3.name = "arg2";
            p3.type = PropertyInfo.STRING_CLASS;
            p3.setValue(urls[2]);


            //add param to object
            request.addProperty(p1);
            request.addProperty(p2);
            request.addProperty(p3);

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
            // TODO: check this.exception
            // TODO: do something with the feed
            if ("Invalid".equals(feed))
            {
                Toast toast = Toast.makeText(context, "Invalid User/Pass", Toast.LENGTH_SHORT);
                toast.show();
            } else if ("Registered".equals(feed) || "Success".equals(feed))
            {
                Intent intent = new Intent(MainActivity.this, UserList.class);
                startActivity(intent);
            }
        }
    }
}