/**
 * The structure of a "file line" in the file "Data". 
 * Each line contains two record, the first one is the main key.
 * Natali Boniel, 201122140.
 */

package M1;

public class RecStru {

    //Each line contains two records, the first one is the main key
    String word1=new String (" ");
    String word2=new String (" ");

    //Constructor for objects of class Data
    public RecStru()
    {
        word1=" ";
        word2=" ";  
    }

    //The program receives a string to update as first record
    public void updateWord1(String myS1)
    {
        word1=myS1;
    }
    
    //The program receives a string to update as second record
    public void updateWord2(String myS2)
    {
        word2=myS2;
    }

}
