/**
* Class Leaf represents a leaf in a B+ tree.
* The leaf is characterized by 2 pointers: 1. pointer to the neighbor leaf on the right.   2. pointer to the specific record on the Data file.
* The leaf is made of (2d-1) mini-cells each one of LeafCell type. since in a B+ tree the number of pointers is bigger than 1 from number of values
* I added this Leaf class a pointer which is the last pointer in the leaf structure - therefore points to the next Leaf,
* if there is no next leaf, it gets value 0 (null).
* Natali Boniel, 201122140.
*/

package M1;

public class Leaf {

   final int d=2;
   LeafCell[] leaf1= new LeafCell[2*d-1];
   Leaf lastPointer=null;
   int count=0; //Pointer to the cell that the one before him is the last updated cell
   Node myFatherNodeIs=null;

   //Constructor for list of objects from type LeafCell 
   public Leaf()
   {
       for (int value=0; value<=(2*d-1-1); value++)
           leaf1[value]= new LeafCell();
   }

   //The method receives an object of LeafCell type.
   //The method updates the data inside the leaf.
   public void add(LeafCell myCell)
   {
       leaf1[count]= new LeafCell(myCell);
       count++;
       
   }

   //The method receives an object of LeafCell type.
   //The method updates the pointer of last leaf so it will point to the next leaf.
   public void updateLastPointer(Leaf myCell)
   {
       lastPointer=myCell;
   } 

   //The method deletes a whole leaf
   public void deleteLeaf (Leaf myLeaf)
   { 
       for(int i=0; i<=2*d-1-1; i++)
           myLeaf.leaf1[i].deleteCell();   
   } 

   //The method deletes specific cell inside the leaf
   public void deleteLeaf (Leaf myLeaf, int loc)  //loc is the wanted location of the cell
   {
       myLeaf.leaf1[loc].deleteCell();  
   }  

   //The method searches the leaf for a specific label (string)
   //The method returns a pointer
   public double [] searchString (String myStr1)  
   {
       int runner=0;
       int runner2=0;
       double[] founded1=new double[2*d-1];
       
       while ((runner<=2d-2) && (myStr1!=leaf1[runner].myLabel))
       {
           if (myStr1==leaf1[runner].myLabel)
           {
               founded1[runner2]=leaf1[runner].rid;
               runner2++;
           }
           runner++;
       }
       return founded1;
   } 
    
   //The method checks whether the leaf is legal and contains at least d values
   //If leaf is illegal the methods prints warning on screen
   public void checkLeaf (Leaf myLeaf)
   {
       int sum2=0;  
       for (int i=0; i<2*d; i++)
           if (myLeaf.leaf1[i].myLabel!=" ")
               sum2++;
       if (sum2<d)
        System.out.println( "This Leaf is illegal because it contains "+sum2+" full cells");       
   }

   //The method receives an object of bNode type.
   //The method updates the father pointer of the node so we will know who is her father
   public void updateWhoIsLeafFather (Node myNode)
   {
        myFatherNodeIs=myNode; 
   }  

}
