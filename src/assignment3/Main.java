/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Aditya Kharosekar
 * amk3587
 * 16465
 * James Creamer
 * jec3875
 * 16475
 * Slip days used: <0>
 * Git URL: https://github.com/JamesECreamer/WordLadder
 * Fall 2016
 */


package assignment3;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static Set<String> dict = makeDictionary();
	static Set<Word> wordDictionary = new HashSet<Word>();
	
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
		ArrayList<String> bob = getWordLadderDFS("STONE", "MONEY");
        System.out.println(bob);
        // TODO methods to read in words, output ladder
	}
	
	public static void dictWordDictionary(Set<String> dictionary){
		Iterator<String> iterator = dictionary.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            wordDictionary.add(new Word(s));
        }
	}
	
	public static void initialize() {
		dictWordDictionary(dict);
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> parameters = new ArrayList<String>(2);
		parameters.add(keyboard.next());
		if(parameters.get(0).equals("/quit")){
			ArrayList<String> empty = new ArrayList<String>();
			return empty;
		}
		parameters.add(keyboard.nextLine());
		return parameters;
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
	
	/*
	PSEUDOCODE
    If (start==end) {
        you're done. return start
    }
    Find all mutants of start. Store into ArrayList
    If (num of new mutants==0) {
        you have reached a dead end. return null
    }
    for (int i=0; i<mutants.length; i++) {
        arraylist<String> result = Recursively call on (mutant[i], end)
        if (result!=null) {
            Return an arraylist which consists of - adding start to the front of result
    }   
     */
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		
		/*  if you have reached end
		 *  return start
		 *  base case
		 */
		if(start == end){
			ArrayList<String> result = new ArrayList<String>();
			result.add(start);
			return result;
		}
		
		ArrayList<Word> mutants = new ArrayList<Word>();
		Iterator<Word> iterator = wordDictionary.iterator();
		int count = 0;
        while (iterator.hasNext()) {
            Word nextWord = iterator.next();
            if(nextWord.visited == false){
            	if(differByOne(nextWord.word, start)){
	            	mutants.add(nextWord);
	            	nextWord.visited = true;
	            	count += 1;
            	}
            }
        }
        
        //if no mutants found
        if(count == 0){
        	return null;
        }
        
        for (int i=0; i<mutants.size(); i++) {
        	ArrayList<String> result = getWordLadderDFS(mutants.get(i).word, end);
        	if(result != null){
        		result.add(0, start);
        		return result;
        	}
        }
		
        return null; //no path has been found
	}

    /**
     * We use a queue, and each element of this queue will be an ArrayList.
     * Each element of this queue will be a possible path from the start word to the end word
     * @param start
     * @param end
     * @return
     */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		Set<String> dict = makeDictionary();
        ArrayList<String> path = new ArrayList<>();
        path.add(start);
        Queue<ArrayList<String>> queue = new LinkedList<>();
        queue.add(path);

        /*As we are going through the entire dictionary, we don't want to hit the start word again */
        dict.remove(start);

        while (!queue.isEmpty() && !getLastWord(queue.peek()).equals(end)) {//while queue is not empty and
                                                                            //head element does not contain end word

            ArrayList<String> ladder = queue.remove();

            if (getLastWord(ladder).equals(end)) {  //we have found destination word
                return ladder;
            }
            Iterator<String> iterator = dict.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();

                if (differByOne(string, getLastWord(ladder))) {
                    ArrayList<String> list = new ArrayList<>(ladder);
                    list.add(string);

                    queue.add(list);

                    iterator.remove();
                }
            }

        }

        if (!queue.isEmpty()) {
            return queue.peek();
        }
        else {
            return null;
        }
	}

    /**
     * Returns last word of current arraylist. Useful to see if you have reached end word
     * @param s arraylist containing current word ladder
     * @return last word of arraylist
     */
    public static String getLastWord(ArrayList<String> s) {
        int i = s.size();
        return s.get(i-1);
    }

    /**
     * Checks if two words are off by 1 character
     * @param word word found in dictionary
     * @param ladderLast word at the end of current path
     * @return true if words are off by 1 character
     */
    public static boolean differByOne(String word, String ladderLast) {
        if (word.length()!=ladderLast.length()) {
            return false;
        }
        int count = 0;
        for (int i=0; i<word.length(); i++) {
            if (word.charAt(i)!=ladderLast.charAt(i)) {
                count++;
            }
        }
        return (count==1);
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
		
	}
	// TODO
	// Other private static methods here
}
