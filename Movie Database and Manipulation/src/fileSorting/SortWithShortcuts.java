/**
 * 
 */
package fileSorting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import databasing.Movie;

/**
 * @author Monsterray
 *
 */
public class SortWithShortcuts {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SortWithShortcuts sws = new SortWithShortcuts();
		sws.run();

	}
	
	/**
	 * Create an Internet shortcut
	 * @param name     name of the shortcut
	 * @param where    location of the shortcut
	 * @param target   URL 
	 * @param icon     URL (ex. http://www.server.com/favicon.ico)
	 * @throws IOException
	 */
	public static void createInternetShortcut(String where, String target, String icon) 
			throws IOException{
		FileWriter fw = new FileWriter(where);
		fw.write("[InternetShortcut]\n");
		fw.write("URL=" + target + "\n");
		if (!icon.equals(""))  {
			fw.write("IconFile=" + icon + "\n");  
		}
		fw.flush();
		fw.close();
	}
	  
	private void run() {
		Scanner in = new Scanner(System.in);
		System.out.println("Input the parent folder of your movies to be sorted: ");
		String locationMovies = in.nextLine();
		System.out.println("Input the parent folder of the shortcuts to be sorted into: ");
		String locationShortcuts = in.nextLine();
		File movieFolder = new File(locationMovies);
		File shortcutFolder = new File(locationMovies);
		File[] movieFiles = movieFolder.listFiles();
		
		for(File currentFile : movieFiles){
			Movie movie = new Movie(locationMovies);
			for(String genre : movie.getMovieGenres()){
				File genreFolder = new File(shortcutFolder.getAbsolutePath() + "/" + genre);
				if(!genreFolder.exists()){
					genreFolder.mkdirs();
				}
//				createInternetShortcut(genreFolder, currentFile, );
			}
		}
		in.close();
	}

}
