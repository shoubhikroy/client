package handler;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapHandler
{
    public static final String SOAP_ACTION = "http://71.190.149.193:9999/ws/rpc";
    public static String URL = "http://71.190.149.193:9999/ws/rpc?wsdl";
    //public static final String SOAP_ACTION = "http://egfyz29u.xyz:9999/ws/rpc";
    //public static String URL = "http://egfyz29u.xyz:9999/ws/rpc?wsdl";
    public static final String NAMESPACE = "http://jaxws.server/";
    public static final String REGISTER = "registerLogin";
    public static final String USERLIST = "getUserList";

    public static SoapObject MakeCall(String URL, SoapSerializationEnvelope envelope, String SOAP_ACTION)
    {
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try
        {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            Object retObj = envelope.bodyIn;
            if (retObj instanceof SoapFault)
            {
                SoapFault fault = (SoapFault) retObj;
                Exception ex = new Exception(fault.faultstring);
            } else
            {
                SoapObject result = (SoapObject) retObj;
                if (result.getPropertyCount() > 0)
                {
                    Object obj = result.getProperty(0);
                    SoapObject j = (SoapObject) obj;
                    return j;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
