package RedBlackTreeSpellCheckerProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Han Zhang
 * 95771 HW2 Part 1 - Red Black Tree - RebBlackTreeSpellChecker
 */
public class RedBlackTreeSpellChecker {
    RedBlackTree tree = new RedBlackTree();
    File f;
    public RedBlackTreeSpellChecker(File f){
        this.f = f;
        Scanner scanner = null;
        try {
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scanner.hasNext()){
            tree.insert(scanner.next().replace('.', ' ').trim());
        }
    }

    public static void main(String[] args) {
        File f = new File(args[0]);

        RedBlackTreeSpellChecker check = new RedBlackTreeSpellChecker(f);
        check.printAllSelections();

        Scanner reader;
        boolean notQuit = true;
        while(notQuit){
            reader = new Scanner(System.in);
            String input = reader.nextLine();
            char choice = input.charAt(0);
            switch (choice){
                case 'd'://display the entire word tree with a level order traversal
                    check.tree.levelOrderTraversal();
                    break;
                case 's'://print the words of the tree in sorted order (using an inorder traversal
                    check.tree.inOrderTraversal();
                    break;
                case 'r'://reverse inorder traversal
                    check.tree.reverseOrderTraversal();
                    break;
                case 'c'://spell check this word
                    check.wordSpellCheck(input);
                    break;
                case 'a':
                    String word = input.split(" ")[1];
                    if(check.tree.contains(word)){
                        System.out.printf("The word \"%s\" is already in the dictionary%n", word);
                    }else {
                        check.tree.insert(word);
                        System.out.printf("%s was added to dictionary%n", word);
                    }
                    break;
                case 'f'://check file for spelling error
                    check.fileSpellCheck(input);
                    break;
                case 'i':
                    System.out.println("The diameter is " +check.tree.getDiameter());
                    break;
                case 'm'://view the menu
                    check.printAllSelections();
                    break;
                case '!':
                    System.out.println("Bye.");
                    notQuit = false;
                    break;
            }
        }
    }

    private void wordSpellCheck(String input){
        String[] strings = input.split(" ");
        if(!strings[0].equalsIgnoreCase("c")){
            System.out.println("something wrong with c");
            return;
        }
        String toFind = strings[1];
        if(tree.contains(toFind)){
            System.out.println("Find " + toFind + " after " + tree.getRecentCompares() + " comparisons");
        }else{
            System.out.printf("%s Not in dictionary. Perhaps you mean%n%s", toFind, tree.closeBy(toFind));
        }
    }

    private void fileSpellCheck(String input){
        String[] strings = input.split(" ");
        if(!strings[0].equalsIgnoreCase("f")){
            System.out.println("something wrong with f");
            return;
        }
        String file = strings[1];
        try {
            Scanner fileReader = new Scanner(new File(file));
            boolean noSpellError = true;
            while(fileReader.hasNext()){
                String word = fileReader.next().replace('.', ' ').trim();
                if(!tree.contains(word)){
                    noSpellError = false;
                    System.out.printf("\"%s\" was not found in ditionary%n", word);
                }
            }
            if(noSpellError){
                System.out.println("No spelling errors found.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void printAllSelections(){
        System.out.println("java RedBlackTreeSpellChecker " + f.toString());
        System.out.println("Loading a tree of English words from " + f.toString());
        System.out.println("Red Black Tree is loaded with " + tree.getSize() + " words");
        System.out.println("Initial tree height is " + tree.height());
        System.out.println("Never worse than 2 * Lg( n+1) = " + 2 * (double)Math.log(tree.getSize() + 1)/Math.log(2));
        System.out.println("Legal commands are: \n" +
                "d   display the entire word tree with a level order traversal.\n" +
                "s    print the words of the tree in sorted order (using an inorder traversal).\n" +
                "r    print the words of the tree in reverse sorted order (reverse inorder traversal). \n" +
                "c   <word> to spell check this word.\n" +
                "a   <word> add this word to tree.\n" +
                "f   <fileName> to check this text file for spelling errors.\n" +
                "i   display the diameter of the tree.\n" +
                "m  view this menu.\n" +
                "!   to quit.\n");
    }
}
