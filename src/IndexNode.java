

import java.util.ArrayList;
import java.util.List;

public class IndexNode  {


    String word;
    int occurences;
    List<Integer> list;

    IndexNode left;
    IndexNode right;



    public IndexNode(){
    this.left = null;
    this.right=null;
    occurences=0;
    }


    public IndexNode(String word, int lineNumber){
        this.word = word;
        list = new ArrayList<>();
        this.list.add(lineNumber);
        this.occurences++;
    }


    public String toString(){

        String listWord = this.word;
        listWord += "  -  " + occurences + " - " + list.toString();
        return listWord;
    }


    public String PrintForTextFile(){           //since the text file gets over whelmed with the printing of the list...
                                                // this will print just the word the amount of times it occurs and the size of the list.
        String listWord = this.word;
        listWord += "  -  " + occurences + " - " + list.size();
        return listWord;
    }


}