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
  //clés twitter
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
        // On fait le lien entre le TwitterStream (qui récupère les messages) et notre écouteur  
        ts.addListener(new TwitterListener(isFilterOn,filterName,timerListener));
         // On démarre la recherche !
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

  // envoi un tweet à un utilisateur
  public void mention(String _tweetMessage){
      try {
          Status status = twitter.updateStatus(_tweetMessage);
          println("Status updated to [" + status.getText() + "].");
      }catch (TwitterException te){
          System.out.println("Error: "+ te.getMessage()); 
      }
  }

}
