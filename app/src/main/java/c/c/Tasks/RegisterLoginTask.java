package c.c.Tasks;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.ksoap2.serialization.SoapObject;

import handler.SoapHandler;
import model.beans.input.RegisterLoginInfo;
import model.beans.output.RegisterLoginResult;
import model.templates.SoapCallBack;

public class RegisterLoginTask extends AsyncTask<RegisterLoginInfo, Void, RegisterLoginResult>
{

    private SoapCallBack<RegisterLoginResult> mCallback;

    public RegisterLoginTask(SoapCallBack<RegisterLoginResult> callback)
    {
        setCallback(callback);
    }

    void setCallback(SoapCallBack<RegisterLoginResult> callback)
    {
        mCallback = callback;
    }

    @Override
    protected void onPreExecute()
    {
        if (mCallback != null)
        {
            NetworkInfo networkInfo = mCallback.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() ||
                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE))
            {
                // If no connectivity, cancel task and update Callback with null data.
                mCallback.updateFromDownload(null);
                cancel(true);
            }
        }
    }

    @Override
    protected RegisterLoginResult doInBackground(RegisterLoginInfo... params)
    {

        if (!isCancelled() && null != params)
        {
            RegisterLoginInfo rli = params[0];
            if (null != rli)
            {
                SoapObject j = SoapHandler.MakeCall(SoapHandler.URL, rli.getSoapEnvelope(), SoapHandler.SOAP_ACTION);
                if (null != j)
                    return new RegisterLoginResult(j);
            }
        }
        return null;
    }

    protected void onPostExecute(RegisterLoginResult result)
    {
        if (result != null && mCallback != null)
        {
            mCallback.updateFromDownload(result);
            mCallback.finishDownloading();
        }
    }
}