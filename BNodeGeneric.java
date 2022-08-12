package laboratorio8;
import java.util.ArrayList;

public class BNodeGeneric <T>{

    protected ArrayList<T>keys;
    protected ArrayList<BNodeGeneric<T>>childs;
    protected int count;

    public BNodeGeneric(int nCount){
    	this.keys = new ArrayList<T>(nCount);
    	this.childs = new ArrayList<BNodeGeneric<T>>(nCount);
    	this.count = 0;
    	for(int i=0; i<nCount; i++){
            this.keys.add(null);
            this.childs.add(null);
        }
    }
    public boolean nodeFull(int nEle){
    	return this.count == nEle;
    }
    public boolean nodeEmpty(int nEle){
    	return this.count < (nEle/2);
    }
    public String toString(){
        String s="(";
        for(int i=0; i<this.count; i++){
        	s+=this.keys.get(i);
        	if (i<this.count-1)
        		s+=", ";
        	else
        		s+=") ";
        }
        return s;
    }
 }