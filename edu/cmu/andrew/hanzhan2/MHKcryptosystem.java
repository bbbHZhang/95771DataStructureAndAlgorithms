package edu.cmu.andrew.hanzhan2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class MHKcryptosystem {
    private final SinglyLinkedList w;
    private final SinglyLinkedList publicSequenceReversed;
    private String s;
    private BigInteger r;
    private BigInteger q;
    private BigInteger ciphertext;
    private Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Enter a string and I will encrypt it as single large integer.");
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++){
            if(String.valueOf(str.charAt(i)).matches("[a-zA-Z ]")) {
                sb.append(str.charAt(i));
            }
        }
        System.out.println("Clear text:");
        System.out.println(sb.toString());
        System.out.println("Number of clear text bytes = " + str.getBytes().length);
        str = sb.toString();
        MHKcryptosystem m = new MHKcryptosystem(str);
        System.out.println(str + " is encrypted as");
        System.out.println(m.encrypted());
        System.out.println("Result of decryption: " + m.decrypted());
    }

    public MHKcryptosystem(String input){
        s = input;
        //create the private key: superincreasing sequence w,
        w = new SinglyLinkedList();
        publicSequenceReversed = new SinglyLinkedList();
        int n = s.getBytes().length * 8;
        BigInteger tmp = new BigInteger(n, random);
        BigInteger sum = BigInteger.ZERO;
        while(n != 0){
            sum = sum.add(tmp);
            w.addAtFrontNode(tmp);
            tmp = sum.add(new BigInteger(n, random)).add(BigInteger.ONE);
            n--;
        }
        //calculate the random number q
        q = sum.add(new BigInteger(n, random)).add(BigInteger.ONE);
        //find r, the coprime number of q
        r = q.subtract(BigInteger.ONE);
        while(!r.gcd(q).equals(BigInteger.ONE)){
            r = r.subtract(BigInteger.ONE);
        }
        w.reset();
        //Create publicSequence with r and w
        while(w.hasNext()){
            BigInteger b = r.multiply((BigInteger)w.next());
            publicSequenceReversed.addAtEndNode(b.mod(q));
        }
        IOException e = new IOException();
        e.getStackTrace();
    }

    public String encrypted(){
        //ciphertext is the public long number
        ciphertext = BigInteger.ZERO;

        //convert input string to binary string
        String msgBinary = new BigInteger(s.getBytes()).toString(2);
        if (msgBinary.length() < publicSequenceReversed.countNodes()) {
            msgBinary = String.format("%0" + (publicSequenceReversed.countNodes() - msgBinary.length()) + "d", 0) + msgBinary;
        }

        //calculate the ciphertext
        publicSequenceReversed.reset();
        for(int i = 0; i < msgBinary.length(); i++){
            BigInteger tmp = BigInteger.valueOf(Character.getNumericValue(msgBinary.charAt(i)));
            tmp = tmp.multiply((BigInteger) publicSequenceReversed.next());
            ciphertext = ciphertext.add(tmp);
        }
        return ciphertext.toString();
    }

    public String decrypted(){
        //convert ciphertext to the long number used to decrypted
        BigInteger tmp = ciphertext.multiply(r.modInverse(q)).mod(q);
        byte[] result_binary = new byte[publicSequenceReversed.countNodes()];

        //the long number is moded by all numbers and staring from the biggest one
        w.reset();
        int index = 0;
        while(w.hasNext()){
            BigInteger tmpBI = (BigInteger) w.next();
            if(tmp.compareTo(tmpBI) >= 0){
                result_binary[index] = 1;
                tmp = tmp.subtract(tmpBI);
            } else {
                result_binary[index] = 0;
            }
            index++;
        }
        //convert the result to string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result_binary.length; i++) {
            sb.append(result_binary[i]);
        }

        return new String(new BigInteger(sb.toString(), 2).toByteArray());

    }


}
