/**
 * 
 */
package databasing;

import java.awt.Image;
import java.util.Iterator;
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
	private String metascore;
	
	/**
	 * This is the main constructor of the class that takes in a String title that is uses on IMDb
	 * to search for information to put into this class to be used by the user.
	 * 
	 *	@param title This is the normal name of the movie to get details about
	 */
	public Movie(String title){
		this.movieTitle = title; // .substring(0, title.length() - 4)
		title = title.replace(" ", "+");
		String title2 = title.replace(" ", "%20");
			//2%20guns%202013
		String site = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + title + "&s=all";
		String moreResultsSite = "http://www.imdb.com/find?q=" + title2 + "&s=tt&ref_=fn_al_tt_mr";
		try{	// 	Need to figure out how to like check the percentage of how similar the titles 
//					are to the search result to figure out if it is right or not
			UserAgent userAgent = new UserAgent();	//create new userAgent (headless browser).
			userAgent.visit(site);	//visit the search result page for the title of the movie
			Element searchPage = userAgent.doc;

			Elements oddResults = searchPage.findEvery("<tr class=\"findResult odd\">"); // Find all tr's who's class matches "findResult odd"
			Elements evenResults = searchPage.findEvery("<tr class=\"findResult even\">"); // Find all tr's who's class matches "findResult even"
			Elements interleaved = interleaveElements(oddResults, evenResults);
			String movieAddress = null;
			for(Element e : interleaved){	// Looks for the first result that doesn't include "(TV Series)" or "(TV Episode)" or "(Video)" and it has to have the same year as the movie title
				boolean isTVShow = e.innerText().contains("(TV Episode)") || e.innerText().contains("(TV Series)");
				boolean hasCorrectYear = e.innerText().contains(title.substring(movieTitle.length() - 4, movieTitle.length()));
//				boolean isVideo = e.innerText().contains("(Video)");
//				if(!isTVShow && !isVideo && hasCorrectYear){
				if(!isTVShow && hasCorrectYear){
					movieAddress = e.findFirst("<a href>").getAt("href");
					System.out.println(movieAddress);
					break;
				}
			}
			if(movieAddress == null){
				userAgent.visit(moreResultsSite);	//visit the search result page for the title of the movie
				searchPage = userAgent.doc;

				oddResults = searchPage.findEvery("<tr class=\"findResult odd\">"); // Find all tr's who's class matches "findResult odd"
				evenResults = searchPage.findEvery("<tr class=\"findResult even\">"); // Find all tr's who's class matches "findResult even"
				interleaved = interleaveElements(oddResults, evenResults);
				for(Element e : interleaved){	// Looks for the first result that doesn't include "(TV Series)" or "(TV Episode)"
					if((!e.innerText().contains("(TV Episode)") || !e.innerText().contains("(TV Series)")) && e.innerText().contains(title.substring(movieTitle.length() - 4, movieTitle.length()))){
						movieAddress = e.findFirst("<a href>").getAt("href");
						break;
					}
				}
			}

			userAgent.visit(movieAddress);//visit a url
			Element moviePage = userAgent.doc;
			this.movieRating = new Double(moviePage.findFirst("<span itemprop=\"ratingValue\">").innerText());	// Find the rating of the movie on IMDb
			Element outerPublishDate = moviePage.findFirst("<meta itemprop=\"datePublished\">");
			String publishHTML = outerPublishDate.outerHTML();
			releaseDate = publishHTML.substring(publishHTML.length() - 12, publishHTML.length() - 2);
			Element outerContentRaiting = null;
			try {
				outerContentRaiting = moviePage.findFirst("<meta itemprop=\"contentRating\">");
			} catch (JauntException je) {
				
			}
			String content;
			if(outerContentRaiting == null){
				content = "No content rating";
			}else{
				content = outerContentRaiting.getAt("content");
			}
			contentRating = content.substring(content.length() - 1, content.length());
			Element outerDuration = moviePage.findFirst("<time itemprop=\"duration\">");	// Find the run time of the movie
			runTime = outerDuration.innerText().trim();
			Element outerSummary = moviePage.findFirst("<div class=\"summary_text\">");	// Find the summary of the movie
			summary = outerSummary.innerText().trim();
			Element outerDirector = moviePage.findFirst("<span itemprop=\"director\">");	// Find the director of the movie
			director = outerDirector.innerText().trim();
			Elements ElementalWriters = moviePage.findEvery("<span itemprop=\"creator\" itemtype=\"http://schema.org/Person\">");	// Find the writers of the movie
			writers = new LinkedList<String>();
			for(Element e : ElementalWriters){
				writers.add(e.innerText().trim());
			}
			Element outerMetascore = null;
			try {
				outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_favorable titleReviewBarSubItem\">");	// Find the Metacritic favorable score (if it has it)
			} catch (JauntException je) {
				try {
					outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_mixed titleReviewBarSubItem\">");	// Find the Metacritic mixed score (if it has it)
				} catch (JauntException e1) {
					try {
						outerMetascore = moviePage.findFirst("<div class=\"metacriticScore score_unfavorable titleReviewBarSubItem\">");	// Find the Metacritic unfavorable score (if it has it)
					} catch (JauntException je2) {
						outerMetascore = null;
					}
				}
			}
			if(outerMetascore == null){
				metascore = "No metacritic score";
			}else{
				metascore = outerMetascore.innerText().trim();
			}
			Elements genres = userAgent.doc.findEvery("<span itemprop=\"genre\">");
			movieGenres = new LinkedList<String>();
			for(Element e : genres){
	      		movieGenres.add(e.innerText());
	      	}
	    }catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
	    	System.err.println(e);
	    }
	}
	
//	private int testSimilarity(String ToTest, String ToTestWith){
//		int percentSimilar = 0;
//		for(int i = 0; ){
//			
//		}
//	}
	
	/**
	 * <div>Method that takes in two Elements Objects and interleaves the two together starting with the first Elements Object</div>
	 * <br>
	 * <div>Example:<br>
	 * oddResults: [1,3,5]<br>
	 * evenResults: [2,4,6]<br><br>
	 * 
	 * interleavedElements: [1,2,3,4,5,6]</div>
	 * 
	 *  @param oddResults The elements that the method will start with
	 *  @param evenResults The elements that the method will interleave with the odd results
	 *	@return An Elements Object with the two Elements Objects interleaved with each other
	 */
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
	
	/**
	 * Method that gets the title of the movie
	 * 
	 *	@return The string of the movie or null
	 */
	public String getTitle() {
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
	public List<String> getGenres() {
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
	 * Method that gets the Image object of the movie
	 * 
	 *	@return The Image object of the poster for the movie
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
	public String getMetascore() {
		return metascore;
	}

}
