package ServerClient.basic;

import java.io.*;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.*;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class SendToServer {
  int port;
  static String in=null;
static String out=null;
  static String host = "http://localhost:";

public int getPort() {return port;}
public String getIn() {return in;}
public void setHost(String h) {host=h;}

public static String run(String text, int port) throws Exception {
	in = text;
/* wrap text in xml and send it to translator server(port)*/
	// Create an instance of XmlRpcClient
	XmlRpcClientConfigImpl translateConfig = new XmlRpcClientConfigImpl();
	translateConfig.setServerURL(new URL(host+String.valueOf(port)+"/RPC2"));
	XmlRpcClient translateClient = new XmlRpcClient();
	translateClient.setConfig(translateConfig);

	//The XML-RPC data type used by server is <struct>. In Java, this data type can be represented using HashMap.
	HashMap<String,String> mosesParams = new HashMap<String,String>();
	//String textToTranslate = new String("some text to translate .");
	mosesParams.put("text", in);
	mosesParams.put("align", "true");
	mosesParams.put("report-all-factors", "true");

	// The XmlRpcClient.execute method doesn't accept Hashmap (pParams). It's either Object[] or List. 
	Object[] params = new Object[] { null };
	params[0] = mosesParams;

	// Invoke the remote method "translate". The result is an Object, convert it to a HashMap.
	HashMap translateResult = (HashMap)translateClient.execute("translate", params);

        // get the returned translations
	out = (String)translateResult.get("text");

	return out;
}

}


