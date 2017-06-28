package handler.RemoteCallsImplService;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.6
//
// Date Of Creation: 6/28/2017 10:12:19 PM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

public class bUser implements KvmSerializable
{

    public String username;
    public int userid;
    public boolean online;
    public boolean gameActive;

    public bUser()
    {
    }

    public bUser(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("username"))
        {
            Object obj = soapObject.getProperty("username");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class))
            {
                SoapPrimitive j = (SoapPrimitive) obj;
                username = j.toString();
            } else if (obj != null && obj instanceof String)
            {
                username = (String) obj;
            }
        }
        if (soapObject.hasProperty("userid"))
        {
            Object obj = soapObject.getProperty("userid");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class))
            {
                SoapPrimitive j = (SoapPrimitive) obj;
                userid = Integer.parseInt(j.toString());
            } else if (obj != null && obj instanceof Number)
            {
                userid = (Integer) obj;
            }
        }
        if (soapObject.hasProperty("online"))
        {
            Object obj = soapObject.getProperty("online");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class))
            {
                SoapPrimitive j = (SoapPrimitive) obj;
                online = Boolean.parseBoolean(j.toString());
            } else if (obj != null && obj instanceof Boolean)
            {
                online = (Boolean) obj;
            }
        }
        if (soapObject.hasProperty("gameActive"))
        {
            Object obj = soapObject.getProperty("gameActive");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class))
            {
                SoapPrimitive j = (SoapPrimitive) obj;
                gameActive = Boolean.parseBoolean(j.toString());
            } else if (obj != null && obj instanceof Boolean)
            {
                gameActive = (Boolean) obj;
            }
        }
    }

    @Override
    public Object getProperty(int arg0)
    {
        switch (arg0)
        {
            case 0:
                return username;
            case 1:
                return userid;
            case 2:
                return online;
            case 3:
                return gameActive;
        }
        return null;
    }

    @Override
    public int getPropertyCount()
    {
        return 4;
    }

    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        switch (index)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "username";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "userid";
                break;
            case 2:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "online";
                break;
            case 3:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "gameActive";
                break;
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1)
    {
    }

}
