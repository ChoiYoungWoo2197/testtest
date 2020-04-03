package com.uwo.isms.service;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Constants;
import com.uwo.isms.dao.CommonUtilDAO;
import com.uwo.isms.dao.FileUploadDAO;
import com.uwo.isms.dao.ProtectionMeasureDAO;
import com.uwo.isms.domain.*;
import com.uwo.isms.domain.enums.ProtectionMeasureStatus;
import com.uwo.isms.util.CommonUtil;
import com.uwo.isms.util.EgovUserDetailsHelper;
import com.uwo.isms.util.FileUpload;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ProtectionMeasureService {

    @Autowired
    private EgovPropertyService propertyService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ProtectionMeasureDAO protectionMeasureDAO;

    @Autowired
    private CommonUtilDAO commonUtilDAO;

    @Autowired
    private FileUploadDAO fileUploadDAO;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /*===[ 일반 유틸 ]================================================================================================*/
    /*================================================================================================================*/

    /**
     * 2019년도에서 현 시점까지 기반시설 보호대책 수립을 구성할 수 있는 연도 정보를 반환한다.
     */
    public List<Integer> getAvailableYearList() {
        List<Integer> yearList = new ArrayList<Integer>();

        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        int currentYear = calendar.get(Calendar.YEAR);

        for (int i = 2019; i < currentYear + 2; i++) {
            yearList.add(i);
        }

        return yearList;
    }

    /**
     * 해당 연도가 기반시설 보호대책 수립 관련 연도인지를 판단한다.
     * 관련 연도가 아니라면 현재 연도를 반환한다.
     * @param year 기반시설 관련 연도 여부를 검사할 대상 연도
     */
    public int getValidedYearOfProtectionMeasure(int year) {
        return getAvailableYearList().contains(year) ? year : commonUtil.getCurrentYear();
    }
   
    /**
     * 기반시설 보호대책 수립연도가 가장 최근날짜를 반환한다.
     */
    public int getLastSeason() {
    	ProtectionMeasureVO protectionMeasureTaskWorkerVO = protectionMeasureDAO.getLastProtectionMeasure();
        return protectionMeasureTaskWorkerVO.getSeason();
    }
    
    /*===[ 물리 파일 ]================================================================================================*/
    /*================================================================================================================*/


    /**
     * 기반시설 관련 파일들을 업로드 한다.
     * @param httpServletRequest HttpServletRequest
     * @param type 기반시설 관련 파일의 형태 : ["guide", "task"]
     */
    private List<FileVO> uploadProtectionMeasureFile(HttpServletRequest httpServletRequest, String type) throws Exception {

        FileUpload fileUpload = new FileUpload();
        String uploadPath = "";
        String conGbn     = "";

        if (type.equalsIgnoreCase("guide")) {
            uploadPath = propertyService.getString("protectionMeasureGuideUploadPath");
            conGbn = Constants.FILE_CON_PTM_GUIDE;
        } else if (type.equalsIgnoreCase("task")) {
            uploadPath = propertyService.getString("protectionMeasureTaskUploadPath");
            conGbn = Constants.FILE_CON_PTM_TASK;
        }

        httpServletRequest.setAttribute("uploadPath", uploadPath);

        return fileUpload.fileuplaod(httpServletRequest, Constants.FILE_GBN_PTM, conGbn);
    }

    /**
     * 기반시설 보호대책 보호지침 파일을 업로드한다.
     * @param httpServletRequest HttpServletRequest
     */
    public FileVO uploadProtectionMeasureGuideFile(HttpServletRequest httpServletRequest) throws Exception {
        List<FileVO> fileVOList = uploadProtectionMeasureFile(httpServletRequest, "guide");
        return fileVOList.size() > 0 ? fileVOList.get(0) : null;
    }

    /**
     * 기반시설 보호대책 중점과제 파일들을 업로드한다.
     * @param httpServletRequest HttpServletRequest
     */
    public List<FileVO> uploadProtectionMeasureTaskFiles(HttpServletRequest httpServletRequest) throws Exception {
        return uploadProtectionMeasureFile(httpServletRequest, "task");
    }

    /*===[ 파일 ]=====================================================================================================*/
    /*================================================================================================================*/

    /**
     * UWO_MNG_FLE.UMF_TBL_GBN 값을 기반시설 관련 Constants.FILE_GBN_PTM 값으로 고정시켜놓고
     * 그 이외의 값들을 입력받아 관련 목록을 가지고온다.
     * @param conGbn UWO_MNG_FLE.UMF_CON_GBN 값
     * @param conKey UWO_MNG_FLE.UMF_CON_KEY 값
     */
    private List<FileVO> getProtectionMeasureFiles(String conGbn, String conKey) throws Exception {
        return fileUploadService.selectFileList(conKey, Constants.FILE_GBN_PTM, conGbn, "");
    }

    /**
     * 기반시설 보호대책 보호지침 파일을 가지고온다.
     * @param protectionMeasureId 기반시설 보호대책 식별번호
     */
    public FileVO getProtectionMeasureGuideFile(int protectionMeasureId) throws Exception {
        List<FileVO> fileVOList = getProtectionMeasureFiles(Constants.FILE_CON_PTM_GUIDE, String.valueOf(protectionMeasureId));
        return fileVOList.size() > 0 ? fileVOList.get(0) : null;
    }

    /**
     * 기반시설 보호대책 중점과제 관련 파일을 가지고온다.
     * @param protectionMeasureTaskId 기반시설 보호대책 식별번호
     */
    public List<FileVO> getProtectionMeasureTaskFiles(int protectionMeasureTaskId) throws Exception {
        return getProtectionMeasureFiles(Constants.FILE_CON_PTM_TASK, String.valueOf(protectionMeasureTaskId));
    }

    public List<FileVO> getProtectionMeasureTaskFilesByProtectionMeasureId(int protectionMeasureId) {
        return protectionMeasureDAO.getProtectionMeasureTaskFileListByProtectionMeasureId(protectionMeasureId);
    }

    /**
     * 기반시설 보호대책 보호지침 파일 정보를 DB에 저장한다.
     * @param taskId 중점업무 식별번호
     * @param fileVO 파일 VO
     */
    public void storeProtectionMeasureGuideFile(int taskId, FileVO fileVO) {
        fileVO.setUmf_con_key(String.valueOf(taskId));
        commonUtilDAO.writeFileTable(fileVO);
    }

    /**
     * 기반시설 보호대책 보호지침 파일 정보를 DB에 갱신한다.
     * @param taskId 중점업무 식별번호
     * @param fileVO 파일 VO
     */
    public void updateProtectionMeasureGuideFile(int taskId, FileVO fileVO) {
        fileUploadDAO.delFile(fileVO);
        storeProtectionMeasureGuideFile(taskId, fileVO);
    }

    /**
     * 기반시설 보호대책 중점과제 파일 정보를 DB에 저장한다.
     * @param taskId 중점과제 식별번호
     * @param fileVO 파일 VO
     */
    public void storeProtectionMeasureTaskFile(int taskId, FileVO fileVO) {
        fileVO.setUmf_con_key(String.valueOf(taskId));
        commonUtilDAO.writeFileTable(fileVO);
    }

    /**
     * 기반시설 보호대책 중점과제 파일 정보들을 DB에 저장한다.
     * @param taskId 중점과제 식별번호
     * @param fileVOList 파일 VO 리스트
     */
    public void storeProtectionMeasureTaskFiles(int taskId, List<FileVO> fileVOList) {
        for (FileVO fileVO : fileVOList) {
            storeProtectionMeasureTaskFile(taskId, fileVO);
        }
    }

    /**
     * 해당 중점과제 노드의 증적파일 관리 권한을 가지고 있는 여부를 확인한다.
     * @param taskId 중점과제 식별번호
     */
    public boolean hasProtectionMeasureTaskFileManagePermission(int taskId) {
        // @TODO 코드구현 해야 됨
        return true;
    }

    /**
     * 기반시설 보호대책 중점과제 파일을 서버로 업로드하고 파일 정보를 DB에 저장한다.
     * @param taskId 중점과제 식별번호
     * @throws Exception
     */
    public List<FileVO> saveProtectionMeasureTaskFiles(int taskId, HttpServletRequest httpServletRequest) throws Exception {
        List<FileVO> uploadedFileVOList = uploadProtectionMeasureTaskFiles(httpServletRequest);
        storeProtectionMeasureTaskFiles(taskId, uploadedFileVOList);
        return uploadedFileVOList;
    }

    public void destroyProtectionMeasureGuideFile(int protectionMeasureId) {
        FileVO fileVO = new FileVO();
        fileVO.setUmf_tbl_gbn(Constants.FILE_GBN_PTM);
        fileVO.setUmf_con_gbn(Constants.FILE_CON_PTM_GUIDE);
        fileVO.setUmf_con_key(String.valueOf(protectionMeasureId));
        fileUploadDAO.delFile(fileVO);
    }

    /**
     * 기반시설 보호대책 중점과제 파일을 삭제한다.
     * 현재는 소프트 삭제로 진행한다. 업로드된 파일은 삭제하지 않고 DB상에서 논리적으로 삭제한다.
     * @param fileKeys 삭제할 파일 키들
     */
    public void destroyProtectionMeasureTaskFiles(String[] fileKeys) {
        for (String fileKey : fileKeys) {
            fileUploadDAO.delFile(fileKey);
        }
    }

    /*===[ 보호대책 ]=================================================================================================*/
    /*================================================================================================================*/

    public List<Integer> getSeasonListOfWorker(int userKey) {
        List<Integer> season = new ArrayList<Integer>();
        List<HashMap> seasonList = protectionMeasureDAO.getSeasonListOfWorker(userKey);
        for (HashMap<String, Integer> map : seasonList) {
            season.add(map.get("SEASON"));
        }
        return season;
    }

    /**
     * 현재 로그인한 사용자가 보호대책 관리 권한을 가지고 있는지 여부를 확인한다.
     * @return boolean
     */
    public boolean hasProtectionMeasureManagePermission() {
        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        return loginVO.getUat_auh_gbn().equalsIgnoreCase("A") || loginVO.getUat_auh_gbn().equalsIgnoreCase("V");
    }

    /**
     * 보호대책 정보를 아이디로 가지고온다.
     */
    public ProtectionMeasureVO getProtectionMeasureById(int protectionMeasureId) {
        return protectionMeasureDAO.getProtectionMeasure(protectionMeasureId);
    }

    /**
     * 보호대책 정보를 연도를 기준으로 가지고온다.
     * @param year 연도
     */
    public ProtectionMeasureVO getProtectionMeasureByYear(int year) {
        return protectionMeasureDAO.getProtectionMeasureBySeason(year);
    }

    /**
     * 보호대책 정보를 DB에 저장한다.
     */
    public ProtectionMeasureVO storeProtectionMeasure(ProtectionMeasureVO protectionMeasureVO) {
        protectionMeasureVO.setStatus(ProtectionMeasureStatus.STAGED);
        int storedProtectionMeasureId = protectionMeasureDAO.storeProtectionMeasure(protectionMeasureVO);
        protectionMeasureVO.setId(storedProtectionMeasureId);
        return protectionMeasureVO;
    }

    /**
     * 보호대책 정보를 DB에 저장하고, 보호지침파일을 업로드한다.
     */
    public void storeProtectionMeasure(ProtectionMeasureVO protectionMeasureVO, HttpServletRequest httpServletRequest) throws Exception {
        ProtectionMeasureVO storedProtectionMeasureVO = storeProtectionMeasure(protectionMeasureVO);
        if (commonUtil.hasFileOnRequest(httpServletRequest)) {
            FileVO uploadedFile = uploadProtectionMeasureGuideFile(httpServletRequest);
            storeProtectionMeasureGuideFile(storedProtectionMeasureVO.getId(), uploadedFile);
        }
    }

    /**
     * 보호대책 DB 정보를 수정한다.
     */
    public ProtectionMeasureVO updateProtectionMeasure(ProtectionMeasureVO protectionMeasureVO) {
        return protectionMeasureVO;
    }

    /**
     * 보호대책 DB 정보를 수정하고, 보호지침파일을 갱신한다.
     */
    public void updateProtectionMeasure(ProtectionMeasureVO protectionMeasureVO, HttpServletRequest httpServletRequest) throws Exception {
        ProtectionMeasureVO updatedProtectionMeasureVO = updateProtectionMeasure(protectionMeasureVO);
        FileVO uploadedFile = uploadProtectionMeasureGuideFile(httpServletRequest);
        destroyProtectionMeasureGuideFile(updatedProtectionMeasureVO.getId());
        updateProtectionMeasureGuideFile(updatedProtectionMeasureVO.getId(), uploadedFile);
    }

    /*===[ 보호대책 중점과제 ]========================================================================================*/
    /*================================================================================================================*/

    /**
     * 해당 중점과제 노드의 설정 권한을 가지고 있는 여부를 확인한다.
     * @param taskId 중점과제 식별번호
     */
    public boolean hasProtectionMeasureTaskManagePermission(int taskId) {
        // @TODO 코드구현 해야 됨
        return true;
    }

    /**
     * 중점과제 노드 식별번호를 이용해 해당 중점과제의 정보를 가지고온다.
     * @param taskId 중점과제 노드 식별번호
     * @return ProtectionMeasureTaskVO
     */
    public ProtectionMeasureTaskVO getProtectionMeasureTask(int taskId) {
        return protectionMeasureDAO.getProtectionMeasureTask(taskId);
    }

    /**
     * 로그인한 사용자의 권한을 분석해 허가된 중점과제 업무만 전달해준다.
     * @param season 연도 정보
     */
    public List<ProtectionMeasureTaskVO> getPermittedProtectionMeasureTasksBySeason(int season) {

        List<ProtectionMeasureTaskVO> returnTaskList = new ArrayList<ProtectionMeasureTaskVO>();
        Set<String> allParentIdsOfReturnTaskSet = new HashSet<String>();

        List<ProtectionMeasureTaskVO> allTaskListOnSeason = getProtectionMeasureTaskBySeason(season);

        LoginVO authenticatedUser = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        if (hasProtectionMeasureManagePermission()) {
            returnTaskList = allTaskListOnSeason;
        } else {

            for (ProtectionMeasureTaskVO protectionMeasureTaskVO : allTaskListOnSeason) {
                String workerKeys = protectionMeasureTaskVO.getWorkerKeysCascade();

                if (workerKeys == null) continue;

                String[] workerkeyArray = workerKeys.split(",");
                List<String> workerKeyList = Arrays.asList(workerkeyArray);

                if (workerKeyList.contains(authenticatedUser.getuum_usr_key()) || allParentIdsOfReturnTaskSet.contains(String.valueOf(protectionMeasureTaskVO.getId()))) {
                    returnTaskList.add(protectionMeasureTaskVO);

                    String parentIds = protectionMeasureTaskVO.getParentIds();

                    if (parentIds != null) {
                        allParentIdsOfReturnTaskSet.addAll(Arrays.asList(parentIds.split(",")));
                    }
                }
            }

        }

        return returnTaskList;
    }

    public List getProtectionMeasureTaskByProtectionMeasureId(int protectionMeasureId) {
        return protectionMeasureDAO.getProtectionMeasureTaskListByProtectionMeasureId(protectionMeasureId);
    }

    /**
     * 해당 연도의 중점과제 노드 목록을 가지고 온다.
     * @param season 연도
     */
    public List getProtectionMeasureTaskBySeason(int season) {
        return protectionMeasureDAO.getProtectionMeasureTaskListBySeason(season);
    }

    /**
     * 중점과제 정보를 DB에 저장한다.
     */
    public int storeProtectionMeasureTask(ProtectionMeasureTaskVO protectionMeasureTaskVO) {
        return protectionMeasureDAO.storeProtectionMeasureTask(protectionMeasureTaskVO);
    }

    /**
     * 중점과제 DB 정보를 수정한다.
     */
    public ProtectionMeasureTaskVO updateProtectionMeasureTask(ProtectionMeasureTaskVO protectionMeasureTaskVO) {
        protectionMeasureDAO.updateProtectionMeasureTask(protectionMeasureTaskVO);
        return protectionMeasureDAO.getProtectionMeasureTask(protectionMeasureTaskVO.getId());
    }

    /**
     * 중점과제를 소프트 삭제한다.
     * @param taskId 중점과제 식별번호
     */
    public int deleteProtectionMeasureTask(int taskId) {
        return protectionMeasureDAO.deleteProtectionMeasureTask(taskId);
    }

    public List<EgovMap> getCategoryListOfUser(Map map) {
        return protectionMeasureDAO.getCategoryListOfUser(map);
    }

    public List<EgovMap> getCategoryListOfLoggedInUser(String season) {
        if (season.length() == 2) {
            season = "20" + season;
        }
        Map<String, String> map = new HashMap<>();
        map.put("manCyl", season);
        map.put("userKey", (String) httpServletRequest.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        return protectionMeasureDAO.getCategoryListOfUser(map);
    }

    public List<EgovMap> getTaskListOfUser(Map map) {
        List<EgovMap> taskList = protectionMeasureDAO.getTaskListOfUser(map);

        Map<String, ProtectionMeasureStatus> statusMap = new HashMap<>();
        for (ProtectionMeasureStatus status : ProtectionMeasureStatus.values()) {
            statusMap.put(status.name(), status);
        }

        for (EgovMap egovMap : taskList) {
            ProtectionMeasureStatus status = statusMap.get(egovMap.get("status"));
            egovMap.put("statusText", status.getTitle());
        }
        return taskList;
    }

    /*===[ 보호대책 중점과제 담당자 ]=================================================================================*/
    /*================================================================================================================*/

    /**
     * 해당 중점과제의 담당자 정보를 가지고 온다.
     * @param taskId 중점과제 노드 식별번호
     */
    public List<ProtectionMeasureTaskWorkerVO> getProtectionMeasureTaskWorkerListByTaskId(int taskId) {
        ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO = new ProtectionMeasureTaskWorkerVO();
        protectionMeasureTaskWorkerVO.setTaskId(taskId);
        return protectionMeasureDAO.getProtectionMeasureTaskWorkerList(protectionMeasureTaskWorkerVO);
    };

    /**
     * 해당 중점과제의 담당자 정보를 저장한다.
     * @param taskId 중점과제 노드 식별번호
     * @param userKeys 담당자 식별번호들
     */
    public void saveProtectionMeasureTaskWorker(int taskId, List<String> userKeys) {
        ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO = new ProtectionMeasureTaskWorkerVO();
        protectionMeasureTaskWorkerVO.setTaskId(taskId);

        deleteProtectionMeasureTaskWorkerOfTask(protectionMeasureTaskWorkerVO);

        for (String userKey : userKeys) {
            protectionMeasureTaskWorkerVO.setUserKey(Integer.parseInt(userKey));
            storeProtectionMeasureTaskWorker(protectionMeasureTaskWorkerVO);
        }
    }

    /**
     * 담당자 정보를 저장한다.
     */
    public void storeProtectionMeasureTaskWorker(ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO) {
        protectionMeasureDAO.storeProtectionMeasureTaskWorker(protectionMeasureTaskWorkerVO);
    }

    /**
     * 해당 중점과제의 담당자 정보를 삭제한다.
     */
    public void deleteProtectionMeasureTaskWorkerOfTask(ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO) {
        protectionMeasureDAO.deleteProtectionMeasureTaskWorker(protectionMeasureTaskWorkerVO);
    }

    public void deleteProtectionMeasureTaskWorkerOfTask(int taskId) {
        ProtectionMeasureTaskWorkerVO protectionMeasureTaskWorkerVO = new ProtectionMeasureTaskWorkerVO();
        protectionMeasureTaskWorkerVO.setTaskId(taskId);

        protectionMeasureDAO.deleteProtectionMeasureTaskWorker(protectionMeasureTaskWorkerVO);
    }

    /**
     * 해당 중점과제 노드의 담당자 관리 권한을 가지고 있는 여부를 확인한다.
     * @param taskId 중점과제 식별번호
     */
    public boolean hasProtectionMeasureTaskWorkerManagePermission(int taskId) {
        // @TODO 코드구현 해야 됨
        return true;
    }

}
