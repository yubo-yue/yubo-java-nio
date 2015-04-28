package org.simple.jnp.ch07;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderViewer {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HeaderViewer.class);

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			try {
				URL u = new URL(args[0]);
				URLConnection uc = u.openConnection();
				LOGGER.info("content-type : {}", uc.getContentType());
				if (null != uc.getContentEncoding()) {
					LOGGER.info("content-encoding : {}",
							uc.getContentEncoding());
				}

				if (uc.getDate() != 0) {
					LOGGER.info("Date: " + new Date(uc.getDate()));
				}
				if (uc.getLastModified() != 0) {
					LOGGER.info("Last modified: "
							+ new Date(uc.getLastModified()));
				}
				if (uc.getExpiration() != 0) {
					LOGGER.info("Expiration date: "
							+ new Date(uc.getExpiration()));
				}
				if (uc.getContentLength() != -1) {
					LOGGER.info("Content-length: "
							+ uc.getContentLength());
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
