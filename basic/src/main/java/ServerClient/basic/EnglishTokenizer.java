package ServerClient.basic;

import java.io.*;
import java.util.HashMap;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.*;

public class EnglishTokenizer {

private  String in;
private static  String out;
private static boolean trueCase = false;

public static String run(String inline) throws IOException {
	 OutputStream stdin = null;
	 InputStream stderr = null;
	 InputStream stdout = null;
	
	Process process = Runtime.getRuntime().exec("perl /home/yuqi/workspace/mosesdecoder/scripts/tokenizer/tokenizer.perl -l en -q -threads 4 -no-escape");
    stdin = process.getOutputStream();
    stderr = process.getErrorStream();
    stdout = process.getInputStream();
    
    stdin.write(inline.getBytes() );
    stdin.flush();
    stdin.close();
    
	try {
		process.waitFor();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	try {
		out = reader.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	// Do lowercasing or not
	if (trueCase) return out;
	else return out.toLowerCase();
}

/*  
public static String run(String inline) throws IOException {
// run tokenizer in perl on in text 
	in = inline;
// Run English tokenization on the input.
//	Process process = null; 			
    ProcessBuilder builder = new ProcessBuilder("bash", "-i");
    builder.redirectErrorStream(true); // so we can ignore the error stream
    Process process = builder.start();
	OutputStreamWriter procIn =  new OutputStreamWriter(process.getOutputStream());
	try {
		procIn.write(in);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		process = Runtime.getRuntime().exec("perl /home/yuqi/workspace/mosesdecoder/scripts/tokenizer/tokenizer.perl -l en -q -threads 4 -no-escape");
//	      stdin = process.getOutputStream();
//	      stdout = process.getInputStream();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//procIn = process.getOutputStream();
    //procOut = process.getInputStream();	
	
	try {			
		process.waitFor();
	}
	catch (InterruptedException e) {
		e.printStackTrace();
	}
	try {
		procIn.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	try {
		out = reader.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return out;
}
*/
public String getIn() {return in;}
public String getOut() {return out;}
public boolean getCase() {return trueCase;}
public void setCase(boolean truecase) {
	if (truecase) trueCase = true;
	else trueCase = false;
}


}
