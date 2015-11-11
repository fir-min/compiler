package edu.towson.cis.cosc455.fsaint1.project1.implementation;


/**
 *
 * @author fsaint1
 */
public class Stack {
    
    private int count = 0; // number of nodes
    private LinkS top; // top of
    private LinkS node; // very first node
    
    
    /**
     * gets the top of stack
     *@param void
     *@return the top item of the stack
     * 
     */
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
        
        z.setNext(new LinkS(t.getText(), null, z));
        
        
        top = z.getNext();
        
        //print();
        
        count--;
                
        
        
    }
    

    /**
     *adds to the stack
     *@param item to be added
     *@return void
     * 
     */
    public void add(String[] add) {
        //System.out.println(count);
        if(count == 0) {
            node = new LinkS(add, null, null);
            top = node;
            
        }
        else {
            top.setNext(new LinkS(add, null, top));
            top = top.getNext();
            
        }
        
        count++;
        
    }
    
    

    /**
     * returns top of the stack doesn't actually remove it
     *@param void
     *@return the top item of the stack
     * 
     */
    public String[] getTop() {
        return top.getText();
    }
    

    /**
     * converts the stack to an array
     *@param void
     *@return array representation of stack
     * 
     */
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
    

    /**
     * 
     *@param void
     *@return the number of entries in stack
     * 
     */
    public int length() {
        return count;
    }
    

    /**
     * searches for a specific item (searches from 1st element to last element)
     *@param String to search for
     *@return the value for that string
     * 
     */
    public String getValue(String x) {
    	String r = "";
    	
        LinkS t = node;
        
        if(t.getText()[0].equals(x)) {
            r = t.getText()[1];
            
           
        }
        else {
        
           while(t.getNext() != null) {
               t = t.getNext(); 
               
               if(t.getText()[0].equals(x)) {
            	   r = t.getText()[1];
                   
                   break;
               }
            
           }
        }
        
        return r;
        
        
        
    }
    
    /**
     * searches for a specific item (searches from last element to 1st element)
     *@param String to search for
     *@return the value for that string
     * 
     */
    public String getValue2(String x) {
    	String r = "";
    	
        LinkS t = top;
        
        if(t.getText()[0].equals(x)) {
            r = t.getText()[1];
            
           
        }
        else {
        
           while(t.getLast() != null) {
               t = t.getLast(); 
               
               if(t.getText()[0].equals(x)) {
            	   r = t.getText()[1];
                   
                   break;
               }
            
           }
        }
        
        return r;
    }
    
    /**
     * check if an item is in the stack
     *@param String to search for
     *@return the number of times that item is in the stack
     * 
     */
    public int isIn(String x) {
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