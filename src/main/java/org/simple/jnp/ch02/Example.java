package org.simple.jnp.ch02;

import java.io.IOException;
import java.io.OutputStream;

public class Example {

	public static void main(String[] args) throws IOException {
		generateCharacters(System.out);
	}
	
	public static void generateCharacters(OutputStream out) throws IOException
	{
		int firstPrintableChar = 33;
		int numberOfPrintableChars = 94;
		int numberOfCharPerLine = 72;
		
		int start = firstPrintableChar;
		
		while(true) {
			for (int i = start; i < start + numberOfCharPerLine; i++) {
				out.write(((i - firstPrintableChar) % numberOfPrintableChars) + firstPrintableChar);
			}
			out.write('\r');
			out.write('\n');
			start = ((start + 1) - firstPrintableChar) % numberOfPrintableChars + firstPrintableChar;
		}
	}

}
