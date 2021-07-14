// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

import javax.swing.text.html.HTMLWriter;

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if(blockSize<=0){
            return -1; }
        Dictionary Block_found = freeBlk.Find(blockSize,false);
        if(Block_found==null){
            return -1; }
        int allocationStartAt = Block_found.address;
        int sizeOfBlock = Block_found.size;
        freeBlk.Delete(Block_found);     // Block_found.Delete(Block_found) can delete in O(1) for DLL
        allocBlk.Insert(allocationStartAt,blockSize,allocationStartAt);
        if(sizeOfBlock>blockSize){
            freeBlk.Insert(allocationStartAt+blockSize,sizeOfBlock-blockSize,sizeOfBlock-blockSize); }
        return allocationStartAt;
    } 
    
    public int Free(int startAddr) {
        Dictionary found= allocBlk.Find(startAddr,true);
        if(found!=null){
            freeBlk.Insert(found.address,found.size,found.size);
            allocBlk.Delete(found);
            return 0; }
        return -1;
    }

    public static void main(String[] args) {
        int s=10;
        A1DynamicMem Ram= new A1DynamicMem(s,1);
        System.out.println(Ram.Allocate(-1));
        System.out.println(Ram.Free(-1));
        System.out.println(Ram.Allocate(-2));
        System.out.println(Ram.Free(-2));
    }
    /*
    public static void main(String[] args) {
        int size = 1000;
        for (int factor = 1; factor <= 100; factor++) {
            long t1 = System.currentTimeMillis();
            int netsize = factor * size;
            A1DynamicMem RAM = new A1DynamicMem(netsize, 1);
            for (int i = 0; i <= netsize + 100; i++) {
                RAM.Allocate(1);
            }
            long t2= System.currentTimeMillis();
            for (int i = 0; i <= netsize + 100; i++) {
                RAM.Free(i);
            }
            long t3 = System.currentTimeMillis();
            //RAM.Defragment();
            long t4 = System.currentTimeMillis();
            System.out.println("Time taken for RAM size= " + netsize + " Allocate took " + (t2 - t1) + " Free took " + (t3 - t2)+" Defragmentor took "+(t4-t3)+ " Total-run-time "+(t4-t1));
        }
    }

     */

}
