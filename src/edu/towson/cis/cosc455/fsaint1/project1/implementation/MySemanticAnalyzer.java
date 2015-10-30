package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import java.util.Stack;

/**
 * This will act as the translator
 * It will convert the mkd components into their respective HTML versions
 *
 */

public class MySemanticAnalyzer {
	public Queue prgQ = new Queue(); // Queue for the program, everything will be translated into it
	public Stack varStack = new Stack(); // Stack for the variables
	
	
	public void mkdBegin() {
		prgQ.add("<html>\n");
	}
	
	public void mkdEnd() {
		prgQ.add("</html>");
		//createFile();
	}
	
	public void varCreate(String var, String value) {
		
	}
	
	public void varUse() {
		
	}
	
	public void audio(String text) {
		String audio = "<audio controls> \n" +
                       "<source src=" + text +">\n" +
                       "</audio>\n";
		
		prgQ.add(audio);
	}
}
