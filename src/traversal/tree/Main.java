package traversal.tree;

import java.util.*;

class Node implements Comparable<Node>{

    private char value;
    private Node rightChild, leftChild;

    public Node (char value) {
        setValue(value);
        setRightChild(null);
        setLeftChild(null);
    }

    public void setValue(char value) {
        this.value = value;
    }
    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public char getValue() {
        return value;
    }

    public Node getRightChild() {
        return rightChild;
    }
    public Node getLeftChild() {
        return leftChild;
    }

    @Override
    public int compareTo(Node node) {
        return Character.compare(getValue(), node.getValue());
    }
}

class Tree {

    private Node root;

    public Tree (Node root) {
        setRoot(root);
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void printPostOrder () {

        try {

            Stack<Character> stack = new Stack<>();
            printPostOrder(root, stack);
            System.out.println(stack);

        } catch (Exception e) {
            //Do nothing
        }
    }

    /**
     * Push elements of the tree by post-order method into a stack
     * First we push right children of each node, then its left children, then itself
     *
     * @param root root of the tree
     * @param stack stack to keep elements in post-order way
     */
    private void printPostOrder(Node root, Stack<Character> stack) {

        if (root.getRightChild() != null) {

            printPostOrder(root.getRightChild(), stack);

        }

        if (root.getLeftChild() != null) {

            printPostOrder(root.getLeftChild(), stack);

        }

        stack.push(root.getValue());
    }

    public void printZigZag () {

        try {

            Stack<Character> stack = new Stack<>();
            boolean leftToRight = true;
            printZigZag(root, leftToRight, stack);
            System.out.println(stack);

        } catch (Exception e) {
            //Do nothing
        }

    }

    /**
     * Push elements of the tree by zigzag method into a stack
     * We must consider the level of each node and its children when pushing it
     * We must change the direction from left to right and right to left level by level
     *
     * @param root root of the tree
     * @param leftToRight starting direction
     * @param stack stack to keep elements in zig-zag way
     */
    private void printZigZag (Node root, boolean leftToRight, Stack<Character> stack) {

        Stack<Node> curLevel = new Stack<>();
        Stack<Node> nextLevel = new Stack<>();

        curLevel.push(root);

        while (!curLevel.isEmpty()) {

            Node node = curLevel.pop();

            stack.push(node.getValue());

            if (leftToRight) {

                if (node.getRightChild() != null) {

                    nextLevel.push(node.getRightChild());

                }

                if (node.getLeftChild() != null) {

                    nextLevel.push(node.getLeftChild());

                }

            } else {

                if (node.getLeftChild() != null) {

                    nextLevel.push(node.getLeftChild());

                }

                if (node.getRightChild() != null) {

                    nextLevel.push(node.getRightChild());

                }
            }

            if (curLevel.isEmpty()) {

                leftToRight = !leftToRight; //Changing direction
                Stack<Node> temp = curLevel;
                curLevel = nextLevel;
                nextLevel = temp;

            }
        }
    }

    /**
     * Find nth element of the tree and return it
     * We must consider the level of each node and its children when traversing the tree
     * We must traverse through the tree by right to left direction for all levels
     * So we use a deque (double queue) to stays on the Right to Left direction all the time
     *
     * @param n number of the element we want
     *
     * @return  nth element
     */
    public char findNthElement (int n) {

        Node node = root;

        if (n != 0) {

            int i = 0;

            Deque<Node> curLevel = new ArrayDeque<>();
            Deque<Node> nextLevel = new ArrayDeque<>();

            curLevel.addFirst(node);

            while (!curLevel.isEmpty()) {

                Node tempNode = curLevel.removeFirst();
                if (tempNode.getRightChild() != null) {
                    nextLevel.add(tempNode.getRightChild());
                    i++;
                }

                if (i == n) {
                    node = nextLevel.removeLast();
                    break;
                }

                if (tempNode.getLeftChild() != null) {
                    nextLevel.add(tempNode.getLeftChild());
                    i++;
                }

                if (i == n) {
                    node = nextLevel.removeLast();
                    break;
                }

                if (curLevel.isEmpty()) {

                    for (Node nodeItr : nextLevel) {
                        curLevel.addLast(nodeItr);
                    }

                    nextLevel.clear();
                }
            }

        }

        return node.getValue();
    }
}

public class Main {

    /**
     * An integer to keep track of nrl sequence
     */
    static int idx = 0;

    /**
     * Convert nrl or rnl sequence to an array of characters and return it
     *
     * @param str nrl or rnl sequence
     * @return 	array of characters from the input sequence
     */
    static char[] toArray (String str) {

        int size = (str.length() + 1) / 2;
        char[] arr = new char[size];

        for (int i = 0; i < size; i++) {
            arr[i] = str.charAt(i * 2);
        }

        return arr;
    }

    /**
     * Construct the tree from nrl and rnl sequences and return its root node
     *
     * @param index is index of nrl element and is assigned by idx
     * @param nrl nrl sequence array
     * @param rnl rnl sequence array
     *
     * @return 	node root of the tree
     */
    static Node constructTree (int index, char[] nrl, char[] rnl) {

        char r = nrl[index];
        char[] rightChildren, leftChildren;
        int rc = 0, lc;		//rc keeps count of right children and lc keeps count of left children
        int i = 0;

        while(r != rnl[i]) {	//When we reach the current element of nrl (keep track of it by index and idx)
            //in rnl, we stop counting right children
            rc++;				//Elements in rnl before the root are its right children and others are left children
            i++;
        }
        lc = rnl.length - rc - 1;

        rightChildren = new char[rc]; //For storing right children of the root
        leftChildren = new char[lc]; //For storing left children of the root

        Node node = new Node (r);
        idx++;

        if (rc > 0) {
            for (int j = 0; j < rc; j++) {
                rightChildren[j] = rnl[j];
            }

            Node rightChild = constructTree(idx, nrl, rightChildren); //Constructing the tree of right children
            node.setRightChild(rightChild);
        }

        if (lc > 0) {
            for (int j = 0; j < lc; j++) {
                leftChildren[j] = rnl[j + i + 1];
            }

            Node leftChild = constructTree(idx, nrl, leftChildren); //Constructing the tree of left children
            node.setLeftChild(leftChild);
        }

        return node;
    }
    public static void main (String[] args) {

        Scanner scan = new Scanner(System.in);

        String nrl, rnl;
        int n;

        nrl = scan.nextLine();
        rnl = scan.nextLine();
        n = scan.nextInt();

        Node root = constructTree(idx, toArray(nrl), toArray(rnl));
        Tree tree = new Tree(root);

        tree.printPostOrder();
        tree.printZigZag();

       try {
            System.out.println(tree.findNthElement(n));
        } catch (Exception e) {
            //Do nothing
        }
    }
}
