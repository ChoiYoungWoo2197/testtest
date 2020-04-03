package com.uwo.isms.job;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.domain.FileVO;

import egovframework.rte.fdl.property.EgovPropertyService;

//@Component("FileUploadJob")
@Controller
@RequestMapping("/job")
public class FileUploadJob {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name="commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	private static final Logger log = LogManager.getLogger(FileUploadJob.class);
	private static StopWatch stopWatch = new StopWatch("job");

	private List<String> fileList = new ArrayList<String>();

	@RequestMapping("/insertManageFile.do")
	public void insertManageFile(HttpServletRequest req) throws SQLException {

		try {
			String localPath = propertyService.getString("workUploadPath") + File.separator + "2016";
			stopWatch.start();
			searchDirectory(new File(localPath));

			int count = fileList.size();
			if (count == 0) {
			    log.info("No result found!");
			}
			else {
			    log.info("Found {} result!", count);
			    for (String fn : fileList) {

			    	FileVO fileVO = new FileVO();
			    	File file = new File(fn);

			    	String fileName = FilenameUtils.getName(fn);
			    	String filePath = FilenameUtils.getFullPathNoEndSeparator(fn);
			    	String fileExtension = FilenameUtils.getExtension(fn);
			    	int fileSize = Math.round(file.length());

			    	// Thubms.db, ~ 제외
			    	if (fileName.equals("Thumbs.db") || fileName.substring(0, 1).equals("~")) {
			    		continue;
			    	}

			    	Pattern pattern = Pattern.compile("([0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{1,2})");
					Matcher matcher = pattern.matcher(fn);

					String golNo = "";
					String depNam = "";
					String wrkKey = "";

					if (matcher.find()) {
						golNo = matcher.group(1);

						// 맨앞자리가 0일경우 0. 삭제
						if (golNo.substring(0,2).equals("0.")) {
							golNo = golNo.substring(2, golNo.length());
						}
					}

					// 부서명
					depNam = fn.substring(localPath.length() + 4, fn.length());
					depNam = depNam.substring(0, depNam.indexOf(File.separator));

					log.info("{} {} : {}", depNam, golNo, fn);

					if (golNo != "" && depNam != "") {
						Map<String, String> map = new HashMap<String, String>();
						map.put("golNo", golNo);
						map.put("depNam", depNam);
						wrkKey = commonUtilDAO.getWrkKeyByCtrAndDep(map);

						// wrkKey 를 못 가져올 경우 부서 없이 한번 더 검색
						if (wrkKey == null || wrkKey == "") {
							map.put("depNam", "");
							wrkKey = commonUtilDAO.getWrkKeyByCtrAndDep(map);
						}
					}

			    	fileVO.setUmf_tbl_gbn("WRK");
			    	fileVO.setUmf_con_gbn("6");
			    	fileVO.setUmf_bcy_cod((String) req.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
			    	fileVO.setUmf_svr_pth(filePath);
			    	fileVO.setUmf_svr_fnm(fileName);
			    	fileVO.setUmf_con_fnm(fileName);
			    	fileVO.setUmf_fle_ext(fileExtension);
			    	fileVO.setUmf_fle_siz(String.valueOf(fileSize));
			    	fileVO.setUmf_rgt_id((String) req.getSession().getAttribute(CommonConfig.SES_USER_KEY));
			    	fileVO.setUmf_con_key(wrkKey);

			    	commonUtilDAO.writeFileTable(fileVO);
			    }
			}
		}
	 	finally {
		 	stopWatch.stop();
		 	log.info(stopWatch.prettyPrint());
	 	}
	}


	private void searchDirectory(File file) {

		if (file.isDirectory()) {
			log.info("Searching directory ... " + file.getAbsoluteFile());

			//do you have permission to read this directory?
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						searchDirectory(temp);
					} else {
						fileList.add(temp.getAbsoluteFile().toString());
					}
				}
			}
			else {
				log.info(file.getAbsoluteFile() + "Permission Denied");
			}
		}
	}
}
