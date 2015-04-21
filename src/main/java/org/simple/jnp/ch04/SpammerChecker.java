package org.simple.jnp.ch04;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpammerChecker {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpammerChecker.class);
	public static final String BACKHOLE = "sbl.spamhaus.org";
	
	public static void main(String[] args)
	{
		for (String arg : args) {
			if (isSpammer(arg))
			LOGGER.info("{} is a known spammer", arg);
			else
				LOGGER.info("{} appears legitimate", arg);
		}
	}
	
	private static boolean isSpammer(String hostname)
	{
		try {
			InetAddress address = InetAddress.getByName(hostname);
			byte[] quad = address.getAddress();
			String query = BACKHOLE;
			
			for (byte octet : quad) {
				int unsignedByte = octet < 0 ? octet + 256 : octet;
				query = unsignedByte + "." + query;
			}
			InetAddress.getAllByName(query);
			return true;
		} catch (UnknownHostException e) {
			return false;
		}
	}
	
}
