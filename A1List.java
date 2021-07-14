// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List


public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {   if(this== null){ return null;}
        if(this.next==null){
            return null;   //Insert-in-front-of-tail-not-allowed
        }
        A1List my_node = new A1List(address,size,key);
        my_node.next= this.next;
        my_node.prev = this;

        this.next.prev = my_node;
        this.next = my_node;

        return my_node;
    }

    public boolean Delete(Dictionary d) {
        if(this== null || d==null){ return false;}
        A1List current = this;
        A1List current1 = this;
        boolean trying = true;
        int mypointer=-1;
        while (current.prev != null) {
            if (current.key == d.key) {
                if(current.address==d.address && current.size==d.size) {
                    trying = false;
                    mypointer = 0;
                    break;
                }else{
                  current=current.prev;
                }
            } else {
                current = current.prev;
            }
        }
        if(trying){
            while (current1 != null) {
                if (current1.key == d.key && current1.prev!=null) {
                    if(current1.address==d.address && current1.size==d.size){
                        trying=false;
                        mypointer=1;
                        break;}
                    else{
                        current1=current1.getNext(); //getNext-returns-null-when-it-reaches-tail-sentinal
                    }
                } else {
                    current1 = current1.getNext();
                }
            }
        }

        if(trying){
            return false; }
        if(mypointer==0 ){
            current.prev.next=current.next;
            current.next.prev=current.prev;
            current.next=null;
            current.prev=null;
            return true;
        }
        current1.prev.next = current1.next;
        current1.next.prev = current1.prev;
        current1.next = null;
        current1.prev = null;
        return true;
        }


    public A1List Find(int k, boolean exact) {
        if(this==null){ return null;}
        if (exact) {
            A1List current1 = this.getFirst();
            while (current1 != null) {
                if (current1.key == k) {
                    return current1;
                } else {
                    current1 = current1.getNext();
                }
            }
            return null;
        } else {
            A1List current1 = this.getFirst();
            while (current1 != null) {
                if (current1.key >= k) {
                    return current1;
                } else {
                    current1 = current1.getNext();
                }
            }
            return null;
        }
    }

    public A1List getFirst()
    {   if(this== null){ return null;}
        A1List current = this;
        while(current.prev !=null){
        current= current.prev; }
        if(current.key!=-1 || current.address!=-1 || current.size!= -1 ){
            return null;     //head's-data-is-not-same-whereas-.prev-is-null
        }
        // current is head now
        if(current.next.next==null && current.next.key ==-1 && current.next.address==-1 && current.next.size==-1){
            return null;}   // empty list
        else{
            return current.next;
        }
    }
    
    public A1List getNext()
    {   if(this== null){ return null;}
        if(this.key == -1 && this.address==-1 && this.size==-1 && this.next==null){
        return null;  //u are on tail currently
        }else if(this.next.next==null && this.next.key==-1 && this.next.address==-1 && this.next.size==-1 ){
        return null;}  // next node is tail
        else{
        return this.next; }
    }

    public boolean sanity()
    {   if(this== null){ return false;}
        A1List mystart = this.getFirst();
        if(mystart==null){
            return this.prev == null || this.next.next == null;  //check-head.prev-tail.next
        }else {
            if (mystart.prev.prev != null) {
                return false;
            }                 //head--prev-pointer-checked
            while (mystart.next != null) {
                if (mystart.prev.next != mystart) {
                    return false;
                }
                if (mystart.next.prev != mystart) {
                    return false;
                }         // invariants-checked
                mystart = mystart.next;
            }             // reached tail-node
            if (mystart.key != -1 || mystart.address != -1 || mystart.size != -1) {
                return false;     //conforming-that-its-tail-node-by-data
            }
            /*
            mystart=this.getFirst();
            while(mystart!=null){
                if(mystart.address<0){ return false;}  // Negative-address is not-allowed
                else{ mystart=mystart.getNext();}
            }
             */
            A1List sound = this.getFirst().prev;      //start-race-from-head-sound-vs-light
            A1List light = this.getFirst().prev;      // faster-pointer-starting-from-head
            while(sound!=null && light!=null){
                if(light.next==null){
                    break;    // light-reached-the-tail-sentinal
                }
                sound=sound.next;
                light=light.next.next;
                if(sound==light){
                    return false;        //cycle-detected
                }
            }
            // no-cycle-in-list
        }
        return true;
    }
    /*
    public static void main(String[] args) {
        A1List mylist = new A1List();
        mylist.Insert(10,10,10);
        mylist.Insert(423,423,231);
        mylist.Insert(-1,-1,-1);
        for(int i=1;i<=5;i++){
            mylist.Insert(i,i*5,i+1);
        }
        A1List mynode = new A1List(10,10,10);
        A1List mymiddle = mylist.getNext().getNext().getNext();
        mymiddle.Delete(mynode);
        A1List start = mylist.getFirst();
        while(start!=null){
            System.out.println(start.key);
            start=start.getNext();
        } // 6 5 4 3 2 -1 231
        System.out.println(mylist.sanity()); //true
        start = mylist.getFirst();
        start.Delete(start);
        start = mylist.getFirst();
        while(start!=null){
            System.out.println(start.key);
            start=start.getNext();
        }  // 5 4 3 2 -1 231
        mynode = new A1List(-1,-1,-1);
        mylist.Delete(mynode);
        start = mylist.getFirst();
        while(start!=null){
            System.out.println(start.key);
            start=start.getNext(); // 5 4 3 2 231
        }

    } */
}