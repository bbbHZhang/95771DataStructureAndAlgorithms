package edu.cmu.andrew.hanzhan2;

import java.math.BigInteger;
import java.util.NoSuchElementException;

/*
@author Han Zhang hanzhan2 but adapted from Michael Main's class.

SinglyLinkedList has a head and tail pointer.

Head and tail are both ObjectNodes.

Class invariants:


This class provides iterator methods:

reset: to reset the iteration - starting from the head of the list.

next: returns the element pointed to by the iterator and increments the iterator to the next node

hasNext: returns true if the iterator points to an existing node and false otherwise.
 */
public class SinglyLinkedList {
    /*
     * The head pointer is null or points to the first element on the list.
     * The tail pointer is null or points to the last node on the list.
     * An integer countNodes is maintained to keep count of the number of nodes added to the list.
     * This provided an O(1) count to the caller.
     */
    private ObjectNode head;
    private ObjectNode tail;
    private int countNodes;
    private ObjectNode nextNode;

    public static void main(String[] args) {
        SinglyLinkedList sll1 = new SinglyLinkedList();
        BigInteger tmp = BigInteger.ZERO;
        System.out.println("Add 0 1 2 3 4 at end node");
        System.out.println("Add 5 6 7 8 9 at front node");
        for(int i = 0; i < 5; i++){
            sll1.addAtEndNode(tmp);
            tmp = tmp.add(BigInteger.ONE);
        }
        for(int i = 0; i < 5; i++){
            sll1.addAtFrontNode(tmp);
            tmp = tmp.add(BigInteger.ONE);
        }
        System.out.println("toString method: " + sll1.toString());
        System.out.println("countNode method: " + sll1.countNodes());
        System.out.println("getLast method: " + sll1.getLast());
        System.out.println("getObjectAt(3): " + sll1.getObjectAt(3));
        System.out.println("Use iterators to print all node: ");
        sll1.reset();
        while (sll1.hasNext()){
            System.out.print(sll1.next() + " ");
        }

    }
    // Constructor
    public SinglyLinkedList(){
        countNodes = 0;
    }

    //Add a node containing the Object c to the end of the linked list.
    public void addAtEndNode(Object o){
        if(o == null) return;
        if (head == null){
            head = new ObjectNode(o, null);
            tail = head;
            countNodes++;
            return;
        }
        ObjectNode tmp = new ObjectNode(o, null);
        tail.setLink(tmp);
        tail = tmp;
        countNodes++;
        return;
    }

    //Add a node containing the Object c to the head of the linked list.
    public void addAtFrontNode(Object o){
        if(o == null) return;
        ObjectNode tmp = new ObjectNode(o, null);
        if (head == null){
            head = tmp;
            tail = head;
            countNodes++;
            return;
        }
        head = new ObjectNode(o, head);
        countNodes++;
        return;
    }

    //Counts the number of nodes in the list.
    public int countNodes(){
        return countNodes;
    }

    //Returns the data in the tail of the list.
    public Object getLast(){
        return tail.getData();
    }

    //Returns a reference (0 based) to the object with list index i.
    public Object getObjectAt(int i){
        if (i < 0) throw new IllegalArgumentException("i should be greater than 0");
        if (i == 0) return head.getData();
        ObjectNode tmp = head;
        while(i != 0 && tmp != null){
            tmp = tmp.getLink();
            i--;
        }
        if (i != 0) throw new IllegalArgumentException("i is bigger than the total amount of nodes");
        return tmp.getData();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        ObjectNode tmp = head;
        sb.append(tmp.getData()).append(" ");
        while(tmp.getLink() != null){
            tmp = tmp.getLink();
            sb.append(tmp.getData()).append(" ");
        }
        return sb.toString();
    }


    /**
     * Checks whether there is next node or not.
     * @return true if there is or false if not
     */
    public boolean hasNext() {
        return nextNode != null;
    }

    /**
     * Returns the next node's data.
     * @return AnyType data of the next node
     */
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object result = nextNode.getData();
        nextNode = nextNode.getLink();
        return result;
    }
    /*
     * Reset the iterator pointer to the head.
     */
    public void reset(){
        nextNode = head;
    }



}
