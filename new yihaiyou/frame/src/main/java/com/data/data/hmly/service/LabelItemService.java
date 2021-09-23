package com.data.data.hmly.service;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.dao.LabelDao;
import com.data.data.hmly.service.dao.LabelItemDao;
import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.vo.ProductLabelVo;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LabelItemService {

    Logger logger = Logger.getLogger(LabelItemService.class);

    private static final String CORE_NAME = "labelItem";

    @Resource
    private LabelItemDao labelItemDao;
    @Resource
    private LabelDao labelDao;
    @Resource
    private SysSiteDao sysSiteDao;
    @Resource
    private SysUnitDao sysUnitDao;
    @Resource
    private TbAreaDao areaDao;
    @Resource
    private MulticoreSolrTemplate solrTemplate;


//	public List<T> getScenicLabels(Object info, TbArea area, String tagIds, Page pageInfo) {
//		
//	}


    public List<Object> getScenicLabels(Object obj, String name, TbArea area, String tagIds, Page pageInfo) throws ClassNotFoundException {

        List<Object> infos = labelItemDao.getScenicLabels(obj, name, area, tagIds, pageInfo);

        return infos;
    }

    public List<T> getScenicLabels(T obj, String name, TbArea area, String tagIds, Page pageInfo) throws ClassNotFoundException {

        List<T> infos = null;
//		infos = labelItemDao.getScenicLabels(obj,name,area,tagIds,pageInfo);
        return infos;
    }

    public List<LabelItem> getLabelItemsByParent(Long targetId, Long parentId, LabelStatus status) {
        LabelItem condition = new LabelItem();
        List<Long> ids = labelDao.listIdsByParent(parentId, status);
        condition.setLabelIds(ids);
        condition.setTargetId(targetId);
        return list(condition, null);
    }

    public List<ProductLabelVo> findProItemList(TargetType type) {
        List<ProductLabelVo> labelVos = new ArrayList<ProductLabelVo>();
        if (type.equals(TargetType.CITY)) {
            List<TbArea> areas = null;
            List<Long> areaIds = new ArrayList<Long>();
            for (TbArea area : areas) {
                areaIds.add(area.getId());
            }
            List<LabelItem> labelItems = labelItemDao.findLabelItemByTargIds(areaIds);
            for (TbArea a : areas) {
                ProductLabelVo labelVo = new ProductLabelVo();
                List<Label> labels = new ArrayList<Label>();
                for (LabelItem ltem : labelItems) {

                    if (ltem.getTargetId() == a.getId()) {
                        labels.add(ltem.getLabel());
                    }
                }
                labelVo.setLabels(labels);
                labelVo.setId(a.getId());
                labelVo.setName(a.getName());
                labelVos.add(labelVo);
            }


        }
        if (type.equals(TargetType.DELICACY)) {

        }
        if (type.equals(TargetType.PLAN)) {

        }
        if (type.equals(TargetType.PRODUCT)) {

        }
        if (type.equals(TargetType.RECOMMEND_PLAN)) {

        }
        if (type.equals(TargetType.SCENIC)) {

        }

        return labelVos;
    }

    public List<LabelItem> listAll() {
        return list(new LabelItem(), null);
    }

    public List<LabelItem> list(LabelItem labelItem, Page page, String... orderProperties) {
        Criteria<LabelItem> criteria = createCriteria(labelItem, orderProperties);
        if (page == null) {
            return labelItemDao.findByCriteria(criteria);
        }
        return labelItemDao.findByCriteria(criteria, page);
    }

    public Criteria<LabelItem> createCriteria(LabelItem labelItem, String... orderProperties) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (labelItem.getLabel() != null) {
            criteria.eq("label.id", labelItem.getLabel().getId());
        }

        if (labelItem.getTargetId() != null && labelItem.getTargetId() > 0) {
            criteria.eq("targetId", labelItem.getTargetId());
        }
        if (labelItem.getLabelIds() != null && labelItem.getLabelIds().size() > 0) {
            criteria.in("label.id", labelItem.getLabelIds());
        }
        return criteria;
    }

    public List<Label> findAllByTargId(Long targId) {

        List<Label> labels = new ArrayList<Label>();
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetId", targId);

        List<LabelItem> items = labelItemDao.findByCriteria(criteria);

        for (LabelItem it : items) {

            LabelItem item = labelItemDao.load(it.getId());

            labels.add(item.getLabel());
        }
        return labels;
    }

    public List<Label> findLabelSiteByTargId(Long siteId, Long targId, String type) {

        List<Label> labels = new ArrayList<Label>();
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetId", targId);
        criteria.eq("targetType", getType(type));

        List<LabelItem> items = labelItemDao.findByCriteria(criteria);

        List<Long> lebIds = new ArrayList<Long>();

        SysSite sysSite = sysSiteDao.load(siteId);

        List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
        List<Label> labelsBySite = new ArrayList<Label>();
        labelsBySite = labelDao.findLabelSite(units);
        for (Label la : labelsBySite) {
            for (LabelItem it : items) {
                if (la.getId() == it.getLabel().getId()) {
                    la.setTargSort(it.getOrder());
                    labels.add(la);
                }
            }

        }

        return labels;
    }

    public List<Label> findLabelUnitByTargId(SysUnit sysUnit, Long targId, String type) {

        List<Label> labels = new ArrayList<Label>();
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetId", targId);
        criteria.eq("targetType", getType(type));
        List<LabelItem> items = labelItemDao.findByCriteria(criteria);
        List<Long> lebIds = new ArrayList<Long>();

        for (LabelItem it : items) {
            lebIds.add(it.getLabel().getId());
        }
        labels = labelDao.findLabelUnitByTargId(sysUnit, lebIds);

        List<Label> resultLabels = new ArrayList<Label>();

        for (Label la : labels) {
            for (LabelItem it : items) {
                if (la.getId() == it.getLabel().getId()) {
                    la.setTargSort(it.getOrder());
                    resultLabels.add(la);
                }
            }

        }

        return resultLabels;
    }

    public List<Label> findLabelListByTargId(long targId) {

        List<Label> labels = new ArrayList<Label>();
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetId", targId);
        List<LabelItem> items = labelItemDao.findByCriteria(criteria);

        for (LabelItem it : items) {

            labels.add(it.getLabel());
        }
        return labels;
    }

    /**
     * 通过targetId和type获取数据
     *
     * @param targId
     * @param type
     * @return
     */
    public List<Label> findAllByTargId(Long targId, String type) {

        List<Label> labels = new ArrayList<Label>();
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetId", targId);
        criteria.eq("targetType", getType(type));
        List<LabelItem> items = labelItemDao.findByCriteria(criteria);

        for (LabelItem it : items) {
            LabelItem item = labelItemDao.load(it.getId());
            Label label = item.getLabel();
            label.setTargSort(item.getOrder());
            labels.add(label);
        }
        return labels;

    }


    public TargetType getType(String type) {
        return TargetType.valueOf(type);
    }

    public List<Long> findTarIdsByLabelId(long labelId, String type) {

        List<Label> labels = labelDao.findLabelByParent(labelId);
        List<Long> targetIds = new ArrayList<Long>();
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        if (labels.size() <= 0) {
            criteria.eq("label.id", labelId);
        } else {
            criteria.in("label", labels);
        }
        criteria.eq("targetType", getType(type));
        List<LabelItem> items = labelItemDao.findByCriteria(criteria);
        for (LabelItem it : items) {
            targetIds.add(it.getTargetId());
//			 if(labels.size()>0){
//				 for(Label la:labels){
//					 targetIds.add(it.getTargetId());
//				 }
//			 }else{
//				 targetIds.add(it.getTargetId());
//			 }

        }
        return targetIds;
    }

    public void saveOrUpdate(String targetId, String labelIds, String itemSorts, String type) {

        List<LabelItem> oldItems = labelItemDao.findItemByTagType(Long.parseLong(targetId), getType(type));
        List<LabelItem> newItems = new ArrayList<LabelItem>();
//		labelItemDao.deleteAll(oldItems);
        if (StringUtils.isNotBlank(labelIds)) {
            String[] lIdStrs = labelIds.split(",");
            String[] sortStrs = itemSorts.split(",");
            Long[] ids = new Long[lIdStrs.length];
            int[] sortLs = new int[sortStrs.length];
            for (int i = 0; i < lIdStrs.length; i++) {
//				String[] strArr = labelIds.split(";");
                ids[i] = Long.parseLong(lIdStrs[i]);
                LabelItem labelItem = labelItemDao.findItemByTagTypeId(ids[i], Long.parseLong(targetId), getType(type));
                labelItemDao.delete(labelItem);
                sortLs[i] = Integer.parseInt(sortStrs[i]);
            }

            for (int i = 0; i < ids.length; i++) {
                Label label = labelDao.finLabelById(ids[i]);
                LabelItem item = new LabelItem();
                item.setLabel(label);
                item.setTargetId(Long.parseLong(targetId));
                item.setTargetType(getType(type));
                item.setCreateTime(new Date());
                item.setOrder(sortLs[i]);
                labelItemDao.save(item);
            }
            /*

			for(LabelItem itme:oldItems){
				for(int i=0;i<ids.length;i++){
					if(itme.getLabel().getId()==ids[i]){
						itme.setOrder(sortLs[i]);
						newItems.add(itme);
						labelItemDao.update(itme);
					}else{
						Label label = labelDao.finLabelById(ids[i]);
						LabelItem item = new LabelItem();
						item.setLabel(label);
						item.setTargetId(Long.parseLong(targetId));
						item.setTargetType(getType(type));
						item.setCreateTime(new Date());
						item.setOrder(sortLs[i]);
						labelItemDao.save(item);
						newItems.add(itme);
					}
					
				}
				
			}
			*/

        }

//		labelItemDao.deleteAll(oldItems); 	//删除旧的产品已贴标签
		
		/*
		
		if(StringUtils.isNotBlank(labelIds)){
			String[] lIdStrs = labelIds.split(",");
			Long[] ids = new Long[lIdStrs.length];
			int[] sortLs = new int[lIdStrs.length];
			for(int i=0;i<lIdStrs.length;i++){
				String[] strArr = labelIds.split(";");
				ids[i] = Long.parseLong(strArr[0]);
				sortLs[i] = Integer.parseInt(strArr[1]);
			}
			List<Label> labels = labelDao.finLabelsByIds(ids);
			int i = 1;
			for(Label la:labels){
				LabelItem item = new LabelItem();
				item.setLabel(la);
				item.setTargetId(Long.parseLong(targetId));
				item.setTargetType(getType(type));
				item.setCreateTime(new Date());
				item.setOrder(i++);
				labelItemDao.save(item);
			}
		}
		
		*/

    }

    public List<LabelItem> beforeSaveItems(Long tagId, List<Label> labels, TargetType type) {
        List<LabelItem> items = new ArrayList<LabelItem>();
        int i = 1;
        for (Label la : labels) {
            LabelItem item = new LabelItem();
            item.setLabel(la);
            item.setTargetId(tagId);
            item.setTargetType(type);
            item.setCreateTime(new Date());
            item.setOrder(i++);
            items.add(item);
        }
        return items;

    }

    public boolean checkTargs(long targId) {
        boolean flag = false;
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetId", targId);

        LabelItem item = labelItemDao.findUniqueByCriteria(criteria);

        if (item != null) {
            flag = true;

        }
        return flag;
    }

    public boolean checkSortByLaId(Long laId, Long targetId, String type, int sort) {

        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("label.id", laId);
        criteria.eq("targetType", getType(type));
        List<LabelItem> items = labelItemDao.findByCriteria(criteria);
        boolean flag = true;
        for (LabelItem labelItem : items) {
            if (!targetId.equals(labelItem.getTargetId())) {
                if (labelItem.getOrder() == sort) {
                    flag = false;
                    break;
                }
            }

        }
        return flag;
    }


    public LabelItem findItem(long targetId, long laId, String type) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("label.id", laId);
        criteria.eq("targetId", targetId);
        criteria.eq("targetType", getType(type));
        return labelItemDao.findUniqueByCriteria(criteria);
    }

    public boolean saveSort(long labelId, long targetId, String type,
                            int sort) {
        boolean flag = checkSortByLaId(labelId, targetId, type, sort);
        if (flag) {
            Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
            criteria.eq("label.id", labelId);
            criteria.eq("targetId", targetId);
            criteria.eq("targetType", getType(type));
            LabelItem item = labelItemDao.findUniqueByCriteria(criteria);
            item.setOrder(sort);
        }
        return flag;
    }

    public LabelItem saveItem(String laId, String targetId, String type) {

        LabelItem item = new LabelItem();
        if (StringUtils.isNotBlank(targetId)) {
            item.setTargetId(Long.parseLong(targetId));
        }
        if (StringUtils.isNotBlank(laId)) {
            Label label = labelDao.finLabelById(Long.parseLong(laId));
            item.setLabel(label);
        }
        if (StringUtils.isNotBlank(type)) {
            item.setTargetType(getType(type));
        }
        item.setCreateTime(new Date());
        int maxOrder = labelItemDao.getMaxOrder(Long.parseLong(laId), TargetType.valueOf(type));
        item.setOrder(maxOrder + 1);
        labelItemDao.save(item);
        return item;
    }

    public void saveAll(List<LabelItem> labelItems) {
        labelItemDao.save(labelItems);
    }

    public int getLabelSize(long labelId, String type) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("label.id", labelId);
        criteria.eq("targetType", getType(type));
        List<LabelItem> items = labelItemDao.findByCriteria(criteria);
        int max = 0;
        if (items.size() > 0) {
            max = items.get(0).getOrder();
            for (int n = 0; n < items.size(); n++) {
                if (max < items.get(n).getOrder()) {
                    max = items.get(n).getOrder();
                }
            }
        }

        return max;
    }

    public void deleteItem(Long laId, Long targetId, String type) {

        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("label.id", laId);
        criteria.eq("targetId", targetId);
        criteria.eq("targetType", getType(type));
        LabelItem item = labelItemDao.findUniqueByCriteria(criteria);
        labelItemDao.delete(item);

    }

    /**
     * 删除产品标签关联
     * @param laId
     * @param targetId
     * @param type
     */
    public void deleteLabelItem(Long laId, Long targetId, String type) {
        String hql = " delete LabelItem where label.id = ? and targetId = ? and targetType = ?";
        labelItemDao.updateByHQL(hql, laId, targetId, getType(type));
    }

    /**
     * 更新排序，上移下移统一交换两个记录的排序字段值
     * @param toUpId
     * @param toDownId
     */
    public void doSwapOrder(Long toUpId, Long toDownId) {
        LabelItem toUpLabelItem = labelItemDao.load(toUpId);
        LabelItem toDownLabelItem = labelItemDao.load(toDownId);
        Integer oldUpLabelItemOrder = toUpLabelItem.getOrder();
        toUpLabelItem.setOrder(toDownLabelItem.getOrder());
        toDownLabelItem.setOrder(oldUpLabelItemOrder);
        labelItemDao.update(toUpLabelItem);
        labelItemDao.update(toDownLabelItem);
    }

    public void deleteAll(List<LabelItem> labelItems) {
        labelItemDao.deleteAll(labelItems);
    }

    public void deleteById(Long id) {
        labelItemDao.delete(id, LabelItem.class);
    }

    public List<LabelItem> findTitemList(List<Long> targIds,
                                         TargetType type) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.in("targetId", targIds);
        criteria.eq("targetType", type);

        return labelItemDao.findByCriteria(criteria);
    }

    public List<LabelItem> findItemByTargId(Long targId, TargetType type) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetId", targId);
        criteria.eq("targetType", type);
        return labelItemDao.findByCriteria(criteria);
    }

    public boolean editSort(long itemId, Integer sort) {
        LabelItem item = labelItemDao.load(itemId);
        boolean flag = true;
        if (sort.equals(item.getOrder())) {
            flag = false;
        } else {
            item.setOrder(sort);
            flag = true;
        }

        return flag;
    }

    public List<LabelItem> getLabelItemByType(TargetType city) {
        return labelItemDao.getLabelItemByType(city);
    }

    public List<LabelItem> getByLabel(Long labelId) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("label.id", labelId);
        return labelItemDao.findByCriteria(criteria);
    }

    /**
     * 查询产品标签
     * @param labelId
     * @param type
     * @return
     */
    public List<LabelItem> findProductLabel(Long labelId, TargetType type, String cityStr) {
        return labelItemDao.findProductLabel(labelId, type, cityStr);
    }

    /**
     * 功能描述：删除产品标签关系
     * @param targetId
     * @param targetType
     */
    public void delItemListByTargetIdId(Long targetId, TargetType targetType) {
        String hql = " delete LabelItem where targetId = ? and targetType = ?";
        labelItemDao.updateByHQL(hql, targetId, targetType);
    }

    public List<LabelItem> findTargIdsByLabel(Label label, TargetType targetType, Page pageInfo) {

        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        DetachedCriteria detachedCriteria = criteria.createCriteria("label", "l");
        if (StringUtils.isNotBlank(label.getName())) {
            criteria.eq("l.name", label.getName());
        }

        if (label.getId() != null) {
            criteria.eq("l.id", label.getId());
        }

        criteria.eq("targetType", targetType);

        return labelItemDao.findByCriteria(criteria, pageInfo);
    }

    public List<LabelItem> findItemsByLabel(Label label, Page page) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        if (label.getId() != null) {
            criteria.eq("l.id", label.getId());
        }
        return labelItemDao.findByCriteria(criteria, page);
    }
}
