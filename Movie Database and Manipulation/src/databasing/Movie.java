/**
 * 
 */
package databasing;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

/**
 * @author Monsterray
 *
 */
public class Movie {
	/**
	 * The normal title of the movie
	 */
	private String movieTitle;
	/**
	 * The rating of the movie on IMDb
	 */
	private double movieRating;
	/**
	 * A list of the genres for this movie
	 */
	private List<String> movieGenres;
	/**
	 * The content rating assigned by the MPAA
	 */
	private String contentRating;
	/**
	 * The runtime of the entire movie
	 */
	private String runTime;
	/**
	 * The date the movie was released (I think this is based on the country 
	 * you are in is what will display on the website)
	 */
	private String releaseDate;
	/**
	 * The picture of the movies poster hosted by amazon
	 */
	private Image moviePoster;
	/**
	 * A short summary of the movie
	 */
	private String summary;
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
	
	/**
	 * This is the main constructor of the class that takes in a String title that is uses on IMDb
	 * to search for information to put into this class to be used by the user.
	 * 
	 *	@param title This is the normal name of the movie to get details about
	 */
	public Movie(String title){
		this.movieTitle = title.substring(0, title.length() - 4);
		title = title.replace(" ", "+");
		try{
			UserAgent userAgent = new UserAgent();	//create new userAgent (headless browser).
			userAgent.visit(title);	//visit a url
			Element firstResult = userAgent.doc.findFirst("<tr class=\"findResult odd\">"); // Find the first result on the search page
			String movieAddress = firstResult.findFirst("<a href>").getAt("href");	// Get the first hyperlink that contains the movie we are looking for
			
			
			userAgent.visit(movieAddress);//visit a url
			Element moviePage = userAgent.doc;
			this.movieRating = new Double(moviePage.findFirst("<span itemprop=\"ratingValue\">").innerText());	// find the rating of the movie on IMDb
			Element outerPublishDate = moviePage.findFirst("<meta itemprop=\"datePublished\">");
			String publishHTML = outerPublishDate.outerHTML();
			releaseDate = publishHTML.substring(publishHTML.length() - 12, publishHTML.length() - 2);
			Element outerContentRaiting = moviePage.findFirst("<meta itemprop=\"contentRating\">");
			String content = outerContentRaiting.getAt("content");
			contentRating = content.substring(content.length() - 1, content.length());
			Element outerDuration = moviePage.findFirst("<time itemprop=\"duration\">");
			runTime = outerDuration.innerText().trim();
			Element outerSummary = moviePage.findFirst("<div class=\"summary_text\">");
			summary = outerSummary.innerText().trim();
			Element outerDirector = moviePage.findFirst("<span itemprop=\"director\">");
			director = outerDirector.innerText().trim();
			Elements ElementalWriters = moviePage.findEvery("<span itemprop=\"creator\" itemtype=\"http://schema.org/Person\">");
			writers = new LinkedList<String>();
			for(Element e : ElementalWriters){
				writers.add(e.innerText().trim());
			}
			Element outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_favorable titleReviewBarSubItem\">");	// <div class="metacriticScore score_favorable titleReviewBarSubItem">
			metascore = Integer.parseInt(outerMetascore.innerText().trim());
			Elements genres = userAgent.doc.findEvery("<span itemprop=\"genre\">");
			movieGenres = new LinkedList<String>();
			for(Element e : genres){
	      		movieGenres.add(e.innerText());
	      	}
	    }catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
	    	System.err.println(e);
	    }
	}
	
	/**
	 * Method that gets the title of the movie
	 * 
	 *	@return The string of the movie or null
	 */
	public String getMovieTitle() {
		return movieTitle;
	}

	/**
	 * Method that gets the rating of the movie
	 * 
	 *	@return The double of the movie rating or null
	 */
	public double getMovieRating() {
		return movieRating;
	}

	/**
	 * Method that gets the List of genres for the movie
	 * 
	 *	@return A List of genres or null
	 */
	public List<String> getMovieGenres() {
		return movieGenres;
	}

	/**
	 * Method that gets the content rating of the movie
	 * 
	 *	@return The string of the content rating or null
	 */
	public String getContentRating() {
		return contentRating;
	}

	/**
	 * Method that gets the runtime of the movie
	 * 
	 *	@return The string representation of the runtime or null
	 */
	public String getRunTime() {
		return runTime;
	}

	/**
	 * Method that gets the release date of the movie
	 * 
	 *	@return The string of the release date or null
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Method that gets the Image 
	 * 
	 *	@return The string of the movie or null
	 */
	public Image getMoviePoster() {
		return moviePoster;
	}

	/**
	 * Method that gets the title of the movie
	 * 
	 *	@return The string of the movie or null
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Method that gets the title of the movie
	 * 
	 *	@return The string of the movie or null
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Method that gets the title of the movie
	 * 
	 *	@return The string of the movie or null
	 */
	public List<String> getWriters() {
		return writers;
	}

	/**
	 * Method that gets the title of the movie
	 * 
	 *	@return The string of the movie or null
	 */
	public int getMetascore() {
		return metascore;
	}

}
