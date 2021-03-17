package algstudent.s3;

/* Params: a = 3, b = 3, k = 2
 */
public class Division4 {
	public static long rec1 (int n) { 
		long cont = 0;
		if (n<=0) cont++;
		else { 
			for (int i=1;i<n;i++) 
				for (int j=1;j<n;j++) 
					cont++ ;  //O(n2)    
	    	rec1(n/3);
	    	rec1(n/3);
	    	rec1(n/3);
	    	rec1(n/3);
		}
		return cont;   
	}
	
	public static void main (String arg []) {
		long t1, t2, cont = 0;
		for (int n=1; n<=10000000; n*=2) {
			 t1 = System.currentTimeMillis();
			 cont = rec1(n);			 
			 t2 = System.currentTimeMillis();
			
			 System.out.println ("n="+n+ "**TIME="+(t2-t1)+"**cont="+cont);
		 }  // for
	} // main
} //class