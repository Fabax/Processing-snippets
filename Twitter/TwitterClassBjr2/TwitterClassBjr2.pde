import twitter4j.conf.*;
import twitter4j.internal.async.*;
//import twitter4j.internal.org.json.*;
import twitter4j.internal.logging.*;
import twitter4j.internal.util.*;
import twitter4j.management.*;
import twitter4j.auth.*;
import twitter4j.api.*;
import twitter4j.util.*;
import twitter4j.internal.http.*;
import twitter4j.*;
import java.util.*;
import ddf.minim.*;
import ddf.minim.signals.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;


ModelTwitter modelBird;
ConfigBird configBird;

String[] hashTags;
String[] users;

int twitterKey;

//KEEP THAT NAME (tweetList)! 
ArrayList<TweetObject> tweetList = new ArrayList<TweetObject>();


void setup() {
  configBird = new ConfigBird("configBird.json");
  initConfig();
  modelBird = new ModelTwitter(twitterKey);
  modelBird.start();
  modelBird.listenToHashtag(hashTags);
  modelBird.getAutoTweets();
}

void draw() {
  for (int i = tweetList.size()-1; i >= 0; i--) {
    TweetObject tweet = tweetList.get(i);
    println(tweet.getUsername());
    println(tweet.getId());
    println(tweet.getMessage());
    println(tweet.getImageUrl());
  }
}

void initConfig() {
  hashTags = configBird.jsonArrayToStringArray("hashtags");
  users = configBird.jsonArrayToStringArray("users");
  twitterKey = configBird.getInt("twitterKey");
}

