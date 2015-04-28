package org.simple.agent;

import java.lang.instrument.Instrumentation;
/**
 * First java agent implementation. see {@link http://www.javabeat.net/introduction-to-java-agents/} 
 * @author yubo
 *
 */
public class TestJavaAgent {
	public static void premain(String agentArgument, Instrumentation instrumentation) {
		System.out.println("Test Java Agent");
	}
}
