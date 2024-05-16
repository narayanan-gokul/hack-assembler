package com;
import com.Token;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

	private Scanner scanner;
	ArrayList<Token> tokens;
	private HashMap<String, Integer> symbolTable;

	public Parser(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		StringBuilder newFilePathBuilder = new StringBuilder(filePath);
		int sbLength = newFilePathBuilder.length();
		newFilePathBuilder.delete(sbLength - 3, sbLength);
		newFilePathBuilder.append("hack");
		String newFilePath = newFilePathBuilder.toString();
		this.scanner = new Scanner(file);
		this.tokens = new ArrayList<Token>();
		this.symbolTable = new HashMap<String, Integer>();
		symbolTable.put("SP", 0);
		symbolTable.put("LCL", 1);
		symbolTable.put("ARG", 2);
		symbolTable.put("THIS", 3);
		symbolTable.put("THAT", 4);
		symbolTable.put("R0", 0);
		symbolTable.put("R1", 1);
		symbolTable.put("R2", 2);
		symbolTable.put("R3", 3);
		symbolTable.put("R4", 4);
		symbolTable.put("R5", 5);
		symbolTable.put("R6", 6);
		symbolTable.put("R7", 7);
		symbolTable.put("R8", 8);
		symbolTable.put("R9", 9);
		symbolTable.put("R10", 10);
		symbolTable.put("R11", 11);
		symbolTable.put("R12", 12);
		symbolTable.put("R13", 13);
		symbolTable.put("R14", 14);
		symbolTable.put("R15", 15);
		symbolTable.put("SCREEN", 16384);
		symbolTable.put("KB", 24576);
		while(scanner.hasNext()) {
			tokenify(scanner.nextLine());
		}
		this.processSymbols();
	}

	private void processSymbols() {
		int address = 16;
		for(Token token: this.tokens) {
			String instruction = token.instruction;
			if(instruction.charAt(0) == '@') {
				StringBuilder sb = new StringBuilder(instruction);
				String symbol = sb.substring(1, sb.length());
				if(this.symbolTable.containsKey(symbol)) {
					sb.replace(1, sb.length(), "" + symbolTable.get(symbol) + "");
				} else if(!isNumeric(symbol)) {
					System.out.println(symbol + " " + address);
					symbolTable.put(symbol, address);
					sb.replace(1, sb.length(), "" + address + "");
					++address;
				}
				token.instruction = sb.toString();
			}
		}
	}

	private boolean isNumeric(String str) {
		if(str == null) return false;
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	public ArrayList<Token> tokens() {
		return this.tokens;
	}

	public void tokenify(String token) {
		StringBuilder sb = new StringBuilder(token);
		if(sb.length() != 0) {
			// Trimming leading white spaces.
			int spaces = 0;
			while(sb.charAt(spaces) == ' ') ++spaces;
			sb.delete(0, spaces);

			// Ignoring comment strings.
			int i = 0;
			int lenString = sb.length();
			while(i < lenString - 1 && sb.charAt(i) != '/' && sb.charAt(i + 1) != '/') {
				++i;
			}
			if(i == lenString - 1) ++i;
			sb.delete(i, lenString);

			if(sb.length() != 0) {
				// Checking for step labels
				if(sb.charAt(0) == '(') {
					int end = 1;
					for(int j = 1; sb.charAt(j) != ')'; ++j) { end = j; }
					String label = sb.substring(1, end + 1);
					symbolTable.put(label, this.tokens.size());
				} else {
					this.tokens.add(new Token(sb.toString()));
				}
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		String filePath = args[0];
		Parser parser = new Parser(filePath);
		for(Token token: parser.tokens()) {
			System.out.println(token);
		}
		System.out.println(1 >> 2);
	}
}
