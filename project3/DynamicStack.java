package project3;

/**
 * 95771 HW3 DynamicStack class.
 * @author Han Zhang hanzhan2
 *
 * This class is the implementation of an array-bsed stack.
 * Initial size of the array is 6. Size will double when full.
 */
public class DynamicStack {
    /** objects array hold all pushed objects */
    private Object[] objects;
    /** top pointer points to the top object */
    private int top;
    /** initial length is 6 */
    private final int INITIAL_LENGTH = 6;

    /**
     * Big Theta(1)
     *
     * Constructor. Initialize the object array and the top pointer.
     */
    public DynamicStack(){
        objects = new Object[INITIAL_LENGTH];
        top = -1;
    }

    /**
     * @best: Big Theta(1)
     * @worst: Big Theta(n)
     *
     * Push object into stack. Double the size if full.
     * @param object to be pushed.
     */
    public void push(Object object){
        top++;
        if(top >= objects.length){
            doubleArray();
        }
        objects[top] = object;
    }

    /**
     * Big Theta(n)
     *
     * Double array size and copy the old objects into the new array.
     */
    private void doubleArray(){
        Object[] tmp = objects;
        objects = new Object[tmp.length * 2];
        System.arraycopy(tmp,0, objects,0, tmp.length);
    }

    /**
     * Big Theta(1)
     * Return the object on the top of stack
     *
     * @return Object at the top position
     */
    public Object pop(){
        if(isEmpty()) return null;
        return objects[top--];
    }

    /**
     * Big Theta(1)
     * To judge whether the stack is empty.
     *
     * @return true if stack is empty, otherwise false.
     */
    public boolean isEmpty(){
        return top == -1;
    }

    public static void main(String[] args) {
        DynamicStack ds = new DynamicStack();
        for(int i = 1; i <= 1000; i++){
            ds.push(i);
        }
        System.out.println("1000 values have been pushed!");
        System.out.println("This stack contains: " + ds.toString());
        System.out.println("Now poping and displaying.");
        int i = 0;
        while(!ds.isEmpty()){
            System.out.print(ds.pop());
            System.out.print(" ");
            i++;
            if (i % 100 ==0) System.out.println();
        }

    }

    /**
     * Big Theta(n)
     *
     * @return [object1, object2, object3...]
     */
    @Override
    public String toString(){
        String str = "";
        for(int i = 0; i <= top; i++){
            str += objects[i];
            str += ", ";
        }
        return str;
    }
}
