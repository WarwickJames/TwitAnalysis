package hashtagAnalyser;

import java.util.List;
import java.util.Map;

public class HashtagAnalyserTests {

	public static void main(String[] args) {
		HashtagAnalyser ha = new HashtagAnalyser();
		ha.loadHashtag("throwbackthursday");
		System.out.println(ha.count());
		Map<String, Integer> frequency = ha.frequencyMap();
		for( String time : frequency.keySet() ) {
			System.out.println(time + "  :  " + frequency.get(time));
		}
		List<String> fullnames = ha.fullnames();
		List<String> usernames = ha.usernames();
		for( int i = 0; i < fullnames.size(); i++ ) {
			System.out.printf("%-30.30s  %-30.30s%n", fullnames.get(i), usernames.get(i+3));
		}
	}
}
