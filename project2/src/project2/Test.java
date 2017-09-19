package project2;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		BigInteger a, b, A, B, s1, s2, p, g;
		Random rand = new SecureRandom();
//		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter two prime numbers: ");
		g = BigInteger.valueOf(5);
		p = BigInteger.valueOf(23);
		
		a = BigInteger.valueOf(rand.nextInt(100)+1);
		System.out.println("Alice secret a: " + a);
		b = BigInteger.valueOf(rand.nextInt(100)+1);
		System.out.println("Bob secret b: " + b);

		A = g.modPow(a, p);
		B = g.modPow(b, p);
		
		s1 = B.modPow(a, p);
		System.out.println("Alice calculated the shared secret: " + s1);
		s2 = A.modPow(b, p);
		System.out.println("Bob calculated the shared secret: " + s2);



////		int a = (int)(Math.random() * 100 + 1); //Not guaranteed to be always unique;
//		int a = 6;
//		int A = (int) (Math.pow(g, a) % p);
//		
//		System.out.println("Generated random nr: " + a + " and calculated: " + A);
//
////		int b = (int)(Math.random() * 100 + 1); //Not guaranteed to be always unique;
//		int b = 15;
//		int B = (int) (Math.pow(g, b) % p);
//		
//		System.out.println("Generated random nr: " + b + " and calculated: " + B);
//		
//		int s1 = (int)(Math.pow(B, a));
////		System.out.println(s1);
//		s1 = (int)(s1 % p);
//		System.out.println("Calculated secret s1: " + s1);
//		int s2 = (int)(Math.pow(A, b) % p);
////		System.out.println(s2);
//		s1 = (int)(s2 % p);
//		System.out.println("Calculated secret s2: " + s2);

	}
}
