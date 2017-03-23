/**
 * 
 */
package databasing;

import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

/**
 * @author Monsterray
 *
 */
public class TestingIMBDConnection {
	/**
	 * The normal title of the movie
	 */
	private String movieTitle;	// Got it
	/**
	 * The rating of the movie on IMDb
	 */
	private double movieRating;	// Got it
	/**
	 * A list of the genres for this movie
	 */
	private List<String> movieGenres;	// Got it
	/**
	 * The content rating assigned by the MPAA
	 */
	private String contentRating;	// Got it
	/**
	 * The runtime of the entire movie
	 */
	private String runTime;	// Got it
	/**
	 * The date the movie was released (I think this is based on the country 
	 * you are in is what will display on the website)
	 */
	private String releaseDate;	// Got it
	/**
	 * The picture of the movies poster hosted by amazon
	 */
	private Image moviePoster;
	/**
	 * A short summary of the movie
	 */
	private String summary;	// Got it
	/**
	 * The director of the movie
	 */
	private String director;
	/**
	 * A list of all of the writers (also includes writers of things the movie was based on)
	 */
	private List<String> writers;
	/**
	 * The rating/score given by Metacritic
	 */
	private int metascore;
	
	private static String movieTitlePatched = "47 ronin 2013".replace(" ", "+");
	private static String site = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + movieTitlePatched + "&s=all";	// http://www.imdb.com/find?ref_=nv_sr_fn&q=12+Years+a+Slave+2013&s=all

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestingIMBDConnection tic = new TestingIMBDConnection();
		tic.run();
		
//		tic.findMovie(site);

	}

	private void run() {	// Make it so it makes files with the information for each movie
		Scanner in = new Scanner(System.in);
//		System.out.println("Input file location: ");
//		String fileNameIn = in.nextLine();
//		String fileNameIn = "47 ronin 2013";
//		Movie movie = new Movie(fileNameIn);
//		for(String s : movie.getMovieGenres()){
//			System.out.println(s);
//		}
		findMovie(site);
		in.close();
	}
	
	public void findMovie(String site){
		movieTitle = site.substring(0, site.length() - 4);
		try{
			UserAgent userAgent = new UserAgent();	//create new userAgent (headless browser).
			userAgent.visit(site);//visit a url
			Element searchPage = userAgent.doc;

			Elements oddResults = searchPage.findEvery("<tr class=\"findResult odd\">"); // Find all tr's who's class matches "findResult odd"
			Elements evenResults = searchPage.findEvery("<tr class=\"findResult even\">"); // Find all tr's who's class matches "findResult even"
			Elements interleaved = interleaveElements(oddResults, evenResults);
			String movieAddress = null;
			System.out.println("\n");
			System.out.println("Interleaved Test: ");
			for(Element e : interleaved){	// (TV Series) (TV Episode)
				if(!e.innerText().contains("(TV Episode)") || !e.innerText().contains("(TV Series)")){
					movieAddress = e.findFirst("<a href>").getAt("href");
					System.out.print("Address of movie on IMDb:\n");
					System.out.println(movieAddress);
					break;
				}
//				System.out.println(e.outerHTML());
			}
			
//			System.out.println("\n");
//			for(Element e : oddResults){
//				
////				System.out.println("First Result: \n" + e.innerHTML());
////				Element resultText = e.findFirst("<td class=\"result_text\">");
////				System.out.println("First Result Inner HTML: \n" + resultText.innerHTML());
//				
////				String innerText = resultText.innerText();
////				System.out.println("First Result Inner HTML: \n" + innerText);
//				
//				movieAddress = e.findFirst("<a href>").getAt("href");
//				System.out.print("Address of movie on IMDb:\n");
//				System.out.println(movieAddress);
//			}
			
			userAgent.visit(movieAddress);//visit a url
			Element moviePage = userAgent.doc;
			this.movieRating = new Double(moviePage.findFirst("<span itemprop=\"ratingValue\">").innerText());	// find the rating of the movie on IMDb
			Element outerPublishDate = moviePage.findFirst("<meta itemprop=\"datePublished\">");
			String publishHTML = outerPublishDate.outerHTML();
			releaseDate = publishHTML.substring(publishHTML.length() - 12, publishHTML.length() - 2);
			Element outerContentRaiting = moviePage.findFirst("<meta itemprop=\"contentRating\">");	// content rating
			String content = outerContentRaiting.getAt("content");
			contentRating = content.substring(content.lastIndexOf("/") + 1, content.length());
//			contentRating = content.substring(content.length() - 1, content.length());
			Element outerDuration = moviePage.findFirst("<time itemprop=\"duration\">");
			runTime = outerDuration.innerText().trim();
			Element outerSummary = moviePage.findFirst("<div class=\"summary_text\">");
			summary = outerSummary.innerText().trim();
			Element outerDirector = moviePage.findFirst("<span itemprop=\"director\">");
			director = outerDirector.innerText().trim();
			Elements ElementalWriters = moviePage.findEvery("<span itemprop=\"creator\" itemtype=\"http://schema.org/Person\">");
			writers = new LinkedList<String>();
	      	int size = writers.size();
	      	int counter = 1;
	      	System.out.println("Writers: \n");
			for(Element e : ElementalWriters){
				writers.add(e.innerText().trim());
	      		System.out.print(e.innerText().trim());
	      		if(counter < size){
	      			System.out.print(", ");
	      		}
	      		counter++;
			}
			Element outerMetascore = null;
			try {
				outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_favorable titleReviewBarSubItem\">");	// Find the Metacritic score ( a little harder than previously thought)
			} catch (JauntException je) {
				try {
					outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_mixed titleReviewBarSubItem\">");	// Find the Metacritic score ( a little harder than previously thought)
				} catch (Exception e1) {
					try {
						outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_unfavorable titleReviewBarSubItem\">");
					} catch (JauntException je2) {
							
					}
				}
			}
			metascore = Integer.parseInt(outerMetascore.innerText().trim());
			

	      	System.out.println("\n");
			System.out.println("Metascore : " + metascore);
			System.out.println("Director : " + director);
			System.out.println("Summary : " + summary);
			System.out.println("Content Raiting: " + runTime);
			System.out.println("Content Raiting: " + contentRating);
			System.out.println("Publish date: " + releaseDate);
			System.out.println("The movie rating is: " + movieRating);
			System.out.println("Genres: ");
			
			Elements genres = userAgent.doc.findEvery("<span itemprop=\"genre\">");
			movieGenres = new LinkedList<String>();
	      	size = genres.size();
	      	counter = 1;
			for(Element e : genres){
	      		movieGenres.add(e.innerText());
	      		System.out.print(e.innerText());
	      		if(counter < size){
	      			System.out.print(", ");
	      		}
	      		counter++;
	      	}
			
	    }catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
	    	System.err.println(e);
	    }
	}

	private Elements interleaveElements(Elements oddResults, Elements evenResults) {	// Possibly don't use iterator?
		Elements interleavedElements = new Elements();
		Iterator<Element> oddElementIter = oddResults.iterator();
		Iterator<Element> evenElementIter = evenResults.iterator();
		while(oddElementIter.hasNext() || evenElementIter.hasNext()){
			if(oddElementIter.hasNext()){
				interleavedElements.addChild(oddElementIter.next());
			}
			if(evenElementIter.hasNext()){
				interleavedElements.addChild(evenElementIter.next());
			}
		}
		return (Elements) interleavedElements;
	}

}
