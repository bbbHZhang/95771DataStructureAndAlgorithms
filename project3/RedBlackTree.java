package project3;

import RedBlackTreeSpellCheckerProject.Queue;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Han Zhang
 * 95771 HW3 - modified RebBlackTree
 * This Red Black Tree is able to handle String and BigInteger pairs
 */
public class RedBlackTree {
    private RedBlackNode root;
    private final RedBlackNode NIL = new RedBlackNode("-1", null, RedBlackNode.BLACK, null, null, null);
    private int size;
    private int recentComparision;

    /**
     *      This constructor creates an empty RedBlackTree.
     *      It creates a RedBlackNode that represents null.
     *      It sets the internal variable tree to point to this
     *      node. This node that an empty tree points to will be
     *      used as a sentinel node. That is, all pointers in the
     *      tree that would normally contain the value null, will instead
     *      point to this sentinel node.
     */
    public RedBlackTree() {
        root = NIL;
        recentComparision = 0;
    }

    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        Random r = new Random();

        for(int j = 1; j <= 50; j++) rbt.insert("var"+j,new BigInteger(10,20, r));

        System.out.println("RBT in order");
        rbt.inOrderTraversal();

        // is a random number in the tree
        int i = r.nextInt(70);
        System.out.println("Randomly searching for var");
        if(rbt.contains("var"+i)) System.out.println("Found var"+i+ ": " + rbt.find("var3").getNumber().toString());
        else System.out.println("var" +i +" not found");

    }


    /*
     * Big Theta(1)
     *
     * @return number of values inserted into the tree.
     */
    public int getSize(){
        return size;
    }

    /*
     * Big Theta(1)
     *
     * @return true if the tree is empty.
     */
    private boolean isEmpty(){
        return root == NIL;
    }


    /**
     * Find the RedBlackNode based on the String(key) given.
     * @best: Big Theta(1)
     * @worst: Big Theta(Log(n))
     *
     * @param data The key String used to search
     * @return NIL if not found. The node.
     */
    public RedBlackNode find(String data){
        recentComparision = 0;
        RedBlackNode tmp = root;
        while(tmp != NIL){
            recentComparision++;
            if(tmp.getData().compareTo(data) == 0){
                return tmp;
            } else if (tmp.getData().compareTo(data) < 0){
                tmp = tmp.getRc();
            } else {
                tmp = tmp.getLc();
            }
        }
        return NIL;
    }


    /**
     * Perfrom an inorder traversal of the tree.
     * The inOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree.
     * Big Theta(n)
     */
    public void inOrderTraversal(){
        if(isEmpty()) return;
        inOrderTraversal(root);
    }

    /**
     * Perfrom an inorder traversal of the tree.
     * The inOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree.
     * Big Theta(n)
     *
     * @param t - node to traversal
     */
    public void inOrderTraversal(RedBlackNode t) {
        inOrderTraversalHelp(t);
    }
    private void inOrderTraversalHelp(RedBlackNode n){
        if(n == NIL) return;
        inOrderTraversalHelp(n.getLc());
        printNode(n);
        inOrderTraversalHelp(n.getRc());
    }

    /*
     * Perfrom an level order traversal of the tree.
     * The levelOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree.
     * Big Theta(n)
     *
     */
    public void levelOrderTraversal(){
        Queue queue = new Queue();
        queue.enQueue(root);
        while(!queue.isEmpty()){
            RedBlackNode n = (RedBlackNode) queue.deQueue();
            printNode(n);
            if(n.getLc() != NIL) queue.enQueue(n.getLc());
            if(n.getRc() != NIL) queue.enQueue(n.getRc());
        }
    }

    /**
     * Perfrom a reverse in order traversal of the tree.
     * The levelOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree.
     * Big Theta(n)
     *
     */
    public void reverseOrderTraversal(){
        if(isEmpty()) return;
        reverseOrderTraversal(root);
    }

    /**
     * Perfrom a reverse in order traversal of the tree.
     * The levelOrderTraversal(RedBlackNode) method is recursive and displays the content of the tree.
     * Big Theta(n)
     *
     * @param t - node to traversal
     */
    public void reverseOrderTraversal(RedBlackNode t){
        if(t == NIL) return;
        reverseOrderTraversalHelper(t);
    }
    private void reverseOrderTraversalHelper(RedBlackNode n){
        if(n == NIL) return;
        reverseOrderTraversalHelper(n.getRc());
        printNode(n);
        reverseOrderTraversalHelper(n.getLc());
    }

    /**
     * Big Theta(n)
     *
     * @return the height of the tree
     */
    public int height(){
        if(root == null || root == NIL) return 0;
        return height(root);
    }

    /**
     * Big Theta(n)
     *
     * @return the height of the tree
     */
    public int height(RedBlackNode node){
        if(node == NIL) return -1;
        return Math.max(1+height(node.getLc()), 1+height(node.getRc()));
    }

    /**
     * The insert() method places a data item into the tree.
     * Big Theta(log(n))
     *
     * @param value to insert.
     * @param number BigInteger to insert
     */
    public void insert(String value, BigInteger number){
        size++;
        RedBlackNode y = NIL;
        RedBlackNode x = root;

        while(x != NIL){
            y = x;
            if(x.getData().compareTo(value) < 0){
                x = x.getRc();
            } else if (x.getData().compareTo(value) > 0){
                x = x.getLc();
            } else {
                //Modified if the value exists, overwrite the BigInteger
                x.setNumber(number);
                return;
            }
        }
        RedBlackNode toInsert = new RedBlackNode(value, number, RedBlackNode.RED, y, NIL, NIL);

        if(y == NIL){
            root = toInsert;
        } else {
            toInsert.setP(y);
            if(y.getData().compareTo(value) < 0){
                y.setRc(toInsert);
            } else {
                y.setLc(toInsert);
            }
        }
        RBInsertFixup(toInsert);


    }

    /**
     * Big Theta(1)
     *
     * @param z the node to be fixed up
     * @pre z is a valid RedBlackNode
     * @post tree will be fixed up
     */
    public void RBInsertFixup(RedBlackNode z) {
        RedBlackNode uncle;
        while(z.getP().getColor() == RedBlackNode.RED){
            if(z.getP() == z.getP().getP().getLc()){
                uncle = z.getP().getP().getRc();
                if(uncle.getColor() == RedBlackNode.RED){
                    z.getP().setColor(RedBlackNode.BLACK);
                    uncle.setColor(RedBlackNode.BLACK);
                    z.getP().getP().setColor(RedBlackNode.RED);
                    z = z.getP().getP();
                } else {
                    if(z.getP().getRc() == z){
                        z = z.getP();
                        leftRotate(z);
                    }
                    z.getP().setColor(RedBlackNode.BLACK);
                    z.getP().getP().setColor(RedBlackNode.RED);
                    rightRotate(z.getP().getP());
                }
            } else {
                uncle = z.getP().getP().getLc();
                if(uncle.getColor() == RedBlackNode.RED){
                    z.getP().setColor(RedBlackNode.BLACK);
                    uncle.setColor(RedBlackNode.BLACK);
                    z.getP().getP().setColor(RedBlackNode.RED);
                    z = z.getP().getP();
                } else {
                    if (z == z.getP().getLc()){
                        z = z.getP();
                        rightRotate(z);
                    }
                    z.getP().setColor(RedBlackNode.BLACK);
                    z.getP().getP().setColor(RedBlackNode.RED);
                    leftRotate(z.getP().getP());
                }
            }

        }
        root.setColor(RedBlackNode.BLACK);
    }

    /**
     * Big Theta(1)
     *
     * @param x  the one to be rotated
     * @pre x is a valid RedBlackNode
     * @post tree will be right rotated
     */
    public void leftRotate(RedBlackNode x){

        RedBlackNode y = x.getRc();
        x.setRc(y.getLc());
        y.getLc().setP(x);
        y.setP(x.getP());

        if(x.getP() == NIL) root = y;
        else {
            if(x == x.getP().getLc()) x.getP().setLc(y);
            else x.getP().setRc(y);
        }
        y.setLc(x);
        x.setP(y);
    }


    /**
     * Big Theta(1)
     *
     * @param x  the one to be rotated
     * @pre x is a valid RedBlackNode
     * @post tree will be right rotated
     */
    public void rightRotate(RedBlackNode x){
        RedBlackNode y = x.getLc();
        x.setLc(y.getRc());
        y.getRc().setP(x);
        y.setP(x.getP());

        if(x.getP() == NIL) root = y;
        else{
            if(x == x.getP().getLc()) x.getP().setLc(y);
            else{
                x.getP().setRc(y);
            }
        }
        y.setRc(x);
        x.setP(y);
    }

    /**
     * The method closeBy(v) returns a value close to v in the tree.
     *
     * @best: Big Theta(1)
     * @worst: Big Theta(Log(n))
     * @pre v is a String
     * @post return the value of node which is closest to the value of v
     */
    public String closeBy(String v){
        RedBlackNode ptr = root;
        RedBlackNode tmp = root;
        int lastComparasion = tmp.getData().compareTo(v);

        while(tmp != NIL){
            if(tmp.getData().compareTo(v) == 0){
                return tmp.getData();
            } else if (tmp.getData().compareTo(v) < 0){//this comparasion is negative
                if(lastComparasion > 0) return tmp.getData();
                ptr = tmp;
                tmp = tmp.getRc();
                lastComparasion = tmp.getData().compareTo(v);//set last negative
            } else {//this compare is positive
                if(lastComparasion < 0) return tmp.getData();
                ptr = tmp;
                tmp = tmp.getLc();
                lastComparasion = tmp.getData().compareTo(v);
            }
        }
        return ptr.getData();
    }

    /**
     * @best: Big Theta(1)
     * @worst: Big Theta(Log(n))
     *
     * @return true if the String v is in the RedBlackTree and false otherwise.
     * @pre v is a String
     * @post return true if the tree contains v otherwise false
     */
    public boolean contains(String v){
        recentComparision = 0;
        RedBlackNode tmp = root;
        while(tmp != NIL){
            recentComparision++;
            if(tmp.getData().compareTo(v) == 0){
                return true;
            } else if (tmp.getData().compareTo(v) < 0){
                tmp = tmp.getRc();
            } else {
                tmp = tmp.getLc();
            }
        }
        return false;
    }

    /**
     * Big Theta(1)
     * @return int recentComparision
     */
    public int	getRecentCompares(){
        return recentComparision;
    }

    /**
     * Big Theta(nlogn) in both cases
     * @return the longest road between any two nodes
     */
    public int getDiameter(){
        return getDiameter(root);
    }


    /**
     * Big Theta(n^2)
     *
     * @param node - root to get diameter
     * @return the longest road between any two nodes
     */
    public int getDiameter(RedBlackNode node){
        if(node == NIL) return 0;
        int lheight = height(node.getLc());
        int rheight = height(node.getRc());

        int ldiameter = getDiameter(node.getLc());
        int rdiameter = getDiameter(node.getRc());

        return Math.max(lheight + rheight + 2, Math.max(ldiameter, rdiameter));
    }

    /**
     * Big Theta(1)
     * Helper method to print all information about this node.
     * @param n node to be printed.
     */
    private void printNode(RedBlackNode n){
        System.out.printf("[data = %s: BigInteger = %s: Color = %s: Parent = %s: LC = %s: RC = %s]%n",
                n.getData(), n.getNumber().toString(), (n.getColor()== RedBlackNode.RED)?"Red":"Black",
                n.getP().getData(), n.getLc().getData(), n.getRc().getData());
    }
}
