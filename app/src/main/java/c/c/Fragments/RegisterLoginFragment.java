package c.c.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.iid.FirebaseInstanceId;

import c.c.Tasks.RegisterLoginTask;
import handler.SoapHandler;
import model.beans.input.RegisterLoginInfo;
import model.templates.SoapCallBack;

public class RegisterLoginFragment extends Fragment
{
    private RegisterLoginTask mDownloadTask;

    public static RegisterLoginFragment getInstance(FragmentManager fragmentManager)
    {
        RegisterLoginFragment registerLoginFragment = new RegisterLoginFragment();
        fragmentManager.beginTransaction().add(registerLoginFragment, TAG).commit();
        return registerLoginFragment;
    }

    public static final String TAG = "RegisterLoginFragment";

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

    public void startDownload(String mUsername, String mPassword)
    {
        cancelDownload();
        mDownloadTask = new c.c.Tasks.RegisterLoginTask(mCallback);
        String params[] = {
                FirebaseInstanceId.getInstance().getToken(),
                mUsername,
                mPassword
        };
        RegisterLoginInfo rli = new RegisterLoginInfo(params, SoapHandler.REGISTER);
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