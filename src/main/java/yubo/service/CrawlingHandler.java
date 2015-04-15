package yubo.service;
import java.util.List;

import org.apache.thrift.TException;

import yubo.service.thrift.CrawlingService;
import yubo.service.thrift.Item;
public class CrawlingHandler implements CrawlingService.Iface {

	@Override
	public void write(List<Item> items) throws TException {
		for (Item item : items)
		{
			System.out.println(item.toString());
		}
	}

}
