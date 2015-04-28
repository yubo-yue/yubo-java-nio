package org.simple.metric;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

public class MetricGetStarted {

	static final MetricRegistry metrics = new MetricRegistry();

	public static void main(String[] args) {
		startReport();
		Meter requests = metrics.meter("requests");
		requests.mark();
		wait5Seconds();
	}

	static void startReport() {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.convertRatesTo(TimeUnit.SECONDS).build();

		reporter.start(1, TimeUnit.SECONDS);
	}
	
	static void wait5Seconds()
	{
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException ignore) {
		}
	}
}
