package project3;

import java.math.BigInteger;
import java.util.Scanner;

public class ReversePolishNotation {
    private RedBlackTree valuePairs = new RedBlackTree();

    public static void main(String[] args) {
        ReversePolishNotation notation = new ReversePolishNotation();
        notation.start();
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("java ReversePolishNotation ");
        String nextLine = scanner.nextLine();
        //keep reading valid inputs
        while(nextLine != null && nextLine.length() > 0){
            try {
                processInput(nextLine);
            } catch (Exception e) {
                System.out.println("Exception in thread \"main\" java.lang.Exception: " +
                    "error: " + e.getMessage());
            }
            nextLine = scanner.nextLine();
        }
        System.out.println("Terminating");
    }

    private void processInput(String nextLine) throws Exception {
        //create an operand stack for each input
        DynamicStack operandStack = new DynamicStack();

        String[] inputs = nextLine.split("\\s+");
        for (String input : inputs) {
            if (Character.isDigit(input.charAt(0)) || Character.isLetter(input.charAt(0))) {
                //a number or variable
                operandStack.push(input);
            } else if (input.matches("[\\+\\-\\*\\/\\%\\=\\~\\#]")) {
                //a operation
                calculate(operandStack, input);
            } else {
                throw new Exception("Invalid input!");
            }
        }
        String res = getNextOperand(operandStack).toString();
        if(!operandStack.isEmpty()){
            throw new Exception("Stack overflow!");
        }
        System.out.println(res);
    }

    private void calculate(DynamicStack operandStack, String operator) throws Exception {

        String operand = (String) operandStack.pop();
        if(operand == null) throw new Exception("Invalid input!");

        BigInteger operand0;
        //get the first operand0
        if(Character.isLetter(operand.charAt(0))){ //operand is variable
            if(!valuePairs.contains(operand)) throw new Exception("No variable " + operand);
            operand0 = valuePairs.find(operand).getNumber();
        } else {//operand is number
            operand0 = new BigInteger(operand);
        }

        BigInteger result = null;

        switch(operator){
            case "~":
                result = operand0.multiply(BigInteger.valueOf(-1));
                break;
            case "#":
                BigInteger bi2 = getNextOperand(operandStack);
                BigInteger bi3 = getNextOperand(operandStack);
                result = bi3.pow(bi2.intValue()).mod(operand0);
                break;
            case "=":
                String var = (String) operandStack.pop();
                if(var == null) throw new Exception("Stack underflow exception");
                if(!Character.isLetter(var.charAt(0))) throw new Exception(var + " not a variable");
                result = operand0;
                valuePairs.insert(var, operand0);
                break;
            case "+":
                result = operand0.add(getNextOperand(operandStack));
                break;
            case "-":
                result = getNextOperand(operandStack).subtract(operand0);
                break;
            case "*":
                result = getNextOperand(operandStack).multiply(operand0);
                break;
            case "/":
                result = getNextOperand(operandStack).divide(operand0);
                break;
            case "%":
                result = getNextOperand(operandStack).mod(operand0);
                break;
        }
        if(result != null) operandStack.push(result.toString());
        else throw new Exception("Something wrong with result");
    }

    private BigInteger getNextOperand(DynamicStack operandStack) throws Exception {
        String str1 = (String) operandStack.pop();
        if(str1 == null) throw new Exception("Stack Underflow exception");

        BigInteger bi2;
        if(Character.isLetter(str1.charAt(0))){
            if(valuePairs.contains(str1)) {
                bi2 = valuePairs.find(str1).getNumber();
            } else throw new Exception(str1 + " not a variable");
        } else {
            bi2 = new BigInteger(str1);
        }
        return bi2;
    }

}
