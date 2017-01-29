import java.util.Iterator;

/**
 * @author Chelsea Hanson
 * ICS240 Assignment 2: due 6/14/16
 *
 *
 * This class sets up a collection of linked nodes and performs basic
 * operations on individual nodes as well as the list as a whole.
 *
 *
 * implementation of Iterator/Iterable modeled off:
 * https://docs.oracle.com/javase/tutorial/java/javaOO/innerclasses.html
 */
public class MembershipLinkedList implements Iterable<MemberNode>, Cloneable {
    private MemberNode head; // points to the first node
    private int memberCount; // counts members in this list
    private MemberNode cursor; // points to the active node
    private MemberNode precursor; // points to the node prior to the active node

    /**
     * This constructor initializes a list for adding members.
     */
    public MembershipLinkedList( ) {
        head = null;
        memberCount = 0;
    }

    /**
     * This method gets the number of members in the list and returns it.
     * @return the number of members in the list
     */
    public int size() {
        return memberCount;
    }

    /**
     * This method adds a new member to the list in alphabetical order.
     * @param element - the member to be added
     */
    public void add(Member element) {
        // head = new MemberNode(element, head);

        if (head == null) {
            head = new MemberNode(element, null);
        }

        else if (head.getData().compareTo(element) > 0) {
            head = new MemberNode(element, head);
        }

        else {
            MembershipIterator alphabetizer = new MembershipIterator(head);

            while (alphabetizer.hasNext()) {
                cursor = alphabetizer.next();

                if (cursor.getData().compareTo(element) > 0) {
                    addNodeBefore(element);
                    break;
                }
                else if (cursor.getLink() == null) {
                    addNodeAfter(element);
                    break;
                }
            }
        }

        memberCount++;
    }

    /**
     * This method removes a node from the list and closes the gap.
     * @param node - the member node to be removed
     */
    public void remove(MemberNode node) {
        if (node.getLink() == null && search(node.getData().getUsername())) {
            precursor.setLink(null);
        }
        else {
            node.setData(node.getLink().getData());
            node.setLink(node.getLink().getLink());
        }
        memberCount--;
    }

    /**
     * This method adds a new node before the current cursor node for the class.
     * @param element - the new member to be added to the list
     */
    private void addNodeBefore(Member element) {
        precursor.setLink(new MemberNode(element, precursor.getLink()));
    }

    /**
     * This method adds a new node after the current cursor node for the class.
     * @param element - the new member to be added to the list
     */
    private void addNodeAfter(Member element) {
        cursor.setLink(new MemberNode(element, cursor.getLink()));
    }

    /**
     * This method adds all the members of a different membership list to this one.
     * @param addend - the list of members to add
     */
    public void addAll(MembershipLinkedList addend) {
        if (addend == null) {
            throw new IllegalArgumentException("addend is null");
        }

        for (Object m : addend) {
            add(((MemberNode) m).getData());
        }
    }

    /**
     * This method searches for, and removes a specific member.
     * @param target - the member to find and remove
     * @return true if the member was found and deleted, else false
     */
    public boolean remove(Member target) {
        MembershipIterator it = new MembershipIterator(head);
        while (it.hasNext()) {
            if (it.next().getData().equals(target)) {
                precursor.setLink(cursor.getLink());
                memberCount--;
                return true;
            }
        }
        return false;
    }

    /**
     * This method sets changes made to the data in a node, and keeps all members
     * in alphabetical order by username.
     * @param nodeToChange - the old node that needs to be updated
     * @param updatedData - the updated member data to be put into the list
     */
    public void modify(MemberNode nodeToChange, Member updatedData) {
        remove(nodeToChange);
        add(updatedData);
    }

    /**
     * This method checks if the given username exists in the list.
     * @param targetUsername - the username in question
     * @return true if the username exists, else false
     */
    public boolean search(String targetUsername) {
        if (head == null) return false;
        MembershipIterator searcher = new MembershipIterator(head);
        while (searcher.hasNext()) {
            cursor = searcher.next();
            if (cursor.getData().getUsername().equals(targetUsername)) {
                return true;
            }
            precursor = cursor;
        }
        return false;
    }

    /**
     * This method retrieves the current cursor on the list.
     * @return the current cursor
     */
    public MemberNode getCursor() {
        return cursor;
    }

    /**
     * This method combines two lists into one.
     * @param m1 - one of the lists to be merged together
     * @param m2 - one of the lists to be merged together
     * @return the merged list
     */
    public static MembershipLinkedList union(MembershipLinkedList m1, MembershipLinkedList m2) {
        if (m1 == null) {
            throw new IllegalArgumentException("m1 is null.");
        }
        if (m2 == null) {
            throw new IllegalArgumentException("m2 is null.");
        }

        MembershipLinkedList result = new MembershipLinkedList();

        result.addAll(m1);
        result.addAll(m2);
        return result;
    }

    /**
     * This method creates an iterator for use with the list.
     * @return a new iterator instance
     */
    public MembershipIterator iterator(){
        return new MembershipIterator(head);
    }

    /**
     * This inner class implements the iterator functionality.
     */
    class MembershipIterator implements Iterator<MemberNode> {
        private MemberNode head;
        private MemberNode cursor;
        private MemberNode precursor;

        /**
         * This constructor creates an iterator and initializes the needed variables.
         * @param head - the first node in the list
         */
        public MembershipIterator(MemberNode head) {
            this.head = head;
            cursor = null;
            precursor = null;
        }

        /**
         * This method checks if the iterator has more elements to go through.
         * @return true if there are more elements, else false
         */
        public boolean hasNext() {
            if (cursor == null && head != null) {
                return true;
            }
            else if (cursor.getLink() != null)
                return true;
            return false;
        }

        /**
         * This method advances the cursor and gets the next element iterated over.
         * @return - the next element in the list
         */
        public MemberNode next() {
            if (cursor == null && head != null) {
                cursor = head;
                return cursor;
            }

            else if (cursor.getLink() == null) {
                precursor = null;
                cursor = null;
                return null;
            }

            precursor = cursor;
            cursor = cursor.getLink();
            return cursor;
        }

        /**
         * This method removes the element under the cursor.
         */
        public void remove() {
            if (head.equals(cursor) && cursor.getLink() == null) {
                head = null;
                cursor = null;
                precursor = null;
            }
            else if (cursor.getLink() == null) {
                precursor.setLink(null);
            }
            else {
                // cursor = cursor.getLink();
                cursor.setData(cursor.getLink().getData());
                cursor.setLink(cursor.getLink().getLink());
            }
            memberCount--;
        }
    }

    /**
     * This method creates a copy of a membership list.
     * @return a copy of the calling list
     */
    public MembershipLinkedList clone() {
        MembershipLinkedList result;

        try {
            result = (MembershipLinkedList) super.clone();
        }
        catch(CloneNotSupportedException e) {
            throw new RuntimeException("This class does not implement Cloneable.");
        }

        result.head = MemberNode.listCopy(head);

        return result;
    }

    /**
     * This method formats the list for output
     * @return the string representation of the members of the list
     */
    @Override
    public String toString() {
        String result = "Member count: " + memberCount;
        for (cursor = head; cursor != null; cursor = cursor.getLink()) {
            result = result + cursor.getData().toString();
        }
        return result;
    }
}