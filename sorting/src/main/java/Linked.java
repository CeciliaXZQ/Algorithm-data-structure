/**
 * Created by ceciliaX on 21/11/16.
 */
public class Linked {

    static int NumberOfswaps = 0;

    private class Node {
        Comparable Data ;
        Node nextNode;

        private Node(Comparable data){
            this.Data = data;
        }
        public Comparable getData(){
            return Data;
        }
        public Node getNextNode() {
            return nextNode;
        }
    }

    private Node head;
    private Node last;
    private Node currentNode;
    private int size;
    private Node n1,n2;

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public void add(Comparable data) {
        Node oldlast = last;
        last = new Node(data);
        last.Data = data;
        last.nextNode = null;
        currentNode = head;
        if(isEmpty()) head =last;
        else oldlast.nextNode = last;
         size++;
    }

    public int getSize() {
        return size;
    }

    public Node getHead() {
     return head;
 }

    private boolean isEmpty() {
        return size == 0;
    }

    public Comparable[] toArray()
    {
        Comparable[] myArray = new Comparable[getSize()];
        int i = 0;
        for (Node current = head; current != null; current = current.nextNode,i++) {
            myArray[i] = current.getData();
        }
        return myArray;
    }

   public void bubblesort() {
        if (this.isEmpty()) {
            System.out.println("n < 0");
        }
        boolean swapped = true;
        int R = this.getSize() - 2;
        while (R >= 0 && swapped) {
             n1 = this.getHead();
            swapped = false;
            for (int i = 0; i <= R; i++) {
                n2 = n1.getNextNode();
                if(less(n2.getData(), n1.getData())) {
                    swapped=true;
                    Swap(n1, n2);
                }
                n1=n1.getNextNode();
            }
            R--;
        }
    }

    private static void Swap(Node n1, Node n2){
        NumberOfswaps++;
        Comparable temp = n2.getData();
        n2.Data = n1.getData();
        n1.Data = temp;
    }

    public void printList() {
        Node currentNode = head;
        while (currentNode != null) {
            Comparable data = currentNode.getData();
            System.out.println(data);
            currentNode = currentNode.nextNode;
        }
        System.out.println("swapscount: "+NumberOfswaps);
    }
}
