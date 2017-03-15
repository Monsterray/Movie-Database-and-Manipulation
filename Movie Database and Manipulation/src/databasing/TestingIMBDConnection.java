/**
 * 
 */
package databasing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.HttpRequest;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import com.jaunt.component.Form;

/**
 * @author Monsterray
 *
 */
public class TestingIMBDConnection {
	
	private static String movieTitlePatched = "12 Years a Slave 2013".replace(" ", "+");
	private static String site = "http://www.imdb.com/find?ref_=nv_sr_fn&q=" + movieTitlePatched + "&s=all";	// http://www.imdb.com/find?ref_=nv_sr_fn&q=12+Years+a+Slave+2013&s=all

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestingIMBDConnection tic = new TestingIMBDConnection();
		tic.findMovie(site);

	}

	private Double movieRating;
	
	public void findMovie(String site){
		try{
			UserAgent userAgent = new UserAgent();	//create new userAgent (headless browser).
			userAgent.visit(site);//visit a url
			
			Element firstResult = userAgent.doc.findFirst("<tr class=\"findResult odd\">"); // Find first div who's class matches "pm"
			System.out.println("First Result: \n" + firstResult.innerHTML());
			Element resultText = firstResult.findFirst("<td class=\"result_text\">");
			System.out.println("First Result Text: \n" + resultText.innerHTML());
			String movieAddress = firstResult.findFirst("<a href>").getAt("href");
			System.out.print("Address of movie on IMDb:\n");
			System.out.println(movieAddress);
			
			userAgent.visit(movieAddress);//visit a url
			Element moviePage = userAgent.doc;
			this.movieRating = new Double(moviePage.findFirst("<span itemprop=\"ratingValue\">").innerText());	// find the rating of the movie on IMDb
			Element outerPublishDate = moviePage.findFirst("<meta itemprop=\"datePublished\">");
			System.out.println("Outer publish date: \n" + outerPublishDate.outerHTML());
			System.out.println("The movie rating is: " + movieRating);
			System.out.println();
			
			Elements genres = userAgent.doc.findEvery("<span itemprop=\"genre\">");
			List<String> movieGenres = new LinkedList<String>();
	      	int size = genres.size();
	      	int counter = 1;
			for(Element e : genres){
	      		System.out.print(e.innerText());
	      		movieGenres.add(e.innerText());
	      		if(counter < size){
	      			System.out.print(", ");
	      		}
	      		counter++;
	      	}
			
	    }catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
	    	System.err.println(e);
	    }
	}

}
