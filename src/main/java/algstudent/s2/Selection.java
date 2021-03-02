package algstudent.s2;

/* This program can be used to sort n elements with 
 * a "bad" algorithm (quadratic). 
 * It is the SELECTION */
public class Selection extends Vector {
	public Selection(int nElements) {
		super(nElements);
	}
	
	@Override
	public void sort() {
		int min;
		
		for(int i = 0; i < elements.length-1; i++) {
			min = getMinimumPosition(i);
			interchange(i, min);
		}
	}  
	
	private int getMinimumPosition(int val) {
		int minimum = Integer.MAX_VALUE;
		int res = 0;
		
		for(int i = val; i < elements.length; i++) {
			if(elements[i] < minimum) {
				minimum = elements[i];
				res = i;
			}
		}
		
		return res;
	}
	
	@Override
	public String getName() {
		return "Selection";
	} 
} 
