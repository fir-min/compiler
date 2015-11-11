package edu.towson.cis.cosc455.fsaint1.project1.implementation;

/**
*
* @author Firmin Saint-Amour
* 
*/
public class Link {
   
  
   private String text;
   private Link next;
   
   /**
    *@param text to be added
    *@param next link
    * 
    */
   public Link(String text, Link next) {
       
       this.text = text;
       this.next = next;
       
   }
   /**
    * sets the next node
    *@param void
    *@return void
    * 
    */
   public void setNext(Link next) {
       this.next = next;
   }
   
   /**
    * gets the next node
    *@param void
    *@return the next node
    * 
    */
   public Link getNext() {
       return this.next;
   }
   
   /**
    * gets the value of the node
    *@param void
    *@return the text in the node
    * 
    */
   public String getText() {
       return this.text;
   }
   
}

