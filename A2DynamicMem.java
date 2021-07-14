// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public int Free(int startAddr) {
        Dictionary found= allocBlk.Find(startAddr,true);
        if(found!=null){
            freeBlk.Insert(found.address,found.size,found.size);
            allocBlk.Delete(found);
            return 0; }
        return -1;
    }

    public void Defragment() {
        Dictionary extra; //auxilary-space
        if(type==2){
            extra= new BSTree(); }
        else if(type==3){
            extra = new AVLTree(); }
        else{ return;}

        Dictionary start = freeBlk.getFirst();
        if(start == null) { return;}

        while(start!=null){
            extra.Insert(start.address,start.size,start.address);
            start=start.getNext();
        }
        Dictionary estart= extra.getFirst();
        Dictionary follower = estart.getNext();
        while(follower!=null){
            if(estart.address+estart.size==follower.address){
                follower.size=follower.size +estart.size;
                estart.size =-1;
                follower.address = estart.address;
                estart=follower;
                follower=follower.getNext();
            }else{
                estart = follower;
                follower=follower.getNext();
            }
        }
        Dictionary modified;
        if(type==2){
            modified= new BSTree(); }
        else if(type==3){
            modified = new AVLTree(); }
        else{ return;}

        Dictionary newstart = extra.getFirst();
        while(newstart!=null){
            if(newstart.size!=-1){
                modified.Insert(newstart.address,newstart.size,newstart.size);
                newstart=newstart.getNext();
            }else{
                newstart=newstart.getNext();
            }
        }
        freeBlk= modified;
    }

    /*
    public static void main(String[] args) {
        A2DynamicMem t1 = new A2DynamicMem(100,3);
        System.out.println(t1.Allocate(10)); //0
        System.out.println(t1.Allocate(10)); //10
        System.out.println(t1.Allocate(10)); //20
        System.out.println(t1.Free(30)); //-1
        System.out.println(t1.Free(10)); //0
        System.out.println(t1.Free(0));  //0
        System.out.println(t1.Free(20));  //0
        t1.Defragment();
        System.out.println(t1.Allocate(100)); //0
    }
    

    public static void main(String[] args) {
        int size = 100000;
        for (int factor = 1; factor <= 100; factor++) {
            long t1 = System.currentTimeMillis();
            int netsize = factor * size;
            A2DynamicMem RAM = new A2DynamicMem(netsize, 3);
            for (int i = 0; i <= netsize + 100; i++) {
                RAM.Allocate(1);
            }
            long t2= System.currentTimeMillis();
            for (int i = 0; i <= netsize + 100; i++) {
                RAM.Free(i);
            }
            long t3 = System.currentTimeMillis();
            RAM.Defragment();
            long t4 = System.currentTimeMillis();
            System.out.println("Time taken for RAM size= " + netsize + " Allocate took " + (t2 - t1) + " Free took " + (t3 - t2)+" Defragmentor took "+(t4-t3)+ " Total-run-time "+(t4-t1));
        }
    } */

}
