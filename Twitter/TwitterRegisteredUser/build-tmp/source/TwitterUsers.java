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

public class TwitterUsers extends PApplet {
















Twitter twitter;
List<Status> homeTimeLine;
List<Status> mentionTimeLine;
List<Status> retweets; 
List<Status> myTimeLine; 

public void setup(){
    // size(800,600);
    twitterConfiguration();
    getRegisteredUserInfos();
}

public void draw(){   
    displayRegisteredUserInfos();
    noLoop();
}

// REGISTERED USER infos ---------------

public void getRegisteredUserInfos(){
    try {
        homeTimeLine = twitter.getHomeTimeline();
        mentionTimeLine = twitter.getMentionsTimeline();
        retweets = twitter.getRetweetsOfMe(); 
        myTimeLine = twitter.getUserTimeline();
    } catch (TwitterException te) {
        println("Failed to get user timeline: " + te.getMessage());
        exit();
    } 
}

public void displayRegisteredUserInfos(){
    println("Main User Home Timeline");
    for (Status status : homeTimeLine) {
        println("@" + status.getUser().getScreenName() + " - " + status.getText());
    }

    println("Main User Mention Timeline");
    for (Status status : mentionTimeLine) {
        println("@" + status.getUser().getScreenName() + " - " + status.getText());
    }

    println("Retweets of the main user");
    for (Status status : retweets) {
        println("@" + status.getUser().getScreenName() + " - " + status.getText());
    }

    println("My timeLine");
    for (Status status : myTimeLine) {
        println("@" + status.getUser().getScreenName() + " - " + status.getText());
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
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "TwitterUsers" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
