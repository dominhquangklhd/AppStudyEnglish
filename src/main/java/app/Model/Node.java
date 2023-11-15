package app.Model;

public class Node {
    public int count = 0;

    public static final int SizeNode = 27;
    public Node[] next = new Node[SizeNode];
    public boolean isEnd = false;

    Node() {

    }
}