package handler.beans;

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

public class RegisterLoginResult implements KvmSerializable
{
    
    public String key;
    public boolean successFlag;
    public String message;

    public RegisterLoginResult()
    {
    }
    
    public RegisterLoginResult(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("key"))
        {
            Object obj = soapObject.getProperty("key");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class))
            {
                SoapPrimitive j = (SoapPrimitive) obj;
                key = j.toString();
            } else if (obj != null && obj instanceof String)
            {
                key = (String) obj;
            }
        }
        if (soapObject.hasProperty("successFlag"))
        {
            Object obj = soapObject.getProperty("successFlag");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class))
            {
                SoapPrimitive j = (SoapPrimitive) obj;
                successFlag = Boolean.parseBoolean(j.toString());
            } else if (obj != null && obj instanceof Boolean)
            {
                successFlag = (Boolean) obj;
            }
        }
        if (soapObject.hasProperty("message"))
        {
            Object obj = soapObject.getProperty("message");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class))
            {
                SoapPrimitive j = (SoapPrimitive) obj;
                message = j.toString();
            } else if (obj != null && obj instanceof String)
            {
                message = (String) obj;
            }
        }
    }
    @Override
    public Object getProperty(int arg0)
    {
        switch (arg0)
        {
            case 0:
                return key;
            case 1:
                return successFlag;
            case 2:
                return message;
        }
        return null;
    }

    @Override
    public int getPropertyCount()
    {
        return 3;
    }

    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info)
    {
        switch (index)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "key";
                break;
            case 1:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "successFlag";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "message";
                break;
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1)
    {
    }

}