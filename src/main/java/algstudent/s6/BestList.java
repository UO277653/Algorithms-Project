package algstudent.s6;

import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BestList {

	List<Song> songs = new ArrayList<Song>();
	// store songs in a list
	
	// 2 blocks max
	List<Song> blockA= new ArrayList<Song>();
	List<Song> blockB= new ArrayList<Song>();
	
	List<Song> blockAres= new ArrayList<Song>();
	List<Song> blockBres= new ArrayList<Song>();
	
	//List<Song> bestSolutionList = new ArrayList<Song>(); // Useful for temporarily storing the solution
	
	int stateCounter = 0;
	
	int length = 0;
	
	// check if one block, second
	
	public static void main(String[] args) { // First argument is the name of the data file
											 // Second argument is the length of the block of songs
		
		BestList list = new BestList();
		
		list.songs = list.readSongsFromFile( "src/main/java/algstudent/s6/" + args[0]);
		
		list.setLength(Integer.valueOf(args[1]));
		list.printListOfSongs();
		
		list.backtracking(0);
		
		list.printBestSolutionList();
	}
	
	// Method to read
	private List<Song> readSongsFromFile(String file) {
		
		BufferedReader fich = null;
		String line;
		List<Song> songs = null;

		try {
			fich = new BufferedReader(new FileReader(file));
			line = fich.readLine(); //first element of the file, number of songs
			
			songs = new ArrayList<Song>();
			
			line = fich.readLine();
			
			while (line != null) {
				String[] args = line.split("\t");
				
				String[] tempCalculateTime = args[1].split(":");
				int totalSeconds = Integer.valueOf(tempCalculateTime[0]) * 60 + Integer.valueOf(tempCalculateTime[1]);
				
				songs.add(new Song(args[0], totalSeconds, args[1], Integer.valueOf(args[2])));
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

		return songs;
	}
		
	public void backtracking(int level) { // lists necessary?
		
		if(level == songs.size()) { // compare solutions and choose the best solution
			
			if(sumScore(blockAres) < sumScore(blockA)) {
				blockAres = new ArrayList<Song>(blockA);
			}
			
			if(sumScore(blockBres) < sumScore(blockB) && !repeated(blockB, blockAres)) {
				blockBres = new ArrayList<Song>(blockB);
			}
			
		} else {
				stateCounter++;
				backtracking(level + 1);
				if(calculateDuration(blockA) + songs.get(level).duration <= length*60) { // Check the condition
					
						stateCounter++;
						blockA.add(songs.get(level));
						backtracking(level + 1);
						blockA.remove(songs.get(level));
				}
				
				if(calculateDuration(blockB) + songs.get(level).duration <= length*60) {
						stateCounter++;
						blockB.add(songs.get(level));
						backtracking(level + 1);
						blockB.remove(songs.get(level));
				}
			}
		}

	private boolean repeated(List<Song> blockB, List<Song> blockAres) {
		for (Song s1 : blockAres) {
			for(Song s2: blockB) {
				if(s1.equals(s2)) {
					return true;
				}
			}
		}
		return false;
	}

	private int calculateDuration(List<Song> songList) {
		int res = 0;
		
		for(Song s: songList) {
			res+=s.duration;
		}
		
		return res;
	}

	private int sumScore(List<Song> songList) {
		
		int res = 0;
		
		for(Song s: songList) {
			res+=s.score;
		}
		
		return res;
	}
	
	public void printBestSolutionList() {
		
		int totalScore = 0;
		
		for(Song s: blockAres) {
			totalScore+= s.score;
		}
		
		for(Song s: blockBres) {
			totalScore+= s.score;
		}
		
		System.out.println("Total score: " + totalScore);
		
		System.out.println("Total counter: " + stateCounter);
		
		System.out.println();
		
		System.out.print("Best block A: \n");
		for(Song s: blockAres) {
			System.out.print(s.toString() + "\n");
		}
		
		System.out.println();
		
		System.out.print("Best block B: \n");
		for(Song s: blockBres) {
			System.out.print(s.toString() + "\n");
		}
	}
	
	public void printListOfSongs() {
		
		System.out.println("Number of songs: " + songs.size() + "\n");
		
		System.out.println("List of songs:");
		for(Song s: songs) {
			System.out.println(s.toString());
		}
		System.out.println();
		
		System.out.println("Length of the blocks: " + this.length + ":" + 0);
	}
	
	public void setLength(int length) {
		
		this.length = length;
		
	}

	static class Song{
		
		public String id;
		public int duration; // in seconds
		public String durInMin;
		public int score;
		
		public Song(String id, int duration, String durInMin, int score) {
			
			this.id = id;
			this.duration = duration;
			this.score = score;
			this.durInMin = durInMin;
		}
		
		public String toString() {
			return "id: " + id + " seconds: " + durInMin + " score: " + score;
		}
	}
}
