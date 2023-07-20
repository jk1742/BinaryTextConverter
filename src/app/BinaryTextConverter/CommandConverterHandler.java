package app.BinaryTextConverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CommandConverterHandler {

	/**
	 * pickArguments
	 * return arguments string array
	 * @param param
	 * @param argsNumber
	 * @param args
	 * @param indexCharacter
	 * @return
	 * @throws Exception 
	 */
	protected static String[] pickArguments(String param, int argsNumber, String[] args, String indexCharacter) throws IllegalArgumentException {
		boolean sw = false;
		int pos = 0;
		String[] carriage = new String[argsNumber];
		// find argument location
		for (int i = 0; i < args.length; i++) {
			String entry = args[i];
			if(param.equals(entry)){
				pos = i;
				sw = true;
				break;
			}
		}
		// copy result
		System.arraycopy (args, pos+1, carriage, 0, argsNumber);
		for (int i = 0; i < carriage.length; i++) {
			String entry = carriage[i];
			if(entry.startsWith(indexCharacter)) {
				sw = false;
				throw new IllegalArgumentException("IllegalArgument");
			}
			if(entry.length() <= 0) sw = false;
		}
		if(sw) return carriage;
		else return null;
	}
	
	/**
	 * pickArguments
	 * return arguments string array
	 * @param param
	 * @param argsNumber
	 * @param args
	 * @param indexCharacter
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private static String[] pickArguments(String param, int argsNumber, String[] args) throws IllegalArgumentException {
		boolean sw = false;
		int pos = 0;
		String[] carriage = new String[argsNumber];
		// find argument location
		for (int i = 0; i < args.length; i++) {
			String entry = args[i];
			if(param.equals(entry)){
				pos = i;
				sw = true;
				break;
			}
		}
		// copy result
		System.arraycopy (args, pos+1, carriage, 0, argsNumber);
		for (int i = 0; i < carriage.length; i++) {
			String entry = carriage[i];
			if(entry.length() <= 0) sw = false;
		}
		if(sw) return carriage;
		else return null;
	}
	
	/**
	 * recogCharsets
	 * @param characterType
	 * @return
	 */
	protected static Charset recogCharsets(String characterType) {
		if(characterType != null) {
			characterType = characterType.toUpperCase();
		}
		Charset charsets =  null;
		switch (characterType) {
			case "LATIN":
				charsets = StandardCharsets.ISO_8859_1;
				break;
			case "ISO_8859_1":
				charsets = StandardCharsets.ISO_8859_1;
				break;
			case "ANSI":
				charsets = StandardCharsets.US_ASCII;
				break;
			case "US_ASCII":
				charsets = StandardCharsets.US_ASCII;
				break;
			default:
				charsets =  StandardCharsets.UTF_8;
				break;
		}
		return charsets;
	}
	
	/**
	 * convertByteToHexString
	 * @param bytes
	 * @return
	 */
	protected static String convertByteToHexString(byte[] bytes) {
		StringBuilder fileString = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String code = Integer.toHexString(bytes[i]);
			// calculate negative
			if(code.length()>2) {
				int negative = bytes[i] & 0xff;
				code = Integer.toHexString(negative);
			}
			if(2 > code.length()) fileString.append("0");
			fileString.append(code);
		}
		return fileString.toString();
	}
	
	/**
	 * convertHexStringToBinaryFile
	 * @param source
	 * @return
	 */
	protected static byte[] convertHexStringToBytes(String source) {
		String hexStore[] = splitText(2, source);
		byte[] bytes = new byte[hexStore.length];
		for (int i = 0; i < hexStore.length; i++) {
        	int seq = Integer.parseInt(hexStore[i],16);
        	bytes[i] = (byte) seq;
        }
		return bytes;
	}
	
	/**
	 * splitText
	 * @param seperate
	 * @param codex
	 * @return
	 */
	protected static String[] splitText(int seperate,String codex) {
    	String[] carriage = new String[codex.length()/seperate];
    	for (int i = 0; i < carriage.length; i++) {
    		carriage[i] = codex.substring( (seperate * i), (seperate * i + seperate) );
    	}
    	return carriage;
    }
}
