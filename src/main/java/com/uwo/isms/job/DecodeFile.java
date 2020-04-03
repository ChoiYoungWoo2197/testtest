package com.uwo.isms.job;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import SCSL.*;

/*
 * 2016-11-02
 * 특정 디렉토리의 암호화 문서를 모두 복호화 하여 저장함
 * 서버에서 실행해야 함
 * ~/softcamp/03_Sample/ 디렉토리에 있음
 */
public class DecodeFile {

  private List<String> result = new ArrayList<String>();

  private static String srcDir = "/home/tomadm/isams_file/work/2016";
  private static String dstDir = "/home/tomadm/isams_file/work/2016/dec";


  public List<String> getResult() {
	return result;
  }

  public static void main(String[] args) {

	DecodeFile DecodeFile = new DecodeFile();

	DecodeFile.searchDirectory(new File(srcDir));

	int count = DecodeFile.getResult().size();
	if (count == 0) {
	    System.out.println("\nNo result found!");
	}
	else {
	    System.out.println("\nFound " + count + " result!\n");
	    for (String fn : DecodeFile.getResult()) {
			System.out.println("Found : " + fn);
			int result = DecodeFile.decodeSCSL(fn);
			System.out.println("Decode : " + result);
	    }
	}
  }

  public void searchDirectory(File directory) {

	if (directory.isDirectory()) {
	    search(directory);
	}
	else {
	    System.out.println(directory.getAbsoluteFile() + " is not a directory!");
	}

  }

  private void search(File file) {

	if (file.isDirectory()) {
	  System.out.println("Searching directory ... " + file.getAbsoluteFile());

        //do you have permission to read this directory?
	    if (file.canRead()) {
			for (File temp : file.listFiles()) {
			    if (temp.isDirectory()) {
			    	search(temp);
			    }
			    else {
			    	result.add(temp.getAbsoluteFile().toString());
			    }
			}
	    }

	 } else {
		System.out.println(file.getAbsoluteFile() + "Permission Denied");
	 }
  }

  public int decodeSCSL(String srcFile) {

	Path p = Paths.get(srcFile);
	String fileName = p.getFileName().toString();
	String dstFile = dstDir + File.separator + fileName;

	SLDsFile sFile = new SLDsFile();

	sFile.SettingPathForProperty("/home/tomadm/softcamp/02_Module/02_ServiceLinker/softcamp.properties");

	int retVal = sFile.CreateDecryptFileDAC (
		"/home/tomadm/softcamp/04_KeyFile/keyDAC_SVR0.sc",
		"SECURITYDOMAIN",
		srcFile,
		dstFile);

	return retVal;
  }
}