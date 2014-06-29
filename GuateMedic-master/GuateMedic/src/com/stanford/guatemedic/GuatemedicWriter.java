package com.stanford.guatemedic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import android.content.Context;

public class GuatemedicWriter {
	
	private Context mContext;
	
	public GuatemedicWriter(Context c) {
		mContext = c;
	}
	
	private String generateFilenameSuffix() {
		return UUID.randomUUID().toString();
	}
	
	private boolean saveData(String filename_prefix, String filename_suffix, String data) {
		Writer writer = null;
		try {			
			String filename = filename_prefix + filename_suffix;
			OutputStream out = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null) {
				try {
					writer.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} 
		}
		return false;
	}
	
	public boolean saveDownloads(String data) {
		return saveData(GuatemedicFileConstants.download_prefix, generateFilenameSuffix(), data);
	}
	
	public boolean saveNewFamily(String data) {
		return saveData(GuatemedicFileConstants.newFamily_prefix, generateFilenameSuffix(), data);
	}
	
	public boolean saveNewChild(String data) {
		return saveData(GuatemedicFileConstants.newChild_prefix, generateFilenameSuffix(), data);
	}
	
	public boolean saveNewFamilyVisit(String data) {
		return saveData(GuatemedicFileConstants.newFamilyVisit_prefix, generateFilenameSuffix(), data);
	}
	
	public boolean saveNewChildVisit(String data) {
		return saveData(GuatemedicFileConstants.newChildVisit_prefix, generateFilenameSuffix(), data);
	}
	
	public boolean saveFamilyModification(String data) {
		return saveData(GuatemedicFileConstants.newFamilyModification_prefix, generateFilenameSuffix(), data);
	}
	
	public boolean saveChildModification(String data) {
		return saveData(GuatemedicFileConstants.newChildModification_prefix, generateFilenameSuffix(), data);
	}

}
