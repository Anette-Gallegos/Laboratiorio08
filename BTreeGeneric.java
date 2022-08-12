package laboratorio8;

public class BTreeGeneric<T extends Comparable<T>>{
	
    private BNodeGeneric<T> root;
    private int orden;
    private boolean up;
    private BNodeGeneric<T> nDes;
   
    public BTreeGeneric(int orden){
    	this.orden = orden;
    	this.root = null;
    }
    
    public boolean isEmpty(){
    	return this.root == null;
    }
    
    public boolean search(T cl){
    	return search(this.root, cl);
    }
    
    private boolean search(BNodeGeneric<T> current, T cl){
    	int pos[] = new int[1];
    	boolean fl;
    	
    	if(current.equals(null))
    		return false;
    	else{
	    	fl = searchNode(current,cl, pos);
	    	if(fl){
	    		System.out.println("Item "+cl+" encontrado en la posicion "+pos[0]);
	    		return true;
	    	}
	    	else
	    		return search(current.childs.get(pos[0]),cl);
    	}
    }
    
    private boolean searchNode(BNodeGeneric<T> current,T cl, int pos[]){
    	pos[0] = 0;
    	while(pos[0] < current.count && current.keys.get(pos[0]).compareTo(cl) < 0)
    		pos[0]++;
        if(pos[0] == current.count)
        	return false;
        return(cl.equals(current.keys.get(pos[0])));
    }
    
    public void insert(T cl){
    	up = false;
       	T mediana;
    	BNodeGeneric<T> pnew;
        mediana = push(this.root,cl);

        if(up){
        	pnew = new BNodeGeneric<T>(this.orden);
        	pnew.count = 1;
        	pnew.keys.set(0,mediana);
        	pnew.childs.set(0,this.root);
        	pnew.childs.set(1,nDes);
        	this.root = pnew;
        }
    }

    private T push(BNodeGeneric<T> current,T cl){ 
    	int pos[] = new int[1];
    	T mediana;
        if(current == null){
            up = true;
            nDes = null;
            return cl;
        }
        else{
            boolean fl;
            fl = searchNode(current,cl, pos);
            if(fl){
            	System.out.println("Item duplicado\n");
            	up = false;
            	return null;
            }
            mediana = push(current.childs.get(pos[0]),cl);
            if(up){
               	if(current.nodeFull(this.orden-1))
               		mediana = dividedNode(current,mediana,pos[0]);
                else{
                    up = false;
                    putNode(current,mediana,nDes,pos[0]);
                }
            }
            return mediana;
        }
    }
    
     private void putNode(BNodeGeneric<T> current,T cl,BNodeGeneric<T> rd,int k){
    	int i;
    	
        for(i = current.count-1; i >= k; i--) {		
        	current.keys.set(i+1,current.keys.get(i));
        	current.childs.set(i+2,current.childs.get(i+1));
        }
        current.keys.set(k,cl);		
        current.childs.set(k+1,rd);
        current.count++;
    }

    private T dividedNode(BNodeGeneric<T> current,T cl,int k){
    	BNodeGeneric<T> rd = nDes; 
    	int i, posMdna;
    	posMdna = (k <= this.orden/2) ? this.orden/2 : this.orden/2+1;	

    	nDes = new BNodeGeneric<T>(this.orden);		
    	for(i = posMdna; i < this.orden-1; i++) {
    		nDes.keys.set(i-posMdna,current.keys.get(i));
    		nDes.childs.set(i-posMdna+1,current.childs.get(i+1));
    	}

    	nDes.count = (this.orden - 1) - posMdna; 
        current.count = posMdna;

        if(k <= this.orden/2)   
        	putNode(current,cl,rd,k);
        else		
        	putNode(nDes,cl,rd,k-posMdna);

        T median = current.keys.get(current.count-1);    
        nDes.childs.set(0,current.childs.get(current.count)); 
        current.count--;
        return median;
    }

    public String toString(){
    	String s = "";
        if(isEmpty())
            s += "BTree is empty...";
        else
            s = writeTree(this.root);
        return s;
    }

    
    private String writeTree(BNodeGeneric<T> current){
    	int i;
    	String s = "";
    	if(current != null) {
       		s += current.toString()+"\n";
    		for(i = 0; i <= current.count; i++)
    			s += writeTree(current.childs.get(i));
    	}
    	return s;
    }
}