package edu.towson.cis.cosc455.fsaint1.project1.implementation;

public class Symbols {

	public static final String BOLD = "**";
	
	public static final char HASH = '#';
	public static final char HEAD = '^';
	public static final char TITLEB = '<';
	public static final char TITLEE = '>';
	
	public static final char PARAB = '{';
	public static final char PARAE = '}';
	public static final char EQSIGN = '=';
	public static final char ITALIC = '*';
    
	public static final char NEWLINE = '~';
	public static final char VAR = '$';
	public static final char LINKB = '[';
	public static final char LINKE = ']';
	
	public static final char AUDIO = '@';
	public static final char VIDEO = '%';
	public static final char ADDB = '(';
	public static final char ADDE = ')';
	
	public static final char LISTB = '+';
	public static final char LISTE = ';';
	
	public static final char[] SYMBOLS = {HASH, HEAD, TITLEB, TITLEE, PARAB, PARAE,
			EQSIGN, ITALIC, NEWLINE, VAR, LINKB, LINKE, AUDIO, VIDEO, ADDB, ADDE, LISTB, LISTE};
	
	public static boolean search(char c) {
		boolean result = false;
		for(int i = 0; i < SYMBOLS.length; i++) {
			if(c == SYMBOLS[i]) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
}

