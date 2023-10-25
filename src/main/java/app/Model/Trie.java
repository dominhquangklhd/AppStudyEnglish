package app.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trie {
    private final Node root = new Node();
    private final int limit = 10;
    private static int numOfWord = 0;
    private List<String> wordsBySearching = new LinkedList<>();

    public Trie(){

    }

    public void resetWordList () {
        wordsBySearching.clear();
    }

    public void dataInWordList() {
        for (String s : wordsBySearching) {
            System.out.println(s);
        }
    }

    public List<String> getWordsBySearching() {
        return wordsBySearching;
    }

    public void insertWord(String word) {
        if (!word.contains("-") || !word.contains(" ") || !word.contains(".")) {
            Node cur = root;
            String tmp = word.toLowerCase();
            if (tmp.equals(word)) {
                for(int i = 0 ; i < word.length(); i++){
                    int idx = word.charAt(i) - 'a';
                    if (idx < 0 || idx >= Node.SizeNode) break;
                    if (cur.next[idx] == null) {
                        cur.next[idx] = new Node();
                    }
                    cur.next[idx].count++;
                    cur = cur.next[idx];
                }
                cur.isEnd = true;
            }
        }
    }

    public void recursiveTrie(Node cur, String word) {
        if (numOfWord < limit) {
            if (cur.isEnd) {
                wordsBySearching.add(word);
                numOfWord++;
            }
            for (int i = 0; i < Node.SizeNode; i++) {
                if (cur.next[i] != null) {
                    char[] unicodeChar = Character.toChars(i + 97);
                    String unicodeString = new String(unicodeChar);
                    recursiveTrie(cur.next[i], word + unicodeString);
                }
            }
        }
    }

    public void search(String word) {
        if (!word.isEmpty()) {
            numOfWord = 0;
            Node cur = root;
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (idx < 0 || idx >= Node.SizeNode || cur.next[idx] == null) {
                    found = false;
                    break;
                } else {
                    cur = cur.next[idx];
                }
            }
            if (found) {
                String tmp = word.toLowerCase();
                recursiveTrie(cur, tmp);
            } else {
                System.out.println("Dont have any words!");
            }
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
