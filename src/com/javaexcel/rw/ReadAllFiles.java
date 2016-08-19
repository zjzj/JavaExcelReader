package com.javaexcel.rw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadAllFiles {
	//��ȡһ���ļ����������ļ������ļ����µ������ļ�  
    public void ReadAllFile(String filePath) {  
        File f = null;  
        f = new File(filePath);  
        File[] files = f.listFiles(); // �õ�f�ļ�������������ļ���  
        List<File> list = new ArrayList<File>();  
        for (File file : files) {  
            if(file.isDirectory()) {  
                //��ε�ǰ·�����ļ��У���ѭ����ȡ����ļ����µ������ļ�  
                ReadAllFile(file.getAbsolutePath());  
            } else {  
                list.add(file);  
            }  
        }  
        for(File file : files) {  
            System.out.println(file.getAbsolutePath());  
        }  
    }  
      
    //��ȡһ���ļ����µ������ļ��к��ļ�  
    public void ReadFile(String filePath) {  
        File f = null;  
        f = new File(filePath);  
        File[] files = f.listFiles(); // �õ�f�ļ�������������ļ���  
        List<File> list = new ArrayList<File>();  
        for (File file : files) {  
            list.add(file);  
        }  
        for(File file : files) {  
            System.out.println(file.getAbsolutePath());  
        }  
    }  
      
    public static void main(String[] args) {  
        String filePath = "E:/ITA";  
        new ReadAllFiles().ReadAllFile(filePath);  
    }  

}
