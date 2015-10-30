package edu.towson.cis.cosc455.fsaint1.project1.implementation;

public class Tokens {

	public static final String DOCB = "#BEGIN";
	public static final String DOCE = "#END";
	public static final String VARB = "$DEF";
	public static final String VARE = "$END";
	public static final String VARU = "$USE";
	
	public static final String[] TOKENS = {DOCB, DOCE, VARB, VARE, VARU};
	
	public static boolean search(String s) {
		boolean result = false;
		for(int i = 0; i < TOKENS.length; i++) {
			if(TOKENS[i].equalsIgnoreCase(s)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
}
