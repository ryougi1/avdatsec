package project2;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class DH {	
	public DH() {
		
	}
	
	public static String[] getRandomNumbers(BigInteger g, BigInteger p) {
		Random rand = new SecureRandom();
		BigInteger x, X;
		
		x = BigInteger.valueOf(rand.nextInt(100)+1);
		X = g.modPow(x, p);
		
		String[] result = new String[2];
		result[0] = x.toString();
		result[1] = X.toString();
		return result;
	}
	
	synchronized public static String computeSecret(String base, String exponent, BigInteger p) {
		BigInteger secret = new BigInteger(base.trim()).modPow(new BigInteger(exponent.trim()), p);
		return secret.toString();
	}
}
