package org.hock_bot.util;

/**
 * 
 * Color codes for important logs via UNIX terminals
 * 
 * Reference: http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
 */
public interface TerminalColorCodes {
	
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	
	/*Reference: http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println */
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	/* my combos */
	public static final String BLACK_ON_WHITE = ANSI_WHITE_BACKGROUND + ANSI_BLACK;
	public static final String RED_ON_WHITE = ANSI_WHITE_BACKGROUND + ANSI_RED;
	public static final String RESET_ALL = ANSI_RESET+ANSI_BLACK_BACKGROUND;

}
