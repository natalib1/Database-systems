/**
 * An inner class that represents an inner node in the B+ tree structure.
 * This node contains a pointer to another Node or Leaf and a label (String).
 * The public variables are accessed by the Node class
 * Natali Boniel, 201122140.
 */

package M1;

public class NodeCell {

    Node bPointer; //A pointer to another node in a B+ tree
    Leaf blPointer; //A pointer to another leaf in a B+ tree
    double rid; //The record number
    String myLabel= new String ("~");   //A specific value of the key field 
    int flag=0;    //A flag to indicate if a record was deleted or not, 0 was deleted, 1 is ok. a cell which was not yet used will get the value 0 as well 
    int ucLeaf=0; //A flag to know is this nodecell points at a leaf then ucLeaf=1 if it points to another nodeCell then ucLeaf=1
    
    //Constructor for objects of class NodeCell
    public NodeCell()
    {
        bPointer=null;
        blPointer=null;
        rid=-1;
    }
    
    //Constructor for objects of class NodeCell
    //The method receives a NodeCell that points to a node or a leaf
    //The method updates the node with that value.
    public NodeCell(NodeCell bnc1, int seeLeaf)
    {
        myLabel=bnc1.myLabel;
        flag=1;
        if (seeLeaf==0)
        { 
        	ucLeaf=0;
        	bPointer=bnc1.bPointer;
        }
        else
        {
            ucLeaf=1;
            blPointer=bnc1.blPointer;
        }   
    }

    //The method receives a number ucLeaf to indicate whether it's a leaf or a node
    public void updateUcLeaf(int whatUc)
    {
        ucLeaf=whatUc;
        flag=1;
    }
    
    //The method receives a Record id (rid) number
    //The method updates the node with that value.
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
    public void deleteBNodeCell()
    {
        flag=0;
    } 
     
}
