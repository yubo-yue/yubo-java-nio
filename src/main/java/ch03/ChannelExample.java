package ch03;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelExample {

	public static void main(String[] args) {

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
		while(buffer.hasRemaining()) {
			dst.write(buffer);
		}
	}
	
	public static void channelCopy2(ReadableByteChannel src, WritableByteChannel dst) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		
		while (src.read(buffer) != -1) {
			buffer.flip();
			//make sure the buffer fully drained
			while(buffer.hasRemaining()) {
				dst.write(buffer);
			}
			//clear the buffer to make buffer empty, to be ready for filling
			buffer.clear();
		}
	}
	
	public static void gatherWriteUsMultipleBuffer() throws FileNotFoundException
	{
		String fileName = "nio-demo";
		FileOutputStream fos = new FileOutputStream(fileName);
	}

}
