/**
 * 
 */
package databasing;

import java.util.Scanner;

/**
 * @author Monsterray
 *
 */
public class TestingIMBDConnection {
/*	*//**
	 * The normal title of the movie
	 *//*
	private String movieTitle;	// Got it
	*//**
	 * The rating of the movie on IMDb
	 *//*
	private double movieRating;	// Got it
	*//**
	 * A list of the genres for this movie
	 *//*
	private List<String> movieGenres;	// Got it
	*//**
	 * The content rating assigned by the MPAA
	 *//*
	private String contentRating;	// Got it
	*//**
	 * The runtime of the entire movie
	 *//*
	private String runTime;	// Got it
	*//**
	 * The date the movie was released (I think this is based on the country 
	 * you are in is what will display on the website)
	 *//*
	private String releaseDate;	// Got it
	*//**
	 * The picture of the movies poster hosted by amazon
	 *//*
	private Image moviePoster;
	*//**
	 * A short summary of the movie
	 *//*
	private String summary;	// Got it
	*//**
	 * The director of the movie
	 *//*
	private String director;
	*//**
	 * A list of all of the writers (also includes writers of things the movie was based on)
	 *//*
	private List<String> writers;
	*//**
	 * The rating/score given by Metacritic
	 *//*
	private int metascore;
	
	private static String movieTitlePatched = "12 Years a Slave 2013".replace(" ", "+");
	private static String site = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + movieTitlePatched + "&s=all";	// http://www.imdb.com/find?ref_=nv_sr_fn&q=12+Years+a+Slave+2013&s=all
*/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestingIMBDConnection tic = new TestingIMBDConnection();
		tic.run();
		
//		tic.findMovie(site);

	}

	private void run() {
		Scanner in = new Scanner(System.in);
		System.out.println("Type the name of a movie to search: ");
		String fileNameIn = in.nextLine();
		Movie movie = new Movie(fileNameIn);
		for(String s : movie.getMovieGenres()){
			System.out.println(s);
		}
		in.close();
	}
	
//	public void findMovie(String site){
//		movieTitle = site.substring(0, site.length() - 4);
//		try{
//			UserAgent userAgent = new UserAgent();	//create new userAgent (headless browser).
//			userAgent.visit(site);//visit a url
//			
//			Element firstResult = userAgent.doc.findFirst("<tr class=\"findResult odd\">"); // Find first div who's class matches "pm"
////			System.out.println("First Result: \n" + firstResult.innerHTML());
//			Element resultText = firstResult.findFirst("<td class=\"result_text\">");
////			System.out.println("First Result Text: \n" + resultText.innerHTML());
//			String movieAddress = firstResult.findFirst("<a href>").getAt("href");
////			System.out.print("Address of movie on IMDb:\n");
////			System.out.println(movieAddress);
//			
//			userAgent.visit(movieAddress);//visit a url
//			Element moviePage = userAgent.doc;
//			this.movieRating = new Double(moviePage.findFirst("<span itemprop=\"ratingValue\">").innerText());	// find the rating of the movie on IMDb
//			Element outerPublishDate = moviePage.findFirst("<meta itemprop=\"datePublished\">");
//			String publishHTML = outerPublishDate.outerHTML();
//			releaseDate = publishHTML.substring(publishHTML.length() - 12, publishHTML.length() - 2);
//			Element outerContentRaiting = moviePage.findFirst("<meta itemprop=\"contentRating\">");
//			String content = outerContentRaiting.getAt("content");
//			contentRating = content.substring(content.length() - 1, content.length());
//			Element outerDuration = moviePage.findFirst("<time itemprop=\"duration\">");
//			runTime = outerDuration.innerText().trim();
//			Element outerSummary = moviePage.findFirst("<div class=\"summary_text\">");
//			summary = outerSummary.innerText().trim();
//			Element outerDirector = moviePage.findFirst("<span itemprop=\"director\">");
//			director = outerDirector.innerText().trim();
//			Elements ElementalWriters = moviePage.findEvery("<span itemprop=\"creator\" itemtype=\"http://schema.org/Person\">");
//			writers = new LinkedList<String>();
//	      	int size = writers.size();
//	      	int counter = 1;
//	      	System.out.println("Writers: \n");
//			for(Element e : ElementalWriters){
//				writers.add(e.innerText().trim());
//	      		System.out.print(e.innerText().trim());
//	      		if(counter < size){
//	      			System.out.print(", ");
//	      		}
//	      		counter++;
//			}
//			Element outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_favorable titleReviewBarSubItem\">");	// <div class="metacriticScore score_favorable titleReviewBarSubItem">
//			metascore = Integer.parseInt(outerMetascore.innerText().trim());
//			
//
//	      	System.out.println("\n");
//			System.out.println("Metascore : " + metascore);
//			System.out.println("Director : " + director);
//			System.out.println("Summary : " + summary);
//			System.out.println("Content Raiting: " + runTime);
//			System.out.println("Content Raiting: " + contentRating);
//			System.out.println("Publish date: " + releaseDate);
//			System.out.println("The movie rating is: " + movieRating);
//			System.out.println("Genres: ");
//			
//			Elements genres = userAgent.doc.findEvery("<span itemprop=\"genre\">");
//			movieGenres = new LinkedList<String>();
//	      	size = genres.size();
//	      	counter = 1;
//			for(Element e : genres){
//	      		movieGenres.add(e.innerText());
//	      		System.out.print(e.innerText());
//	      		if(counter < size){
//	      			System.out.print(", ");
//	      		}
//	      		counter++;
//	      	}
//			
//	    }catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
//	    	System.err.println(e);
//	    }
//	}

}
