package com.cc.zmj;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Example {
	private static Summary summary=new Summary();
	public static void main(String[] args) throws Exception {
		File file=new File("data.txt");
		if((!file.exists()) || (!file.canRead())){
			System.out.println("file is not exist or cannot read!!!");
		}
		BufferedReader br = null;
        FileReader fb = null;
        StringBuffer sb = new StringBuffer();
        try
        {
            fb = new FileReader(file);
            br = new BufferedReader(fb);

            String str = null;
            while ((str = br.readLine()) != null)
            {
                sb.append(str + "\r\n");
            }
            str=sb.toString();
            String res=summary.generateSummary(str);
            System.out.println(res);
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeQuietly(br);
            closeQuietly(fb);
        }


	}
    private static void closeQuietly(Closeable closeable)
    {
        try
        {
            if (closeable != null)
            {
                closeable.close();
            }
        }
        catch (IOException e)
        {
        }
    }
}
