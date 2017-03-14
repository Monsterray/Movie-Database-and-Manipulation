/**
 * 
 */
package databasing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

/**
 * @author Monsterray
 *
 */
public class testingIMBDConnection {
	
	static String site = "http://imdb.com";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			UserAgent userAgent = new UserAgent();	//create new userAgent (headless browser).
			userAgent.visit(site);//visit a url   
			
			Element drinkTrail = userAgent.doc.findFirst("<div class=\"pm\">"); // Find first div who's class matches "pm"
			System.out.print("Drink Trail: ");
	      	Elements trailElements = drinkTrail.findEvery("<a href>");
	      	int size = trailElements.size();
	      	int counter = 1;
	      	for(Element e : trailElements){
	      		System.out.print(e.innerText().replace("&reg;", "\u00AE"));
	      		if(counter < size){
	      			System.out.print(" -> ");
	      		}
	      		counter++;
	      	}
			System.out.println("\n");
	      	
			Element drinkName = userAgent.doc.findFirst("<h1 class=\"fn recipe_title\">"); //find first div who's class matches "fn recipe_title"
			String title = drinkName.innerHTML();
	      	System.out.println("Title of drink: " + title.substring(0, title.length() - 7));
			System.out.println();
	      	
			Element recipeStats = userAgent.doc.findFirst("<div class=\"recipeStats\">"); //find first div who's class matches "recipeStats"
	      	String glassType = recipeStats.findFirst("<img>").getAt("title");
	      	String alcoholPercent = recipeStats.findFirst("<b>").innerText();
	      	System.out.println("Serve in and alcohol percentage:");
			System.out.println(glassType);
			System.out.println(alcoholPercent);
			System.out.println();
	      	
			Element recipe_data = userAgent.doc.findFirst("<div class=\"recipe_data\">"); //find first div who's class matches recipe_data"
			Elements seperateIngredients = recipe_data.findEvery("<span class=\"ingredient\">");
			Map<String, String> ingredients = new HashMap<String, String>();
			System.out.println("Ingredients:");
			for(Element section : seperateIngredients){
				System.out.println(section.findFirst("<span class=\"name\">").innerText().replace("&reg;", "\u00AE") + " : " + section.findFirst("<span class=\"amount\">").innerText());
				ingredients.put(section.findFirst("<span class=\"name\">").innerText(), section.findFirst("<span class=\"amount\">").innerText());
			}
			System.out.println();
	      	
			Element instructions = userAgent.doc.findFirst("<div class=\"RecipeDirections instructions\">"); //find first div who's class matches "RecipeDirections instructions"
	      	System.out.println("Instructions:\n" + instructions.innerHTML());
	      	
	      	
			Element nutritionDiv = userAgent.doc.findFirst("<div itemprop=\"nutrition\">"); // Find first div who's class matches "nutrition"
			Element nutritionTable = nutritionDiv.findFirst("<table>");	// Find the first table within the nutrition div
			Elements spans = nutritionTable.findEach("<span>");
			String servingSize = nutritionDiv.findFirst("itemprop=\"servingsize\"").innerText();
	      	System.out.println("\nNutrition information: " + servingSize.substring(0, servingSize.length() - 1));
			
			Map<String, String> data = new HashMap<String, String>();
			
			List<Element> rows = nutritionTable.findEvery("<td>").toList();
	    }catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
	    	System.err.println(e);
	    }

	}

}
