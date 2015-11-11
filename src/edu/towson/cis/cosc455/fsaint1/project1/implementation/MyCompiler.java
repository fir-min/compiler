package edu.towson.cis.cosc455.fsaint1.project1.implementation;

/**
*
* @author Firmin Saint-Amour
* 
* 
*/
public class MyCompiler {
	public static String mkdFile; // "Project 1 Test Cases\\Test2.mkd";
	public static final String fileExt = ".mkd";
	
	/**
	 *
	 * @param name of mkd file
	 * @return void
	 */
	public static void main(String args[]) {
		if(args.length == 0) {
			System.out.println("Please provide file name");
			exit();
		}
		
		else {
			mkdFile = args[0];
			//System.out.println(mkdFile);
		}
		
		if(mkdFile.substring(mkdFile.length()- 4).equals(fileExt)) {
			MyLexicalAnalyzer lex = new MyLexicalAnalyzer(mkdFile);
			MySyntaxAnalyzer syn = new MySyntaxAnalyzer(lex, mkdFile.substring(0, mkdFile.length()- 4));
			
			try {
				syn.start();
			} catch (CompilerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exit();
			}
		}
		else {
			System.out.println("Please make sure to use a .mkd file");
			exit();
		}
		
	}
	
	
	/**
	 * exits the program
	 * @param void
	 * @return void
	 */
	public static void exit() {
		System.exit(0);
	}

}
