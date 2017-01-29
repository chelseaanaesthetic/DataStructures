/**
 * @author Chelsea Hanson
 * ICS240 Project 1: due 5/31/16
 *
 * This Class provides a way to collect multiple Member objects
 * and store them in an array for easy access and modification.
 *
 * code borrowed and modified from edu.colorado.collections;
 * http://www.cs.colorado.edu/~main/docs/.
 */
public class MembershipList implements Cloneable {
    // maintains a list of members
    private Member[] membership;
    // keeps track of how many members are in the list
    private int memberCount;

    /**
     * This constructor initializes a list to a given capacity.
     */
    public MembershipList() {
        final int INITIAL_CAPACITY = 10;
        memberCount = 0;
        membership = new Member[INITIAL_CAPACITY];
    }

    /**
     * This constructor initializes a list to user specified capacity.
     * @param initialCapacity - the user specified size to initialize the list to
     */
    public MembershipList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException
                    ("initialCapacity is negative: " + initialCapacity);
        }
        memberCount = 0;
        membership = new Member[initialCapacity];
    }

    /** 
     * This method takes a Member and adds it to the list after
     * ensuring there is enough space to do so and increasing
     * the capacity if not. 
     * @param element - the Member to add to the list
     */
    public void add(Member element) {
        if (memberCount == membership.length) {
            ensureCapacity(memberCount*2 + 1);
        }
        membership[memberCount] = element;
        memberCount++;
    }

    /**
     * This method takes a list of members and adds the entire list to the end of 
     * the current list.
     * @param addend - the list of members to add
     */
    public void addAll(MembershipList addend) {
        ensureCapacity(memberCount + addend.memberCount);

        System.arraycopy(addend.membership, 0, membership, memberCount, addend.memberCount);
        memberCount += addend.memberCount;
    }

    /**
     * This method adds multiple individual members to the list at the same time.
     * @param elements - multiple members to be added to the list
     */
    public void addMany(Member... elements) {
        if (memberCount + elements.length > membership.length) {
            ensureCapacity((memberCount + elements.length)*2);
        }

        System.arraycopy(elements, 0, membership, memberCount, elements.length);
        memberCount += elements.length;
    }

    /**
     * This method searches the list for a specific member and returns it's index in the list.
     * @param target - the member to search the list for
     * @return the index of the member in question, or -1 if it is not found
     */
    public int find(Member target) {
        int index = 0;
        while ((index < memberCount) && (target != membership[index])) {
            index++;
        }

        if (index == memberCount) {
            return -1;
        }

        else {
            return index;
        }
    }

    /**
     * This method removes the element at the given index 
     * and also readjusts the length and fills the empty space left behind.
     * 
     * @param index - the index of the element to be removed
     * @return true if the element at the given index was removed, else false
     */
    public boolean remove(int index) {
        if (index < membership.length) {
            memberCount--;
            membership[index] = membership[memberCount];
            return true;
        }
        return false;
    }

    /**
     * This method replaces the member at the given index with the new 
     * member given by the user. 
     * 
     * @param index - the index of the member to be modified
     * @param element - the member to overwrite the member at the given index
     * @return true if the member at the given index is modified, else false
     */
    public boolean modify(int index, Member element) {
        if (index < membership.length) {
            membership[index] = element;
            return true;
        }
        return false;
    }

    /**
     * This method makes a clone of a list for use elsewhere without affecting the original list
     * @return a clone of the calling list 
     */
    @Override 
    public MembershipList clone() {
        MembershipList answer;

        try {
            answer = (MembershipList) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("This class does not implement Cloneable.");
        }
        answer.membership = membership.clone();
        return answer;
    }

    /**
     * This method increases the size of the array in order to fit more members at a later time.
     * @param minimumCapacity - the size to increase the array to
     */
    public void ensureCapacity(int minimumCapacity) {
        Member[] biggerArray;

        if (membership.length < minimumCapacity) {
            biggerArray = new Member[minimumCapacity];
            System.arraycopy(membership, 0, biggerArray, 0, memberCount);
        }
    }

    /**
     * This method returns the full length of the array
     * @return the capacity (total size filled or not) of the array 
     */
    public int getCapacity() {
        return membership.length;
    }

    /** 
     * This method returns the number of members in the calling array
     * @return the actual number of members stored in the array 
     */
    public int getMemberCount() {
        return memberCount;
    }

    /**
     * This method changes the claimed number of members in the array. 
     * It is called when members are deleted.
     * @param count - the number of members in the array as claimed by the program
     */
    public void setMemberCount(int count) {
        memberCount = count;
    }

    /**
     * This method reduces the capacity of the array to just fit those members already in 
     * the array. Uninstantiated indices at the beginning and end will be cut off.
     */
    public void trimToSize() {
        Member[] trimmedArray;

        if (membership.length != memberCount) {
            trimmedArray = new Member[memberCount];
            System.arraycopy(membership, 0, trimmedArray, 0, memberCount);
            membership = trimmedArray;
        }
    }

    /**
     * This method takes two arrays and merges them together. 
     * 
     * @param m1 - an array to merge with another array
     * @param m2 - an array to merge with another array
     * @return the array produced from merging two other arrays
     */
    public static MembershipList union(MembershipList m1, MembershipList m2) {
        MembershipList answer = new MembershipList(m1.getCapacity() + m2.getCapacity());

        System.arraycopy(m1.membership, 0, answer.membership, 0, m1.memberCount);
        System.arraycopy(m2.membership, 0, answer.membership, m1.memberCount, m2.memberCount);
        answer.memberCount = m1.memberCount + m2.memberCount;
        return answer;
    }

    /**
     * This method takes an index and returns the member found at that index.
     *
     * @param index - the location to look for a member
     * @return the member at the given index
     */
    public Member get(int index) {
        return membership[index];
    }

    /**
     * This method replaces the content at the given index with the given member.
     * 
     * @param index - the location to overwrite data
     * @param m - the member to place at the given index
     */ 
    public void set(int index, Member m) {
        membership[index] = m;
    }

    /**
     * This method checks whether the given string is the username of a member in the calling member array.
     * @param target - the username privided by the user to search for in the array
     * @return true if the username is found, else false
     */
    public boolean search(String target) {
        int index = 0;
        while ((index < memberCount) && (target != membership[index].getUsername())) {
            index++;
        }

        if (index == memberCount) {
            return false;
        }

        else {
            return true;
        }
    }

    /**
     * This method checks if the given username string is that of a member in the caling array, and returns the index if found.
     * @param target - the username provided by the user to search for in the array
     * @return the index of the member in question, or -1 if it doesn't exist
     */
    public int indexOf(String target) {
        int index = 0;
        while ((index < memberCount) && (target != membership[index].getUsername())) {
            index++;
        }
        if (index == memberCount) {
            return -1;
        }
        else {
            return index;
        }
    }

    /**
     * This method formats the data stored in the array for output.
     * @return a string representation of all of the members in the array
     */
    @Override
    public String toString() {
        String text = "\n";
        for (Member m : membership) {
            text += (m.toString() + "\n\n");
        }
        return text;
    }
}