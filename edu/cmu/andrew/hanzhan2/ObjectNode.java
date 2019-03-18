package edu.cmu.andrew.hanzhan2;
// File: ObjectNode.java from the package edu.colorado.nodes
// Complete documentation is available from the ObjectNode link in:
//   http://www.cs.colorado.edu/~main/docs

/******************************************************************************
 * A ObjectNode provides a node for a linked list with
 * Object data in each node.
 *
 * @note
 *   Lists of nodes can be made of any length, limited only by the amount of
 *   free memory in the heap. But beyond Integer.MAX_VALUE (2,147,483,647),
 *   the answer from listLength is incorrect because of arithmetic
 *   overflow.
 *
 * @see
 *   <A HREF="../../../../edu/colorado/nodes/ObjectNode.java">
 *   Java Source Code for this class
 *   (www.cs.colorado.edu/~main/edu/colorado/nodes/ObjectNode.java) </A>
 *
 * @author Michael Main
 *   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
 *
 * @version Feb 10, 2016
 *
// * @see Node
// * @see BooleanNode
// * @see CharNode
// * @see DoubleNode
// * @see FloatNode
// * @see IntNode
// * @see LongNode
// * @see ShortNode
 ******************************************************************************/
public class ObjectNode{
    // Invariant of the ObjectNode class:
    //   1. The node's Object data is in the instance variable data.
    //   2. For the final node of a list, the link part is null.
    //      Otherwise, the link part is a reference to the
    //      next node of the list.
    private Object data;
    private ObjectNode link;


    /**
     * Initialize a node with a specified initial data and link to the next
     * node. Note that the initialLink may be the null reference,
     * which indicates that the new node has nothing after it.
     * Big Theta(1)
     *
     * @param initialData
     *   the initial data of this new node
     * @param initialLink
     *   a reference to the node after this new node--this reference may be null
     *   to indicate that there is no node after this new node.
     * @preconditions
     *  Object initialData is not null.     *
     * @postcondition
     *   This node contains the specified data and link to the next node.
     **/
    public ObjectNode(Object initialData, ObjectNode initialLink)
    {
        data = initialData;
        link = initialLink;
    }


    /**
     * Modification method to add a new node after this node.
     * Big Theata(1)
     *
     * @param item
     *   the data to place in the new node
     * @precondition
     *   Object item is not null.
     * @postcondition
     *   A new node has been created and placed after this node.
     *   The data for the new node is item. Any other nodes
     *   that used to be after this node are now after the new node.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for a new
     *   ObjectNode.
     **/
    public void addNodeAfter(Object item)
    {
        link = new ObjectNode(item, link);
    }


    /**
     * Accessor method to get the data from this node.
     * Big Theta(1)
     *
     * @return
     *   the data from this node
     **/
    public Object getData( )
    {
        return data;
    }


    /**
     * Accessor method to get a reference to the next node after this node.
     * Big Theta(1)
     *
     * @return
     *   a reference to the node after this node (or the null reference if there
     *   is nothing after this node)
     **/
    public ObjectNode getLink( )
    {
        return link;
    }


    /**
     * Copy a list.
     * Big Theta(N)
     *
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @return
     *   The method has made a copy of the linked list starting at
     *   source. The return value is the head reference for the
     *   copy.
     * @precondition Source is not null and this is not a end-to-head circular list.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     **/
    public static ObjectNode listCopy(ObjectNode source)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;

        // Handle the special case of the empty list.
        if (source == null)
            return null;

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null)
        {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head reference for the new list.
        return copyHead;
    }

    /*
     * Copy a list recursively.
     * Big Theta(N)
     *
     * @param
     *      ObjectNode source to be copied.
     * @return
     *      ObjectNode the head of the copy
     *
     */
    public static ObjectNode listCopy_rec(ObjectNode source){
        if (source == null)
            return null;
        ObjectNode head = new ObjectNode("head", null);
        listCopy_rec_helper(source, head);
        return head.link;
    }


    /*
     * Private helper method for recursive listcopy method.
     * @param
     *      source ObjectNode to be copied.
     *      head   The head ObjectNode to store the list.
     */
    private static void listCopy_rec_helper(ObjectNode source, ObjectNode head){
        if(source == null){
            return;
        }
        head.link = new ObjectNode(source.data, null);
        listCopy_rec_helper(source.link, head.link);
    }


    /**
     * Copy a list, returning both a head and tail reference for the copy.
     * Big Theta(N)
     *
     * @param source
     *   the head of a linked list that will be copied (which may be
     *   an empty list in where source is null)
     * @return
     *   The method has made a copy of the linked list starting at
     *   source.  The return value is an
     *   array where the [0] element is a head reference for the copy and the [1]
     *   element is a tail reference for the copy.
     * @precondition
     *   Source is not null and the list is not circular.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     **/
    public static ObjectNode[ ] listCopyWithTail(ObjectNode source)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Handle the special case of the empty list.
        if (source == null)
            return answer; // The answer has two null references .

        // Make the first node for the newly created list.
        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null)
        {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references.
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }


    /**
     * Compute the number of nodes in a linked list.
     * Big Theta(N)
     *
     * @param head
     *   the head reference for a linked list (which may be an empty list
     *   with a null head)
     * @return
     *   the number of nodes in the list with the given head
     * @note
     *   A wrong answer occurs for lists longer than Int.MAX_VALUE.
     **/
    public static int listLength(ObjectNode head)
    {
        ObjectNode cursor;
        int answer;

        answer = 0;
        for (cursor = head; cursor != null; cursor = cursor.link)
            answer++;

        return answer;
    }

    /*
     *  List length recursive method.
     *  Big Theta(N)
     *
     *  @param head ObjectNode to be copied
     *  @return the length of this list.
     */
    public static int listLength_rec(ObjectNode head){
        return listLength_rec_helper(head, 0);
    }

    /*
     * Private recursive helper method for recursive list length method.
     * Big Theta(N)
     *
     * @param
     *      next ObjectNode to be copied.
     *      count integer to store current counter.
     * @return
     *      Node number till now.
     */
    private static int listLength_rec_helper(ObjectNode head, int count){
        if (head == null){
            return count;
        }
        return listLength_rec_helper(head.link, count + 1);
    }

    /**
     * Copy part of a list, providing a head and tail reference for the new copy.
     * Big Theta(N)
     *
     * @precondition
     *   start and end are non-null references to nodes
     *   on the same linked list,
     *   with the start node at or before the end node.
     * @return
     *   The method has made a copy of the part of a linked list, from the
     *   specified start node to the specified end node. The return value is an
     *   array where the [0] component is a head reference for the copy and the
     *   [1] component is a tail reference for the copy.
     * @param start
     *   first node to copy
     * @param end
     *   final node to copy
     * @exception IllegalArgumentException
     *   Indicates that start and end are not references
     *   to nodes on the same list.
     * @exception NullPointerException
     *   Indicates that start is null.
     * @exception OutOfMemoryError
     *   Indicates that there is insufficient memory for the new list.
     **/
    public static ObjectNode[ ] listPart(ObjectNode start, ObjectNode end)
    {
        ObjectNode copyHead;
        ObjectNode copyTail;
        ObjectNode cursor;
        ObjectNode[ ] answer = new ObjectNode[2];

        // Make the first node for the newly created list. Notice that this will
        // cause a NullPointerException if start is null.
        copyHead = new ObjectNode(start.data, null);
        copyTail = copyHead;
        cursor = start;

        // Make the rest of the nodes for the newly created list.
        while (cursor != end)
        {
            cursor = cursor.link;
            if (cursor == null)
                throw new IllegalArgumentException
                        ("end node was not found on the list");
            copyTail.addNodeAfter(cursor.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }


    /**
     * Find a node at a specified position in a linked list.
     * Big Theta(N)
     *
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param position
     *   a node number
     * @precondition
     *   position &gt; 0.
     * @return
     *   The return value is a reference to the node at the specified position in
     *   the list. (The head node is position 1, the next node is position 2, and
     *   so on.) If there is no such position (because the list is too short),
     *   then the null reference is returned.
     * @exception IllegalArgumentException
     *   Indicates that position is not positive.
     **/
    public static ObjectNode listPosition(ObjectNode head, int position)
    {
        ObjectNode cursor;
        int i;

        if (position < 0)
            throw new IllegalArgumentException("position is not positive");

        cursor = head;
        for (i = 0; (i < position) && (cursor != null); i++)
            cursor = cursor.link;

        return cursor;
    }


    /**
     * Search for a particular piece of data in a linked list.
     * Big Theta(N)
     *
     * @param head
     *   the head reference for a linked list (which may be an empty list in
     *   which case the head is null)
     * @param target
     *   a piece of data to search for
     * @return
     *   The return value is a reference to the first node that contains the
     *   specified target. If there is no such node, the null reference is
     *   returned.
     **/
    public static ObjectNode listSearch(ObjectNode head, Object target)
    {
        ObjectNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.link)
            if (target == cursor.data)
                return cursor;

        return null;
    }


    /**
     * Modification method to remove the node after this node.
     * Big Theta(1)
     *
     * @precondition
     *   This node must not be the tail node of the list.
     * @postcondition
     *   The node after this node has been removed from the linked list.
     *   If there were further nodes after that one, they are still
     *   present on the list.
     * @exception NullPointerException
     *   Indicates that this was the tail node of the list, so there is nothing
     *   after it to remove.
     **/
    public void removeNodeAfter( )
    {
        link = link.link;
    }


    /**
     * Modification method to set the data in this node.
     * Big Theta(1)
     *
     * @param newData
     *   the new data to place in this node
     * @postcondition
     *   The data of this node has been set to newData.
     **/
    public void setData(Object newData)
    {
        data = newData;
    }


    /**
     * Modification method to set the link to the next node after this node.
     * Big Theta(1)
     *
     * @param newLink
     *   a reference to the node that should appear after this node in the linked
     *   list (or the null reference if there is no node after this node)
     * @postcondition
     *   The link to the node after this node has been set to newLink.
     *   Any other node (that used to be in this link) is no longer connected to
     *   this node.
     **/
    public void setLink(ObjectNode newLink)
    {
        link = newLink;
    }

    /*
     * Overriden toString method to convert all the nodes to string.
     * Big Theta(N)
     *
     * @return String contains all nodes' data.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        ObjectNode tmp = this;
        while (tmp != null){
            sb.append(tmp.data);
            tmp = tmp.link;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ObjectNode node = new ObjectNode("A", null);
        ObjectNode tmp = node;
        for(char c = 'B'; c <= 'Z'; c++){
            tmp.addNodeAfter(String.valueOf(c));
            tmp = tmp.link;
        }
        System.out.println(node.toString());
        System.out.println("Number of nodes is " + ObjectNode.listLength(node));
        System.out.println("Number of nodes is " + ObjectNode.listLength_rec(node));
        ObjectNode k = ObjectNode.listCopy(node);
        System.out.println(k.toString());
        System.out.println("Number of nodes in k is " + ObjectNode.listLength(k));
        System.out.println("Number of nodes in k is " + ObjectNode.listLength_rec(k));
        ObjectNode k2 = ObjectNode.listCopy_rec(node);
        System.out.println(k2.toString());
        System.out.println("Number of nodes in k2 is " + ObjectNode.listLength(k2));
        System.out.println("Number of nodes in k2 is " + ObjectNode.listLength_rec(k2));

    }
}
