/** 
 * This class represents a binary search tree with support for left and right rotation.
 */
public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree_Placeholder<T> {

    /**
     * No argument constructor
     */
    public BSTRotation() {
        super();
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     * @param child is the node being rotated from child to parent position 
     * @param parent is the node being rotated from parent to child position
     */
    protected void rotate(BinaryNode<T> child, BinaryNode<T> parent) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("Nodes cant be null");
        }
        if (child.getUp() != parent) {
            throw new IllegalArgumentException("Nodes dont have a parent and child relationship");
        }

        BinaryNode<T> grandparent = parent.getUp();

        if (parent.getLeft() == child) {
            // Right rotation
            parent.setLeft(child.getRight());
            if (child.getRight() != null) {
                child.getRight().setUp(parent);
            }
            child.setRight(parent);
        } else if (parent.getRight() == child) {
            // Left rotation
            parent.setRight(child.getLeft());
            if (child.getLeft() != null) {
                child.getLeft().setUp(parent);
            }
            child.setLeft(parent);
        } else {
            throw new IllegalArgumentException("Child is not a child of parent");
        }

        parent.setUp(child);
        child.setUp(grandparent);

        if (grandparent != null) {
            if (grandparent.getLeft() == parent) {
                grandparent.setLeft(child);
            } else {
                grandparent.setRight(child);
            }
        } else {
            this.root = child;
        }
    }

    // Test Methods
    
    // Test 1: Right rotation
    public boolean test1() {
    BSTRotation<Integer> tree = new BSTRotation<>();
    BinaryNode<Integer> root = new BinaryNode<>(10);
    BinaryNode<Integer> left = new BinaryNode<>(5);
    tree.root = root;
    root.setLeft(left);
    left.setUp(root);
    tree.rotate(left, root);
    return tree.root == left && left.getRight() == root && root.getUp() == left;
   }

    // Test 2: Left rotation
    public boolean test2() {
    BSTRotation<Integer> tree = new BSTRotation<>();
    BinaryNode<Integer> root = new BinaryNode<>(10);
    BinaryNode<Integer> right = new BinaryNode<>(15);
    BinaryNode<Integer> shared1 = new BinaryNode<>(12);
    BinaryNode<Integer> shared2 = new BinaryNode<>(17);
    tree.root = root;
    root.setRight(right);
    right.setUp(root);
    right.setLeft(shared1);
    right.setRight(shared2);
    shared1.setUp(right);
    shared2.setUp(right);
    tree.rotate(right, root);
    return tree.root == right && right.getLeft() == root && root.getUp() == right && root.getLeft() == null && root.getRight() == shared1 && shared1.getUp() == root
        && right.getRight() == shared2 && shared2.getUp() == right;
    }


    // Test 3: Rotation on non root with a grandparent
    public boolean test3() {
    BSTRotation<Integer> tree = new BSTRotation<>();
    BinaryNode<Integer> grand = new BinaryNode<>(15);
    BinaryNode<Integer> parent = new BinaryNode<>(10);
    BinaryNode<Integer> child = new BinaryNode<>(5);

    tree.root = grand;
    grand.setLeft(parent);
    parent.setUp(grand);
    parent.setLeft(child);
    child.setUp(parent);

    tree.rotate(child, parent);

    return tree.root == grand && grand.getLeft() == child && child.getRight() == parent;
    }


    // Main method
    public static void main(String[] args) {
        BSTRotation<Integer> tree = new BSTRotation<>();
        System.out.println("Test1: " + tree.test1());
        System.out.println("Test2: " + tree.test2());
        System.out.println("Test3: " + tree.test3());
    }
}
