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

public class TwitterWall extends PApplet {
















Twitter twitter; // objet twitter
String searchString = "#pokemon"; // utilis\u00e9 dans la recherche de tweets
List<Status> tweets; // liste contenant 100 tweets
PImage img; // conteneur de l'image de profil 
String imageUrl; // url de l'image de profil 
String tweetText; // le texte du tweet
float xPos, yPos; // coordonn\u00e9es de notre tweet 
float imageWidth, imageHeight; // taille de l'image de profil 
int currentTweet = 0; //variable nous indiquant quel tweet doit \u00eatre affich\u00e9 
int tweetRows, tweetColumns; //variables servant a placer le tweet dans le sketch
int tweetRowsCount = 0, tweetColumnsCount = 0; //variables indiquant sur quelle ligne et sur quelle colone il faut afficher le tweet

//Mensuration du tweet a afficher
int tweetWidth ; // largeur total du tweet + photo affich\u00e9
int tweetHeight ; // taille de l'image de profil 
int tweetTextSize ; // place occup\u00e9 par le text du tweet

int frameNumber = 0;

public void setup(){
    //variables d'affichages
    background(0); // couleur du fond d'ecran
    textSize(11); // taille du texte
    textLeading(11); //taille des interlignes du texte

    tweetColumns = 5; //nombre de collones a afficher dans le sketch
    tweetRows = 16; // nombre de lignes a afficher dans le sktech

    tweetWidth = 278; // largeur total du tweet + photo affich\u00e9
    tweetHeight = 48; // taille de l'image de profil 
    tweetTextSize = tweetWidth - tweetHeight ; // place occup\u00e9 par le text du tweet

    // la taille du sketch est dynamique et depend du nombre de collones, de lignes ainsi que de la taille des twetts que nous affichons
    size(tweetColumns*tweetWidth,tweetRows*tweetHeight); 

    //preparation des identifiants de l'application 
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
    cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
    cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
    cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");

    TwitterFactory tf = new TwitterFactory(cb.build());
    
    //cr\u00e9ation de l'objet twitter qui servira tout au long du sketch
    twitter = tf.getInstance();

    //fonction appell\u00e9 pour la premiere fois afin de charger des tweets dans notre liste
    getNewTweets();
    //la fonction refreshTweets est ensuite appell\u00e9 dans un thread a par afin de ne pas ralentir le sketch lorsque de nouveaux tweets sont charg\u00e9 dans le tableau
    thread("refreshTweets");
}

public void draw(){
    
    // si le compteur "curentTweet" depasse le total de tweets dans la list nous faisons une remise a zero du sketch
    if (currentTweet >= tweets.size()){
        currentTweet = 0; 
        tweetColumnsCount = 0;
        tweetRowsCount = 0;
        background(0);

    }

    // contient le tweet \u00e0 afficher pour cette frame
    Status status = tweets.get(currentTweet);
    currentTweet ++;
    //------------------------------------------------

    //On determine la position du tweet
    xPos = tweetColumnsCount*tweetWidth;
    yPos = tweetRowsCount*tweetHeight;

    //image de profil 
    imageUrl = status.getUser().getProfileImageURL();
    img = loadImage(imageUrl,"jpg");
    imageWidth = img.width;
    imageHeight = img.height;

    // texte du tweet
    tweetText = status.getText();

    //Affichage du tweet
    // fill(0);
    image(img, xPos, yPos);
    text(tweetText ,xPos+tweetHeight, yPos, tweetTextSize, tweetHeight);

    // determiner la position du prochain tweet
    if(tweetColumnsCount%tweetColumns == 0 && tweetColumnsCount !=0){
        tweetColumnsCount = 0;
        tweetRowsCount ++;
        if(tweetRowsCount%tweetRows ==0 && tweetRowsCount !=0){
            tweetRowsCount = 0;
        }
    } else{
        tweetColumnsCount++;
    }
}

//fonction qui vient chercher les 100 derniers tweets
public void getNewTweets(){
    try {
        Query query = new Query(searchString);
        query.count(100);
        QueryResult result = twitter.search(query);
        tweets = result.getTweets();
    } catch (TwitterException te) {
        println("Echec de la recherche de tweets: " + te.getMessage());
        exit();
    } 
}

//charge de nouveau tweets dans la list "tweets" toutes les 
public void refreshTweets(){
    while (true){
        getNewTweets();
        println("tweets mis a jours"); 
        delay(30000);
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "TwitterWall" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
