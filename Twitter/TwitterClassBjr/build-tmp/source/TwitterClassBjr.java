import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import twitter4j.conf.*; 
import twitter4j.internal.async.*; 
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

public class TwitterClassBjr extends PApplet {



//import twitter4j.internal.org.json.*;















ModelTwitter modelBird;
ConfigBird configBird;

String[] hashTags;
String[] users;
int twitterKey;
ArrayList<TweetObject> tweetList = new ArrayList<TweetObject>();


public void setup() {
  configBird = new ConfigBird("configBird.json");
  initConfig();
  modelBird = new ModelTwitter(twitterKey);
  modelBird.listenToHashtag(hashTags);
}

public void draw() {
  for (int i = tweetList.size()-1; i >= 0; i--) {
    TweetObject tweet = tweetList.get(i);
    println(tweet.getUsername());
    println(tweet.getId());
    println(tweet.getMessage());
    println(tweet.getImageUrl());

  }
}

public void initConfig() {
  hashTags = configBird.jsonArrayToStringArray("hashtags");
  users = configBird.jsonArrayToStringArray("users");
  twitterKey = configBird.getInt("twitterKey");
}

public class ConfigBird 
{
	
	processing.data.JSONObject allConfigs;
	
	public ConfigBird (String _config) {
		allConfigs = loadJSONObject(_config);
	}

	public String[] jsonArrayToStringArray(String _jsonArray) {
		processing.data.JSONArray tempJsonArray = allConfigs.getJSONArray(_jsonArray);

		String [] citations = new String[tempJsonArray.size()];
		for (int i = 0; i < tempJsonArray.size(); i++){
			citations[i] = tempJsonArray.getString(i);
		}
		return citations;
	}

	public String getString(String _value) {
		return allConfigs.getString(_value);
	}

	public boolean getBool(String _value) {
		return allConfigs.getBoolean(_value);
	}

	public int getInt(String _value) {
		return allConfigs.getInt(_value);
	}

	public String metersToKilometers(float _value){
		String valueString;

		if(_value > 1000){
			valueString = _value/1000+" km";
		}else{
			valueString = _value+" m";
		}
		return valueString;
	}

	public String makeCustomMessage(float _value){
		String[] messages = jsonArrayToStringArray("citations");
		String stringToReturn = "";
		int counter = 500;
		int palier = getInt("palier");
		int taille = messages.length;

		for (int i = 0; i<taille; i++){

			if(i == taille -1){
				stringToReturn = messages[taille -1];
			}else if(_value < counter && _value >counter - palier){
				stringToReturn =  messages[i];
			}
			counter += palier;
		}
		return stringToReturn;
	}

}
class Filtre
{ 
  String fichierMots;
  String lines[];
  int csvWidth;
  String [] arrayMots;
  String [] message;
  boolean filterOK;

  Filtre (String fichierMots_)
  {
    this.arrayMots = new String [0];
    this.fichierMots = fichierMots_;
    this.filterOK = true;
    this.arrayMots = loadStrings(fichierMots);
  }

  public void filterBadWords(String [] message_)
  {
    this.message = message_;
    this.filterOK = true;
     
    //Check si chaque mot n'est pas dans nos mots \u00e0 filtrer
    for (int i=0; i < message.length; i++)
    {
      for (int j=0; j < this.arrayMots.length; j++)
      {
        if (this.arrayMots[j].toLowerCase().equals(message[i].toLowerCase()) == true)
        {
          filterOK = false;
          //println("Message filtered : bad word found = "+arrayMots[j]);
        }
      }
    } 
  } 
}

public class ModelTwitter {
  //configuration de twitter
  Twitter twitter;
  User user;
  Configuration c;
  List<Status> tweets; // list of tweets for a search
  Status status;
  String[] hashTags;
  int timerListener ; // temps entre chaque envois de tweets au controller
  //Filtres
  String[] wordsOfTheTweet; // Array utiliser dans le filtrage pour stocker tous les mots d'un tweet
  boolean isFilterOn = false;
  String filterName;
  //cl\u00e9s twitter
  processing.data.JSONArray  twitterKeySet; 
  processing.data.JSONObject twitterKeys = new processing.data.JSONObject(); 

  //--------------------------------------
  //  CONSTRUCTOR
  //--------------------------------------
  
  public ModelTwitter (int _twitterKey) {
    twitterConfiguration(_twitterKey);
    //go get the filter enable status
    isFilterOn = configBird.getBool("enableFilter");
    if(isFilterOn){
      //if filter is on we gp get the text;
      filterName = configBird.getString("filter");
    }
    //configuration of the time tweets get sent to the controller 
  }

  //----- FIN DE GETTERS AND SETTERS

  // CONFIGURATION
  private void twitterConfiguration(int _twitterKey){
      ConfigurationBuilder cb = new ConfigurationBuilder();

      twitterKeySet = loadJSONArray("twitterKeys.json");
      twitterKeys = twitterKeySet.getJSONObject(_twitterKey);

      cb.setDebugEnabled(true);
      cb.setOAuthConsumerKey(twitterKeys.getString("setOAuthConsumerKey"));
      cb.setOAuthConsumerSecret(twitterKeys.getString("setOAuthConsumerSecret"));
      cb.setOAuthAccessToken(twitterKeys.getString("setOAuthAccessToken"));
      cb.setOAuthAccessTokenSecret(twitterKeys.getString("setOAuthAccessTokenSecret"));

      c = cb.build();
      TwitterFactory tf = new TwitterFactory(c);
      twitter = tf.getInstance();
  }

  //Methode that will listen to a specifique hashtag and will return the result in live
  public void listenToHashtag(String[] _keyWords){
        TwitterStream ts = new TwitterStreamFactory(c).getInstance();
        FilterQuery filterQuery = new FilterQuery(); 
        filterQuery.track(_keyWords);
        // On fait le lien entre le TwitterStream (qui r\u00e9cup\u00e8re les messages) et notre \u00e9couteur  
        ts.addListener(new TwitterListener(isFilterOn,filterName,timerListener));
         // On d\u00e9marre la recherche !
        ts.filter(filterQuery);  
  }  
  // USER INFOS -----------------

  //get the user informations
  public void getUserInformations(String[] _users) {
    String[] userList = _users;
    for (int i = 0; i<userList.length; i++){
      try {
        user = twitter.showUser(userList[i]);
        displayUserInformations();
      } catch (TwitterException te) {
        println("Failed to get user informations " + te.getMessage());
        exit();
      }
    }
  }

  //display the user information (for debug)
  public void displayUserInformations() {
    println("getLocation(): "+user.getLocation());
    println("getFriendsCount(): "+user.getFriendsCount());
    println("getFollowersCount(): "+user.getFollowersCount());
    println("getDescription(): "+user.getDescription());
    println("getCreatedAt() : "+user.getCreatedAt() );
    println("getDescriptionURLEntities(): "+user.getDescriptionURLEntities());
    println("getFavouritesCount() : "+user.getFavouritesCount() );
  }// END OF USER INFOS -----------------

  //SEARCH TWEETS --------------
  public void searchTweets(String _search, int _numberOfResults){
    try {
        Query query = new Query(_search);
        query.count(_numberOfResults);
        QueryResult result = twitter.search(query);
        tweets = result.getTweets();
    } catch (TwitterException te) {
        println("Failed to search tweets: " + te.getMessage());
        exit();
    } 
  }

  public void displaySearchedTweets(int _numberOfResults){
    for (int i = 0; i<_numberOfResults; i++){
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
  }//END OF SEARCH TWEETS --------------

  // envoi un tweet \u00e0 un utilisateur
  public void mention(String _tweetMessage){
      try {
          Status status = twitter.updateStatus(_tweetMessage);
          println("Status updated to [" + status.getText() + "].");
      }catch (TwitterException te){
          System.out.println("Error: "+ te.getMessage()); 
      }
  }

}
public class TweetObject {

public String userName; 
public String message; 
public String id; 
public String imageUrl; 

  public TweetObject (String _userName, String _message,String _id, String _imageUrl) {
    userName = _userName;
    message = _message;
    id = _id;
    imageUrl = _imageUrl;
  }

  public void display(){
    println(this.userName);
    println(this.message);
    println(this.id);
    println(this.imageUrl);
  }

  // getters
  public String getUsername() {
    return userName;
  }

   public String getMessage() {
    return message;
  }

   public String getId() {
    return id;
  }

   public String getImageUrl() {
    return imageUrl;
  }

}
public class TwitterListener implements StatusListener{
  // onStatus : nouveau message qui vient d'arriver 
  
  String messageTweet, userName, userId, imageUrl; //informations qui seront filtr\u00e9s par la fonction modelTwitter
  processing.data.JSONArray tweets ;
  int counter;
  boolean isFilterOn ;
  String[]wordsOfTheTweet; // Array utiliser dans le filtrage pour stocker tous les mots d'un tweet
  Filtre filtre;

  public TwitterListener(boolean _isFilterOn,String _filterName ,int _time){
    filtre = new Filtre(_filterName);
    isFilterOn = _isFilterOn;
    tweets =  new processing.data.JSONArray();
    counter = 0;
  }

  public void onStatus(Status status) {
        //get the informations we want to return 
    userName = status.getUser().getScreenName();
    userId = Long.toString(status.getUser().getId());
    messageTweet = status.getText();
    imageUrl = status.getUser().getProfileImageURL();
  
    if(isFilterOn){
      filerTweet();
    }else{
      makeJson(userId,userName,messageTweet,imageUrl);
    }
  }  

  public void makeJson(String _id, String _user, String _message, String _imageUrl){
    processing.data.JSONObject tweetInfos =  new processing.data.JSONObject();
    processing.data.JSONObject tweetId =  new processing.data.JSONObject();

    tweetList.add(new TweetObject(_user,_message,_id,_imageUrl));
  }

  public void filerTweet(){
    if(messageTweet!=null){
        wordsOfTheTweet = messageTweet.split(" ");
        filtre.filterBadWords(wordsOfTheTweet);
    if (filtre.filterOK){
         makeJson(userId,userName,messageTweet, imageUrl);
    }else{
        println("not added to the Json");
      }
    }
  }

  // onDeletionNotice
  public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) 
  {
  }

  // onTrackLimitationNotice
  public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
  }  

  // onScrubGeo : r\u00e9cup\u00e9ration d'infos g\u00e9ographiques
  public void onScrubGeo(long userId, long upToStatusId) 
  {
    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
  }

  public void onStallWarning(StallWarning warning){

  }
  // onException : une erreur est survenue (d\u00e9connexion d'internet, etc...)
  public void onException(Exception ex) 
  {
    ex.printStackTrace();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TwitterClassBjr" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
