package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import java.util.Stack;

/**
 * This will act as the translator
 * It will convert the mkd components into their respective HTML versions
 *
 */

public class MySemanticAnalyzer {
	public Queue prgQ = new Queue(); // Queue for the program, everything will be translated into it
	
	
	
	/**
	 * simply adds <html> to the program queue
	 * @param void
	 * @return void
	 */
	public void mkdBegin() {
		prgQ.add("<html>\n");
	}
	
	
	/**
	 * simply adds </html> to the program queue
	 * @param void
	 * @return void
	 */
	public void mkdEnd() {
		prgQ.add("</html>");
		//createFile();
	}
	
	
	/**
	 * 
	 * @param String s = > string to go within head / title
	 * @param boolean t => wheather or not there is a title
	 * @return void
	 * 
	 * 
	 */
	public void head(String s, boolean t) {
		String head;
		
		if(t) {
			head = "<head> <title> " + s + " </title> </head>";
		}
		else {
			head = "<head> " + s + " </head>";
		}
		
		prgQ.add(head);
	}
	
	/**
	 * simply adds the audio tag to the program queue
	 * @param String text => the link for the audio file
	 * @return void
	 */
	public void audio(String text) {
		String audio = "<audio controls> \n" +
                       "<source src=" + text +">\n" +
                       "</audio>\n";
		
		prgQ.add(audio);
	}
	
	/**
	 * simply adds the video tag to the program queue
	 * @param String text => the link for the video file
	 * @return void
	 */
	public void video(String text) {
		String video = "<iframe src=" + text + "/>";
		
		prgQ.add(video);
	}
	
	
	/**
	 * simply adds the <br>  tag to the program queue
	 * @param void
	 * @return void
	 */
	public void newline() {
		String n = "<br> ";
		prgQ.add(n);
				
	}
	
	/**
	 * creates the link and adds it to the queue
	 * @param String l => name of the link
	 * @param String value => actual link it self 
	 * @return void
	 */
	public void link(String l, String value) {
		String link = "<a href=" + value + ">" + l + " </a>";
		prgQ.add(link);
	}
	
	
	/**
	 * adds bolded text
	 * @param String s => text to be bolded
	 * @return void
	 */
	public void bold(String s) {
		String b = "<b> " + s + "</b>";
		prgQ.add(b);
	}
	
	/**
	 * adds italics text
	 * @param String s => text to be in italics
	 * @return void
	 */
	public void italics(String s) {
		String b = "<i> " + s + "</i>";
		prgQ.add(b);
	}
	
	/**
	 * adds a paragraph
	 * @param String s => what will be in the paragraph
	 * @return void
	 */
	public void paragraph(String s) {
		String para = "<p>" + s + "</p>";
		prgQ.add(para);
	}
	
	/**
	 * adds list items
	 * @param String s => list item
	 * @return void
	 */
	public void listItem(String s) {
		String l = "<li>" + s + "</li>";
		prgQ.add(l);
	}
	
	
}
