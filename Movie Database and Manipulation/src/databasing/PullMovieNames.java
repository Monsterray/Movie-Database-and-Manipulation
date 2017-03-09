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

	private String fileNameIn;
	private Writer writer;
	private static long startTime;
	private static long endTime;
	private static long totalTime;
	private String fileTitle = "Movie Titles";
	
	/**
	 * This what runs when the program is started. All this does is start the run() method that actually starts the program up.
	 * @param args input from when the program was started
	 */
	public static void main(String[] args) {
		System.out.println("Initializing...");
		PullMovieNames pullMovieNames = new PullMovieNames();

		pullMovieNames.run();
		
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime);
		System.out.println("Finished in " + totalTime + " miliseconds!");
		System.out.println("Finished in " + totalTime / 1000 + " miliseconds!");
	}

	/**
	 * This is the method that does all of the hard work for the entire program.
	 */
	public void run() {
		Scanner in = new Scanner(System.in);
		System.out.println("Input file location: ");
		fileNameIn = in.nextLine();

		System.out.println("Running...");
		startTime = System.currentTimeMillis();
		
		Map<String, Integer> movies = new HashMap<String, Integer>();
		File currentFolder = new File(fileNameIn);

		fillMapInfo(currentFolder, movies);
		try {
			sortMovies(movies);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in.close();
	}
	
	/**
	 * Finds movies and adds their name and year released into a file at location
	 * @param location String, is the path of the parent folder to be searched
	 */
	public void fillMapInfo(File parentFolder, Map<String, Integer> map) {	// format is "Name [extra info] year"
		File[] allFiles = parentFolder.listFiles();
		for (int i = 0; i < allFiles.length; i++) { // In the middle of changing this to only grab the info and adding it to a map to be 
//													   sorted and then I need to add a way to write the data to files
			String extension = allFiles[i].getName().substring(allFiles[i].getName().lastIndexOf('.'), allFiles[i].getName().length());
			String name = allFiles[i].getName().substring(0, allFiles[i].getName().length() - 9);
			int year = Integer.parseInt(allFiles[i].getName().substring(name.length() + 1, allFiles[i].getName().length() - 4));
			if (allFiles[i].isFile() && !extension.equalsIgnoreCase(".db")) {
				try {
					map.put(name, year);
				} catch (Exception e) {
					System.out.println("Exception");
				}
			} else if (allFiles[i].isDirectory()) {
				fillMapInfo(allFiles[i], map);
			}
		}
	}
	
	/**
	 * Sorts the movies in the Map by title and then by year and puts them into two different files
	 * @param movies Map<String, Integer>, is the Map that is full of all the movies and the years they came out
	 * @throws IOException 
	 */
	public void sortMovies(Map<String, Integer> movies) throws IOException{
		File statText = new File("./" + fileTitle + " Sorted By Title.txt");
		FileOutputStream is = new FileOutputStream(statText);
		OutputStreamWriter osw = new OutputStreamWriter(is);
		writer = new BufferedWriter(osw);
		
		Map<String, Integer> sortedByTitleMovies = sortByKey(movies);
		for (Entry<String, Integer> entry : sortedByTitleMovies.entrySet()){
			writer.write(entry.getKey() + " " + entry.getValue() + "\n");
		}
		writer.close();
		
		writer = new BufferedWriter(
					new OutputStreamWriter(
					new FileOutputStream(
					new File("./" + fileTitle + " Sorted By Year.txt"))));
		Map<String, Integer> sortedByYearMovies = sortByValue(movies);
		for (Entry<String, Integer> entry : sortedByYearMovies.entrySet()){
			writer.write(entry.getValue() + " " + entry.getKey() + "\n");
		}
		
		System.out.println(movies.size() + " movies found, sorted, and writen to files!");
		writer.close();
	}

	/**
	 * Takes in a Map and puts it into a tree map which automatically sorts the map by key
	 * @param unsortMap the map to be sorted
	 */
	private <k, v> Map<k, v> sortByKey(Map<k, v> unsortMap) {
		Map<k, v> sortedMap = new TreeMap<k, v>(unsortMap);

		
		return sortedMap;
	}
	
	/**
	 * Takes in a Map and puts it into a tree map which automatically sorts the map by key
	 * @param unsortMap the map to be sorted
	 */
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
}
