package handler;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapHandler
{
    public static final String SOAP_ACTION = "http://71.190.149.193:9999/ws/rpc";
    public static String URL = "http://71.190.149.193:9999/ws/rpc?wsdl";
    public static final String NAMESPACE = "http://jaxws.server/";
    public static final String REGISTER = "registerLogin";
    public static final String USERLIST = "getUserList";

    public static SoapObject MakeCall(String URL, SoapSerializationEnvelope Envelope, String SOAP_ACTION)
    {
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try
        {
            androidHttpTransport.call(SOAP_ACTION, Envelope);
            SoapObject response = (SoapObject) Envelope.getResponse();
            return response;
        } catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;
    }
}
