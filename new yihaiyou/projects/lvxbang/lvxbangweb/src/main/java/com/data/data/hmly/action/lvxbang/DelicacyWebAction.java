package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.ctriphotel.base.StringUtil;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.request.DelicacySearchRequest;
import com.data.data.hmly.service.restaurant.vo.DelicacySolrEntity;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by guoshijie on 2015/12/18.
 */
public class DelicacyWebAction extends LxbAction implements ModelDriven<DelicacySearchRequest> {

    @Resource
    private DelicacyService delicacyService;
    @Resource
    private CommentService commentService;
    @Resource
    private SuggestService suggestService;
    @Resource
    private AreaService areaService;

    public DelicacySearchRequest delicacyRequest = new DelicacySearchRequest();
    public Comment comment = new Comment();
    public Integer pageNo = 0;
    public Integer pageSize = 10;
    public Long delicacyId;
    public String cityName;
    public String cityCode;
    public List<TbArea> cityList = new ArrayList<>();

    public Result index() {
        setAttribute(LXBConstants.LVXBANG_DELICACY_BANNER_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DELICACY_BANNER));
        setAttribute(LXBConstants.LVXBANG_DELICACY_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DELICACY_INDEX));
        return dispatch();
    }

    public Result detail() {
        Integer status = delicacyService.getStatus(delicacyId);
        if (status != 1) {
            return dispatch404();
        } else {
            setAttribute(LXBConstants.LVXBANG_DELICACY_DETAIL_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DELICACY_DETAIL + delicacyId));
            setAttribute(LXBConstants.LVXBANG_DELICACY_HEAD_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DELICACY_HEAD + delicacyId));
            return dispatch();
        }
    }

    public Result count() {
        return json(JSONArray.fromObject(delicacyService.countFromSolr(delicacyRequest)));
    }

    public Result list() {
        Page page = new Page(pageNo, pageSize);
        List<DelicacySolrEntity> list = delicacyService.listFromSolr(delicacyRequest, page);
        return json(JSONArray.fromObject(list));
    }

    public Result delicacyList() {

        if (!StringUtil.isBlank(cityCode)) {
            cityList.add(areaService.get(Long.parseLong(cityCode)));
            return dispatch();
        }
        if (getParameter("cityCode") != null && !StringUtil.isBlank(getParameter("cityCode").toString())) {
            cityList.add(areaService.get(Long.parseLong(getParameter("cityCode").toString())));
            return dispatch();
        }

        return dispatch();
    }

    public Result commentList() {
        Delicacy delicacy = delicacyService.get(delicacyId);
        List<Comment> list = delicacy.getDelicacyCommentList();
        List<Comment> newList = new ArrayList<Comment>();
        for (Comment c : list) {
            List<CommentScore> commentScores = c.getCommentScores();
            List<CommentScore> newCommentScores = new ArrayList<CommentScore>();
            for (CommentScore cs : commentScores) {
                if (Objects.equals(cs.getCommentScoreType().getName(), "总体评价")) {
                    newCommentScores.add(cs);
                }
            }
            c.setCommentScores(newCommentScores);
            newList.add(c);
        }
        return json(JSONArray.fromObject(newList, JsonFilter.getIncludeConfig("comments", "user", "commentScores")));
    }

    public Result saveComment() {
        Member user = getLoginUser();
        comment.setUser(user);
        comment.setType(ProductType.delicacy);
        commentService.saveComment(comment);
        //delicacyService.saveDelicacyComment(comment);
        return json(JSONArray.fromObject("success"));
    }

    public Result testCount() {
        return text(getParameter("count").toString());
    }

    @Override
    public DelicacySearchRequest getModel() {
        return delicacyRequest;
    }

    public Result delicacyIndex() {
        if (getParameter("delicacyId") != null) {
            Long delicacyId = Long.parseLong(getParameter("delicacyId").toString());
            Delicacy delicacy = delicacyService.get(delicacyId);
            delicacyService.indexDelicacy(delicacy);
            return text("索引成功");
        } else {
            delicacyService.indexDelicacy();
            return text("索引成功");
        }
    }

    public Result suggestList() throws UnsupportedEncodingException {
        String name = (String) getParameter("name");
        List<SuggestionEntity> suggestion = new ArrayList<>();
        if (delicacyRequest != null) {
            if (delicacyRequest.getCityIds().isEmpty()) {
                suggestion = suggestService.suggestDelicacy(name, 10);
            } else {
                suggestion = suggestService.suggestDelicacy(delicacyRequest.getCityIds(), name, 10);
            }
        } else {
            suggestion = suggestService.suggestDelicacy(name, 10);
        }
//        List<SuggestionEntity> list = suggestService.suggestDelicacy(name, 10);
        return json(JSONArray.fromObject(suggestion));
    }
}