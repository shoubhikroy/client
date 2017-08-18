package c.c.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.firebase.iid.FirebaseInstanceId;

import c.c.Tasks.GetUserListTask;
import handler.SoapHandler;
import model.beans.input.RegisterLoginInfo;
import model.templates.SoapCallBack;

public class GetUserListFragment extends Fragment
{
    private GetUserListTask mDownloadTask;

    public static GetUserListFragment getInstance(FragmentManager fragmentManager)
    {
        GetUserListFragment getUserListFragment = new GetUserListFragment();
        fragmentManager.beginTransaction().add(getUserListFragment, TAG).commit();
        return getUserListFragment;
    }

    public static final String TAG = "GetUserListFragment";
    protected SoapCallBack mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mCallback = (SoapCallBack) context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onDestroy()
    {
        cancelDownload();
        super.onDestroy();
    }

    public void startDownload(String mUsername, String mPassword, String mMethod)
    {
        cancelDownload();
        mDownloadTask = new GetUserListTask(mCallback);
        String params[] = {
                FirebaseInstanceId.getInstance().getToken(),
                mUsername,
                mPassword
        };
        RegisterLoginInfo rli = new RegisterLoginInfo(params, mMethod);
        mDownloadTask.execute(rli);
    }

    public void cancelDownload()
    {
        if (mDownloadTask != null)
        {
            mDownloadTask.cancel(true);
        }
    }
}