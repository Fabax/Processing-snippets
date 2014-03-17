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

  void filterBadWords(String [] message_)
  {
    this.message = message_;
    this.filterOK = true;
     
    //Check si chaque mot n'est pas dans nos mots à filtrer
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

