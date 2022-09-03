import java.io.*; 
import java.util.*; 

// Tree treeNode
class treeNode {
    int id;
    int level;
    treeNode root;
    treeNode next;
    treeNode child;
    treeNode prev;
    treeNode parent;
    treeNode(int id){        
        this.id=id;
           
    }
  
}
class AvltreeNode{
    int id;
    treeNode n;
AvltreeNode left;
AvltreeNode right;
int levelavl;
int height;
AvltreeNode parent;
        
AvltreeNode(int id){
    this.id=id;
}

}
class LLNode{
    int id;
    LLNode next;
    LLNode prev;
    LLNode(int id){
        this.id=id;
    }
}
class LinkedList{
    LLNode first, last;
    int number;

    LinkedList()
    {
        number = 0;

    }

    void Insert(LLNode n)
    {
        last.next = n;
        n.prev= last;
        last = last.next;
        number++;
    }

    void Delete(LLNode n)
    {
        n.prev.next = n.next;
        n.next.prev = n.prev;
        number--;
    }
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root treeNode
int lev=0;
AvltreeNode rootavlnode;
treeNode root;
//public int level=0;
public int s=0;

public boolean isEmpty(){
    //your implementation
    if(s==0){
        return(true);
    }
    else {
        return(false);
    }   
} 

public int size(){
    //your implementation
    return(s);
    // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public int level(int id) throws IllegalIDException{
    //your implementation
    AvltreeNode S=search(rootavlnode,id);
    return(S.n.level);
     //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 
int max(int a, int b) {
    return (a > b) ? a : b;
}
int height(AvltreeNode n){
    if(n==null){
      return(0);
    }
    return(n.height);
    }            
public void hireOwner(int id) throws NotEmptyException{
    //your implementation
    if(root!=null){
        throw new  NotEmptyException();
    }
    else{
   root=new treeNode(id);
    rootavlnode=new AvltreeNode(id);
   rootavlnode.n=root;
   rootavlnode.height=1+max(height(rootavlnode.left),height(rootavlnode.right));
   root.level=1;
  lev++;
   s++;
    }
     //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}
public AvltreeNode search(AvltreeNode rootavlnode ,int id)throws IllegalIDException{
    if(rootavlnode==null){
        throw new IllegalIDException();
    }
    if(rootavlnode.id==id){
          return(rootavlnode);
    }
    if(rootavlnode.id>id){
      return(search(rootavlnode.left,id));
    }
    else
    return(search(rootavlnode.right,id));
}
int getBalance(AvltreeNode n) {
    if (n == null)
        return 0;

    return height(n.left) - height(n.right);
}
AvltreeNode rightRotate(AvltreeNode y) {
    AvltreeNode x = y.left;
    AvltreeNode T2 = x.right;

    // Perform rotation
    x.right = y;
    y.left = T2;
    if(T2!=null){
        T2.parent = y;
    }

    if(y==rootavlnode){
        rootavlnode = x;
    }

    // Update heights
    y.height = max(height(y.left), height(y.right)) + 1;
    x.height = max(height(x.left), height(x.right)) + 1;
    if(y.parent!=null){
        if(y==y.parent.right){
            y.parent.right=x;
        }else if(y==y.parent.left){
            y.parent.left=x;
        }
    }
    x.parent=y.parent;
    
    y.parent=x;     
    // Return new root
    return x;
}

AvltreeNode leftRotate(AvltreeNode x) {
    AvltreeNode y = x.right;
    AvltreeNode T2 = y.left;

    // Perform rotation
    y.left = x;
    x.right = T2;
    if(T2!=null){
        T2.parent = x;
    }
    if(x==rootavlnode){
        rootavlnode = y;
    }

    //  Update heights
    x.height = max(height(x.left), height(x.right)) + 1;
    y.height = max(height(y.left), height(y.right)) + 1;
    if(x.parent!=null){
        if(x==x.parent.right){
            x.parent.right=y;
        }else if(x==x.parent.left){
            x.parent.left=y;
        }
    }
     y.parent=x.parent;
     x.parent=y;
    // Return new root
    return y;
}

public void makeavl(AvltreeNode node,int id,treeNode tempo,int w) throws IllegalIDException{
    
    
    if(node.id>id){
        if(node.left==null){
            node.left=new AvltreeNode(id);
            node.left.n=tempo;
            node.levelavl=w;
            node.left.parent=node;
        }
        makeavl(node.left,id,tempo,w);
    }
    else if(node.id<id){
        if(node.right==null){
            node.right=new AvltreeNode(id);
            node.right.n=tempo;
            node.levelavl=w;
            node.right.parent=node;
    }
        makeavl(node.right,id,tempo,w);
    }
    node.height=1+max(height(node.left),height(node.right));
  int balance=getBalance(node);
  //System.out.println("-----------"+node.id);
  //printTree(rootavlnode);
  if (balance > 1 && id < node.left.id)
             rightRotate(node);

            else if (balance > 1 && id > node.left.id) {
                node.left = leftRotate(node.left);
                 rightRotate(node);
            }

            else if (balance < -1 && id > node.right.id) {
                leftRotate(node);
            }       
            else if (balance < -1 && id < node.right.id) {
                
                node.right = rightRotate(node.right);
                 leftRotate(node);
            }   
}

public void hireEmployee(int id, int bossid) throws IllegalIDException{
    //your implementation
    treeNode tempo;
    treeNode tempoprev=null;
    treeNode y;

    if(root==null){
        throw new IllegalIDException();
    }
    AvltreeNode temp=search(rootavlnode,bossid);
    treeNode boss=temp.n;
    if(boss.child==null){
        boss.child=new treeNode(id);
        tempo=boss.child;
        tempo.parent=boss;
        tempo.level=boss.level+1;
        lev++;
    }
    else{
         tempo=boss.child;
         if(tempo.id>id){
             y=new treeNode(id);
             boss.child=y;
             y.parent=boss;
             y.next=tempo;
             tempo.prev=y;
         }
         else{
        while(tempo.id<id&&tempo.next!=null){
            tempoprev=tempo;
            tempo=tempo.next;
        }
        if(tempo.next==null){
            y=new treeNode(id);
            tempo.next=y;
            y.prev=tempo;
            y.parent=boss;
        }
        else{
         y=new treeNode(id);
        tempoprev.next=y;
        y.prev=tempoprev;
        y.next=tempo;
        tempo.prev=y;
        y.parent=boss;
        }
    }
        y.level=boss.level+1;
        tempo=y;

    }
    int w=tempo.level;
    makeavl(rootavlnode,id,tempo,w);
    
    s++;
     //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public void fireEmployee(int id) throws IllegalIDException{
    //your implementation
    AvltreeNode t=search(rootavlnode,id);
    if(t.n.child!=null){
        throw new  IllegalIDException();
    }

    if(t.n.parent!=null&&t.n.parent.child.id==id){
        t.n.parent.child=t.n.next;
        s--;
        //also to delete this node from AVLTREE TO BE DONE(NOT DID YET: 23/2/22)
    }
    else{
        if(t.n.prev!=null){
            t.n.prev.next=t.n.next;
        }
        if(t.n.next!=null)
         t.n.next.prev=t.n.prev;
         s--;
         //also to delete this node from AVLTREE TO BE DONE(NOT DID YET: 23/2/22)
    }
    t.n=null;
    }
    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");



public void fireEmployee(int id, int manageid) throws IllegalIDException{
    //your implementation
    AvltreeNode t=search(rootavlnode,id);

    if(t.n.id==root.id){
        throw new  IllegalIDException();
    }

    AvltreeNode t1=search(rootavlnode,manageid);
    if(t1.n==null){
        throw new  IllegalIDException();
    }
    if(t1.n.child==null){
        t1.n.child=t.n.child;
        t1.n.next=t.n.next;
        if(t.n.next!=null){
            t.n.next.prev=t.n.prev;
        }

        
        treeNode tempo=t.n.child;
        while(tempo!=null){
            tempo.parent=t1.n;
            tempo=tempo.next;
        }

    }
    else{
    treeNode tempo=t1.n.child;
    while(tempo.next!=null){
        tempo=tempo.next;
    }
    
    tempo.next=t.n.child;
    t.n.child.prev=tempo;
    t.n.child.parent=tempo.parent;
    while(tempo!=null){
        tempo.parent=t1.n;
        tempo=tempo.next;
    }
    t.n.child=null;
    if(t.n.next!=null&&t.n.prev!=null){
    t.n.prev.next=t.n.next;
    if(t.n.next!=null){          
    t.n.next.prev=t.n.prev;
    }
}
else{
    t.n.parent.child=t.n.next;
}

    }
    s--;
    t.n=null;
     //also to delete this node from AVLTREE TO BE DONE(NOT DID YET: 23/2/22)
     //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}                                                     

public int boss(int id) throws IllegalIDException{ 
    //your implementation
    AvltreeNode t=search(rootavlnode,id);
    if(root.id==id){
        return(-1);
    }
    else
    return(t.n.parent.id);
     //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{
    //your implementation
      AvltreeNode t1=search(rootavlnode,id1);
      AvltreeNode t2=search(rootavlnode,id2);
      if(t1.n==root||t2.n==root){
          throw new IllegalIDException();
              }
      while(true){
          if(t1.n.level==t2.n.level){
              break;
          }
         if(t1.n.level<t2.n.level) {
             t2.n=t2.n.parent;
         }
         else if(t1.n.level>t2.n.level){
            t1.n=t1.n.parent;
         }
      }
      if(t1.n==t2.n){
          return(t1.n.parent.id);
      }
      while(true){
      if(t1.n==t2.n){
          return(t1.n.id);
      }
      else{
          t1.n=t1.n.parent;
          t2.n=t2.n.parent;
      }
    }


    //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}
static void swap(int[] arr, int i, int j)
{
    int tem = arr[i];
    arr[i] = arr[j];
    arr[j] = tem;
}
static int partition(int[] arr, int l, int h)
{
     
    
    int pivot = arr[h];
     
    
    int i = (l - 1);
 
    for(int j = l; j <= h - 1; j++)
    {
         
        
        if (arr[j] < pivot)
        {
             
            
            i++;
            swap(arr, i, j);
        }
    }
    swap(arr, i + 1, h);
    return (i + 1);
}
static void quickSort(int[] arr, int low, int high)
{
    if (low < high)
    {
         
       
        int pi = partition(arr, low, high);
 
        
        quickSort(arr, low, pi - 1);     
        quickSort(arr, pi + 1, high);
    }
}
 
public String toString(int id) throws IllegalIDException{
    
    String st="";
     AvltreeNode t=search(rootavlnode,id);
     
     
     treeNode t1=t.n;
     int f=t1.level;
    
     int a[]=new int[s];
     
     a[0]=t1.id;
     int i=1;
     for(int k=f;k<lev-1;k++){
        int ac[]=new int[s];
        int j=0;
        for(int w=0;w<i;w++){
            AvltreeNode s=search(rootavlnode,a[w]);
             
              treeNode t2=s.n.child;
                  while(t2!=null){
                      ac[j]=t2.id;
                      j++;
                      t2=t2.next;
                  }      
                  
                }                                 
              quickSort(a,0,i-1);
              for(int w=0;w<i;w++){
                   st=st+" "+a[w];
                  }
              
              
              
              
           
              a=ac;
             
              i=j;   
     
    }
    
    
    
     
    st=st.substring(1,st.length());
    
     

    
    return(st);
     //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

}
// class IllegalIDException extends Exception{
//     public IllegalIDException(){
//         super();
//         printStackTrace();
        
//     }
// }

// class NotEmptyException extends Exception{
//     public NotEmptyException(){
//         super();
//     }
// }


