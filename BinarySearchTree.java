public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    protected BinaryNode<T> root; // root node
    public BinarySearchTree() {
        root = null; // initially make it empty
    }

    //insert
    public void insert(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Cant insert null into a BST");
        }
        BinaryNode<T> newNode = new BinaryNode<>(data);
        if (root == null) {
            root = newNode;
        } else {
            insertHelper(newNode, root);
        }
    }

    //insertHelper
    protected void insertHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {
        if (subtree == null) {
            return;
        }
        if (newNode.getData().compareTo(subtree.getData()) <= 0) { //left
            if (subtree.getLeft() == null) {
                subtree.setLeft(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getLeft());
            }
        } else { //right
            if (subtree.getRight() == null) {
                subtree.setRight(newNode);
                newNode.setUp(subtree);
            } else {
                insertHelper(newNode, subtree.getRight());
            }
        }
    }

    //contains
    public boolean contains(Comparable<T> find) {
        if (find == null) {
            return false;
        }
        BinaryNode<T> current = root;
        while (current != null) {
            if (find.compareTo(current.getData()) == 0) {
                return true;
            } else if (find.compareTo(current.getData()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return false;
    }

    //size

    //isEmpty

    //clear
}
