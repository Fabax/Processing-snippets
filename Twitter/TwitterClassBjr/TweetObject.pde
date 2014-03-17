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
