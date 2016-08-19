package com.javaexcel.rw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessExcelFiles {

    public static void main(String[] args) {
    	InputStream is = null;
    	InputStream is2 = null;
    	InputStream is3 = null;
    	String filepath = null;
    	
    	ExcelReader excelReader = new ExcelReader();
    	
    	String outfilepath = "D:\\t7.txt";
		String workDir ="e:\\ITA";
		
		List<String> getfiles = new ArrayList<String>();
		ListAllFilesByExt laf = new ListAllFilesByExt();
		// ��ȡĿ¼�µ������ļ�
		getfiles = laf.listAllFiles(workDir, ".xls");
		for(int z=0;z<getfiles.size();z++){
			System.out.println(getfiles.get(z));
			filepath = getfiles.get(z);
			
	    	try {
	            
	            is = new FileInputStream(filepath);
	           // ��ȡExcel��������
	            int sheetNums = excelReader.getExcelSheetNum(is);
	            for(int i=0;i<sheetNums;i++){
	                is2 = new FileInputStream(filepath);
	                is3 = new FileInputStream(filepath);  
	             // ��ȡExcel������
	                String[] title = excelReader.readExcelTitle(is2,i);
	             // ����Excel������
	                String[] ftitle = excelReader.filterTitle(title);
	                System.out.println("���Excel���ı���:");
	                for (int j=0;j<title.length;j++) {
	                    if(ftitle[j]=="Y"){
	                    	System.out.println("���������: "+title[j]);
	                    }
	                }
	                
	             // ��ȡExcel�������    
	                Map<Integer, String> map = excelReader.readExcelContent(is3,ftitle,i);
//	                System.out.println("���Excel��������:");
//	                for (int k = 1; k <= map.size(); k++) {
//	                     System.out.println(map.get(k));
//	                }
	                
	             //��ʼд��txt�ļ�
	                ResultFileWriter rfw = new ResultFileWriter();
	                rfw.txtWriter(outfilepath, map);
	            }
	            
	   
	        } catch (FileNotFoundException e) {
	            System.out.println("δ�ҵ�ָ��·�����ļ�!");
	            e.printStackTrace();
	        }
			
		}
    	

    }
}

