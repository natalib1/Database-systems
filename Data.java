/**
 * The data file.
 * It can  contain 5 lines. each line contains 2 records.
 * The first record is the main key.
 * Natali Boniel, 201122140.
 */

package M1;

public class Data {

    //Instance variables - replace the example below with your own
    RecStru[] myFile;
    
    //Constructor for objects of class Data
    public Data()
    {
    	myFile=new RecStru [5];
    	for(int i=0; i<5; i++)
            myFile[i]= new RecStru();
    }

}
