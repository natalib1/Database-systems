/**
* Class Node represents a node (not a leaf) in a B+ tree.
* The node is made of (2d) mini-cells each one of LeafCell type. since in a B+ tree the number of pointers is bigger than 1 from number of values
* Each node contains between d and 2d values
* I added this Node class a pointer which is the last pointer in the node structure - therefore points to a node or a leaf whose values (String)
* are bigger than those in the last NodeCell.
* Natali Boniel, 201122140.
*/

package M1;

public class Node {
	
	final int d=2;
	NodeCell[] node1= new NodeCell[2*d];
	//Last pointer in the node structure - therefore points to a node or a leaf whose values (String) are bigger than those in the last NodeCell
	Node lastPointer=null;
	Leaf lastPointer2=null;
	Node myFatherNodeIs=null; 
	//A pointer to the father of the node to help the action "insert"
	//if my father is the root then "null"
	int count=0; //Pointer to the cell that the one before him is the last updated cell
  
    //Constructor for list of objects from type bNodeCell 
    public Node()
    {
    	for (int value=0; value<=(2*d-1); value++)
            node1[value]= new NodeCell();
    }

    //The method receives an object of bNodeCell type.
    //The method updates the data inside the node accordingly.
    public void add (NodeCell myCell, int seeLeaf)
    {
        node1[count]= new NodeCell(myCell, seeLeaf);
        count++; 
    }

    //The method receives an object of Node type.
    //The method updates the father pointer of the node so we will know who is her father
    public void updateWhoIsMyFather (Node myNode)
    {
        myFatherNodeIs=myNode; 
    }
    
    //The method receives an object of Node type.
    //The method updates the last pointer of the node so it will point to the next node.
    public void updateLastPointer (Node myNode)
    {
        lastPointer=myNode;     
    }
    
    //The method receives an object of Leaf type.
    //The method updates the last pointer of the node so it will point to the leaf.
    public void updateLastPointer (Leaf myNode)
    {
        lastPointer2=myNode;
    }
    
    //The method deletes a whole node
    public void deleteNode (Node myNode)
    {
        for(int i=0; i<=2*d-1; i++)
                myNode.node1[i].deleteBNodeCell();  
    } 

    //The method deletes specific cell inside the node
    //If this action causes the nose to be illegal, a warning appears on screen
    public void deleteCellNode (Node myNode, int loc)  //loc is the wanted location of the cell 
    {
        myNode.node1[loc].deleteBNodeCell();
        checkNode(myNode); 
    }  

    //The method checks whether the node is legal and contains at least d values
    //If node is illegal the methods prints warning on screen
    public void checkNode (Node myNode)
    {
      int sum2=0;   //A variable to know how many cells in the node are full,it should be between d and 2*d 
      for (int i=0; i<2*d; i++)
          if (myNode.node1[i].myLabel!=" ")
        	  sum2++;
      if (sum2<d)
    	  System.out.println( "This Node is illegal because it contains "+sum2+" full cells");  
    }  
	   
}
