package edu.towson.cis.cosc455.fsaint1.project1.implementation;


/**
*
* @author fsaint1
*/
public class LinkS {
   
  
   private String[] text = new String[2];
   private LinkS next;
   
   public LinkS(String[] text, LinkS next) {
       
       this.text = text;
       this.next = next;
       
   }
   
   public void setNext(LinkS next) {
       this.next = next;
   }
   
   public LinkS getNext() {
       return this.next;
   }
   
   public String[] getText() {
       return this.text;
   }
   
}

