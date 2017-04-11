import java.util.*;
import java.util.Iterator;

/**
 * Created by ceciliaX on 25/11/16.
 */

public class Trie {
    public int childPos;



    public boolean exist(char a, ArrayList<Node> children) {
        childPos=0;
        boolean check = false;
        for (int i = 0; i < children.size(); i++) {
            char b = children.get(i).getchar();
            if (a == b) {
                check = true;
                childPos = i;
                break;
            }
            childPos=i+1;
        }
        return check;
    }

    public void put(String k, Node root) {
        Node current = root;
        for (int i = 0; i < k.length(); i++) {
            boolean exist = exist(k.charAt(i), current.children);

            if (exist) {
                current = current.children.get(childPos);

                if (i == k.length() - 1) {
                    current.addValue();
                }

            } else {
                current.addNode(k.charAt(i));
                current = current.children.get(childPos);
                if (i == k.length() - 1) {
                    current.addValue();
                    current.k = k;
                }

            }
        }
    }

    public int get(String k, Node root) {
        Node current = root;

        for (int i = 0; i < k.length(); i++) {
            boolean exist = exist(k.charAt(i), current.children);

            if (exist) {
                current = current.children.get(childPos);
            }
            else {
                return 0;
            }
            }
        return current.value;
        }

     public int count (String k,Node root) {
         Node current = root;
         int counter = 0;
         for (int i = 0; i < k.length(); i++) {
             boolean exist = exist(k.charAt(i), current.children);

             if (exist) {
                 current = current.children.get(childPos);
             }
         }
             counter = current.value;
                 Queue<Node> kids = new LinkedList<Node>();
                 kids.add(current);
                 while (!kids.isEmpty()) {
                     current = kids.poll();
                     for (int j = 0; j < current.children.size(); j++) {
                         kids.add(current.children.get(j));
                         counter = counter + current.children.get(j).getmyValue();
                     }
                 }

         return counter;
     }

     public int distinct(String k, Node root) {
         Node current = root;
         int counter = 0;
         for (int i = 0; i < k.length(); i++) {
             boolean exist = exist(k.charAt(i), current.children);

             if (exist) {
                 current = current.children.get(childPos);
             }
         }
             Queue<Node> kids = new LinkedList<Node>();
             kids.add(current);
             while (!kids.isEmpty()) {
                 current = kids.poll();

                 for (int j = 0; j < current.children.size(); j++) {
                     kids.add(current.children.get(j));
                     if(current.children.get(j).getmyValue()!=0){
                         counter++;
                     }
                 }
             }

         return counter;
     }


    public void print(Node root) {
        Node current = root;
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        while (!q.isEmpty()) {
            current = q.poll();
            current.printNode();
            for (int i = 0; i < current.children.size(); i++) {
                q.add(current.children.get(i));
            }

        }
    }



    public Iterator<java.util.Map.Entry<String, Integer>> iterator (String k, Node root){
         Node current=root;
        for (int i =0 ; i<k.length() ; i++)
          {
              current = current.children.get(i);
          }
        return new TrieIterator(root);
    }

    private class  TrieIterator implements Iterator<Map.Entry<String, Integer>>{
        Node current;
        Node child;
        Node root;
        int count=1;
        int tempcount =0;

        TrieIterator(Node root)
        {
            this.root = root;
            child=null;
            int tempcount =0;
            getNext(root);
        }

        public boolean hasNext() {
            // TODO Auto-generated method stub
            return child!= null;
        }

        public Map.Entry<String, Integer> next() {

            current=child;

            count++;
            tempcount =0;
            child=null;
            getNext(root);
            //TODO Auto-generated method stub
            return current;
          }

        public void getNext(Node root)
        {
            if ( root.getmyValue() != 0)
            {
                tempcount++;
                if ( tempcount == count)
                {
                    child=root;
                    return;
                }
            }
            for ( int i = 0 ; i < root.children.size();i++){
                getNext(root.children.get(i));
            }
        }
          public void remove(){}
      }
  }

