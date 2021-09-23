package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshijie on 2014/12/11.
 */
public class OptimizeUtil {

    @Autowired
    TransTimeService transTimeService;

    List<PlanTrip> rePlan(List<PlanTrip> planTripList) {
        PlanTrip hotel = null;
        for (PlanTrip planTrip : planTripList) {
            if (planTrip.getTripType() == TripType.HOTEL.value()) {
                hotel = planTrip;
                planTripList.remove(planTrip);
                break;
            }
        }
        int tripSize = planTripList.size();

        int[][] distances = new int[tripSize][tripSize];        //距离储存数组，distances[i][j] , i景点到j景点的距离
        int[] weight = new int[tripSize];
        float[] spot_time = {
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
        };   //游玩i景点所需时间
        int[] fromHotelDistance = new int[tripSize];      //酒店到景点距离，hotel[i][j]是酒店i到景点j的距离
        int[] toHotelDistance = new int[tripSize];      //酒店到景点距离，hotel[i][j]是酒店i到景点j的距离

//        float[][] dist_time = new float[30][30];   //时间储存数组，dist_time[i][j] , i景点到j景点的时间
//        float[][] hotel_time = new float[30][30];  //酒店到景点时间，hotel_time[i][j]是酒店i到景点j的时间
        int[] trace = new int[tripSize];          //最短路径的浏览顺序
        int cur;
        int t_tag;             //每天浏览时间 1：4-6小时 2:7-10小时 3:11-16小时

        int status = (1 << planTripList.size()) - 1;    //起点状态
        int[][] dp = new int[planTripList.size()][status];
        init(planTripList, status, distances, fromHotelDistance, toHotelDistance, hotel, weight, dp);

        if (hotel != null) {
            int i = 0;
            int startIndex = getNearestFromHotel(fromHotelDistance);  //找出丽出发酒店最近的景点startIndex
            int endIndex = getNearestToHotel(toHotelDistance);  //找出丽结束酒店最近的景点endIndex

            if (startIndex != 0) {
                for (i = 0; i < tripSize; i++)   //交换最近点startIndex和第0个景点行交换
                {
                    int t = distances[startIndex][i];
                    distances[startIndex][i] = distances[0][i];
                    distances[0][i] = t;
//                float k = dist_time[startIndex][i];
//                dist_time[startIndex][i] = dist_time[0][i];
//                dist_time[0][i] = k;
                }
                for (i = 0; i < tripSize; i++)   //交换最近点startIndex和第0个景点列交换
                {
                    int t = distances[i][0];
                    distances[i][0] = distances[i][startIndex];
                    distances[i][startIndex] = t;
//                float k = dist_time[i][0];
//                dist_time[i][0] = dist_time[i][startIndex];
//                dist_time[i][startIndex] = k;
                }
            }


            if (startIndex == endIndex)
                endIndex = getNearestFromHotel(distances[0]);  //如果最近点相同，找最近点的最近点作为结束点
            if (endIndex != tripSize - 1) {
                for (i = 0; i < tripSize; i++)   //交换最近点endIndex和第n个景点
                {
                    int t = distances[tripSize - 1][i];
                    distances[tripSize - 1][i] = distances[endIndex][i];
                    distances[endIndex][i] = t;
//                float k = dist_time[n - 1][i];
//                dist_time[n - 1][i] = dist_time[endIndex][i];
//                dist_time[endIndex][i] = k;
                }
                for (i = 0; i < tripSize; i++)   //交换最近点startIndex和第n个景点
                {
                    int t = distances[i][tripSize - 1];
                    distances[i][tripSize - 1] = distances[i][endIndex];
                    distances[i][endIndex] = t;
//                float k = dist_time[i][n - 1];
//                dist_time[i][n - 1] = dist_time[i][endIndex];
//                dist_time[i][endIndex] = k;
                }
            }

        } else {
            int kw = 0, value = 0;
            for (int i = 0; i < tripSize; i++)
                if (value < weight[i]) {
                    value = weight[i];
                    kw = i;
                }
            for (int i = 0; i < tripSize; i++)   //交换权值最大的点和第0个景点行交换
            {
                int t = distances[kw][i];
                distances[kw][i] = distances[0][i];
                distances[0][i] = t;
            }

            for (int i = 0; i < tripSize; i++)   //交换权值最大的点和第0个景点列交换
            {
                int t = distances[i][0];
                distances[i][0] = distances[i][kw];
                distances[i][kw] = t;
            }
        }

        dp[0][1] = 0;
        cur = 0;
        	
        getMinDistance(0, status, tripSize, distances, dp);                         //获取最短距离
        get_trace(cur, status, tripSize, distances, trace, dp);                          //获取最短距离的浏览顺序
        List<PlanTrip> result = new ArrayList<PlanTrip>();
        result.add(hotel);
        for (int i : trace) {
            result.add(planTripList.get(i));
        }
//        sum = sum_time(dist_time, spot_time, hotel_time, trace, n);         //计算花费时间
        return result;
    }

    int init(List<PlanTrip> planTripList, int status, int dist[][], int fromHotelDistances[], int toHotelDistances[], PlanTrip hotel, int weight[], int dp[][])    //初始化各数据结构的值
    {


        for (int i = 0; i < planTripList.size(); ++i) {
            weight[i] = i;
            fromHotelDistances[i] = getTransTime(hotel, planTripList.get(i));
            toHotelDistances[i] = getTransTime(planTripList.get(i), hotel);
            for (int j = 0; j < status; j++)
                dp[i][j] = -1;
            for (int j = 0; j < planTripList.size(); ++j) {
                dist[i][j] = getTransTime(planTripList.get(i), planTripList.get(j));

            }
        }
        return 0;
    }

    int getTransTime(PlanTrip start, PlanTrip end) {
        return 0;
    }

    int num_of_1(int status, int n)        //获取多少位为1，表示已经走过多少地点
    {
        int i, sum = 0, t = 1;
        for (i = 0; i < n; ++i) {
            if ((status & t) > 0) {
                ++sum;
            }
            t = t << 1;
        }
        return sum;
    }

    int find_of_1(int status, int n, int cur)        //找到终点
    {
        int i, t = 1;
        for (i = 0; i < n; ++i) {
            if ((status & t) > 0) {
                if (i != cur && i != (n - 1))
                    return i;
            }
            t = t << 1;
        }
        return -1;
    }

    int getMinDistance(int cur, int status, int n, int dist[][], int dp[][])    //动态规划，对已经计算过的dp[][]直接返回
    {
        if (dp[cur][status] != -1) {
            return dp[cur][status];
        }
        if (num_of_1(status, n) > 3)      //除了终点，还有其他地方可以走
        {
            int i, t = 2;
            int minLength = Integer.MAX_VALUE;
            for (i = 1; i < n - 1; i++, t <<= 1) {
                if (i == cur) {
                    continue;
                }
                if ((status & t) > 0) {
                    int key = getMinDistance(i, status & (~(1 << cur)), n, dist, dp) + dist[cur][i];
                    if (key < minLength)      //去掉当前位
                    {
                        minLength = key;
                    }
                }
            }
            return dp[cur][status] = minLength;
        } else                           //只能回到最终点
        {
            int x = find_of_1(status, n, cur);
            return dp[cur][status] = dist[cur][x] + dist[x][n - 1];  //返回到终点的距离，递归边界
        }
    }

    int getNearestFromHotel(int hotelDistances[])                 //找从酒店出发最近景点
    {
        int k = Integer.MAX_VALUE;
        int x = 0;
        for (int i = 0; i < hotelDistances.length; i++) {
            if (hotelDistances[i] < k) {
                k = hotelDistances[i];
                x = i;
            }
        }
        return x;
    }

    int getNearestToHotel(int hotelDistances[])                 //找到酒店最近景点
    {
        int k = Integer.MAX_VALUE;
        int x = 0;
        for (int i = 0; i < hotelDistances.length; i++) {
            if (hotelDistances[i] < k) {
                k = hotelDistances[i];
                x = i;
            }
        }
        return x;
    }

    int get_trace(int cur, int status, int n, int dist[][], int trace[], int dp[][])          //获得最短行程顺序
    {
        int k = 0, i;
        if (num_of_1(status, n) > 2)      //除了终点，还有其他地方可以走
        {
            int t = 2;
            for (i = 1, t = 2; i < n - 1; i++, t <<= 1) {
                if (cur == i)
                    continue;
                float key = (dp[i][status & (~(1 << cur))] + dist[cur][i]);

                if ((status & t) > 0) {
                    if (dp[cur][status] == key) {
                        k = i;
                        break;
                    }
                }
            }
            trace[cur] = k;
            if (i == (n - 1)) {
                int x = find_of_1(status, n, cur);
                trace[cur] = x;
                trace[x] = n - 1;
            }
            get_trace(k, status & (~(1 << cur)), n, dist, trace, dp);
        }
        return 0;
    }

    float sum_time(float dist_time[][], float spot_time[], float hotel_time[][], int trace[], int n)                   //计算花费时间
    {
        float sum = 0, i;
        sum = hotel_time[0][0];
        int next = 0;
        for (i = 1; i < n - 1; i++) {
            sum = sum + dist_time[next][trace[next]] + spot_time[next];
            next = trace[next];
        }
        sum = sum + spot_time[n - 1] + hotel_time[1][n - 1] + dist_time[next][trace[next]];

        return sum;
    }


}
