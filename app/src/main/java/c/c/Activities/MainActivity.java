package c.c.Activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import c.c.Fragments.RegisterLoginFragment;
import c.c.R;
import model.beans.output.RegisterLoginResult;
import model.templates.SoapCallBack;
import model.objectManagers.SettingsManager;

public class MainActivity extends AppCompatActivity implements SoapCallBack<RegisterLoginResult>
{
    private static Context context;

    EditText editUserName;
    EditText editPassword;
    TextView textStatus;

    public static Context getAppContext()
    {
        return MainActivity.context;
    }

    private RegisterLoginFragment mRegisterLoginFragment;
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editText);
        textStatus = (TextView) findViewById(R.id.textStatus);

        MainActivity.context = getApplicationContext();

        if (getIntent().getExtras() != null)
        {
            for (String key : getIntent().getExtras().keySet())
            {
                Object value = getIntent().getExtras().get(key);
                //Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        mRegisterLoginFragment = RegisterLoginFragment.getInstance(getSupportFragmentManager());

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
/*                sendSOAPRequest();*/
                String params[] = {
                        editUserName.getText().toString().isEmpty() ? "empty" : editUserName.getText().toString(),
                        editPassword.getText().toString().isEmpty() ? "empty" : editPassword.getText().toString()
                };


                startDownload(params[0], params[1]);
            }
        });
    }

    private void startDownload(String username, String password)
    {
        if (!mDownloading && mRegisterLoginFragment != null)
        {
            // Execute the async download.
            mRegisterLoginFragment.startDownload(username, password);
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(RegisterLoginResult result)
    {
        // Update your UI here based on result of download.
        if (null != result)
        {
            if (result.successFlag)
            {
                SettingsManager sM = SettingsManager.getInstance();
                //STORE RLI as a global??
                sM.createSetting(
                        "loginInfo",
                        editUserName.getText().toString().isEmpty() ? "empty" : editUserName.getText().toString(),
                        editPassword.getText().toString().isEmpty() ? "empty" : editPassword.getText().toString(),
                        null
                );
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

    @Override
    public NetworkInfo getActiveNetworkInfo()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void finishDownloading()
    {
        mDownloading = false;
        if (mRegisterLoginFragment != null)
        {
            mRegisterLoginFragment.cancelDownload();
        }
    }


/*    public void sendSOAPRequest()
    {
        new SendMsg().execute(FirebaseInstanceId.getInstance().getToken(),
                editUserName.getText().toString().isEmpty() ? "empty" : editUserName.getText().toString(),
                editPassword.getText().toString().isEmpty() ? "empty" : editPassword.getText().toString());
    }*/

/*    public class SendMsg extends AsyncTask<String, Void, RegisterLoginResult>
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
    }*/
}