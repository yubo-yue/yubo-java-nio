package ch03;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class ChannelOperations {

	public static void main(String[] args) throws IOException {
		memoryMappedFilesTest();
	}
	
	public static void memoryMappedFilesTest() throws IOException {
		File file = File.createTempFile("mmaptest", null);
		RandomAccessFile raFile = new RandomAccessFile(file, "rw");
		FileChannel channel = raFile.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(100);

		buffer.put("This is file content".getBytes());
		buffer.flip();
		channel.write(buffer);

		buffer.clear();
		buffer.put("This is more file content".getBytes());
		buffer.flip();
		channel.write(buffer, 8192);

		// create three types of mapping to the same file
		MappedByteBuffer roBuffer = channel.map(MapMode.READ_ONLY, 0,
				channel.size());
		MappedByteBuffer rwBuffer = channel.map(MapMode.READ_WRITE, 0,
				channel.size());
		MappedByteBuffer cowBuffer = channel.map(MapMode.PRIVATE, 0,
				channel.size());

		System.out.println("Begin");
		showBuffers(roBuffer, rwBuffer, cowBuffer);
		
		cowBuffer.position(8);
		cowBuffer.put("COW".getBytes());
		System.out.println("Changes made to COW buffer");
		showBuffers(roBuffer, rwBuffer, cowBuffer);
		
		rwBuffer.position(9);
		rwBuffer.put("R/W".getBytes());
		rwBuffer.position(8194);
		rwBuffer.put("R/W".getBytes());
		rwBuffer.force();
		
		System.out.println("Changes made to R/W buffer");
		showBuffers(roBuffer, rwBuffer, cowBuffer);

		buffer.clear();
		buffer.put("Channel write".getBytes());
		buffer.flip();
		
		channel.write(buffer, 0);
		buffer.rewind();
		channel.write(buffer, 8202);
		
		System.out.println("Write on channel");
		showBuffers(roBuffer, rwBuffer, cowBuffer);
		
		cowBuffer.position(8207);
		cowBuffer.put(" COW2 ".getBytes());
		
		System.out.println("Second write to COW buffer");
		showBuffers(roBuffer, rwBuffer, cowBuffer);
		
		rwBuffer.position(0);
		rwBuffer.put(" R/W2 ".getBytes());
		rwBuffer.position(8210);
		rwBuffer.put(" R/W2 ".getBytes());
		rwBuffer.force();
		
		System.out.println("Second write to r/w buffer");
		showBuffers(roBuffer, rwBuffer, cowBuffer);
		
		channel.close();
		raFile.close();
		file.delete();
		
	}
	
	public static void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer cow)
	{
		dumpBuffer("R/O", ro);
		dumpBuffer("R/W", rw);
		dumpBuffer("COW", cow);
		
		System.out.println("");
	}

	public static void dumpBuffer(final String prefix, ByteBuffer buffer) {
		System.out.print(prefix + ": '");
		int nulls = 0;
		int limit = buffer.limit();

		for (int i = 0; i < limit; ++i) {
			char c = (char)buffer.get(i);
			if (c == '\u0000') {
				nulls++;
				continue;
			}
			if (nulls != 0) {
				System.out.print("|[" + nulls + " nulls]|");
				nulls = 0;
			}
			System.out.print(c);
		}
		System.out.print("'\n");
	}

}
