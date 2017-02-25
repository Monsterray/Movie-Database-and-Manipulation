package databasing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

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
		totalTime = (endTime - startTime) / 1000;
		System.out.println("Finished in " + totalTime + " seconds!");
	}

	private String fileNameIn;
	@SuppressWarnings("unused")
	private File folder;
	private File statText;
	private FileOutputStream is;
	private OutputStreamWriter osw;
	private Writer w;
	private static long startTime;
	private static long endTime;
	private static long totalTime;
	private String fileTitle = "Movie Titles";
	
	/**
	 * @param location String
	 */
	public void outputInfo(String location) {
		File currentFolder = new File(location);
		File[] allFiles = currentFolder.listFiles();

		for (int i = 0; i < allFiles.length; i++) {
			if (allFiles[i].isFile() && !allFiles[i].getName().substring(allFiles[i].getName().length() - 3, allFiles[i].getName().length()).equalsIgnoreCase(".db")) {
				try {
					w.write(allFiles[i].getName().substring(0, allFiles[i].getName().length() - 4) + "\n");
//					w.write(allFiles[i].getName().substring(0, allFiles[i].getName().length() - 9) + "\t");
//					w.write(allFiles[i].getName().substring(allFiles[i].getName().length() - 9, allFiles[i].getName().length() - 4) + "\n");
				} catch (Exception e) {
					System.out.println("");
				}
			} else if (allFiles[i].isDirectory()) {
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

		folder = new File(fileNameIn);
		statText = new File("./" + fileTitle + ".txt");
		is = new FileOutputStream(statText);
		osw = new OutputStreamWriter(is);
		w = new BufferedWriter(osw);
		
		outputInfo(fileNameIn);
		w.close();
		in.close();
	}
}
