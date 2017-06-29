package c.c;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import handler.RemoteCallsImplService.RegisterLoginInfo;
import handler.RemoteCallsImplService.UserListResult;
import handler.RemoteCallsImplService.UserListResult;
import handler.SoapHandler;

public class UserList extends AppCompatActivity
{
    private static Context context;
    ArrayList<String> list;
    ListView listView;

    public static Context getAppContext()
    {
        return UserList.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        listView = (ListView) findViewById(R.id.ListView);

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

        list = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        sendSOAPRequest();

    }


    public void sendSOAPRequest()
    {
        new UserList.SendMsg().execute(FirebaseInstanceId.getInstance().getToken(),
                "username",
                "password");

    }

    public class SendMsg extends AsyncTask<String, Void, UserListResult>
    {

        private Exception exception;

        @Override
        protected UserListResult doInBackground(String... params)
        {
            //Object retObj = SoapHandler.MakeCall(SoapHandler.URL,rli.getSoapEnvelope(),SoapHandler.SOAP_ACTION);
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
            return getUserList(arg0);
        }

        protected void onPostExecute(UserListResult result)
        {
            if (null != result)
            {
                if ((boolean) result.getProperty(1))
                {
                    list.add("yes");
                }
            }
        }
    }

    private UserListResult getUserList(RegisterLoginInfo arg0)
    {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        SoapObject soapReq = new SoapObject(SoapHandler.NAMESPACE, SoapHandler.USERLIST);
        soapEnvelope.addMapping(SoapHandler.NAMESPACE, "arg0", new RegisterLoginInfo().getClass());
        soapReq.addProperty("arg0", arg0);

        soapEnvelope.setOutputSoapObject(soapReq);

        HttpTransportSE httpTransport = new HttpTransportSE(SoapHandler.URL);
        try
        {
            httpTransport.call(SoapHandler.SOAP_ACTION, soapEnvelope);

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
                    UserListResult resultVariable = new UserListResult(j);
                    return resultVariable;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
