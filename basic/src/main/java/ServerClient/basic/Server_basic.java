package ServerClient.basic;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.HashMap;
import java.net.URL;
import java.io.IOException;
import java.net.*;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;


/**
 * Hello world!
 *
 */
public class Server_basic 
{
	  private static Socket socket;
	  
	  public static void main(String[] args)
	  {
    	     try
    	     {
    		//int portNumber = Integer.parseInt(args[0]);
    		int inputPortNumber = 8080;
    		int translatePortNumber = 8081;
    		int recaserPortNumber = 8082;

    	        /* listen to a port(8080) to get a plain text */	
    	        ServerSocket serverSocket = new ServerSocket(inputPortNumber);
    	        System.out.println("Server Started and listening to the port "+String.valueOf(inputPortNumber));

    	        //Server is running always. This is done using this while(true) loop
    		while(true)
    	        {
    	           //Reading the message from the client
    			   socket = serverSocket.accept();
    	           InputStream is = socket.getInputStream();
    	           InputStreamReader isr = new InputStreamReader(is);
    	           BufferedReader in = new BufferedReader(isr);

    	           OutputStream os = socket.getOutputStream();
    	           OutputStreamWriter osw = new OutputStreamWriter(os);
    	           BufferedWriter out = new BufferedWriter(osw);

    		   String inputLine;
    		   while ((inputLine = in.readLine()) != null) {
//    		   String inputLine="This is a test.";
    		      System.out.println("Text received from client is "+inputLine);
    		      /*run tokenizer in perl on in text */
    		      String textTokenized = EnglishTokenizer.run(inputLine);
    		      /* run translation */
    		      String translation = SendToServer.run(textTokenized, translatePortNumber);
    		      /* run recasing */
 //   		      String finalTranslation = SendToServer.run(translation, recaserPortNumber);
//    	              System.out.println("translation is "+finalTranslation);	

//    	              String sent = "这是 the first 测 试 。";
//    	              String standardout = "这是the first测试。";
    	              String outline = SplitZHJACharacters.splitStringCharacters(translation);
    	              System.out.print(outline.trim().replaceAll("  +", ""));
    	              
    	              //Sending the response back to the client.
  	    	      out.write(translation);
    		      out.flush();
    		    }

    	        } //while
    	     } // try
    	     catch (Exception e)
    	     {
    	        e.printStackTrace();
    	     }
    	     finally
    	     {
    	        try
    	        {
    	        	socket.close();
    	        }
    	        catch(Exception e){}
    	      }

    	  } // main

    }
