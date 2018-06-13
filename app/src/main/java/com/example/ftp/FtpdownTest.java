package com.example.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class FtpdownTest {
	
	public static void main(String[] args) {
		ArrayList<String> names=new ArrayList<String>();
		names.add("8969288f4245120e7c3870287cce0ff3.jpg");
//		names.add("a1e74684393506031fbc728708004c32.png");
//		names.add("bdf3bf1da3405725be763540d6601144.jpg");
		FtpdownTest ftpdownload=new FtpdownTest();
	//	ftpdownload.downloadFtpFile(names);
		ftpdownload.getFileList();
	}
	
	private void downloadFtpFile(ArrayList<String> files) {
		FTPUtil ftp = new FTPUtil("192.168.5.1", 21, "", "");
		try {
			ftp.openConnect();
			for (String fileName : files) {
				String path = "d:\\ftpTest\\";
				Result result = ftp.download("\\stufflib",fileName, path);
				
				String md5=getMd5ByFile(new File(path, fileName));
				if (result.isSucceed()) {
					System.out.println(fileName + "下载FTP文件成功!!! md5="+md5);
				} else {
					System.out.println(fileName + "下载FTP文件失败!!!md5="+md5);
				}
			}
			ftp.closeConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 获取文件的MD5值
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	private void getFileList(){
		FTPUtil ftp = new FTPUtil("192.168.5.1", 21, "", "");
		try {
			ftp.openConnect();
		    List<FTPFile> ftpFileList=	ftp.listFiles("/upload");
			for (FTPFile ftpFile : ftpFileList) {
				System.out.println("file name="+ ftpFile.getName());
			}
			ftp.closeConnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
