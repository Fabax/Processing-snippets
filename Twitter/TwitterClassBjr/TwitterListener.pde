
public class TwitterListener implements StatusListener{
  // onStatus : nouveau message qui vient d'arriver 
  
  String messageTweet, userName, userId, imageUrl; //informations qui seront filtrés par la fonction modelTwitter
  processing.data.JSONArray tweets ;
  int counter;
  boolean isFilterOn ;
  String[]wordsOfTheTweet; // Array utiliser dans le filtrage pour stocker tous les mots d'un tweet
  Filtre filtre;

  public TwitterListener(boolean _isFilterOn,String _filterName ,int _time){
    filtre = new Filtre(_filterName);
    isFilterOn = _isFilterOn;
    if(isFilterOn == true){

    }
   
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

    tweetInfos.setString("userName",_user);
    tweetInfos.setString("message",_message);
    tweetInfos.setString("id",_id);
    tweetInfos.setString("imageUrl",_imageUrl);

    tweets.setJSONObject(counter, tweetInfos);
    println("messageTweet: "+tweets);
    counter ++;
     
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

  // onScrubGeo : récupération d'infos géographiques
  public void onScrubGeo(long userId, long upToStatusId) 
  {
    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
  }

  public void onStallWarning(StallWarning warning){

  }
  // onException : une erreur est survenue (déconnexion d'internet, etc...)
  public void onException(Exception ex) 
  {
    ex.printStackTrace();
  }
}
