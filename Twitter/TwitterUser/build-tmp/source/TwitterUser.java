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

public class TwitterUser extends PApplet {
















Twitter twitter;
String[] userName = {"tutoprocessing"};
List<Status> userTimeLine;
ArrayList<User> followers = new ArrayList<User>();
ArrayList<User> friends = new ArrayList<User>();
User user;



public void setup() {
  size(800, 600);
  twitterConfiguration();
  getUserInformations(userName);
  getNewUserTimeLine(userName);
  getFriendsList(userName);
  getFollowersList(userName);
}

public void draw() {   
  displayUserInformations(); 
  displayUserTimeline();
  displayFriendList();
  displayFollowersList();

  noLoop();
}

// TIMELINE info ---------------
public void getNewUserTimeLine(String[] _users) {
  try {
    userTimeLine = twitter.getUserTimeline(_users[0]);
  } 
  catch (TwitterException te) {
    println("Failed to get user timeline: " + te.getMessage());
    exit();
  }
}
public void displayUserTimeline() {
  for (Status status : userTimeLine) {
    println("@" + status.getUser().getScreenName() + " - " + status.getText());
  }
}

// USER info -----------------
public void getUserInformations(String[] _users) {
  try {
    user = twitter.showUser(_users[0]);
    String name = user.getStatus().getText();
  } 
  catch (TwitterException te) {
    println("Failed to get user informations " + te.getMessage());
    exit();
  }
}
public void displayUserInformations() {
  println("getLocation(): "+user.getLocation());
  println("getFriendsCount(): "+user.getFriendsCount());
  println("getFollowersCount(): "+user.getFollowersCount());
  println("getDescription(): "+user.getDescription());
  println("getCreatedAt() : "+user.getCreatedAt() );
  println("getDescriptionURLEntities(): "+user.getDescriptionURLEntities());
  println("getFavouritesCount() : "+user.getFavouritesCount() );
}

// FRIEND LIST -----------------
public void getFriendsList(String[] _users) {
  try {
    long cursor = -1;
    IDs ids;
    println("Listing friends's ids.");
    do {
      if (0 < _users.length) {
        ids = twitter.getFriendsIDs(_users[0], cursor);
      } 
      else {
        ids = twitter.getFriendsIDs(cursor);
      }
      for (long id : ids.getIDs()) {
        println(id);
        friends.add(twitter.showUser(id)); 

      }
    } 
    while ( (cursor = ids.getNextCursor ()) != 0);
    println("done listing friends");
  } 
  catch (TwitterException te) {
    te.printStackTrace();
    println("Failed to get followers' ids: " + te.getMessage());
    exit();
  }
}
public void displayFriendList(){
    println("FRIENDS");
    for (int i = 0; i<friends.size(); i++){
      println(friends.get(i).getScreenName());
  } 
}

//FOLLOWERLIST -----------------
public void getFollowersList(String[] _users) {
  try {
    long cursor = -1;
    IDs ids;
    println("Listing followers's ids.");
    do {
      if (0 < _users.length) {
        ids = twitter.getFollowersIDs(_users[0], cursor);
      } 
      else {
        ids = twitter.getFollowersIDs(cursor);
      }
      for (long id : ids.getIDs()) {
        println(id);
        followers.add(twitter.showUser(id)); 

      }
    } 
    while ( (cursor = ids.getNextCursor ()) != 0);
    println("done listingFollowers");
  } 
  catch (TwitterException te) {
    te.printStackTrace();
    println("Failed to get followers' ids: " + te.getMessage());
    exit();
  }
}
public void displayFollowersList(){
    println("FOLLOWERS");
    for (int i = 0; i<followers.size(); i++){
      println(followers.get(i).getScreenName());
  }
}

//SETUPS -----------------------
public void twitterConfiguration() {
  ConfigurationBuilder cb = new ConfigurationBuilder();
  cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
  cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
  cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
  cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");
  TwitterFactory tf = new TwitterFactory(cb.build());
  twitter = tf.getInstance();
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "TwitterUser" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
