import java.net.SocketOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Trie {
    private Node root = new Node();
    private final int limit = 1;
    private static int numOfWord = 0;

    public Trie(){

    }

    public void insertWord(String word){
        Node cur = root;
        for(int i = 0 ; i < word.length(); i++){
            int idx = word.charAt(i) - 'a';
            if (cur.next[idx] == null) {
                cur.next[idx] = new Node();
            }
            cur.next[idx].count++;
            cur = cur.next[idx];
        }
        cur.isEnd = true;
    }

    public void recursiveTrie(Node cur, String word) {
        if (numOfWord < limit) {
            if (cur.isEnd) {
                System.out.println(word);
                numOfWord++;
            }
            for (int i = 0; i < 26; i++) {
                if (cur.next[i] != null) {
                    char[] unicodeChar = Character.toChars(i + 97);
                    String unicodeString = new String(unicodeChar);
                    recursiveTrie(cur.next[i], word + unicodeString);
                }
            }
        }
    }

    public void search(String word) {
        Node cur = root;
        boolean found = true;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (cur.next[idx] == null) {
                System.out.println("None");
                found = false;
                break;
            }
            cur = cur.next[idx];
        }
        if (found) {
            recursiveTrie(cur, word);
        }
    }

    public int query(String s) {
        Node cur = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (cur.next[idx] == null) {
                return 0;
            }
            cur = cur.next[idx];
        }
        return cur.count;
    }
}
