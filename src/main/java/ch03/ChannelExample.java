package ch03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ChannelExample {

	public static void main(String[] args) throws Exception {
		System.out.println("io.tmpdir " + System.getProperty("java.io.tmpdir"));
		ChannelExample.gatherWriteUsMultipleBuffer();
	}

	public static void channelCopy(ReadableByteChannel src,
			WritableByteChannel dst) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		while (src.read(buffer) != -1) {
			buffer.flip();
			dst.write(buffer);
			buffer.compact();
		}

		buffer.flip();
		while (buffer.hasRemaining()) {
			dst.write(buffer);
		}
	}

	public static void channelCopy2(ReadableByteChannel src,
			WritableByteChannel dst) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

		while (src.read(buffer) != -1) {
			buffer.flip();
			// make sure the buffer fully drained
			while (buffer.hasRemaining()) {
				dst.write(buffer);
			}
			// clear the buffer to make buffer empty, to be ready for filling
			buffer.clear();
		}
	}

	private static String[] col1 = { "Aggregate", "Enable", "Leverage",
			"Facilitate", "Synergize", "Repurpose", "Strategize", "Reinvent",
			"Harness" };
	private static String[] col2 = { "cross-platform", "best-of-breed",
			"frictionless", "ubiquitous", "extensible", "compelling",
			"mission-critical", "collaborative", "integrated" };
	private static String[] col3 = { "methodologies", "infomediaries",
			"platforms", "schemas", "mindshare", "paradigms",
			"functionalities", "web services", "infrastructures" };
	private static String newline = System.getProperty("line.separator");

	public static void gatherWriteUsMultipleBuffer() throws Exception {
		String tmpDir = System.getProperty("java.io.tmpdir");
		String fileName = tmpDir + "nio-demo";
		FileOutputStream fos = new FileOutputStream(fileName);
		GatheringByteChannel gatherChannel = fos.getChannel();	
		ByteBuffer[] bbs = ChannelExample.utterBS(3);
		
		while(gatherChannel.write(bbs) > 0) {
			//loop until write return zero
		}
		
		fos.close();
	}

	private static ByteBuffer[] utterBS(int howMany) throws Exception {
		List<ByteBuffer> list = new LinkedList<ByteBuffer>();
		for (int i = 0; i < howMany; i++) {

			list.add(pickRandom(col1, " "));
			list.add(pickRandom(col2, " "));
			list.add(pickRandom(col3, newline));
		}
		ByteBuffer[] bufs = new ByteBuffer[list.size()];
		list.toArray(bufs);
		return (bufs);
	}

	private static Random rand = new Random();

	private static ByteBuffer pickRandom(String[] strings, String suffix)
			throws Exception {
		String string = strings[rand.nextInt(strings.length)];
		int total = string.length() + suffix.length();
		ByteBuffer buf = ByteBuffer.allocate(total);
		buf.put(string.getBytes("US-ASCII"));
		buf.put(suffix.getBytes("US-ASCII"));
		buf.flip();
		return (buf);
	}

}
