package algstudent.s1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MatrixOperationsTimes {
	
	private int[][] matrix;
	private int n;
	
	public static void main(String[] args) {
		MatrixOperationsTimes mo = new MatrixOperationsTimes("src/main/java/MatrixOperations.txt");
		mo.measureDiagonal1Times();
	}
	
	public MatrixOperationsTimes(int n, int min, int max) {
		// Parameter checking?
		
		this.n = n;
		matrix = new int[n][n];
		Random r = new Random();
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++) {
				matrix[i][j] = r.nextInt(max) + min;
			}
		
	}
	
	public MatrixOperationsTimes(String  fileName) {
		loadNumbers(fileName);
	}
	
	public int getSize() {
		return this.n;
	}
	
	public void write() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	/*
	 * Quadratic
	 */
	public int sumDiagonal1() {
		int res = 0;
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i == j) {
					res += matrix[i][j];
				}
			}
		}
		
		return res;
	}
	
	/*
	 * Linear
	 */
	public int sumDiagonal2() {
		int res = 0;
		
		for(int i = 0; i < n; i++) {
			res += matrix[i][i];
		}
		
		return res;
	}
	
	public void loadNumbers(String fileName) {
		
	    String line;
	    String[] lineData= null;
	    int counterRows = 0;
	    int counterColumns = 0;
	    
	    try {
	    	   BufferedReader file = new BufferedReader(new FileReader(fileName));
	    	   n = Integer.valueOf(file.readLine());
	    	   matrix = new int[n][n];
	    	   
	    		while (file.ready()) {
	    			int number;
	    			line = file.readLine();
	    			lineData = line.split("\t");
	    			for(int i = 0; i < n; i++) {
	    				number = Integer.valueOf(lineData[counterColumns]);
	    				this.matrix[counterRows][counterColumns] = number;
	    				counterColumns++;
	    			}
	    			counterRows++;
	    			counterColumns = 0;
	    		}
	    		file.close();
	    }
	    catch (FileNotFoundException fnfe) {
	      System.out.println("File not found.");
	    }
	    catch (IOException ioe) {
	      new RuntimeException("I/O Error.");
	    }
	}
	
	public void travelPath(int i, int j) {
		if(i >= n || j >= n) {
			return;
		}
		switch(matrix[i][j]) {
		case 1:
			matrix[i][j] = -1;
			travelPath(i - 1, j);
			break;
		
		case 2:
			matrix[i][j] = -1;
			travelPath(i, j + 1);
			break;
		
		case 3:
			matrix[i][j] = -1;
			travelPath(i + 1, j);
			break;
		
		case 4:
			matrix[i][j] = -1;
			travelPath(i, j - 1);
			break;
			
		case -1:
			return;
		}
	}
	
	private void measureDiagonal1Times() {
		long t1, t2;
		
		for(int n = 10; n <= Integer.MAX_VALUE; n*=3) {
		
			t1 = System.currentTimeMillis();
			
			for(int repetitions = 0; repetitions <= n; repetitions++) {
				sumDiagonal1();
			}
			
			t2 = System.currentTimeMillis();
			
			System.out.printf("SIZE-%d TIME-%d milliseconds\n", n, t2-t1);
		}
	}
	
	private void measureDiagonal2Times() {
		long t1, t2;
		
		for(int n = 10; n <= Integer.MAX_VALUE; n*=3) {
		
			t1 = System.currentTimeMillis();
			
			for(int repetitions = 0; repetitions <= n; repetitions++) {
				sumDiagonal2();
			}
			
			t2 = System.currentTimeMillis();
			
			System.out.printf("SIZE-%d TIME-%d milliseconds\n", n, t2-t1);
		}
	}
}
