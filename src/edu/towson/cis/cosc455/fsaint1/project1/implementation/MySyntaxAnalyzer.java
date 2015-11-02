package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import java.util.Stack;

import edu.towson.cis.cosc455.fsaint1.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {
	@SuppressWarnings("rawtypes")
	public Stack varStack = new Stack(); // Stack for the variables
	MyLexicalAnalyzer lex;
	MySemanticAnalyzer sem = new MySemanticAnalyzer();
	public MySyntaxAnalyzer(MyLexicalAnalyzer lex) {
		this.lex = lex;
	}
	
	public void exit() {
		System.exit(0);
	}
	
	
	
	public void start() throws CompilerException {
		while(lex.fileState) {
			
			lex.getNextToken();
			// very first token #BEGIN
			if(lex.currentToken.charAt(0) == Symbols.HASH) {
				mkdBegin();
			}
			
			// #END the end of the file
			if(!lex.fileState) {
				if(lex.currentToken.charAt(0) == Symbols.HASH) {
					mkdEnd();
				}
			}
			
			// HEAD
			if(lex.currentToken.charAt(0) == Symbols.HEAD) {
				head();
			}
			
			
			// italics & italics
			if(lex.currentToken.charAt(0) == Symbols.ITALIC) {
				if(lex.currentToken.equals(Symbols.BOLD)) {
					bold();
				}
				else {
					italics();
				}
			}
			
			
			// use and define variables
			if(lex.currentToken.charAt(0) == Symbols.VAR) {
				if(lex.currentToken.equalsIgnoreCase(Tokens.VARB)) {
					variableDefine();
				}
				
				if(lex.currentToken.equalsIgnoreCase(Tokens.VARU)) {
					variableUse();
				}
			}
			
			// list item
			if(lex.currentToken.charAt(0) == Symbols.LISTB) {
				listItem();
			}
			
			// audio
			if(lex.currentToken.charAt(0) == Symbols.AUDIO) {
				audio();
			}
			
			// video
			if(lex.currentToken.charAt(0) == Symbols.VIDEO) {
				video();
			}
			
			// paragraph
			if(lex.currentToken.charAt(0) == Symbols.PARAB) {
				paragraph();
			}
			
			// newline
			if(lex.currentToken.charAt(0) == Symbols.NEWLINE) {
				newline();
			}
			
			// link
			if(lex.currentToken.charAt(0) == Symbols.LINKB) {
				link();
			}
			
			// new line \n
			if(lex.currentToken.equals("\n")) {
				sem.newline();
			}
			
			
		}
	}
	
	@Override
	public void mkdBegin() throws CompilerException {
		
		
		if(lex.currentToken.equalsIgnoreCase(Tokens.DOCB)) {
			sem.mkdBegin();
		}
		
		else {
			throw new CompilerException("Expected " + Tokens.DOCB + " got " +
										lex.currentToken + " instead a line " +
										lex.lineNum);
		}

	}
	
	public void mkdEnd() throws CompilerException {
		if(lex.currentToken.equalsIgnoreCase(Tokens.DOCE)) {
			sem.mkdEnd();
		}
		
		else {
			throw new CompilerException("Expected " + Tokens.DOCE + " got " +
										lex.currentToken + " instead a line " +
										lex.lineNum);
		}

	}
	/**
	 * @param void
	 * @return void
	 * checks to see if the head is valid uses the title method
	 */
	@Override
	public void head() throws CompilerException {
		int lineNum = lex.lineNum;
		boolean t = false;
		String head = "";
		String title = "";
		
		
		lex.getNextToken();
		
		if(lex.currentToken.charAt(0) == Symbols.TITLEB) {
			t = true;
			title = title();
		}
		
		if(lex.currentToken.charAt(0) != Symbols.HEAD) {
			lex.getNextToken();
			
			while(lex.currentToken.charAt(0) != Symbols.HEAD) {
				if(lex.lineNum != lineNum) {
					System.out.println("Syntax error at " + lex.lineNum);
					exit();
				}
				
				head += (lex.currentToken + " ");
				lex.getNextToken();
			}
		}
		
		/*
		 * true for title
		 * false for no title
		 */
		
		if(t) {
			sem.head(title, true);
		}
		if(!t) {
			sem.head(head, false);
		}
		
		
		
		/**
		 * ^ < some text > ^
		 */

	}
	
	
	/**
	 * @param void
	 * @return String
	 * checks to see if the title is valid 
	 */
	@Override
	public String title() throws CompilerException {
		String title = "";
		lex.getNextToken();
		
		while(lex.currentToken.charAt(0) != Symbols.TITLEE) {
			title += (lex.currentToken + " ");
			lex.getNextToken();
		}
		
		return title;

	}

	@Override
	public void body() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void paragraph() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void innerText() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void variableDefine() throws CompilerException {
		
		/*
		 * varName => name of variable
		 * varValue => value
		 * needs to use stack to keep track of variables
		 * 
		 */
		String varName;
		String varValue;
		
		lex.getNextToken();
		
		varName = lex.currentToken;
		
		lex.getNextToken();
		
		if(lex.currentToken.charAt(0) != Symbols.EQSIGN) {
			throw new CompilerException("Expected " + Symbols.EQSIGN + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		else {
			lex.getNextToken();
			
			varValue = lex.currentToken;
		}
		
		//sem.addVar(varName, varValue, d)
	}

	@Override
	public void variableUse() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bold() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void italics() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void listItem() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void innerItem() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void link() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void audio() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void video() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void newline() throws CompilerException {
		// TODO Auto-generated method stub

	}


}
