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
List<Status> homeTimeLine;
List<Status> mentionTimeLine;
List<Status> retweets; 
List<Status> myTimeLine; 

void setup(){
    twitterConfiguration();
    getRegisteredUserInfos();
}

void draw(){   
    displayRegisteredUserInfos();
    noLoop();
}

// REGISTERED USER infos ---------------

void getRegisteredUserInfos(){
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

void displayRegisteredUserInfos(){
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

void twitterConfiguration(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
    cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
    cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
    cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");
    TwitterFactory tf = new TwitterFactory(cb.build());
    twitter = tf.getInstance();
}

