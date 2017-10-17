package ServerClient.basic;


import java.io.BufferedReader;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.io.UnsupportedEncodingException;


public class SplitZHJACharacters {


    private static final String FILE_ENCODING = "utf-8";


    public File fileSplitCharacters(File file) throws IOException {

        /* Create a tmp file for segmented sentences */


        File outfile = File.createTempFile("segc-", "");

        FileOutputStream fos = new FileOutputStream(outfile);

        OutputStreamWriter osw = new OutputStreamWriter(fos,FILE_ENCODING);

        FileInputStream tmpfis = new FileInputStream(file);

        BufferedReader br = new BufferedReader(new InputStreamReader(tmpfis,FILE_ENCODING));
             
        String line; 

        while ((line = br.readLine()) != null) {

                String outline = StripNonValidXMLCharacters.strip(splitStringCharacters(line));

                osw.write(outline);

        }

        

        return outfile;

    }


    public static String splitStringCharacters(String line) throws UnsupportedEncodingException {

        String outline = "";


        final byte[] utf8Bytes = line.getBytes(FILE_ENCODING);

        int len = utf8Bytes.length;


        int i = 0;

        while ((i < len) && (utf8Bytes[i] != '\0')) {

            // Arrays.fill(word, (byte) 0);

            if ((utf8Bytes[i] & 0xF0 ^ 0xF0) == 0) { // this word ocupies 4

                                                     // bytes

                byte[] word = new byte[4];

                if (i + 3 < len) {

                    System.arraycopy(utf8Bytes, i, word, 0, 4);

                    i = i + 4;

                } else {

                    System.arraycopy(utf8Bytes, i, word, 0, 4);

                    i = i + 4; /* i+4 > len */

                }

                String s = new String(word, FILE_ENCODING);

                outline = outline + " " + s + " ";

            } else if ((utf8Bytes[i] & 0xE0 ^ 0xE0) == 0) { // this word ocupies

                                                            // 3 bytes

                byte[] word = new byte[3];

                if (i + 2 < len) {

                    System.arraycopy(utf8Bytes, i, word, 0, 3);

                    i = i + 3;

                } else {

                    System.arraycopy(utf8Bytes, i, word, 0, 3);

                    i = i + 3; /* i+3 > len */

                }

                String s = new String(word,FILE_ENCODING);

                outline = outline + " " + s + " ";

            } else if ((utf8Bytes[i] & 0xC0 ^ 0xC0) == 0) { // this word ocupies

                                                            // 2 bytes

                byte[] word = new byte[2];

                if (i + 1 < len) {

                    System.arraycopy(utf8Bytes, i, word, 0, 2);

                    i = i + 2;

                } else {

                    System.arraycopy(utf8Bytes, i, word, 0, 2);

                    i = i + 2; /* i+2 > len */

                }

                String s = new String(word,FILE_ENCODING);

                outline = outline + " " + s + " ";

            } else {

                byte[] word = new byte[1];

                System.arraycopy(utf8Bytes, i, word, 0, 1);

                i = i + 1;

                String s = new String(word,FILE_ENCODING);

                outline = outline + s;

            }


        }

        outline = outline + "\n";

        return outline;

    }

}