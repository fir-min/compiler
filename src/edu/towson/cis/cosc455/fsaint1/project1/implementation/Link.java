package edu.towson.cis.cosc455.fsaint1.project1.implementation;

/**
*
* @author fsaint1
*/
public class Link {
   
  
   private String text;
   private Link next;
   
   public Link(String text, Link next) {
       
       this.text = text;
       this.next = next;
       
   }
   
   public void setNext(Link next) {
       this.next = next;
   }
   
   public Link getNext() {
       return this.next;
   }
   
   public String getText() {
       return this.text;
   }
   
}

