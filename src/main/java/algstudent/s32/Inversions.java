package algstudent.s32;

import java.util.Arrays;
import java.util.List;

public class Inversions {

	List<Integer> ranking;
	
	public Inversions(List<Integer> ranking) {
		this.ranking = ranking;
	}

	public String start() {
		
		long[] rankingArray = new long[ranking.size()]; // To copy to array
		
		for(int i = 0; i < ranking.size(); i++) {
			rankingArray[i] = ranking.get(i);
		}
		
		return String.valueOf(mergeSortAndCount(rankingArray, 0, rankingArray.length - 1));
	}
	
	private long mergeSortAndCount(long[] arr, int left, int right) {
		
		long count = 0;
		if (left < right) {
			
			int center = (left + right) / 2;
			count += mergeSortAndCount(arr, left, center);
			count += mergeSortAndCount(arr, center + 1, right);
			count += combine(arr, left, center, right);
		
		}
		
		return count;
	}
	
	private long combine(long[] arr, int left, int center, int right){

		long[] leftArray = Arrays.copyOfRange(arr, left, center + 1);
		long[] rightArray = Arrays.copyOfRange(arr, center + 1, right + 1);
		
		int i = 0, j = 0, k = left;
		
		long swaps = 0;
		
		while (i < leftArray.length && j < rightArray.length) { // Choosing the minimum
			if (leftArray[i] <= rightArray[j])
				arr[k++] = leftArray[i++];
			else {
				arr[k++] = rightArray[j++];
				swaps += (center + 1) - (left + i);
			}
		}
		
		while (i < leftArray.length)
			arr[k++] = leftArray[i++];
		while (j < rightArray.length)
			arr[k++] = rightArray[j++];
		
		return swaps;
	}
}
