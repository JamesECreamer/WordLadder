/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	public static Set<String> dict = makeDictionary();
	public static ArrayList<ArrayList<String>> buckets = new ArrayList<ArrayList<String>>();
	
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		String dictWord = "";
		if(dict.iterator().hasNext()){
			dictWord = dict.iterator().next();
		}
		
		for(int j=0; j < dictWord.length(); j++){
			ArrayList<String> temp = new ArrayList<String>(1);
			temp.add("a");
			buckets.add(j, temp);
		}
		
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		// TO DO
		return null;
	}
	
	public static boolean distanceOne(String start, String word){
		int count = 0;
		if(start.length() != word.length()){
			return false;
		}
		for(int i=0; i < start.length(); i++){
			if(start.charAt(i) != word.charAt(i)){
				count++;
			}
		}
		if(count == 1){
			return true;
		} else {
			return false;
		}
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		String dictWord;
		// Base case
		if(start.equals(end)){
			ArrayList<String> result = new ArrayList<>(1);
			result.add(start);
			return result;
		}
		// if there is a next word get the next word from dict
		if(dict.iterator().hasNext()){
			dictWord = dict.iterator().next();
		} else {
			return null;
		}
		//if dictWord is one letter off
		if(distanceOne(start, dictWord)){
			ArrayList<String> result = getWordLadderDFS(dictWord, end);
		}
		//if(result == null){
			//there is no path
		//}
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
			
			if(dict.iterator().hasNext()){
				//dictWord = dict.iterator().next();
			} else {
				//return null;
			}
		
		return null; // replace this line later with real return
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		//after finding no ladder put check inside array list
		//print no ladder found
		System.out.println("a " + ladder.size() + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size() - 1));
		for(int i = 0; i < ladder.size(); i++){
			System.out.println(ladder.get(i));
		}
	}
	// TODO
	// Other private static methods here
}
