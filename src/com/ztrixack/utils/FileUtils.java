package com.ztrixack.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.annotation.SuppressLint;

/**
 * File Utility
 * 
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * 
 *         Copyright (C) 2013 Silicon Craft Technology Co.,Ltd. Licensed under
 *         the GNU Lesser General Public License (LGPL)
 *         http://www.gnu.org/licenses/lgpl.html
 */
@SuppressLint("DefaultLocale")
public class FileUtils {

	private static final String DEBUG_TAG = FileUtils.class.getName();

	/**
	 * Check file is exist and not empty, is not?
	 * 
	 * @param fullFileName
	 *            is a path of file
	 * @return true, if it is exist and not empty.
	 */
	public static boolean checkIfFileExistAndNotEmpty(String fullFileName) {
		if (fullFileName != null) {
			File f = new File(fullFileName);
			long lengthInBytes = f.length();
			ZLog.i(DEBUG_TAG, fullFileName + " length in bytes: "
					+ lengthInBytes);
			return lengthInBytes > 100;
		} else {
			return false;
		}
	}

	/**
	 * Check folder is exist, is not?
	 * 
	 * @param fullFolderName
	 *            is a path of folder
	 * @return true, if it is exist.
	 */
	public static boolean checkIfFolderExists(String fullFolderName) {
		File f = new File(fullFolderName);
		return f.exists() && f.isDirectory();
	}

	/**
	 * Delete folder.
	 * 
	 * @param fullFileName
	 *            is a path of file
	 * @return true, if can delete.
	 */
	public static boolean deleteFolder(String fullFolderName) {
		return deleteDir(new File(fullFolderName));
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	/**
	 * Create folder.
	 * 
	 * @param fullFolderName
	 *            is a path of folder
	 * @return true, if can create.
	 */
	public static boolean createFolder(String fullFolderName) {
		File f = new File(fullFolderName);
		return f.mkdirs();
	}

	/**
	 * Delete file.
	 * 
	 * @param fullFileName
	 *            is a path of file
	 * @return true, if can delete.
	 */
	public static void deleteFile(String fullFileName) {
		File f = new File(fullFileName);
		boolean isdeleted = f.delete();
		ZLog.i(DEBUG_TAG, "deleteing: " + fullFileName + " isdeleted: "
				+ isdeleted);
	}

	/**
	 * Create file.
	 * 
	 * @param fullFileName
	 *            is a path of file
	 * @return true, if can create.
	 */
	public static void createFile(String fullFileName) {
		File f = new File(fullFileName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			ZLog.e(DEBUG_TAG, e.getMessage());
		}
	}

	/**
	 * Get size of file.
	 * 
	 * @param fullFileName
	 *            is a path of file
	 * @return length in bytes.
	 */
	public static long getFileSizeInBytes(String fullFileName) {
		File f = new File(fullFileName);
		long lengthInBytes = f.length();
		ZLog.i(DEBUG_TAG, "fullFileName length in bytes: " + lengthInBytes);
		return lengthInBytes;

	}

	/**
	 * Get valid FFMPGEG file name.
	 * 
	 * @param path
	 *            is a path of file
	 * @return valid name.
	 */
	public static String getValidFFMpgegFileNameFromPath(String path) {
		int startIndex = path.lastIndexOf("/") + 1;
		int endIndex = path.lastIndexOf("");

		String name = path.substring(startIndex, endIndex) + "_sl_"
				+ System.currentTimeMillis();
		String ext = path.substring(endIndex + 1);
		ZLog.i(DEBUG_TAG, "name: " + name + " ext: " + ext);
		String validName = (name.replaceAll("\\Q.\\E", "_")).replaceAll(" ",
				"_");
		return validName + "" + ext;
	}

	/**
	 * Get valid file name.
	 * 
	 * @param path
	 *            is a path of file
	 * @return valid name.
	 */
	public static String getValidFileNameFromPath(String path) {
		int startIndex = path.lastIndexOf("/") + 1;
		int endIndex = path.lastIndexOf("");

		String name = path.substring(startIndex, endIndex);
		String ext = path.substring(endIndex + 1);
		ZLog.i(DEBUG_TAG, "name: " + name + " ext: " + ext);
		String validName = (name.replaceAll("\\Q.\\E", "_")).replaceAll(" ",
				"_");
		return validName + "" + ext;
	}

	/**
	 * Get upset folder from file path.
	 * 
	 * @param filePath
	 *            is a path of file.
	 * @return folder path.
	 */
	public static String getWorkingFolderFromFilePath(String filePath) {
		int index = filePath.lastIndexOf("/");
		return filePath.substring(0, index + 1);
	}

	/**
	 * Get file name from file path.
	 * 
	 * @param filePath
	 *            is a path of file.
	 * @return file name.
	 */
	public static String getFileNameFromFilePath(String filePath) {
		int index = filePath.lastIndexOf("/");
		return filePath.substring(index + 1, filePath.length());
	}

	/**
	 * Copy file into another folder path.
	 * 
	 * @param filePath
	 *            file is copied.
	 * @param folderPath
	 *            folder is pasted.
	 * @return File path is pasted.
	 */
	public static String copyFileToFolder(String filePath, String folderPath) {
		ZLog.i(DEBUG_TAG, "Coping file: " + filePath + " to: " + folderPath);
		String validFilePathStr = filePath;

		try {
			FileInputStream is = new FileInputStream(filePath);
			BufferedOutputStream o = null;
			String validFileName = getValidFileNameFromPath(filePath);
			validFilePathStr = folderPath + validFileName;
			File destFile = new File(validFilePathStr);
			try {
				byte[] buff = new byte[10000];
				int read;
				o = new BufferedOutputStream(new FileOutputStream(destFile),
						10000);
				while ((read = is.read(buff)) > -1) {
					o.write(buff, 0, read);
				}
			} finally {
				is.close();
				if (o != null)
					o.close();

			}
		} catch (Exception e) {
			ZLog.e(DEBUG_TAG, e.getMessage());
		}
		return validFilePathStr;
	}

	/**
	 * Get valid file name to into another folder path.
	 * 
	 * @param filePath
	 *            A path of file is copied.
	 * @param folderPath
	 *            folder is pasted.
	 * @return Valid file path is pasted.
	 */
	public static String getCopyValidFilePath(String filePath, String folderPath) {
		String validFileName = getValidFileNameFromPath(filePath);
		return folderPath + validFileName;
	}

	/**
	 * Append file.
	 * 
	 * @param filePath
	 *            is a path of file.
	 * @param line
	 *            line that want to append.
	 */
	public static void appendToFile(String filePath, String line) {
		File f2 = new File(filePath);
		String LINE_SEPARATOR = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append(LINE_SEPARATOR).append(line);

		try {
			// open writer in append mode (if false will overwrite the file)
			FileWriter writer = new FileWriter(f2, true);

			writer.append(sb.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			ZLog.e(DEBUG_TAG,
					"Failed to write to : " + filePath + " " + e.getMessage());
		}
	}

	/**
	 * Check file path is Contain Spaces, is not?
	 * 
	 * @param fileName
	 *            is a path of file.
	 * @return true, if context file have spaces.
	 */
	public static boolean isFileContainSpaces(String fileName) {
		return fileName.contains(" ");

	}

	/**
	 * Check file is exist and not empty, is not? And if file is not empty, it
	 * return size of file.
	 * 
	 * @param fullFileName
	 *            is a path of file
	 * @return length in bytes.
	 */
	public static long checkIfFileExistAndNotEmptyReturnSize(String fullFileName) {
		File f = new File(fullFileName);
		long lengthInBytes = f.length();
		ZLog.i(DEBUG_TAG, fullFileName + " length in bytes: " + lengthInBytes);
		return lengthInBytes;

	}

}