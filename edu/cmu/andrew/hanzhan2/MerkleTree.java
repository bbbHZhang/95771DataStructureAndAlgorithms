package edu.cmu.andrew.hanzhan2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * This is the part 3 of hw 1 for 95771, Data Structure and Algorithms.
 *
 * @author Han Zhang hanzhang
 *
 * CrimeLatLonXY.csv is the answer.
 */
public class MerkleTree {
    /*
     * Local invariants
     * lists is used to store all the lists
     * root is the root of Merkle Tree
     */
    private SinglyLinkedList lists = new SinglyLinkedList();
    private String root;

    /*Constructor*/
    public static void main(String[] args){
        MerkleTree mt = new MerkleTree();
        try {
            mt.readFile("C:\\Users\\Han\\eclipse-workspace\\95771Projects\\src\\edu\\cmu\\andrew\\hanzhan2\\Project1-CrimeLatLonXY.csv");
            mt.hashToRoot();
            System.out.println(mt.root);
            System.out.println("A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    /*
     * Hash function.
     *
     */
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash =
                digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public void readFile(String filename) throws IOException {
        BufferedReader in = null;
        SinglyLinkedList list0 = new SinglyLinkedList();
        try {
            //read file
            File f = new File(filename);
            System.out.println(filename);
            in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF8"));
            String str;
            //add all lines to the first list
            while((str = in.readLine()) != null){
                list0.addAtEndNode(str);
            }
            //check even and hash for the second list
            list0 = checkEven(list0);
            list0.reset();
            SinglyLinkedList list1 = new SinglyLinkedList();
            while(list0.hasNext()){
                list1.addAtEndNode(h((String) list0.next()));
            }
            lists.addAtEndNode(list0);
            lists.addAtEndNode(list1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void hashToRoot() throws NoSuchAlgorithmException {
        lists.reset();
        lists.next();
        SinglyLinkedList tmpList = (SinglyLinkedList) lists.next();
        //hash to the root node
        while(tmpList.countNodes() >= 2) {
            //toAdd is the new SLL
            SinglyLinkedList toAdd = new SinglyLinkedList();
            //hash tmpList and store it in toAdd
            tmpList.reset();
            while (tmpList.hasNext()) {
                toAdd.addAtEndNode(h((String) tmpList.next() + (String) tmpList.next()));
            }
            if (toAdd.countNodes() != 1 && toAdd.countNodes() % 2 != 0) {
                toAdd = checkEven(toAdd);
            }
            lists.addAtEndNode(toAdd);
            tmpList = toAdd;
        }
        tmpList.reset();
        root = (String) tmpList.next();
    }

    private SinglyLinkedList checkEven(SinglyLinkedList list){
        //used to deal with list with odd number of nodes
        list.reset();
        int counter = 0;
        String last = null;
        while(list.hasNext()){
            last = (String) list.next();
            counter++;
        }
        if(counter % 2 != 0){
            list.addAtEndNode(last);
        }
        return list;
    }

}
