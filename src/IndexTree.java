import java.io.*;
import java.util.Scanner;
import java.lang.*;


public class IndexTree {


    private IndexNode root;



    public IndexTree() {
        this.root = null;
    }

    public IndexNode node() {
        return this.root;
    }


    public void add(String word, int lineNumber){
        this.root = add(this.root, word,lineNumber);
    }


    private IndexNode add(IndexNode root, String word, int lineNumber){


        if(root == null){
            return new IndexNode(word, lineNumber);
        }
        int comparisonResult = word.compareTo(root.word);

        if(comparisonResult == 0){
            root.occurences++;
            root.list.add(lineNumber);
            return root;

        }else if(comparisonResult < 0){
            root.left = add(root.left, word, lineNumber);
            return root;

        }else{
            root.right = add(root.right, word, lineNumber);
            return root;
        }
    }


    public boolean contains(String word){ // returns true if the word is in the index
        return contains(this.root, word);
    }



    private boolean contains(IndexNode root, String word) {
        if (root == null) {
            return false;
        }
        int comparisonResult = word.compareTo(root.word);
        if (comparisonResult == 0) {
           // System.out.println(root.toString());
            return true;
        } else if (comparisonResult < 0) {
            return contains(root.left, word);
        } else {
            return contains(root.right, word);
        }
    }


    private void InOrderTraverse(IndexNode root, StringBuilder sb) { //print everything with list
        if (root == null) {
            sb.append("\n");

        } else {
            InOrderTraverse(root.left,  sb);
            sb.append(root.toString());
            InOrderTraverse(root.right, sb);
        }

    }


    private void InOrderTraverseWithoutList(IndexNode root, StringBuilder sb) {  // this is only if you want to print to text file without a list
        if (root == null) {
            sb.append("\n");


        } else {
            InOrderTraverseWithoutList(root.left,  sb);
            sb.append(root.PrintForTextFile());
            InOrderTraverseWithoutList(root.right, sb);
        }

    }


    public void delete(String word){
        this.root = delete(this.root, word);

    }

    private IndexNode delete(IndexNode root, String word){
        if (root == null) {
            return null;


        }
        int comparisonResult = word.compareTo(root.word);
        if (comparisonResult < 0) {
            root.left = delete(root.left, word);
            return root;
        } else if (comparisonResult > 0) {
            root.right = delete(root.right, word);
            return root;
        } else {



            if (root.left == null && root.right == null) {
                return null;
            }
            else if (root.left != null && root.right == null) {
                return root.left;
            } else if (root.left == null && root.right != null) {
                return root.right;


            } else {
                IndexNode rootOfLeftSubtree = root.left;
                IndexNode parentOfPredecessor = null;
                IndexNode predecessor = null;

                if (rootOfLeftSubtree.right == null) {
                    root.word = rootOfLeftSubtree.word;
                    root.left = rootOfLeftSubtree.left;
                    return root;
                } else {
                    parentOfPredecessor = rootOfLeftSubtree;
                    IndexNode current = rootOfLeftSubtree.right;
                    while (current.right != null) {
                        parentOfPredecessor = current;
                        current = current.right;
                    }

                    predecessor = current;
                    root.word = predecessor.word;
                    parentOfPredecessor.right = predecessor.left;
                    return root;
                }
            }

        }

    }

    public static void saveDoc(String contents, String filename) {   //this will print to a txt file since you cant see all
                                                                        //that is reutrned on the console.
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(new File(filename)));
            pw.print(contents);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.err.println("Error writing to file:  " + filename);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        InOrderTraverse(root, sb);
        return sb.toString();
    }



    public String PrintWithoutList() {
        StringBuilder sb = new StringBuilder();
        InOrderTraverseWithoutList(root, sb);
        return sb.toString();
    }
   public static void main(String[] args){

        IndexTree index = new IndexTree();
       int linNUm = 0;


       try {
           Scanner scanner = new Scanner(new File("pg100.txt"));
           while(scanner.hasNextLine()){
               String line = scanner.nextLine();
               linNUm++;
               // if(line.equals("")){                //uncheck if you want to see each line
               //  continue;
               //  }
               // System.out.println(line);
               String[] words = line.split("[\\s+]|[-]");
               for(String word : words){
                   word = word.replaceAll("[[\\p{Punct}\\p{Blank}]|[0-9]]", "");

                   if(word.equals("")){
                       continue;
                   }
                   //System.out.println(word);          //uncheck if you want to see each individual word stripped of punctuation
                   index.add(word,linNUm);
               }
           }
           scanner.close();

       } catch (FileNotFoundException e1) {
           e1.printStackTrace();
       }



       //Uncheck to test different methods inside of the object.


      // index.delete("zwaggerd");
      System.out.println(index);
      // System.out.println(index.contains("zwaggerd"));
       //index.saveDoc(index.toString(),"/Users/anthonyrogers/Desktop/projectsave.txt"); //use if you want to print all results in word doc
       //index.saveDoc(index.PrintWithoutList(),"/Users/anthonyrogers/Desktop/projectsave.txt" );// print without a list to text file



   }
}
