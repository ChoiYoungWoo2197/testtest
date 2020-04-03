package com.uwo.isms.web;

import com.uwo.isms.common.CommonConfig;
import com.uwo.isms.common.Node;
import com.uwo.isms.domain.*;
import com.uwo.isms.domain.enums.ProtectionMeasureTaskNodeType;
import com.uwo.isms.domain.enums.ProtectionMeasureTaskStatus;
import com.uwo.isms.domain.enums.ProtectionMeasureUserMode;
import com.uwo.isms.service.CommonService;
import com.uwo.isms.service.ProtectionMeasureService;
import com.uwo.isms.util.CommonUtil;
import com.uwo.isms.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ProtectionMeasureController extends SupportController {

    @Autowired
    private ProtectionMeasureService protectionMeasureService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CommonService commonService;

    /**
     * 기반시설 보호대책 관리자 페이지 (/inspt/FM-INSPT005.do)
     * @param inputtedYear 연도 정보
     */
    @RequestMapping(value = "/inspt/FM-INSPT005.do", method = RequestMethod.GET)
    public String adminPage(@RequestParam(value = "year", required = false, defaultValue = "-1") int inputtedYear,
                                        ModelMap map, HttpServletRequest httpServletRequest) throws Exception {

        if (inputtedYear == -1)
            inputtedYear = commonUtil.getCurrentYear();

        ProtectionMeasureUserMode mode = ProtectionMeasureUserMode.MANAGER;
        List<EgovMap> mainCycleList = commonService.getAllMainCycleList();

        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ProtectionMeasureVO protectionMeasure = protectionMeasureService.getProtectionMeasureByYear(inputtedYear);

        String protectionMeasureJson = commonUtil.getJsonFromObject(protectionMeasure);

        if (protectionMeasure != null) {
            map.addAttribute("protectionMeasureGuideFile",
                    protectionMeasureService.getProtectionMeasureGuideFile(protectionMeasure.getId()));
        }

        map.addAttribute("mode", mode);
        map.addAttribute("loginVO", loginVO);
        map.addAttribute("currentYear", commonUtil.getCurrentYear());
        map.addAttribute("mainCycleList", mainCycleList);
        map.addAttribute("selectedYear", inputtedYear);
        map.addAttribute("protectionMeasure", protectionMeasure);
        map.addAttribute("protectionMeasureJson", protectionMeasureJson);

        return protectionMeasure == null ? "FM-INSPT005_CREATE" : "FM-INSPT005";
    }

    /**
     * 기반시설 보호대책 생성 요청
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_PTM_STORE.do", method = RequestMethod.POST)
    public String storeProtectionMeasure(ProtectionMeasureVO protectionMeasureVO, HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse) throws Exception {
        String title = (protectionMeasureVO.getSeason() + 1) + "년도 기반시설 보호대책";
        protectionMeasureVO.setTitle(title);
        protectionMeasureService.storeProtectionMeasure(protectionMeasureVO, httpServletRequest);
        return "redirect:/inspt/FM-INSPT005.do?year=" + protectionMeasureVO.getSeason();
    }

    /**
     * 기반시설 보호대책 중점과제 정보를 표 형태로 보여준다.
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_RESULT_POPUP.do", method = RequestMethod.POST)
    public String showProtectionMeasureTaskTable(@RequestParam(value = "id") int protectionMeasureId, ModelMap map) {

        ProtectionMeasureVO protectionMeasureVO = protectionMeasureService.getProtectionMeasureById(protectionMeasureId);
        List<ProtectionMeasureTaskVO> protectionMeasureTaskVOList
                = protectionMeasureService.getProtectionMeasureTaskByProtectionMeasureId(protectionMeasureId);

        List<FileVO> proFileVOList = protectionMeasureService.getProtectionMeasureTaskFilesByProtectionMeasureId(protectionMeasureId);

        Node<ProtectionMeasureTaskVO> rootNode = new Node<>(new ProtectionMeasureTaskVO());
        Map<Integer, Node> allNodes = new HashMap<>();

        for (ProtectionMeasureTaskVO protectionMeasureTaskVO : protectionMeasureTaskVOList) {

            List<FileVO> files = new ArrayList<>();
            for (FileVO fileVO : proFileVOList) {
                if (Integer.parseInt(fileVO.getUmf_con_key()) == protectionMeasureTaskVO.getId()) {
                    files.add(fileVO);
                }
            }
            protectionMeasureTaskVO.setFiles(files);

            Node<ProtectionMeasureTaskVO> node = new Node<>(protectionMeasureTaskVO);
            allNodes.put(protectionMeasureTaskVO.getId(), node);

            int parentNodeId = protectionMeasureTaskVO.getParentNodeId();

            if (parentNodeId == 0) {
                rootNode.addChild(node);
            } else {
                Node parentNode = allNodes.get(parentNodeId);
                parentNode.addChild(node);
            }
        }

        map.addAttribute("protectionMeasure", protectionMeasureVO);
        map.addAttribute("protectionMeasureTaskList", protectionMeasureTaskVOList);
        map.addAttribute("rootNode", rootNode);

        return "FM-INSPT005_TABLE_POPUP";
    }

    /**
     * 기반시설 보호대책 중점과제 정보를 표 형태로 보여준다.
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_REPORT_POPUP.do", method = RequestMethod.POST)
    public String showProtectionMeasureTaskReport(@RequestParam(value = "id") int protectionMeasureId, ModelMap map) {

        ProtectionMeasureVO protectionMeasureVO = protectionMeasureService.getProtectionMeasureById(protectionMeasureId);
        List<ProtectionMeasureTaskVO> protectionMeasureTaskVOList
                = protectionMeasureService.getProtectionMeasureTaskByProtectionMeasureId(protectionMeasureId);

        List<FileVO> proFileVOList = protectionMeasureService.getProtectionMeasureTaskFilesByProtectionMeasureId(protectionMeasureId);

        Node<ProtectionMeasureTaskVO> rootNode = new Node<>(new ProtectionMeasureTaskVO());
        Map<Integer, Node> allNodes = new HashMap<>();

        for (ProtectionMeasureTaskVO protectionMeasureTaskVO : protectionMeasureTaskVOList) {

            List<FileVO> files = new ArrayList<>();
            for (FileVO fileVO : proFileVOList) {
                if (Integer.parseInt(fileVO.getUmf_con_key()) == protectionMeasureTaskVO.getId()) {
                    files.add(fileVO);
                }
            }
            protectionMeasureTaskVO.setFiles(files);

            Node<ProtectionMeasureTaskVO> node = new Node<>(protectionMeasureTaskVO);
            allNodes.put(protectionMeasureTaskVO.getId(), node);

            int parentNodeId = protectionMeasureTaskVO.getParentNodeId();

            if (parentNodeId == 0) {
                rootNode.addChild(node);
            } else {
                Node parentNode = allNodes.get(parentNodeId);
                parentNode.addChild(node);
            }
        }

        map.addAttribute("protectionMeasure", protectionMeasureVO);
        map.addAttribute("protectionMeasureTaskList", protectionMeasureTaskVOList);
        map.addAttribute("rootNode", rootNode);

        return "FM-INSPT005_REPORT_POPUP";
    }

    @RequestMapping(value = "/inspt/FM-INSPT005_RESULT_EXCEL.do", method = RequestMethod.GET)
    public ModelAndView fmInspt005ResultExcelGet(@RequestParam(value = "id") int protectionMeasureId) {
        ModelAndView modelAndView = new ModelAndView("inspt005ExcelView");

        ProtectionMeasureVO protectionMeasureVO = protectionMeasureService.getProtectionMeasureById(protectionMeasureId);
        List<ProtectionMeasureTaskVO> protectionMeasureTaskVOList
                = protectionMeasureService.getProtectionMeasureTaskByProtectionMeasureId(protectionMeasureId);

        Node<ProtectionMeasureTaskVO> rootNode = new Node<>(new ProtectionMeasureTaskVO());
        Map<Integer, Node> allNodes = new HashMap<>();

        for (ProtectionMeasureTaskVO protectionMeasureTaskVO : protectionMeasureTaskVOList) {
            Node<ProtectionMeasureTaskVO> node = new Node<>(protectionMeasureTaskVO);
            allNodes.put(protectionMeasureTaskVO.getId(), node);
            
            int parentNodeId = protectionMeasureTaskVO.getParentNodeId();

            if (parentNodeId == 0) {
                rootNode.addChild(node);
            } else {
                Node parentNode = allNodes.get(parentNodeId);
                parentNode.addChild(node);
            }
        }

        modelAndView.addObject("protectionMeasure", protectionMeasureVO);
        modelAndView.addObject("protectionMeasureTask", protectionMeasureTaskVOList);
        modelAndView.addObject("rootNode", rootNode);

        return modelAndView;
    }


    /**
     * 기반시설 보호대책 사용자 페이지 (/mwork/FM-MWORK021.do)
     * @param selectedYear 연도 정보
     */
    @RequestMapping(value = "/mwork/FM-MWORK021.do", method = RequestMethod.GET)
    public String workerPage(@RequestParam(value = "year", required = false, defaultValue = "-1") int selectedYear,
                             @RequestParam(value = "category", required = false, defaultValue = "") String selectedCategory,
                                        ModelMap modelMap, HttpServletRequest httpServletRequest) throws Exception {
    	
    	List<EgovMap> mainCycleList = commonService.getAllMainCycleList();
    	
    	if (selectedYear == -1) {
        	//selectedYear = commonUtil.getCurrentYear();
        	selectedYear = protectionMeasureService.getLastSeason();
        }
            
        ProtectionMeasureVO protectionMeasure = protectionMeasureService.getProtectionMeasureByYear(selectedYear);
        FileVO protectionMeasureGuideFile = null;
        List<EgovMap> categoryList = null;

        if (protectionMeasure != null) {
            protectionMeasureGuideFile = protectionMeasureService.getProtectionMeasureGuideFile(protectionMeasure.getId());
            categoryList = protectionMeasureService.getCategoryListOfLoggedInUser(String.valueOf(selectedYear));
        }

        Map<String, Object> queryParameterMap2 = new HashMap<>();
        queryParameterMap2.put("userKey", (String) httpServletRequest.getSession().getAttribute(CommonConfig.SES_USER_KEY));
        queryParameterMap2.put("manCyl", selectedYear);
        queryParameterMap2.put("categoryId", selectedCategory);
        List<EgovMap> taskList = protectionMeasureService.getTaskListOfUser(queryParameterMap2);

        modelMap.addAttribute("selectedYear", selectedYear);
        modelMap.addAttribute("selectedCategory", selectedCategory);
        modelMap.addAttribute("mainCycleList", mainCycleList);
        modelMap.addAttribute("protectionMeasure", protectionMeasure);
        modelMap.addAttribute("protectionMeasureGuideFile", protectionMeasureGuideFile);
        modelMap.addAttribute("categoryList", categoryList);
        modelMap.addAttribute("taskList", taskList);

        return "FM-MWORK021";
    }

    @RequestMapping(value = "/mwork/FM-MWORK021_VIEW_POPUP.do", method = RequestMethod.GET)
    public String taskViewPage(@RequestParam(value = "task") int taskId,
                           ModelMap modelMap) {

        ProtectionMeasureTaskVO protectionMeasureTaskVO
                = protectionMeasureService.getProtectionMeasureTask(taskId);

        modelMap.addAttribute("task", protectionMeasureTaskVO);
        
        return "FM-MWORK021_VIEW_POPUP";
    }

    @RequestMapping(value = "/mwork/FM-MWORK021_CREATE_POPUP.do", method = RequestMethod.GET)
    public String taskCreatePage(@RequestParam(value = "category") int categoryId,
                                @RequestParam(value = "protectionMeasure") int protectionMeasureId,
                           ModelMap modelMap) {

        modelMap.addAttribute("categoryId", categoryId);
        modelMap.addAttribute("protectionMeasureId", protectionMeasureId);

        return "FM-MWORK021_CREATE_POPUP";
    }

    /**
     * 기반시설 보호대책 중점과제 생성 요청
     */
    @RequestMapping(value = "/mwork/FM-MWORK021_STORE.do", method = RequestMethod.POST)
    public String storeProtectionMeasureTaskSync(HttpSession session, ProtectionMeasureTaskVO protectionMeasureTaskVO,
                                                 HttpServletResponse httpServletResponse) throws Exception {

        String loggedInUserKey = (String) session.getAttribute(CommonConfig.SES_USER_KEY);

        protectionMeasureTaskVO.setNodeType(ProtectionMeasureTaskNodeType.TASK);
        protectionMeasureTaskVO.setStatus(ProtectionMeasureTaskStatus.INITIATED);
        protectionMeasureTaskVO.setUserKey(Integer.parseInt(loggedInUserKey));

        int storedProtectionMeasureTaskId = protectionMeasureService.storeProtectionMeasureTask(protectionMeasureTaskVO);

        return "redirect:/mwork/FM-MWORK021_VIEW_POPUP.do?task=" + storedProtectionMeasureTaskId;
    }

    /**
     * 기반시설 보호대책 중점과제 정보 수정 요청
     */
    @RequestMapping(value = "/mwork/FM-MWORK021_UPDATE.do", method = RequestMethod.POST)
    public String updateProtectionMeasureTaskSync(ProtectionMeasureTaskVO protectionMeasureTaskVO,
                                                  HttpServletRequest httpServletRequest) throws Exception {
        String referer = httpServletRequest.getHeader("Referer");
        ProtectionMeasureTaskVO updatedProtectionMeasureTaskVO
                = protectionMeasureService.updateProtectionMeasureTask(protectionMeasureTaskVO);
        return "redirect:" + referer;
    }

    /**
     * 기반시설 보호대책 정보 수정 요청
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_UPDATE.do", method = RequestMethod.POST)
    public String updateProtectionMeasure(ProtectionMeasureVO protectionMeasureVO, HttpServletRequest httpServletRequest,
                                          HttpServletResponse httpServletResponse) throws Exception {
        if ( protectionMeasureService.hasProtectionMeasureManagePermission()) {
            protectionMeasureService.updateProtectionMeasure(protectionMeasureVO, httpServletRequest);
        }

        return "redirect:/inspt/FM-INSPT005.do?year=" + protectionMeasureVO.getSeason();
    }

    /**
     * 기반시설 보호대책 중점과제 목록 요청
     * 연도(시즌) 정보를 기준으로 목록을 조회한다.
     * @param season 연도 정보
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_TASK_LIST.do", method = RequestMethod.GET)
    public ModelAndView showProtectionMeasureTaskListBySeason(@RequestParam(value = "season") int season, HttpSession httpSession) {
        ModelAndView modelAndView = makeJsonResponse();

        modelAndView.addObject("taskList",
                protectionMeasureService.getPermittedProtectionMeasureTasksBySeason(season));
        modelAndView.addObject("permittedTaskList", "");

        return modelAndView;
    }

    /**
     * 기반시설 보호대책 중점과제 정보 조회 요청
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_TASK_SHOW.do", method = RequestMethod.GET)
    public ModelAndView showProtectionMeasureTaskList(ProtectionMeasureTaskVO protectionMeasureTaskVO) {
        ModelAndView jsonResponse = makeJsonResponse();

        ProtectionMeasureTaskVO protectionMeasureTask
                = protectionMeasureService.getProtectionMeasureTask(protectionMeasureTaskVO.getId());

        List workerList = null;

        if (protectionMeasureTask.getNodeType() == ProtectionMeasureTaskNodeType.TASK) {
            workerList = protectionMeasureService.getProtectionMeasureTaskWorkerListByTaskId(protectionMeasureTask.getId());
        }

        jsonResponse.addObject("task", protectionMeasureTask);
        jsonResponse.addObject("workerList", workerList);

        return jsonResponse;
    }

    /**
     * 기반시설 보호대책 중점과제 생성 요청
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_TASK_STORE.do", method = RequestMethod.POST)
    public ModelAndView storeProtectionMeasureTask(HttpSession session, ProtectionMeasureTaskVO protectionMeasureTaskVO,
                                                   HttpServletResponse httpServletResponse) throws Exception {

//        if ( ! protectionMeasureService.hasProtectionMeasureTaskManagePermission(protectionMeasureTaskVO.getParentNodeId()))
//            commonUtil.redirectTo403Page(httpServletResponse);

        String loggedInUserKey = (String) session.getAttribute(CommonConfig.SES_USER_KEY);

        protectionMeasureTaskVO.setStatus(ProtectionMeasureTaskStatus.INITIATED);
        protectionMeasureTaskVO.setUserKey(Integer.parseInt(loggedInUserKey));

        int storedProtectionMeasureTaskId = protectionMeasureService.storeProtectionMeasureTask(protectionMeasureTaskVO);

        protectionMeasureTaskVO.setId(storedProtectionMeasureTaskId);

        return makeJsonResponse().addObject("task", protectionMeasureTaskVO);
    }



    /**
     * 기반시설 보호대책 중점과제 정보 수정 요청
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_TASK_UPDATE.do", method = RequestMethod.POST)
    public ModelAndView updateProtectionMeasureTask(ProtectionMeasureTaskVO protectionMeasureTaskVO,
                                                    HttpServletResponse httpServletResponse) throws Exception {

//        if ( ! protectionMeasureService.hasProtectionMeasureTaskManagePermission(protectionMeasureTaskVO.getId()))
//            commonUtil.redirectTo403Page(httpServletResponse);

        ProtectionMeasureTaskVO updatedProtectionMeasureTaskVO
                = protectionMeasureService.updateProtectionMeasureTask(protectionMeasureTaskVO);
        return makeJsonResponse().addObject("tast", updatedProtectionMeasureTaskVO);
    }

    /**
     * 기반시설 보호대책 중점과제 삭제
     * @param taskId 중점과제 식별번호
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_TASK_DELETE.do", method = RequestMethod.POST)
    public ModelAndView deleteProtectionMeasureTask(@RequestParam(value = "id") int taskId,
                                                    HttpServletResponse httpServletResponse) throws Exception {

//        if ( ! protectionMeasureService.hasProtectionMeasureTaskManagePermission(taskId))
//            commonUtil.redirectTo403Page(httpServletResponse);

        return makeJsonResponse().addObject("deleteCount", protectionMeasureService.deleteProtectionMeasureTask(taskId));
    }

    /**
     * 기반시설 보호대책 담당자 정보 수정 요청
     * @param taskId 중점과제 노드 식별번호
     * @param userKeys 담당자 식별번호들
     */
    @RequestMapping(value = "/inspt/FM-INSPT005_WORKER_UPDATE.do", method = RequestMethod.POST)
    public ModelAndView updateProtectionMeasureTaskWorker(@RequestParam(value = "taskId") String taskId,
                                                          @RequestParam(value = "userKeys[]", required = false) String[] userKeys,
                                                          HttpServletResponse httpServletResponse) throws Exception {

        if (userKeys == null) {
            protectionMeasureService.deleteProtectionMeasureTaskWorkerOfTask(Integer.parseInt(taskId));
        } else {
            protectionMeasureService.saveProtectionMeasureTaskWorker(Integer.parseInt(taskId), Arrays.asList(userKeys));
        }

        return makeJsonResponse().addObject("taskId", taskId);
    }

    /**
     * 기반시설 보호대책 보호지침 파일 요청
     * @param protectionMeasureId 기반시설 보호대책 식별번호
     */
    @RequestMapping(value = "/protectionMeasure/file/show.do", method = RequestMethod.GET)
    public ModelAndView protectionMeasureFileShow(@RequestParam(value = "id") String protectionMeasureId) throws Exception {
        return makeJsonResponse().addObject("file",
                protectionMeasureService.getProtectionMeasureGuideFile(Integer.parseInt(protectionMeasureId)));
    }

    /**
     * 기반시설 보호대책 중점과제 관련 파일 목록 요청
     * @param taskId 기반시설 보호대책 중점과제 식별번호
     */
    @RequestMapping(value = "/protectionMeasure/task/file/list.do", method = RequestMethod.GET)
    public ModelAndView protectionMeasureTaskFileList(@RequestParam(value = "id") String taskId) throws Exception {
        return makeJsonResponse().addObject("files",
                protectionMeasureService.getProtectionMeasureTaskFiles(Integer.parseInt(taskId)));
    }

    /**
     * 기반시설 보호대책 중점과제 파일 저장, 삭제
     * @param taskId 중점과제 식별번호
     * @param deletedFiles 삭제할 파일의 식별번호들
     */
    @RequestMapping(value = "/protectionMeasure/task/file/update.do", method = RequestMethod.POST)
    public ModelAndView protectionMeasureTaskFileUpdate(@RequestParam(value = "conKey") String taskId,
                                                        @RequestParam(value = "deletedFiles[]", required = false) String[] deletedFiles,
                                                        HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse) throws Exception {

        if (deletedFiles != null && deletedFiles.length > 0) {
            protectionMeasureService.destroyProtectionMeasureTaskFiles(deletedFiles);
        }

        protectionMeasureService.saveProtectionMeasureTaskFiles(Integer.parseInt(taskId), httpServletRequest);

        return makeJsonResponse().addObject("deletedFiles", deletedFiles);
    }

}
