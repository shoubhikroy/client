package model.beans.input;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import handler.SoapHandler;
import model.beans.KRegisterLoginInfo;

public class RegisterLoginInfo
{
    SoapSerializationEnvelope soapEnvelope;

    public SoapSerializationEnvelope getSoapEnvelope()
    {
        return soapEnvelope;
    }

    public void setSoapEnvelope(SoapSerializationEnvelope soapEnvelope)
    {
        this.soapEnvelope = soapEnvelope;

    }

    public RegisterLoginInfo(String[] params, String method)
    {
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

        KRegisterLoginInfo arg0 = new KRegisterLoginInfo(request);

        soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        soapEnvelope.implicitTypes = true;
        SoapObject soapReq = new SoapObject(SoapHandler.NAMESPACE, method);
        soapEnvelope.addMapping(SoapHandler.NAMESPACE, "arg0", new KRegisterLoginInfo().getClass());
        soapReq.addProperty("arg0", arg0);

        soapEnvelope.setOutputSoapObject(soapReq);
    }
}
