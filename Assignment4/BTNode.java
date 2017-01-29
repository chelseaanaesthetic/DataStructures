/**
 * A BTNode provides a node for a binary tree. Each node contains a piece of data
 * (which is a reference to an E object) and references to a left and right child.
 * The reference stored in a node can also be null.
 *
 * @author Chelsea Hanson
 * ICS240 Assignment 4: due 7/26/16
 *
 * Methods taken from BTNode.java by Michael Main:
 * http://www.cs.colorado.edu/~main/edu/colorado/nodes/BTNode.java
 */
public class BTNode<E> {

    // Invariant of the generic BTNode<E> class:
    //    1. Each node has one reference to an E Object, stored in data.
    //    2. The instance variables left and right are references to the node's children.

    private E data;
    private BTNode<E> left, right;

    /**
     * Initialize a BTNode with a specified initial data and links children.
     * Note that a child link may be the null reference, which indicates that
     * the new node does not have that child.
     * @param initialData - the initial data of this new node
     * @param initialLeft - a reference to the left child of this new node --
     *                    this reference may be null to indicate that there
     *                    is no node after this new node
     * @param initialRight - a reference to the left child fo this new node --
     *                     this reference may be null to indicate that there
     *                     is no node after this new node.
     *
     * Postcondition: This node contains the specified data nd links to its children.
     */
    public BTNode(E initialData, BTNode<E> initialLeft, BTNode<E> initialRight) {
        data = initialData;
        left = initialLeft;
        right = initialRight;
    }

    /**
     * Accessor method to get the data from this node.
     * @return the data from this node
     */
    public E getData() {
        return data;
    }

    /**
     * Accessor method to get a reference to the left child of this node.
     * @return - a reference to the left child of this node
     *      (or the null reference if there is no left child)
     */
    public BTNode<E> getLeft() {
        return left;
    }

    /**
     * Accessor method to get a reference to the right child fo this node.
     * @return - a reference to the right child of this node
     *      (or the null reference if there is no right child)
     */
    public BTNode<E> getRight() {
        return right;
    }

    /**
     * Modification method to set the data in this node.
     * @param newData - the new data to place in this node
     */
    public void setData(E newData) {
        data = newData;
    }

    /**
     * Modification method to set the link to the left child of this node.
     * @param newLeft - a reference to the node that should appear as the left child of this node
     *                (or the null reference if there is no left child for this node)
     */
    public void setLeft(BTNode<E> newLeft) {
        left = newLeft;
    }

    /**
     * Modification method to set the link to the right child of this node.
     * @param newRight- a reference to the node that should appear as the right child of this node
     *                (or the null reference if there is no right child for this node)
     */
    public void setRight(BTNode<E> newRight) {
        right = newRight;
    }
}