import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

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

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TwitterSearch extends PApplet {
















Twitter twitter;
String searchString = "#processing";
List<Status> tweets;
Status status;


public void setup(){
    size(800,600);
    twitterConfiguration();
    getNewTweets();
}

public void draw(){
    displayTweetInfos();
    noLoop();
}

public void displayTweetInfos(){
    for (int i = 0; i<100; i++){
        status = tweets.get(i);
        println("Tweet numero "+i);
        println("Texte : "+status.getText());
        println("Date : "+status.getCreatedAt());
        println("Id du status : "+status.getId());
        println("Nombre de retweets : "+status.getRetweetCount());
        if(status.isRetweet()){
            println("Nom du retweeter : "+status.getRetweetedStatus().getUser().getScreenName());
        }
        println("envoyeur du tweet: "+status.getUser().getScreenName());
        if(status.getPlace() != null){
            println("pays : "+status.getPlace().getCountry());
            println("ville : "+status.getPlace().getName()) ;
            println("adress : "+status.getPlace().getStreetAddress());
        }
        
    }
}

public void getNewTweets(){
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

public void twitterConfiguration(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
    cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
    cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
    cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");
    TwitterFactory tf = new TwitterFactory(cb.build());
    twitter = tf.getInstance();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "TwitterSearch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
