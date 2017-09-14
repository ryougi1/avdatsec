package project2;

public class DH {	
	public DH() {
		
	}
	
	public static String[] generateSecret(int g, int p) {
		int a = (int)(Math.random() * 100 + 1); //Not guaranteed to be always unique;
		Double dSecret = Math.pow(g, a) % p;
		int iSecret = dSecret.intValue();
		String[] result = new String[2];
		result[0]=Integer.toString(iSecret);
		result[1]=Integer.toString(a);
		return result;
	}
	
	synchronized public static int computeSecret(String m, String a, int p) {
		int A = Integer.parseInt(a);
		System.out.println("m equals:"+m);
		int M = Integer.valueOf(m);
		int secret = (int) Math.pow(M, A) % p;
		return secret;
	}
}
