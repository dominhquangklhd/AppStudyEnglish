package app.Model;

import java.util.LinkedList;
import java.util.List;

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
        boolean checkString = word.matches("^[a-z ]*$");
        if (checkString) {
            Node cur = root;
            for(int i = 0 ; i < word.length(); i++){
                int idx = word.charAt(i) - 'a';
                if (idx == ' ' - 'a') {
                    idx = Node.SizeNode - 1;
                }
                if (cur.next[idx] == null) {
                    cur.next[idx] = new Node();
                }
                cur.next[idx].count++;
                cur = cur.next[idx];
            }
            cur.isEnd = true;
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
                    int tmp = i;
                    if (i == Node.SizeNode - 1) {
                        tmp = ' ' - 97;
                    }
                    char[] unicodeChar = Character.toChars(tmp + 97);
                    String unicodeString = new String(unicodeChar);
                    recursiveTrie(cur.next[i], word + unicodeString);
                }
            }
        }
    }

    public void search(String word) {
        String wordLC = word.toLowerCase();
        boolean checkString = word.matches("^[a-z ]*$");
        if (!word.isEmpty() && checkString) {
            numOfWord = 0;
            Node cur = root;
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (idx == ' ' - 'a') {
                    idx = Node.SizeNode - 1;
                }
                if (cur.next[idx] == null) {
                    found = false;
                    break;
                } else {
                    cur = cur.next[idx];
                }
            }
            if (found) {
                recursiveTrie(cur, wordLC);
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
