/**
 ***********************************
 * @source CommonUtilController.java
 * @date 2014. 10. 21.
 * @project isms3_godohwa
 * @description
 ***********************************
 */
package com.uwo.isms.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.service.CommonUtilService;
import com.uwo.isms.service.FileUploadService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
@RequestMapping("/common")
public class CommonUtilController extends SupportController {

	/*  grid excel down
	 * @see com.uwo.isms.service.CommonCodeService#getSampleCode()
	 */

	@Resource(name = "commonUtilService")
	private CommonUtilService commonUtilService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadServiceImpl;

	@RequestMapping("/gridExcelDown.do")
	public ModelAndView fmSetup006 (ModelMap model,	HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();


//        List<?> list = commonCodeService.getSampleCode();

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map resultMap = new HashMap();
//        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	@RequestMapping("/getFileDown.do")
	public void getFileDown(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String key = (String)req.getParameter("downKey");
		//String where = (String)req.getParameter("where");

		/*
		FileVO fvo = commonUtilService.getFileName(key);
		String fileName = fvo.getUmf_svr_fnm();

		// 2016-11-01, getUmf_svr_pth() 로 변경
		String uploadPath = fvo.getUmf_svr_pth();
		/*String uploadPath = propertyService.getString("uploadpath");
		if(where.equals("doc")){
			uploadPath = propertyService.getString("docSampleUploadPath");
		}else if(where.equals("brd")){
			uploadPath = propertyService.getString("boardUploadPath");
		}else if(where.equals("wrk")){
			uploadPath = propertyService.getString("workUploadPath");
		}else if(where.equals("ins")){
			uploadPath = propertyService.getString("insptUploadPath");
		}else if(where.equals("svc")){
			uploadPath = propertyService.getString("serviceUploadPath");
		}
		String fullPath = uploadPath + File.separator + fileName;

		ServletOutputStream sos = null;
		FileInputStream fis = null;

		File downFile = new File(fullPath);
		try{
			sos = res.getOutputStream();
			fis = new FileInputStream(downFile);

			res.setContentType("application/octet-stream");
			res.setHeader("Content-Disposition", "attachment;filename=" + UriUtils.encodeFragment(fvo.getUmf_con_fnm(), "UTF-8") + ";");

			byte[] outbyte = new byte[4096];
			while(fis.read(outbyte,0,4096)!=-1){
				sos.write(outbyte,0,4096);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			fis.close();
			sos.flush();
			sos.close();
		} */

		/*
		 * 2017-01-16
		 * uploadpath DB에서 가져옴
		 * 기존 다운로드 방식은 파일 마지막 부분에 불필요 문자가 추가됨으로써 업로드/다운로드 파일 불일치 현상 발생
		 */
 		FileVO fvo = commonUtilService.getFileName(key);
		String fileName = fvo.getUmf_svr_fnm();
		String uploadPath = fvo.getUmf_svr_pth();
		String fullPath = uploadPath + File.separator + fileName;

	    res.setHeader("Content-Disposition", "attachment;filename=" + UriUtils.encodeFragment(fvo.getUmf_con_fnm(), "UTF-8") + ";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = res.getOutputStream();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(fullPath);
			FileCopyUtils.copy(fis, out);
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch(IOException ioe) {}
			}
		}
		out.flush();
	}

	/*
	 * 2017-01-27, getFileDown() 로 대체함
	 *
	@RequestMapping("/getFileDown_sample.do")
	public void getFileDown_sample(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String key = (String)req.getParameter("downKey");
		String where = "doc";

		FileVO fvo = commonUtilService.getFileName(key);
		String fileName = fvo.getUmf_svr_fnm();
		String uploadPath = propertyService.getString("uploadpath");
		if(where.equals("doc")){
			uploadPath = propertyService.getString("docSampleUploadPath");
		}else if(where.equals("brd")){
			uploadPath = propertyService.getString("boardUploadPath");
		}else if(where.equals("wrk")){
			uploadPath = propertyService.getString("workUploadPath");
		}else if(where.equals("ins")){
			uploadPath = propertyService.getString("insptUploadPath");
		}else if(where.equals("svc")){
			uploadPath = propertyService.getString("serviceUploadPath");
		}
		String fullPath = uploadPath + File.separator + fileName;

		ServletOutputStream sos = null;
		FileInputStream fis = null;

		File downFile = new File(fullPath);
		try{
			sos = res.getOutputStream();
			fis = new FileInputStream(downFile);

			res.setContentType("application/octet-stream");
			res.setHeader("Content-Disposition", "attachment;filename=" + UriUtils.encodeFragment(fvo.getUmf_con_fnm(), "UTF-8") + ";");

			byte[] outbyte = new byte[4096];
			while(fis.read(outbyte,0,4096)!=-1){
				sos.write(outbyte,0,4096);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			fis.close();
			sos.flush();
			sos.close();
		}
	}*/


	@RequestMapping("/delFile.do")
	public ModelAndView delFile(@RequestParam String key ,Model model,final HttpServletRequest req) throws Exception{
		//String key = (String)req.getParameter("key");
		fileUploadServiceImpl.delFile(key);
		String seq = fileUploadServiceImpl.boardKey(key);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		return mav;
	}

	@RequestMapping(value="/ZIP_DOWN.do")
	public void zipFileDown(HttpServletRequest req, HttpServletResponse res) throws Exception{

		Calendar cal = new GregorianCalendar();
    	String uId = (String)req.getSession().getAttribute(CommonConfig.SES_USER_ID_KEY);
    	String fileName = cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+"-"+cal.get(cal.DATE)+"_"+uId;

		String check[] = req.getParameterValues("checking");
		String zip[]= req.getParameterValues("checking");
		String uploadPath = propertyService.getString("workUploadPath");

		Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String datefolder =sdf.format(dt).toString();
	    File dir = new File("D:/usr/", "temp");
	      // 폴더가 없으면 생성
	    if(!dir.exists()){
	         dir.mkdir();
	    }

	    res.setContentType("Content-type: text/zip");
	    res.setHeader("Content-Transfer-Encoding:", "binary");
		res.setHeader("Content-Disposition", "attachment;filename="+fileName+".zip");
		res.setHeader("Pragma", "no-cache;");
		res.setHeader("Expires", "-1;");

		//res.setContentType("Content-type: text/zip");
	    //res.setHeader("Content-Disposition","attachment;filename="+fileName+".zip");


		List files = new ArrayList();

		List checkList = new ArrayList();
		List zipList = new ArrayList();
		String fName="";
		String cName="";

		for(int i=0;i<check.length;i++){
			String fKey = check[i];
			List nameList = fileUploadServiceImpl.getFileName(fKey);
			for(int j=0;j<nameList.size();j++){
				EgovMap nameMap = (EgovMap)nameList.get(j);
				fName = (String)nameMap.get("umfSvrFnm");
				cName = (String)nameMap.get("umfConFnm");
				zipList.add(uploadPath+"\\"+fName);
				checkList.add(dir+"//"+cName);
			}
		}
		FileInputStream fis_1 = null;
		FileOutputStream fos_1 = null;
		for(int i=0;i<checkList.size();i++){
			fis_1 = new FileInputStream((String)zipList.get(i));
			if(fis_1 != null){
				fos_1 = new FileOutputStream((String)checkList.get(i));
				byte[] buf  = new byte[1024];
				int read;
				while((read = fis_1.read(buf))!=-1){
					fos_1.write(buf,0,read);
				}
				files.add(new File((String)checkList.get(i)));
			}
		}

		ServletOutputStream out = res.getOutputStream();
		ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new BufferedOutputStream(out));
		for (int i=0;i<files.size();i++) {
			File file = (File)files.get(i);
			zos.putArchiveEntry(new ZipArchiveEntry(file.getName()));

			// Get the file
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);

			} catch (FileNotFoundException fnfe) {
				// If the file does not exists, write an error entry instead of
				// file
				// contents
				zos.write(("ERROR: Could not find file " + file.getName())
						.getBytes());
				zos.closeArchiveEntry();
				continue;
			}

			BufferedInputStream fif = new BufferedInputStream(fis);

			// Write the contents of the file
			int data = 0;
			while ((data = fif.read()) != -1) {
				zos.write(data);
			}
			fif.close();

			zos.closeArchiveEntry();
		}

		zos.close();
		out.close();
	}

	@RequestMapping("/fileView_popup.do" )
	public String fileView_popup (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        return "FM-FILEVIEW_popup";
	}

	@RequestMapping("/fileView.do" )
	public void fileView (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ServletOutputStream sos = null;
		FileInputStream fis = null;
		File viewFile = new File("c://tmp/aaaa.xls");
		try{
			sos = res.getOutputStream();
			fis = new FileInputStream(viewFile);

			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Transfer-Encoding:", "binary");
			res.setHeader("Content-Disposition", "attachment;filename=" + UriUtils.encodeFragment("aaaa.xls", "UTF-8") + ";");
			res.setHeader("Pragma", "no-cache;");
			res.setHeader("Expires", "-1;");

			byte[] outbyte = new byte[4096];
			while(fis.read(outbyte,0,4096)!=-1){
				sos.write(outbyte,0,4096);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			fis.close();
			sos.flush();
			sos.close();
		}
	}

	@RequestMapping("/userList.do")
	public ModelAndView userList (ModelMap model, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ModelAndView mav = new ModelAndView();

        SearchVO searchVO = new SearchVO();
        searchVO.setDivision(req.getParameter("popService"));
        searchVO.setDept(req.getParameter("popDept"));
        searchVO.setWorkerName(req.getParameter("popName"));

        List<?> list = commonUtilService.userList(searchVO);

		res.setCharacterEncoding("");
		res.setContentType("text/xml; charset=UTF-8");

		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", list);
        mav.addAllObjects(resultMap);
        mav.setViewName("jsonView");

        return mav;
	}

	/**
	 * 키워드로 사용자를 검색한다.
	 * @param keyword 검색 키워드
	 */
	@RequestMapping(value = "/user/keyword/search.do", method = RequestMethod.GET)
	public ModelAndView searchUserByKeywordForAsync(@RequestParam(value = "keyword") String keyword) {
		return makeJsonResponse().addObject("userList", commonUtilService.searchUserByKeyword(keyword));
	}

	/**
	 * 사용자 식벌번호들로 사용자를 검색한다.
	 * @param userKeys 검색할 사용자 식별번호들
	 */
	@RequestMapping(value = "/user/keys/search.do", method = RequestMethod.GET)
	public ModelAndView searchUserByInUserKeyByAsync(@RequestParam(value="ids[]") String[] userKeys) {
		return makeJsonResponse()
				.addObject("userList", commonUtilService.searchUserByInUserKey(Arrays.asList(userKeys)));
	}

	/**
	 * 파일 목록을 범용적으로 요청하기위한 용도
	 * @param fileVO 검색 값들을 담을 VO
	 */
	@RequestMapping(value = "/file/list.do", method = RequestMethod.GET)
	public ModelAndView getFileListByAsync(FileVO fileVO) throws Exception {
		List<FileVO> fileVOList = fileUploadServiceImpl.selectFileList(fileVO.getUmf_con_key(),
				fileVO.getUmf_tbl_gbn(), fileVO.getUmf_con_gbn(), fileVO.getUmf_bcy_cod());

		return makeJsonResponse().addObject("fileList", fileVOList);
	}

}