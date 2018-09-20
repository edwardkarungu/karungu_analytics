/**
 * 
 */
package app;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class SearchTweets {
	
	private static Twitter twitter = null;;
	
	/**
	 * 
	 */
	public SearchTweets() {
		configure();
	}
	
	private void configure() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("3Nd7T7QbczItr8j5I1VHtr1iC")
		  .setOAuthConsumerSecret("71UkREljR7CVPtC7mkqfqk4i34Tbv22VLYlk1y4G2qMbDCzDbf")
		  .setOAuthAccessToken("705370571-k4fJ89820Wq5x6GJewxeh8V2klWImeCP8w7fNYmn")
		  .setOAuthAccessTokenSecret("GIfFRQBvED7ryRV5n6u3R4nSwyzO5N6JDsINQCgneVv2G");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	private List<Status> getTweets(String queryText){
		
		List<Status> tweetsFound = new ArrayList<Status>();
        try {
        	
            Query query = new Query(queryText);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        return tweetsFound;
	}
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args search query
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
            System.exit(-1);
        }
        
        SearchTweets miner = new SearchTweets();
        miner.getTweets(args[0]);
    }
}
