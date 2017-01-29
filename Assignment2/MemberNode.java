import java.util.Iterator;

/**
 * @author Chelsea Hanson
 * ICS240 Assignment 2: due 6/14/16
 *
 *
 * This class sets up the structure of a node for a linked list of members.
 *
 *
 * implementation of a node modeled off:
 * http://www.cs.colorado.edu/~main/docs/
 */
public class MemberNode {
    private Member data;
    private MemberNode link;

    /**
     * This constructor creates a new member node based on user input
     * @param initialData - The member that the node represents
     * @param initialLink - A link to the next node
     */
    public MemberNode(Member initialData, MemberNode initialLink) {
        data = initialData;
        link = initialLink;
    }

    /**
     * This method retrieves the member data in the node.
     * @return - the Member that is represented by the node
     */
    public Member getData() {
        return data;
    }

    /**
     * This method retrieves the link in the node.
     * @return - the node that the link points to
     */
    public MemberNode getLink() {
        return link;
    }

    /**
     * This method sets the member data in the node.
     * @param newMember - user defined member
     */
    public void setData(Member newMember) {
        data = newMember;
    }

    /**
     * This method sets the link in the node.
     * @param newLink - the next node in the list to link to
     */
    public void setLink(MemberNode newLink) {
        link = newLink;
    }

    /**
     * This method adds the given node after the calling node.
     * @param element - the new node to add
     */
    public void addNodeAfter(Member element) {
        link = new MemberNode(element, link);
    }

    /**
     * This method removes the node after the calling node.
     */
    public void removeNodeAfter() {
        link = link.link;
    }

    /**
     * This method counts how many Members are in the given list.
     * @param head - the start of the list to check
     * @return the number of members in the list
     */
    public static int listLength(MemberNode head) {
        MemberNode cursor;
        int count = 0;

        for (cursor = head; cursor != null; cursor = cursor.link) {
            count++;
        }

        return count;
    }

    /**
     * This method determines whether the given query is found in the list.
     * @param head - the start of the list to check
     * @param username - the query to search for
     * @return the node that represents the search query if found, else null
     */
    public static MemberNode listSearch(MemberNode head, String username) {
        MemberNode cursor;

        for (cursor = head; cursor != null; cursor = cursor.link) {
            if (cursor.data.getUsername().equals(username)) {
                return cursor;
            }
        }

        return null;
    }

    /**
     * This method determines which node is located at the given position.
     * @param head - the first node in the list to check
     * @param position - the location in the list to retrieve the node from
     * @return the node contained at the given position
     */
    public static MemberNode listPosition(MemberNode head, int position) {
        MemberNode cursor;

        if (position <= 0) {
            throw new IllegalArgumentException("position is not positive.");
        }

        cursor = head;
        for (int i = 1; (i < position) && (cursor != null); i++) {
            cursor = cursor.link;
        }

        return cursor;
    }

    /**
     * This method makes a duplicate copy of a linked list
     * @param source - the linked list to be copied
     * @return the head node of the new list
     */
    public static MemberNode listCopy(MemberNode source) {
        MemberNode copyHead;
        MemberNode copyTail;

        if (source == null) {
            return null;
        }

        copyHead = new MemberNode(source.data, null);
        copyTail = copyHead;

        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        return copyHead;
    }

    /**
     * This method makes a duplicate copy of a linked list.
     * @param source - the list to be copied
     * @return an array of 2 nodes, the head and the tail
     */
    public static MemberNode[] listCopyWithTail(MemberNode source) {
        MemberNode copyHead;
        MemberNode copyTail;
        MemberNode[] result = new MemberNode[2];

        if (source == null) {
            return result;
        }

        copyHead = new MemberNode(source.data, null);
        copyTail = copyHead;

        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        result[0] = copyHead;
        result[1] = copyTail;

        return result;
    }

    /**
     * This method creates a partial list based on the start and end nodes.
     * @param start - the node to start the list at
     * @param end - the node to end the list with
     * @return an array of 2 nodes, the head and the tail
     */
    public static MemberNode[] listPart(MemberNode start, MemberNode end) {
        MemberNode copyHead;
        MemberNode copyTail;
        MemberNode[] result = new MemberNode[2];

        if (start == null) {
            throw new IllegalArgumentException("start is null");
        }
        if (end == null) {
            throw new IllegalArgumentException("end is null");
        }

        copyHead = new MemberNode(start.data, null);
        copyTail = copyHead;

        while (start != end) {
            start = start.link;
            if (start == null) {
                throw new IllegalArgumentException("end node was not found on the list");
            }
            copyTail.addNodeAfter(start.data);
            copyTail = copyTail.link;
        }

        result[0] = copyHead;
        result[1] = copyTail;

        return result;

    }

    /**
     * This method calls to format the data for output.
     * @return a string representation of the Member represented in the node
     */
    @Override
    public String toString() {
        return data.toString();
    }
}
