package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.domain.Dis;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.app.soutu.plan.newAlg.ToolService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by guoshijie on 2014/12/11.
 */

@Service
public class OptimizePlanService {
	
	@Autowired
	private ToolService toolService;
	@Autowired
	private DisService disService;

    List<Point> planTripList;  
    Point hotel;  

    int[][] distances;        //距离储存数组，distances[i][j] , i景点到j景点的距离
    int[] weight;     //当没有住酒店的时候，是以景点的权重作为首要考虑对象
    int[] fromHotelDistance;      //酒店到景点距离，hotel[i][j]是酒店i到景点j的距离
    int[] trace;          //最短路径的浏览顺序
    int cur;   //起始点

    int status;    //起点状态
    int size;   //景点的个数
    int leftestPointIndex;  //最左边的景点索引
    
    int[][] dp;  //动态规划所需要的状态位
    int[]realTrace;
    
    public void init(List<Point> planTriList, Point hotel) {
        this.planTripList = planTriList;
        this.hotel = hotel;
        this.size = planTripList.size();//景点数

        distances = new int[size][size];        //距离储存数组，distances[i][j] , i景点到j景点的距离
        weight = new int[size];            //景点的权重
        fromHotelDistance = new int[size];      //酒店到景点距离，hotel[i][j]是酒店i到景点j的距离

        trace = new int[size];          //最短路径的浏览顺序
        realTrace= new int[size];
        status = (1 << planTripList.size()) - 1;    //起点状态
        dp = new int[planTripList.size()][status + 1];
    }

    //直接给外部调用的，是这个方法
    public List<Point> rePlan()
    {
        init();

        if (size <= 2) {//假如景点小于二的话
            if (hotel != null) {
                int startIndex = getNearestFromHotel(fromHotelDistance);  //找出离出发酒店最近的景点startIndex
                if (startIndex != 0) {
                    replacePoint(size, distances, startIndex);
                }
            } else {
                int index = 0, value = 0;
                for (int i = 0; i < size; i++)
                    if (value < weight[i]) {
                        value = weight[i];
                        index = i;
                    }
                replacePoint(size, distances, index);
            }
            for (int i = 0; i < trace.length; i++) {
                trace[i] = i;
            }
        } else {
            if (hotel != null) {
                int startIndex = getNearestFromHotel(fromHotelDistance);  //找出丽出发酒店最近的景点startIndex
                if (startIndex != 0) {
                    replacePoint(size, distances, startIndex);
                }
                
            } else {
//				我们目前是直接取最东边的
//            	若以后要添加进入关于景点权重的信息，则利用此段代码
//                int index = 0, value = 0;
//                for (int i = 0; i < size; i++)
//                    if (value < weight[i]) {
//                        value = weight[i];
//                        index = i;
//                    }
            	
            	//TODO:目前是直接采取从最右边出发,日后如果有需要改进的时候,可以从这边,直接选取第一个点,不用做交换就出发
            	//TODO:
            	//TODO:
            	//TODO:
            	//TODO:
                replacePoint(size, distances, leftestPointIndex);
            }
            
            
            dp[0][1] = 0;
            cur = 0;   
            dynamicPlanCore();
        }
        List<Point> result = new ArrayList<Point>();
        if (hotel != null) {
            result.add(hotel);
        }
        for (int i=0;i<size;i++) {
        	int index= realTrace[trace[i]];
            result.add(planTripList.get(index));
        }
        return result;
    }

    //交换两个阵邻接阵
    private void replacePoint(int size, int[][] array, int index) {
    	realTrace[0]=index;
    	realTrace[index]=0;
    	int i;
        for (i = 0; i < size; i++)   //交换最近点startIndex和第0个景点行交换
        {
            int t = array[index][i];
            array[index][i] = array[0][i];
            array[0][i] = t;
        }
        for (i = 0; i < size; i++)   //交换最近点startIndex和第0个景点列交换
        {
            int t = array[i][0];
            array[i][0] = array[i][index];
            array[i][index] = t;
        }
    }

    
    //关于行程这部分的初始化，应该是要有具体的数据，是要直接距离，而不是利用 它的欧氏几何距离
    //因为条件限制，目前会将所有的权重置为1，倘若没有酒店的时候，是直接按照第一个选择的景点出发
    //TODO:但是等以后要是要添加入景点的权重时候，则可以在此初始化的weight[]数组上进行初始化
    int init()    //初始化各数据结构的值
    {
    	double leftestX=Double.MIN_VALUE;
    	
    	List<Long> scenicIdList = new ArrayList<Long>();
    	for(Point point : planTripList){
    		scenicIdList.add(point.scenicInfoId);
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("sIds", scenicIdList);
    	paramMap.put("eIds", scenicIdList);
    	List<Dis> disList = disService.list(paramMap);
    	Map<String, Integer> resMap = new HashMap<String, Integer>();
		for(Dis dis : disList){
			String index = dis.getSId() + "_" + dis.getEId();
			resMap.put(index, dis.getTaxiDis());
		}
		
        for (int i = 0; i < planTripList.size(); ++i) {
            weight[i] = 1;
            realTrace[i]=i;
            trace[i]=i;
            if (hotel != null) {
                fromHotelDistance[i] = disService.getTwoPointDis(hotel, planTripList.get(i));
                		//pointDis(hotel, planTripList.get(i), DisService.MODE_TAXI);
            }
            for (int j = 0; j < dp[i].length; j++)
                dp[i][j] = -1;
            for (int j = 0; j < planTripList.size(); ++j) {
                String indexString = planTripList.get(i).scenicInfoId + "_" + planTripList.get(j);
                if(resMap.get(indexString) == null || resMap.get(indexString) == 0){
                	distances[i][j] = disService.getTwoPointDis(planTripList.get(i), planTripList.get(j));
                }else {
                	distances[i][j] = resMap.get(indexString);
				}
            }
            if(planTripList.get(i).x > leftestX){
            	leftestPointIndex=i;  //记录下在最东边的点
            	leftestX=planTripList.get(i).x;
            }
        }
        return 0;
    }


    //动态规划算法的核心
  	public int dynamicPlanCore()
  	{     
  		int minlen=0;
  		if(size==1){//假如就一个景点的时候
  			trace[0]=0;
			if(hotel!=null){//假如酒店不为空时
				minlen=fromHotelDistance[0];
			}
			return minlen;
		}
  		
  		for(int i=0;i<size;i++)//初始化状态位
  		{
  			for(int j=0;j<dp[0].length;j++)
  			{
  				dp[i][j]=-1;
  			}
  		}
  		dp[0][1]=0; //从起始点出发
  		minlen=Integer.MAX_VALUE;
  		if(this.hotel!=null){  //假设为有住酒店的情况下，提取路径的方法
  			minlen=dynamicHotelTravel();
  		}
  		else {
			minlen=dynamicOneDay();
		}
  		return minlen;
  	}

  	//动态规划的迭代，当为一天游的时候
	private int dynamicOneDay() {
		int record=-1;
		int minlen=Integer.MAX_VALUE;
		for(int i=1;i<size;i++)
		{
			int temp=distances[0][i]+getMinOneDay(i,(1<<size)-2);
			if(temp<minlen)
			{
				record=i;
				minlen= getMinOneDay(i,(1<<size)-2) + distances[0][i];
			}
		}
		extractSortPointOneDay(record, (1<<size)-2, trace,0);
		return minlen;
	}

	//当有酒店参与的时候
	private int dynamicHotelTravel() {
		int record=-1;
		int minlen=Integer.MAX_VALUE;
		for(int i=1;i<size;i++)
		{
			if(distances[0][i]+getMinHotel(i,(1<<size)-1)<minlen)
			{
				record=i;
				minlen= getMinHotel(i,(1<<size)-1) + distances[0][i];
			}
		}
		extractSortPointHotelTravel(record, (1<<size)-1, trace,0);
		return minlen;
	}

	private int getMinHotel(int cur, int status) {//求有酒店情况下的回路最短
		if(dp[cur][status]!=-1){  //dp[cur][status]表示的是，当前cur点，走出status这种状态的，比如说走了1245个景点之后的距离
			return dp[cur][status];
		}
		if(toolService.numOfPoint(status,size)>2){  //当游玩的景点，还有两处（其中一处为起始点）以上，则进入该状态
			int t=2;  
			int minlen=Integer.MAX_VALUE; //随机定义的一个最短长度
			for(int i=1;i<size;i++,t<<=1){  //遍历每个景点
				if(i==cur){//假如是当前景点，则跳过此次	
					continue;
				}
				if((status&t)!=0){  //假如，该点暂时还没有走过，比如说status=11101（2进制），表示的是当前活动中第二个景点还没有去过
									//当i=1时，t=00010(2进制 ),表示的是第2个景点，则status&t =0，说明这一点已经走过了，则不进入下面步骤 
									//当i=2时，t=00100(2进制)，表示的是第3个景点，则status&t=100,说明这一点没有走过，则可以进入下面的步骤进行计算
				if(getMinHotel(i,status & (~(1<<cur)))+distances[cur][i] < minlen)	//递归迭代，获取从i点到剩余的整个旅游行程遍历完的距离，如果比之前的选择还短，就替代掉，否则接着遍历下一个点
				{															//比如说：当cur=2,status=11111，即第cur个景点,由11111表示其他景点都还没有旅游过
					minlen=getMinHotel(i, status & (~(1<<cur)))+distances[cur][i];	//status & (~(1<<cur))),表示，原有的已经走过的景点状态status ,加上当前点走完之后的新的旅游状态
				}															//比如 11111 & ~（1<<2）=  11011，表示的是，当前第2点已经走过啦，所以新的status就是11011																			//所以getMin(i,status)+dist[cur][i]表示的就是，如果从当前点cur，下一点为i,走到终点所要花费的最小长
				}
			}
			return dp[cur][status]=minlen;  //最后返回,如果从当前点cur，当前状态status,还有多少点没有走完的情况下，走完所需要花费的最短距离了
		}
		else {   //当游玩的景点只剩下两处时，即（最后一处和起始处）,status虽然是显示2，但是也只剩下起点了而已
			return dp[cur][status]=distances[cur][0];//
		}
	}
	private int getMinOneDay(int nowCur, int status) {//假如是就一天计划没有酒店的情况下
		if(dp[nowCur][status]!=-1){  //dp[cur][status]表示的是，当前cur点，走出status这种状态的，比如说走了1245个景点之后的距离
			return dp[nowCur][status];
		}
		if(toolService.numOfPoint(status,size)>1){  //当游玩的景点，还有两处（其中一处为起始点）以上，则进入该状态
			int t=2;  
			int minlen=Integer.MAX_VALUE; //随机定义的一个最短长度
			
			for(int i=1;i<size;i++,t<<=1){  //遍历每个景点
				if( i == nowCur ){//假如是当前景点，则跳过此次	
					continue;
				}
				if( ( status & t ) != 0){  //假如，该点暂时还没有走过，比如说status=11101（2进制），表示的是当前活动中第二个景点还没有去过
									//当i=1时，t=00010(2进制 ),表示的是第2个景点，则status&t =0，说明这一点已经走过了，则不进入下面步骤 
									//当i=2时，t=00100(2进制)，表示的是第3个景点，则status&t=100,说明这一点没有走过，则可以进入下面的步骤进行计算
					if(getMinOneDay(i,status & (~(1<<nowCur)))+distances[nowCur][i] < minlen)	//递归迭代，获取从i点到剩余的整个旅游行程遍历完的距离，如果比之前的选择还短，就替代掉，否则接着遍历下一个点
					{															//比如说：当cur=2,status=11111，即第cur个景点,由11111表示其他景点都还没有旅游过
						minlen=getMinOneDay(i, status & (~(1<<nowCur)))+distances[nowCur][i];	//status & (~(1<<cur))),表示，原有的已经走过的景点状态status ,加上当前点走完之后的新的旅游状态
					}															//比如 11111 & ~（1<<2）=  11011，表示的是，当前第2点已经走过啦，所以新的status就是11011
																				//所以getMin(i,status)+dist[cur][i]表示的就是，如果从当前点cur，下一点为i,走到终点所要花费的最小长度
				}
			}
			return dp[nowCur][status]=minlen;  //最后返回,如果从当前点cur，当前状态status,还有多少点没有走完的情况下，走完所需要花费的最短距离了
		}
		else {   //当游玩的景点只剩下1处时，即（最后一处）
			return dp[nowCur][status]=distances[nowCur][(int) (Math.log(status)/Math.log(2))];
		}	
	}

	//获取有住酒店时候的回路路径的办法
	public void extractSortPointHotelTravel(int first,int beforeStatus,int[] trace,int mark){
		if(first==0 && beforeStatus!=(1<<size)-1){//假如已经到结尾点了，非起点，就结束
			return;
		}
		else if(beforeStatus==(1<<size)-1){ 
			trace[mark++]=first;
		}
		int i=0,nowStatus=beforeStatus&~(1<<first);
		int min=Integer.MAX_VALUE;
		int record=-1;
		
		for(i=0;i<size;i++){
			int temp=(dp[i][nowStatus]);
			int tempRecord=temp+distances[first][i];
			if(temp==-1){
				continue;
			}
			if(temp<min){
				min=tempRecord;
				record=i;
			}
		}
		
		if(record!=-1){
			trace[mark++]=record;
			extractSortPointHotelTravel(record,nowStatus,trace,mark);
		}
	}
	//获取路径的办法，主要部分位运算做的还是蛮繁琐的，解释起来还是比较麻烦，如果前面看懂了，这个地方也不难，我就不做太多注释了
	public void extractSortPointOneDay(int first,int beforeStatus,int[] trace,int mark)
	{
		if(beforeStatus==(1<<size)-2){  //说明是第一个点,此时mark=0
			trace[mark]=0;
			trace[++mark]=first;
		}
		int i=0,nowStatus=beforeStatus&~(1<<first);
		int min=Integer.MAX_VALUE;
		int record=-1;
		for(i=0;i<size;i++){
			int temp=(dp[i][nowStatus]);
			int tempRecord=temp+distances[first][i];
			if(temp==-1){
				continue;
			}
			if(tempRecord<min)
			{
				min=tempRecord;
				record=i;
			}
		}
		
		if(record!=-1)
		{
			trace[++mark]=record;
			extractSortPointOneDay(record,nowStatus,trace,mark);
		}
	}

	//从新算法移植过来，因为之前我的设计是认为可能出现一天两酒店的情况，所以是用hotel[i][j]表示从酒店i到景点j的距离，
	//而这边已经采用了一阶阵来表示，所以就直接采用这边的办法
	//i参数表示的是：获取酒点i距离最近的景点
	//hTag参数的作用是：
	//因为当有住酒店的时候，我们是从酒店出发，第一个已经是先访问了距离酒店最近的景点了
	//如果到了傍晚，我们在返回酒店的最后一程，也选择距离酒店最近的景点,再求最短，就会出现，今早第一个已经访问过的景点
	//所以hTag的参数表示的意思就是：-1表示航程刚开始的，
	//当hTag>0,表示，该点已经被排在今天第一个访问的，这个是真正最短的景点，所以不能作为最后一个访问
//	public int getNearestNode(int i,int hTag){
//		int minDist=Integer.MAX_VALUE;
//		int x=0;
//		for(int j=0;j<size;j++){		
//				
//		//这边hotel[i][j]表示，从酒店i到景点J所需要路程，怎么会出现hotel[i][j]为0的，不知道何意
//		if(hotel[i][j] == 0){
//			continue;
//		}
//		else if(hotel[i][j]<minDist) {
//			if(hTag!=-1 && j==hTag){//假如此次是在定酒店的距离第二近的景点，则当j点是最短的景点，则跳过此次
//				continue;
//			}
//			minDist=hotel[i][j];
//			x=j;
//			}
//		}
//		return x;
//	}

    int getNearestFromHotel(int hotelDistances[])                 //找从酒店出发最近景点
    {
        int k = Integer.MAX_VALUE;
        int x = 0;
        for (int i = 1; i < hotelDistances.length; i++) {
            if (hotelDistances[i] < k) {
                k = hotelDistances[i];
                x = i;
            }
        }
        return x;
    }
}
