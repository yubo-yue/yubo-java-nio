package org.simple.jnp.ch05;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLOperations.class);
	
	public static void main(String[] args) {
		//readFromURL();
		String[] inputs = {"http://www.sina.com.cn"};
		viewSource(inputs);
	}
	
	public static void readFromURL()
	{
		try {
			URL url = new URL("http://www.sina.com.cn");
			
			try (InputStream in = url.openStream()) {
				int c;
				while ((c = in.read()) != -1) LOGGER.info("{}", (char)c);
			}
		} catch (IOException e) {
			LOGGER.error("error", null, e);
		}
	}
	
	public static void viewSource(String[] args)
	{
		if (args.length > 0) {
			InputStream in = null;
			
			try {
				URL u = new URL(args[0]);
				in = u.openStream();
				in = new BufferedInputStream(in);
				Reader r = new InputStreamReader(in);
				int c ;
				while((c = r.read()) != -1) {
					System.out.print( (char)c);
				}
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
