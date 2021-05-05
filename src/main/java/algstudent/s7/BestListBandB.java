package algstudent.s7;

import java.io.BufferedReader;  
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.DSAGenParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import algstudent.s6.BestList.Song;
import algstudent.s7.util.BranchAndBound;
import algstudent.s7.util.Node;

public class BestListBandB extends BranchAndBound {

	List<Song> songs = new ArrayList<Song>();
	
	public static void main(String[] args) {
		
		SongNode n = new SongNode(readSongsFromFile("src/main/java/algstudent/s7/" + args[0]),
				Integer.valueOf(args[1]));
		
		BestListBandB b = new BestListBandB(n);
		b.branchAndBound(b.getRootNode()); 		
		b.printSolutionTrace();
	}
	
	public static List<Song> readSongsFromFile(String file) {
		
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
	
	public BestListBandB(SongNode initNode) {
		rootNode=initNode;
	}
	
	static class SongNode extends Node{  // a node is a state of the problem
								  // snapshot of the problem, step
		
		// List that stores all the songs
		List<Song> songs = new ArrayList<Song>();
		
		// The 2 blocks
		List<Song> blockA= new ArrayList<Song>();
		List<Song> blockB= new ArrayList<Song>();
		
		int stateCounter = 0;
		int length = 0;
		
		 public SongNode(List<Song> songsList, int length) {
			this.songs = songsList;
			this.length = length;
		}
		 
		 public SongNode(List<Song> songsList, List<Song> blockA, List<Song> blockB, 
				 int level, int length, UUID parentID) {
				this.songs = songsList;
				this.blockA = blockA;
				this.blockB = blockB;
				this.depth = level;
				this.length = length;
				this.parentID = parentID;
				calculateHeuristicValue();
			}
		 
		@Override
		public void calculateHeuristicValue() {
			
			int res = 0;
			if(prune()) {
				heuristicValue = Integer.MAX_VALUE;
			} else {
					res += sumScore(blockA) + sumScore(blockB);
					heuristicValue=-res;
			}
		}

		@Override
		public ArrayList<Node> expand() {
			
			ArrayList<Node> result=new ArrayList<Node>();
			
			// Version for finding first solution
//			result.add(new SongNode(this.songs, new ArrayList<>(blockA), new ArrayList<>(blockB), 
//					depth+1, length, this.getID()));
//			
//			if(!blockA.contains(songs.get(depth))) {
//				blockA.add(songs.get(depth));
//				result.add(new SongNode(this.songs, new ArrayList<>(blockA), new ArrayList<>(blockB), 
//						depth+1, length, this.getID()));
//				blockA.remove(songs.get(depth));
//			}
//			
//			if(!blockB.contains(songs.get(depth))) {
//				blockB.add(songs.get(depth));
//				result.add(new SongNode(this.songs, new ArrayList<>(blockA), new ArrayList<>(blockB), 
//						depth+1, length, this.getID()));
//				blockB.remove(songs.get(depth));
//			}
				
				
			// Version for finding best solution (slower)
			for(Song s: songs) {
				

				result.add(new SongNode(this.songs, new ArrayList<>(blockA), new ArrayList<>(blockB), 
						depth+1, length, this.getID()));
				
				if(!blockA.contains(s)) {
					blockA.add(s);
					result.add(new SongNode(this.songs, new ArrayList<>(blockA), new ArrayList<>(blockB), 
							depth+1, length, this.getID()));
					blockA.remove(s);
				}
				
				if(!blockB.contains(s)) {
					blockB.add(s);
					result.add(new SongNode(this.songs, new ArrayList<>(blockA), new ArrayList<>(blockB), 
							depth+1, length, this.getID()));
					blockB.remove(s);
				}
			
			}
			
			return result;
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

		@Override
		public boolean isSolution() {
			return (this.depth == songs.size()) ? true : false;
		}
		
		private boolean prune() {
			
			return (calculateDuration(blockA) > length*60) ||
					(calculateDuration(blockB) > length*60) || repeated(blockA, blockB);
		}
		
		@Override
		public String toString() {
			
			int totalScore = 0;
			String res = "";
			
			for(Song s: blockA) {
				totalScore+= s.score;
			}
			
			for(Song s: blockB) {
				totalScore+= s.score;
			}
			
			res += "Total score: " + totalScore + "\n";
			
			res += "Level: " + depth + "\n";
			
			res += "\nBest block A: \n";
			
			for(Song s: blockA) {
				res += s.toString() + "\n";
			}
			
			res += "\nBest block B: \n";
			for(Song s: blockB) {
				res += s.toString() + "\n";
			}
			
			return res;
		}
	}
	
	// expand() prune() isSolution() calculateHeuristicValue() toString()

}
