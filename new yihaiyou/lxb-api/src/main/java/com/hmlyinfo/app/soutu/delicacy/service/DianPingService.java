package com.hmlyinfo.app.soutu.delicacy.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.domain.RestaurantComment;
import com.hmlyinfo.app.soutu.delicacy.mapper.RestaurantCommentMapper;
import com.hmlyinfo.app.soutu.delicacy.mapper.RestaurantMapper;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.ListUtil;


@Service
public class DianPingService {
	
	//http://www.dianping.com/ajax/json/shop/wizard/getReviewListFPAjax?_nr_force=1430815375313&act=getreviewlist&shopId=5446794&tab=all&order=
	
	private static final String REVIEWS_URL_FIRST = "http://www.dianping.com/ajax/json/shop/wizard/getReviewListFPAjax?_nr_force=";
	private static final String REVIEWS_URL_MIDDLE = "&act=getreviewlist&shopId=";
	private static final String REVIEWS_URL_AFTER = "&tab=all&order=";
	
	@Autowired
	private RestaurantMapper<Restaurant> restaurantMapper;
	@Autowired
	private RestaurantCommentMapper<RestaurantComment> rcMapper;
	@Autowired
	private RestaurantService restaurantService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public Map<String, Object> test()
	{
		
		String ids = "196";
		String[] idList = ids.split(",");
		for(String id : idList){
			try {
				getRecentReviews(id);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//从大众点评上根据shopId下载数据
	public Map<String, Object> getRecentReviews(String id) throws ClientProtocolException, IOException
	{
		Restaurant restaurant = restaurantMapper.selById(id);
		if(restaurant == null || restaurant.getDianpingId() == 0){
			return null;
		}
		String dianpingId = restaurant.getDianpingId().toString();
//		String postUrl = REVIEWS_URL_FIRST + System.currentTimeMillis() + REVIEWS_URL_MIDDLE + dianpingId + REVIEWS_URL_AFTER;
		HttpPost httpRequst = new HttpPost("http://www.dianping.com/ajax/json/shop/wizard/getReviewListFPAjax?_nr_force=1430815763513&act=getreviewlist&shopId=18393609&tab=all&order=");

		httpRequst.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
		HttpResponse httpResponse = HttpClientUtils.getHttpClient().execute(httpRequst);
	
    	String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
    	
    	Map<String, Object> paramMap = objectMapper.readValue(resultStr, Map.class);
    	
    	if(paramMap.get("code") == null)
    		return paramMap;
    	if(paramMap.get("code").toString().equals("200")){
    		String commentString = (String) paramMap.get("msg");
    		restaurant.setDianpingComment(commentString);
    		restaurantMapper.update(restaurant);
    	}
    	
    	return paramMap;
	}
	
	//读取酒店名称和点评上的id更新进数据库中
	public void updateShopId()
	{
		String sumString = "153,513604;154,510289;155,2439571;156,3642685;157,2936614;158,17677663;159,10656043;160,18176040;161,508495;162,17900853;163,2355826;164,23041557;165,509774;166,2381848;167,585842;168,507715;169,508130;170,4081966;171,13835400;172,3546289;173,5446794;174,5540787;175,2980477;176,509774;177,585842;178,513604;179,508149;180,6104517;181,2396422;182,4713345;183,4713159;184,3106810;185,502110;186,4503319;187,505532;188,17854622;189,500552;190,503597;191,2467834;192,502074;193,3587476;195,503014;196,4503319;197,500589;198,4503319;199,18588190;200,18588190;201,502116;202,507512;203,5541620;204,507131;205,4503319;206,507232;207,9489268;208,18588190;209,14731381;210,501026;211,14731381;212,17854622;213,502511;214,4530857;300,2317999;301,5584770;302,4664315;303,3010099;304,557606;305,14169401;306,2317999;307,22996612;308,568479;309,1917007;310,5685553;311,17609889;312,5685553;314,6427785;315,557573;316,553384;317,2434861;318,17190436;319,17688768;320,553384;321,553145;322,2422567;323,5410634;324,553075;325,2342929;326,553092;327,553761;328,8894348;329,4180963;330,5679340;331,2079265;332,5958941;333,2294884;334,17224334;335,5143090;336,553284;337,5465185;338,553092;339,553437;340,2988715;341,19339518;342,554287;343,4020874;345,554237;346,554258;347,2194507;348,3023278;349,5383164;350,554554;351,583002;352,2945341;353,586547;354,9033219;355,554539;356,5425755;357,2023210;358,2359315;359,3686344;360,555454;361,550469;362,2214439;363,9295389;364,566080;365,2265361;366,9842816;367,18179018;368,572369;369,3578080;370,564939;371,5277088;372,2577952;373,21140198;374,3887863;375,17977626;376,2226853;377,2602951;378,6345830;379,2135509;380,2374117;381,2374117;382,2602951;383,8693888;384,4680860;385,16988539;386,550381;387,2242156;388,19399940;389,5561742;390,14173260;391,13850554;392,11552921;394,583715;395,3303331;396,2091964;397,2783890;719,18329700;720,10333858;721,558361;722,14160750;723,9074177;724,21265170;725,17823818;726,2911339;727,2463157;728,14160750;729,19378654;730,5593623;731,9074177;732,8884345;733,5218691;734,2083432;735,558361;736,19405740;737,5384130;738,5685913;739,2969623;740,6063150;741,18663997;743,5103541;744,5364494;745,4295527;748,4217972;749,2092219;750,16818646;751,14712043;752,10010824;753,5319026;755,2600116;756,2323231;757,2092219;758,4734924;759,6210760;760,15963707;761,2582602;762,6228446;763,5567616;765,4658156;766,15962848";
		String[] list = sumString.split(";");
		for(String lineString : list){
			String[] nameId = lineString.split(",");
			String id = nameId[0];
			String shopId = nameId[1];
			Restaurant restaurant = restaurantMapper.selById(id);
			restaurant.setDianpingId(Long.parseLong(shopId));
			restaurantMapper.update(restaurant);
		}
	}
	
	
	public void insertTest()
	{
		String idsString = "1,2,3,4,5,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,300,301,302,303,304,305,306,307,308,309,310,311,312,314,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,394,395,396,397,719,720,721,722,723,724,725,726,727,728,729,730,731,732,733,734,735,736,737,738,739,740,741,743,744,745,748,749,750,751,752,753,755,756,757,758,759,760,761,762,763,765,766";
		String[] idList = idsString.split(",");
		for(String id : idList){
			try {
				insertComment(id);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//把评论html分割成具体评论插入数据库
	public void insertComment(String id) throws ParseException
	{
		Restaurant res = restaurantMapper.selById(id);


		String ss = res.getDianpingComment();
		Long restaurantId = res.getId();
		if(ss == null || ss.equals(""))
			return;
		int beginIndex = 0;
		while(ss.indexOf("<li class", beginIndex) > 0){
			int commentBegin = ss.indexOf("<li class", beginIndex);
			int commentEnd = ss.indexOf("</li>", beginIndex);
			beginIndex = commentEnd + "</li>".length();
			System.out.println("ss=" + ss.length());
			String commentString = ss.substring(commentBegin, commentEnd);
			String nickName = commentString.substring(commentString.indexOf("alt=\"") + "alt=\"".length(), commentString.indexOf("\" data-lazyload="));
			int userBegin = commentString.indexOf("data-lazyload=\"") + "data-lazyload=\"".length();
			int userEnd = commentString.indexOf(">", userBegin) - 2;
			String userFace = commentString.substring(userBegin, userEnd);
			
			String taste;
			if(commentString.indexOf("口味：") > 0){
				int tasteBegin = commentString.indexOf("口味：") + "口味：".length();
				int tasteEnd = commentString.indexOf("</span>", tasteBegin);
				taste = commentString.substring(tasteBegin, tasteEnd);
			}else {
				taste = "-1";
			}
			
			String envir;
			if(commentString.indexOf("环境：") > 0){
				int envirBegin = commentString.indexOf("环境：") + "环境：".length();
				int envirEnd = commentString.indexOf("</span>", envirBegin);
				envir = commentString.substring(envirBegin, envirEnd);
			}else {
				envir = "-1";
			}
			
			String servi;
			if(commentString.indexOf("服务：") > 0){
				int serviBegin = commentString.indexOf("服务：") + "服务：".length();
				int serviEnd = commentString.indexOf("</span>", serviBegin);
				servi = commentString.substring(serviBegin, serviEnd);
			}else {
				servi = "-1";
			}
			
			String avera;
			if(commentString.indexOf("人均：") > 0){
				int averaBegin = commentString.indexOf("人均：") + "人均：".length();
				int averaEnd = commentString.indexOf("</span>", averaBegin);
				avera = commentString.substring(averaBegin, averaEnd);
			}else {
				avera = "-1";
			}
			
			int desBegin = commentString.indexOf("<p class=\"desc\">") + "<p class=\"desc\">".length();
			String desc = commentString.substring(desBegin, commentString.indexOf("</p>", desBegin));
			int timeBegin = commentString.indexOf("<span class=\"time\">") + "<span class=\"time\">".length();
			String timeString = "2015-" + commentString.substring(timeBegin, commentString.indexOf("</span>", timeBegin));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(timeString);
			
			RestaurantComment newComment = new RestaurantComment();
			newComment.setNickname(nickName);
			newComment.setUserFace(userFace);
			newComment.setRestaurantId(restaurantId);
			if(!taste.equals("-1")){
				newComment.setTasteScore(Integer.parseInt(taste));
			}
			if(!envir.equals("-1")){
				newComment.setEnvironmentScore(Integer.parseInt(envir));
			}
			if(!servi.equals("-1")){
				newComment.setServiceScore(Integer.parseInt(servi));
			}
			if(!avera.equals("-1")){
				newComment.setAverageScore(Integer.parseInt(avera));
			}
			
			newComment.setContent(desc);
			newComment.setCreateTime(date);
			
			Map<String, Object> parMap = new HashMap<String, Object>();
			parMap.put("restaurantId", restaurantId);
			parMap.put("nickname", nickName);
			List<RestaurantComment> rcList = rcMapper.list(parMap);
			if(rcList.isEmpty()){
				rcMapper.insert(newComment);
			}else {
				newComment.setId(ListUtil.getSingle(rcList).getId());
				rcMapper.update(newComment);
			}
			
			
			System.out.println("test");
		}
	}
	
	
	
}
