package c.c;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import services.RemoteCallsImplService.RegisterLoginInfo;
import services.RemoteCallsImplService.RegisterLoginResult;

public class MainActivity extends AppCompatActivity
{
    private static final String NAMESPACE = "http://jaxws.server/";
    private static final String REGISTER = "registerLogin";
    private static final String SOAP_ACTION = "http://71.190.149.193:9999/ws/rpc";
    private static String URL = "http://71.190.149.193:9999/ws/rpc?wsdl";

    private static Context context;

    EditText editUserName;
    EditText editPassword;
    TextView textStatus;

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
        textStatus = (TextView) findViewById(R.id.textStatus);

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

    public class SendMsg extends AsyncTask<String, Void, RegisterLoginResult>
    {

        private Exception exception;

        @Override
        protected RegisterLoginResult doInBackground(String... params)
        {
/*          SoapObject request = new SoapObject(NAMESPACE, REGISTER);

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
            return null;*/


            SoapObject soapReq = new SoapObject(NAMESPACE, REGISTER);

            SoapObject request = new SoapObject();

            PropertyInfo p1 = new PropertyInfo();
            p1.name = "key";
            p1.type = PropertyInfo.STRING_CLASS;
            p1.setValue(params[0]);

            PropertyInfo p2 = new PropertyInfo();
            p2.name = "userName";
            p2.type = PropertyInfo.STRING_CLASS;
            p2.setValue(params[1]);

            PropertyInfo p3 = new PropertyInfo();
            p3.name = "password";
            p3.type = PropertyInfo.STRING_CLASS;
            p3.setValue(params[2]);

            request.addProperty(p1);
            request.addProperty(p2);
            request.addProperty(p3);

            RegisterLoginInfo arg0 = new RegisterLoginInfo(request);
            soapReq.addProperty("arg0", arg0);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.implicitTypes = true;
            soapEnvelope.addMapping("http://jaxws.server/", "arg0", new RegisterLoginInfo().getClass());
            soapEnvelope.setOutputSoapObject(soapReq);


            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            try
            {
                httpTransport.call(SOAP_ACTION, soapEnvelope);

                Object retObj = soapEnvelope.bodyIn;

                if (retObj instanceof SoapFault)
                {
                    SoapFault fault = (SoapFault) retObj;
                    Exception ex = new Exception(fault.faultstring);
                } else
                {
                    SoapObject result = (SoapObject) retObj;

                    if (result.getPropertyCount() > 0)
                    {
                        Object obj = result.getProperty(0);
                        SoapObject j = (SoapObject) obj;
                        RegisterLoginResult resultVariable = new RegisterLoginResult(j);
                        return resultVariable;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(RegisterLoginResult result)
        {
            if (null != result)
            {
                String test = (String) result.getProperty(0);
                if ((boolean) result.getProperty(1))
                {
                    Intent intent = new Intent(MainActivity.this, UserList.class);
                    startActivity(intent);
                } else
                {
                    textStatus.setText("invalid user/pass combo");
                }
            } else
            {
                textStatus.setText("cannot contact server, its probably down");
            }
        }
    }
}