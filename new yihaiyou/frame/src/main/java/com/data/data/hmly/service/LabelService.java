package com.data.data.hmly.service;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.dao.LabelDao;
import com.data.data.hmly.service.dao.LabelItemDao;
import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.vo.LabelsVo;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LabelService {

    Logger logger = Logger.getLogger(LabelService.class);

    private static final String CORE_NAME = "label";

    @Resource
    private LabelDao labelDao;
    @Resource
    private LabelItemDao labelItemDao;
    @Resource
    private SysUnitDao sysUnitDao;
    @Resource
    private SysSiteDao sysSiteDao;
    @Resource
    private MulticoreSolrTemplate solrTemplate;


    public List<Label> getAllChildsLabels(Label label) {

        Criteria<Label> criteria = new Criteria<Label>(Label.class);

        criteria.eq("parent", label);
        criteria.eq("status", LabelStatus.USE);
        criteria.orderBy("sort", "asc");
        return labelDao.findByCriteria(criteria);

    }

    public List<Label> getChildLabels(Label parent, Page page) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.eq("parent", parent);
        criteria.eq("status", LabelStatus.USE);
        criteria.orderBy("sort", "asc");
        return labelDao.findByCriteria(criteria, page);
    }

    public List<Label> listAll() {
        return list(new Label(), null);
    }


    public List<Label> list(Label label, Page page, String... orderProperties) {
        Criteria<Label> criteria = createCriteria(label, orderProperties);
        if (page == null) {
            return labelDao.findByCriteria(criteria);
        }
        return labelDao.findByCriteria(criteria, page);
    }

    public Criteria<Label> createCriteria(Label label, String... orderProperties) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        if (orderProperties.length == 2 && orderProperties[0] != null && orderProperties[1] != null) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1 && orderProperties[0] != null) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (StringUtils.isNotBlank(label.getName())) {
            criteria.eq("name", label.getName());
        }
        if (StringUtils.isNotBlank(label.getSearchName())) {
            criteria.like("name", label.getSearchName(), MatchMode.ANYWHERE);
        }
        if (label.getStatus() != null) {
            criteria.eq("status", label.getStatus());
        }
        if (label.getParent() != null) {
            criteria.eq("parent.id", label.getParent().getId());
        }
        return criteria;
    }

    /**
     * 获取标签树列表
     *
     * @param page
     * @param labelParent
     * @return
     */
    public List<LabelsVo> getTreLabels(Page page, Label labelParent) {
        List<Label> parentLabels = labelDao.getTreLabels(page, labelParent);            //取得父级标签
        List<Label> childrenLabels = labelDao.getChildsTreLabels(labelParent);        //取得子级标签
        List<LabelsVo> labelVos = new ArrayList<LabelsVo>();
        for (Label la : parentLabels) {
            int a = -1;
            if (StringUtils.isNotBlank(labelParent.getName())) {
                a = la.getName().indexOf(labelParent.getName());
            }

            LabelsVo labelsVo = new LabelsVo();
            labelsVo.setId(la.getId());
            labelsVo.setText(la.getName());
            labelsVo.setName(la.getName());
            labelsVo.setAlias(la.getAlias());
            labelsVo.setSort(String.valueOf(la.getSort()));
            labelsVo.setLevel(1);
            labelsVo.setCreateTime(la.getCreateTime());
            labelsVo.setStatus(la.getStatus().toString());
            labelsVo.setChildren(treeLabelsChild(childrenLabels, la.getId()));
            if (labelsVo.getChildren().size() > 0) {
                labelsVo.setIsParent("1");
            } else {
                labelsVo.setIsParent("0");
            }

            if (a >= 0) {
                labelVos.add(labelsVo);
            } else {
                labelVos.add(labelsVo);
            }


        }

        return labelVos;
    }

    /**
     * 拼装树，构成标签列表
     *
     * @param childrenLabels
     * @param parentId
     * @return
     */
    private List<LabelsVo> treeLabelsChild(List<Label> childrenLabels, Long parentId) {
        List<LabelsVo> vos = new ArrayList<LabelsVo>();
        for (Label label : childrenLabels) {
            if (label.getParent().getId() == parentId) {
                LabelsVo vo = new LabelsVo();
                vo.setId(label.getId());
                vo.setName(label.getName());
                vo.setAlias(label.getAlias());
                vo.setSort(String.valueOf(label.getSort()));
                vo.setText(label.getName());
                vo.setLevel(2);
                vo.setCreateTime(label.getCreateTime());
                vo.setStatus(label.getStatus().toString());
                vos.add(vo);
            }
        }
        return vos;
    }

    public List<LabelsVo> findTreeByParent(Long parentId, LabelStatus status) {
        List<LabelsVo> vos = new ArrayList<LabelsVo>();
        List<Label> labels = listByParent(parentId, status);
        for (Label la : labels) {
            LabelsVo vo = new LabelsVo();
            vo.setId(la.getId());
            vo.setName(la.getName());
            vo.setText(la.getName());
            vo.setLevel(2);
            vo.setCreateTime(la.getCreateTime());
            vo.setStatus(la.getStatus().toString());
            vos.add(vo);
        }
        return vos;
    }

    private List<Label> listByParent(Long parentId, LabelStatus status) {
        return labelDao.listByParent(parentId, status);
    }

    public List<Long> getLabelIdsByParent(Long parentId, LabelStatus status) {
        return labelDao.listIdsByParent(parentId, status);
    }

    public Label load(long id) {
        return labelDao.load(id);
    }

    /**
     * 保存或更新Label
     *
     * @param label
     */
    public void saveOrUpdate(Label label) {

        if (label.getId() != null) {
            List<Label> labels = getAllChildsLabels(label);
            if (label.getParent() == null && !labels.isEmpty()) {
                for (Label la : labels) {
                    la.setStatus(label.getStatus());
                    labelDao.update(la);
                }
            }
            labelDao.update(label);
        } else {
            label.setCreateTime(new Date());
            labelDao.save(label);
        }

    }

    public void delLabels(Label label) {

        List<Label> labels = labelDao.listByParent(label.getId(), LabelStatus.DEL);
        labelDao.delete(label);
        labelDao.deleteAll(labels);
    }

    public Map<String, Object> checkLabels(Label label) {

        Map<String, Object> map = new HashMap<String, Object>();

        List<LabelItem> items = labelItemDao.findNodeByLabel(label);
        if (items.size() <= 0) {

            List<Label> labels = labelDao.listByParent(label.getId(), LabelStatus.DEL);
            if (labels.size() > 0) {
                for (Label la : labels) {
                    List<LabelItem> laItems = labelItemDao.findNodeByLabel(la);
                    if (laItems.size() > 0) {
                        map.put("msg", "'" + la.getName() + "'已使用，不能删除！");
                        map.put("flag", false);
                        break;
                    } else {
                        map.put("flag", true);
                    }
                }
            } else {
                map.put("flag", true);
            }


        } else {
            map.put("msg", "'" + label.getName() + "'已使用，不能删除！");
            map.put("flag", false);
        }

        return map;
    }

    public List<LabelsVo> getLabelVos(List<Label> labels, LabelStatus status) {
        List<LabelsVo> labelVos = new ArrayList<LabelsVo>();

        for (Label la : labels) {
            LabelsVo labelsVo = new LabelsVo();
            labelsVo.setId(la.getId());
            labelsVo.setText(la.getName());
            labelsVo.setName(la.getName());
            labelsVo.setCreateTime(la.getCreateTime());
            labelsVo.setStatus(la.getStatus().toString());
            labelsVo.setChildren(findTreeByParent(la.getId(), status));
            labelVos.add(labelsVo);
        }

        return labelVos;
    }

    /**
     * 查询标签树
     * @return
     */
    public List<LabelsVo> findAllTree() {
        Label condition = new Label();
        condition.setStatus(LabelStatus.USE);
        condition.setParentId(-1L);
        List<Label> labels = labelDao.listLabels(condition);

        // 构建标签树
        List<LabelsVo> labelVos = new ArrayList<LabelsVo>();
        for (Label la : labels) {
            LabelsVo labelsVo = new LabelsVo();
            labelsVo.setId(la.getId());
            labelsVo.setText(la.getName());
            labelsVo.setName(la.getName());
            labelsVo.setCreateTime(la.getCreateTime());
            labelsVo.setStatus(la.getStatus().toString());
            labelsVo.setLevel(la.getLevel());
            labelsVo.setLeaf(la.getLeaf());
            labelVos.add(labelsVo);
            buildLabelTree(labelsVo, condition);
        }
        return labelVos;
    }

    /**
     * 构建标签树
     * @param parentLabel
     * @param condition
     */
    public void buildLabelTree(LabelsVo parentLabel, Label condition) {
        if (!parentLabel.getLeaf()) {    // 存在子节点，递归
            condition.setParentId(parentLabel.getId());
            List<Label> childLabels = labelDao.listLabels(condition);
            List<LabelsVo> labelVos = new ArrayList<LabelsVo>();
            for (Label la : childLabels) {
                LabelsVo labelsVo = new LabelsVo();
                labelsVo.setId(la.getId());
                labelsVo.setText(la.getName());
                labelsVo.setName(la.getName());
                labelsVo.setCreateTime(la.getCreateTime());
                labelsVo.setStatus(la.getStatus().toString());
                labelsVo.setLevel(la.getLevel());
                labelsVo.setLeaf(la.getLeaf());
                labelVos.add(labelsVo);
                buildLabelTree(labelsVo, condition);
            }
            parentLabel.setChildren(labelVos);
        }
    }

    /**
     * 移动标签时递归查询标签树（状态非删除）并设置对应层级和修改父节点信息
     * 既有选择父节点又有选择子节点，移动整个父节点
     *
     * @param toIdStr       移动到目标标签标识
     * @param moveIdsArray  待移动的标签标识
     * @return
     */
    public Map<String, Object> doMoveLabelHandle(String toIdStr, String[] moveIdsArray) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        Set<Long> labelIdSet = new HashSet<Long>(); // 待移动的所有标签标识
        List<Label> moveLabelList = new ArrayList<Label>();
        Label toLabel = null;
        Integer level = 1;
        if (StringUtils.isNotBlank(toIdStr)) {
            toLabel = labelDao.load(Long.valueOf(toIdStr));
            level = toLabel.getLevel() + 1;
        }
        for (String moveIdStr : moveIdsArray) {
            Label moveLabel = labelDao.load(Long.valueOf(moveIdStr));
            moveLabelList.add(moveLabel);
            labelIdSet.add(moveLabel.getId());
            // 查找子节点
            findMoveLabelChild(moveLabel, labelIdSet);
        }

        // 检查目标标签是否合法：不能移动到已选择的标签或者子标签内
        if (StringUtils.isNotBlank(toIdStr) && labelIdSet.contains(Long.valueOf(toIdStr))) {
            result.put("success", false);
            result.put("errorMsg", "不能移动到已选择的标签（或已选择标签的子标签）内");
            return result;
        }

        // 检查选择的节点中是否存在覆盖部分，过滤掉这部分标签及子标签
        List<Label> subLabelList = new ArrayList<Label>();
        for (Label la : moveLabelList) {
            if (la.getParent() != null && labelIdSet.contains(la.getParent().getId())) {
                subLabelList.add(la);
            }
        }
        if (subLabelList.size() > 0) {
            moveLabelList.removeAll(subLabelList);
        }

        // 更新新父节点为非叶子节点
        if (toLabel != null && toLabel.getLeaf()) {
            toLabel.setLeaf(false); // 该为非叶子节点
            labelDao.update(toLabel);
        }
        // 更新移动的节点和移动节点的子节点
        List<Label> oldParentList = new ArrayList<Label>();
        for (Label la : moveLabelList) {
            Label moveLabelParent = la.getParent(); // 记录原来的父节点，方便更新
            if (moveLabelParent != null) {
                oldParentList.add(moveLabelParent);
            }
            la.setParent(toLabel);
            la.setLevel(level);
            labelDao.update(la);
            updateChildren(la, la.getLevel() + 1);
        }
        // 更新移动节点的父节点是否为叶子节点
        for (Label la : oldParentList) {
            Long count = labelDao.countChildren(la.getId());
            if (count <= 0) {
                la.setLeaf(true);
                labelDao.update(la);
            }
        }
        return result;
    }

    /**
     * 递归更新子标签
     */
    public void updateChildren(Label parent, int level) {
        if (parent.getChildren() != null && !parent.getChildren().isEmpty()) {
            for (Label la : parent.getChildren()) {
                la.setLevel(level);
                labelDao.update(la);
                updateChildren(la, la.getLevel() + 1);
            }
        }
    }

    /**
     * 查找移动子标签
     */
    public void findMoveLabelChild(Label parentLabel, Set<Long> labelIdSet) {
        if (!parentLabel.getLeaf()) {    // 存在子节点，递归
            Label condition = new Label();
            condition.setParentId(parentLabel.getId());
            List<Label> childLabels = labelDao.listLabels(condition);
            for (Label la : childLabels) {
                labelIdSet.add(la.getId());
                findMoveLabelChild(la, labelIdSet);
            }
            parentLabel.setChildren(childLabels);
        }
    }

    public List<LabelsVo> findAllTree(Page pageInfo) {
        List<Label> labels = labelDao.findAllTree(pageInfo);
        return getLabelVos(labels, LabelStatus.USE);
    }

    public List<LabelsVo> findAllTree(Page page, Label label) {
        Criteria<Label> criteria = createCriteria(label);
        criteria.isNull("parent");
        criteria.eq("status", LabelStatus.USE);
        List<Label> labels;
        if (page != null) {
            labels = labelDao.findByCriteria(criteria, page);
        } else {
            labels = labelDao.findByCriteria(criteria);
        }
        return getLabelVos(labels, LabelStatus.USE);
    }

    public List<LabelsVo> findLabelSiteTree(Long siteId, Page pageInfo) {

//		Long id = siteId.getSysSite().getId();

        SysSite sysSite = sysSiteDao.load(siteId);

        List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);

        List<Label> labels = labelDao.findLabelSiteTree(units, pageInfo);

        return getLabelVos(labels, LabelStatus.USE);
    }

    public List<LabelsVo> findLabelSiteTree(Long siteId, Page page, Label label) {
        SysSite sysSite = sysSiteDao.load(siteId);
        List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
        Criteria<Label> criteria = createCriteria(label);
        criteria.isNull("parent");
        criteria.in("sysUnit", units);
        criteria.eq("status", LabelStatus.USE);
        List<Label> labels;
        if (page != null) {
            labels = labelDao.findByCriteria(criteria, page);
        } else {
            labels = labelDao.findByCriteria(criteria);
        }
        return getLabelVos(labels, LabelStatus.USE);
    }

    public List<LabelsVo> findLabelUnitTree(SysUnit companyUnit, Page pageInfo) {
        List<Label> labels = labelDao.findLabelUnitTree(companyUnit, pageInfo);

        return getLabelVos(labels, LabelStatus.USE);
    }

    public List<LabelsVo> findLabelUnitTree(SysUnit sysUnit, Page page, Label label) {
        Criteria criteria = createCriteria(label);
        criteria.isNull("parent");
        criteria.eq("sysUnit", sysUnit);
        criteria.eq("status", LabelStatus.USE);
        List<Label> labels;
        if (page != null) {
            labels = labelDao.findByCriteria(criteria, page);
        } else {
            labels = labelDao.findByCriteria(criteria);
        }
        return getLabelVos(labels, LabelStatus.USE);
    }


    public List<Label> findLabelsByParent(Long lId) {
        return labelDao.findLabelByParent(lId);
    }

    public Label findUnique(Label label) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        if (StringUtils.isNotBlank(label.getName())) {
            criteria.eq("name", label.getName());
        }
        if (label.getParent() != null) {
            criteria.eq("parent.id", label.getParent().getId());
        }
        if (label.getStatus() != null) {
            criteria.eq("status", label.getStatus());
        }
        criteria.ne("status", LabelStatus.DEL);
        List<Label> labels = labelDao.findByCriteria(criteria);

        if (labels.isEmpty()) {
            return  null;
        }

        return labels.get(0);
    }

    /**
     * 通过标签名称规则模糊查询标签
     *
     * @param labelName
     * @return
     */
    public List<Label> findParentLabelListByName(String labelName) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.like("name", labelName, MatchMode.START);
        criteria.orderBy("sort", "asc");
//        criteria.isNull("parent");
        return labelDao.findByCriteria(criteria);
    }

    /**
     * 查询标签列表
     * @param label
     * @param targetType    查询产品已关联的标签
     * @param targetId
     * @return
     */
    public List<LabelsVo> listLabels(Label label, TargetType targetType, Long targetId) {
        List<Label> labels = labelDao.listLabels(label);
        // 查询产品已关联的标签
        Set<Long> labelItemIdSet = new HashSet<Long>();
        if (targetType != null && targetId != null) {
            List<LabelItem> labelItems = labelItemDao.findItemByTagType(targetId, targetType);
            for (LabelItem item : labelItems) {
                labelItemIdSet.add(item.getLabel().getId());
            }
        }
        // 为了适应关键字查询，特殊处理，遍历父节点
        String queryParentId = "-1";
        if (label.getParentId() != null) {
            queryParentId = String.valueOf(label.getParentId());
        }
        Map<String, List<LabelsVo>> parentIdAndChildrenMap = new LinkedHashMap<String, List<LabelsVo>>();
        Map<Long, LabelsVo> idAndLabelsMap = new LinkedHashMap<Long, LabelsVo>();
        for (Label la : labels) {
            // 判断是否已经处理过的标签（去除重复标签）
            LabelsVo vo = null;
            if (idAndLabelsMap.containsKey(la.getId())) {
                continue;
            } else {
                vo = buildLabelsVo(la, labelItemIdSet);
                idAndLabelsMap.put(vo.getId(), vo);
            }
            // 处理父节点
            String parentId = "-1";
            if (vo.getParentId() != null) {
                parentId = vo.getParentId();
            }
            if (parentIdAndChildrenMap.containsKey(parentId)) { // 已经存在父节点，取出并加上该标签
                parentIdAndChildrenMap.get(parentId).add(vo);
            } else {
                List<LabelsVo> children = new ArrayList<LabelsVo>();
                children.add(vo);
                parentIdAndChildrenMap.put(parentId, children);
            }
            // 如果当前查询结果标签不是所查节点标签，递归查询出父节点直到所查节点标签
            if (!queryParentId.equals(parentId)) {
                findParent(queryParentId, parentIdAndChildrenMap, idAndLabelsMap, la.getParent(), labelItemIdSet);
            }
        }

        // 处理返回结果
        List<LabelsVo> labelVos = new ArrayList<LabelsVo>();
        Set<Long> idSet = idAndLabelsMap.keySet();
        Iterator<Long> idIts = idSet.iterator();
        while (idIts.hasNext()) {
            Long id = idIts.next();
            LabelsVo vo = idAndLabelsMap.get(id);
            if (parentIdAndChildrenMap.containsKey(String.valueOf(id))) {   // 存在子节点，则设置children
                vo.setChildren(parentIdAndChildrenMap.get(String.valueOf(id)));
            }  // else 可以考虑处理不是非叶子节点但未查询子节点的样式，但要考虑该标签不可关联
            if ((vo.getParentId() == null && "-1".equals(queryParentId)) || queryParentId.equals(vo.getParentId())) {
                labelVos.add(vo);
            }
        }
        return labelVos;
    }
    /**
     * 查询标签列表
     * @param label
     * @param targetType    查询产品已关联的标签
     * @param targetId
     * @return
     */
    public List<LabelsVo> listNoExistsLabels(Label label, TargetType targetType, Long targetId) {
        List<Label> labels = labelDao.getNoExistsLabelsList(label, targetType, targetId);
        // 查询产品已关联的标签
        Set<Long> labelItemIdSet = new HashSet<Long>();
        if (targetType != null && targetId != null) {
            List<LabelItem> labelItems = labelItemDao.findItemByTagType(targetId, targetType);
            for (LabelItem item : labelItems) {
                labelItemIdSet.add(item.getLabel().getId());
            }
        }
        // 为了适应关键字查询，特殊处理，遍历父节点
        String queryParentId = "-1";
        if (label.getParentId() != null) {
            queryParentId = String.valueOf(label.getParentId());
        }
        Map<String, List<LabelsVo>> parentIdAndChildrenMap = new LinkedHashMap<String, List<LabelsVo>>();
        Map<Long, LabelsVo> idAndLabelsMap = new LinkedHashMap<Long, LabelsVo>();
        for (Label la : labels) {
            // 判断是否已经处理过的标签（去除重复标签）
            LabelsVo vo = null;
            if (idAndLabelsMap.containsKey(la.getId())) {
                continue;
            } else {
                vo = buildLabelsVo(la, labelItemIdSet);
                idAndLabelsMap.put(vo.getId(), vo);
            }
            // 处理父节点
            String parentId = "-1";
            if (vo.getParentId() != null) {
                parentId = vo.getParentId();
            }
            if (parentIdAndChildrenMap.containsKey(parentId)) { // 已经存在父节点，取出并加上该标签
                parentIdAndChildrenMap.get(parentId).add(vo);
            } else {
                List<LabelsVo> children = new ArrayList<LabelsVo>();
                children.add(vo);
                parentIdAndChildrenMap.put(parentId, children);
            }
            // 如果当前查询结果标签不是所查节点标签，递归查询出父节点直到所查节点标签
            if (!queryParentId.equals(parentId)) {
                findParent(queryParentId, parentIdAndChildrenMap, idAndLabelsMap, la.getParent(), labelItemIdSet);
            }
        }

        // 处理返回结果
        List<LabelsVo> labelVos = new ArrayList<LabelsVo>();
        Set<Long> idSet = idAndLabelsMap.keySet();
        Iterator<Long> idIts = idSet.iterator();
        while (idIts.hasNext()) {
            Long id = idIts.next();
            LabelsVo vo = idAndLabelsMap.get(id);
            if (parentIdAndChildrenMap.containsKey(String.valueOf(id))) {   // 存在子节点，则设置children
                vo.setChildren(parentIdAndChildrenMap.get(String.valueOf(id)));
            }  // else 可以考虑处理不是非叶子节点但未查询子节点的样式，但要考虑该标签不可关联
            if ((vo.getParentId() == null && "-1".equals(queryParentId)) || queryParentId.equals(vo.getParentId())) {
                labelVos.add(vo);
            }
        }
        return labelVos;
    }

    /**
     * 构建LabelsVo
     * @param la
     * @return
     */
    private LabelsVo buildLabelsVo(Label la, Set<Long> labelItemIdSet) {
        LabelsVo labelsVo = new LabelsVo();
        labelsVo.setId(la.getId());
        labelsVo.setText(la.getName());
        labelsVo.setName(la.getName());
        labelsVo.setAlias(la.getAlias());
        labelsVo.setSort(String.valueOf(la.getSort()));
        labelsVo.setLevel(la.getLevel());
        labelsVo.setCreateTime(la.getCreateTime());
        labelsVo.setStatus(la.getStatus().toString());
        labelsVo.setLeaf(la.getLeaf());
        if (la.getLeaf() != null && !la.getLeaf()) {
            labelsVo.setState("closed");
        }
        if (labelItemIdSet.contains(la.getId())) {
            labelsVo.setSelected(true);
        } else {
            labelsVo.setSelected(false);
        }
        if (la.getParent() != null) {
            labelsVo.setParentId(la.getParent().getId().toString());
        }
        return labelsVo;
    }

    /**
     * 如果当前查询结果标签不是所查节点标签，递归查询出父节点直到所查节点标签
     */
    public void findParent(String queryParentId, Map<String, List<LabelsVo>> parentIdAndChildrenMap,
                           Map<Long, LabelsVo> idAndLabelsMap, Label la, Set<Long> labelItemIdSet) {
        // 判断是否已经处理过的标签（去除重复标签）
        LabelsVo vo = null;
        if (idAndLabelsMap.containsKey(la.getId())) {
            return;
        } else {
            vo = buildLabelsVo(la, labelItemIdSet);
            idAndLabelsMap.put(vo.getId(), vo);
        }
        // 处理父节点
        String parentId = "-1";
        if (vo.getParentId() != null) {
            parentId = vo.getParentId();
        }
        if (parentIdAndChildrenMap.containsKey(parentId)) { // 已经存在父节点，取出并加上该标签
            parentIdAndChildrenMap.get(parentId).add(vo);
        } else {
            List<LabelsVo> children = new ArrayList<LabelsVo>();
            children.add(vo);
            parentIdAndChildrenMap.put(parentId, children);
        }
        // 如果当前查询结果标签不是所查节点标签，递归查询出父节点直到所查节点标签
        if (!queryParentId.equals(parentId)) {
            findParent(queryParentId, parentIdAndChildrenMap, idAndLabelsMap, la.getParent(), labelItemIdSet);
        }
    }

    /**
     * 查询已关联标签列表
     * @param targetType    查询产品已关联的标签
     * @param targetId
     * @return
     */
    public List<LabelsVo> listBindLabels(TargetType targetType, Long targetId) {
        List<LabelsVo> labelVos = new ArrayList<LabelsVo>();
        List<LabelItem> labelItems = labelItemDao.findItemByTagType(targetId, targetType);
        for (LabelItem item : labelItems) {
            Label la = item.getLabel();
            LabelsVo labelsVo = new LabelsVo();
            labelsVo.setId(la.getId());
            labelsVo.setText(la.getName());
            labelsVo.setName(la.getName());
            labelsVo.setAlias(la.getAlias());
            labelsVo.setSort(String.valueOf(item.getOrder())); // 取关联标签的排序字段
            labelsVo.setLevel(la.getLevel());
            labelsVo.setCreateTime(la.getCreateTime());
            labelsVo.setStatus(la.getStatus().toString());
            labelsVo.setLeaf(la.getLeaf());
            StringBuilder dir = new StringBuilder("/");
            buildDir(la, dir);
            labelsVo.setDir(dir.toString());
            labelVos.add(labelsVo);
        }
        return labelVos;
    }

    /**
     * 构建标签所在路径
     * @param label
     * @param dir
     */
    private void buildDir(Label label, StringBuilder dir) {
        if (label.getParent() != null) {
            dir.insert(1, label.getParent().getName() + "/");
            buildDir(label.getParent(), dir);
        }
    }

    /**
     * 保存或更新Label
     * 新增保存，需更新上一级标签的leaf属性
     * 更新时，如果状态为隐藏则更新所有子节点状态为隐藏
     *
     * @param label
     */
    public void saveLabel(Label label) {
        if (label.getId() != null) {    // 更新时，如果状态为隐藏则更新所有子节点状态为隐藏
            labelDao.update(label);
            if (label.getStatus() == LabelStatus.IDLE) {
                updateChildren(label.getId(), LabelStatus.IDLE);
            }
        } else {    // 新增保存，需更新上一级标签的leaf属性
            Label parentLabel = label.getParent();
            if (parentLabel != null) {
                label.setLevel(parentLabel.getLevel() + 1);
                if (parentLabel.getLeaf()) {
                    parentLabel.setLeaf(false);
                    labelDao.update(parentLabel);
                }
            } else {
                label.setLevel(1);
            }
            label.setCreateTime(new Date());
            label.setLeaf(true);
            labelDao.save(label);
        }
    }

    /**
     * 删除标签，同时删除所有子标签，并判断是否是父标签最后一个子标签，如果是则更新父级标签为叶子节点
     * @param label
     * @return 返回需要更新的父节点
     */
    public Long delLabel(Label label) {
        label.setStatus(LabelStatus.DEL);
        labelDao.update(label);
        labelItemDao.delByLabelId(label.getId());
        updateChildren(label.getId(), LabelStatus.DEL);
        if (label.getParent() != null) {
            Long count = labelDao.countChildren(label.getParent().getId()); // 父节点的剩余子节点数
            if (count <= 0) {
                Label parentLabel = label.getParent();
                parentLabel.setLeaf(true);
                labelDao.update(parentLabel);
                return parentLabel.getId();
            }
        }
        return null;
    }

    /**
     * 递归更新子标签状态
     * @param parenId
     * @param status
     */
    public void updateChildren(Long parenId, LabelStatus status) {
        Label paramLabel = new Label();
        paramLabel.setParentId(parenId);
        if (status == LabelStatus.IDLE) {   // 如果是更新为隐藏，只查询正常状态
            paramLabel.setStatus(LabelStatus.USE);
        }
        List<Label> childLabels = labelDao.listLabels(paramLabel);
        if (childLabels.size() <= 0) {
            return;
        }
        for (Label childLabel : childLabels) {
            childLabel.setStatus(status);
            labelDao.update(childLabel);
            labelItemDao.delByLabelId(childLabel.getId());
            updateChildren(childLabel.getId(), status);
        }
    }

    /**
     * 标签名称模糊查询
     * @param labelName
     * @return
     */
    public List<Label> listLabelByKey(String labelName) {
        Page pageInfo = new Page(1, 20);
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.like("name", labelName, MatchMode.START);
        criteria.eq("status", LabelStatus.USE);
        criteria.orderBy("sort", "asc");
        List<Label> labels = labelDao.findByCriteria(criteria, pageInfo);
        return labels;
    }

}
