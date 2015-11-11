package edu.towson.cis.cosc455.fsaint1.project1.implementation;

/**
*
* @author Firmin Saint-Amour
* 
* 
*/



import edu.towson.cis.cosc455.fsaint1.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {
	public String fileName; // name of output file
	public Stack varStack = new Stack(); // Stack for the variables
	public MyLexicalAnalyzer lex; // lexer
	public MySemanticAnalyzer sem = new MySemanticAnalyzer(this); // semenatics
	
	/**
	 * @param MyLexicalAnalyzer 
	 * @param String output file name
	 * 
	 */
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
	 * checks for a set of syntax errors at the beginning of each line
	 * @param void
	 * @return String
	 * @throws CompilerException
	 * 
	 */
	public void beginningCheck() throws CompilerException{
		if(lex.currentToken.length() < 1 || lex.currentToken.isEmpty() || lex.currentToken.equals("")) {
			return;
		}
		
		char c = lex.currentToken.charAt(0);
		if (c == Symbols.ADDE || c == Symbols.LINKE || c == Symbols.PARAE || c == Symbols.TITLEE) {
			throw new CompilerException("Syntax error at line # " + lex.lineNum + " & character # " + lex.currentPosition);
		}
	}
	
	/**
	 * 
	 * starts the syntax analyzer
	 * @param void
	 * @return void
	 * @throws CompilerException
	 * 
	 */
	public void start() throws CompilerException {
		while(lex.fileState) {
			
			lex.getNextToken();
			//System.out.println("CT: " + lex.currentToken);
			beginningCheck();
			// very first token #BEGIN
			
			if(lex.currentToken.length() < 1 || lex.currentToken.isEmpty() || lex.currentToken.equals("")) {
				
				continue;
			}
			
			else if(lex.currentToken.charAt(0) == Symbols.VAR) {
				System.out.println("Variable " + lex.currentToken +
						lex.currentToken.equalsIgnoreCase(Tokens.VARB));
				
				if(lex.currentToken.equalsIgnoreCase(Tokens.VARB)) {
					System.out.println("Variable def");
					variableDefine();
					
				}
				
				else if(lex.currentToken.equalsIgnoreCase(Tokens.VARU)) {
					System.out.println("Variable use");
					variableUse();
				}
				
				else {
					throw new CompilerException("Syntax error " +
							lex.currentToken + " at line " +
							lex.lineNum);
				}
			}
			
			else if(lex.currentToken.charAt(0) == Symbols.HASH) {
				if(lex.currentToken.equalsIgnoreCase(Tokens.DOCB)) {
					mkdBegin();
				}
				else {
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
	/**
	 * This method implements the BNF grammar rule for the document begin annotation.
	 * @throws CompilerException
	 */
	public void mkdBegin() throws CompilerException {
		
		
		if(lex.currentToken.equalsIgnoreCase(Tokens.DOCB)) {
			sem.mkdBegin();
		}
		
		else {
			throw new CompilerException("Syntax error expected" + Tokens.DOCB + " got " +
										lex.currentToken + " instead a line " +
										lex.lineNum);
		}
		
		//System.out.println("current Token: " + lex.currentToken);

	}
	/**
	 * This method implements the BNF grammar rule for the document end annotation.
	 * @throws CompilerException
	 */
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
	 * checks to see if the head is valid, uses the title method
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
			//System.out.println("The title " + title);
		} 
		else {
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
			
			title += (lex.currentToken + " ");
			lex.getNextToken();
		}
		
		System.out.println(title + "  this is the title");
		return title;

	}

	

	@Override
	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 */
	public void paragraph() throws CompilerException {
		int vars = 0;
		
		
		sem.addParaB();
		
		lex.getNextToken();
		
		
		while (!lex.currentToken.equals("" + (Symbols.PARAE))) {
			if(lex.currentToken.equals("" + (Symbols.PARAE))) {
				break;
			}
			
			if(lex.currentToken.equals("" + (Symbols.PARAB))) {
				throw new CompilerException("Syntax error expected " + Symbols.PARAE + " got " +
						lex.currentToken + " instead at line " +
						lex.lineNum);
			}
			
			else if(lex.currentToken.length() < 1 || lex.currentToken.isEmpty() || lex.currentToken.equals("")) {
				lex.getNextToken();
				continue;
			}
			
			
			// var
			else if(lex.currentToken.charAt(0) == Symbols.VAR) {
				if(lex.currentToken.equals(Tokens.VARU)) {
					variableUse();
				}
				
				if(lex.currentToken.equals(Tokens.VARB)) {
					vars++;
					variableDefine();
					
					
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
			
			lex.getNextToken();
		}
		
		
		
		for(int i = 0; i < vars; i++) {
			varStack.pop();
		}
		
		sem.addParaE();

	}

	@Override
	/**
	 * This method implements the BNF grammar rule for the innerText annotation.
	 * @throws CompilerException
	 */
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
		
		String varN = lex.currentToken; // name  of variable
		if(varStack.isIn(varN) >= 1) {
			String value = varStack.getValue2(varN); // value
			
			lex.getNextToken(); // should be the variable end token $end
			System.out.println(lex.currentToken + " var use");
			if(!lex.currentToken.equals(Tokens.VARE)) {
				throw new CompilerException("Syntax error, expected " + Tokens.VARE + " got " +
						lex.currentToken + " instead at line " +
						lex.lineNum);
			}
			
			else {
				sem.innerText(value + " ");
				
				
				
				
			}
			
			
		}
		else {
			throw new CompilerException("Semantic error variable '" + varN + "' is not defined at" +
							" line # " + lex.lineNum);
		}
		

	}
	
	
	/**
	 * This method implements the BNF grammar rule for the variable-use annotation. use within listItem
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
	public String variableUseList() throws CompilerException {
		lex.getNextToken(); // should be the variable name
		
		String varN = lex.currentToken; // name  of variable
		if(varStack.isIn(varN) >= 1) {
			String value = varStack.getValue2(varN); // value
			
			lex.getNextToken(); // should be the variable end token $end
			System.out.println(lex.currentToken + " var use");
			if(!lex.currentToken.equals(Tokens.VARE)) {
				throw new CompilerException("Syntax error, expected " + Tokens.VARE + " got " +
						lex.currentToken + " instead at line " +
						lex.lineNum);
			}
			
			else {
				return value;
				
				
				
				
			}
			
			
		}
		else {
			throw new CompilerException("Semantic error variable '" + varN + "' is not defined at" +
							" line # " + lex.lineNum);
		}
		

	}
	

	
	@Override
	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
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
	/**
	 * This method implements the BNF grammar rule for the italics annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
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
	/**
	 * This method implements the BNF grammar rule for the list item annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
	public void listItem() throws CompilerException {
		//lex.getNextToken();
		
		String b = " ";
		
		while (!lex.currentToken.equals("" + (Symbols.LISTE))) {
			
			
			lex.getNextToken(); 
			System.out.println(lex.currentToken + "  &&&  " );
			
			
			if(lex.currentToken.equals("" + (Symbols.LISTE))) {
				//System.out.println("break");
				break;
			}
			
			
			else if(lex.currentToken.length() < 1 || lex.currentToken.isEmpty() || lex.currentToken.equals("")) {
				
				continue;
			}
			
			
			
			else if(lex.currentToken.charAt(0) == Symbols.VAR) {
				
				if(lex.currentToken.equalsIgnoreCase(Tokens.VARB)) {
					throw new CompilerException("Syntax error " + lex.currentToken + 
							" at line " +
							lex.lineNum);
				}
				else {
					b += (variableUseList() + " ");
					
				}

			}
			else if(lex.currentToken.charAt(0) == Symbols.ADDE || 
					lex.currentToken.charAt(0) == Symbols.LINKE || 
					lex.currentToken.charAt(0) == Symbols.PARAE || 
					lex.currentToken.charAt(0) == Symbols.TITLEE ||
					lex.currentToken.charAt(0) == Symbols.LINKB || 
					lex.currentToken.charAt(0) == Symbols.PARAB ||
					lex.currentToken.charAt(0) == Symbols.ADDB || 
					lex.currentToken.charAt(0) == Symbols.TITLEB  )
				
				throw new CompilerException("Syntax error '" + 
						lex.currentToken + "' at line " +
						lex.lineNum);
			else {
				b += (lex.currentToken + " ");
				//System.out.println("dsgasghahafhfah rghadh   " + b + "   " +lex.currentToken);
			}
			
			//System.out.println(b + "   bbbb");
		}
		
		sem.listItem(b);

	}
	
	

	

	@Override
	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
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
		
		System.out.println(name + " link name **");
		
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
	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
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
	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
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
	/**
	 * This method implements the BNF grammar rule for the newline annotation.
	 * @param void
	 * @return void
	 * @throws CompilerException
	 */
	public void newline() throws CompilerException {
		// TODO Auto-generated method stub
		/*
		 * ~
		 * <br>
		 */
		
		sem.newline();

	}


}
