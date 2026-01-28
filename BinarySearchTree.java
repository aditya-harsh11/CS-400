public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
    protected BinaryNode<T> root;// root node
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts a new data value into the sorted collection.
     * @param data the new value being inserted
     * @throws NullPointerException if data argument is null, we do not allow
     */
    @Override
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
    
    /**
     * Helper method to insert values into the BST
     * @param newNode the node to insert
     * @param subtree the root of the subtree to insert into
     */
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

    /**
     * Check whether data is stored in the tree.
     * @param find the value to check for in the collection
     * @return true if the collection contains data one or more times, 
     * and false otherwise
     */
    @Override
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

    /**
     * Counts the number of values in the collection, with each duplicate value
     * being counted separately within the value returned.
     * @return the number of values in the collection, including duplicates
     */
    @Override
    public int size() {
        return sizeHelper(root);
    }

    /**
     * Helper method to count the number of nodes in the BST
     * @param node the root of the subtree to count nodes in
     * @return the total number of nodes in the BST
     */

    private int sizeHelper(BinaryNode<T> node) {
        if (node == null){
            return 0;
        }
        return 1 + sizeHelper(node.getLeft()) + sizeHelper(node.getRight());
    }

    /**
     * Checks if the collection is empty.
     * @return true if the collection contains 0 values, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Removes all values and duplicates from the collection.
     */
    @Override
    public void clear() {
        root = null;
    }
}
