import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import com.Parser;
import com.Interpreter;
import com.Token;

public class Assembler {
	public static void main(String[] args) throws IOException {
		String filePath = args[0];
		Parser parser = new Parser(filePath);
		Interpreter interpreter = new Interpreter();
		ArrayList<Token> tokens = parser.tokens();
		String newFilePath = createNewFile(filePath);
		FileWriter writer = new FileWriter(newFilePath);
		int i = 0;
		for(Token token: tokens) {
			//if(i == 15) break;
			String binaryInstruction = interpreter.encode(token);
			//System.out.println(token.instruction);
			//System.out.println(binaryInstruction);
			writer.write(binaryInstruction + '\n');
			++i;
		}
		writer.close();
	}

	private static String createNewFile(String filePath) throws IOException {
		StringBuilder newFilePathBuilder = new StringBuilder(filePath);
		int sbLength = newFilePathBuilder.length();
		newFilePathBuilder.delete(sbLength - 3, sbLength);
		newFilePathBuilder.append("hack");
		String newFilePath = newFilePathBuilder.toString();
		File newFile = new File(newFilePath);
		newFile.createNewFile();
		return newFilePath;
	}
}
