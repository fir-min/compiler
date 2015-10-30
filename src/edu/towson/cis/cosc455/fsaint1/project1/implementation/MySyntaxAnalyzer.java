package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import edu.towson.cis.cosc455.fsaint1.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {
	MyLexicalAnalyzer lex;
	MySemanticAnalyzer sem = new MySemanticAnalyzer();
	public MySyntaxAnalyzer(MyLexicalAnalyzer lex) {
		this.lex = lex;
	}
	
	public void start() throws CompilerException {
		while(lex.fileState) {
			lex.getNextToken();
			
			// very first token #BEGIN
			if(lex.lineNum == 1) {
				mkdBegin();
			}
			
			// #END the end of the file
			if(!lex.fileState) {
				mkdEnd();
			}
			
			// HEAD
			if(lex.currentToken.charAt(0) == Symbols.HEAD) {
				head();
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
		String head = "";
		
		head += lex.currentToken; // head = "^"
		
		lex.getNextToken();
		
		
		
		/**
		 * ^ < some text > ^
		 */

	}

	@Override
	public void title() throws CompilerException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
	public void listitem() throws CompilerException {
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
