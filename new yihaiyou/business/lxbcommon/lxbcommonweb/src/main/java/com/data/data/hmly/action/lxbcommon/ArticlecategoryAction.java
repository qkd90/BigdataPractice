package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.lxbcommon.ArticleCategoryService;
import com.data.data.hmly.service.lxbcommon.entity.ArticleCategory;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/4/14.
 */
public class ArticlecategoryAction extends FrameBaseAction {

    @Resource
    private ArticleCategoryService articleCategoryService;

    private int page;
    private int rows;

    private ArticleCategory category = new ArticleCategory();

    public Result list() {
        return dispatch();
    }

    public Result getCategoryList() {
        Page page = new Page(this.page, this.rows);
        List<ArticleCategory> categoryTreeList = articleCategoryService.getTreeGirdCategory(category, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("parentCategory", "grandCategory", "children");
        return datagrid(categoryTreeList, page.getTotalCount(), jsonConfig);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }
}
