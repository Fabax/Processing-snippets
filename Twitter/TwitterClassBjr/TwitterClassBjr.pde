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
String tweetsAutomatics;
int twitterKey;
int w, h;


void setup() {
  configBird = new ConfigBird("configBird.json");
  initConfig();
  size(w, h, P3D);
  modelBird = new ModelTwitter(twitterKey);
  modelBird.listenToHashtag(hashTags);
}

void draw() {
  
}

void initConfig() {
  hashTags = configBird.jsonArrayToStringArray("hashtags");
  users = configBird.jsonArrayToStringArray("users");
  twitterKey = configBird.getInt("twitterKey");
  w = configBird.getInt("width");
  h = configBird.getInt("height");
}

