/**
 * An inner class that represents an inner node in the leaf structure.
 * This node contains a pointer to a record, a pointer to the next inner node and a label (String).
 * The public variables are accessed by the Leaf class.
 * Natali Boniel, 201122140.
 */

package M1;

public class LeafCell {
 
    double rid;                         //The record number
    String myLabel= new String ("~");   //A specific value of the key field
    int flag=0;                         //A flag to indicate if a record was deleted or not, 0 was deleted, 1 is ok. a cell which was not yet used will get the value 0 as well

    //Constructor for objects of class LeafCell
    public LeafCell()
    {
        rid=-1;
    }

    //Constructor for objects of class LeafCell
    //The method receives a LeafCell
    //The method updates the Leaf with that cell
    public LeafCell(LeafCell lf1)
    {
        rid=lf1.rid;
        myLabel=lf1.myLabel;
        flag=1;
    }

    //The method receives a Record id (rid) number
    //The method updates the node with that value
    public void updateRid(double myRid)
    {
        rid=myRid;
        flag=1;
    }
    
    //The method receives a String
    //The method updates the node with that value.
    public void updateLabel(String myString)
    {
        myLabel=myString;
        flag=1;
    }

    //The method deletes a cell.
    public void deleteCell()
    {
        flag=0;
    } 

}
