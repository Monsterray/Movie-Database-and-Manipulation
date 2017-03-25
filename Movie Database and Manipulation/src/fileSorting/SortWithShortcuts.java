/**
 * 
 */
package fileSorting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import databasing.Movie;

/**
 * @author Monsterray
 *
 */
public class SortWithShortcuts {
	static long startTime;		// Start time for how long the program is running
	static long endTime;		// End time for program run time

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SortWithShortcuts sws = new SortWithShortcuts();
		sws.run();
		
		endTime = System.currentTimeMillis();
		long totalTime = (endTime - startTime) / 1000;
		System.out.println("Finished in " + totalTime + "seconds!");

	}
	
	/**
	 * Create an Internet shortcut
	 * 
	 * @param where    location of the shortcut
	 * @param target   location of target file/url
	 * @param icon     URL (ex. http://www.server.com/favicon.ico)
	 * @param iconIndex 
	 * 
	 * @throws IOException
	 */
	public static void createInternetShortcut(String where, String target, String icon, int iconIndex) 
			throws IOException{
		FileWriter fw = new FileWriter(where);
		fw.write("[InternetShortcut]\n");
		fw.write("URL=\"" + target + "\"\n");
		if (!icon.equals(""))  {
			fw.write("IconFile=" + icon + "\n");
			fw.write("IconIndex=" + iconIndex + "\n");
		}
		fw.flush();
		fw.close();
	}
	  
	private void run() {
		Scanner in = new Scanner(System.in);
		//		System.out.println("Input the parent folder of your movies to be sorted: ");
//		String locationMovies = in.nextLine();
//		System.out.println("Input the parent folder of the shortcuts to be sorted into: ");
//		String locationShortcuts = in.nextLine();
		System.out.println("Running...");
		startTime = System.currentTimeMillis();
		
		String locationMovies = "E:\\Videos\\Movies";
		String locationShortcuts = "E:\\Videos\\Java Sorted Movies";
		File movieFolder = new File(locationMovies);
		File shortcutFolder = new File(locationShortcuts);
		File[] movieFiles = movieFolder.listFiles();
		
		for(File currentFile : movieFiles){
//			if(currentFile.getName().toLowerCase().equals("Batman The Dark Knight Returns [1[2012]+2[2013]] 2013.mp4".toLowerCase())){}else{
				String movieName = currentFile.getName().substring(0, currentFile.getName().lastIndexOf('.'));
				if(movieName.contains("[")){
					movieName = movieName.substring(0, movieName.indexOf("[")) + movieName.substring(movieName.lastIndexOf("]") + 1, movieName.length());
				}
				
				System.out.println("Looking for data on " + movieName);
				Movie movie = new Movie(movieName);
				List<String> genreLocations = new LinkedList<String>();
				for(String genre : movie.getGenres()){
					File genreFolder = new File(shortcutFolder.getAbsolutePath() + "/" + genre);
					if(!genreFolder.exists()){
						genreFolder.mkdirs();
					}
					try {
						//IconFile=C:\Windows\System32\shell32.dll
						//IconIndex=115
	
						createInternetShortcut(genreFolder.getAbsolutePath() + "/" + movie.getTitle() + ".url", currentFile.getAbsolutePath(), "C:\\Windows\\System32\\shell32.dll", 115);
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
					genreLocations.add(genreFolder.getAbsolutePath());
				}
	//			try {
	//				shortcutMakerMaker(currentFile.getAbsolutePath(), shortcutFolder.getAbsolutePath(), genreLocations);
	//			} catch (IOException e) {
	//				e.printStackTrace();
	//			}
//			}
		}
		in.close();	
	}	

//	public void shortcutMakerMaker(String targetFileLocation, String shortcutLocation, List<String> genreLocations) throws IOException{
//		System.out.println("targetFileLocation = " + targetFileLocation);
//		System.out.println("shortcutLocation = " + shortcutLocation);
//		String title = targetFileLocation.substring(targetFileLocation.lastIndexOf("\\") + 1, targetFileLocation.lastIndexOf("."));
//		String singleString = "";
//		int i = 0;
//		for(String loc : genreLocations){
//			if(i != 0){
//				singleString = singleString + " " + loc;
//			}else{
//				singleString = loc;
//			}
//			i++;
//		}
//		System.out.println("single String =" + singleString);
//		
//		String combinedArgs = "\"" + title + "\" \"" + targetFileLocation + "\" \"" + singleString + "\"";
//		System.out.println("combinedArgs =" + combinedArgs);
//		Process p =  Runtime.getRuntime().exec("cmd /c start ShortcutMaker.bat " + combinedArgs);
//
//		List<String> cmdAndArgs = Arrays.asList("cmd", "/c", "start", "ShortcutMaker.bat", title, targetFileLocation, singleString);
////		File dir = new File("C:/Program Files/salesforce.com/Data Loader/cliq_process/upsert");
//
//		ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
//		pb.directory(null);
//		Process p2 = pb.start();
//	}
	
}
