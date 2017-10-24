package hashtagAnalyser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLFetcher {
	
	private static Document fetchDoc(String url) {
		Connection conn = Jsoup.connect(url);
		try {
			Document doc = conn.get();
//			Elements links = doc.select("a[href]");
//		      for (Element link : links) {
//		        // get the value from the href attribute
//		        System.out.println("\nlink: " + link.attr("href"));
//		        System.out.println("text: " + link.text());
//		      }
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Document fetchHTML(String hashtag) {
		Document doc = fetchDoc(hashtag);
		return doc;
	}
	
	public static List<Document> fetchAllPages(String url) {
		List<Document> pages = new LinkedList<Document>();
		Document doc = fetchDoc(url);
		return null;
	}
}
