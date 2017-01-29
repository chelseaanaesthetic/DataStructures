/**
 * An Entry provides the structure for the data portion of the BTNode. Each Entry
 * contains a key and a word that can be compared to other Entry's or words.
 *
 * @author Chelsea Hanson
 * ICS240 Assignment 4: due 7/26/16
 */
public class Entry implements Comparable<Entry> {
    private static int count = 0;
    private int key;
    private String word;

    /**
     * This constructor initializes an Entry with the given word
     * and the next calculated key.
     * @param word - the data to become an Entry
     */
    public Entry(String word) {
        this.key = ++count;
        this.word = word;
    }

    /**
     * This accessor method gets the key from this Entry.
     * @return the key from this Entry.
     */
    public int getKey() {
        return key;
    }

    /**
     * This accessor method gets the word (data) from this Entry.
     * @return the word (data) from this Entry.
     */
    public String getWord() {
        return word;
    }

    /**
     * This method compares the word (data) in this Entry to that from another Entry.
     * @param e - the Entry to be compared to this Entry
     * @return 0 if the two are equal
     *         < 0 if this Entry is smaller than (alphabetically before) the given Entry
     *         > 0 if this Entry is greater than (alphabetically after) the given Entry
     */
    @Override
    public int compareTo(Entry e) {
        return word.compareTo(e.getWord());
    }

    /**
     * This method compares the word (data) in this Entry to a given word.
     * @param s - the String word to be compared to this Entry
     * @return 0 if the two are equal
     *         < 0 if this Entry is smaller than (alphabetically before) the given String
     *         > 0 if this Entry is greater than (alphabetically after) the given String
     */
    public int compareTo(String s) {
        return word.compareTo(s);
    }

    /**
     * This method checks if this Entry is the same as the given Entry.
     * @param e - the given Entry to be compared to
     * @return true if this word (data) and the given word (data) are the same,
     *         else false
     */
    public boolean equals(Entry e) {
        return word.equals(e.getWord());
    }

    /**
     * This method checks if the word (data) in this Entry is the same as the given String.
     * @param s - the given String to be compared to
     * @return true if this word (data) and the given String are the same, else false
     */
    public boolean equals(String s) {
        return word.equals(s);
    }

    /**
     * This method creates a formatted String of this Entry for viewing.
     * @return the String representation of this Entry
     */
    @Override
    public String toString() {
        return (key + ". " + word);
    }
}