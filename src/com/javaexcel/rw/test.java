package com.javaexcel.rw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class test {
	 public static void main(String[] args) {
	    	InputStream is = null;
	    	InputStream is2 = null;
	    	InputStream is3 = null;
	    	String filepath = null;
	    	
	    	ExcelReader excelReader = new ExcelReader();
	    	
	    	String outfilepath = "D:\\t7.txt";

			filepath = "e:\\ITA\\2012 Equipment\\EEMS List\\EEMS List of May.2012.xls";
				
		    	try {
		            
		            is = new FileInputStream(filepath);
		           // 读取Excel表格标数量
		            int sheetNums = excelReader.getExcelSheetNum(is);
		            for(int i=0;i<sheetNums;i++){
		            	System.out.println("ee");
		                is2 = new FileInputStream(filepath);
		                is3 = new FileInputStream(filepath);  
		             // 读取Excel表格标题
		                String[] title = excelReader.readExcelTitle(is2,i);
		             // 过滤Excel表格标题
		                String[] ftitle = excelReader.filterTitle(title);
		                System.out.println("获得Excel表格的标题:");
		                for (int j=0;j<title.length;j++) {
		                    if(ftitle[j]=="Y"){
		                    	System.out.println("输出过滤列: "+title[j]);
		                    }
		                }
		                
		             // 读取Excel表格内容    
		                Map<Integer, String> map = excelReader.readExcelContent(is3,ftitle,i);
//		                System.out.println("获得Excel表格的内容:");
//		                for (int k = 1; k <= map.size(); k++) {
//		                     System.out.println(map.get(k));
//		                }
		                
		             //开始写入txt文件
		                ResultFileWriter rfw = new ResultFileWriter();
		                rfw.txtWriter(outfilepath, map);
		            }
		            
		   
		        } catch (FileNotFoundException e) {
		            System.out.println("未找到指定路径的文件!");
		            e.printStackTrace();
		        } catch (java.lang.NullPointerException e) {
		        	e.printStackTrace();
		        }
				
			}
	    	

	}

