package c.c;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import handler.beans.KRegisterLoginInfo;
import handler.beans.RegisterLoginResult;
import handler.beans.UserListResult;
import handler.SoapHandler;
import handler.beans.bUser;
import handler.beans.input.RegisterLoginInfo;

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
            RegisterLoginInfo rli = new RegisterLoginInfo(params, SoapHandler.USERLIST);
            SoapObject j = SoapHandler.MakeCall(SoapHandler.URL, rli.getSoapEnvelope(), SoapHandler.SOAP_ACTION);
            if (null != j)
                return new UserListResult(j);
            return null;
        }

        protected void onPostExecute(UserListResult result)
        {
            if (null != result)
            {
                if (result.successFlag)
                {
                    for (int i = 0; i < result.userlist.size(); i++)
                    {
                        bUser bU = result.userlist.get(i);
                        list.add("userid: " + bU.userid + " username: " + bU.username + " online: " + bU.online + " gamesActive: " + bU.gameActive);
                    }
                    listView.setAdapter(new ArrayAdapter(UserList.this, android.R.layout.simple_list_item_1, list));
                }
            }
        }
    }
}
