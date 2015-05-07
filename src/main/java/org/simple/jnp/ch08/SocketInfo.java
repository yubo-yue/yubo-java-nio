package org.simple.jnp.ch08;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketInfo {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketInfo.class);
	
	public static void main(String[] args) {
		for (String host : args) {
			try {
				Socket theSocket = new Socket(host, 80);
				LOGGER.info("connect to {} on port {} from port {} of {}", theSocket.getInetAddress(), theSocket.getPort(), theSocket.getLocalPort(), theSocket.getLocalAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
