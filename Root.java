/**
* Class Root represents a root in a B+ tree.
* The root is made of (2d) mini-cells each one of LeafCell type. since in a B+ tree the number of pointers is bigger than 1 from number of values
* Each node contains between 1 and 2d values
* Natali Boniel, 201122140.
*/

package M1;

public class Root extends Node{
  
    Node myRoot=new Node(); //The root is a node which stores between 1 and 2*d values
    Node myTree=null;       //The pointer to the tree

    //Constructor for objects of class Root
    public Root()
    {
        myRoot=new Node();
        myTree=myRoot;
    }

    //The method checks whether the root is legal and contains between 1 and 2d values
    //If root is illegal the methods prints warning on screen
    public void checkRoot (Node myNode)
    {
        int sum=0;
        for (int i=0; i<2*d; i++)
            if (myRoot.node1[i].myLabel!=" ")
                sum++;
        if (sum<1)
            System.out.println( "This Root is illegal because it is empty");
    } 
    
    //The method searches a value in the B+ tree
    //The Methods returns the pointer to the file or null
    public double searchValue (Node myRoot1, String myWord)
    {
        boolean flag=false;
        int i=0;
        Node myResult;
        while ((flag==false) && (i<2*d))  // searching the root
        {
            if (myWord.compareTo(myRoot1.node1[i].myLabel)<0)  // myWord is smaller than myLabel
            {
            	if (myRoot1.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
            		return checkValueInNode(myRoot1.lastPointer, myWord);
                else //The root points to a leaf
                    return checkValueInLeaf(myRoot1.lastPointer2, myWord);         
            }
            else //myWord is bigger or equal to myLabel
            {
            	if(i<2*d-1)
            		if (myWord.compareTo(myRoot1.node1[i+1].myLabel)<0) //myWord is smaller than myLabel of next cell
            			if (myRoot1.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
            				return checkValueInNode(myRoot1.node1[i+1].bPointer, myWord);
                        else //The root points to a leaf
                            return checkValueInLeaf(myRoot1.node1[i+1].blPointer,myWord); 
                if (i==2*d-1)
                    if (myRoot1.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
                        return checkValueInNode(myRoot1.lastPointer, myWord);
                    else //The root points to a leaf
                        return checkValueInLeaf(myRoot1.lastPointer2, myWord);             
            }   
            i++;
        }
       
        return -2;
    }
    
    //The method searches a value in the nodes B+ tree
    //The Methods returns the pointer to the file or null
    public double checkValueInNode (Node treeNode, String myWord)
    {
        boolean flag=false;
        int i=0;
        while ((flag==false) && (i<2*d))  //Searching the node
        {
        	if (myWord.compareTo(treeNode.node1[i].myLabel)<0)  //myWord is smaller than myLabel 
            {
        		i=2*d;
                if (treeNode.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
                	return checkValueInNode(treeNode.node1[i].bPointer, myWord);
                else //The root points to a leaf
                    return checkValueInLeaf(treeNode.node1[i].blPointer, myWord);    
            }
            else //myWord is lexically bigger or equal to myLabe
            {
                if(i<2*d-1)
                    if (myWord.compareTo(treeNode.node1[i+1].myLabel)<0) //myWord is smaller than myLabel of next cell
                    	if (treeNode.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
                    		return checkValueInNode(treeNode.node1[i+1].bPointer, myWord);
                        else //The root points to a leaf
                            return checkValueInLeaf(treeNode.node1[i+1].blPointer,myWord); 
                if (i==2*d-1)
                    if (treeNode.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
                        return checkValueInNode(treeNode.lastPointer, myWord);
                    else //The root points to a leaf
                        return checkValueInLeaf(treeNode.lastPointer2, myWord);            
             }   
             i++;
        }
        return -2; 
    }
    
    //The method searches a value in a leaf of the B+ tree
    //The Methods returns the pointer to the file or null
    public double checkValueInLeaf (Leaf treeLeaf, String myWord)
    {
        boolean flag=false;
        int i=0;
        Node myResult;
        while ((flag==false) && (i<2*d))  //Searching the leaf
        {
            if (myWord.compareTo(treeLeaf.leaf1[i].myLabel)==0)  //myWord is equals myLabel
            	return treeLeaf.leaf1[i].rid;          
            i++;       
        }
        if (flag==false)
            System.out.println ("Value not found in leaf");
        return -1;  
    }
    
    //The method inserts a record to the B+ tree according to B+ tree rules
    public void insert1Record (String myRecord, double myRid)
    {
    	int flag=0;
        int i=0;
        NodeCell myCell1;
        LeafCell mid;
        LeafCell myResult11=new LeafCell();
   
        while ((i<2*d) && (flag==0))
        {
            if (myRecord.compareTo(myRoot.node1[i].myLabel)<0)  //myRecord is smaller than myLabel
            {
            	if (myRoot.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf OR the tree is empty
                {
            		if(hasNoSons()==0) //The tree has no leaves and no nodes
                    {
                        myCell1= new NodeCell();
                        myCell1.updateLabel(myRecord);
                        myCell1.updateRid(myRid);
                        myCell1.updateUcLeaf(0);
                        insertValueInRoot2(myCell1,0);
                    }
                    else
                        reachTheLeaf(myRoot.node1[i].bPointer, myRecord, myRid);
                 }
                 else //The root points to a leaf 
                    insertValueInLeafToRoot(myRoot.node1[i].blPointer, myRecord, myRid,myRoot);
                 i=2*d;            
             }
             else //myRecord is bigger or equal to myLabel
             {  
                 if(i<2*d-1)
                    if (myRecord.compareTo(myRoot.node1[i+1].myLabel)<0) //myRecord is smaller than myLabel of next cell
                    {
                    	if (myRoot.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
                    		reachTheLeaf(myRoot.node1[i+1].bPointer, myRecord, myRid);
                        else /* The root points to a leaf */    
                            insertValueInLeafToRoot(myRoot.node1[i+1].blPointer,myRecord, myRid,myRoot);
                        flag=1;
                    }
                 //Now we are left with last cell, where myRecord is not smaller than the label in the last full cell
                 //I check the last pointer in the node
                 if (i==2*d-1)
                 {
                    if (myRoot.lastPointer!=null)  //The root points to a node which is not a leaf
                        reachTheLeaf(myRoot.lastPointer, myRecord, myRid);
                    else //The root points to a leaf OR the root has no nodes and leaves
                    {
                        if (myRoot.lastPointer2!=null)
                        	insertValueInLeafToRoot(myRoot.lastPointer2, myRecord, myRid, myRoot);
                        else //if the tree has no leaves and  no nodes
                        {
                            myCell1= new NodeCell();
                            myCell1.updateLabel(myRecord);
                            myCell1.updateRid(myRid);
                            myCell1.updateUcLeaf(0);
                            insertValueInRoot2(myCell1,0); 
                        } 
                    }
                    flag=1;
                 }
             }  
             i++;
        }      
    }
        
    //The method inserts a value into a leaf of the B+ tree.
    //he leaf values are sorted
    //The methods returns a leafCell that has to be updated in the parent of this leaf
    //otherwise if there is no need, according to B+ tree law, it returns null
    public void insertValueInLeafToRoot (Leaf treeLeaf, String myRecord, double myRid,  Node specificRoot)
    {
       LeafCell myCell1;
       LeafCell myCell2=new LeafCell(); //The cell that goes up after creating another array of leafcells and dividing the values between it and the original leaf
       Leaf myAddition;
       Leaf myAddition2;
       int i=d;  //d small values stay in the old leaf, all the rest move to the new Leaf "myAddition"
                 //therefore the first cell I move is cell number d
       int smallerThanD=0;
       //A parameter to know if the value I need to add will be in the first leaf where the cellnumber of the full cells will
       //be less than d, or the second leaf that contains the cells from original array that number of cell >=d
                 
       if (treeLeaf.leaf1[2*d-1].rid==(-1)) //there is free space in the leaf
       {
           //Building a new record according to parameters given
           myCell1= new LeafCell();
           myCell1.updateLabel(myRecord);
           myCell1.updateRid(myRid);
           treeLeaf.add(myCell1);      //Adding the new record
           treeLeaf=sortMe(treeLeaf);
        }
        else  //There is no place in the leaf for a new value
        {
	        myAddition= new Leaf();  //Creating a new Leaf
	        myAddition2= new Leaf(); //Creating a new Leaf
	        myCell2=treeLeaf.leaf1[i];
	        
	        //Finding the location of the new tree leaf I want to create between him and the 4 cells in the root
	        for(int k=0; k<d; k++)
	        {
	        	if (myRecord.compareTo(treeLeaf.leaf1[k].myLabel)<0) //myRecord is smaller than myLabel of next cell 
	        		smallerThanD=1;
	        }
	        
	        if (smallerThanD==0)  //The cell should be in the right leaf
	        {
	            //Moving values from the original root to a new leaf
                for(; i<2*d; i++) 
                {
                    myAddition.leaf1[myAddition.count].updateRid(treeLeaf.leaf1[i].rid);
                    myAddition.leaf1[myAddition.count].updateLabel(treeLeaf.leaf1[i].myLabel);
                    myAddition.count=myAddition.count+1;
                    treeLeaf.deleteLeaf(treeLeaf,i);   //Delete specific cell in treeLeaf
                }
                //The addition of the cell given to the correct leaf
                myAddition.leaf1[myAddition.count].updateRid(myRid);
                myAddition.leaf1[myAddition.count].updateLabel(myRecord);
                myAddition.count=myAddition.count+1;
                
                sortMe(myAddition); //Sorting the right leaf
                     
                //Updating the new leaf to know who is his father
                myAddition.updateWhoIsLeafFather(treeLeaf.myFatherNodeIs);
                insertLeafCellInRoot(myAddition, 0,specificRoot); //The value that goes up to the root
             }   
             else  //The cell should be in the left leaf
             {
                //Moving values from the original root to a new leaf
                for(int b=d-1; b<2*d; b++) 
                {
                    myAddition2.leaf1[myAddition2.count].updateRid(treeLeaf.leaf1[b].rid);
                    myAddition2.leaf1[myAddition2.count].updateLabel(treeLeaf.leaf1[b].myLabel);
                    myAddition2.count=myAddition2.count+1;
                    treeLeaf.deleteLeaf(treeLeaf,b);   //Delete specific cell in treeLeaf
                }
                       
                //Addition of the cell given
                treeLeaf.leaf1[treeLeaf.count].myLabel=myRecord;
                treeLeaf.leaf1[treeLeaf.count].rid=myRid;
                treeLeaf.count=treeLeaf.count+1;
              
                sortMe(treeLeaf); //Sorting the right leaf
              
                //Updating the father of the new leaf
                myAddition2.updateWhoIsLeafFather(treeLeaf.myFatherNodeIs);
                insertLeafCellInRoot(myAddition2, 0, specificRoot);
            }
        }
    }
      
    //The method sorts the leaf cells in the B+ tree
    public Leaf sortMe(Leaf treeLeaf)
    {
       String minimum;
       int place=0;
       for (int i=0; i<2*d-1; i++)
       {
          minimum=treeLeaf.leaf1[i].myLabel;
          for(int j=i+1; j<2*d; j++)
              if (minimum.compareTo(treeLeaf.leaf1[j].myLabel)>0) //minimum is bigger than specific string in array
                    place=j;
          if (i!=place)
          {
                //doing the swap between the 2 strings 
                String swap=treeLeaf.leaf1[place].myLabel;  
                treeLeaf.leaf1[place].myLabel=minimum;
                minimum=treeLeaf.leaf1[place].myLabel;
          }
       }
       return treeLeaf;
    }
        
    //The method inserts a value only to the root of the B+ tree (leaves cell type)
    //According to B+ tree rules
    //The root values are sorted
    public void insertValueInRoot (LeafCell myMid, int cLeaf2)
    {
       Root OldRoot=new Root(); //A tree root parameter to save the old root when the tree is added anew level, meaning a new root
       NodeCell myCell1;
       Leaf myAddLeaf;
       Leaf myAddLeaf2;
       int i=d;  //d small values stay in the old leaf, all the rest move to the new Leaf "myAddition"
                 //therefore the first cell I move is cell number d 
       int smallerThanD=0;
       //A parameter to know if the value I need to add will be in the first leaf where the cellnumber of the full cells will
       //be less than d, or the second leaf that contains the cells from original array that number of cell >=d 
       
       if (myRoot.node1[2*d-1].rid!=(-1)) //There is place in the root for a new value
       {
           //Building a new record according to parameters given
           NodeCell myNodeC=new NodeCell();
           myNodeC.updateUcLeaf(cLeaf2);
           myNodeC.updateRid(myMid.rid);
           myNodeC.updateLabel(myMid.myLabel);
           myRoot.add(myNodeC,cLeaf2);      //Adding the new record
           myRoot.node1=sortMe2(myRoot.node1);
        }
        else
        //There is no place in the root for a new value 
        //therefore we need to establish 2 leaves that will be connected to a root according to B+ tree rules
         {
            myAddLeaf= new Leaf(); //Creating a new leaf
            myAddLeaf2= new Leaf(); //Creating a new leaf
            Node myNewNewRoot= new Node(); //Building a new root
            
            //Finding the location of myMid between him and the 4 cells in the root
            for(int k=0; k<d; k++)
            {
            	if (myMid.myLabel.compareTo(myRoot.node1[k].myLabel)<0) //myMid is lexically smaller than myLabel of next cell
            		smallerThanD=1;
            }
            
            if (smallerThanD==0)  //The cell should be in the right leaf
            {
                //Moving values from the  original root to  a new leaf
                for(; i<2*d; i++) 
                {
                    myAddLeaf.leaf1[i].updateRid(myRoot.node1[i].rid);
                    myAddLeaf.leaf1[i].updateLabel(myRoot.node1[i].myLabel);
                    myAddLeaf.count=myAddLeaf.count+1;
                    myRoot.deleteCellNode(myRoot,i);   //Delete specific cell in root
                }
                myAddLeaf.add(myMid); //Addition of the cell given
                
                sortMe(myAddLeaf); //Sorting the right leaf
           
                //Updating the new root with cells from leaves according to B+ tree laws    
                myNewNewRoot.add(myRoot.node1[0],1);
                int myLocc=myNewNewRoot.count-1; /*saving the location of the cell in which data came from cell in myRoot*/
                myNewNewRoot.node1[count].rid=myAddLeaf.leaf1[0].rid;
                myNewNewRoot.node1[count].myLabel=myAddLeaf.leaf1[0].myLabel;
                myNewNewRoot.node1[count].ucLeaf=1;
                myNewNewRoot.node1[count].blPointer=myAddLeaf;
               
                OldRoot.myRoot=this.myRoot;
                myRoot=myNewNewRoot;
                myAddLeaf.updateWhoIsLeafFather(myRoot);
                Leaf OldLeaf1=new Leaf();
                
                //Changing the Node OldRoot to a leaf "OldLeaf1"
                //myRoot was left with only d-1 full cells
                for(int j=0; j<d; j++)
                {
                    OldLeaf1.leaf1[OldLeaf1.count].updateRid(OldRoot.myRoot.node1[j].rid);
                    OldLeaf1.leaf1[OldLeaf1.count].updateLabel(OldRoot.myRoot.node1[j].myLabel);
                    OldLeaf1.count=OldLeaf1.count+1;
                }
                
                OldLeaf1.updateWhoIsLeafFather(myRoot); 
                myNewNewRoot.node1[myLocc].blPointer=OldLeaf1; 
                myRoot=myNewNewRoot; //So the new root will know that OldLeaf is his son
            }
            else  //The cell should be in the left leaf
            {
                //Moving values from the  original root to  a new leaf
                for(int b=d-1; b<2*d; b++) 
                {
                    myAddLeaf2.leaf1[myAddLeaf2.count].updateRid(myRoot.node1[b].rid);
                    myAddLeaf2.leaf1[myAddLeaf2.count].updateLabel(myRoot.node1[b].myLabel);
                    myAddLeaf2.count=myAddLeaf2.count+1;
                    myRoot.deleteCellNode(myRoot,b);   //Delete specific cell in root
                }
                
                //Transforming the Node "OldRoot" to a leaf "OldLeaf2" while keeping the data
                //myRoot was left with only d-2 full cells */
                Leaf OldLeaf2=new Leaf();
                for(int j=0; j<d-1; j++)
                {
                    OldLeaf2.leaf1[j].updateRid(myRoot.node1[j].rid);
                    OldLeaf2.leaf1[j].updateLabel(myRoot.node1[j].myLabel);
                    OldLeaf2.count=OldLeaf2.count+1;
                }
    
                OldLeaf2.add(myMid); //Addition of the cell given
               
                sortMe(OldLeaf2); //Sorting the right leaf
       
                //Updating the new root with cells from leaves according to B+ tree laws   
                myNewNewRoot.add(myRoot.node1[0],1);
                int myLocc2=myNewNewRoot.count-1;
                myNewNewRoot.node1[myNewNewRoot.count].rid=myAddLeaf2.leaf1[0].rid;
                myNewNewRoot.node1[myNewNewRoot.count].myLabel=myAddLeaf2.leaf1[0].myLabel;
                myNewNewRoot.node1[myNewNewRoot.count].ucLeaf=1;
                myNewNewRoot.node1[myNewNewRoot.count].blPointer=myAddLeaf2;
                myNewNewRoot.node1[myLocc2].blPointer=OldLeaf2;
        
                //Updating the new root of the entire tree
                myRoot=myNewNewRoot;
                myAddLeaf.updateWhoIsLeafFather(myRoot);
                OldLeaf2.updateWhoIsLeafFather(myRoot);
            }
        }
    }
    
    //The method inserts a value in a root of the B+ tree (NodeCell type)
    //According to B+ tree rules
    //The root values are sorted
    public void insertValueInRoot2 (NodeCell myNodeC1, int cLeaf2)
    {
       Root OldRoot=new Root(); //A tree root parameter to save the old root when the tree is added anew level, meaning a new root
       Leaf myAddLeaf;
       Leaf myAddLeaf2;
       int i=d;  //d small values stay in the old leaf, all the rest move to the new Leaf "myAddition"
                 //therefore the first cell I move is cell number d 
       int smallerThanD=0;
       //A parameter to know if the value I need to add will be in the first leaf where the cellnumber of the full cells will
       //be less than d, or the second leaf that contains the cells from original array that number of cell >=d 
                 
       if (myRoot.node1[2*d-1].rid==(-1)) //The root is not full
       {
           //Building a new record according to parameters given
           myRoot.add(myNodeC1,cLeaf2);      //Adding the new record
           myRoot.node1=sortMe2(myRoot.node1);
       } 
       else
       //There is no place in the root for a new value
       //therefore we need to establish 2 leaves that will be connected to a root according to B+ tree rules
       {
           myAddLeaf= new Leaf(); //Creating a new leaf
           myAddLeaf2= new Leaf(); //Creating a new leaf 
           Node myNewNewRoot= new Node(); //Building a new root
            
           //Finding the location of myMid between him and the 4 cells in the root
           for(int k=0; k<d; k++)
        	   if (myNodeC1.myLabel.compareTo(myRoot.node1[k].myLabel)<0) //myMid is smaller than myLabel of next cell 
        		   smallerThanD=1;
           
           if (smallerThanD==0)  //The cell should be in the right leaf
           {
               //Moving values from the original root to a new leaf
                for(; i<2*d; i++) 
               {
                   myAddLeaf.leaf1[i].updateRid(myRoot.node1[i].rid);
                   myAddLeaf.leaf1[i].updateLabel(myRoot.node1[i].myLabel);
                   myAddLeaf.count=myAddLeaf.count+1;
                   myRoot.deleteCellNode(myRoot,i);   //Delete specific cell in root
               }
                
               //Addition of the cell given
               myAddLeaf.leaf1[myAddLeaf.count].updateRid(myNodeC1.rid);
               myAddLeaf.leaf1[myAddLeaf.count].updateLabel(myNodeC1.myLabel);
               myAddLeaf.count=myAddLeaf.count+1;
               sortMe(myAddLeaf); //Sorting the right leaf
           
               //Updating the new root with cells from leaves according to B+ tree laws    
               myNewNewRoot.add(myRoot.node1[0],1);
               int myLoca1=myNewNewRoot.count-1;
               myNewNewRoot.node1[count].rid=myAddLeaf.leaf1[0].rid;
               myNewNewRoot.node1[count].myLabel=myAddLeaf.leaf1[0].myLabel;
               myNewNewRoot.node1[count].ucLeaf=1;
               myNewNewRoot.node1[count].blPointer=myAddLeaf;               
               OldRoot.myRoot=this.myRoot;
               myRoot=myNewNewRoot;
               myAddLeaf.updateWhoIsLeafFather(myRoot);
               Leaf OldLeaf1=new Leaf();
              
               //Changing the Node OldRoot to a leaf "OldLeaf1"
               //myRoot was left with only d-1 full cells
               for(int j=0; j<d; j++)
                {
                   OldLeaf1.leaf1[OldLeaf1.count].updateRid(OldRoot.myRoot.node1[j].rid);
                   OldLeaf1.leaf1[OldLeaf1.count].updateLabel(OldRoot.myRoot.node1[j].myLabel);
                   OldLeaf1.count=OldLeaf1.count+1;
               }
                
               OldLeaf1.updateWhoIsLeafFather(myRoot);
               myNewNewRoot.node1[myLoca1].blPointer=OldLeaf1;
               myRoot=myNewNewRoot;
           }
           else  //The cell should be in the left leaf
           {
               //moving values from the  original root to  a new leaf
        	   for(int b=d-1; b<2*d; b++) 
               {
                   myAddLeaf2.leaf1[myAddLeaf2.count].updateRid(myRoot.node1[b].rid);
                   myAddLeaf2.leaf1[myAddLeaf2.count].updateLabel(myRoot.node1[b].myLabel);
                   myAddLeaf2.count=myAddLeaf2.count+1;
                   myRoot.deleteCellNode(myRoot,b);   //Delete specific cell in root
               }
                    
               //Transforming the Node "OldRoot" to a leaf "OldLeaf2" while keeping the data
               //myRoot was left with only d-2 full cells
               Leaf OldLeaf2=new Leaf();
               for(int j=0; j<d-1; j++)
               {
                   OldLeaf2.leaf1[j].updateRid(myRoot.node1[j].rid);
                   OldLeaf2.leaf1[j].updateLabel(myRoot.node1[j].myLabel);
                   OldLeaf2.count=OldLeaf2.count+1;
               }
            
              //Addition of the cell given
              OldLeaf2.leaf1[OldLeaf2.count].updateRid(myNodeC1.rid);
              OldLeaf2.leaf1[OldLeaf2.count].updateLabel(myNodeC1.myLabel);
              OldLeaf2.count=myAddLeaf.count+1;
             
              sortMe(OldLeaf2); //Sorting the right leaf
       
              //Updating the new root with cells from leaves according to B+ tree laws     
              myNewNewRoot.add(myRoot.node1[0],1);
              int myLoca2=myNewNewRoot.count-1;
              myNewNewRoot.node1[myNewNewRoot.count].rid=myAddLeaf2.leaf1[0].rid;
              myNewNewRoot.node1[myNewNewRoot.count].myLabel=myAddLeaf2.leaf1[0].myLabel;
              myNewNewRoot.node1[myNewNewRoot.count].ucLeaf=1;
              myNewNewRoot.node1[myNewNewRoot.count].blPointer=myAddLeaf2;
              myNewNewRoot.node1[myLoca2].blPointer=OldLeaf2;
                  
	          //Updating the new root of the entire tree
	          myRoot=myNewNewRoot;
	          myAddLeaf.updateWhoIsLeafFather(myRoot);
	          OldLeaf2.updateWhoIsLeafFather(myRoot);
           }
        }         
    }
      
    //A special method for reachTheLeaf function
    //It receives a leaf, record,rid, place in array and a Father node
    //It will put this leaf in correct place and will keep updating nodes and root if needed
    public void insertValueInLeafReachFunction (Leaf treeLeaf, String myRecord, double myRid, Node specificRoot)
    {
       LeafCell myCell1;
       LeafCell myCell2=new LeafCell(); //The cell that goes up after creating another array of leafcells and dividing the values between it and the original leaf
       Leaf myAddition;
       Leaf myAddition2;
       int i=d;  //d small values stay in the old leaf, all the rest move to the new Leaf "myAddition"
                 //therefore the first cell I move is cell number d
       int smallerThanD=0;
       //A parameter to know if the value I need to add will be in the first leaf where the cellnumber of the full cells will
       //be less than d, or the second leaf that contains the cells from original array that number of cell >=d  
                 
       if (treeLeaf.leaf1[2*d-1].rid==(-1)) //There is free space in the leaf
       {
            //Building a new record according to parameters given 
            myCell1= new LeafCell();
            myCell1.updateLabel(myRecord);
            myCell1.updateRid(myRid);
            treeLeaf.add(myCell1);      //Adding the new record
            treeLeaf=sortMe(treeLeaf);
       }
       else  //There is no place in the leaf for a new value
       {
	        myAddition= new Leaf(); //Creating a new Leaf
	        myAddition2= new Leaf(); //Creating a new Leaf
	        myCell2=treeLeaf.leaf1[i];
	        
	        //Finding the location of the new tree leaf I want to create between him and the 4 cells in the root
            for(int k=0; k<d; k++)
            	if (myRecord.compareTo(treeLeaf.leaf1[k].myLabel)<0) //myRecord is  smaller than myLabel of next cell  
            		smallerThanD=1;
            
            if (smallerThanD==0)  //The cell should be in the right leaf
            {
                //Moving values from the  original leaf to  a new leaf 
                for(; i<2*d; i++) 
                {
                    myAddition.leaf1[myAddition.count].updateRid(treeLeaf.leaf1[i].rid);
                    myAddition.leaf1[myAddition.count].updateLabel(treeLeaf.leaf1[i].myLabel);
                    myAddition.count=myAddition.count+1;
                    treeLeaf.deleteLeaf(treeLeaf,i);   //Delete specific cell in treeLeaf
                }
                
                //The addition of the cell given to the correct leaf
                myAddition.leaf1[myAddition.count].updateRid(myRid);
                myAddition.leaf1[myAddition.count].updateLabel(myRecord);
                myAddition.count=myAddition.count+1;
                
                sortMe(myAddition); //Sorting the right leaf
                
                //Updating the new leaf to know who is his father
                myAddition.updateWhoIsLeafFather(treeLeaf.myFatherNodeIs);
                insertLeafCellInNode(myAddition, 0,specificRoot); //The value that goes up to the root
            }   
            else  //The cell should be in the left leaf
            {
                //Moving values from the original root to a new leaf
                for(int b=d-1; b<2*d; b++) 
                {
                    myAddition2.leaf1[myAddition2.count].updateRid(treeLeaf.leaf1[b].rid);
                    myAddition2.leaf1[myAddition2.count].updateLabel(treeLeaf.leaf1[b].myLabel);
                    myAddition2.count=myAddition2.count+1;
                    treeLeaf.deleteLeaf(treeLeaf,b);   //Delete specific cell in treeLeaf
                }
                              
                //Addition of the cell given
                treeLeaf.leaf1[treeLeaf.count].myLabel=myRecord;
                treeLeaf.leaf1[treeLeaf.count].rid=myRid;
                treeLeaf.count=treeLeaf.count+1;
                
                sortMe(treeLeaf); //Sorting the right leaf
                
                //Updating the father of the new leaf
                myAddition2.updateWhoIsLeafFather(treeLeaf.myFatherNodeIs);
                insertLeafCellInNode(myAddition2, 0, specificRoot);   
            }
        } 
    }
       
    //The method sorts the node/root cells in the B+ tree
    //In Selection sort method, avg running time O(n^2)
    public NodeCell[] sortMe2(NodeCell[] myN1)
    {
    	String minimum;
        int place=0;
        for (int i=0; i<2*d; i++)
        {
        	minimum=myN1[i].myLabel;
        	for(int j=i+1; j<2*d; j++)
        		if (minimum.compareTo(myN1[j].myLabel)>0) //minimum is bigger than specific string in array
        			place=j;
            if (i!=place)
            {
                //Doing the swap between the 2 strings  
                String swap=myN1[place].myLabel;  
                myN1[place].myLabel=minimum;
                minimum=myN1[place].myLabel;
            }
        }
        return myN1;
    } 
    
    //The method receives a Node, a string,and r-id (integer number)
    //The method searches the correct leaf to build a Leaf cell (that includes the string and r-id number) in the right place in the tree.
    //The method will update this leaf in the right place according to B+ tree rules.
    //It will also update the relevant nodes and root if necessary according to B+ tree rules.
    public void reachTheLeaf(Node Rooty, String myRecord, double myRid)
    {
    	//Finding the specific pointer in Root to the next node or leaf
        int i=0;
        int place1=0; //The place of the specific leafCell
      
        //Searching the specific leaf */   
        while (i<2*d)
        {
            if (myRecord.compareTo(Rooty.node1[i].myLabel)<0)  //myRecord is smaller than myLabel
            {
            	if (Rooty.node1[i].ucLeaf==0)  //The node points to a node which is not a leaf
            		reachTheLeaf(Rooty.node1[i].bPointer, myRecord, myRid);
            	else //we reached the leaf
                {
            		place1=i;
                    i=2*d;
                }                               
            }
            else  //myRecord is lexically bigger or equal to myLabel
            {  
                if(i<2*d-1)
                    if (myRecord.compareTo(Rooty.node1[i+1].myLabel)<0) //myRecord is lexically smaller than myLabel of next cell 
                    {
                    	if (Rooty.node1[i].ucLeaf==0)  //The node points to a node which is not a leaf
                    		reachTheLeaf(Rooty.node1[i+1].bPointer, myRecord, myRid);
                        else //The node points to a leaf
                        {
                            place1=i+1;
                            i=2*d;
                        }
                    } 
   
                //Now we are left with last cell, where myRecord is not smaller than the label in the last full cell  
                //therefore I check the last pointer in the node
                if (i==2*d-1)
                {
                    if (Rooty.lastPointer!=null)  //The root points to a node which is not a leaf 
                        reachTheLeaf(Rooty.lastPointer, myRecord, myRid);
                    else //The root points to a leaf
                        place1=2*d-1;          
                }     
            }
            i++;           
        }
        insertValueInLeafReachFunction(Rooty.node1[place1].blPointer,myRecord, myRid,Rooty);
    }

    //The method checks whether the tree root has no nodes and no leaves beside himself.
    //The method returns 0 if there are no sons, otherwise returns the number of sons
    private int hasNoSons ()
    {
       int noSon=0;
       int runner=0;
       for(; runner<2*d; runner++)
            if ((myRoot.node1[runner].bPointer!=null) || (myRoot.node1[runner].blPointer!=null))
                    noSon++;
       return noSon;
    }

    //The method inserts a value to the root of the B+ tree (leafcell type)
    //he method receives a Leaf variable and an integer location of the specific cell
    //According to B+ tree rules
    //The root values are sorted
    private void insertLeafCellInRoot (Leaf myMid, int loc, Node specificRoot)
    {
       Root OldRoot=new Root(); //A tree root parameter to save the old root when the tree is added anew level, meaning a new root
       NodeCell myCell1;
       Leaf myAddLeaf;
       Leaf myAddLeaf2;
       int i=d;  //d small values stay in the old leaf, all the rest move to the new Leaf "myAddition"
                 //therefore the first cell I move is cell number d
       int smallerThanD=0;
       //A parameter to know if the value I need to add will be in the first leaf where the cellnumber of the full cells will
       //be less than d, or the second leaf that contains the cells from original array that number of cell >=d  
       
       if (specificRoot.node1[2*d-1].rid!=(-1)) //There is place in the root for a new value
       {
           //Building a new record according to parameters given
           NodeCell myNodeC=new NodeCell();
           myNodeC.updateUcLeaf(1);
           myNodeC.updateRid(myMid.leaf1[loc].rid);
           myNodeC.updateLabel(myMid.leaf1[loc].myLabel);
           specificRoot.add(myNodeC,1);      //Adding the new record
           specificRoot.node1[count-1].blPointer=myMid; //Updating the root who is his new son
           specificRoot.node1=sortMe2(specificRoot.node1);
       }
       else
       //There is no place in the root for a new value
       //therefore we need to establish 2 leaves that will be connected to a root according to B+ tree rules
       {
           myAddLeaf= new Leaf(); //Creating a new leaf
           myAddLeaf2= new Leaf(); //Creating a new leaf
           Node myNewNewRoot= new Node();  //Building a new root
            
           //Finding the location of myMid between him and the 4 cells in the root
           for(int k=0; k<d; k++)
                  if (myMid.leaf1[loc].myLabel.compareTo(specificRoot.node1[k].myLabel)<0) //myMid is smaller than myLabel of next cell 
                        smallerThanD=1;
           
           if (smallerThanD==0)  //The cell should be in the right leaf
           {
                //Moving values from the  original root to  a new leaf
                for(; i<2*d; i++) 
                {
                    myAddLeaf.leaf1[i].updateRid(specificRoot.node1[i].rid);
                    myAddLeaf.leaf1[i].updateLabel(specificRoot.node1[i].myLabel);
                    myAddLeaf.count=myAddLeaf.count+1;
                    myRoot.deleteCellNode(specificRoot,i);   //Delete specific cell in root
                }
                
                myAddLeaf.add(myMid.leaf1[loc]); //Addition of the cell given
                
                sortMe(myAddLeaf); //Sorting the right leaf
           
                //Updating the new root with cells from leaves according to B+ tree laws   
                myNewNewRoot.add(specificRoot.node1[0],1);
                int myLocc=count-1;
              
                myNewNewRoot.node1[count].rid=myAddLeaf.leaf1[0].rid;
                myNewNewRoot.node1[count].myLabel=myAddLeaf.leaf1[0].myLabel;
                myNewNewRoot.node1[count].ucLeaf=1;
                myNewNewRoot.node1[count].blPointer=myAddLeaf;
               
                myNewNewRoot.count=myNewNewRoot.count+1;
                OldRoot.myRoot=specificRoot;
                specificRoot=myNewNewRoot;
                myAddLeaf.updateWhoIsLeafFather(specificRoot);
                Leaf OldLeaf1=new Leaf();
                
                //Changing the Node OldRoot to a leaf "OldLeaf1"
                //yRoot was left with only d-1 full cells
                for(int j=0; j<d; j++)
                {
                    OldLeaf1.leaf1[OldLeaf1.count].updateRid(OldRoot.myRoot.node1[j].rid);
                    OldLeaf1.leaf1[OldLeaf1.count].updateLabel(OldRoot.myRoot.node1[j].myLabel);
                    OldLeaf1.count=OldLeaf1.count+1;
                }
                
                OldLeaf1.updateWhoIsLeafFather(specificRoot); 
                myNewNewRoot.node1[myLocc].blPointer=OldLeaf1; 
                specificRoot=myNewNewRoot;
                //So the new root will know that OldLeaf is his son
             }
             else  //The cell should be in the left leaf
             {
                //Moving values from the original root to a new leaf
                for(int b=d-1; b<2*d; b++) 
                {
                    myAddLeaf2.leaf1[myAddLeaf2.count].updateRid(specificRoot.node1[b].rid);
                    myAddLeaf2.leaf1[myAddLeaf2.count].updateLabel(specificRoot.node1[b].myLabel);
                    myAddLeaf2.count=myAddLeaf2.count+1;
                    specificRoot.deleteCellNode(specificRoot,b);   //Delete specific cell in root
                }
                
               //Transforming the Node "OldRoot" to a leaf "OldLeaf2" while keeping the data
               //myRoot was left with only d-2 full cells
               Leaf OldLeaf2=new Leaf();
               for(int j=0; j<d-1; j++)
               {
                    OldLeaf2.leaf1[j].updateRid(specificRoot.node1[j].rid);
                    OldLeaf2.leaf1[j].updateLabel(specificRoot.node1[j].myLabel);
                    OldLeaf2.count=OldLeaf2.count+1;
               }
    
               OldLeaf2.add(myMid.leaf1[loc]); //Addition of the cell given
              
               sortMe(OldLeaf2); //Sorting the right leaf
       
               //Updating the new root with cells from leaves according to B+ tree laws     
               myNewNewRoot.add(specificRoot.node1[0],1);
               int myLocc2=myNewNewRoot.count-1;
               myNewNewRoot.node1[myNewNewRoot.count].rid=myAddLeaf2.leaf1[0].rid;
               myNewNewRoot.node1[myNewNewRoot.count].myLabel=myAddLeaf2.leaf1[0].myLabel;
               myNewNewRoot.node1[myNewNewRoot.count].ucLeaf=1;
               myNewNewRoot.node1[myNewNewRoot.count].blPointer=myAddLeaf2;
               myNewNewRoot.node1[myLocc2].blPointer=OldLeaf2;
               
	           //Updating the new root of the entire tree
	           specificRoot=myNewNewRoot;
	           myAddLeaf.updateWhoIsLeafFather(specificRoot);
	           OldLeaf2.updateWhoIsLeafFather(specificRoot);
           }
       } 
   }
    
   //The method inserts a leafCell into a node of the B+ tree (leafcell type)
   //The method receives a Leaf variable and an integer location of the specific cell according to B+ tree rules
   //The root values are sorted
   private void insertLeafCellInNode (Leaf myMid, int loc, Node specificRoot)
   {
       Node OldRoot=new Node(); //A tree root parameter to save the old root when the tree is added anew level, meaning a new root
       NodeCell myCell1;
       Leaf myAddLeaf;
       Leaf myAddLeaf2;
       int i=d;  //d small values stay in the old leaf, all the rest move to the new Leaf "myAddition"
                 //therefore the first cell I move is cell number d
       int smallerThanD=0;
       //A parameter to know if the value I need to add will be in the first leaf where the cellnumber of the full cells will
       //be less than d, or the second leaf that contains the cells from original array that number of cell >=d 
       
      if (specificRoot.node1[2*d-1].rid!=(-1)) //There is place in the root for a new value
      {
    	  //Building a new record according to parameters given
    	  NodeCell myNodeC=new NodeCell();
    	  myNodeC.updateUcLeaf(1);
    	  myNodeC.updateRid(myMid.leaf1[loc].rid);
    	  myNodeC.updateLabel(myMid.leaf1[loc].myLabel);
    	  specificRoot.add(myNodeC,1);      //Adding the new record
    	  specificRoot.node1[count-1].blPointer=myMid; //Updating the root who is his new son
    	  specificRoot.node1=sortMe2(specificRoot.node1);
      }
      else
      //There is no place in the root for a new value
      //therefore we need to establish 2 leaves that will be connected to a root according to B+ tree rules
      {
    	  myAddLeaf= new Leaf(); //Creating a new leaf
    	  myAddLeaf2= new Leaf(); //Creating a new leaf
    	  Node myNewNewRoot= new Node();  //Building a new root
        
          //Finding the location of myMid between him and the 4 cells in the root
          for(int k=0; k<d; k++)
              if (myMid.leaf1[loc].myLabel.compareTo(specificRoot.node1[k].myLabel)<0) //myMid is lexically smaller than myLabel of next cell
                    smallerThanD=1;
          
          if (smallerThanD==0)  //The cell should be in the right leaf
          {
        	  //Moving values from the  original root to  a new leaf
        	  for(; i<2*d; i++) 
        	  {
        		  myAddLeaf.leaf1[i].updateRid(specificRoot.node1[i].rid);
        		  myAddLeaf.leaf1[i].updateLabel(specificRoot.node1[i].myLabel);
        		  myAddLeaf.count=myAddLeaf.count+1;
        		  specificRoot.deleteCellNode(specificRoot,i);   //Delete specific cell in root
        	  }
        	  
        	  myAddLeaf.add(myMid.leaf1[loc]); //Addition of the cell given
        	  
        	  sortMe(myAddLeaf); //Sorting the right leaf
       
        	  //Updating the new root with cells from leaves according to B+ tree laws   
              myNewNewRoot.add(specificRoot.node1[0],1);
              int myLocc=count-1;
              
              myNewNewRoot.node1[count].rid=myAddLeaf.leaf1[0].rid;
              myNewNewRoot.node1[count].myLabel=myAddLeaf.leaf1[0].myLabel;
              myNewNewRoot.node1[count].ucLeaf=1;
              myNewNewRoot.node1[count].blPointer=myAddLeaf;
               
              myNewNewRoot.count=myNewNewRoot.count+1;
              OldRoot=specificRoot;
              specificRoot=myNewNewRoot;
              myAddLeaf.updateWhoIsLeafFather(specificRoot);
              Leaf OldLeaf1=new Leaf();
              //Changing the Node OldRoot to a leaf "OldLeaf1" 
              //specificRoot was left with only d-1 full cells
              
              for(int j=0; j<d; j++)
              {
            	  OldLeaf1.leaf1[OldLeaf1.count].updateRid(OldRoot.node1[j].rid);
            	  OldLeaf1.leaf1[OldLeaf1.count].updateLabel(OldRoot.node1[j].myLabel);
            	  OldLeaf1.count=OldLeaf1.count+1;
              }
              OldLeaf1.updateWhoIsLeafFather(specificRoot); 
              myNewNewRoot.node1[myLocc].blPointer=OldLeaf1; 
              specificRoot=myNewNewRoot;
              //So the new root will know that OldLeaf is his son
          }
          else  //The cell should be in the left leaf
          {
              //Moving values from the  original root to  a new leaf
        	  for(int b=d-1; b<2*d; b++) 
        	  {
        		  myAddLeaf2.leaf1[myAddLeaf2.count].updateRid(specificRoot.node1[b].rid);
         	      myAddLeaf2.leaf1[myAddLeaf2.count].updateLabel(specificRoot.node1[b].myLabel);
         	      myAddLeaf2.count=myAddLeaf2.count+1;
         	      specificRoot.deleteCellNode(specificRoot,b);   //Delete specific cell in root
        	  }
            
              //Transforming the Node "OldRoot" to a leaf "OldLeaf2" while keeping the data
              //myRoot was left with only d-2 full cells
              Leaf OldLeaf2=new Leaf();
              for(int j=0; j<d-1; j++)
              {
            	  OldLeaf2.leaf1[j].updateRid(specificRoot.node1[j].rid);
            	  OldLeaf2.leaf1[j].updateLabel(specificRoot.node1[j].myLabel);
            	  OldLeaf2.count=OldLeaf2.count+1;
              }
    
              OldLeaf2.add(myMid.leaf1[loc]); //Addition of the cell given
              
              sortMe(OldLeaf2); //Sorting the right leaf
       
              //Updating the new root with cells from leaves according to B+ tree laws  
              myNewNewRoot.add(specificRoot.node1[0],1);
              int myLocc2=myNewNewRoot.count-1;
              myNewNewRoot.node1[myNewNewRoot.count].rid=myAddLeaf2.leaf1[0].rid;
              myNewNewRoot.node1[myNewNewRoot.count].myLabel=myAddLeaf2.leaf1[0].myLabel;
              myNewNewRoot.node1[myNewNewRoot.count].ucLeaf=1;
              myNewNewRoot.node1[myNewNewRoot.count].blPointer=myAddLeaf2;
              myNewNewRoot.node1[myLocc2].blPointer=OldLeaf2;
              
              //Updating the new root of the entire tree
              specificRoot=myNewNewRoot;
              myAddLeaf.updateWhoIsLeafFather(specificRoot);
              OldLeaf2.updateWhoIsLeafFather(specificRoot);
          }
      }
   }

   //The method inserts a value to a node of the B+ tree 
   //The method receives a Leaf variable and an integer location of the specific cell according to B+ tree rules
   //The root values are sorted
   private void insertNodeInRoot (Node myMid, int loc, Node specificRoot)
   {
       Node OldRoot=new Node(); //A tree root parameter to save the old root when the tree is added anew level, meaning a new root
       NodeCell myCell1;
       Node myAddNode;
       Node myAddNode2;
       int i=d;  //d small values stay in the old leaf, all the rest move to the new Leaf "myAddition"
                 //therefore the first cell I move is cell number d
       int smallerThanD=0;
       //A parameter to know if the value I need to add will be in the first leaf where the cellnumber of the full cells will
       //be less than d, or the second leaf that contains the cells from original array that number of cell >=d  
       
       if (specificRoot.node1[2*d-1].rid!=(-1)) //There is place in the node for a new value
       {
           //Building a new record according to parameters give
           specificRoot.add(myMid.node1[loc],1);      //Adding the new record
           specificRoot.node1[count-1].bPointer=myMid; //Updating the root who is his new son
           specificRoot.node1=sortMe2(specificRoot.node1);
       }
       else
       //There is no place in the node for a new value
       //therefore we need to establish 2 nodes that will be connected to a specificRoot
       {
           myAddNode= new Node(); //Creating a new Node
           myAddNode2= new Node(); //Creating a new Node  
           Node myNewNewRoot= new Node();  //Building a new node
            
           //Finding the location of myMid between him and the 4 cells in the root
           for(int k=0; k<d; k++)
        	   if (myMid.node1[loc].myLabel.compareTo(specificRoot.node1[k].myLabel)<0) /* myMid is lexically smaller than myLabel of next cell  */
        		   smallerThanD=1;

           if (smallerThanD==0)  //The cell should be in the right node
           {
        	   //Moving values from the  original node to  a new node
        	   for(; i<2*d; i++) 
        	   {
        		   myAddNode.node1[i].updateRid(specificRoot.node1[i].rid);
        		   myAddNode.node1[i].updateLabel(specificRoot.node1[i].myLabel);
        		   myAddNode.count=myAddNode.count+1;
        		   specificRoot.deleteCellNode(specificRoot,i);   //Delete specific cell in root
        	   }
        	   
        	   myAddNode.add(myMid.node1[loc],0); //Addition of the cell given
        	   
        	   sortMe2(myAddNode.node1); //Sorting the right leaf
       
        	   //Updating the new root with cells from leaves according to B+ tree laws    
        	   myNewNewRoot.add(specificRoot.node1[0],1);
               int myLocc=count-1;
              
               myNewNewRoot.node1[myLocc]=myAddNode.node1[0];
               myNewNewRoot.node1[count].bPointer=myAddNode;
               myNewNewRoot.count=myNewNewRoot.count+1;
               
               OldRoot=specificRoot;
               specificRoot=myNewNewRoot;
               myNewNewRoot=OldRoot;
               myAddNode.updateWhoIsMyFather(specificRoot);
               myNewNewRoot.updateWhoIsMyFather(specificRoot);
           }
           else  //the cell should be in the left node
           {
               //moving values from the  original root to  a new node
        	   for(int b=d-1; b<2*d; b++) 
        	   {
        		   myAddNode2.node1[myAddNode2.count].updateRid(specificRoot.node1[b].rid);
        		   myAddNode2.node1[myAddNode2.count].updateLabel(specificRoot.node1[b].myLabel);
        		   myAddNode2.count=myAddNode2.count+1;
        		   specificRoot.deleteCellNode(specificRoot,b);   //delete specific cell in root
        	   }
                    
        	   specificRoot.node1[specificRoot.count].updateRid( myMid.node1[loc].rid); //Addition of the cell given
        	   specificRoot.node1[specificRoot.count].updateLabel(myMid.node1[loc].myLabel);
        	   
        	   sortMe2(specificRoot.node1); //Sorting the left Node
       
        	   //Updating the new root with cells from leaves according to B+ tree laws   
               myNewNewRoot.add(specificRoot.node1[0],1);
               int myLocc2=myNewNewRoot.count-1;
               myNewNewRoot.add(myAddNode2.node1[0], 0);
            
               //Updating the new root of the entire tree
               OldRoot=specificRoot;
               specificRoot=myNewNewRoot;
               myNewNewRoot=OldRoot;
               myAddNode2.updateWhoIsMyFather(specificRoot);
               myNewNewRoot.updateWhoIsMyFather(specificRoot);
           }
       }
   }

   //The method deletes a record in a node/root of the B+ tree according to B+ tree rules
   //The node values are sorted
   public void deleteRecordInNode (Node myN3, String myRecord)
   {
       double myRid2;
       //Finding the rid number of the wanted cell that contains myRecord string
       myRid2=searchValue(myN3, myRecord);
       int runner=0;
       while (runner<2*d)
            if (myN3.node1[runner].rid==myRid2)
                {
                    myN3.node1[runner].flag=0;   //Delete that cell
                    runner=2*d;             //Stopping the loop
                }
       for (int i=0; i<2*d; i++)
       {
    	   if (myN3.node1[i].ucLeaf==0)  
                deleteRecordInNode(myN3.node1[i].bPointer, myRecord);
    	   else
                deleteRecordInLeaf(myN3.node1[i].blPointer, myRecord);
       }
   }
    
   //The method deletes a record in a node/root of the B+ tree according to B+ tree rules
   //he node values are sorted
   private void deleteRecordInLeaf (Leaf myL3, String myRecord)
   {
       double myRid2;
       //Finding the rid number of the wanted cell that contains myRecord string
       myRid2=checkValueInLeaf(myL3, myRecord);
       int runner=0;
       while (runner<2*d)
            if (myL3.leaf1[runner].rid==myRid2)
                {
                    myL3.leaf1[runner].flag=0;   //Delete that cell
                    runner=2*d;   //Stopping the loop
                }    
    }
    
   //The method searches all records between myR1 and myR2 in the B+ tree
   //Assumption myR1<myR2 lexically
   //The methods prints on screen all rid values
   public void searchInRange (Node myT1, String myR1, String myR2)
   {
	   boolean flag=false;
	   int i=0;
	   Node myResult;
	   while ((flag==false) && (i<2*d))  //Searching the root
       {
		   if ( myR1.compareTo(myT1.node1[i].myLabel)<0)  //myR1 is smaller than myLabel
		   {
			   i=2*d;
               if (myT1.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
            	   searchInRange(myT1.node1[i].bPointer, myR1, myR2);
               else //The root points to a leaf
            	   printMyLeaf(myT1.node1[i].blPointer, myR1,myR2);     
           }
           else //myR1 is bigger or equal to myLabel
           {  
               if(i<2*d-1)
                   if (myR1.compareTo(myT1.node1[i+1].myLabel)<0) //myR1 is smaller than myLabel of next cell
                	   if (myT1.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
                		   searchInRange(myT1.node1[i+1].bPointer, myR1, myR2);
                       else //The root points to a leaf
                           printMyLeaf(myT1.node1[i+1].blPointer,myR1, myR2); 
               if (i==2*d-1)
                   if (myT1.node1[i].ucLeaf==0)  //The root points to a node which is not a leaf
                       searchInRange(myT1.lastPointer, myR1, myR2);
                    else //The root points to a leaf
                       printMyLeaf(myT1.lastPointer2, myR1, myR2);   
           }   
           i++;
       }
   }
    
   //The method prints records in a leaf of the B+ tree that hold strings between myR1 and myR2.
   //It prints their position in RecordFile and the string itself.
   public void printMyLeaf(Leaf myL3, String myR1, String myR2)
   {
       double myRid2;
       int runner=0;
       while (runner<2*d)
       {
            if ((myR1.compareTo(myL3.leaf1[runner].myLabel)<=0) && (myR2.compareTo(myL3.leaf1[runner].myLabel)>=0))
            {
               System.out.println ("the rid number is:"+myL3.leaf1[runner].rid);
               System.out.println ("and the label is:"+myL3.leaf1[runner].myLabel);
            }
            runner++;
       }
   }
       
}
