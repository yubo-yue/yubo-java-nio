package utils;

import java.nio.ByteBuffer;

/**
 * Since java doesn't provide unsigned primitive type, each unsiged value read
 * from buffer is promoted up to the next bigger primitive data types.
 * 
 * @author yubo
 *
 */
public class UnsignedUtility {

	public static short getUnsignedByte(ByteBuffer bb) {
		return ((short) (bb.get() & 0xff));
	}

	public static void putUnsignedByte(ByteBuffer bb, int value) {
		bb.put(((byte) (value & 0xff)));
	}

	public static short getUnsignedByte(ByteBuffer bb, int position) {
		return ((short) (bb.get(position) & 0xff));
	}

	public static void putUnsignedByte(ByteBuffer bb, int position, int value) {
		bb.put(position, (byte)(value & 0xff));
	}
}
