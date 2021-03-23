package algstudent.s4;

import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class SegmentsPlacement {
	
	private static int[] segmentsNumbers;
	private static int[] segmentsNumbersCopy;
	
	public static void main(String[] args) {
		
		//The main method should include all the needed code to execute the 3 greedy algorithms, 
		//one after the other. The main method should also have an argument: the path of the text file to be used.
		
		segmentsNumbers = readSegmentsFromFile(Paths.get("").toAbsolutePath().toString() + "/src/main/java/algstudent/s4/" + String.valueOf(args[0]));
		
		int[] arrayGreedy1 = greedyAlgorithm1();
		
		computeAveragePufosos(arrayGreedy1, "Greedy 1");
		
		int[] arrayGreedy2 = greedyAlgorithm2();
		
		computeAveragePufosos(arrayGreedy2, "Greedy 2");
		
		int[] arrayGreedy3 = greedyAlgorithm3();
		
		computeAveragePufosos(arrayGreedy3, "Greedy 3");
	}
	
	public SegmentsPlacement(int[] initArray) {
		SegmentsPlacement.segmentsNumbers = initArray;
	}

	// Method to read
	private static int[] readSegmentsFromFile(String file) {
		BufferedReader fich = null;
		String line;
		int[] elements = null;
		int n = 0;

		try {
			fich = new BufferedReader(new FileReader(file));
			line = fich.readLine(); //first element of the file, number of segments
			
			elements = new int[Integer.valueOf(line)];
			
			line = fich.readLine();
			
			while (line != null) {
				elements[n] = Integer.valueOf(line);
				n++;
				line = fich.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("There is no file: "+file);
		} catch (IOException e) {
			System.out.println("Error reading the file: "+file);
		} finally {
			if (fich!=null)
				try {
					fich.close();
				} catch (IOException e) {
					System.out.println("Error closing the file: "+file);
					e.printStackTrace();
				}
		}

		return elements;
	}
	
	// Greedy 1 
	public static int[] greedyAlgorithm1() {
		
		return segmentsNumbers;
	}
	
	// Greedy 2
	public static int[] greedyAlgorithm2() {
		
		sort();
		
		int[] temp = segmentsNumbersCopy;
		
		int[] res = new int[temp.length];
		
		for (int i = 0; i < temp.length; i++) {
			res[i] = temp[temp.length - 1 - i];
		}
		
		return res;
	}
	
	// Greedy 3
	public static int[] greedyAlgorithm3() {
		
		sort();
		
		int[] res = segmentsNumbersCopy;
		
		return res;
	}
	
	// Method to calculate average
	private static float computeAveragePufosos(int[] numbers, String name) { // computeAveragePufosos and solution

		System.out.print("- " + name + ":\n");
		float n = 0;
		float midpoint = 0;
		float cost = 0;
		for(int i = 0; i < numbers.length; i++) {
			midpoint = (2*n + numbers[i])/2;
			cost += midpoint;
			System.out.print("S" + i + ": (" + n + "," + (n + numbers[i]) + ")" + " midpoint: " + midpoint + "\n");
			n += numbers[i];
		}
		
		System.out.print("Cost: " + cost + " pufosos\n");
		
		return n;
	}
	
	// Sorting functionality
	
	/*get the position of the median of the three (left, right and 
	 the element which position is in the center between them, and
	 move the elements to order them */
	private static int median_of_three(int left, int right) { 
		int center = (left + right) / 2;
		if (segmentsNumbersCopy[left] > segmentsNumbersCopy[center])
			interchange(left, center);
		if (segmentsNumbersCopy[left] > segmentsNumbersCopy[right])
			interchange(left, right);
		if (segmentsNumbersCopy[center] > segmentsNumbersCopy[right])
			interchange(center, right);
		return center;
	}
	
	private static void quickSort(int left, int right) {
		int i = left;
		int j = right - 1;
		int pivot;
		
		if (left < right){ //if there is one element it is not necessary
			int center = median_of_three(left, right);
			//if there are less than or equal to 3 elements, there are just ordered
			if ((right - left) >= 3){ 
				pivot = segmentsNumbersCopy[center]; //choose the pivot
				interchange(center, right); //hide the pivot

				do {         
			    	while (segmentsNumbersCopy[i] <= pivot && i < right) i++; //first element > pivot
			    	while (segmentsNumbersCopy[j] >= pivot && j > left) j--; //first element < pivot
			        if (i < j) interchange(i, j);
			    } while (i < j);   //end while
				
				//we set the position of the pivot
				interchange(i, right);
				quickSort(left, i-1);
				quickSort(i+1, right);		
			} //if
		} //if
	}
	
	protected static void interchange(int i, int j) {
		int t;
		t = segmentsNumbersCopy[i];
		segmentsNumbersCopy[i] = segmentsNumbersCopy[j];
		segmentsNumbersCopy[j] = t;
	}

	public static void sort() {
		segmentsNumbersCopy = Arrays.copyOfRange(segmentsNumbers, 0, segmentsNumbers.length);
		quickSort(0, segmentsNumbersCopy.length-1);
	}
}
