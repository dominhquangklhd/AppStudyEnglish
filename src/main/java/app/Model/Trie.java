package app.Model;

import app.DB_Connection.DatabaseConnection;
import app.Main;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Trie {
    private final Node root = new Node();
    private final int limit = 10;
    private static int numOfWord = 0;
    private List<String> wordsBySearching = new LinkedList<>();

    public Trie() {

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

    /**
     * Inserts a word to the trie.
     * @param word the word to insert
     */
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

    /**
     * Recursively finds all words having word as prefix, adding it to a list.
     * @param cur the current end node of the word.
     * @param word the word as prefix
     */

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

    /**
     * If a word presents in the trie, recursively search for words having this word as prefix.
     * @param word the word to search
     */

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

    /**
     * Deletes a word from the trie.
     * @param word the target to delete
     */

    public void deleteWord(String word) {
        boolean checkString = word.matches("^[a-z ]*$");
        if (checkString) {
            deleteWord(root, word, 0);
        }
    }

    private boolean deleteWord(Node cur, String word, int depth) {
        if (cur == null) {
            return false;
        }

        if (depth == word.length()) {
            if (cur.isEnd) {
                cur.isEnd = false; // Mark as not the end of a word
                return isLeafNode(cur);
            }
            return false;
        }

        int idx = word.charAt(depth) - 'a';
        if (idx == ' ' - 'a') {
            idx = Node.SizeNode - 1;
        }

        if (deleteWord(cur.next[idx], word, depth + 1)) {
            cur.next[idx] = null; // Remove the child node if it's unnecessary
            return isLeafNode(cur) && !cur.isEnd; // If the node has no child and it's not the end of
                                                    // another word, then can safely delete it
        }

        return false;
    }

    /**
     * Checks if a node is a leaf (has no child).
     * @param node the node to check
     * @return true if the node is a leaf, false otherwise
     */
    private boolean isLeafNode(Node node) {
        for (int i = 0; i < Node.SizeNode; i++) {
            if (node.next[i] != null) {
                return false;
            }
        }
        return true;
    }
}