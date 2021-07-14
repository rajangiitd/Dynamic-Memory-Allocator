// Class: Height balanced AVL Tree
// Binary Search Tree
import java.util.*;
public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    {   AVLTree item = new AVLTree(address,size,key);
        AVLTree root = this;
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
            AVLTree child = root.parent.right; // copy-of-root-node
            while(child!=null){
                if((child.key < item.key) || (child.key==item.key && child.address<item.address)){
                    child=child.right;
                    if(child==null){
                        root.right =item;
                        item.parent=root;
                        Irebalance(item.parent);
                        break;
                    }
                    root=root.right;
                }else{
                    child=child.left;
                    if(child==null){
                        root.left =item;
                        item.parent=root;
                        Irebalance(item.parent);
                        break;
                    }
                    root=root.left;
                }
            }
            return item;
        }
    }

    public boolean Delete(Dictionary e)
    {
        AVLTree root = this;
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
                            root.parent.left = null; }
                        if (root == root.parent.right) {
                            root.parent.right = null;}
                        rebalance(root.parent);
                        return true;
                    }
                    if (kids == 1) {
                        AVLTree children = root.left;
                        if (root.right != null) {
                            children = root.right;
                        }
                        if (root == root.parent.left) {
                            root.parent.left = children;}
                        if (root == root.parent.right) {
                            root.parent.right = children;}
                        children.parent=root.parent;
                        rebalance(children);
                        return true;
                    }
                    if (kids == 2) {
                        AVLTree mysuccessor = root.getNext();
                        root.address=mysuccessor.address;
                        root.key=mysuccessor.key;
                        root.size=mysuccessor.size;
                        AVLTree children= mysuccessor.right;
                        if(children!=null) {
                            if(mysuccessor==mysuccessor.parent.left){
                                mysuccessor.parent.left = children;
                                children.parent=mysuccessor.parent;}
                            else{
                                mysuccessor.parent.right=children;
                                children.parent=mysuccessor.parent;
                            }
                            rebalance(children);
                        }else{
                            if(mysuccessor==mysuccessor.parent.left){
                                mysuccessor.parent.left=null;
                            }else if(mysuccessor==mysuccessor.parent.right){
                                mysuccessor.parent.right=null;
                            }
                            rebalance(mysuccessor.parent);
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

    public AVLTree Find(int k, boolean exact)
    {   AVLTree root = this;
        while(root.parent!=null){
            root=root.parent;
        } // root is sentinal now
        if(root.right!=null){
            root=root.right; //root-have-tree-root-now
        }else{
            return null; //empty-tree
        }
        if(exact){
            AVLTree child = root.parent.right;
            while(true){
                if(child.key==k && child.address==0){
                    return child;
                }else if(child.key>k || (child.key==k && child.address>0)){
                    child=child.left;
                }else{
                    child=child.right;
                }
                if(child!=null){
                    root=child;
                }else{
                    break;
                }
            }
            if(root.key==k){
                return root;
            }else {
                while (root.key < k) {
                    root = root.getNext();
                    if (root == null || root.key>k){
                        return null;}
                    if(root.key==k){
                        return root;
                    }}
                return null;}
            }else {
            AVLTree child = root.parent.right;
            while(true){
                if(child.key==k && child.address==0){
                    return child;
                }else if(child.key>k || (child.key==k && child.address>0)){
                    child=child.left;
                }else{
                    child=child.right;
                }
                if(child!=null){
                    root=child;
                }else{
                    break;
                }
            }
            if(root.key==k){
                return root;
            }else {
                while (root.key < k) {
                    root = root.getNext();
                    if (root == null ){
                        return null;}
                }
                return root;}
        }
    }

    public AVLTree getFirst()
    {   AVLTree root = this;
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

    public AVLTree getNext()
    {   AVLTree present = this;
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

    private void right_rotate(AVLTree x){
        AVLTree y = x.left;
        y.parent = x.parent;
        if(y.parent.left==x){
            y.parent.left=y;
        }else if(y.parent.right==x){
            y.parent.right=y;
        }
        x.left = y.right;
        if(x.left !=null){
            x.left.parent =x;}
        y.right = x;
        x.parent =y;
        x.height= Max(getHeight(x.left),getHeight(x.right)) +1;
        y.height= Max(getHeight(y.left),getHeight(y.right)) +1;
    }
    private void left_rotate(AVLTree x){
        AVLTree y = x.right;
        y.parent = x.parent;
        if(y.parent.left==x){
            y.parent.left = y;
        }else if(y.parent.right==x){
            y.parent.right = y;
        }
        x.right = y.left;
        if(x.right !=null){
            x.right.parent =x;
        }
        y.left = x;
        x.parent = y;
        x.height= Max(getHeight(x.left),getHeight(x.right)) +1;
        y.height= Max(getHeight(y.left),getHeight(y.right)) +1;
    }
    private void Irebalance( AVLTree x){
        if(x==null) return;
        while(x.parent!=null){
            int h = x.height;
            x.height= Max(getHeight(x.left),getHeight(x.right)) +1;
            if(x.height==h){ return;}
            if(getHeight(x.left)>=2+ getHeight(x.right)){
                if(getHeight(x.left.left)>=getHeight(x.left.right)){
                    right_rotate(x);
                }else{
                    left_rotate(x.left);
                    right_rotate(x);
                }
            }else if(getHeight(x.right)>=2+ getHeight(x.left)){
                if(getHeight(x.right.right)>=getHeight(x.right.left)){
                    left_rotate(x);
                }else{
                    right_rotate(x.right);
                    left_rotate(x);
                }
            }
            x=x.parent;
        }
    }
    private void rebalance( AVLTree x){
        if(x==null) return;
        while(x.parent!=null){
            x.height= Max(getHeight(x.left),getHeight(x.right)) +1;
            if(getHeight(x.left)>=2+ getHeight(x.right)){
                if(getHeight(x.left.left)>=getHeight(x.left.right)){
                    right_rotate(x);
                }else{
                    left_rotate(x.left);
                    right_rotate(x);
                }
            }else if(getHeight(x.right)>=2+ getHeight(x.left)){
                if(getHeight(x.right.right)>=getHeight(x.right.left)){
                    left_rotate(x);
                }else{
                    right_rotate(x.right);
                    left_rotate(x);
                }
            }
            x=x.parent;
        }
    }
    private int getHeight(AVLTree x){
        if(x==null) return -1;
        return x.height;
    }
    private int Max(int a, int b){
        if(a>b){
            return a;
        }
        return b;
    }
    /*----- SANITY-Helper-Methods-STARTS-NOW-----*/
    private boolean Compare( AVLTree node1,AVLTree node2){
        if(node1.key!=node2.key){
            return node1.key<node2.key;
        }else{
            return node1.address<node2.address;
        } //returns true if node1<node2
    }
    private boolean Equal(AVLTree node1,AVLTree node2){
        return (node1.key==node2.key) && (node1.address==node2.address);
    }
    private AVLTree treeMin(AVLTree node){
        if(node ==null) return null;
        while(node.left!=null){
            node = node.left;
        }
        return node; // return-minimum-key-of-tree-rooted-at-node
    }
    private AVLTree treeMax(AVLTree node){
        if(node ==null) return null;
        while(node.right!=null){
            node=node.right;
        }
        return node; //returns-maximum-key-of-tree-rooted-at-node
    }
    private boolean checkAVL(AVLTree root, AVLTree minValue, AVLTree maxValue) {
        if (root == null) {
            return true;
        }
        if(Equal(root,minValue) || Equal(root,maxValue)){
            return false; //equal-key,address-not-allowed
        }
        if ( Compare(root,minValue) || Compare(maxValue,root)) {
            return false; //key-property-violated
        }
        return (checkAVL(root.left, minValue , root ) && checkAVL(root.right, root, maxValue));
    }
    private boolean checkLink(AVLTree root){
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
    private int absdif(int a,int b){
        if(a>b) return a-b;
        else return b-a;
    }
    private boolean checkHeight( AVLTree root){
        if(root==null) return true;
        if(absdif(getHeight(root.left),getHeight(root.right))<=1){
            int heavykid = Max(getHeight(root.left),getHeight(root.right));
            if(getHeight(root)!= heavykid+1){
                return false;
            }else{
                return checkHeight(root.left) && checkHeight(root.right);
            }
        }else{
            return false;
        }
    }
    public boolean sanity()
    { AVLTree root = this;
        if(root==null){ return true;}
        if(root.left== null && root.right==null){
            return true;
        }
        if (root.parent == null && root.right == null) {
            return true; //empty tree
        }
        if(root.parent==null){
            root=root.right;
        } //call-was-made-from-sentinal-so-comes-to-root
        if(!checkLink(root)){
            return false;
        } // parent-child-links-checked
        AVLTree least_node = treeMin(root);  // minimum-value-node-of-tree
        AVLTree max_node =treeMax(root);   // maximum-value-node-of-tree
        AVLTree atleast = new AVLTree(least_node.address,least_node.size,least_node.key-1);
        AVLTree atmost = new AVLTree(max_node.address+1,max_node.size,max_node.key );
        return checkAVL(root, atleast, atmost) && checkHeight(root);
        // going-from-root-to-leaves-key-value-property-checked
    }
    public static void printtree(AVLTree root){
        if(root.parent==null){
            root=root.right;
            if(root==null){
                System.out.println("empty tree :)");
                return;
            }
        }
        System.out.println(root.key+" heve height of "+root.height);
        AVLTree left= root.left;
        if(left==null) {System.out.println(root.key + " have left child as "+ left);}
        else{ System.out.println(root.key + " have left as "+ left.key);}
        AVLTree right= root.right;
        if(right==null) System.out.println(root.key + " have right child as "+ right);
        else System.out.println(root.key + " have right child as "+ right.key);
        if(left!=null)  printtree(left);
        if(right!=null ) printtree(right);

    }

}


