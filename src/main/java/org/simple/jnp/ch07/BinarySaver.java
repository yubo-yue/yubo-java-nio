package org.simple.jnp.ch07;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BinarySaver {

	public static void saveBinaryFile(URL u) throws IOException {
		URLConnection uc = u.openConnection();
		String contentType = uc.getContentType();
		int contentLength = uc.getContentLength();

		if (contentType.startsWith("text/") || contentLength == -1) {
			throw new IOException("Not a binary file");
		}
		
		try (InputStream raw = uc.getInputStream()) {
			InputStream in = new BufferedInputStream(raw);
			byte[] data = new byte[contentLength];
			int offset = 0;
			
			while (offset < contentLength) {
				int bytesRead = in.read(data, offset, data.length - offset);
				if (bytesRead == -1) break;
				offset += bytesRead;
			}
			
			if (offset != contentLength) {
				throw new IOException("expected bytes is not equal acutally read bytes.");
			}
			
			String fileName = u.getFile();
			fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
			try (FileOutputStream fos = new FileOutputStream(fileName)) {
				fos.write(data);
				fos.flush();
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length; ++i) {
			try {
				URL u = new URL(args[i]);
				saveBinaryFile(u);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
