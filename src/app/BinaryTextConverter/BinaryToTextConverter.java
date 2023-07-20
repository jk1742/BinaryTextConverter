package app.BinaryTextConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


public class BinaryToTextConverter extends CommandConverterHandler{
	
	public static void main(String[] args) throws IOException {
		
		BinaryToTextConverter converter = new BinaryToTextConverter();
		//
		// input argument check
		if (args.length < 1) {
			System.out.println();
			System.out.println("binaryToText [binary file name][--type [\"utf-8 | hex | ansi | latin\"]] ");
			System.out.println("Please, Insert proper arguments");
			System.out.println("    First argument must be file name with location. [binary file name]");
			System.out.println("    options: [--type \"utf-8 | hex | ansi | latin\"]");
			System.out.println();
			return;
		}
		String appFileName = args[0];
		Path sourcePath = Paths.get(appFileName);
		//
		// Check file name
		File f = new File(sourcePath.toString());
		if(!f.exists()) { 
			System.out.println("The file with the specified name does not exist.");
			System.out.println("    First argument must be file name with location. [binary file name]");
			return;
		} else { f = null; }
		//
		// pick Arguments
		String characterType = "utf-8"; 
		Charset charset = null;
		try {
			String[] strArray = converter.pickArguments("--type", 1, args,"--");
			if(strArray != null)characterType = strArray[0];
		} catch (IllegalArgumentException e1) {
			if("IllegalArgument".equals(e1.getMessage())) System.out.println("\"--type\" Input Arguments error. Please, Check your arguements");
		}
		//
		// set result text file
		String fileName = sourcePath.getFileName().toString().split("\\.")[0];
		Path destinationPath = Paths.get(sourcePath.getParent().toString(), fileName + ".txt");
		//
		// read source binary file
		File originalFile = new File(sourcePath.toString());
		byte[] bytes = null;
		try {
			@SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
			bytes = new byte[(int) originalFile.length()];
			fileInputStreamReader.read(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String contents="";
		if("HEX".equals(characterType.toUpperCase())) {
			contents	= converter.convertByteToHexString(bytes);
		} else {
			charset 	= converter.recogCharsets(characterType);
			contents 	= new String(Base64.getEncoder().encode(bytes), charset);
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath.toString()));
		    writer.write(contents);
		    writer.close();		    
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		//
		// report
		//	calculate file size
		String strSize = String.valueOf((contents.length()/1024));
		strSize = strSize.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
		System.out.println();
		System.out.println("binaryToText ");
		System.out.println("    source      : " + sourcePath);
		System.out.println("    destination : " + destinationPath);
		System.out.println("    charset     : " + charset);
		System.out.println("    length      : " + strSize +"kb");
		System.out.println("    status      : Generating a encoded-text completed");
		System.out.println("    author      : jk1742@synthesis-intelect.net");
		System.out.println("    version     : 0.0.1");
		System.out.println();
	}
	
	BinaryToTextConverter(){
		
	}
	
}
