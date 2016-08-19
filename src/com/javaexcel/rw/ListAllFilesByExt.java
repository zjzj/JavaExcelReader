package com.javaexcel.rw;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class ListAllFilesByExt {
	private List<String> getfiles = new ArrayList<String>();
	
	class FilenameFilterImpl2 implements FilenameFilter {
		private String ext;
		public FilenameFilterImpl2(String ext) {
			this.ext = ext;
		}

		@Override
		public boolean accept(File dir, String name) {

			//若文件为目录，返回true
			if (new File(dir,name).isDirectory())
				return true;

			//若文件拓展名为ext，返回true
			return name.toLowerCase().endsWith(ext);
		}
	}
	
	private void print(File dir, FilenameFilter filter) {
		//根据过滤器，列出所有文件
		File[] list = dir.listFiles(filter);
		
		for (File file : list) {

			if(file.isDirectory()){
				print(file, filter);
			}
			else{
				//System.out.println(file.getAbsolutePath());
				getfiles.add(file.getAbsolutePath().replace("\\", "\\\\"));
			}
		}

	}
	
	public List<String> listAllFiles(String dir, String ext) {
		File fdir = new File(dir);
	
		if (!fdir.isDirectory()) {
			System.out.println("Direcotry " + dir + " doesn't exists");
			//return;
		}

		FilenameFilterImpl2 filter = new FilenameFilterImpl2(ext);
		
		print(fdir, filter);
		return getfiles;
	}
	
	public static void main(String[] args) {
		
		//当前项目目录
		String workDir ="e:\\ITA\\2016 Equipment";
		List<String> getfiles = new ArrayList<String>();
		ListAllFilesByExt laf = new ListAllFilesByExt();
		getfiles = laf.listAllFiles(workDir, ".xls");
		for(int i=0;i<getfiles.size();i++){
			System.out.println(getfiles.get(i));
		}
		
	}


}

