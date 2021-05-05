package algstudent.s7;

import static org.junit.Assert.*; 

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.junit.Test;

import algstudent.s6.BestList.Song;
import algstudent.s7.BestListBandB.SongNode;

public class BestListBandBTests {

	@Test
	public void testDifferentN() {
		
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		
//		sizes.add(25);
//		sizes.add(50);
//		sizes.add(100);
//		sizes.add(200);
//		sizes.add(400);
		
		sizes.add(5);
		sizes.add(10);
		sizes.add(15);
		
		for(Integer n: sizes) {
			List<Song> songs = generateSongsList(n);
			int totalDuration = calculateDuration(songs);
			
			// Uncomment for backtracking testing (warning: it never ends for big values of n)
//			BestList bestList = new BestList();
//			bestList.songs = songs;
//			bestList.setLength((int) (totalDuration * 0.4));
//			long startSback = System.currentTimeMillis();
//			bestList.backtracking(0);
//			long endSback = System.currentTimeMillis();
			
			
			BestListBandB b = new BestListBandB(new SongNode(songs, (int) (totalDuration * 0.4)));
			long startSBandB = System.currentTimeMillis();
			b.branchAndBound(b.getRootNode());
			long endSBandB = System.currentTimeMillis();
			
			// Uncomment for backtracking testing (warning: it never ends for big values of n)
			//System.out.println("Backtracking - Size: " + n + " Total millis: " + (endSback - startSback));
			System.out.println("Branch and bound - Size: " + n + " Total millis: " + (endSBandB - startSBandB));
		}
	}
	
	private ArrayList<Song> generateSongsList(int n){
		
		ArrayList<Song> songs = new ArrayList<Song>();
		
		int score, minSong, secSong;
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
        	score = rand.nextInt(5000);
        	minSong = rand.nextInt(10);
        	secSong = rand.nextInt(61);
            songs.add(new Song(String.valueOf(i), secSong + minSong * 60, minSong + ":" + secSong,score));
        }
		
		return songs;
	}
	
	private int calculateDuration(List<Song> songList) {
		int res = 0;
		
		for(Song s: songList) {
			res+=s.duration;
		}
		
		return res;
	}
}
