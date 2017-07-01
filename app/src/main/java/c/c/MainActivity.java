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

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import handler.beans.KRegisterLoginInfo;
import handler.beans.RegisterLoginResult;
import handler.SoapHandler;
import handler.beans.input.RegisterLoginInfo;
import model.objectManagers.SettingsManager;

public class MainActivity extends AppCompatActivity
{
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
        String username;
        String password;

        @Override
        protected RegisterLoginResult doInBackground(String... params)
        {
            username = params[1];
            password = params[2];
            RegisterLoginInfo rli = new RegisterLoginInfo(params, SoapHandler.REGISTER);
            SoapObject j = SoapHandler.MakeCall(SoapHandler.URL, rli.getSoapEnvelope(), SoapHandler.SOAP_ACTION);
            if (null != j)
                return new RegisterLoginResult(j);
            return null;
        }

        protected void onPostExecute(RegisterLoginResult result)
        {
            if (null != result)
            {
                if (result.successFlag)
                {
                    SettingsManager sM = SettingsManager.getInstance();
                    sM.createSetting("loginInfo", username, password, null);
                    Intent intent = new Intent(MainActivity.this, UserList.class);
                    startActivity(intent);
                } else
                {
                    textStatus.setText("invalid Setting/pass combo");
                }
            } else
            {
                textStatus.setText("cannot contact server, its probably down");
            }
        }
    }
}