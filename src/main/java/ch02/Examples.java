package ch02;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Examples {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Examples.class);

	public static void main(String[] args) {
		//createBuffer();
		//duplicateBuffer();
		Examples.bufferView();
	}

	public static void bulkMove() {
		CharBuffer buffer = CharBuffer.allocate(100);
		bufferSummary(buffer);

		String data = "hello world";
		buffer.put(data.toCharArray());
		bufferSummary(buffer);

		char[] bigArr = new char[1000];
		buffer.flip();
		int length = buffer.remaining();
		// drain buffer to array
		buffer.get(bigArr, 0, length);
		bufferSummary(buffer);

		printArray(bigArr);

	}

	public static void createBuffer() {
		CharBuffer charBuffer = CharBuffer.allocate(100);
		bufferSummary(charBuffer);

		int[] intArr = new int[100];
		IntBuffer intBuffer = IntBuffer.wrap(intArr);
		bufferSummary(intBuffer);
	}
	
	public static void duplicateBuffer()
	{
		CharBuffer charBuffer = CharBuffer.allocate(8);
		Examples.bufferSummary(charBuffer);
		charBuffer.position(3).limit(6).mark().position(5);
		Examples.bufferSummary(charBuffer);
		CharBuffer dupBuffer = charBuffer.duplicate();
		Examples.bufferSummary(dupBuffer);
		dupBuffer.clear();
		Examples.bufferSummary(dupBuffer);
	}
	
	public static void bufferView()
	{
		ByteBuffer byteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		
		byteBuffer.put(0, (byte) 0);
		byteBuffer.put(1, (byte) 'H');
		byteBuffer.put(2, (byte)0);
		byteBuffer.put(3, (byte)'i');
		byteBuffer.put(4, (byte)0);
		byteBuffer.put(5, (byte)'!');
		byteBuffer.put(6, (byte)0);
		
		bufferSummary(byteBuffer);
		bufferSummary(charBuffer);
	}

	public static void bufferSummary(Buffer buffer) {
		LOGGER.info("------------------------------");
		LOGGER.info("{}", buffer.getClass().getName());
		LOGGER.info("Capacity: {}, Limit : {}, Mark : {}, Position : {}",
				buffer.capacity(), buffer.limit(), buffer.mark(),
				buffer.position());
		LOGGER.info("Remaining : {}", buffer.remaining());
		LOGGER.info("------------------------------");
	}

	public static void printArray(char[] arr) {
		LOGGER.info("------------------------------");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
		}
		System.out.println();
		LOGGER.info("------------------------------");
	}
}
