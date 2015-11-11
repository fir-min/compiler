package edu.towson.cis.cosc455.fsaint1.project1.implementation;


/**
*
* @author Firmin Saint-Amour
* 
*
*/
public class LinkS {
   
  
   private String[] text = new String[2]; // name & value
   private LinkS next; // next node
   private LinkS last; // previous node
   
   /**
   *@param String[name, value] name of var, and value of var
   *@param Node, next node
   *@param Node, previous node
   * 
   */
   public LinkS(String[] text, LinkS next, LinkS last) {
       
       this.text = text;
       this.next = next;
       this.last = last;
       
   }
   
   /**
    * sets the next node
    *@param void
    *@return void
    * 
    */
   public void setNext(LinkS next) {
       this.next = next;
   }
   
   
   /**
    * sets the previous node
    *@param void
    *@return void
    * 
    */
   public void setLast(LinkS last) {
       this.last = last;
   }
   
   /**
    * gets the next node
    *@param void
    *@return the next node
    * 
    */
   public LinkS getNext() {
       return this.next;
   }
   
   /**
    * gets the previous node
    *@param void
    *@return the last node
    * 
    */
   public LinkS getLast() {
       return this.last;
   }
   
   
   /**
    * gets the value of the node
    *@param void
    *@return the text in the node
    * 
    */
   public String[] getText() {
       return this.text;
   }
   
}

