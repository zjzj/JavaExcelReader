package com.javaexcel.rw;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����Excel���Ĺ�����
 */
public class ExcelReader {
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    /**
     * ��ȡExcel sheet����
     * @param InputStream
     * @return int ����
     */    
    public int getExcelSheetNum(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				if(wb!=null){
					wb.close();
				}
        		if(fs!=null){
        			fs.close();
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        int sheetNums = wb.getNumberOfSheets();
        return sheetNums;
    }
    
    /**
     * ��ȡExcel����ͷ������
     * @param InputStream
     * @return String ��ͷ���ݵ�����
     */
    public String[] readExcelTitle(InputStream is,int n) {
        int colNum =0;
        String[] title = {"NA"};
    	try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				if(wb!=null){
					wb.close();
				}
        		if(fs!=null){
        			fs.close();
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        sheet = wb.getSheetAt(n);
        row = sheet.getRow(0);
        // ����������
        if (null != row){
        	colNum = row.getPhysicalNumberOfCells();       
            title = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                //title[i] = getStringCellValue(row.getCell((short) i));
                title[i] = getCellFormatValue(row.getCell((short) i));
            }
        }
        
        return title;
  
    }

    public String[] filterTitle(String[] title){
    	String[] ftitle = new String[title.length];
    	for(int i=0;i<ftitle.length;i++){
    		ftitle[i]="N";
    	}
    	String[] S = {"���ص���","PO","CCF No"};
		for(int i=0;i<S.length;i++){
			Pattern pattern = Pattern.compile(S[i],Pattern.CASE_INSENSITIVE);
			for(int j=0;j<title.length;j++){
				Matcher matcher = pattern.matcher((CharSequence) title[j]);
				if(matcher.find()){
			         ftitle[j]="Y";
			      }
			}
		}
    	
    	return ftitle;
    }
    /**
     * ��ȡExcel��������
     * @param InputStream
     * @return Map ������Ԫ���������ݵ�Map����
     */
    public Map<Integer, String> readExcelContent(InputStream is,String[] columnIndex,int n) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        String sheetName = "";
        int colNum = 0;
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				if(wb!=null){
					wb.close();
				}
        		if(fs!=null){
        			fs.close();
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        sheet = wb.getSheetAt(n);
        sheetName = wb.getSheetName(n);
        // �õ�������
        int rowNum = sheet.getLastRowNum();
        //int rowNum = sheet.getPhysicalNumberOfRows();
        row = sheet.getRow(0);
        if (null != row){
        	colNum = row.getPhysicalNumberOfCells();
        }else {
        	
        }
              
        System.out.println("��ȡ����: "+rowNum);
        int k=1;
        // ��������Ӧ�ôӵڶ��п�ʼ,��һ��Ϊ��ͷ�ı���
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // ÿ����Ԫ�������������"-"�ָ���Ժ���Ҫʱ��String���replace()������ԭ����
                // Ҳ���Խ�ÿ����Ԫ����������õ�һ��javabean�������У���ʱ��Ҫ�½�һ��javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
            	if(columnIndex[j]=="Y"){
            		try{
            			str += getCellFormatValue(row.getCell((short) j)).trim() + "    \t";
            		}catch(java.lang.NullPointerException e){
            			//System.out.println(i);
            			//e.printStackTrace();
            			//break;
            		}
            			
            	} 	   
                j++;
            }
            
            if(str.trim().isEmpty()){
            	//content.put(k, "Debug");
            	//k++;
            	str = "";
            }else{
            	str+=sheetName;
            	content.put(k, str);
            	str = "";
            	k++;
            }
       
        }
        System.out.println("д��map "+content.size() +" �У�");
        return content;
    }

    /**
     * ��ȡ��Ԫ����������Ϊ�ַ������͵�����
     * 
     * @param cell Excel��Ԫ��
     * @return String ��Ԫ����������
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * ��ȡ��Ԫ����������Ϊ�������͵�����
     * 
     * @param cell
     *            Excel��Ԫ��
     * @return String ��Ԫ����������
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[����]", "-").replace("��", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("���ڸ�ʽ����ȷ!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ����HSSFCell������������
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";

        if (cell != null) {
            // �жϵ�ǰCell��Type
        	
            switch (cell.getCellType()) {
            // �����ǰCell��TypeΪNUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            	// �жϵ�ǰ��cell�Ƿ�ΪDate
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                	// �����Date������ת��ΪData��ʽ
                    
                    //����1�������ӵ�data��ʽ�Ǵ�ʱ����ģ�2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //����2�������ӵ�data��ʽ�ǲ�����ʱ����ģ�2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    break;                
                }else{
                	DecimalFormat df = new DecimalFormat("#.####");    
                	cellvalue = df.format(cell.getNumericCellValue());
                	break;
                }
            	
            case HSSFCell.CELL_TYPE_FORMULA: {
                if(cell.getCachedFormulaResultType()==cell.CELL_TYPE_ERROR){
                	cellvalue = "#DIV/0!";
                	break;
                }
            	
            	// �жϵ�ǰ��cell�Ƿ�ΪDate
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    System.out.println("**");
                	// �����Date������ת��ΪData��ʽ
                    
                    //����1�������ӵ�data��ʽ�Ǵ�ʱ����ģ�2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //����2�������ӵ�data��ʽ�ǲ�����ʱ����ģ�2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    break;                
                }

                // ����Ǵ�����
                else {
                    // ȡ�õ�ǰCell����ֵ
                	DecimalFormat df = new DecimalFormat("#.####");    
                	cellvalue = df.format(cell.getNumericCellValue());
                }
                break;
            }
            // �����ǰCell��TypeΪSTRIN
            case HSSFCell.CELL_TYPE_STRING:
                // ȡ�õ�ǰ��Cell�ַ���
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
            	cellvalue = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
            	cellvalue = "";
                break;
            // Ĭ�ϵ�Cellֵ
            default:
                cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}
