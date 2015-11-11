package edu.towson.cis.cosc455.fsaint1.project1.implementation;


/**
*
* @author fsaint1
*/
public class LinkS {
   
  
   private String[] text = new String[2];
   private LinkS next;
   private LinkS last;
   
   public LinkS(String[] text, LinkS next, LinkS last) {
       
       this.text = text;
       this.next = next;
       this.last = last;
       
   }
   
   public void setNext(LinkS next) {
       this.next = next;
   }
   
   public void setLast(LinkS last) {
       this.last = last;
   }
   
   public LinkS getNext() {
       return this.next;
   }
   
   public LinkS getLast() {
       return this.last;
   }
   
   public String[] getText() {
       return this.text;
   }
   
}

