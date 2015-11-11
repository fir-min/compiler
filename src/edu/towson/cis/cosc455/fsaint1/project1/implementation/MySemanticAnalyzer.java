package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import java.awt.Desktop;
import java.io.*;

/**
 * This will act as the translator
 * It will convert the mkd components into their respective HTML versions
 *
 */

public class MySemanticAnalyzer {
	public Queue prgQ = new Queue(); // Queue for the program, everything will be translated into it
	
	
	public MySyntaxAnalyzer syn;
	
	public MySemanticAnalyzer(MySyntaxAnalyzer syn) {
		this.syn = syn;
	}
	
	
	/**
	 * simply adds <html> to the program queue
	 * @param void
	 * @return void
	 */
	public void mkdBegin() {
		prgQ.add("<html>\n");
		prgQ.add("<body>\n");
	}
	
	
	/**
	 * simply adds </html> to the program queue
	 * @param void
	 * @return void
	 */
	public void mkdEnd() {
		prgQ.add("</body>\n");
		prgQ.add("</html>");
		createFile();
	}
	
	public void createFile() {
		//System.out.println("booyakasha");
		String fileName = syn.fileName.concat(".html");
		String[] out = new String[prgQ.count];
		Writer writer = null;
		
		out = prgQ.toArray();

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(fileName), "utf-8"));
		    for(String x: out) {
		    	writer.write(x);
		    }
		    
		    openHTMLFileInBrowswer(fileName);
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {/*ignore*/}
		}
	}
	
	void openHTMLFileInBrowswer(String htmlFileStr){
		//System.out.println("oh yeahhhh");
		File file= new File(htmlFileStr.trim());
		if(!file.exists()){
			System.err.println("File "+ htmlFileStr +" does not exist.");
			return;
		}
		try{
			Desktop.getDesktop().browse(file.toURI());
		}
		catch(IOException ioe){
			System.err.println("Failed to open file");
			ioe.printStackTrace();
		}
		return ;
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
			head = "<head> <title> " + s + " </title> </head>\n";
			System.out.println("semantic " + head);
			prgQ.add(head);
		}
		else {
			head = "<head> " + s + " </head>\n";
			prgQ.add(head);
		}
		
		
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
	 * @param String text -> the link for the video file
	 * @return void
	 */
	public void video(String text) {
		String video = "<iframe src=" + text + "/>\n";
		
		prgQ.add(video);
	}
	
	
	/**
	 * simply adds the <br>  tag to the program queue
	 * @param void
	 * @return void
	 */
	public void newline() {
		String n = "<br> \n";
		prgQ.add(n);
				
	}
	
	/**
	 * creates the link and adds it to the queue
	 * @param String l => name of the link
	 * @param String value => actual link it self 
	 * @return void
	 */
	public void link(String l, String value) {
		String link = "<a href=" + value + ">" + l + " </a>\n";
		prgQ.add(link);
	}
	
	
	/**
	 * adds bolded text
	 * @param String s => text to be bolded
	 * @return void
	 */
	public void bold(String s) {
		String b = "<b> " + s + "</b>\n";
		prgQ.add(b);
	}
	
	/**
	 * adds italics text
	 * @param String s => text to be in italics
	 * @return void
	 */
	public void italics(String s) {
		String b = "<i> " + s + "</i>\n";
		prgQ.add(b);
	}
	
	
	
	/**
	 * adds list items
	 * @param String s => list item
	 * @return void
	 */
	public void listItem(String s) {
		String l = "<li> " + s + " </li>\n";
		prgQ.add(l);
	}
	
	public void innerText(String s) {
		prgQ.add(s);
	}
	
	public void addParaB() {
		prgQ.add("<p>\n");
	}
	
	public void addParaE() {
		prgQ.add("</p>\n");
	}
	
	
}
