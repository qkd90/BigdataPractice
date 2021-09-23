package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.yhypc.vo.CruiseShipResponse;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.cruiseship.*;
import com.data.data.hmly.service.cruiseship.entity.*;
import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;
import com.data.data.hmly.service.cruiseship.request.CruiseShipSearchRequest;
import com.data.data.hmly.service.cruiseship.vo.CruiseShipSolrEntity;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.Request;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/1/9.
 */
public class CruiseShipWebAction extends FrameBaseAction {

    @Resource
    private CategoryService categoryService;
    @Resource
    private CategoryTypeService categoryTypeService;
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;
    @Resource
    private CommentService commentService;
    @Resource
    private MemberService memberService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private CruiseShipProjectService cruiseShipProjectService;
    @Resource
    private CruiseShipRoomService cruiseShipRoomService;
    @Resource
    private CruiseShipProjectImageService cruiseShipProjectImageService;
    @Resource
    private CruiseShipProjectClassifyService cruiseShipProjectClassifyService;

    private List<Category> cruiseshipBrands = Lists.newArrayList();
    private List<Category> cruiseshipLines = Lists.newArrayList();
    private List<CruiseShipRoom> cruiseShipRooms = Lists.newArrayList();

    private CruiseShip cruiseShip = new CruiseShip();
    private CruiseShipSearchRequest cruiseShipSearchRequest = new CruiseShipSearchRequest();
    private Map<String, Object> map = new HashMap<String, Object>();
    private int pageIndex = 0;
    private int pageSize = 10;
    private Long cruiseShipId;
    private Long dateId;
    private Long projectId;
    private Long classifyId;
    private Long parentId;

    private CruiseShipProject cruiseShipProject;
    private CruiseShipRoomType roomType;


    public Result getCruiseshipRoomList() {
        if (dateId == null) {
            return dispatch404();
        }
        List<CruiseShipRoom> cruiseShipRoomList = cruiseShipRoomDateService.getRoomList(dateId, null, new Date());
        return json(JSONArray.fromObject(cruiseShipRoomList, JsonFilter.getIncludeConfig("")));
    }
    public Result getRoomList() {
        if (dateId == null) {
            return dispatch404();
        }
        List<CruiseShipRoom> cruiseShipRoomList = cruiseShipRoomDateService.queryRoomList(dateId);
        for(CruiseShipRoom cruiseShipRoom : cruiseShipRoomList ){
            List<Productimage> productimageList = productimageService.findAllImagesByProIdAadTarId(cruiseShipRoom.getCruiseShip().getId(),cruiseShipRoom.getId(),null);
            cruiseShipRoom.setProductimages(productimageList);
        }

        return json(JSONArray.fromObject(cruiseShipRoomList, JsonFilter.getIncludeConfig("")));
    }

    //查找邮轮项目
    public Result getCruiseShipProjectList(){

        List<CruiseShipProject> projectList = cruiseShipProjectService.listById(cruiseShipId,parentId);
        for(CruiseShipProject cruiseShipProject : projectList){
            List<CruiseShipProjectImage> imageList = cruiseShipProjectImageService.queryImagesById(cruiseShipProject.getId());
            cruiseShipProject.setProjectImages(imageList);
        }
        return json(JSONArray.fromObject(projectList, JsonFilter.getIncludeConfig("projectImages","cruiseShipProjectClassify")));
    }

    public Result getTotalCommentPage() {
        Comment comment = new Comment();
        comment.setTargetIdList(cruiseShipDateService.getIdListByCruiseshipId(cruiseShipId));
        Long result = commentService.countMyComment(comment);
        return json(JSONArray.fromObject(result));
    }

    public Result getCommentList() {
        if (cruiseShipId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

        Comment comment = new Comment();
        comment.setTargetIdList(cruiseShipDateService.getIdListByCruiseshipId(cruiseShipId));
        Page page = new Page(pageIndex, pageSize);
        List<Comment> commentList = commentService.getCommentList(comment, page);
        List<Comment> tempCommentList = Lists.newArrayList();
        for (Comment tempComment : commentList) {
            Member user = memberService.get(tempComment.getUser().getId());
            Float totalScore = 0F;
            List<CommentScore> commentScores = tempComment.getCommentScores();
            for (CommentScore commentScore : commentScores) {
                totalScore += commentScore.getScore().floatValue();
            }
            tempComment.setAvgScore(totalScore==0F ? 0F: totalScore / 20);
            tempComment.setUserName(user.getNickName());
            if (StringUtils.isNotBlank(user.getHead()) && !user.getHead().startsWith("http")) {
                tempComment.setHead(QiniuUtil.URL + user.getHead());
            } else {
                tempComment.setHead(user.getHead());
            }
            tempCommentList.add(tempComment);
        }
        map.put("commentList", tempCommentList);
        simpleResult(map, true, "");
        return jsonResult(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("comments")));
    }


    public Result getCommentInfo() {
        if (cruiseShipId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        Comment comment = new Comment();
        comment.setTargetIdList(cruiseShipDateService.getIdListByCruiseshipId(cruiseShipId));
        Integer commentAvgScore = commentService.getAvgScore(comment);
        Long commentCount = commentService.countMyComment(comment);
        map.put("commentAvgScore", commentAvgScore == 0 ? 0 : commentAvgScore.floatValue() / 20);
        map.put("commentCount", commentCount);
        simpleResult(map, true, "");
        return jsonResult(JSONObject.fromObject(map));
    }


    public Result index() {
        setAttribute(YhyConstants.YHY_CRUISESHIP_INDEX_KEY, FileUtil.loadHTML(YhyConstants.YHY_CRUISESHIP_INDEX));
        return dispatch();
    }

    public Result detail() {
        if (dateId == null) {
            return dispatch404();
        }
        CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(dateId);
        cruiseShip = cruiseShipDate.getCruiseShip();
        if (cruiseShip.getStatus() != ProductStatus.UP) {
            return dispatch404();
        }
        setAttribute(YhyConstants.YHY_CRUISESHIP_DETAIL_KEY, FileUtil.loadHTML(YhyConstants.YHY_CRUISESHIP_DETAIL + dateId));
        setAttribute(YhyConstants.YHY_CRUISESHIP_HEAD_KEY, FileUtil.loadHTML(YhyConstants.YHY_CRUISESHIP_HEAD + dateId));

        return dispatch();
    }

    public Result getTotalPage() {
        List<String> dateRange = Lists.newArrayList();
        if (cruiseShipSearchRequest.getDateRange().size() > 0 ) {
            if (cruiseShipSearchRequest.getDateRange().size() == 1) {
                dateRange.add(DateUtils.format(DateUtils.toDate(cruiseShipSearchRequest.getDateRange().get(0)), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
            } else if (cruiseShipSearchRequest.getDateRange().size() == 2) {
                dateRange.add(DateUtils.format(DateUtils.toDate(cruiseShipSearchRequest.getDateRange().get(0)), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
                dateRange.add(DateUtils.format(DateUtils.toDate(cruiseShipSearchRequest.getDateRange().get(1)), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
            }
        } else {
            dateRange.add(DateUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
        }

        cruiseShipSearchRequest.setDateRange(dateRange);
        Long result = cruiseShipService.countFromSolr(cruiseShipSearchRequest);
        return json(JSONArray.fromObject(result));
    }

    public Result getCruiseshipList() {
        Page page = new Page(pageIndex, pageSize);
        List<String> dateRange = Lists.newArrayList();
        if (cruiseShipSearchRequest.getDateRange().size() > 0 ) {
            if (cruiseShipSearchRequest.getDateRange().size() == 1) {
                dateRange.add(DateUtils.format(DateUtils.toDate(cruiseShipSearchRequest.getDateRange().get(0)), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
            } else if (cruiseShipSearchRequest.getDateRange().size() == 2) {
                dateRange.add(DateUtils.format(DateUtils.toDate(cruiseShipSearchRequest.getDateRange().get(0)), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
                dateRange.add(DateUtils.format(DateUtils.toDate(cruiseShipSearchRequest.getDateRange().get(1)), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
            }
        } else {
            dateRange.add(DateUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
        }

        cruiseShipSearchRequest.setDateRange(dateRange);
        List<CruiseShipSolrEntity> cruiseShipSolrEntities = cruiseShipService.listFromSolr(cruiseShipSearchRequest, page);
        List<CruiseShipResponse> cruiseShipResponses = Lists.transform(cruiseShipSolrEntities, new Function<CruiseShipSolrEntity, CruiseShipResponse>() {
            @Override
            public CruiseShipResponse apply(CruiseShipSolrEntity cruiseShipSolrEntity) {

                return new CruiseShipResponse(cruiseShipSolrEntity);
            }
        });
        map.put("page", page);
        map.put("cruiseShipResponses", cruiseShipResponses);
        return json(JSONObject.fromObject(map));
    }

    public Result list() {

        //游轮品牌
        CategoryType categoryType = categoryTypeService.getByType("cruise_ship_brand");
        Category category = new Category();
        category.setType(categoryType);
        category.setParentId(0L);
        cruiseshipBrands = categoryService.getCategoryList(category);


        //游轮航线
        categoryType = categoryTypeService.getByType("cruise_ship_route");
        category.setType(categoryType);
        category.setParentId(0L);
        cruiseshipLines = categoryService.getCategoryList(category);

        cruiseShipSearchRequest.setDate(StringUtils.htmlEncode(cruiseShipSearchRequest.getDate()));

        return dispatch();
    }

    public Result productDetails(){
        cruiseShipProject = cruiseShipProjectService.get(projectId);
        if (cruiseShipProject == null){
            return dispatch404();
        }
        List<CruiseShipProject> projectList = cruiseShipProjectService.listById(cruiseShipProject.getCruiseShip().getId(),cruiseShipProject.getCruiseShipProjectClassify().getCruiseShipProjectClassify().getId());
        getRequest().setAttribute("projectList",projectList);
        return dispatch();
    }


    public Result cabinMore(){
        List<CruiseShipRoom> cruiseShipRoomInside = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomSeascape = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomBalcony = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomSuite = Lists.newArrayList();


        cruiseShip = cruiseShipService.findById(cruiseShipId);
        cruiseShipRooms = cruiseShipRoomService.listByCruiseShipId(cruiseShipId);

        for(CruiseShipRoom cruiseShipRoomsItem : cruiseShipRooms){

            String strRoomType = cruiseShipRoomsItem.getRoomType().toString();

            if(strRoomType.equals("inside")){
                cruiseShipRoomInside.add(cruiseShipRoomsItem);
            }else if(strRoomType.equals("seascape")){
                cruiseShipRoomSeascape.add(cruiseShipRoomsItem);
            }else if(strRoomType.equals("balcony")){
                cruiseShipRoomBalcony.add(cruiseShipRoomsItem);
            }else{
                cruiseShipRoomSuite.add(cruiseShipRoomsItem);
            }
        }

        getRequest().setAttribute("cruiseShipRoomInside" , cruiseShipRoomInside);
        getRequest().setAttribute("cruiseShipRoomSeascape" , cruiseShipRoomSeascape);
        getRequest().setAttribute("cruiseShipRoomBalcony" , cruiseShipRoomBalcony);
        getRequest().setAttribute("cruiseShipRoomSuite" , cruiseShipRoomSuite);

        if(cruiseShip == null){
            return dispatch404();
        }
        return dispatch();
    }

    public Result chooseCabin(){

        List<CruiseShipRoom> cruiseShipRoomInside = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomSeascape = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomBalcony = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomSuite = Lists.newArrayList();

        cruiseShip = cruiseShipService.findById(cruiseShipId);

        cruiseShipRoomInside = cruiseShipRoomDateService.getRoomList(dateId, CruiseShipRoomType.inside, null);
        cruiseShipRoomSeascape = cruiseShipRoomDateService.getRoomList(dateId, CruiseShipRoomType.seascape, null);
        cruiseShipRoomBalcony = cruiseShipRoomDateService.getRoomList(dateId, CruiseShipRoomType.balcony, null);
        cruiseShipRoomSuite = cruiseShipRoomDateService.getRoomList(dateId, CruiseShipRoomType.suite, null);

        getRequest().setAttribute("cruiseShipRoomInside",cruiseShipRoomInside);
        getRequest().setAttribute("cruiseShipRoomSeascape",cruiseShipRoomSeascape);
        getRequest().setAttribute("cruiseShipRoomBalcony",cruiseShipRoomBalcony);
        getRequest().setAttribute("cruiseShipRoomSuite",cruiseShipRoomSuite);

        return dispatch();
    }

    public Result customerInformation(){

        cruiseShip = cruiseShipService.findById(cruiseShipId);

        return dispatch();
    }

    public Result productMore(){

        List<CruiseShipProjectClassify> listCruiseShipProjectClassify = Lists.newArrayList();

        listCruiseShipProjectClassify = cruiseShipProjectClassifyService.listById(parentId);

        getRequest().setAttribute("listCruiseShipProjectClassify" , listCruiseShipProjectClassify);

        return dispatch();
    }


    public Result cruiseDetails(){
        List<CruiseShipRoom> cruiseShipRoomInside = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomSeascape = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomBalcony = Lists.newArrayList();
        List<CruiseShipRoom> cruiseShipRoomSuite = Lists.newArrayList();

        int parentIdService = 1, parentIdFood = 2, parentIdEntertainment = 3;

        cruiseShip = cruiseShipService.findById(cruiseShipId);
        cruiseShipRooms = cruiseShipRoomService.listByCruiseShipId(cruiseShipId);

        for(CruiseShipRoom cruiseShipRoomsItem : cruiseShipRooms){

            String strRoomType = cruiseShipRoomsItem.getRoomType().toString();

            if(strRoomType.equals("inside")){
                cruiseShipRoomInside.add(cruiseShipRoomsItem);
            }else if(strRoomType.equals("seascape")){
                cruiseShipRoomSeascape.add(cruiseShipRoomsItem);
            }else if(strRoomType.equals("balcony")){
                cruiseShipRoomBalcony.add(cruiseShipRoomsItem);
            }else{
                cruiseShipRoomSuite.add(cruiseShipRoomsItem);
            }
        }

        getRequest().setAttribute("cruiseShipRoomInside" , cruiseShipRoomInside);
        getRequest().setAttribute("cruiseShipRoomSeascape" , cruiseShipRoomSeascape);
        getRequest().setAttribute("cruiseShipRoomBalcony" , cruiseShipRoomBalcony);
        getRequest().setAttribute("cruiseShipRoomSuite" , cruiseShipRoomSuite);

        List<CruiseShipProjectClassify> listCruiseShipProjectClassifyService = Lists.newArrayList();
        listCruiseShipProjectClassifyService = cruiseShipProjectClassifyService.listById((long)parentIdService);
        List<CruiseShipProject> serviceProjectList = Lists.newArrayList();
        for (CruiseShipProjectClassify cruiseShipProjectClassify : listCruiseShipProjectClassifyService) {
            serviceProjectList.addAll(cruiseShipProjectClassify.getCruiseShipProject());
        }

        List<CruiseShipProjectClassify> listCruiseShipProjectClassifyFood = Lists.newArrayList();
        listCruiseShipProjectClassifyFood = cruiseShipProjectClassifyService.listById((long)parentIdFood);

        List<CruiseShipProjectClassify> listCruiseShipProjectClassifyEntertainmen = Lists.newArrayList();
        listCruiseShipProjectClassifyEntertainmen = cruiseShipProjectClassifyService.listById((long)parentIdEntertainment);

        getRequest().setAttribute("listCruiseShipProjectClassifyService" , serviceProjectList);
        getRequest().setAttribute("listCruiseShipProjectClassifyFood" , listCruiseShipProjectClassifyFood);
        getRequest().setAttribute("listCruiseShipProjectClassifyEntertainmen" , listCruiseShipProjectClassifyEntertainmen);

        return dispatch();
    }


    public List<Category> getCruiseshipBrands() {
        return cruiseshipBrands;
    }

    public void setCruiseshipBrands(List<Category> cruiseshipBrands) {
        this.cruiseshipBrands = cruiseshipBrands;
    }

    public List<Category> getCruiseshipLines() {
        return cruiseshipLines;
    }

    public void setCruiseshipLines(List<Category> cruiseshipLines) {
        this.cruiseshipLines = cruiseshipLines;
    }

    public CruiseShip getCruiseShip() {
        return cruiseShip;
    }

    public void setCruiseShip(CruiseShip cruiseShip) {
        this.cruiseShip = cruiseShip;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public CruiseShipSearchRequest getCruiseShipSearchRequest() {
        return cruiseShipSearchRequest;
    }

    public void setCruiseShipSearchRequest(CruiseShipSearchRequest cruiseShipSearchRequest) {
        this.cruiseShipSearchRequest = cruiseShipSearchRequest;
    }

    public Long getCruiseShipId() {
        return cruiseShipId;
    }

    public void setCruiseShipId(Long cruiseShipId) {
        this.cruiseShipId = cruiseShipId;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public CruiseShipRoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(CruiseShipRoomType roomType) {
        this.roomType = roomType;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public CruiseShipProject getCruiseShipProject() {
        return cruiseShipProject;
    }

    public void setCruiseShipProject(CruiseShipProject cruiseShipProject) {
        this.cruiseShipProject = cruiseShipProject;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
