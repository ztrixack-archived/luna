package com.ztrixack.utils;

import android.annotation.SuppressLint;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Converter {
	/**
	 * Parse hex string to integer string.
	 * 
	 * @param sarray
	 *            Hex string.
	 * @return integer string.
	 */
	public static String parseHexStringToIntString(String sarray) {
		String[] separated = sarray.split("\\s+");
		String s = "";
		for (String ss : separated)
			s += (Integer.toString(Integer.parseInt(ss, 16)) + " ");
		return s;
	}

	/**
	 * Parse hex string to byte array.
	 * 
	 * @param sarray
	 *            Hex string.
	 * @return byte array.
	 */
	public static byte[] parseHexStringToByteArray(String sarray) {
		List<String> separated = new ArrayList<String>();
		int index = 0;
		while (index < sarray.length()) {
			separated.add(sarray.substring(index,
					Math.min(index + 2, sarray.length())));
			index += 2;
		}
		byte[] b = new byte[separated.size()];
		for (int i = 0; i < separated.size(); ++i) {
			try {
				b[i] = (byte) Integer.parseInt(separated.get(i), 16);
			} catch (NumberFormatException e) {
				b[i] = (byte) 0xFF;
			}
		}
		return b;
	}

	/**
	 * Parse string to byte array, can chooses
	 * 
	 * @param sarray
	 *            Hex string.
	 * @return byte array.
	 */
	public static byte[] parseStringToByteArray(String sarray, int radix) {
		List<String> separated = new ArrayList<String>();
		int index = 0;
		int width = (int) Math.ceil((Math.log10(0xFF) / Math.log10(radix)));
		while (index < sarray.length()) {
			String s = sarray.substring(index,
					Math.min(index + width, sarray.length()));
			if (Integer.parseInt(s, radix) > 255) {
				sarray = sarray.substring(0, index) + "0"
						+ sarray.substring(index, sarray.length());
				s = sarray.substring(index,
						Math.min(index + width, sarray.length()));
			}
			separated.add(s);
			index += width;
		}
		byte[] b = new byte[separated.size()];
		for (int i = 0; i < separated.size(); ++i) {
			try {
				b[i] = (byte) Integer.parseInt(separated.get(i), radix);
			} catch (NumberFormatException e) {
				b[i] = (byte) 0xFF;
			}
		}
		return b;
	}

	/**
	 * Parse byte array to hex string.
	 * 
	 * @param barray
	 *            byte array.
	 * @return hex string.
	 */
	public static String parseByteArrayToHexString(byte[] barray) {
		StringBuilder hexString = new StringBuilder("");
		try {
			Preconditions.checkNotNull(barray);
			for (byte b : barray) {
				hexString.append(String.format("%02x", b & 0xff));
			}
			return hexString.toString();
		} catch (NullPointerException ignore) {
			return "";
		}

	}

	/**
	 * Parse byte array to string by radix.
	 * 
	 * @param barray
	 *            byte array
	 * @param radix
	 *            base number.
	 * @return default string.
	 */
	public static String parseByteArrayToString(byte[] barray, int radix) {
		StringBuilder string = new StringBuilder("");
		String temp;
		int width = (int) Math.ceil((Math.log10(0xFF) / Math.log10(radix)));
		for (byte b : barray) {
			temp = Integer.toString((int) b & 0xFF, radix);
			while (temp.length() % width != 0) {
				temp = "0" + temp;
			}
			string.append(temp);
		}

		return string.toString();
	}

	/**
	 * Parse byte array to ASCII string.
	 * 
	 * @param barray
	 *            byte array.
	 * @return ASCII.
	 */
	public static String parseByteArrayToASCIIString(byte[] barray) {
		StringBuilder strBuilder = new StringBuilder();
		for (byte aBarray : barray) {
			// if (aBarray < 0)
			// throw new IllegalArgumentException();
			strBuilder.append((char) aBarray);
		}
		return strBuilder.toString();
	}

	/**
	 * Parse byte array to ASCII string.
	 * 
	 * @param barray
	 *            byte array.
	 * @param length
	 * @return ASCII.
	 */
	public static String parseByteArrayToASCIIString(byte[] barray, int length) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < length; ++i) {
			if (i == barray.length) {
				break;
			}
			strBuilder.append((char) barray[i]);
		}
		return strBuilder.toString();
	}

	/**
	 * Parse ASCII string to byte array.
	 * 
	 * @param ASCII
	 *            ascii string.
	 * @return byte array.
	 */
	public static byte[] parseASCIIStringToByteArray(String ascii) {
		int length = ascii.length();
		byte[] b = new byte[length];
		for (int i = 0; i < length; ++i) {
			b[i] = (byte) ascii.charAt(i);
		}
		return b;
	}

	/**
	 * Parse integer to byte array.
	 * 
	 * @param intad
	 *            integer.
	 * @return byte array.
	 */
	public static byte[] parseIntToByteArray(int intad) {
		ByteBuffer byte_buffer_addr = ByteBuffer.allocate(4);
		byte_buffer_addr.putInt(intad);
		return byte_buffer_addr.array();
	}

	/**
	 * Parse byte to binary string.
	 * 
	 * @param bytead
	 *            byte.
	 * @return binary string.
	 */
	public static String parseByteToBinaryString(byte bytead) {
		String str = Integer.toBinaryString((int) bytead & 0xFF);
		while (str.length() < 8) {
			str = '0' + str;
		}
		return str;
	}

	/**
	 * Parse byte array to integer.
	 * 
	 * @param barray
	 *            byte array.
	 * @return integer.
	 */
	public static int parseByteArrayToInt(byte[] barray) {
		int value = 0;
		for (int i = 0; i < barray.length; i++) {
			int shift = (barray.length - 1 - i) * 8;
			value += (barray[i] & 0xFF) << shift;
		}
		return value;
	}

	/**
	 * Parse decimal string to hex string.
	 * 
	 * @param arg
	 *            decimal string.
	 * @return hex string.
	 */
	public static String toHex(String arg) {
		return String.format("%X ",
				new BigInteger(arg.getBytes(/* YOUR_CHARSET? */)));
	}

	/**
	 * Parse integer to minimum byte array.
	 * 
	 * @param intad
	 *            integer.
	 * @return byte array.
	 */
	public static byte[] parseFixIntToByteArray(int intad) {
		String HexStr = Integer.toHexString(intad);
		// noinspection UnnecessaryLocalVariable
		return parseHexStringToByteArray(HexStr);
	}

	/**
	 * Parse long integer to minimum byte array.
	 * 
	 * @param intad
	 *            long integer.
	 * @return byte array.
	 */
	public static byte[] parseFixLongToByteArray(long longad) {
		String HexStr = Long.toHexString(longad);
		return parseHexStringToByteArray(HexStr);
	}

	/**
	 * Parse integer to byte array. It is can limit array.
	 * 
	 * @param intad
	 *            integer.
	 * @param i
	 *            byte array limitation.
	 * @return byte array.
	 */
	public static byte[] parseFixIntToByteArray(int intad, int i) {
		String HexStr = Integer.toHexString(intad);
		while (HexStr.length() < i * 2) {
			HexStr = "0" + HexStr;
		}
		if (HexStr.length() > i * 2) {
			HexStr = HexStr.substring(HexStr.length() - (i * 2));
		}
		return parseHexStringToByteArray(HexStr);
	}

	/**
	 * Converts a byte[2] to a short, in LITTLE_ENDIAN format
	 */
	public static short parse2BytesToShort(byte argB1, byte argB2) {
		return (short) (argB1 | (argB2 << 8));
	}

	/**
	 * Parse byte to hex string.
	 * 
	 * @param bytead
	 *            byte.
	 * @return Hex string.
	 */
	@SuppressLint("DefaultLocale")
	public static String parseByteToHexString(byte bytead) {
		StringBuilder hexString = new StringBuilder("");
		hexString.append(String.format("%02x", bytead & 0xff));
		return hexString.toString().toUpperCase();
	}
}
