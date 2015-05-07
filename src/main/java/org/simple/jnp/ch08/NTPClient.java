package org.simple.jnp.ch08;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

public class NTPClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(NTPClient.class);
	private static final String NTP_SERVER = "time.nist.gov";
	private static final int NTP_PORT = 13;
	
	public static void main(String[] args) {
		
		try (Socket socket = new Socket(NTP_SERVER, NTP_PORT)) {
			socket.setSoTimeout(15000); //15 seconds
			InputStream in = socket.getInputStream();
			
			StringBuilder time = new StringBuilder();
			
			InputStreamReader reader = new InputStreamReader(in, Charsets.US_ASCII);
			
			for (int c = reader.read(); c != -1; c = reader.read()){
				time.append((char) c);
			}
			
			LOGGER.info("Time return : {}", time.toString());
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
