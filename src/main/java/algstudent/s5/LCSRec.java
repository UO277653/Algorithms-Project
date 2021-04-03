package algstudent.s5;

import java.util.Random;
import java.util.Locale.LanguageRange;

public class LCSRec {
	
	/**
	 * Generates a random sequence
	 * @param n sequence size
	 * @return random sequence, sting of characters
	 */
	public String genRandomSeq(int n){
		char[] dna_elements = {'A', 'C', 'G', 'T'};
		String result = "";
		Random r = new Random();
		for (int i=0; i<n; i++)
			result += dna_elements[r.nextInt(4)];
		return result;
	}
	
	/**
	 * Find a MSC directly by a recursive approach 
	 */
	public String findLongestSubseq(String s1, String s2){
		String L1, L2, L3;
		if(!(s1.length()==0) && !(s2.length()==0)) {
			
			L1 = findLongestSubseq(s1.substring(0, s1.length() - 1), s2); 
			L2 = findLongestSubseq(s1, s2.substring(0, s2.length() - 1)); 
 			L3 = findLongestSubseq(s1.substring(0, s1.length() - 1), s2.substring(0, s2.length() - 1));

 			if(s2.substring(s2.length() - 1, s2.length()).equals(s1.substring(s1.length() - 1, s1.length()))) {// if(s1.substring(s1.length() - 1, s1.length()).equals(s2.substring(s2.length() - 1, s2.length()))) {
				//L3 = L3 + s1.substring(s1.length() - 1, s1.length());
				L3 = L3 + s2.substring(s2.length() - 1, s2.length());
			}
			if (L1.length() > L2.length() && L1.length() > L3.length()) {
				return L1;
			}
			
			if (L2.length() > L1.length() && L2.length() > L3.length()) {
				return L2;
			}
			
			return L3;
		}
		return ""; 
		// TODO: find directly a MSC (whitout a table) of two input sequences using recursion  
	}
	
	/**
	 * Get the index for the longest of three strings
	 * @param l1 e.g. input L1=MSC(S1�, S2). S1� S1 without its last char
	 * @param l2 e.g. input L1=MSC(S1, S2'). S2' S2 without its last char
	 * @param l3 e.g. input L3=MSC(S1�, S2�) or L3+1 when both current chars are equal
	 * @return index of the longest string
	 */
	@SuppressWarnings("unused")
	private int longest(String l1, String l2, String l3) {
		return -1;
		// TODO (optional): from three different sequences (e.g. subsequences) gets index for the longest
	}

}
