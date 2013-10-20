/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <contact@tutoprocessing.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Poul-Henning Kamp
 * ----------------------------------------------------------------------------
 */

import twitter4j.conf.*;
import twitter4j.internal.async.*;
import twitter4j.internal.org.json.*;
import twitter4j.internal.logging.*;
import twitter4j.json.*;
import twitter4j.internal.util.*;
import twitter4j.management.*;
import twitter4j.auth.*;
import twitter4j.api.*;
import twitter4j.util.*;
import twitter4j.internal.http.*;
import twitter4j.*;
import twitter4j.internal.json.*;
import java.util.*;

Twitter twitter;
String searchString = "#processing";
List<Status> tweets;
Status status;


void setup(){
    size(800,600);
    twitterConfiguration();
    getNewTweets();
}

void draw(){
    displayTweetInfos();
    noLoop();
}

void displayTweetInfos(){
    for (int i = 0; i<100; i++){
        status = tweets.get(i);
        println("Tweet numero "+i);
        println("Texte : "+status.getText());
        println("Date : "+status.getCreatedAt());
        println("Nombre de retweets : "+status.getRetweetCount());
        if(status.isRetweet()){
            println("Nom du retweeter : "+status.getRetweetedStatus().getUser().getScreenName());
        }
        println("envoyeur du tweet: "+status.getUser().getScreenName());
        if(status.getPlace() != null){
            println("pays : "+status.getPlace().getCountry());
            println("ville : "+status.getPlace().getName());
        }
        
    }
}

void getNewTweets(){
    try {
        Query query = new Query(searchString);
        query.count(100);
        QueryResult result = twitter.search(query);
        tweets = result.getTweets();
    } catch (TwitterException te) {
        println("Failed to search tweets: " + te.getMessage());
        exit();
    } 
}

void twitterConfiguration(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
    cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
    cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
    cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");
    TwitterFactory tf = new TwitterFactory(cb.build());
    twitter = tf.getInstance();
}
