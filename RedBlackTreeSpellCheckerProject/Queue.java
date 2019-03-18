package RedBlackTreeSpellCheckerProject;

import java.util.NoSuchElementException;
/*
 * 95771 - Homework2 Part1 Queue
 * A simple Queue class using array
 *
 * @author Han Zhang
 */
public class Queue {
    /**
     * Constant for default capacity of the underlying array.
     */
    private static final int DEFAULT_CAPACITY = 5;
    /**
     * Array of elements.
     */
    private Object[] que;
    /**
     * location of front element.
     */
    private int front;
    /**
     * location of back element.
     */
    private int back;
    /**
     * number of items in the queue.
     */
    private int nItems;

    /**
     * Constructs a new queue with default capacity.
     */
    public Queue(){
        que = new Object[DEFAULT_CAPACITY];
        back = -1;
        front = 0;
        nItems = 0;
        int[] A = new int[]{1,2,3};

    }

    /**
     * Gets and deletes an item from the front of the queue in O(1).
     * @return the first item in the queue
     * @throws NoSuchElementException indicates that queue is empty
     */
    public Object deQueue(){
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        // need to wrap around
        int index = front % que.length;
        Object item = que[index];
        // make sure to delete the item
        que[index] = null;
        // front moves up to the right
        front++;
        nItems--;
        return item;

    }

    /**
     * Enqueues a new item to the back of the queue in O(1).
     * @param x a new item to add
     */
    public void enQueue(Object x){
        if (isFull()) {
            enlargQue();
        }
        // back moves up
        back++;
        // need to wrap around
        que[back % que.length] = x;
        nItems++;

    }

    /**
     * Helper method to double the array size
     */
    private void enlargQue(){
        Object[] tmp = que;
        que = new Object[tmp.length * 2];
        if(front % tmp.length > back % tmp.length){
            front = front % tmp.length;
            back = back % tmp.length;
            System.out.printf("length: %s, front: %d. back; %d%n", tmp.length, front, back);
            System.out.printf("length - front: %d. back + 1; %d%n", tmp.length - front, back + 1);
            System.arraycopy(tmp, front % tmp.length, que , 0,  tmp.length - front);
            System.arraycopy(tmp, 0, que , tmp.length - front ,  back + 1);
        }else{
            System.arraycopy(tmp, 0, que , 0,  tmp.length);
        }

        front = 0;
        back = tmp.length - 1;
    }

    /**
     * Gets an item from the front of the queue in O(1) but does not delete it.
     * @return the first item in the queue
     */
    public Object getFront(){
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return que[front % que.length];
    }

    /**
     * Checks if the queue is empty or not in O(1).
     * @return true if the queue is empty, false if not
     */
    public boolean isEmpty(){
        return nItems == 0;
    }

    /**
     * Private helper method to check if queue is full or not.
     * @return true if it is full and false if not
     */
    public boolean isFull(){
        return nItems == que.length;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = front; i <= back; i++){
            sb.append(que[i%que.length]).append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Queue q = new Queue();
        for(int i = 0; i < 10; i++) q.enQueue(i);
        for(int i = 0; i < 10; i++) q.deQueue();
    }
}
