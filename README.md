Tree Traversal

Consider the tree below:
     A
   /   \
  C	    B
 / \	 \
F   E	  D
   /	 / \
  R     X   W
	 
We consider preorder traversal of this tree as NRL(Node - Right Child - Left Child).
NRL sequence: A B D W X C E R F

Also, we consider inorder traversal of this tree as RNL(Right Child - Node - Left Child).
RNL sequence: W D X B A E R C F

This program gets NRL and RNL sequences of a tree as inputs and displays postorder traversal sequence and zigzag traversal sequence as outputs.

Postorder traversal is considered as RLN (Right Child - Left Child - Node).
RLN sequence: W X D B R E F C A

Zigzag traversal sequence of this tree: A C B D E F R X W

It also gets an integer number like n as input and displays nth node of the tree. Index of each node is considered as below:
     0
   /   \
  2	    1
 / \	 \
5   4	  3
   /	 / \
  8     7   6

So, if n = 1, the 1st node will be 'B'.
