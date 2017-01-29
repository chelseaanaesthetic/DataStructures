import javax.swing.*;
/**
 * @author Chelsea Hanson
 * ICS240 Assignment 4: due 7/26/16
 *
 * Modeled from IntTreeBag.java by Michael Main:
 * http://www.cs.colorado.edu/~main/EDU/colorado/collections/IntTreeBag.java
 * http://www.cs.laurentian.ca/dgoforth/cosc2007/week2/IntTreeBag.java
 *
 */
public class BinarySearchTree {
    private BTNode<Entry> root;

    /**
     * This constructor creates a new tree and initializes the root and sets
     * the counts to 0.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * This method inserts a new element into the tree.
     * @param element - the new element to be inserted
     * @return - true if the inserted word was unique, otherwise false
     */
    public boolean add(Entry element) {
        BTNode<Entry> cursor;
        boolean done;
        boolean isDuplicate = false;

        if (root == null) {
            root = new BTNode<>(element, null, null);
        }
        else {
            cursor = root;
            done = false;

            while (!done) {
                if (cursor.getData().compareTo(element) == 0) {
                    isDuplicate = true;
                }
                if (cursor.getData().compareTo(element) > 0) {
                    if (cursor.getLeft() == null) {
                        cursor.setLeft(new BTNode<>(element, null, null));
                        done = true;
                    }
                    else {
                        cursor = cursor.getLeft();
                    }
                }
                else {
                    if (cursor.getRight() == null) {
                        cursor.setRight(new BTNode<>(element, null, null));
                        done = true;
                    }
                    else {
                        cursor = cursor.getRight();
                    }
                }
            }
        }
        return !isDuplicate;
    }

    /**
     * This method counts how many times the specific element occurs in the tree.
     * @param target - the word to be searched for in the tree
     * @return - the number of times the target occurs in the tree
     */
    public int countOccurrences(String target) {
        BTNode<Entry> cursor = root;
        int answer = 0;

        while (cursor != null) {
            if (cursor.getData().compareTo(target) < 0) {
                cursor = cursor.getRight();
            }
            else {
                if (cursor.getData().equals(target)) {
                    answer++;
                    cursor = cursor.getRight();
                }
                else {
                    cursor = cursor.getLeft();
                }
            }
        }
        return answer;
    }

    /**
     * This recursive method traverses the tree to build a string representation
     * of the tree in ascending order.
     * @param cursor - the current node to get data from
     * @return - the string representation of the sorted tree
     */
    private String toString(BTNode<Entry> cursor) {
        if (cursor.getLeft() != null) {
            if (cursor.getRight() != null){
                return (toString(cursor.getLeft()) + cursor.getData().toString() + "\n" + toString(cursor.getRight()));
            }
            else {
                return (toString(cursor.getLeft()) + cursor.getData().toString() + "\n");
            }
        }
        else if (cursor.getRight() != null) {
            return (cursor.getData().toString() + "\n" + toString(cursor.getRight()));
        }
        else {
            return (cursor.getData().toString() + "\n");
        }
    }

    /**
     * This method displays the string representation of the sorted tree to the
     * given JTextArea.
     * @param outputBox - the specified text box to display the tree to
     */
    public void printTo(JTextArea outputBox) {
        outputBox.setText(toString(root));
    }
}