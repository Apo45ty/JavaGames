import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Test1 {
	public static void main(String... s) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("3ahqeBpKBQ8aFZpxbGBNCvtWI")
		  .setOAuthConsumerSecret("3VICwmBPa8is3E9bUE11hdXYOwFkWfvDPpBsBBSTwal793cvAR")
		  .setOAuthAccessToken("1355238781-hVlRyVTg18kEttozvzqdgAt9PCV6EWfhgjJCVeU")
		  .setOAuthAccessTokenSecret("PoVLP9B7T8Rrm64KW6ky02fi9fmi1h3d0CwwwK9zE5SKT");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
	    Query query = new Query("worldcup");
	    QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    for (Status status : result.getTweets()) {
	        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	    }
	    
	}
}
