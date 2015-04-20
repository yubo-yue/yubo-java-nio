package ch03.datagram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeServer {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TimeServer.class);
	
	private static final int DEFAULT_TIME_PORT = 9999;
	private static final long DIFF_1900 = 2208988800L;

	protected DatagramChannel channel;

	public TimeServer(int port) throws IOException {
		this.channel = DatagramChannel.open();
		this.channel.socket().bind(new InetSocketAddress(port));
		LOGGER.info("Listening on port {} for time requests ", port);
	}
	
	public void listen() throws IOException
	{
		ByteBuffer longBuffer = ByteBuffer.allocate(8);
		longBuffer.order(ByteOrder.BIG_ENDIAN);
		longBuffer.putLong(0, 0);
		longBuffer.position(4);
		
		ByteBuffer buffer = longBuffer.slice();
		while(true)
		{
			buffer.clear();
			SocketAddress sa = this.channel.receive(buffer);
			
			if (sa == null) {
				continue;
			}
			
			LOGGER.info("Time request from {}", sa);
			buffer.clear();
			longBuffer.putLong(0, (System.currentTimeMillis()  / 1000) + DIFF_1900);
			this.channel.send(buffer, sa);
		}
	}
	
	public static void main(String[] args)
	{
		int port = DEFAULT_TIME_PORT;
		
		if (args.length > 0) {
			port = Integer.parseInt(args[1]);
		}
		
		try {
			TimeServer server = new TimeServer(port);
			server.listen();
		} catch (IOException e) {
			LOGGER.error("can't bind to port {}, try a different one", port, e);
		}
	}
}
