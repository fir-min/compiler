package edu.towson.cis.cosc455.fsaint1.project1.implementation;



import edu.towson.cis.cosc455.fsaint1.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {
	public String fileName;
	public Stack varStack = new Stack(); // Stack for the variables
	public MyLexicalAnalyzer lex;
	public MySemanticAnalyzer sem = new MySemanticAnalyzer();
	
	
	public MySyntaxAnalyzer(MyLexicalAnalyzer lex, String fileName) {
		this.lex = lex;
		this.fileName = fileName;
	}
	
	/**
	 * 
	 * exits the program
	 * @param void
	 * @return void
	 * @throws void
	 * 
	 */
	public void exit() {
		System.exit(0);
	}
	

	/**
	 * 
	 * starts the syntax analyzer
	 * @param void
	 * @return String
	 * @throws CompilerException
	 * 
	 */
	public void start() throws CompilerException {
		while(lex.fileState) {
			
			lex.getNextToken();
			// very first token #BEGIN
			if(lex.currentToken.charAt(0) == Symbols.HASH) {
				mkdBegin();
			}
			
			// #END the end of the file
			else if(!lex.fileState) {
				if(lex.currentToken.charAt(0) == Symbols.HASH) {
					mkdEnd();
				}
			}
			
			// HEAD
			else if(lex.currentToken.charAt(0) == Symbols.HEAD) {
				head();
			}
			
			
			// italics & italics
			else if(lex.currentToken.charAt(0) == Symbols.ITALIC) {
				if(lex.currentToken.equals(Symbols.BOLD)) {
					bold();
				}
				else {
					italics();
				}
			}
			
			
			// use and define variables
			else if(lex.currentToken.charAt(0) == Symbols.VAR) {
				if(lex.currentToken.equalsIgnoreCase(Tokens.VARB)) {
					variableDefine();
				}
				
				else if(lex.currentToken.equalsIgnoreCase(Tokens.VARU)) {
					variableUse();
				}
				
				else {
					throw new CompilerException("Expected " + Tokens.VARU + " got " +
							lex.currentToken + " instead at line " +
							lex.lineNum);
				}
			}
			
			// list item
			else if(lex.currentToken.charAt(0) == Symbols.LISTB) {
				listItem();
			}
			
			// audio
			else if(lex.currentToken.charAt(0) == Symbols.AUDIO) {
				audio();
			}
			
			// video
			else if(lex.currentToken.charAt(0) == Symbols.VIDEO) {
				video();
			}
			
			// paragraph
			else if(lex.currentToken.charAt(0) == Symbols.PARAB) {
				paragraph();
			}
			
			// newline
			else if(lex.currentToken.charAt(0) == Symbols.NEWLINE) {
				newline();
			}
			
			// link
			else if(lex.currentToken.charAt(0) == Symbols.LINKB) {
				link();
			}
			
			// new line \n
			else if(lex.currentToken.equals("\n")) {
				sem.newline();
			}
			
			
			// just plain text
			else {
				innerText();
			}
			
		}
	}
	
	@Override
	public void mkdBegin() throws CompilerException {
		
		
		if(lex.currentToken.equalsIgnoreCase(Tokens.DOCB)) {
			sem.mkdBegin();
		}
		
		else {
			throw new CompilerException("Syntax error expected" + Tokens.DOCB + " got " +
										lex.currentToken + " instead a line " +
										lex.lineNum);
		}

	}
	
	public void mkdEnd() throws CompilerException {
		if(lex.currentToken.equalsIgnoreCase(Tokens.DOCE)) {
			sem.mkdEnd();
		}
		
		else {
			throw new CompilerException("Syntax error expected " + Tokens.DOCE + " got " +
										lex.currentToken + " instead a line " +
										lex.lineNum);
		}

	}
	
	@Override
	/**
	 * checks to see if the head is valid uses the title method
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
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
				
				head.concat(lex.currentToken).concat(" ");
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
	
	@Override
	/**
	 * checks to see if the title is valid 
	 * @param void
	 * @return String
	 * @throws CompilerException
	 * 
	 */
	public String title() throws CompilerException {
		String title = "";
		lex.getNextToken();
		
		
		
		while(lex.currentToken.charAt(0) != Symbols.TITLEE) {
			title.concat(lex.currentToken).concat(" ");
			lex.getNextToken();
		}
		
		return title;

	}

	@Override
	//skip
	public void body() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void paragraph() throws CompilerException {
		int vars = 0;
		
		
		sem.addParaB();
		
		lex.getNextToken();
		
		
		while (!lex.currentToken.equals(Symbols.PARAE)) {
			if(lex.currentToken.equals(Symbols.PARAB)) {
				throw new CompilerException("Syntax error expected " + Symbols.PARAE + " got " +
						lex.currentToken + " instead at line " +
						lex.lineNum);
			}
			
			// var
			else if(lex.currentToken.charAt(0) == Symbols.VAR) {
				if(lex.currentToken.equals(Tokens.VARU)) {
					variableUse();
				}
				
				if(lex.currentToken.equals(Tokens.VARB)) {
					variableDefine();
					
					vars++;
				}
				
				
			}
			
			// new line
			else if (lex.currentToken.equals(Symbols.NEWLINE)) {
				newline();
			}
			
			// italic / bold
			else if(lex.currentToken.charAt(0) == Symbols.ITALIC) {
				if(lex.currentToken.equals(Symbols.BOLD)) {
					bold();
				}
				else {
					italics();
				}
			}
			
			// list
			else if(lex.currentToken.charAt(0) == Symbols.LISTB) {
				listItem();
			}
			
			// audio
			else if(lex.currentToken.charAt(0) == Symbols.AUDIO) {
				audio();
			}
			
			// video
			else if(lex.currentToken.charAt(0) == Symbols.VIDEO) {
				video();
			}
			
			
			
			// link
			else if(lex.currentToken.charAt(0) == Symbols.LINKB) {
				link();
			}
			
			// new line \n
			else if(lex.currentToken.equals("\n")) {
				sem.newline();
			}
			
			
			// just plain text
			else {
				innerText();
			}
		}
		
		
		
		for(int i = 0; i < vars; i++) {
			varStack.pop();
		}
		
		sem.addParaE();

	}

	@Override
	//skip
	public void innerText() throws CompilerException {
		sem.innerText(lex.currentToken + " ");
	 }
	
	@Override
	/**
	 * This method implements the BNF grammar rule for the variable-define annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
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
		
		lex.getNextToken(); // equal sign
		
		if(lex.currentToken.charAt(0) != Symbols.EQSIGN) {
			throw new CompilerException("Syntax error expected " + Symbols.EQSIGN + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		else {
			lex.getNextToken(); // value of variable
			
			
			varValue = lex.currentToken;
			String[] s = {varName, varValue};
			varStack.add(s);
		}
		
		lex.getNextToken(); // end of variable define $end
		
		if(!lex.currentToken.equalsIgnoreCase(Tokens.VARE)) {
			throw new CompilerException("Expected " + Tokens.VARE + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		
		
		//sem.addVar(varName, varValue, d)
	}

	@Override
	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
	public void variableUse() throws CompilerException {
		lex.getNextToken(); // should be the variable name
		
		String varN = lex.currentToken;
		
		String value = varStack.getValue(varN);
		
		lex.getNextToken(); // should be the variable end token $end
		
		sem.innerText(varN);

	}
	
	@Override
	public void bold() throws CompilerException {
        String b = "";
        
        lex.getNextToken();
        
        b.concat(lex.currentToken).concat(" ");
        
        while(!lex.currentToken.equals(Symbols.BOLD)) {
        	lex.getNextToken();
        	
        	if(!lex.currentToken.equals(Symbols.BOLD)) {
        		b.concat(lex.currentToken).concat(" ");
        	}
        	
        	
        }
        
        sem.bold(b);
        
        
	}

	@Override
	public void italics() throws CompilerException {
        String i = "";
        
        lex.getNextToken();
        
        i.concat(lex.currentToken).concat(" ");
        
        while(!lex.currentToken.equals(Symbols.ITALIC)) {
        	lex.getNextToken();
        	
        	if(!lex.currentToken.equals(Symbols.ITALIC)) {
        		i.concat(lex.currentToken).concat(" ");
        	}
        	
        	
        }
        
        sem.italics(i);
        

	}

	@Override
	public void listItem() throws CompilerException {
		lex.getNextToken();
		
		String b = lex.currentToken;
		
		while (!lex.currentToken.equals(Symbols.LISTE)) {
			lex.getNextToken(); 
			
			if(lex.currentToken.charAt(0) != Symbols.LISTE && lex.currentToken.charAt(0) != Symbols.VAR) {
				b.concat(lex.currentToken).concat(" ");
			}
			
			else if(lex.currentToken.charAt(0) == Symbols.VAR) {
				lex.getNextToken();
				b.concat(lex.currentToken).concat(" ");
				lex.getNextToken();
			}
		}
		
		sem.listItem(b);

	}

	@Override
	//skip
	public void innerItem() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void link() throws CompilerException {
		String name = "";
		String link = "";
		int linkE = 0;
		int nameE = 0;
		
		String l = lex.currentToken;
		
		for(int i = 1; i < l.length(); i++) {
			if(l.charAt(i) == Symbols.LINKE) {
				break;
			}
			else {
				nameE ++;
			}
		}
		
		name = l.substring(1, nameE);
		
		if(l.charAt(nameE+1) != Symbols.ADDB) {
			throw new CompilerException("Syntax error expected " + Symbols.ADDB + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		
		for(int e = nameE + 1; e < l.length(); e++) {
			if(l.charAt(e) == Symbols.LINKE) {
				break;
			}
			else {
				linkE ++;
			}
		}
		
		link = l.substring(nameE + 1, linkE);
		
		sem.link(name, link);
	}

	@Override
	public void audio() throws CompilerException {
		
		
		String t = lex.currentToken;
		
		if(t.charAt(1) != Symbols.ADDB) { // checks for (
			throw new CompilerException("Expected " + Symbols.ADDB + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		
		else if(t.charAt(t.length() - 1 ) != Symbols.ADDE) { // checks for )
			throw new CompilerException("Expected " + Symbols.ADDE + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		
		else  {
			String audio = t.substring(2, t.length() - 1 );
			sem.audio(audio);
		}

	}

	@Override
	public void video() throws CompilerException {
        String t = lex.currentToken;
		
		if(t.charAt(1) != Symbols.ADDB) { // checks for (
			throw new CompilerException("Expected " + Symbols.ADDB + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		
		else if(t.charAt(t.length() - 1 ) != Symbols.ADDE) { // checks for )
			throw new CompilerException("Expected " + Symbols.ADDE + " got " +
					lex.currentToken + " instead at line " +
					lex.lineNum);
		}
		
		else  {
			String video = t.substring(2, t.length() - 1 );
			sem.audio(video);
		}

	}

	@Override
	public void newline() throws CompilerException {
		// TODO Auto-generated method stub
		/*
		 * ~
		 * <br>
		 */
		
		sem.newline();

	}


}
