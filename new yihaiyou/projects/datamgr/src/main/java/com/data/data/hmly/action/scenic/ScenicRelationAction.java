//package com.data.data.hmly.action.scenic;
//
//import com.data.data.hmly.action.FrameBaseAction;
//import com.data.data.hmly.service.base.ActionResult;
//import com.data.data.hmly.service.base.ResultModel;
//import com.data.data.hmly.service.base.constants.BizConstants;
//import com.data.data.hmly.service.base.persistent.PageDto;
//import com.data.data.hmly.service.base.util.HttpUtil;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Controller
//@RequestMapping("/mgrer/scenicRelation")
//public class ScenicRelationAction extends FrameBaseAction{
//    @Resource
//    private ScenicService service;
//    @Resource
//    private ScenicExtendService scenicOtherService;
//    @Resource
//    private DataScenicService dataScenicService;
//    @Resource
//    private DataScenicRelationService scenicRelationService;
//    @Resource
//    private DataScenicRelationDetailService scenicRelationDetailsService;
//    @Resource
//    private SolrScenicService solrScenicDao;
//
//    @RequestMapping(value = "/toRelationList", method = RequestMethod.GET)
//    public String toRelationList() {
//        return "mgrer/scenic/scenicRelations";
//    }
//
//    @RequestMapping("/saveRelation")
//    @Transactional
//    public
//    @ResponseBody
//    ActionResult submitScenicRelation(DataScenicRelationEntity relation) throws IOException {
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("scenicId", relation.getScenicIds());
//        paramMap.put("source", relation.getSource());
//        Object old = scenicRelationService.one(paramMap);
//        if (old != null) {
//            relation.setStatus(2);
//            scenicRelationService.update(relation);
//        } else {
//            scenicRelationService.insert(relation);
//        }
//        if (reinitDetail(relation.getScenicIds())) {
//            etl(relation.getScenicIds());
//            return ActionResult.createSuccess();
//        } else {
//            return ActionResult.createFail(-1);
//        }
//    }
//
////    @RequestMapping("/initRelations")
////    @Transactional
////    public
////    @ResponseBody
////    ActionResult initRelations(ScenicRelation relation) throws IOException {
////        Map<String, Object> paramMap = new HashMap<String, Object>();
////        paramMap.put("status", 1);
////        List<Map<String, String>> scenicInfos = service.listName2(paramMap);
////        for (Map<String, String> s : scenicInfos) {
////            reinitDetail(Integer.parseInt(s.get("scenicId")));
////        }
////        return ActionResult.createSuccess();
////    }
//
//    private Boolean reinitDetail(int scenicId) {
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("scenicId", scenicId);
//        scenicRelationDetailsService.delete(paramMap);//��վɵ�relation
//        List<DataScenicRelationEntity> relations = scenicRelationService.list(paramMap);
//
//        DataScenicRelationDetailsEntity details = new DataScenicRelationDetailsEntity();
//        details.setScenicIds(scenicId);
//        for (DataScenicRelationEntity r : relations) {
//            int sourceId = r.getSourceId();
//            details = GetRelationDetails(details, sourceId);
//        }
//        scenicRelationDetailsService.insert(details);
//        return true;
//    }
//
//
//    @RequestMapping("/delRelation")
//    @Transactional
//    public
//    @ResponseBody
//    ActionResult deleteScenicRelation(int scenic_id, int source_id) throws IOException {
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("scenicId", scenic_id);
//        paramMap.put("sourceId", source_id);
//        Object old = scenicRelationService.one(paramMap);
//        if (old != null) {
//            scenicRelationService.delete(paramMap);
//        }
//        return ActionResult.createSuccess();
//    }
//
//
//    @RequestMapping("/getSimilar")
//    public String getSimilar(int sid, String sname, Model model) {
////        Map<String, Object> paramMap = new HashMap<String, Object>();
//        List<Scenic> scenics = null;
//        try {
//            scenics = solrScenicDao.findScenicByTitle(sname);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        List<Scenic> ctrip = new ArrayList<Scenic>();
//        List<Scenic> cncn = new ArrayList<Scenic>();
//        List<Scenic> tuniu = new ArrayList<Scenic>();
//        List<Scenic> ly = new ArrayList<Scenic>();
//        List<Scenic> mfw = new ArrayList<Scenic>();
//        for (Scenic s : scenics) {
//            String source = s.getData_source();
//            if ("ctrip".equals(source)) {
//                ctrip.add(s);
//            } else if ("cncn".equals(source)) {
//                cncn.add(s);
//            } else if ("tuniu".equals(source)) {
//                tuniu.add(s);
//            } else if ("ly".equals(source)) {
//                ly.add(s);
//            } else {
//                mfw.add(s);
//            }
//        }
////        model.addAttribute("scenics", scenics);
//        model.addAttribute("sid", sid);
//        model.addAttribute("ctrip", ctrip);
//        model.addAttribute("cncn", cncn);
//        model.addAttribute("tuniu", tuniu);
//        model.addAttribute("ly", ly);
//        model.addAttribute("mfw", mfw);
//        return "mgrer/scenic/scenicSimilar";
//    }
//
//
//    /**
//     * ��ѯ�����б�
//     *
//     * @return ScenicRelation
//     */
//    @RequestMapping("/listRelation")
//    public
//    @ResponseBody
//    ResultModel<DataScenicRelationEntity> listRelation(final HttpServletRequest request) {
//        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
//        Object relatied = paramMap.get("relatied");
//        if (relatied != null) {
//            int num = Integer.parseInt(relatied.toString());
//            if (num >= 0) {
//                List<DataScenicRelationEntity> relations = scenicRelationService.listWithGroup(paramMap);
//                List<Long> _ids = new ArrayList<Long>();
//                for (DataScenicRelationEntity r : relations) {
//                    _ids.add(Long.parseLong(Integer.toString(r.getScenicIds())));
//                }
//                paramMap.put("ids", _ids);
//            }
//        }
//        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
//        paramMap.put("status", 1);
//        List<ScenicInfo> infos = service.list(paramMap);
////        String ids = "";
//        List<Long> _ids = new ArrayList<Long>();
//        for (ScenicInfo s : infos) {
//            _ids.add(Long.parseLong(s.getId().toString()));
//        }
//
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("ids", _ids);
//        List<DataScenicRelationEntity> relations = scenicRelationService.list(param);
//        Map<Long, DataScenicRelationEntity> map = new HashMap<Long, DataScenicRelationEntity>();
//        for (DataScenicRelationEntity r : relations) {
//            long sid = r.getScenicIds();
//            DataScenicRelationEntity relation = new DataScenicRelationEntity();
//            if (map.containsKey(sid)) {
//                relation = map.get(sid);
//                map.remove(sid);
//            }
//            String source = r.getSource();
//            if ("ctrip".equals(source)) {
//                relation.setCtripId(r.getSourceId());
//                relation.setCtripName(r.getSourceName());
//                relation.setCtripUrl(r.getSourceUrl());
//            } else if ("cncn".equals(source)) {
//                relation.setCncnId(r.getSourceId());
//                relation.setCncnName(r.getSourceName());
//                relation.setCncnUrl(r.getSourceUrl());
//            } else if ("tuniu".equals(source)) {
//                relation.setTuniuId(r.getSourceId());
//                relation.setTuniuName(r.getSourceName());
//                relation.setTuniuUrl(r.getSourceUrl());
//            } else if ("ly".equals(source)) {
//                relation.setLyId(r.getSourceId());
//                relation.setLyName(r.getSourceName());
//                relation.setLyUrl(r.getSourceUrl());
//            } else {
//                relation.setMfwId(r.getSourceId());
//                relation.setMfwName(r.getSourceName());
//                relation.setMfwUrl(r.getSourceUrl());
//            }
//            map.put(sid, relation);
//        }
//        List<DataScenicRelationEntity> results = new ArrayList<DataScenicRelationEntity>();
//
//        for (ScenicInfo s : infos) {
//            DataScenicRelationEntity relation = new DataScenicRelationEntity();
//            if (map.containsKey(s.getId())) {
//                relation = map.get(s.getId());
//            }
//            relation.setScenicIds(s.getId());
//            relation.setScenicName(s.getName());
//            results.add(relation);
//        }
//
//        ResultModel<DataScenicRelationEntity> result = new ResultModel<DataScenicRelationEntity>();
//        int count = service.count(paramMap);
//        result.setTotal(count);
//        if (count > 0) {
//            result.setRows(results);
//        }
//        return result;
//    }
//
//    /**
//     * ��ѯ�����б�
//     *
//     * @return ScenicRelation
//     */
//    @RequestMapping("/listRelationWithOutGroup")
//    public
//    @ResponseBody
//    ResultModel<DataScenicRelationEntity> listRelationWithOutGroup(final HttpServletRequest request) {
//        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
//        Object relatied = paramMap.get("relatied");
//        if (relatied != null) {
//            int num = Integer.parseInt(relatied.toString());
//            if (num >= 0) {
//                List<DataScenicRelationEntity> relations = scenicRelationService.listWithGroup(paramMap);
//                List<Integer> _ids = new ArrayList<Integer>();
//                for (DataScenicRelationEntity r : relations) {
//                    _ids.add(r.getScenicIds());
//                }
//                paramMap.put("ids", _ids);
//            }
//        }
//        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
//        paramMap.put("status", 1);
//        List<ScenicInfo> infos = service.list(paramMap);
//        List<Integer> _ids = new ArrayList<Integer>();
//        for (ScenicInfo s : infos) {
//            _ids.add(s.getId());
//        }
//
//        ////By ZZL/2015.10.16 �������¼ܺ�,��������������
////        if(_ids.size() <= 0 | _ids == null){
////        	 ResultModel<ScenicRelation> result = new ResultModel<ScenicRelation>();
////        	 result.setTotal(0);
////        	 result.setRows(null);
////        	 return result;
////        }
//
//        if (_ids.size()==0){
//            return null;
//        }
//
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("ids", _ids);
//        List<DataScenicRelationEntity> relations = scenicRelationService.list(param);
//        ResultModel<DataScenicRelationEntity> result = new ResultModel<DataScenicRelationEntity>();
//        int count = relations.size();
//        result.setTotal(count);
//        if (count > 0) {
//            result.setRows(relations);
//        }
//        return result;
//    }
//
//    /**
//     * ��������ģ����ѯ����
//     *
//     * @return
//     */
//    @RequestMapping(value = "/listName")
//    @ResponseBody
//    public ActionResult getNameInfo(final HttpServletRequest request) throws UnsupportedEncodingException {
//        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
//        paramMap.put("deleteFlag", BizConstants.COMMON_FLAG_FALSE);
//        paramMap.put("isModified", 2);
//        paramMap.put("status", 1);
//        List<Map<String, String>> names = service.listName(paramMap);
//        return ActionResult.createSuccess(names);
//    }
//
//    @RequestMapping("/etl")
//    public
//    @ResponseBody
//    ActionResult etl(long scenicId){
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("scenicId", scenicId);
//        List<DataScenicRelationDetailsEntity> details = scenicRelationDetailsService.list(paramMap);
//        for (DataScenicRelationDetailsEntity d : details) {
//            updateScenicInfo(d);
//            updateScenicOther(d);
//            d.setEtlStatus(1);
//            scenicRelationDetailsService.update(d);
//        }
//        return ActionResult.createSuccess();
//    }
//
//    @RequestMapping("/etls")
//    public
//    @ResponseBody
//    ActionResult etls(final HttpServletRequest request) throws Exception {
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        int pageSize = 30;
//        int page = 0;
//        do {
//            PageDto pageDto = new PageDto(page, pageSize);
//            paramMap.put("pageDto", pageDto);
//            List<DataScenicRelationDetailsEntity> details = scenicRelationDetailsService.list(paramMap);
//            for (DataScenicRelationDetailsEntity d : details) {
//                updateScenicInfo(d);
//                updateScenicOther(d);
//                d.setEtlStatus(1);
//                scenicRelationDetailsService.update(d);
//            }
//        } while ((page += 1) < 2000);
//        return ActionResult.createSuccess();
//    }
//
//    private void updateScenicOther(DataScenicRelationDetailsEntity d) {
//        ScenicExtendEntity scenicOther = scenicOtherService.getByScenicId(Long.parseLong(d.getScenicIds().toString()));
//        ScenicInfo ScenicInfo = service.info(Long.parseLong(d.getScenicIds().toString()));
//
////        if (d.getAddress() > 0) {
////            DataScenic s = dataScenicService.selColumnById(String.valueOf(d.getAddress()), "address");
////            scenicOther.setAddress(replaceAddress(s.getAddress(), s.getDataSource()));
////        }
////
////        if (d.getTelephone() > 0) {
////            DataScenic s = dataScenicService.selColumnById(String.valueOf(d.getTelephone()), "telephone");
////            scenicOther.setTelephone(s.getTelephone());
////        }
//        if (d.getWebsite() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getWebsite()), "website");
//            scenicOther.setWebsite(s.getWebsite());
//        }
//        if (d.getIntroduction() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getIntroduction()), "introduction");
////            scenicOther.setIntroduction(s.getIntroduction());
//            scenicOther.setDescription(s.getIntroduction());
//            ScenicInfo.setIntro(s.getIntroduction());
//        }
//        if (d.getAdvice() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getAdvice()), "advice");
////            scenicOther.setAdvice(s.getAdvice());
//            scenicOther.setHow(s.getAdvice()); // ��ô��<==>���潨�� ???
//        }
//        if (d.getBestTime() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getBestTime()), "bestTime");
////            scenicOther.setBestTime(s.getBestTime());
//            scenicOther.setAdviceTimeDesc(s.getBestTime());
//        }
//        if (d.getNotice() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getNotice()), "notice");
//            scenicOther.setNotice(s.getNotice());
//        }
//        if (d.getRecommendReason() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getRecommendReason()), "recommendReason");
//            scenicOther.setRecommendReason(s.getRecommendReason());
//        }
//
//        ScenicExtendEntity exists = scenicOtherService.selByScenicId(String.valueOf(d.getScenicIds()));
//        if (exists == null) {
////            scenicOther.setId(null);
//            scenicOther.setId(d.getScenicIds());
////            scenicOther.setScenicInfoId(d.getScenicIds());
//            scenicOtherService.insert(scenicOther);
//        } else {
//            scenicOtherService.updateWithDataScenic(scenicOther);
//        }
//    }
//
//    private ScenicInfo updateScenicInfo(DataScenicRelationDetailsEntity d) {
//        ScenicInfo scenicUpdate = new ScenicInfo();
//        ScenicExtendEntity scenicExtendUpdate = new ScenicExtendEntity();
//        ScenicGeoinfoEntity scenicGeoinfoUpdate = new ScenicGeoinfoEntity();
//        scenicUpdate.setId(d.getScenicIds());
//        scenicExtendUpdate.setId(d.getScenicIds());
//        scenicGeoinfoUpdate.setId(d.getScenicIds());
//        if (d.getAddress() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getAddress()), "address");
//            scenicExtendUpdate.setAddress(replaceAddress(s.getAddress(), s.getDataSource()));
//        }
//        if (d.getLatitude() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getLatitude()), "latitude");
////            scenicUpdate.setLatitude(s.getLatitude());
//            scenicGeoinfoUpdate.setBaiduLat(s.getLatitude());
//        }
//        if (d.getLongitude() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getLongitude()), "longitude");
////            scenicUpdate.setLongitude(s.getLongitude());
//            scenicGeoinfoUpdate.setBaiduLng(s.getLongitude());
//        }
//        if (d.getOpenTime() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getOpenTime()), "openTime");
//            scenicExtendUpdate.setOpenTime(s.getOpenTime());
//        }
//        if (d.getAdviceTime() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getAdviceTime()), "adviceTime");
//            String adviceTime = s.getAdviceTime();
//            if (!"".equals(adviceTime)) {
//                int minutes = 0;
//                adviceTime = replaceChzNums(adviceTime);
//                minutes = getMinutes(adviceTime, minutes);
////                scenicUpdate.setAdviceHours(minutes);
////                scenicUpdate.setAdviceTime(adviceTime);
//                scenicExtendUpdate.setAdviceTime(minutes);
//                scenicExtendUpdate.setAdviceTimeDesc(adviceTime);
//
//            }
//        }
//        if (d.getTicket() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getTicket()), "ticket");
//            scenicUpdate.setTicket(s.getTicket());
//        }
//
//        if (d.getBestTime() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getBestTime()), "bestTime");
////            scenicUpdate.setBestTime(s.getBestTime());
//            ////��������ֶ�
//        }
//        if (d.getHow() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getHow()), "how");
////            scenicUpdate.setHow(s.getHow());
//            scenicExtendUpdate.setHow(s.getHow());
//        }
//        if (d.getLevel() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getLevel()), "level");
//            scenicUpdate.setLevel(s.getLevel());
//        }
////        if (d.getRanking() > 0) {
////            DataScenic s = dataScenicService.selColumnById(String.valueOf(d.getRanking()), "ranking");
////            scenicUpdate.setRanking(s.getRanking());
////        } else {
////            scenicUpdate.setRanking(9999);
////        }
//        if (d.getBestHour() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getBestHour()), "bestHour");
////            scenicUpdate.setBestHour(s.getBestHour());
//            ////��������ֶ�
//        }
//        if (d.getAdvice() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getAdvice()), "advice");
////            scenicUpdate.setAdvice(s.getAdvice());
//            ////��������ֶ�
//        }
//        if (d.getTheme() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getTheme()), "theme");
//            String theme = s.getTheme();
//            if ("ctrip".equals(s.getDataSource())) {
//                theme = theme.replace(" ", ",");
//            }
////            scenicUpdate.setTheme(theme);
//            ////��������ֶ�
//        }
//        if (d.getScenicType() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getScenicType()), "scenicType");
////            scenicUpdate.setScenicType(s.getScenicType());
//            ////��������ֶ�
//        }
//        if (d.getNotice() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getNotice()), "notice");
////            scenicUpdate.setNotice(s.getNotice());
//            scenicExtendUpdate.setNotice(s.getNotice());
//        }
//        if (d.getGuide() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getGuide()), "guide");
////            scenicUpdate.setGuide(s.getGuide());
//            scenicExtendUpdate.setTrafficGuide(s.getGuide());
//        }
//        if (d.getHpl() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getHpl()), "hpl");
////            scenicUpdate.setHpl(s.getHpl());
//            //��������ֶ�
//        }
//        if (d.getShortComment() > 0) {
//            DataScenicInfo s = dataScenicService.selColumnById(String.valueOf(d.getShortComment()), "shortComment");
//            String comment = s.getShortComment();
//            if (!comment.contains("��������")) {
//                if (comment.contains("����") && comment.endsWith("�ĵ���")) {
//                    comment = comment.split("����")[0];
//                }
////                scenicUpdate.setShortComment(comment);
//                ////��������ֶ�
//            }
//
//        }
//
//        service.updateWithDataScenic(scenicUpdate);
//        return scenicUpdate;
//    }
//
//    private int getMinutes(String adviceTime, int minutes) {
//        String[] rs = {
//                "[-����](\\d+\\.\\d+|\\d+)��?\\s?(����|Сʱ|��)"
//                , "(\\d+\\.\\d+|\\d+)��?\\s?(����|Сʱ|��)"
//                , "(\\d+\\.\\d+|\\d+)"
//        };
//        for (String r : rs) {
//            Pattern p = Pattern.compile(r);
//            Matcher m = p.matcher(adviceTime);
//            double num;
//            String unit = "";
//            if (m.find()) {
//                num = Double.parseDouble(m.group(1));
//                if (m.groupCount() == 2)
//                    unit = m.group(2);
//                if (unit.equals("��")) {
//                    minutes = (int) (num * 60 * 8);
//                } else if (unit.equals("Сʱ")) {
//                    minutes = (int) (num * 60);
//                } else {
//                    minutes = (int) num;
//                }
//                break;
//            }
//        }
//        return minutes;
//    }
//
//    private String replaceAddress(String address, String source) {
//        if ("ctrip".equals(source))
//            address = address.replace("��ַ��", "");
//        else if ("cncn".equals(source))
//            address = address.replace("[�鿴��ͼ]", "");
//            if(address.contains("��")){
//                address = address.substring(address.indexOf(" ")+1,address.length());
//            }
//        else if ("ctrip".equals(source))
//            address = address.replace("�����ַ��", "");
//        return address;
//    }
//
//    private String replaceChzNums(String adviceTime) {
//        adviceTime = adviceTime.replace("��", "0.5").replace("ȫ��", "1��").replace("һ", "1").replace("��", "2").replace("��", "3")
//                .replace("��", "4").replace("��", "5").replace("��", "6").replace("��", "7").replace("��", "8")
//                .replace("��", "9").replace("ʮ", "10");
//        return adviceTime;
//    }
//
//
//    private DataScenicRelationDetailsEntity GetRelationDetails(DataScenicRelationDetailsEntity details, int sourceId) {
//        DataScenicInfo ds = dataScenicService.selById(String.valueOf(sourceId));
//        if (ds == null) {
//            return details;
//        }
//        if (details.getStar() == 0 && ds.getStar() > 0) {
//            details.setStar(sourceId);
//        }
//        if (details.getPrice() == 0 && ds.getPrice() > 0) {
//            details.setPrice(sourceId);
//        }
//        if (details.getScore() == 0 && ds.getScore() > 0) {
//            details.setScore(sourceId);
//        }
//        if (details.getAddress() == 0 && ds.getAddress() != null && !"".equals(ds.getAddress())) {
//            details.setAddress(sourceId);
//        }
//        if (details.getTelephone() == 0 && ds.getTelephone() != null && !"".equals(ds.getTelephone())) {
//            details.setTelephone(sourceId);
//        }
//        if (details.getOpenTime() == 0 && ds.getOpenTime() != null && !"".equals(ds.getOpenTime())) {
//            details.setOpenTime(sourceId);
//        }
//        if (details.getAdviceTime() == 0 && ds.getAdviceTime() != null && !"".equals(ds.getAdviceTime())) {
//            details.setAdviceTime(sourceId);
//            details.setAdviceHours(sourceId);//����adviceTime����
//        }
//
//        if (details.getBestTime() == 0 && ds.getBestTime() != null && !"".equals(ds.getBestTime())) {
//            details.setBestTime(sourceId);
//        }
//        if (details.getShortComment() == 0 && ds.getShortComment() != null && !"".equals(ds.getShortComment())
//                && !ds.getShortComment().contains("��������")) {
//            details.setShortComment(sourceId);
//        }
//        if (details.getLongitude() == 0 && ds.getLongitude() > 0) {
//            details.setLongitude(sourceId);
//        }
//        if (details.getLatitude() == 0 && ds.getLatitude() > 0) {
//            details.setLatitude(sourceId);
//        }
//        if (details.getUrl() == 0 && ds.getUrl() != null) {
//            details.setUrl(sourceId);
//        }
////        if (details.getIsCity() == 0 && ds.getIsCity() > 0) {
////            details.setIsCity(sourceId);
////        }
////        if (details.getIsStation() == 0 && ds.getIsStation() > 0) {
////            details.setIsStation(sourceId);
////        }
//        if (details.getStationType() == 0 && ds.getStationType() > 0) {
//            details.setStationType(sourceId);
//        }
//        if (details.getHighestPrice() == 0 && ds.getHighestPrice() > 0) {
//            details.setHighestPrice(sourceId);
//        }
//        if (details.getMarketPrice() == 0 && ds.getMarketPrice() > 0) {
//            details.setMarketPrice(sourceId);
//        }
//        if (details.getLevel() == 0 && ds.getLevel() != null && !"0".equals(ds.getLevel()) && !"".equals(ds.getLevel())) {
//            details.setLevel(sourceId);
//        }
//        if (details.getRanking() == 0 && ds.getRanking() > 0) {
//            details.setRanking(sourceId);
//        }
//        if (details.getBestHour() == 0 && ds.getBestHour() != null && !"".equals(ds.getBestHour())) {
//            details.setBestHour(sourceId);
//        }
//        if (details.getIntroduction() == 0 && ds.getIntroduction() != null && !"".equals(ds.getIntroduction())) {
//            details.setIntroduction(sourceId);
//        }
//        if (details.getWebsite() == 0 && ds.getWebsite() != null && !"".equals(ds.getWebsite())) {
//            details.setWebsite(sourceId);
//        }
//        if (details.getAdvice() == 0 && ds.getAdvice() != null && !"".equals(ds.getAdvice())) {
//            details.setAdvice(sourceId);
//        }
//        if (details.getTheme() == 0 && ds.getTheme() != null && !"".equals(ds.getTheme())) {
//            details.setTheme(sourceId);
//        }
//        if (details.getHow() == 0 && ds.getHow() != null && !"".equals(ds.getHow())) {
//            details.setHow(sourceId);
//        }
//
//        if (details.getScenicType() == 0 && ds.getScenicType() != null && !"".equals(ds.getScenicType())) {
//            details.setScenicType(sourceId);
//        }
//        if (details.getNotice() == 0 && ds.getNotice() != null && !"".equals(ds.getNotice())) {
//            details.setNotice(sourceId);
//        }
//        if (details.getGuide() == 0 && ds.getGuide() != null && !"".equals(ds.getGuide())) {
//            details.setGuide(sourceId);
//        }
//        if (details.getHpl() == 0 && ds.getHpl() != null && !"".equals(ds.getHpl())) {
//            details.setHpl(sourceId);
//        }
//        if (details.getRecommendReason() == 0 && ds.getRecommendReason() != null && !"".equals(ds.getRecommendReason())) {
//            details.setRecommendReason(sourceId);
//        }
//        return details;
//    }
//}
