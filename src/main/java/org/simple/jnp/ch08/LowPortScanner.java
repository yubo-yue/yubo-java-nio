package org.simple.jnp.ch08;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LowPortScanner {

	private static final Logger LOGGER = LoggerFactory.getLogger(LowPortScanner.class);
	
	public static void main(String[] args){
		String host = args.length > 0 ? args[1] : "localhost";
		
		for (int i = 1; i < 1024; ++i) {
			try (Socket socket = new Socket(host, i)) {
				LOGGER.info("There is a server on port {} of host {}", i, host);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				break;
			} catch (IOException ignore) {
			}
		}
	}
}
