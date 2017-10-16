package absin.twitterFeed;

import java.util.ArrayList;

import twitter4j.Location;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.TrendsResources;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws TwitterException {
		// getAllWOEIDs();
		// getTweetsbyQuery("metoo");
		getTrendsByWOEID(23424848);
	}

	private static void getTrendsByWOEID(int woeid) throws TwitterException {
		Twitter twitter = BuildTwitter();
		Trends trends = twitter.getPlaceTrends(woeid);
		for (int i = 0; i < trends.getTrends().length; i++) {
		    System.out.println(trends.getTrends()[i].getName());
		}
	}

	private static void getTrendsByWOEID() {
		try {
			Twitter twitter = BuildTwitter();
			ResponseList<Location> locations;
			locations = twitter.getAvailableTrends();
			TrendsResources trends = twitter.trends();
			System.out.println("Showing available trends");

			for (Location location : locations) {
				System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
				Trends placeTrends = trends.getPlaceTrends(location.getWoeid());
				Trend[] trends2 = placeTrends.getTrends();
				for (Trend trend : trends2) {
					System.err.println(trend.getName());
				}
			}

			System.out.println("done.");
			System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get trends: " + te.getMessage());
			System.exit(-1);
		}
	}

	private static Twitter BuildTwitter() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("k4gxoBZberNKnBQHLaqNYE5OV")
				.setOAuthConsumerSecret("36xeybLYkGzDJyY9KMR4Gd9dfblp7CRapXmkRmJ4fWP1YdNIU8")
				.setOAuthAccessToken("396510325-krOzW8MRjSWpcFUFOBNicgU9kZhFYcC1ECgX3z7J")
				.setOAuthAccessTokenSecret("GAhhLpJABRwpucgUMYXid5bR8LZZ5gNg6YK47Isy9hUbh");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}

	public static void getTweetsbyQuery(String searchString) {
		// try the search
		try {
			Twitter twitter = BuildTwitter();
			Query query = new Query(searchString);
			// get the last 50 tweets
			query.count(200);
			QueryResult result = twitter.search(query);
			ArrayList<Status> tweets = (ArrayList) result.getTweets();
			for (Status status : tweets) {

				String placeName = "NA";
				try {
					placeName = status.getPlace().getFullName();
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println(placeName);
			}
			System.out.println(tweets.size());
		}
		// if there is an error then catch it and print it out
		catch (TwitterException te) {
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
	}
}
