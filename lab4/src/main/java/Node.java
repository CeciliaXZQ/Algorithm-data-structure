import java.util.ArrayList;

/**
 * Created by ceciliaX on 25/11/16.
 */
public class Node implements java.util.Map .Entry{
    public int value = 0;
    public char c;
    public String k;
    private Node parent;

    ArrayList<Node> children = new ArrayList<Node>();
    private int NumberOfChildren = 0;

    public Node (char c) {
        this.c = c;
    }

    public void addNode(char a){
        Node newChild = new Node(a);
        newChild.parent=this;
        this.children.add(newChild);

    }

    public String getKey() {
        return k;
    }

    public Integer getValue() {
        return this.value;
    }

    public int getmyValue(){
        return this.value;
    }

    public Object setValue(Object value) {
        return null;
    }

    public int addValue(){
        return this.value++;

    }

    public char getchar(){
        return c;
    }

    public void printNode(){
        System.out.println(this.getchar()+"-"+this.value);
    }
}
