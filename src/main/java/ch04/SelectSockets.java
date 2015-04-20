package ch04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectSockets {
	public static final int PORT_NUMBER = 9998;
	
	public static void main(String[] args) throws IOException {
		new SelectSockets().go(args);
	}

	protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws IOException {
		if (channel == null)
			return;
		
		channel.configureBlocking(false);
		
		channel.register(selector, ops);
	}
	
	private ByteBuffer buffer = ByteBuffer.allocate(1024);
	
	private void go(String[] args) throws IOException
	{
		int port = PORT_NUMBER;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverChannel.socket();
		Selector selector = Selector.open();
		serverSocket.bind(new InetSocketAddress(port));
		serverChannel.configureBlocking(false);
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true) {
			int n = selector.select();
			if (n == 0) continue;
			
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					registerChannel(selector, channel, SelectionKey.OP_READ);
					sayHello(channel);
				}
				
				if (key.isReadable()) {
					readDataFromSocket(key);
				}
				//remove key from selected set; it 's been handled
				it.remove();
			}
		}
		
	}
	
	protected void readDataFromSocket(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();
		int count;
		buffer.clear();
		
		while ((count = socketChannel.read(buffer)) > 0) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				socketChannel.write(buffer);
			}
			buffer.clear();
		}
		
		if (count < 0) {
			socketChannel.close();
		}
	}
	
	private void sayHello(SocketChannel channel) throws IOException
	{
		buffer.clear();
		buffer.put("Hi there!\r\n".getBytes());
		buffer.flip();
		
		channel.write(buffer);
	}
}
