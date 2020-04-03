/*
 * 2016-09-20
 * 실서버에는 문서 복화화 로직이 추가되어 있으므로
 * 이 파일로 업데이트를 할 경우 문제가 발생 할 수 있습니다.
 *
 */
package com.uwo.isms.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.FileVO;

import egovframework.rte.fdl.property.EgovPropertyService;

public class FileUpload {

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	public List<FileVO> fileuplaod(HttpServletRequest request, String tblGbn, String conGbn) throws Exception {

		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		String uploadPath = (String)request.getAttribute("uploadPath");
		File saveFolder = new File(uploadPath);

		String fileName = null;
		List<FileVO> result = new ArrayList<FileVO>();
		// 디렉토리 생성
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		if (!isDir) {

			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			MultipartFile file;
			String filePath;
			System.out.println("@@@@"+files.size());
			while (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();
				file = entry.getValue();
				fileName = file.getOriginalFilename();
				if (!"".equals(fileName)) {
					Calendar cal = Calendar.getInstance();
					System.out.println(cal.getTimeInMillis());
					String vfnm = cal.getTimeInMillis() +"_"+ file.getOriginalFilename();
					FileVO vo = new FileVO();
					vo.setUmf_tbl_gbn(tblGbn);
					vo.setUmf_con_gbn(conGbn);
					vo.setUmf_bcy_cod((String)request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					vo.setUmf_rgt_id((String)request.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					vo.setUmf_svr_pth(uploadPath);
					vo.setUmf_svr_fnm(vfnm);
					vo.setUmf_con_fnm(file.getOriginalFilename());
					String[] ext = ((String)file.getOriginalFilename()).split("\\.");
					vo.setUmf_fle_ext(ext[ext.length-1]);
					//vo.setUmf_fle_ext(file.getContentType());
					vo.setUmf_fle_siz(Long.toString(file.getSize()));
					// 파일 전송
					filePath = uploadPath + File.separator + vfnm;

					file.transferTo(new File(filePath));

					// result.add(fileName);
					result.add(vo);
					// fileUpDAO.file_upload(vo);
				}
			}
		}
		return result;
	}

	public List<FileVO> fileuplaod_J(HttpServletRequest request, String tblGbn, String conGbn) throws Exception {

		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		final List<MultipartFile> fileList = multiRequest.getFiles("b_file");

		String uploadPath = (String)request.getAttribute("uploadPath");
		File saveFolder = new File(uploadPath);

		String fileName = null;
		List<FileVO> result = new ArrayList<FileVO>();
		// 디렉토리 생성
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		if (!isDir) {
			MultipartFile file;
			String filePath;

			for (int i=0; i<fileList.size(); i++) {
				file = fileList.get(i);
				fileName = file.getOriginalFilename();
				if (!"".equals(fileName)) {
					Calendar cal = Calendar.getInstance();
					String vfnm = cal.getTimeInMillis() +"_"+ file.getOriginalFilename();

					FileVO vo = new FileVO();
					vo.setUmf_tbl_gbn(tblGbn);
					vo.setUmf_con_gbn(conGbn);
					vo.setUmf_bcy_cod((String)request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					vo.setUmf_rgt_id((String)request.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					vo.setUmf_svr_pth(uploadPath);
					vo.setUmf_svr_fnm(vfnm);
					vo.setUmf_con_fnm(file.getOriginalFilename());
					String[] ext = ((String)file.getOriginalFilename()).split("\\.");
					vo.setUmf_fle_ext(ext[ext.length-1]);
					//vo.setUmf_fle_ext(file.getContentType());
					vo.setUmf_fle_siz(Long.toString(file.getSize()));

					// 파일 전송
					filePath = uploadPath + File.separator + vfnm;
					file.transferTo(new File(filePath));

					result.add(vo);
				}
			}
		}
		return result;
	}

public List<FileVO> fileuplaod_svc(HttpServletRequest request, String tblGbn, String allGbn, String infGbn) throws Exception {

		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		String allGbnYn = (String)request.getAttribute("uvmAllOrg");
		String infGbnYn = (String)request.getAttribute("uvmInfOrg");

		String uploadPath = (String)request.getAttribute("uploadPath");
		File saveFolder = new File(uploadPath);

		String fileName = null;
		List<FileVO> result = new ArrayList<FileVO>();
		// 디렉토리 생성
		boolean isDir = false;

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}
		if (!isDir) {

			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			MultipartFile file;
			String filePath;
			System.out.println("@@@@"+files.size());
			while (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();
				file = entry.getValue();
				fileName = file.getOriginalFilename();
				if (!"".equals(fileName)) {
					System.out.println("!@#!@#!@#@" + allGbnYn);
					System.out.println("!@#!@#!@#@" + infGbnYn);
					Calendar cal = Calendar.getInstance();
					System.out.println(cal.getTimeInMillis());
					String vfnm = cal.getTimeInMillis() +"_"+ file.getOriginalFilename();
					FileVO vo = new FileVO();
					vo.setUmf_tbl_gbn(tblGbn);
					if(allGbnYn.equals("Y")){
						vo.setUmf_con_gbn(allGbn);
					}else if(!allGbnYn.equals("Y") && infGbnYn.equals("Y")){
						vo.setUmf_con_gbn(infGbn);
					}
					vo.setUmf_bcy_cod((String)request.getSession().getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					vo.setUmf_rgt_id((String)request.getSession().getAttribute(CommonConfig.SES_USER_KEY));
					vo.setUmf_svr_pth(uploadPath);
					vo.setUmf_svr_fnm(vfnm);
					vo.setUmf_con_fnm(file.getOriginalFilename());
					String[] ext = ((String)file.getOriginalFilename()).split("\\.");
					vo.setUmf_fle_ext(ext[ext.length-1]);
					//vo.setUmf_fle_ext(file.getContentType());
					vo.setUmf_fle_siz(Long.toString(file.getSize()));
					// 파일 전송
					filePath = uploadPath + File.separator + vfnm;

					file.transferTo(new File(filePath));

					// result.add(fileName);
					result.add(vo);
					// fileUpDAO.file_upload(vo);
					allGbnYn = "N";
				}
			}
		}
		return result;
	}
}
