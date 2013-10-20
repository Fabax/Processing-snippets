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

public class TwitterSend extends PApplet {
















Twitter twitter;
String tweetMessage = "tweet";
String directMessage = "message de test";
String reveiver = "fabax1";

public void setup(){
    twitterConfiguration();
}

public void draw(){
}

public void tweet(String _tweetMessage){
    try {
        Status status = twitter.updateStatus(_tweetMessage);
        println("Status updated to [" + status.getText() + "].");
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
    cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
    cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
    cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
    cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");
    TwitterFactory tf = new TwitterFactory(cb.build());
    twitter = tf.getInstance();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "TwitterSend" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
