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

Twitter twitter; // objet twitter
String searchString = "#pokemon"; // utilisé dans la recherche de tweets
List<Status> tweets; // liste contenant 100 tweets
PImage img; // conteneur de l'image de profil 
String imageUrl; // url de l'image de profil 
String tweetText; // le texte du tweet
float xPos, yPos; // coordonnées de notre tweet 
float imageWidth, imageHeight; // taille de l'image de profil 
int currentTweet = 0; //variable nous indiquant quel tweet doit être affiché 
int tweetRows, tweetColumns; //variables servant a placer le tweet dans le sketch
int tweetRowsCount = 0, tweetColumnsCount = 0; //variables indiquant sur quelle ligne et sur quelle colone il faut afficher le tweet

//Mensuration du tweet a afficher
int tweetWidth ; // largeur total du tweet + photo affiché
int tweetHeight ; // taille de l'image de profil 
int tweetTextSize ; // place occupé par le text du tweet

int frameNumber = 0;

void setup(){
    //variables d'affichages
    background(0); // couleur du fond d'ecran
    textSize(11); // taille du texte
    textLeading(11); //taille des interlignes du texte

    tweetColumns = 5; //nombre de collones a afficher dans le sketch
    tweetRows = 16; // nombre de lignes a afficher dans le sktech

    tweetWidth = 278; // largeur total du tweet + photo affiché
    tweetHeight = 48; // taille de l'image de profil 
    tweetTextSize = tweetWidth - tweetHeight ; // place occupé par le text du tweet

    // la taille du sketch est dynamique et depend du nombre de collones, de lignes ainsi que de la taille des twetts que nous affichons
    size(tweetColumns*tweetWidth,tweetRows*tweetHeight); 

    //preparation des identifiants de l'application 
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setOAuthConsumerKey("34sJKGiU71xaVBVeutDA");
    cb.setOAuthConsumerSecret("sMGrXuf2zbhS29cEV9HYHDeNoU45aoGWcw1t2JbJMMk");
    cb.setOAuthAccessToken("1272243708-woC2NKzPErcj9CAsUGURNOmS9OL4ISdFI9hyQmh");
    cb.setOAuthAccessTokenSecret("D846JFR6nH9v13icgBcLfyNCUVWg53R9jhWwjmwuBU");

    TwitterFactory tf = new TwitterFactory(cb.build());
    
    //création de l'objet twitter qui servira tout au long du sketch
    twitter = tf.getInstance();

    //fonction appellé pour la premiere fois afin de charger des tweets dans notre liste
    getNewTweets();
    //la fonction refreshTweets est ensuite appellé dans un thread a par afin de ne pas ralentir le sketch lorsque de nouveaux tweets sont chargé dans le tableau
    thread("refreshTweets");
}

void draw(){
    
    // si le compteur "curentTweet" depasse le total de tweets dans la list nous faisons une remise a zero du sketch
    if (currentTweet >= tweets.size()){
        currentTweet = 0; 
        tweetColumnsCount = 0;
        tweetRowsCount = 0;
        background(0);

    }

    // contient le tweet à afficher pour cette frame
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
void getNewTweets(){
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
void refreshTweets(){
    while (true){
        getNewTweets();
        println("tweets mis a jours"); 
        delay(30000);
    }
}
