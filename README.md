# Database-systems
Realization of a B+ tree

java

The program uses one file to the tree "pages" (nodes). 
A second file is the main data file, which contains data records. 
The main data file will is accepted as input to the program and the program build the index for it in a separate file.

3 kind of pages:

1. Title page (one), to store general information about the structure of the tree file, and what is the main file that the index points to
2. Pages representing internal nodes in which the voters will be pages of the appropriate child nodes
3. Pages that represent leaves in which the voters will be the appropriate record identifiers in the data file

The program include the following actions and allow the user to: 

1. Load an index for a given data file as user input. The file is built from records of a fixed length, and all fields are the same type. The first field is the key field on which to build the index. build the index - by reading in the loop for record entry operation added as described below, for all entries in the input file

2. A file entry is found in the search field. If the value is in the index, the action returns the full record from the file. otherwise the action returns a message that the value was not found

3.  Add a file entry to the file, as well as the index. The user will be able to enter data for a new record into the program file. Add the record to the file and add an appropriate index entry to the tree (including browsing and splitting handling, as needed).

4.  Delete from the file and the index. The user can delete an entry according to the value of the search field. Deleting is only done by marking the entry in the file and in the index

5. Scan and display the file in the index's sort order
