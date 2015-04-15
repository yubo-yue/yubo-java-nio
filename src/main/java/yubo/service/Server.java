package yubo.service;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import yubo.service.thrift.CrawlingService;

public class Server {
	private void start()
	{
		try {
			TServerSocket serverTransport = new TServerSocket(7911);
			CrawlingHandler handler = new CrawlingHandler();
			
			CrawlingService.Processor<CrawlingService.Iface> processor = new CrawlingService.Processor<CrawlingService.Iface>(handler);
			
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			System.out.println("Starting server on port 7911 ...");
			server.serve();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Server server = new Server();
		server.start();
	}
}
