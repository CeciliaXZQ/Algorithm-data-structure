import java.util.*;

import edu.princeton.cs.introcs.In;
import java.net.URL;
import java.util.Map.Entry;

/**
 * Created by ceciliaX on 28/11/16.
 */
public class Driver {

    public static void main(String[] args) {
        Trie trie = new Trie();
        Node root = new Node(' ');

        URL url = ClassLoader.getSystemResource("kap1.txt");

        if (url != null) {
            System.out.println("Reading from: " + url);
        } else {
            System.out.println("Couldn't find file: kap1.txt");
        }

        In input = new In(url);

        while (!input.isEmpty()) {
            String line = input.readLine().trim();
            String[] words = line.split("(\\. )|:|,|;|!|\\?|( - )|--|(\' )| ");
            String lastOfLine = words[words.length - 1];

            if (lastOfLine.endsWith(".")) {
                words[words.length - 1] = lastOfLine.substring(0, lastOfLine.length() - 1);
            }


            for (String word : words) {
                String word2 = word.replaceAll("\"|\\(|\\)", "");

                if (word2.isEmpty()) {
                    continue;
                }

                System.out.println(word2);
                //trie.put(word2.toLowerCase(), root);
            }
        }
    }
}
















/*


        //Perform analysis
        System.out.println("10 words with the highest frequency:");
        System.out.println(Arrays.toString(minMaxFrequency(trie, true, root)) + "\n");

        System.out.println("10 words with the lowest frequency:");
        System.out.println(Arrays.toString(minMaxFrequency(trie, false, root)) + "\n");

        System.out.println("prefix of length 2 has the highest frequency:");
        System.out.println(prefixLengthHighestFrequency(trie, 2, root) + "\n");

        System.out.println(" the letter that the most different words start with:");
        System.out.println(mostCommonFirstLetter(trie, root) + "\n");
    }

    public static Map.Entry[] minMaxFrequency(Trie trie, boolean max, Node root) {
        final int reverser = max==true ? 1 : -1;

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue(new Comparator() {

            public int compare(Object o1, Object o2) {
                Map.Entry<String, Integer> e1 = (Map.Entry<String, Integer>) o1;
                Entry<String, Integer> e2 = (Map.Entry<String, Integer>) o2;
                if (e1.getValue() > e2.getValue()) {
                    return -1 * reverser;
                }
                if (e2.getValue() > e1.getValue()) {
                    return 1 * reverser;
                }
                return 0;
            }
        });

        Iterator<java.util.Map.Entry<String, Integer>> iterator = trie.iterator("", root);
        while (iterator.hasNext()) {
            pq.add(iterator.next());
        }

        Entry[] res = new Entry[10];
        for (int i = 0; i < res.length; i += 1) {
            res[i] = pq.poll();
        }

        return res;
    }

    public static String prefixLengthHighestFrequency(Trie trie, int length, Node root) {
        Iterator<java.util.Map.Entry<String, Integer>> iterator = trie.iterator("", root);

        String maxPrefix = null;
        int maxCount = 0;

        String currPrefix = null;
        int currentCount = 0;

        while (iterator.hasNext()) {
            Entry<String, Integer> word = iterator.next();

            if (word.getKey().length() >= length) {
                String comparePrefix = word.getKey().substring(0, length);
                if (!comparePrefix.equals(currPrefix)) {
                    if (currentCount > maxCount || maxPrefix == null) {
                        maxCount = currentCount;
                        maxPrefix = currPrefix;
                    }
                    currentCount = 0;
                    currPrefix = comparePrefix;
                }
                currentCount += word.getValue();
               // currPrefix += word.getKey();
            }
        }

        if (currentCount > maxCount) {
            maxPrefix = currPrefix;
        }

        return maxPrefix;
    }

    public static String mostCommonFirstLetter(Trie trie, Node root) {
        Iterator<java.util.Map.Entry<String, Integer>> iterator = trie.iterator("", root);

        String maxPrefix = null;
        int maxCount = 0;

        String currPrefix = null;
        int currentCount = 0;

        while (iterator.hasNext()) {
            Entry<String, Integer> word = iterator.next();

            if (word.getKey().length() >= 1) {
                String comparePrefix = word.getKey().substring(0, 1);
                if (!comparePrefix.equals(currPrefix)) {
                    if (currentCount > maxCount || maxPrefix == null) {
                        maxCount = currentCount;
                        maxPrefix = currPrefix;
                    }
                    currentCount = 0;
                    currPrefix = comparePrefix;
                }
                currentCount += 1;
            }
        }

        if (currentCount > maxCount) {
            maxPrefix = currPrefix;
        }

        return maxPrefix;
    }
    }

*/


