package databasing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Monsterray
 *
 */
public class PullMovieNames {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Initializing...");
		PullMovieNames pullMovieNames = new PullMovieNames();

		try {
			pullMovieNames.run();
		} catch (IOException e) {e.printStackTrace();}
		
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime);
		System.out.println("Finished in " + totalTime + " miliseconds!");
		System.out.println("Finished in " + totalTime / 1000 + " miliseconds!");
	}

	private String fileNameIn;
	private Writer w;
	private static long startTime;
	private static long endTime;
	private static long totalTime;
	private String fileTitle = "Movie Titles";

	HashMap<Integer, String> movies = new HashMap<Integer, String>();
	
	public void sortMovies(String location){
		HashMap<String, Integer> movies = new HashMap<String, Integer>();
		File currentFolder = new File(location);
		File[] allFiles = currentFolder.listFiles();

		for (int i = 0; i < allFiles.length; i++) {
			String extension = allFiles[i].getName().substring(allFiles[i].getName().lastIndexOf('.'), allFiles[i].getName().length());
			String name = allFiles[i].getName().substring(0, allFiles[i].getName().length() - 9);
			int year = Integer.parseInt(allFiles[i].getName().substring(name.length() + 1, allFiles[i].getName().length() - 4));
			if (allFiles[i].isFile() && !extension.equalsIgnoreCase(".db")) {
				try {
					movies.put(name, year);
				} catch (Exception e) {
					System.out.println("Exception");
				}
			} else if (allFiles[i].isDirectory()) {
				outputInfo(allFiles[i].getAbsolutePath());
			}
		}

		Map<String, Integer> sortedByTitleMovies = sortByKey(movies);
		for (Entry<String, Integer> entry : sortedByTitleMovies.entrySet()){
		    System.out.println("\t" + entry.getKey() + "/ " + entry.getValue());
		}
		
		Map<String, Integer> sortedByYearMovies = sortByValue(movies);
		for (Entry<String, Integer> entry : sortedByYearMovies.entrySet()){
		    System.out.println("\t\t" + entry.getKey() + "/ " + entry.getValue());
		}
		
		System.out.println(movies.size() + " movies!");
	}
	
	private Map<String, Integer> sortByKey(Map<String, Integer> unsortMap) {
		Map<String, Integer> sortedMap = new TreeMap<String, Integer>(unsortMap);

		
		return sortedMap;
	}
	
    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) 
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
	
	
	/**
	 * Finds movies and adds their name and year released into a file at location
	 * @param location String, is the path of the parent folder to be searched
	 */
	public void outputInfo(String location) {	// format is "Name [extra info] year"
		File currentFolder = new File(location);
		File[] allFiles = currentFolder.listFiles();

		for (int i = 0; i < allFiles.length; i++) {
			String extension = allFiles[i].getName().substring(allFiles[i].getName().lastIndexOf('.'), allFiles[i].getName().length()); // Grabs the extension to find thumb files so they can be ignored
//			System.out.println("extension = " + extension);
			if (allFiles[i].isFile() && !extension.equalsIgnoreCase(".db")) { // This is where thumb files get ignored
				try {
					String name = allFiles[i].getName().substring(0, allFiles[i].getName().length() - 8);
//					System.out.println("name = " + name);
					int year = Integer.parseInt(allFiles[i].getName().substring(name.length(), allFiles[i].getName().length() - 4));
//					System.out.println("year = " + year);
					w.write(name + " " + year + "\n");
				} catch (Exception e) {
					System.out.println("Exception");
				}
			} else if (allFiles[i].isDirectory()) { // If the program finds a folder it will recurse down into the folder to find movies
				outputInfo(allFiles[i].getAbsolutePath());
			}
		}
	}
	
	/**
	 * 
	 */
	public void run() throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("Input file location: ");
		fileNameIn = in.nextLine();

		System.out.println("Running...");
		startTime = System.currentTimeMillis();

		File statText = new File("./" + fileTitle + ".txt");
		FileOutputStream is = new FileOutputStream(statText);
		OutputStreamWriter osw = new OutputStreamWriter(is);
		w = new BufferedWriter(osw);
		
		outputInfo(fileNameIn);
		sortMovies(fileNameIn);
		
		w.close();
		in.close();
	}
}
