import java.util.*;

import edu.princeton.cs.introcs.In;
import java.net.URL;
import java.util.Map.Entry;

/**
 * Created by ceciliaX on 25/11/16.
 */
public class TestTrie {

    public static void main(String[] args) {
        Trie a = new Trie();
        Node root = new Node('-');
        a.put("cat", root);
        a.put("car", root);
        a.put("car", root);
        a.put("cat", root);
        a.put("cast", root);
        a.put("happy", root);
        a.put("happen", root);

        a.print(root);
        System.out.println("Get: " + a.get("cat", root));
        System.out.println("Count: " + a.count("ca", root));
        System.out.println("Distinct: " + a.distinct("ha", root));
        //System.out.println("Iterator: " + a.iterator("", root));

        Iterator<Map.Entry<String, Integer>> k = a.iterator(" ", root);

        while ( k.hasNext())
        {
            Map.Entry<String, Integer> b = k.next();
            System.out.println(b.getValue()+ "" +b.getKey());

        }

    }


    }






