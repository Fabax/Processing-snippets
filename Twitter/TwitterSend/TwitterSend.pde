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
String tweetMessage = "tweet";
String directMessage = "message de test";
String reveiver = "fabax1";

void setup(){
    twitterConfiguration();
}

void draw(){
}

void tweet(String _tweetMessage){
    try {
        Status status = twitter.updateStatus(_tweetMessage);
        println("Status updated to [" + status.getText() + "].");
    }catch (TwitterException te){
        System.out.println("Error: "+ te.getMessage()); 
    }
}

void directMessage(String _reveiver ,String _directMessage){
  try {
        twitter.sendDirectMessage(_reveiver,_directMessage);
        println("Direct message sent");
    }catch (TwitterException te){
        System.out.println("Error: "+ te.getMessage()); 
    }
}

void keyPressed(){
    if(key == 't' || key =='T'){
        tweet(tweetMessage);
    }

    if(key == 'd' || key =='D'){
        directMessage(reveiver,directMessage);
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
