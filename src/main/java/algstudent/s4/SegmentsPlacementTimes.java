package algstudent.s4;

import java.util.Random;

public class SegmentsPlacementTimes {

	int[] vector;
	static int nTimes = 1000;
	static SegmentsPlacement sp;
	
	public static void main(String[] args) {
		/*
		 * This  class  will  simulate  (through the Random class)  inputs  of  different  sizes n and  for  each n they  will  measure  
		 * the  time  it  takes to  execute  each  of  the  3 greedy algorithms.
		 */
		
		int[] vector;
		
		long t;
		
		for (int n = 100; n <= 12800; n*= 2) {
				
				t = 0;
				
				vector = new int[n];
				
				fillVector(vector);
				
				sp = new SegmentsPlacement(vector);
				
				
				for (int i = 0; i < nTimes; i++) {
					t += measureHeuristic1();
				}
				System.out.println ("n=" + n + "**TIME (heuristic 1)=" + t);
				
				t = 0;
				for (int i = 0; i < nTimes; i++) {
					t += measureHeuristic2();
				}
				System.out.println ("n=" + n + "**TIME (heuristic 2)=" + t);
				
				t = 0;
				for (int i = 0; i < nTimes; i++) {
					t += measureHeuristic3();
				}
				
				System.out.println ("n=" + n + "**TIME (heuristic 3)=" + t);
				System.out.println();
				
		}
		
	}

	private static void fillVector(int[] vector) {
		
		Random r = new Random();
		for (int i = 0; i < vector.length; i++) {
			vector[i] = r.nextInt(Integer.MAX_VALUE); // Lengths are integer numbers in the interval [1..99]. r.nextInt(99) + 1;
		}
	}

	private static long measureHeuristic1() {
		long t1,t2;
		t1 = System.currentTimeMillis();
		SegmentsPlacement.greedyAlgorithm1();
		t2 = System.currentTimeMillis();
		return t2-t1;
	}
	
	private static long measureHeuristic2() {
		long t1,t2;
		t1 = System.currentTimeMillis();
		SegmentsPlacement.greedyAlgorithm2();
		t2 = System.currentTimeMillis();
		return t2-t1;
	}
	
	private static long measureHeuristic3() {
		long t1,t2;
		t1 = System.currentTimeMillis();
		SegmentsPlacement.greedyAlgorithm3();
		t2 = System.currentTimeMillis();
		return t2-t1;
	}
}
