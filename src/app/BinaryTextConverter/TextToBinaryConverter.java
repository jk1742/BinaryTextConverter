package app.BinaryTextConverter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;

public class TextToBinaryConverter extends CommandConverterHandler {
	public static void main(String[] args) throws IOException {
		TextToBinaryConverter converter = new TextToBinaryConverter();
		if (args.length < 2) {
			System.out.println("textToBinary [text file][aplication name][--type [\"utf-8 | hex | ansi | latin\"]] ");
			System.out.println("please insert proper arguments");
			System.out.println("    obligataries: [text file] [aplication name]");
			System.out.println("    options: [--type \"utf-8 | hex | ansi | latin\"]");
			System.out.println();
			return;
		}
		String 	textFileName 	= args[0];
		String 	appFileName 	= args[1];
		Path 	sourcePath 		= Paths.get(textFileName);
		Path 	destinationPath = Paths.get(appFileName);
		// check source text file
		File f = new File(sourcePath.toString());
		if(!f.exists()) { 
			System.out.println("The file with the specified name does not exist.");
			System.out.println("    First argument must be file name with location. [text file name]");
			return;
		} else { f = null; }
		//
		// characterType process
		String 	characterType 	= "utf-8"; 
		Charset charset 		= null;
		try {
			String[] strArray = converter.pickArguments("--type", 1, args,"--");
			if(strArray != null)characterType = strArray[0];
		} catch (IllegalArgumentException e1) {
			if("IllegalArgument".equals(e1.getMessage())) System.out.println("\"--type\" Input Arguments error. Please, Check your arguements");
		}
		//
		// read text file
		BufferedOutputStream 	bos 		= null;
		FileOutputStream 		fos 		= null;
		byte[] 					byteData 	= null;
		String 					stringData 	= null;
		try {
			File file = new File(sourcePath.toString());
			charset = converter.recogCharsets(characterType);
			//
			// read text file
		    @SuppressWarnings("resource")
		    Scanner myReader = new Scanner(file, charset);
		    while (myReader.hasNextLine()) {
		    	stringData = myReader.nextLine();
		    }
		    //
		    // convert string to byte
			if("HEX".equals(characterType.toUpperCase())) {
				byteData = converter.convertHexStringToBytes(stringData);
			} else {
				byteData = Base64.getDecoder().decode(stringData);				
			}
		    // create FileOutputStream from filename
			fos = new FileOutputStream(destinationPath.toString());
			// create BufferedOutputStream for FileOutputStream
			bos = new BufferedOutputStream(fos);
			// write file
			bos.write(byteData);
			bos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		String strSize = String.valueOf((byteData.length/1024));
		strSize = strSize.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
		System.out.println();
		System.out.println("textToBinary ");
		System.out.println("    source      : " + sourcePath);
		System.out.println("    destination : " + destinationPath);
		System.out.println("    charset     : " + charset);
		System.out.println("    length      : " + strSize +"kb");
		System.out.println("    status      : Generating a binary file completed");
		System.out.println("    author      : jk1742@synthesis-intelect.net");
		System.out.println("    version     : 0.0.1");
		System.out.println();
	}
}
