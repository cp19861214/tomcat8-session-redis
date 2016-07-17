package org.sourcecode.tomcat.session;

import java.util.Random;

import org.apache.catalina.util.StandardSessionIdGenerator;

public class RedisSessionIdGenerator extends StandardSessionIdGenerator {

	final String[] words = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9" };

	@Override
	public String generateSessionId(String route) {
		String sessionId = super.generateSessionId(route);
		String sRand = "";
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			String rand = words[random.nextInt(words.length)];
			sRand += rand;
		}
		return sessionId + sRand;
	}

	public static void main(String[] args) {
		RedisSessionIdGenerator i = new RedisSessionIdGenerator();
		System.out.println(i.words.length);
	}
}
