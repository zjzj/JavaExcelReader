package com.javaexcel.rw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ResultFileWriter {
	
	public void txtWriter(String filepath,Map map){
		System.out.println("��ʼд���ļ�������");
		
        FileWriter fw = null;
        BufferedWriter bw = null;
		try {
			fw = new FileWriter(filepath,true);
			bw = new BufferedWriter(fw);
            for (int i = 1; i <= map.size(); i++) {
                try {
					bw.write(map.get(i)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
            }
            bw.flush();
		System.out.println("���д���ļ���");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
