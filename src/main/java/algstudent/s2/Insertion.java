package algstudent.s2;


/* This program can be used to sort n elements with 
 * a "bad" algorithm (quadratic). 
 * It is the DIRECT INSERTION */
public class Insertion extends Vector {	
	public Insertion(int nElements) {
		super(nElements);
	}

	@Override
	public void sort(){
		int counter;
		int pivot;
		
		for(int i = 1; i < elements.length; i++) {
			pivot = elements[i];
			counter = i-1;
			
			while(counter>=0 && pivot<elements[counter]) {
				elements[counter+1] = elements[counter];
				counter--;
			}
			
			elements[counter+1] = pivot;
		}
	}

	@Override
	public String getName() {
		return "Insertion";
	} 
} 
