package de.kai_morich.simple_bluetooth_terminal;


import javax.sql.DataSource;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.string;

public class WebService<DataTable> {
    //Namespace of the Webservice - can be found in WSDL
    private static String NAMESPACE = "http://tempuri.org/";
    //Webservice URL - WSDL File location

    private static final String URL = "http://192.168.1.3:32768/Service.asmx";//Make sure you changed IP address
    //SOAP Action URI again Namespace + Web method name
    private static String SOAP_ACTION = "http://tempuri.org/";

    public static String register(String p1,String p2,String p3,String p4,String p5,String p6,String p7,String p8,String webMethName) {
        String registerStatus ="";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();
        PropertyInfo pat = new PropertyInfo();
        PropertyInfo em = new PropertyInfo();
        PropertyInfo ht = new PropertyInfo();
        PropertyInfo s = new PropertyInfo();
        PropertyInfo vnum = new PropertyInfo();
        PropertyInfo cardNum = new PropertyInfo();

        unamePI.setName("p1");
        unamePI.setValue(p1);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        passPI.setName("p2");
        passPI.setValue(p2);
        passPI.setType(String.class);		//Add the property to request object
        request.addProperty(passPI);

        pat.setName("p3");		//Set dataType
        pat.setValue(p3);		//Set dataType
        pat.setType(String.class);		//Add the property to request object
        request.addProperty(pat);

        em.setName("p4");		// Set Value
        em.setValue(p4);		// Set dataType
        em.setType(String.class);		// Add the property to request object
        request.addProperty(em);

        ht.setName("p5");		// Set Value
        ht.setValue(p5);		// Set dataType
        ht.setType(String.class);		// Add the property to request object
        request.addProperty(ht);

        s.setName("p6");		// Set Value
        s.setValue(p6);		// Set dataType
        s.setType(String.class);		// Add the property to request object
        request.addProperty(s);

        vnum.setName("p7");		// Set Value
        vnum.setValue(p7);		// Set dataType
        vnum.setType(String.class);		// Add the property to request object
        request.addProperty(vnum);

        cardNum.setName("p8");		// Set Value
        cardNum.setValue(p8);		// Set dataType
        cardNum.setType(String.class);		// Add the property to request object
        request.addProperty(cardNum);


        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to  boolean variable variable
            registerStatus = response.toString();

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'

            e.printStackTrace();
        }
        //Return booleam to calling object
        return registerStatus;
    }

    public static String logincheck(String username,String webMethName) {
        String loginStatus ="";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();


        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(username);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to  boolean variable variable
            loginStatus = response.toString();

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'

            e.printStackTrace();
        }
        //Return booleam to calling object
        return loginStatus;
    }

    public static String logincheck(String username,String password,String webMethName) {
        String loginStatus ="";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();


        // Set Username
        unamePI.setName("username");
        // Set Value
        unamePI.setValue(username);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("password");
        //Set dataType
        passPI.setValue(password);
        //Set dataType
        passPI.setType(String.class);
        //Add the property to request object
        request.addProperty(passPI);



        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to  boolean variable variable
            loginStatus = response.toString();

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'

            e.printStackTrace();
        }
        //Return booleam to calling object
        return loginStatus;
    }

    public static String CarInoutStatus(String userid,String cardno,String webMethName) {
        String CarInoutStatus ="";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        // Property which holds input parameters
        PropertyInfo unamePI = new PropertyInfo();
        PropertyInfo passPI = new PropertyInfo();


        // Set Username
        unamePI.setName("p1");
        // Set Value
        unamePI.setValue(userid);
        // Set dataType
        unamePI.setType(String.class);
        // Add the property to request object
        request.addProperty(unamePI);
        //Set Password
        passPI.setName("p2");
        //Set dataType
        passPI.setValue(cardno);
        //Set dataType
        passPI.setType(String.class);
        //Add the property to request object
        request.addProperty(passPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to  boolean variable variable
            CarInoutStatus = response.toString();

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'

            e.printStackTrace();
        }
        //Return booleam to calling object
        return CarInoutStatus;
    }
    public static String getHistory(String p1,String p2,String webMethName) {
        String loginStatus ="";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo param1 = new PropertyInfo();
        PropertyInfo param2 = new PropertyInfo();


        // Set Username
        param1.setName("p1");
        // Set Value
        param1.setValue(p1);
        // Set dataType
        param1.setType(String.class);
        // Add the property to request object
        request.addProperty(param1);

        // Set Username
        param2.setName("p2");
        // Set Value
        param2.setValue(p2);
        // Set dataType
        param2.setType(String.class);
        // Add the property to request object
        request.addProperty(param2);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to  boolean variable variable
            loginStatus = response.toString();

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'

            e.printStackTrace();
        }
        //Return booleam to calling object
        return loginStatus;
    }

    public static String getuser(String username,String webMethName) {
        String loginStatus ="";
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo user = new PropertyInfo();


        // Set Username
        user.setName("username");
        // Set Value
        user.setValue(username);
        // Set dataType
        user.setType(String.class);
        // Add the property to request object
        request.addProperty(user);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to  boolean variable variable
            loginStatus = response.toString();

        } catch (Exception e) {
            //Assign Error Status true in static variable 'errored'

            e.printStackTrace();
        }
        //Return booleam to calling object
        return loginStatus;
    }



    public static String updateLocation(String p1,String p2,String p3,String p4,String p5,String p6,String webMethName)
    {
        String loginStatus ="";
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo pI1=new PropertyInfo();
        PropertyInfo pI2=new PropertyInfo();
        PropertyInfo pI3=new PropertyInfo();
        PropertyInfo pI4=new PropertyInfo();
        PropertyInfo pI5=new PropertyInfo();
        PropertyInfo pI6=new PropertyInfo();

        pI1.setName("p1");
        pI1.setValue(p1);
        pI1.setType(String.class);
        request.addProperty(pI1);

        pI2.setName("p2");
        pI2.setValue(p2);
        pI2.setType(String.class);
        request.addProperty(pI2);

        pI3.setName("p3");
        pI3.setValue(p3);
        pI3.setType(String.class);
        request.addProperty(pI3);

        pI4.setName("p4");
        pI4.setValue(p4);
        pI4.setType(String.class);
        request.addProperty(pI4);

        pI5.setName("p5");
        pI5.setValue(p5);
        pI5.setType(String.class);
        request.addProperty(pI5);

        pI6.setName("p6");
        pI6.setValue(p6);
        pI6.setType(String.class);
        request.addProperty(pI6);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try
        {
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            loginStatus = response.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return loginStatus;
    }




    //get notification
    public static String getnot(String name,String webMethName)
    {
        String loginStatus ="";
        SoapObject request = new SoapObject(NAMESPACE, webMethName);
        PropertyInfo p1=new PropertyInfo();

        p1.setName("vecno");
        p1.setValue(name);
        p1.setType(String.class);
        request.addProperty(p1);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try
        {
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            loginStatus = response.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return loginStatus;
    }
}