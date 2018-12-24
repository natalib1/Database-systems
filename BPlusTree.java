/**
* The main class.
* Natali Boniel, 201122140.
*/

package M1;

import java.util.Scanner;

public class BPlusTree {
	
	public static void main(String[] args)
    {
        Data meyda= new Data();
        String message= new String (" ");
        double riddd=123456;
        System.out.println("Hello, we have space for 5 data lines, each contains 2 records of String type");
        Scanner scan = new Scanner(System.in);
        
        //Updating file of data called meyda by user
        for(int i=0; i<5; i++)
        {
            System.out.println("Please insert record1");
            message=scan.nextLine();
            meyda.myFile[i].updateWord1(message);
            System.out.println("Please insert record2");
            message=scan.nextLine();
            meyda.myFile[i].updateWord2(message);
        }
        
        //Loading index from Meyda to our B+ tree
        Root myBT1=new Root();
        for (int i=0; i<5; i++)
            myBT1.insert1Record(meyda.myFile[i].word1, i);
      /*             
        System.out.println("Please enter string/line for search");
        message=scan.nextLine();
        riddd=myBT1.searchValue(myBT1, message);
        System.out.println ("The rid number for wanted string is:"+riddd);
       
        System.out.println("Please enter string/line for deletion");
        message=scan.nextLine();
        myBT1.deleteRecordInNode(myBT1, message);  
*/
        System.out.println("The Tree:");
        myBT1.searchInRange(myBT1," ","~");        
    }

}
