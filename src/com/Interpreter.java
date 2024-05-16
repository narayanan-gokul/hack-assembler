package com;
import com.Token;
import java.util.HashMap;

public class Interpreter {

	private HashMap<String, String> compMap;
	private HashMap<String, String> jumpMap;

	public Interpreter() {
		this.compMap = new HashMap<String, String>();
		compMap.put("0", "0101010");
		compMap.put("1", "0111111");
		compMap.put("-1", "0111010");
		compMap.put("D", "0001100");
		compMap.put("A", "0110000");
		compMap.put("M", "1110000");
		compMap.put("!D", "0001111");
		compMap.put("!A", "0110001");
		compMap.put("!M", "1110001");
		compMap.put("-D", "0001111");
		compMap.put("-A", "0110011");
		compMap.put("-M", "1110011");
		compMap.put("D+1", "0011111");
		compMap.put("A+1", "0110111");
		compMap.put("M+1", "1110111");
		compMap.put("D-1", "0001110");
		compMap.put("A-1", "0110010");
		compMap.put("M-1", "1110010");
		compMap.put("D+A", "0000010");
		compMap.put("D+M", "1000010");
		compMap.put("D-A", "0010011");
		compMap.put("D-M", "1010011");
		compMap.put("A-D", "0000111");
		compMap.put("M-D", "1000111");
		compMap.put("D&A", "0000000");
		compMap.put("D&M", "1000000");
		compMap.put("D|A", "0010101");
		compMap.put("D|M", "1010101");
		this.jumpMap = new HashMap<String, String>();
		jumpMap.put("", "000");
		jumpMap.put("JGT", "001");
		jumpMap.put("JEQ", "010");
		jumpMap.put("JGE", "011");
		jumpMap.put("JLT", "100");
		jumpMap.put("JNE", "101");
		jumpMap.put("JLE", "110");
		jumpMap.put("JMP", "111");
	}

	public String encode(Token token) {
		String instruction = token.instruction;
		if(instruction.charAt(0) == '@') {
			return this.encodeAInstruction(instruction);
		} else {
			return this.encodeCInstruction(instruction);
		}
	}

	private String encodeAInstruction(String instruction) {
		int address = Integer.parseInt(
			(new StringBuilder(instruction)).substring(1)
		);
		int len = 0;
		StringBuilder sb = new StringBuilder();
		int count = 0;
		while(count < 16) {
			sb.append(address & 1);
			address = address >> 1;
			++count;
		}
		return sb.reverse().toString();
	}

	public String encodeCInstruction(String instruction) {
		StringBuilder sb = new StringBuilder("111");
		StringBuilder dest = new StringBuilder();
		StringBuilder comp = new StringBuilder();
		StringBuilder jump = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < instruction.length(); ++i) {
			if(instruction.charAt(i) == '='){
				dest.append(temp.toString());
				temp = new StringBuilder();
			} else if(instruction.charAt(i) == ';') {
				comp.append(temp.toString());
				temp = new StringBuilder();
			} else if(instruction.charAt(i) != ' ') {
				temp.append(instruction.charAt(i));
			}
		}
		if(comp.length() == 0) comp.append(temp.toString());
		else jump.append(temp.toString());
		String destOpCode = this.dest(dest.toString());
		String compOpCode = this.comp(comp.toString());
		String jumpOpCode = this.jump(jump.toString());
		return sb.append(compOpCode + destOpCode + jumpOpCode).toString();
	}

	private String dest(String dest) {
		String dFlag = "0";
		String aFlag = "0";
		String mFlag = "0";
		for(int i = 0; i < dest.length(); ++i) {
			char curr = dest.charAt(i);
			if(curr == 'D') dFlag = "1";
			else if(curr == 'A') aFlag = "1";
			else if(curr == 'M') mFlag = "1";
		}
		StringBuilder result = new StringBuilder();
		result.append(aFlag + dFlag + mFlag);
		return result.toString();
	}

	private String comp(String comp) {
		return this.compMap.get(comp);
	}

	private String jump(String jump) {
		return this.jumpMap.get(jump);
	}

	public static void main(String[] args) {
		Interpreter inter = new Interpreter();
		String result = inter.encode(new Token("@256"));
		System.out.println(result);
	}
}
