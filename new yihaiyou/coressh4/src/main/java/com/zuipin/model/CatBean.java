package com.zuipin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.zuipin.entity.TProductCatInterface;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author:zhengry
 * @version Revision 2.0.0
 * @email:zryuan@xiangyu.cn
 * @see:
 * @创建日期：2013-9-24
 * @功能说明：生成商品分类目录树的辅助类
 */
public class CatBean {
    private TProductCatInterface cat;
    
    private List<CatBean>   children;
    
    public void addChild(CatBean node) {
        if (this.children == null) {
            children = new ArrayList<CatBean>();
            children.add(node);
        } else {
            children.add(node);
        }
    }
    
    public static List<CatBean> processCat(List<TProductCatInterface> list) {
        HashMap<String, CatBean> map = new HashMap<String, CatBean>();// 中间对象存放map
        List<CatBean> rsList = new ArrayList<CatBean>();// 结果list
        // 将list中的数据，转换成catBean类型，放入Map中备用
        for (int i = 0; i < list.size(); i++) {
            TProductCatInterface t = list.get(i);
            CatBean cat = new CatBean();
            cat.setCat(t);
            cat.setChildren(new ArrayList<CatBean>());
            map.put(t.getId().toString(), cat);
        }
        // 遍历first的数据，把每个节点加入他的父节点的children
        for (TProductCatInterface t : list) {
            if (t.getParentId() != null) {
                String pid = t.getParentId().toString();
                CatBean pnode = (CatBean) map.get(pid);
                if (null != pnode) {
                    CatBean cnode = (CatBean) map.get(t.getId().toString());
                    pnode.addChild(cnode);
                    map.put(pid, pnode);
                }
            }
        }
        // 选出所有一级分类
        for (TProductCatInterface t : list) {
            if (t.getParentId() == null) {
                CatBean node = (CatBean) map.get(t.getId().toString());
                // Object[] arr = node.getChildren().toArray();
                // node.setChildren(Arrays.asList(arr));
                rsList.add(node);
            }
        }
        return rsList;
    }
    
    public List<CatBean> getChildren() {
        return children;
    }
    
    public void setChildren(List<CatBean> children) {
        this.children = children;
    }
    
    public TProductCatInterface getCat() {
        return cat;
    }
    
    public void setCat(TProductCatInterface cat) {
        this.cat = cat;
    }
    
}
