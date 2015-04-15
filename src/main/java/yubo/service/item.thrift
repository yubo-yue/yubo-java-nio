namespace java yubo.service.thrift

struct Item {
	1: i64 id,
	2: string content,
}

service CrawlingService {
	void write(1:list<Item> items),
}