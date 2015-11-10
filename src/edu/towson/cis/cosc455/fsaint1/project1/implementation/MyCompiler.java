package edu.towson.cis.cosc455.fsaint1.project1.implementation;



// lexical checks only for valid tokens

public class MyCompiler {
	public static String mkdFile = "C:\\Users\\FIR_MIN\\Desktop\\cosc 455\\Project 1 Test Cases\\Project 1 Test Cases\\Test3.mkd";
	/*
	public static void main(String args[]) {
		if(args.length == 0) {
			System.out.println("Please provide file name");
			exit();
		}
		
		else {
			mkdFile = args[0];
		}
		
		MyLexicalAnalyzer lex = new MyLexicalAnalyzer(mkdFile);
		MySyntaxAnalyzer syn = new MySyntaxAnalyzer(lex, mkdFile.substring(0, mkdFile.length()- 5));
		
		try {
			syn.start();
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exit();
		}
		
	}
	*/
	public static void main(String args[]) {
		
		MyLexicalAnalyzer lex = new MyLexicalAnalyzer(mkdFile);
		MySyntaxAnalyzer syn = new MySyntaxAnalyzer(lex, mkdFile.substring(0, mkdFile.length()- 5));
		
		try {
			syn.start();
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exit();
		}
		
	}
	
	public static void exit() {
		System.exit(0);
	}

}
