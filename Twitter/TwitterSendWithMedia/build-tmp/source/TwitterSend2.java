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

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TwitterSend2 extends PApplet {

/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <contact@tutoprocessing.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Poul-Henning Kamp
 * ----------------------------------------------------------------------------
 */
















Twitter twitter;
String tweetMessage = "Les experimentations recommencent";
String directMessage = "message de test";
String reveiver = "fabax1";

public void setup(){
    twitterConfiguration();
}

public void draw(){
}

public void tweet(String _tweetMessage){
    try {
        StatusUpdate status = new StatusUpdate(tweetMessage);
        status.setMedia(new File("/Users/Fabax/Pro/Processing/Processing-snippets/Twitter/TwitterSend2/data/nbd.jpg"));// BY SPECIFYING FILE PATH
        //status.setMedia("File name", new FileInputStream("")); // By InputStream
        Status updateStatus = twitter.updateStatus(status);
        // Status status = twitter.updateStatus(_tweetMessage);
        // println("Status updated to [" + status.getText() + "].");
    }catch (TwitterException te){
        System.out.println("Error: "+ te.getMessage()); 
    }
}

public void directMessage(String _reveiver ,String _directMessage){
  try {
        twitter.sendDirectMessage(_reveiver,_directMessage);
        println("Direct message sent");
    }catch (TwitterException te){
        System.out.println("Error: "+ te.getMessage()); 
    }
}

public void keyPressed(){
    if(key == 't' || key =='T'){
        tweet(tweetMessage);
    }

    if(key == 'd' || key =='D'){
        directMessage(reveiver,directMessage);
    }

}

public void twitterConfiguration(){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setOAuthConsumerKey("2XYWWfo3BHdF17nVF6Nyg");
    cb.setOAuthConsumerSecret("XQtpXlLu8uJWgyI3XVEMHcCHCSwar9KbTnTWuXJmXk");
    cb.setOAuthAccessToken("613021275-Y3Utjpyh8cfAYpGel6UWs1aqzUMOdGuxuwsUc9jo");
    cb.setOAuthAccessTokenSecret("2NC2l6KgVVymgeNez9X2s0YezMhVXuO1UPq8j60IeI");
    TwitterFactory tf = new TwitterFactory(cb.build());
    twitter = tf.getInstance();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TwitterSend2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
