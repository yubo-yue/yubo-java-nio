package org.simple.jnp.ch04;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OReillyByName {
	private static final Logger LOGGER = LoggerFactory.getLogger(OReillyByName.class);

	public static void main(String[] args) throws UnknownHostException {
		
		OReillyByName.getInetAddressByHostname();
		OReillyByName.getInetAddressOfLocal();
	}
	
	public static void getInetAddressByHostname() throws UnknownHostException
	{
		String hostName = "www.oreilly.com";
		InetAddress address = InetAddress.getByName(hostName);
		LOGGER.info("{}", address);
		InetAddress[] allAddresses = InetAddress.getAllByName(hostName);
		for (InetAddress ia : allAddresses) {
			LOGGER.info("{}", ia);
		}
	}
	
	public static void getInetAddressOfLocal()
	{
		try {
			InetAddress address = InetAddress.getLocalHost();
			LOGGER.info("{}", address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
