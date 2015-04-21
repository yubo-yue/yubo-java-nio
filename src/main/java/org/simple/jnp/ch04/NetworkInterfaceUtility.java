package org.simple.jnp.ch04;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkInterfaceUtility {

	private final static Logger LOGGER = LoggerFactory.getLogger(NetworkInterfaceUtility.class);
	
	public static void main(String[] args) {
		NetworkInterfaceUtility.getNetworkInterface();
		NetworkInterfaceUtility.getNetworkInterfaces();
		getNetworkInterfaceInfo();
	}
	
	public static void getNetworkInterface()
	{
		try {
			NetworkInterface ni = NetworkInterface.getByName("eth0");
			if (null != ni) LOGGER.info("{}", ni);
			
			NetworkInterface local = NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1"));
			if (null != local) LOGGER.info("{}", local);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static void getNetworkInterfaces()
	{
		try {
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()) {
				NetworkInterface ni = nis.nextElement();
				LOGGER.info("{}", ni);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void getNetworkInterfaceInfo()
	{
		try {
			NetworkInterface em1 = NetworkInterface.getByName("em1");
			if (null != em1) 
			{
				Enumeration<InetAddress> addresses = em1.getInetAddresses();
				while(addresses.hasMoreElements()) {
					LOGGER.info("{}", addresses.nextElement());
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}
