package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.hmlyinfo.app.soutu.base.CacheKey;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.mapper.ScenicInfoMapper;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPrice;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarSight;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarSightTicketRelation;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarPriceMapper;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarSightTicketRelationMapper;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarTicketMapper;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class QunarTicketService extends BaseService<QunarTicket, Long>{
	
	private static final String PARTNER_CODE = "3999347859";
	private static final String	PARTNER_KEY = "a61ac440fe54d5e99025d5541aa12038";

	private CacheProvider cacheProvicer = XMemcachedImpl.getInstance();
	@Autowired
	private QunarTicketMapper<QunarTicket> mapper;
	@Autowired
	private QunarPriceMapper<QunarPrice> priceMapper;
	@Autowired
	private ScenicInfoMapper<ScenicInfo> scenicMapper;
	@Autowired
	private QunarSightTicketRelationMapper<QunarSightTicketRelation> relMapper;
	@Autowired
	private QunarService qunarService;
	@Autowired
	private QunarPriceService qunarPriceService;
	@Autowired
	private ScenicInfoService scenicInfoService;
	@Autowired
	private QunarOrderService qunarOrderService;
	@Autowired
	private QunarSightService qunarSightService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public BaseMapper<QunarTicket> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 通过scenicIds查询首要门票
	 * @param scenicIds
	 * @return
	 */
	public List<QunarTicket> getPrice(Map<String, Object> params, List<QunarTicket> ticketList)
	{
		// 查询门票价格
		List<String> cList = Lists.newArrayList();
		List<String> tList = Lists.newArrayList();
		for (QunarTicket t : ticketList)
		{
			if (t.getTeamType() == 0)
			{
				cList.add(t.getProductId());
			}
			else if (t.getTeamType() == 1)
			{
				tList.add(t.getProductId());
			}
		}
		
		// 先处理价格模式为日历的门票
		Date date;
        if (params.get("date") == null) {
            date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);
            date = calendar.getTime();
        }else {
            long dateMil = Long.parseLong(params.get("date").toString());
            date = new Date(dateMil);
        }
        
		
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("productIds", cList);
		paramMap.put("useDate", sdf.format(date));
		List<QunarPrice> cpList = qunarPriceService.list(paramMap);
		Map<String, QunarPrice> cpMap = Maps.newHashMap();
		for (QunarPrice p : cpList)
		{
			cpMap.put(p.getProductId(), p);
		}
		for (QunarTicket t : ticketList)
		{
			QunarPrice p = cpMap.get(t.getProductId());
			if (p != null)
			{
				t.setPrice(p);
			}
		}
		
		// 处理价格模式为分类的景点门票
		paramMap.clear();
		paramMap.put("productIds", tList);
		paramMap.put("displayDate", sdf.format(date));
		List<QunarPrice> tpList = qunarPriceService.list(paramMap);
		Map<String, QunarPrice> tpMap = Maps.newHashMap();
		for (QunarPrice p : tpList)
		{
			tpMap.put(p.getProductId(), p);
		}
		for (QunarTicket t : ticketList)
		{
			QunarPrice p = cpMap.get(t.getProductId());
			if (p != null)
			{
				t.setPrice(p);
			}
		}
		
		for (int i = ticketList.size() - 1; i >= 0; i--)
		{
			QunarTicket t = ticketList.get(i);
			if (t.getPrice() == null)
			{
				ticketList.remove(i);
			}
		}
		
		return ticketList;
	}
	
    public boolean hasTicket(Map<String, Object> paramMap, long scenicId) {

    	return ticketCount(paramMap, scenicId) > 0;
    }

    /**
     * 根据一个景点列表查询去哪儿门票列表
     * @param paramMap 景点列表
     * @param scenicId 主景点编号，用来标识缓存KEY
     * @return
     */
    public List<QunarTicket> listDetail(Map<String, Object> paramMap, long scenicId) {
		
        if (paramMap.get("date") == null) {
            String cacheKey = CacheKey.QUNAR_PRICE_PR + scenicId;
            List<QunarTicket> ticketList = cacheProvicer.get(cacheKey);
            if (ticketList != null) {
                return ticketList;
            }

            ticketList = getTicketList(paramMap, scenicId);
            ticketList = getPrice(paramMap, ticketList);

            // 将数据存储到缓存中
            cacheProvicer.set(cacheKey, ticketList, getNowToNextdaySeconds());

            return ticketList;
        }else {
            List<QunarTicket> ticketList = getTicketList(paramMap, scenicId);
            ticketList = getPrice(paramMap, ticketList);
            return ticketList;
        }
	}
    
    private List<QunarTicket> getTicketList(Map<String, Object> paramMap, long scenicId)
    {
    	String cacheKey = CacheKey.QUNAR_TICKET_PR + scenicId;
    	// 尝试从缓存中获取数据
    	List<QunarTicket> ticketList = cacheProvicer.get(cacheKey);
    	if (ticketList != null)
    	{
    		return ticketList;
    	}
    	
    	List<Long> scenicIdList = (List<Long>) paramMap.get("scenicIds");
		
		List<Long> sightIdList = new ArrayList<Long>();
		
		/**
		 * 根据本地景点id列表查询qunar景点id列表
		 */
		Map<String, Object> sightMap = Maps.newHashMap();
		sightMap.put("scenicIds", scenicIdList);
		List<QunarSight> sightList = qunarSightService.list(sightMap);
		sightIdList = ListUtil.getIdList(sightList, "id");
		
		/**
		 * 根据sightId列表查询productId列表
		 */
		Map<String, Object> relMap = Maps.newHashMap();
		relMap.put("sightIds", sightIdList);
		List<QunarSightTicketRelation> relList = relMapper.list(relMap);
		Set productIds = ListUtil.getIdSet(relList, "productId");
		
		// 根据productId列表查询门票列表
		Map<String, Object> ticketMap = Maps.newHashMap();
		ticketMap.put("productIds", productIds);
		ticketList = mapper.list(ticketMap);
		
		// 将数据保存到缓存中
		// 计算需要存储的时长，存储到第二天的凌晨
		// TODO 后续考虑从每日同步作业中来维护这个缓存数据
		long expiry = getNowToNextdaySeconds();
		cacheProvicer.set(cacheKey, ticketList, expiry);
		return ticketList;
    }
	
    //根据scenicIds查询门票数量
	public int ticketCount(Map<String, Object> paramMap, long scenicId) {
		
		return getTicketList(paramMap, scenicId).size();
	}
	
	/**
	 * 计算从现在时间到凌晨的秒数
	 * @return
	 */
	private long getNowToNextdaySeconds()
	{
		Date now = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		Date zdate = calendar.getTime();
		return (zdate.getTime() - now.getTime()) / 1000;
	}
	
	/**
	 * 查询套票列表，根据套票相关景点数量排序
	 * @param scenicIds
	 * @return
	 */
	public List<QunarTicket> listSeasonTicket(List<Long> scenicIds)
	{
		List<QunarTicket> qtList = getSeasonTicketByScenicIds(scenicIds);
		
		if (qtList == null)
		{
			return null;
		}
		else 
		{
			
			for(QunarTicket qt : qtList){
				Set<Long> qtScenicList = qt.getScenicIdList();
				Set<Long> sceColSet = new HashSet<Long>();
				for(long sc : scenicIds){
					sceColSet.add(sc);
				}
				sceColSet.retainAll(qtScenicList);
				qt.setComSize(sceColSet.size());
			}
			Collections.sort(qtList, new Comparator<QunarTicket>()
			{
				@Override
				public int compare(QunarTicket o1, QunarTicket o2) 
				{
					if (o1.getComSize() != o2.getComSize()) {
						return o1.getComSize() - o2.getComSize();
					}else {
						double price = o1.getPrice().getSalePrice() - o2.getPrice().getSalePrice();
						if(price > 0){
							return 1;
						}else {
							return -1;
						}
					}
				}
				
			});
			return qtList;
		}
	}
	
	private List<QunarTicket> getSeasonTicketByScenicIds(List<Long> scenicIds)
	{
		/**
		 *  套票的缓存结构为{scenicId : [{套票id1, 景点列表 : [scenidId1, scenidId2, ...]}, {套票id2, 景点列表 : [scenidId1, scenidId2, ...]}, {...}]}
		 *  目前缓存放置在memcached中，因为memcached没有数据结构，所以采用全部数据读取的方式
		 *  套票数据当采集完整以后可能会比较大，全量读取对于带宽利用和内存管理都是不好的方式，因为目前数据比较少，暂时没有问题
		 *  后续需要迁移到redis中，能够优化读取和内存管理
		 */
		// TODO 迁移缓存到redis中
		
		Multimap<Long, QunarTicket> cacheTicket = cacheProvicer.get(CacheKey.QUNAR_SEASON_TICKET_PR);
		// 如果缓存为空，则初始化一次
		// TODO 后续需要采用自动化作业每天生成一次缓存数据，以避免在第一次访问的时候速度慢的问题
		if (cacheTicket == null)
		{
			List<QunarTicket> ticketList = mapper.listSeasonTicket();
			Map<String, Object> interimMap = new HashMap<String, Object>();
			ticketList = getPrice(interimMap, ticketList);
			// 处理productId对应多个scenicId的关系
			Multimap<String, Long> productMap = ArrayListMultimap.create();
			for (QunarTicket t : ticketList)
			{
				// 对厦门特殊处理，套票中不需要包含鼓浪屿
				if (t.getScenicId() != 13165)
				{
					productMap.put(t.getProductId(), t.getScenicId());
				}
			}
			
			cacheTicket = ArrayListMultimap.create();
			for (QunarTicket t : ticketList)
			{
				QunarTicket nt = new QunarTicket();
				nt.setId(t.getId());
				nt.setProductName(t.getProductName());
				nt.setProductId(t.getProductId());
				Set<Long> scenicIdSet = Sets.newHashSet(productMap.get(nt.getProductId()));
				nt.setScenicIdList(scenicIdSet);
				nt.setPrice(t.getPrice());
				
				cacheTicket.put(t.getScenicId(), nt);
			}
			
			// 存入缓存
			cacheProvicer.set(CacheKey.QUNAR_SEASON_TICKET_PR, cacheTicket);
		}
		
		// 查询能够匹配这些景点列表的套票并且去重
		Map<String, QunarTicket> matchedMap = Maps.newHashMap();
		for (Long sid : scenicIds)
		{
			List<QunarTicket> ctList = (List<QunarTicket>) cacheTicket.get(sid);
			// 去重复
			for (QunarTicket ct : ctList)
			{
				matchedMap.put(ct.getProductId(), ct);
			}
		}
		
		// 查询不到匹配的套票信息
		if (matchedMap.values().isEmpty())
		{
			return null;
		}
		else 
		{
			List<QunarTicket> mapValuesList = new ArrayList<QunarTicket>(matchedMap.values());   
			return mapValuesList;
		}
	}
	
	
	
	//初始化缓存数据
	public void initCache(){
		new Thread(){
			public void run(){
				initTicketCache();
			}
		}.start();
		
		initSeasonTicketCache();
	}
	
	//初始化qunar门票和价格缓存
	public void initTicketCache(){
		Map<String, Object> params = new HashMap<String, Object>();
		List<ScenicInfo> scenicInfos = scenicInfoService.listColumns(params, Lists.newArrayList("id"));
		List<String> scenicIdList = ListUtil.getIdList(scenicInfos, "id");
		for(String scenicId : scenicIdList){
			List<Long> idList = Lists.newArrayList();
	    	// 处理子景点门票
	    	scenicInfoService.prepareScenic(idList, Long.parseLong(scenicId));
	    	Map<String, Object> scenicMap = new HashMap<String, Object>();
	    	scenicMap.put("scenicIds", idList);
	    	//初始化
	    	listDetail(scenicMap, Long.parseLong(scenicId));
		}
		
	}
	
	//初始化套票缓存
	public void initSeasonTicketCache(){
		
		Multimap<Long, QunarTicket> cacheTicket;
		List<QunarTicket> ticketList = mapper.listSeasonTicket();
		Map<String, Object> interimMap = new HashMap<String, Object>();
		ticketList = getPrice(interimMap, ticketList);
		// 处理productId对应多个scenicId的关系
		Multimap<String, Long> productMap = ArrayListMultimap.create();
		for (QunarTicket t : ticketList)
		{
			// 对厦门特殊处理，套票中不需要包含鼓浪屿
			if (t.getScenicId() != 13165)
			{
				productMap.put(t.getProductId(), t.getScenicId());
			}
		}
		
		cacheTicket = ArrayListMultimap.create();
		for (QunarTicket t : ticketList)
		{
			QunarTicket nt = new QunarTicket();
			nt.setId(t.getId());
			nt.setProductName(t.getProductName());
			nt.setProductId(t.getProductId());
			Set<Long> scenicIdSet = Sets.newHashSet(productMap.get(nt.getProductId()));
			nt.setScenicIdList(scenicIdSet);
			nt.setPrice(t.getPrice());
			
			cacheTicket.put(t.getScenicId(), nt);
		}
		
		// 存入缓存
		cacheProvicer.set(CacheKey.QUNAR_SEASON_TICKET_PR, cacheTicket);
	}
	
	// 可用库存查询
	public int availableCount(String productId, String date){
		int count = 0;
		Map<String, Object> ticketInfo = qunarService.singlePrice(productId);
		List<Map<String, Object>> priceInfos = (List<Map<String, Object>>) ticketInfo.get("priceInfos");
		int teamType = Integer.parseInt(ticketInfo.get("teamType").toString());
		
		// 分类型门票，判断日期是否在区间内
		if(teamType == 1){
			Map<String, Object> priceInfo = priceInfos.get(0);
			String displayBeginDate = priceInfo.get("displayBeginDate").toString();
			String displayEndDate = priceInfo.get("displayEndDate").toString();
			if(date.compareTo(displayBeginDate) >= 0 && date.compareTo(displayEndDate) <= 0){
				count = Integer.parseInt(priceInfo.get("availableCount").toString());
			}
			return count;
		}
		
		// 日历型数据，判断是否有指定日期的价格数据
		for(Map<String, Object> priceInfo : priceInfos){
			String useDate = priceInfo.get("useDate").toString();
			if(date.compareTo(useDate) == 0){
				count = Integer.parseInt(priceInfo.get("availableCount").toString());
				break;
			}
		}
		return count;
	}
	
}
