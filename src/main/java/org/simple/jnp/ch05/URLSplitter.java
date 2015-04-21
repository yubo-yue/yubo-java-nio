package org.simple.jnp.ch05;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLSplitter {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLSplitter.class);
	
	public static void main(String[] args) {
		for (int i = 0; i < args.length; ++i) {
			try {
				URL u = new URL(args[i]);
				LOGGER.info("The url is {}", u);
				LOGGER.info("The scheme is {}", u.getProtocol());
				LOGGER.info("The user info is {}", u.getUserInfo());
				
				String host = u.getHost();
				if (null !=  host) {
					int atSign = host.indexOf('@');
					if (atSign != -1) host = host.substring(atSign + 1);
					LOGGER.info("The host is {}", host );
				} else {
					LOGGER.info("The host is null");
				}
				
				LOGGER.info("The port is {}", u.getPort());
				LOGGER.info("The path is {}", u.getPath());;
				LOGGER.info("THE ref is {}", u.getRef());
				LOGGER.info("The query string is {}", u.getQuery());;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
}
