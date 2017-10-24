package hashtagAnalyser;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HashtagAnalyser {
	
	private Document doc;
	private long n;
	private List<String> tweets;
	private Map<String, Integer> frequency;
	private boolean frequencyLoaded;
	
	private boolean timeSearch;
	private boolean timeToSearch;
	private String dateFrom;
	private String dateTo;

	public HashtagAnalyser() {
		timeSearch = false;
		timeToSearch = false;
	}
	
	public void setTime(String dateFrom) {
		timeSearch = true;
		timeToSearch = false;
		this.dateFrom = dateFrom;
//		Calendar c = Calendar.getInstance();
//		String dateTo = c.get(Calendar.YEAR) + "-" + String.format("%02d", c.get(Calendar.MONTH)) + "-" + String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
//		this.dateTo = dateTo;
	}
	
	public void setTime(String dateFrom, String dateTo) {
		timeSearch = true;
		timeToSearch = true;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	
	public void resetTime() {
		timeSearch = false;
		timeToSearch = false;
	}
	
	public void loadHashtag(String hashtag) {
		frequency = new HashMap<String, Integer>();
		frequencyLoaded = false;
		
		// https://twitter.com/search?f=tweets&vertical=default&q=%23bristol%20since%3A2017-09-01%20until%3A2017-09-30
		// https://twitter.com/search?f=tweets&vertical=default&q=%23bristol%20since%3A2017-10-04
		// https://twitter.com/search?f=tweets&vertical=default&q=%23bristol
		String url = "https://twitter.com/search?f=tweets&q=%23" + hashtag;
		if( timeSearch ) {
			url += "%20since%3A" + dateFrom;
			if( timeToSearch ) url += "%20unstil%3A" + dateTo;
		}
		doc = HTMLFetcher.fetchHTML(url);
		
		Elements elements = doc.select("div.content");
		tweets = elements.eachText();
		n = tweets.size();
	}
	
	public List<String> fullnames() {
		return doc.select("strong.fullname").eachText();
	}
	
	public List<String> usernames() {
		return doc.select("span.username").eachText();
	}
	
	public long countL() {
		return n;
	}
	
	public int count() {
		return (int) n;
	}
	
	public Map<String, Integer> frequencyMap() {
		if( frequencyLoaded ) return frequency;
		Elements timestamps = doc.select("._timestamp");
		List<String> times = timestamps.eachText();
		for( String time : times ) {
			String[] split = time.split(" ");
			Calendar c = Calendar.getInstance();
			if( split.length == 2 ) {
				c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(split[1]));
				String month = split[0];
				c.set(Calendar.MONTH, parseMonth(month));
			} else if( split.length == 3 ) {
				c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(split[0]));
				c.set(Calendar.MONTH, parseMonth(split[1]));
				c.set(Calendar.YEAR, Integer.parseInt(split[2]));
			}
			time = String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR);
			
			frequency.put(time, frequency.getOrDefault(time, 0) + 1);
		}
		frequencyLoaded = true;
		return frequency;
	}
	
	private int parseMonth(String month) {
		int m = 0;
		char d = month.charAt(month.length()-1);
		switch (d) {
			case 'n': if( month.charAt(1) == 'a' ) m = 0;
					  else m = 5;
					  break;
			case 'b': m = 1;
					  break;
			case 'r': if( month.charAt(1) == 'a' ) m = 2;
			  		  else m = 3;
			  		  break;
			case 'y': m = 4;
	  		  		  break;
			case 'l': m = 6;
	  		  		  break;
			case 'g': m = 7;
	  		  		  break;
			case 'p': m = 8;
	  		  		  break;
			case 't': m = 9;
	  		  		  break;
			case 'v': m = 10;
	  		  		  break;
			case 'c': m = 11;
	  		  		  break;
	  		default : throw new IllegalArgumentException();
		}
		return m;
	}
	
	
}
