package org.simple.jnp.ch05;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolTester {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProtocolTester.class);

	public static void main(String[] args) {

		// hypertext transfer protocol
		testProtocol("http://www.adc.org");
		// secure http
		testProtocol("https://www.amazon.com/exec/obidos/order2/");
		// file transfer protocol
		testProtocol("ftp://ibiblio.org/pub/languages/java/javafaq/");
		// Simple Mail Transfer Protocol
		testProtocol("mailto:elharo@ibiblio.org");
		// telnet
		testProtocol("telnet://dibner.poly.edu/");
		// local file access
		testProtocol("file:///etc/passwd");
		// gopher
		testProtocol("gopher://gopher.anc.org.za/");
		// Lightweight Directory Access Protocol
		testProtocol("ldap://ldap.itd.umich.edu/o=University%20of%20Michigan,c=US?postalAddress");
		// JAR
		testProtocol("jar:http://cafeaulait.org/books/javaio/ioexamples/javaio.jar!"
				+ "/com/macfaq/io/StreamCopier.class");
		// NFS, Network File System
		testProtocol("nfs://utopia.poly.edu/usr/tmp/");
		// a custom protocol for JDBC
		testProtocol("jdbc:mysql://luna.ibiblio.org:3306/NEWS");
		// rmi, a custom protocol for remote method invocation
		testProtocol("rmi://ibiblio.org/RenderEngine");
		// custom protocols for HotJava
		testProtocol("doc:/UsersGuide/release.html");
		testProtocol("netdoc:/UsersGuide/release.html");

		testProtocol("systemresource://www.adc.org/+/index.html");
		testProtocol("verbatim:http://www.adc.org/");
	}

	private static void testProtocol(String url) {
		try {
			URL u = new URL(url);
			LOGGER.info("{} is supported.", u.getProtocol());
		} catch (MalformedURLException e) {
			String protocol = url.substring(0, url.indexOf(':'));
			LOGGER.info("{} is not supported.", protocol);
		}
	}
}
