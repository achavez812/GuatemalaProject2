package com.stanford.guatemedic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;


public class GuatemedicFileReader {
	
	private Context mContext;
	
	public GuatemedicFileReader(Context c) {
		mContext = c;
	}
	
	private ArrayList<File> getFiles(String dir_name) {
		ArrayList<File> theFiles = new ArrayList<File>();
		File dir = mContext.getFilesDir();
		for (File f : dir.listFiles()) {
			if (f.getName().startsWith(dir_name)) 
				theFiles.add(f);
		}
		return theFiles;
	}
	
	public ArrayList<File> getDownloadedFiles() {
		return getFiles(GuatemedicFileConstants.download_prefix);
	}
	
	public ArrayList<File> getNewFamilyFiles() {
		return getFiles(GuatemedicFileConstants.newFamily_prefix);
	}
	
	public ArrayList<File> getNewChildFiles() {
		return getFiles(GuatemedicFileConstants.newChild_prefix);
	}
	
	public ArrayList<File> getNewFamilyVisitFiles() {
		return getFiles(GuatemedicFileConstants.newFamilyVisit_prefix);
	}
	
	public ArrayList<File> getNewChildVisitFiles() {
		return getFiles(GuatemedicFileConstants.newChildVisit_prefix);
	}
	
	public ArrayList<File> getNewFamilyModificationFiles() {
		return getFiles(GuatemedicFileConstants.newFamilyModification_prefix);
	}
	
	public ArrayList<File> getNewChildModificationFiles() {
		return getFiles(GuatemedicFileConstants.newChildModification_prefix);
	}
	
	public String getStringData(File f) {
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(f.getName());
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			return jsonString.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try { 
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public boolean isUploadNeeded() {
		if (!getNewChildVisitFiles().isEmpty()) return true;
		else if (!getNewFamilyVisitFiles().isEmpty()) return true;
		else if (!getNewChildFiles().isEmpty()) return true;
		else if (!getNewFamilyFiles().isEmpty()) return true;
		else if (!getNewChildModificationFiles().isEmpty()) return true;
		else if (!getNewFamilyModificationFiles().isEmpty()) return true;
		else return false;
	}
	
	public boolean hasDownloadedData() {
		return !getDownloadedFiles().isEmpty();
	}

}
