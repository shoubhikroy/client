package c.c.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

import c.c.Fragments.GetUserListFragment;
import c.c.Fragments.GetUserListFragment;
import c.c.R;
import model.beans.output.RegisterLoginResult;
import model.beans.output.UserListResult;
import handler.SoapHandler;
import model.beans.bUser;
import model.beans.input.RegisterLoginInfo;
import model.objectManagers.SettingsManager;
import model.objects.Setting;
import model.templates.SoapCallBack;

public class UserList extends FragmentActivity implements SoapCallBack<UserListResult>
{
    private static Context context;
    ArrayList<String> list;
    ListView listView;

    public static Context getAppContext()
    {
        return UserList.context;
    }

    private GetUserListFragment mGetUserListFragment;
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        mGetUserListFragment = GetUserListFragment.getInstance(getSupportFragmentManager());
        listView = (ListView) findViewById(R.id.ListView);

        UserList.context = getApplicationContext();

        if (getIntent().getExtras() != null)
        {
            for (String key : getIntent().getExtras().keySet())
            {
                Object value = getIntent().getExtras().get(key);
            }
        }

        list = new ArrayList<>();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        sendSOAPRequest();
    }

    public void sendSOAPRequest()
    {
        startDownload();
    }

    private void startDownload()
    {
        if (!mDownloading && mGetUserListFragment != null)
        {
            SettingsManager sM = SettingsManager.getInstance();
            Setting loginInfo = sM.getSettingFromKey("loginInfo");
            mGetUserListFragment.startDownload(loginInfo.getValue(), loginInfo.getValue2(), SoapHandler.USERLIST);
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(UserListResult result)
    {
        if (null != result)
        {
            if (result.successFlag)
            {
                for (int i = 0; i < result.userlist.size(); i++)
                {
                    bUser bU = result.userlist.get(i);
                    list.add("userid: " + bU.userid + " |username: " + bU.username + " |online: " + bU.online + " |gamesActive: " + bU.gameActive);
                }
                listView.setAdapter(new ArrayAdapter(UserList.this, android.R.layout.simple_list_item_1, list));
            }
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
        if (mGetUserListFragment != null)
        {
            mGetUserListFragment.cancelDownload();
        }
    }
}
