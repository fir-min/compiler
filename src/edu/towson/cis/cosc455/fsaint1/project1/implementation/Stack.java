package edu.towson.cis.cosc455.fsaint1.project1.implementation;



/**
 *
 * @author fsaint1
 */
public class Stack {
    
    private int count = 0;
    private LinkS top;
    private LinkS node;
    
    
    
    public void pop() {
        
        if(count == 0) {
            top = null;
            node = null;
            return;
        }
        
        LinkS t = node;
        int x = 0;
        while(x < count - 2) {
            t = t.getNext();
            
            x++;
            
        }
        
        LinkS z = node;
        int y = 0;
        while(y < count - 3) {
            z = z.getNext();
            
            y++;
            
        }
        
        z.setNext(new LinkS(t.getText(), null));
        
        
        top = z.getNext();
        
        //print();
        
        count--;
                
        
        
    }
    
    public void add(String[] add) {
        //System.out.println(count);
        if(count == 0) {
            node = new LinkS(add, null);
            top = node;
            
        }
        else {
            top.setNext(new LinkS(add, null));
            top = top.getNext();
            
        }
        
        count++;
        
    }
    
    
    
    public String[] getTop() {
        return top.getText();
    }
    
    
    public String[][] array() {
        int count = 1;
        
        LinkS n = node;
        
        LinkS z = node;
        
        while(n.getNext() != null) {
            n = n.getNext();
            count++;
        }
        
        String[][] r = new String[count][2];
        
        
        //n = sPath;
        
        int x = 0;
        while(x < count) {
            r[x] = z.getText();
            z = z.getNext();
            
            x++;
            
        }
        
        return r;
    }
    
    public int length() {
        return count;
    }
    
    
    public int VarIn(String x) {
         int athena = 0;
         LinkS t = node;
         
         if(t == null) {
             return athena;
         }
         
         if(t.getText()[0].equals(x)) {
             athena++;
             
         }
         else {
         
            while(t.getNext() != null) {
                t = t.getNext(); 
                
                if(t.getText()[0].equals(x)) {
                    athena++;
                    break;
                }
             
            }
         }
         
         return athena;
     }
   
}