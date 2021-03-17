package algstudent.s32;

import java.util.List;

public class InversionsQuadratic {

	List<Integer> ranking;
	
	long[] rankingArray;
	
	public InversionsQuadratic(List<Integer> ranking) {
		this.ranking = ranking;
		
		rankingArray = new long[ranking.size()]; // To copy to array
		
		for(int i = 0; i < ranking.size(); i++) {
			rankingArray[i] = ranking.get(i);
		}
	}
	
    public long start() // Brute force O(n^2)
    {
        long inv_count = 0;
        for (int i = 0; i < rankingArray.length - 1; i++)
            for (int j = i + 1; j < rankingArray.length; j++)
                if (rankingArray[i] > rankingArray[j])
                    inv_count++;
 
        return inv_count;
    }

}
