package com.uwo.isms.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.domain.LoginVO;
import com.uwo.isms.service.*;
import com.uwo.isms.util.EgovUserDetailsHelper;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uwo.isms.common.Constants;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.dao.FMInsptDAO;
import com.uwo.isms.domain.BoardVO;
import com.uwo.isms.domain.FileVO;
import com.uwo.isms.domain.SearchVO;
import com.uwo.isms.util.EgovStringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service("fmInsptService")
public class FMInsptServiceImpl implements FMInsptService {

	Logger log = LogManager.getLogger(FMInsptServiceImpl.class);

	@Resource(name = "fmInsptDAO")
	private FMInsptDAO fmInsptDAO;

	@Resource(name = "commonUtilDAO")
	private CommonUtilDAO commonUtilDAO;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "commonUtilService")
	private CommonUtilService commonUtilService;

	@Autowired
	private FMCompsService fmCompsService;

	@Autowired
	private InspectionManagerService inspectionManagerService;

	@Resource(name = "fileUploadService")
	FileUploadService fileUploadService;

	// 2016-11-01, searchVO to map
	@Override
	public List<?> fm_inspt001_list(Map<String, String> map) {
		// 2018-03-22 s, 컴플라이언스 코드 첫글자
		if (StringUtils.isNotEmpty(map.get("standard"))) {
			map.put("standardD", map.get("standard").substring(0, 1));
		}
		return fmInsptDAO.fm_inspt001_list(map);
	}

	public List<?> fm_inspt001_list2(Map<String, String> map) {
		// 2018-03-22 s, 컴플라이언스 코드 첫글자
		if (StringUtils.isNotEmpty(map.get("standard"))) {
			map.put("standardD", map.get("standard").substring(0, 1));
		}
		return fmInsptDAO.fm_inspt001_list2(map);
	}

	public List<?> fm_inspt001_list3(Map<String, String> map) {
		// 2018-03-22 s, 컴플라이언스 코드 첫글자
		if (StringUtils.isNotEmpty(map.get("standard"))) {
			map.put("standardD", map.get("standard").substring(0, 1));
		}
		return fmInsptDAO.fm_inspt001_list3(map);
	}

	@Override
	public List<?> fm_inspt001_getFileList(Map<String, String> map) {
		return fmInsptDAO.fm_inspt001_getFileList(map);
	}

	public List<?> fm_inspt001_getFileList2(Map<String, String> map) {
		return fmInsptDAO.fm_inspt001_getFileList2(map);
	}

	@Override
	public List<?> fm_inspt001_getSaFileList(Map<String, String> map) {
		return fmInsptDAO.fm_inspt001_getSaFileList(map);
	}

	public List<?> fm_inspt001_getFileList_by_department_of_service(Map<String, String> map) {
		return fmInsptDAO.fm_inspt001_getFileList_by_department_of_service(map);
	}

	// 2016-11-01, N 추가
	@Override
	public int fm_inspt001_setMsrFile(Map<String, String> map) {
		return fmInsptDAO.fm_inspt001_setMsrFile(map);
	}

	@Override
	public List<?> fm_inspt001_getStdList(SearchVO searchVO) {

		return fmInsptDAO.fm_inspt001_getStdList(searchVO);
	}

	@Override
	public List<?> fm_inspt001_getCntr1List(SearchVO searchVO) {

		return fmInsptDAO.fm_inspt001_getCntr1List(searchVO);
	}

	@Override
	public List<?> fm_inspt001_getCntr2List(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt001_getCntr2List(searchVO);
	}

	@Override
	public List<?> fm_inspt001_getCntr3List(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt001_getCntr3List(searchVO);
	}

	@Override
	public List<?> fm_inspt001_getCntr4List(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt001_getCntr4List(searchVO);
	}

	@Override
	public List<?> fm_inspt001_getCntr5List(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt001_getCntr5List(searchVO);
	}

	@Override
	public List<?> fm_inspt002_list(Map<String, String> map) {
		return fmInsptDAO.fm_inspt002_list(map);
	}

	@Override
	public EgovMap fm_inspt002_info(Map<String, String> map) {
		return fmInsptDAO.fm_inspt002_info(map);
	}

	@Override
	public void fm_inspt002_insert(Map<String, Object> map) {
		map.put("ufmRstSta", Constants.RST_STA_N);
		fmInsptDAO.fm_inspt002_insert(map);
		String[] arrWrkId = (String[]) map.get("ufpWrkId");
		String[] arrMngType = (String[]) map.get("mngType");

		for (int i = 0; i < arrWrkId.length; i++) {
			if ("I".equals(arrMngType[i])) {
				Map<String, String> mngMap = new HashMap<String, String>();
				mngMap.put("ufpWrkId", arrWrkId[i]);
				mngMap.put("ufpFltKey", map.get("ufmFltKey").toString());
				mngMap.put("rgtId", map.get("rgtId").toString());
				fmInsptDAO.fm_inspt002_mng_insert(mngMap);
			}
		}
	}

	@Override
	public void fm_inspt002_update(Map<String, Object> map) {
		fmInsptDAO.fm_inspt002_update(map);

		String[] arrWrkId = (String[]) map.get("ufpWrkId");
		String[] arrMngType = (String[]) map.get("mngType");

		for (int i = 0; i < arrWrkId.length; i++) {
			Map<String, String> mngMap = new HashMap<String, String>();
			mngMap.put("ufpWrkId", arrWrkId[i]);
			mngMap.put("ufpFltKey", map.get("ufmFltKey").toString());

			if ("I".equals(arrMngType[i])) {
				mngMap.put("rgtId", map.get("uptId").toString());
				fmInsptDAO.fm_inspt002_mng_insert(mngMap);
			} else if ("D".equals(arrMngType[i])) {
				mngMap.put("uptId", map.get("uptId").toString());
				fmInsptDAO.fm_inspt002_mng_delete(mngMap);
			}
		}
	}

	@Override
	public List<?> fm_inspt002_mng_list(Map<String, String> map) {
		return fmInsptDAO.fm_inspt002_mng_list(map);
	}

	@Override
	public EgovMap fm_inspt002_mng_info(Map<String, String> map) {
		return fmInsptDAO.fm_inspt002_mng_info(map);
	}

	@Override
	public void fm_inspt002_mng_update(Map<String, String> map, List<FileVO> list) {
		if (map.get("authKey").equals(Constants.AUH_A)
				&& map.get("ufpStaCod").equals(Constants.FLT_STA_SUC)) {
			map.put("ufpCofYn", "Y");
		}
		fmInsptDAO.fm_inspt002_mng_update(map);

		// uwo_flt_map 가 모두 승인완료 일 경우 uwo_flt_mtr 업데이트
		if (map.get("authKey").equals(Constants.AUH_A)
				&& map.get("ufpStaCod").equals(Constants.FLT_STA_SUC)) {
			fmInsptDAO.fm_inspt002_updateRst(map);
		}

		for (int i = 0; i < list.size(); i++) {
			FileVO fvo = list.get(i);
			fvo.setUmf_con_key(map.get("ufpMapKey").toString());
			commonUtilDAO.writeFileTable(fvo);
		}
	}

	@Override
	public void fm_inspt002_apv_update(Map<String, Object> map) {
		fmInsptDAO.fm_inspt002_apv_update(map);
	}

	@Override
	public String inspt001_view(String wKey) {
		return fmInsptDAO.inspt001_view(wKey);
	}

	@Override
	public void fmInspt001_viewUpdate(SearchVO searchVO) {
		fmInsptDAO.fmInspt001_viewUpdate(searchVO);
	}

	@Override
	public List<?> fm_inspt002_report(Map<String, String> map) {
		return fmInsptDAO.fm_inspt002_report(map);
	}

	@Override
	public List<?> fm_inspt002_report_mng(Map<String, String> map) {
		return fmInsptDAO.fm_inspt002_report_mng(map);
	}

	@Override
	public List<?> fm_inspt003_list(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt003_list(searchVO);
	}

	@Override
	public List<?> fm_inspt004_list(Map<String, String> map) {
		// 2018-03-22 s, 컴플라이언스 코드 첫글자
		if (StringUtils.isNotEmpty(map.get("standard"))) {
			map.put("standardD", map.get("standard").substring(0, 1));
		}
		return fmInsptDAO.fm_inspt004_list(map);
	}

	@Override
	public List<?> fm_inspt004_report(Map<String, String> map) {
		return fmInsptDAO.fm_inspt004_report(map);
	}

	@Override
	public List<?> fm_inspt004_getCtrCode(Map<String, String> map) {
		return fmInsptDAO.fm_inspt004_getCtrCode(map);
	}

	@Override
	public List<?> fm_inspt004_mappinglist() {
		return fmInsptDAO.fm_inspt004_mappinglist();
	}

	@Override
	public List<?> fmInspt004_service_M(Map<String, String> map) {
		return fmInsptDAO.fmInspt004_service_M(map);
	}

	@Override
	public List<?> fm_inspt004_popup(Map<String, String> map) {
		return fmInsptDAO.fm_inspt004_popup(map);
	}

	@Override
	public void fm_inspt004_popup_mapping(Map map) {
		fmInsptDAO.fm_inspt004_popup_mapping(map);
	}

	@Override
	public void fm_inspt004_update(Map map) {
		fmInsptDAO.fm_inspt004_update(map);
	}

	@Override
	public EgovMap fm_inspt004_download(Map map) {
		return fmInsptDAO.fm_inspt004_download(map);
	}

	@Override
	public List<?> fm_inspt004_downloadList(Map map) {
		// 2018-03-22 s, 컴플라이언스 코드 첫글자
		if (StringUtils.isNotEmpty(map.get("standard").toString())) {
			map.put("standardD", map.get("standard").toString().substring(0, 1));
		}
		return fmInsptDAO.fm_inspt004_downloadList(map);
	}

	@Override
	public String fm_inspt004_createZip(List<EgovMap> list) throws Exception {

		byte[] buffer = new byte[1024];
		ArrayList<String> nameList = new ArrayList<String>();

		String output = propertyService.getString("uploadpath") + File.separator
				+ RandomStringUtils.randomAlphanumeric(10) + ".zip";

		FileOutputStream fos = new FileOutputStream(output);
		ZipOutputStream zos = new ZipOutputStream(fos);

		for (int i = 0; i < list.size(); i++) {

			EgovMap entry = (EgovMap) list.get(i);

			//ucm3lvNam값을 쿼리문에서 뽑아오지 않기 때문에 조사할 필요가 없다. 2020-02-07 최영우 수정
			if (entry.get("ucm1lvCod") == null || entry.get("ucm1lvNam") == null
					|| entry.get("ucm2lvCod") == null || entry.get("ucm2lvNam") == null
					|| entry.get("ucm3lvCod") == null) {
					/*entry.get("ucm3lvNam") == null) {*/
				
				continue;
			}

			// 1자리 숫자일 경우 앞에 _ 추가(Windows 정렬기준)
			String ucm1lvCod = entry.get("ucm1lvCod").toString();
			Pattern pattern = Pattern.compile("^([A-Za-z]*)([0-9]?)");
			Matcher matcher = pattern.matcher(ucm1lvCod);
			if (matcher.matches()) {
				// ucm1lvCod = matcher.group(1) + StringUtils.leftPad(matcher.group(2), 2, "-");
				ucm1lvCod = new StringBuffer(matcher.group(1)).append("_").append(matcher.group(2))
						.toString();
			}

			// Windows 디렉토리명에 다음문자 사용불가 \/:*?"<>|
			String ucm1lvNam = StringUtils.removePattern(entry.get("ucm1lvNam").toString(),
					"[\\\\/:*?\"<>|]");
			String ucm2lvNam = "";
			if (entry.get("ucm2lvCod") != null) {
				ucm2lvNam = StringUtils.removePattern(entry.get("ucm2lvNam").toString(),
						"[\\\\/:*?\"<>|]");
			}
			ucm1lvNam = EgovStringUtil.cutString(ucm1lvNam, ",,", 30);
			ucm2lvNam = EgovStringUtil.cutString(ucm2lvNam, ",,", 30);
			
			/*
			 * 값이 모두 있는 경우에는 모든 폴더 생성 
			 * ucm3lvCod의 값만 없는 경우에는 ucm2lvCod 폴더까지 생성 
			 * ucm2lvCod, ucm3lvCod의 값이 모두 없는 경우에는 ucm1lvCod 폴더까지만 생성 
			 */ 
			String zipPath = "";
			if (entry.get("ucm2lvCod") != null && entry.get("ucm3lvCod") != null) {
				zipPath = ucm1lvCod + " " + ucm1lvNam + File.separator + entry.get("ucm2lvCod")
						+ " " + ucm2lvNam + File.separator + entry.get("ucm3lvCod")
						+ File.separator;
			} else if (entry.get("ucm2lvCod") != null && entry.get("ucm3lvCod") == null) {
				zipPath = ucm1lvCod + " " + ucm1lvNam + File.separator + entry.get("ucm2lvCod")
						+ " " + ucm2lvNam + File.separator;
			} else {
				zipPath = ucm1lvCod + " " + ucm1lvNam + File.separator;
			}

			String zipName = zipPath;
			String fileName = null;

			/*
			 * filename.txt filename (2).txt filename (3).txt
			 */
			if (entry.get("umfConFnm") != null) {

				if (entry.containsKey("umfFleKey")) {
					int fileKey = ((BigDecimal) entry.get("umfFleKey")).intValue();

					if (!isFileOfCompletedWork(fileKey)) {
						continue;
					}
				}

				zipName += entry.get("umfConFnm").toString();
				String ext = FilenameUtils.getExtension(zipName);

				while (nameList.contains(zipName)) {
					String baseName = zipPath + FilenameUtils.getBaseName(zipName);
					pattern = Pattern.compile("(.+)\\((\\d+)\\)$");
					matcher = pattern.matcher(baseName);

					if (matcher.matches()) {
						String path = matcher.group(1);
						int count = Integer.parseInt(matcher.group(2));
						count++;
						zipName = path + " (" + String.valueOf(count) + ")." + ext;
					} else {
						zipName = baseName + " (2)." + ext;
					}
				}
				nameList.add(zipName);
				fileName = entry.get("umfSvrPth").toString() + File.separator
						+ entry.get("umfSvrFnm").toString();
			}
			log.debug("zipPath: {}, zipName: {}, fileName: {}", zipPath, zipName, fileName);

			ZipEntry ze = new ZipEntry(zipName);

			try {
				zos.putNextEntry(ze);
			} catch (ZipException exception) {
				log.warn("duplicate zip entry : " + zipName);
			}

			if (fileName != null) {
				File f = new File(fileName);
				if (f.exists()) {
					FileInputStream in = new FileInputStream(fileName);

					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					in.close();
				}
			}
		}
		zos.closeEntry();
		// remember close it
		zos.close();
		return output;
	}

	public String fm_inspt004_createZip_with_sa(List<EgovMap> list, List<EgovMap> saFileList)
			throws Exception {

		byte[] buffer = new byte[1024];
		ArrayList<String> nameList = new ArrayList<String>();

		String output = propertyService.getString("uploadpath") + File.separator
				+ RandomStringUtils.randomAlphanumeric(10) + ".zip";

		FileOutputStream fos = new FileOutputStream(output);
		ZipOutputStream zos = new ZipOutputStream(fos);

		for (int i = 0; i < list.size(); i++) {

			EgovMap entry = (EgovMap) list.get(i);

			BigDecimal currentCtrKey = (BigDecimal) entry.get("ucmCtrKey");
			// 1자리 숫자일 경우 앞에 _ 추가(Windows 정렬기준)
			String ucm1lvCod = entry.get("ucm1lvCod").toString();
			Pattern pattern = Pattern.compile("^([A-Za-z]*)([0-9]?)");
			Matcher matcher = pattern.matcher(ucm1lvCod);
			if (matcher.matches()) {
				// ucm1lvCod = matcher.group(1) + StringUtils.leftPad(matcher.group(2), 2, "-");
				ucm1lvCod = new StringBuffer(matcher.group(1)).append("_").append(matcher.group(2))
						.toString();
			}

			// Windows 디렉토리명에 다음문자 사용불가 \/:*?"<>|
			String ucm1lvNam = StringUtils.removePattern(entry.get("ucm1lvNam").toString(),
					"[\\\\/:*?\"<>|]");
			String ucm2lvNam = StringUtils.removePattern(entry.get("ucm2lvNam").toString(),
					"[\\\\/:*?\"<>|]");
			ucm1lvNam = EgovStringUtil.cutString(ucm1lvNam, ",,", 30);
			ucm2lvNam = EgovStringUtil.cutString(ucm2lvNam, ",,", 30);

			String zipPath = ucm1lvCod + " " + ucm1lvNam + File.separator + entry.get("ucm2lvCod")
					+ " " + ucm2lvNam + File.separator + entry.get("ucm3lvCod") + File.separator;

			String zipName = zipPath;
			String fileName = null;

			/*
			 * filename.txt filename (2).txt filename (3).txt
			 */
			if (entry.get("umfConFnm") != null) {

				zipName += entry.get("umfConFnm").toString();
				String ext = FilenameUtils.getExtension(zipName);

				while (nameList.contains(zipName)) {
					String baseName = zipPath + FilenameUtils.getBaseName(zipName);
					pattern = Pattern.compile("(.+)\\((\\d+)\\)$");
					matcher = pattern.matcher(baseName);

					if (matcher.matches()) {
						String path = matcher.group(1);
						int count = Integer.parseInt(matcher.group(2));
						count++;
						zipName = path + " (" + String.valueOf(count) + ")." + ext;
					} else {
						zipName = baseName + " (2)." + ext;
					}
				}
				nameList.add(zipName);
				fileName = entry.get("umfSvrPth").toString() + File.separator
						+ entry.get("umfSvrFnm").toString();
			}
			log.debug("zipPath: {}, zipName: {}, fileName: {}", zipPath, zipName, fileName);

			ZipEntry ze = new ZipEntry(zipName);
			zos.putNextEntry(ze);

			if (fileName != null) {
				File f = new File(fileName);
				if (f.exists()) {
					FileInputStream in = new FileInputStream(fileName);

					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					in.close();
				}
			}

			for (EgovMap saFileMap : saFileList) {
				if (saFileMap.get("ctrKey").equals(currentCtrKey)) {
					String saFileName = saFileMap.get("svrPth").toString() + File.separator
							+ saFileMap.get("svrFnm").toString();
					File f = new File(saFileName);
					if (f.exists()) {

						String saZipName = (String) saFileMap.get("conFnm");
						String saExt = FilenameUtils.getExtension(saZipName);

						if (nameList.contains(zipPath + saZipName))
							continue;

						while (nameList.contains(zipPath + saZipName)) {
							String baseName = zipPath + FilenameUtils.getBaseName(saZipName);
							pattern = Pattern.compile("(.+)\\((\\d+)\\)$");
							matcher = pattern.matcher(baseName);

							if (matcher.matches()) {
								String path = matcher.group(1);
								int count = Integer.parseInt(matcher.group(2));
								count++;
								saZipName = path + " (" + String.valueOf(count) + ")." + saExt;
							} else {
								saZipName = saZipName + " (2)." + saExt;
							}
						}
						nameList.add(zipPath + saZipName);

						ZipEntry ze2 = new ZipEntry(zipPath + saZipName);
						zos.putNextEntry(ze2);
						FileInputStream in = new FileInputStream(saFileName);

						int len;
						while ((len = in.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}
						in.close();
					}
				}
			}
		}
		zos.closeEntry();
		// remember close it
		zos.close();
		return output;
	}

	@Override
	public List<?> getInspt002MainList(Map map) {
		return fmInsptDAO.getInspt002MainList(map);
	}

	public List<EgovMap> getSaWorkFileRelatedOtherService(Map map) {
		return fmInsptDAO.getSaWorkFileRelatedOtherService(map);
	}

	public void fm_inspt001_insertMsrSaFile(Map map) {
		fmInsptDAO.fm_inspt001_insertMsrSaFile(map);
	}

	public void fm_inspt001_deleteMsrSaFile(Map map) {
		fmInsptDAO.fm_inspt001_deleteMsrSaFile(map);
	}

	public List<EgovMap> getMeasureSaFile(Map map) {
		return fmInsptDAO.getMeasureSaFile(map);
	}

	@Override
	public List<EgovMap> getSaExcelData(String bcyCode, String depCode) {
		return fmInsptDAO.getSaExcelData(bcyCode, depCode);
	}

	@Override
	public void updateWrkPrg(Map map) {
		fmInsptDAO.updateWrkPrg(map);
	}

	@Override
	public List<EgovMap> getSaZipList(Map<String, String> map) {
		return fmInsptDAO.getSaZipList(map);
	}

	public boolean isFileOfCompletedWork(int fileKey) {
		EgovMap work = fmInsptDAO.getWorkByFile(fileKey);
		return ((String) work.get("utwWrkSta")).equals("90");
	}

	public List<EgovMap> fm_inspt008_compliance_list(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt008_compliance_list(searchVO);
	}

	@Override
	public List<EgovMap> fm_inspt008_work_list(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt008_work_list(searchVO);
	}

	@Override
	public List<EgovMap> fm_inspt008_file_list(SearchVO searchVO) {
		return fmInsptDAO.fm_inspt008_file_list(searchVO);
	}

	@Override
	public void fm_inspt006_excel_upload(MultipartFile file, SearchVO searchVO) throws Exception {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		InputStream inputStream = commonUtilService.decryptFileDAC(file);

		// 엑셀 워크북
		Workbook wb = WorkbookFactory.create(inputStream);

		Sheet resultSheet = wb.getSheetAt(0);
		Sheet metaSheet = wb.getSheetAt(1);

		// metaSheet 유효성 검사
		Row metaSheetRow = metaSheet.getRow(0);
		String inputHashValue = metaSheetRow.getCell(0).getStringCellValue();
		int inputKeyItemNumber = (int) metaSheetRow.getCell(1).getNumericCellValue();
		String inputBcyCode = metaSheetRow.getCell(2).getStringCellValue();
		String inputService = metaSheetRow.getCell(3).getStringCellValue();
		String inputStandard = metaSheetRow.getCell(4).getStringCellValue();

		String hashValue = DigestUtils.md5Hex(inputBcyCode + inputService + inputStandard)
				.toUpperCase();

		String complianceKind = fmCompsService.getKindOfCompliance(searchVO.getStandard());

		if (!inputHashValue.equals(hashValue)) {

		}

		// resultSheet 변경점 확인

		Map<Integer, Map<String, Object>> result = new HashMap<>();
		Map<String, Object> resultOfControl = null;

		for (int i = 1; i < resultSheet.getLastRowNum(); i++) {
			Row row = resultSheet.getRow(i);
			if (row == null)
				continue;

			int controlKey = (int) row.getCell(0).getNumericCellValue();
			int answerKey = (int) row.getCell(1).getNumericCellValue();
			int isSelected = row.getCell(8) == null ? 0
					: (int) row.getCell(8).getNumericCellValue();
			String mpAnswerValue = row.getCell(10) == null ? ""
					: row.getCell(10).getStringCellValue();
			String resultComment = row.getCell(11) == null ? ""
					: row.getCell(11).getStringCellValue();
			String policyComment = row.getCell(12) == null ? ""
					: row.getCell(12).getStringCellValue();

			if (!result.containsKey(controlKey)) {
				resultOfControl = new HashMap<>();
				resultOfControl.put("resultComment", resultComment);
				resultOfControl.put("policyComment", policyComment);
				resultOfControl.put("answers", new ArrayList<String>());
				result.put(controlKey, resultOfControl);
			}

			if (isSelected == 1) {
				ArrayList<String> answers = (ArrayList<String>) resultOfControl.get("answers");
				if (complianceKind.equals("infra_mp")) {
					answers.add(mpAnswerValue);
				} else {
					answers.add(String.valueOf(answerKey));
				}
			}

		}

		for (Map.Entry<Integer, Map<String, Object>> entry : result.entrySet()) {
			int controlKey = entry.getKey();
			Map<String, Object> entryValue = entry.getValue();
			String resultComment = (String) entryValue.get("resultComment");
			String policyComment = (String) entryValue.get("policyComment");
			ArrayList<String> answers = (ArrayList<String>) entryValue.get("answers");

			//
			Map<String, Object> map = new HashMap<>();
			map.put("service", searchVO.getService());
			map.put("standard", searchVO.getStandard());
			map.put("bcyCode", searchVO.getBcyCode());
			map.put("controlItem", String.valueOf(controlKey));
			map.put("rstDtl", resultComment);
			map.put("rltDoc", policyComment);
			inspectionManagerService.saveInspectionResultAdditionalInfo(map);

			//
			map.put("selectedAnswer", answers.toArray(new String[answers.size()]));
			map.put("complianceKind", complianceKind);
			map.put("workerKey", loginVO.getuum_usr_key());
			inspectionManagerService.saveInspectionResult(map);
		}

	}

	@Override
	public void fm_inspt006_zip_upload(MultipartFile file, SearchVO searchVO) throws Exception {

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

		String uploadBasePath = propertyService.getString("uploadpath");
		String tblGbnOfInspectionFile = "IST";

		Map<String, String> controlItemListQueryConditionMap = new HashMap<>();
		controlItemListQueryConditionMap.put("bcyCod", searchVO.getBcyCode());
		controlItemListQueryConditionMap.put("ctrGbn", searchVO.getStandard());

		List<EgovMap> controlItemList = fmCompsService
				.getControlItemList(controlItemListQueryConditionMap);

		if (controlItemList.isEmpty()) {
			// TODO 오류 처리 필요
		}

		Map<String, EgovMap> controlItemMap = new HashMap<>();
		for (EgovMap egovMap : controlItemList) {
			controlItemMap.put((String) egovMap.get("ucm3lvCod"), egovMap);
		}

		// 압축 파일을 임시 저장한다.
		File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
		FileOutputStream o = new FileOutputStream(zip);
		IOUtils.copy(file.getInputStream(), o);
		o.close();

		// 임시 저장된 압축 파일을 zip4j 라이브러리를 이용해 압축 해제한다.
		String unzipDestinationPath = uploadBasePath + File.separator + "ist_temp_unzip"
				+ File.separator + UUID.randomUUID().toString();
		File tempUnzipDirectory = new File(unzipDestinationPath);

		try {
			ZipFile zipFile = new ZipFile(zip);
			UnzipParameters param = new UnzipParameters();
			zipFile.setFileNameCharset("ISO8859-1");
			List list = zipFile.getFileHeaders();

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				FileHeader fh = (FileHeader) iterator.next();
				byte[] b = fh.getFileName().getBytes("ISO8859-1");
				String fileName = null;
				try {
					fileName = new String(b, Charset.forName("EUC-KR"));
				} catch (Throwable e) {
					fileName = fh.getFileName();
				}
				zipFile.extractFile(fh, unzipDestinationPath, param, fileName);
			}

			File[] directoryList = tempUnzipDirectory.listFiles();

			if (directoryList == null) {
				// TODO 오류 처리 필요
			}

			for (File controlItemDirectory : directoryList) {
				String controlItem3LvCode = controlItemDirectory.getName();

				// 디렉토리가 아니거나, 파일을 포함하고 있지 않거나,
				// 해당 디렉토리 이름이 통제항목 번호가 아닌 경우는 더 이상 진행하지 않는다.
				if (!controlItemDirectory.isDirectory() || controlItemDirectory.listFiles() == null
						|| controlItemDirectory.listFiles().length < 1
						|| !controlItemMap.containsKey(controlItem3LvCode))
					continue;

				for (File fileOfControlItem : controlItemDirectory.listFiles()) {

					// 알아야 하는 것들 umf_tbl_gbn, umf_con_gbn, umf_con_key, umf_bcy_cod
					// umf_tbl_gbn 은 IST 로 고정 값이다.
					// umf_con_gbn, umf_bcy_cod 는 요청할때 줘야하는 정보다.
					// umf_con_key 는 통제항목 번호 1.1.1 이 값을 이용해서 실제 식별번호를 알아내야 한다.

					EgovMap matchedControlItem = controlItemMap.get(controlItem3LvCode);
					int matchedControlItemKey = ((BigDecimal) matchedControlItem.get("ucmCtrKey"))
							.intValueExact();

					String pathThatMoveTo = uploadBasePath + File.separator + tblGbnOfInspectionFile
							+ File.separator + searchVO.getStandard();
					File directoryThatMoveTo = new File(pathThatMoveTo);

					String rawFileName = fileOfControlItem.getName();
					String fileName = rawFileName;
					String fileNameToBeMoved = UUID.randomUUID().toString() + "_" + fileName;

					log.debug("###[rawFileName]### : " + rawFileName);
					log.debug("###[fileName]### : " + fileName);
					log.debug("###[fileNameToBeMoved]### : " + fileNameToBeMoved);

					File destinationFilePath = new File(
							pathThatMoveTo + File.separator + fileNameToBeMoved);

					if (!directoryThatMoveTo.exists()) {
						directoryThatMoveTo.mkdirs();
					}

					fileOfControlItem.renameTo(destinationFilePath);

					// TODO FileUpload.java 내용과 중복된다. 리팩토링 필요하다.
					FileVO fileVO = new FileVO();

					fileVO.setUmf_tbl_gbn(tblGbnOfInspectionFile);
					fileVO.setUmf_con_gbn(searchVO.getStandard());
					fileVO.setUmf_con_key(String.valueOf(matchedControlItemKey));
					fileVO.setUmf_bcy_cod((String) httpServletRequest.getSession()
							.getAttribute(CommonConfig.SES_MAN_CYL_KEY));
					fileVO.setUmf_rgt_id((String) httpServletRequest.getSession()
							.getAttribute(CommonConfig.SES_USER_KEY));
					fileVO.setUmf_svr_pth(pathThatMoveTo);
					fileVO.setUmf_svr_fnm(fileNameToBeMoved);
					fileVO.setUmf_con_fnm(fileName);
					String[] ext = (fileName).split("\\.");
					fileVO.setUmf_fle_ext(ext[ext.length - 1]);
					fileVO.setUmf_fle_siz(Long.toString(fileOfControlItem.length()));

					fileUploadService.storeFile(fileVO);

				}
			}

		} catch (net.lingala.zip4j.exception.ZipException e) {
			e.printStackTrace();
		} finally {
			// 임시 압축파일을 삭제한다.
			zip.delete();
			// 임시 압축 디렉토리를 삭제한다.
			if (tempUnzipDirectory.isDirectory()) {
				FileUtils.deleteDirectory(tempUnzipDirectory);
			}

		}

	}

}
