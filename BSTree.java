// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

import java.awt.*;

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree item = new BSTree(address,size,key);
        BSTree root = this;
        while(root.parent!=null){
            root=root.parent;
        } // root is sentinal now
        if(root.right==null){
            root.right= item;
            item.parent=root;
            return item;
            //tree was empty
        }else{
            root=root.right; //come-to-root-from-sentinal
            BSTree child = root.parent.right; // copy-of-root-node
            while(child!=null){
                if((child.key < item.key) || (child.key==item.key && child.address<item.address)){
                    child=child.right;
                    if(child==null){
                        root.right =item;
                        item.parent=root;
                        return item;
                    }
                    root=root.right;
                }else{
                    child=child.left;
                    if(child==null){
                        root.left =item;
                        item.parent=root;
                        return item;
                    }
                    root=root.left;
                }
            }
        }
        return item;
    }

    public boolean Delete(Dictionary e) {
        BSTree root = this;
        while (root.parent != null) {
            root = root.parent;
        } // root is sentinal now
        if (root.right != null) {
            root = root.right; //root-have-tree-root-now
            while (root != null) {
                if (root.key == e.key && root.address == e.address && root.size == e.size) {
                    //delete this node;
                    int kids = 0;
                    if (root.left != null) {
                        kids+=1;
                    }
                    if (root.right != null) {
                        kids+=1;
                    }
                    //we-got-non-null-subtree-count
                    if (kids == 0) {
                        if (root == root.parent.left) {
                            root.parent.left = null;}
                        if (root == root.parent.right) {
                            root.parent.right = null;}
                        return true;
                        }
                    if (kids == 1) {
                        BSTree children = root.left;
                        if (root.right != null) {
                            children = root.right;
                        }
                        if (root == root.parent.left) {
                            root.parent.left = children;}
                        if (root == root.parent.right) {
                            root.parent.right = children;}
                        children.parent=root.parent;
                        return true;
                        }
                    if (kids == 2) {
                        BSTree mysuccessor = root.getNext();
                        root.address=mysuccessor.address;
                        root.key=mysuccessor.key;
                        root.size=mysuccessor.size;
                        BSTree children= mysuccessor.right;
                        if(children!=null) {
                            if(mysuccessor==mysuccessor.parent.left){
                                mysuccessor.parent.left = children;
                                children.parent=mysuccessor.parent;}
                            else{
                                mysuccessor.parent.right=children;
                                children.parent=mysuccessor.parent;
                            }
                        }else{
                            if(mysuccessor==mysuccessor.parent.left){
                                mysuccessor.parent.left=null;
                            }else if(mysuccessor==mysuccessor.parent.right){
                                mysuccessor.parent.right=null;
                            }
                        }
                        return true;
                    }
                } else if (root.key < e.key || (root.key == e.key && root.address < e.address)) {
                    root = root.right;
                } else {
                    root = root.left;
                }
            }
            return false;  //search-completed-no-such-node-found
        }
            return false; //tree-was-empty
        }

    public BSTree Find(int key, boolean exact)
    {   BSTree root = this;
        while(root.parent!=null){
            root=root.parent;
        } // root is sentinal now
        if(root.right!=null){
            root=root.right; //root-have-tree-root-now
        }else{
            return null; //empty-tree
        }
        if(exact){
            while(root.key >=key && root.left!=null) {
                root = root.left;
            }
            while(root.right!=null && root.right.key<key){
                root=root.right;
            }
            while(root.key!=key){
                root=root.getNext();
                if(root==null){
                    return null;
                }
                if(root.key>key){
                    return null;
                }
            }
            return root;
        }else {
            while(root.key>=key && root.left!=null){
                root=root.left;
            }
            while(root.right!=null && root.right.key<key){
                root=root.right;
            }
            while(root.key<key){
                    root=root.getNext();
                    if(root==null){
                        return null;
                    }
                }
            return root;
            }
    }

    public BSTree getFirst()
    {   BSTree root = this;
        while(root.parent!=null){
            root=root.parent;
        } // root is sentinal now
        if(root.right!=null){
            root=root.right; //root-have-tree-root-now
        }else{
            return null; //empty-tree
        }

        while(root.left!=null){
            root=root.left;
        }
        return root;
    }

    public BSTree getNext()
    {   BSTree present = this;
        if(present.parent==null) return null;
        if(present.right!=null){
            present=present.right;
            while(present.left!=null){
                present=present.left;
            }
            return present;
        }else{
            while(present!=present.parent.left){
                present=present.parent;
                if(present.parent==null){
                    return null; // you-reached-sentinal-max-element's-successor-doesn't-exist
                }
            }
            return present.parent;
        }
    }

    // private-helper-methods-for-sanity
    private boolean Compare( BSTree node1,BSTree node2){
        if(node1.key!=node2.key){
            return node1.key<node2.key;
        }else{
            return node1.address<node2.address;
        }
    }
    private boolean Equal(BSTree node1,BSTree node2){
        return (node1.key==node2.key) && (node1.address==node2.address);
    }
    private BSTree treeMin(BSTree node){
        if(node ==null) return null;
        while(node.left!=null){
            node = node.left;
        }
        return  node;
    }
    private BSTree treeMax(BSTree node){
        if(node ==null) return null;
        while(node.right!=null){
            node=node.right;
        }
        return node;
    }
    private boolean checkBST(BSTree root, BSTree minValue, BSTree maxValue) {
        if (root == null) {
            return true;
        }
        if(Equal(root,minValue) || Equal(root,maxValue)){
            return false; //equal-key,address-not-allowed
        }
        if ( Compare(root,minValue) || Compare(maxValue,root)) {
            return false; //key-property-violated
        }
        return (checkBST(root.left, minValue , root ) && checkBST(root.right, root, maxValue));
    }
    private boolean checkLink(BSTree root){
        if(root==null){
            return true;
        }
        if(root.right!=null){
            if(root.right.parent != root ) return false;
            if(root.left!=null){
                if(root.left.parent !=root) return false;
                else return checkLink(root.left) && checkLink(root.right);
            }else{
                return checkLink(root.right);}
        }else{
            if(root.left!=null){
                if(root.left.parent !=root) return false;
                else return checkLink(root.left);
            }
        }
        return true;
    }

    public boolean sanity()
    {   BSTree root = this;
        if(root==null){ return true;}
        if(root.left== null && root.right==null){
            return true;
        }
        if(root.parent==null && root.right!=null){
            root=root.right;
        } //call-was-made-from-sentinal-so-comes-to-root
        if(!checkLink(root)){
            return false;
        } // parent-child-links-checked

        BSTree least_node = treeMin(root);  // minimum-value-node-of-tree
        BSTree max_node =treeMax(root);   // maximum-value-node-of-tree
        BSTree atleast = new BSTree(least_node.address,least_node.size,least_node.key-1);
        BSTree atmost = new BSTree(max_node.address,max_node.size,max_node.key +1);
        return checkBST(root, atleast, atmost);
        // going-from-root-to-leaves-key-value-property-checked
    }



}
 


