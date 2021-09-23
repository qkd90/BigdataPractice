package com.data.spider.service.pojo.ctrip;

import java.util.List;

/**
 * Created by Sane on 16/2/15.
 */
public class DistrictList {

    /**
     * Timestamp : /Date(1455507274384+0800)/
     * Ack : Success
     * Errors : []
     * Version : 1.0
     * Extension : [{"Id":"CLOGGING_TRACE_ID","Value":"6545876997278036321"}]
     */

    private ResponseStatusEntity ResponseStatus;
    /**
     * ResponseStatus : {"Timestamp":"/Date(1455507274384+0800)/","Ack":"Success","Errors":[],"Version":"1.0","Extension":[{"Id":"CLOGGING_TRACE_ID","Value":"6545876997278036321"}]}
     * Result : [{"Id":32,"DistrictType":"CITY","Name":"丽江","EName":"Lijiang","Introduce":"潺潺溪水，拂面杨柳，悠扬古乐\u2026 在丽江，感受一如既往的慵懒慢生活。","CityId":37,"GCoord":{"Lat":26.8767738342285,"Lng":100.230377197266},"BCoord":{"Lat":26.8827667236328,"Lng":100.236877441406},"CoverImageId":20997907,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg09.c-ctrip.com/images/tg/736/108/026/1298bb7b9e424f9a94a1d8101972eb94_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100007,"DistrictType":"PROVINCE","Name":"云南","EName":"Yunnan","CityId":0,"PinYin":"yun nan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"li jiang","IsOverseas":false,"Distance":0,"Score":33.1657776260357},{"Id":61,"DistrictType":"CITY","Name":"三亚","EName":"Sanya","Introduce":"天之涯、海之角，大自然珍藏了一块美玉在中国的南海之滨，其实天堂离我们如此之近！","CityId":43,"GCoord":{"Lat":18.2528476715088,"Lng":109.511909484863},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":23519413,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg06.c-ctrip.com/images/fd/tg/g2/M04/88/93/CghzgFWwtaGAcfZFAAiBFf7XyGY672_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100001,"DistrictType":"PROVINCE","Name":"海南","EName":"Hainan","CityId":0,"PinYin":"hai nan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"san ya","IsOverseas":false,"Distance":0,"Score":27.5860869108924},{"Id":38,"DistrictType":"CITY","Name":"香港","EName":"Hong Kong","Introduce":"这是华洋交错的东方明珠，这是张爱玲念念不忘的倾城，这是林夕笔下\u201c我所爱的香港\u201d。","CityId":58,"GCoord":{"Lat":22.396427154541,"Lng":114.109497070313},"BCoord":{"Lat":22.402717590332,"Lng":114.115928649902},"CoverImageId":21474152,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg01.c-ctrip.com/images/fd/tg/g2/M09/89/25/Cghzf1Wwv_6ALOGqAAXHUrnJUCI396_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"xiang gang","IsOverseas":true,"Distance":0,"Score":33.953727501676},{"Id":1,"DistrictType":"CITY","Name":"北京","EName":"Beijing","Introduce":"去故宫探访帝王生活，北海的碧幽，胡同的市井，798的艺术氛围，一切赋予帝都新意义！","CityId":1,"GCoord":{"Lat":39.9042129516602,"Lng":116.407409667969},"BCoord":{"Lat":39.9105377197266,"Lng":116.413795471191},"CoverImageId":17007299,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg09.c-ctrip.com/images/fd/tg/g1/M05/7B/AD/CghzfFWwuq-AA5pdAALKb19P8Vo085_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"Bei jing","IsOverseas":false,"Distance":0,"Score":33.9218085853663},{"Id":2,"DistrictType":"CITY","Name":"上海","EName":"Shanghai","Introduce":"摩天大楼、石库门、老洋房在这座摩登之都中交融与辉映，演绎着过去与未来的华丽篇章。","CityId":2,"GCoord":{"Lat":31.2304592132568,"Lng":121.473701477051},"BCoord":{"Lat":31.236328125,"Lng":121.480239868164},"CoverImageId":14781406,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg07.c-ctrip.com/images/tg/690/622/727/ee367d0c4790411780d7ebeb96a83685_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"shang hai","IsOverseas":false,"Distance":0,"Score":34.4168409088356},{"Id":21,"DistrictType":"CITY","Name":"厦门","EName":"Xiamen","Introduce":"漫步琴岛，聆听鼓浪屿的音符，环岛骑行，去厦大感受最美校园的一缕阳光。by游游君","CityId":25,"GCoord":{"Lat":24.4865245819092,"Lng":118.089294433594},"BCoord":{"Lat":24.4861488342285,"Lng":118.095855712891},"CoverImageId":54071474,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg03.c-ctrip.com/images/fd/tg/g3/M06/24/42/CggYG1YDjVOAYL5HAAuB_J_Kaao324_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100038,"DistrictType":"PROVINCE","Name":"福建","EName":"Fujian","CityId":0,"PinYin":"fu jian"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"xia men","IsOverseas":false,"Distance":0,"Score":34.088450575287},{"Id":14,"DistrictType":"CITY","Name":"杭州","EName":"Hangzhou","Introduce":"西湖十景风光秀丽，灵隐古刹鸟鸣山幽，泡上一杯龙井，茶香味浓。","CityId":17,"GCoord":{"Lat":30.2740898132324,"Lng":120.155067443848},"BCoord":{"Lat":30.2799987792969,"Lng":120.161613464355},"CoverImageId":46815169,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg02.c-ctrip.com/images/fd/tg/g1/M0A/67/BA/CghzflWsuTOAHRsyAB40GAEXzmw876_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100065,"DistrictType":"PROVINCE","Name":"浙江","EName":"Zhejiang","CityId":0,"PinYin":"zhe jiang"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"hang zhou","IsOverseas":false,"Distance":0,"Score":34.6200876033829},{"Id":7,"DistrictType":"CITY","Name":"西安","EName":"Xian","Introduce":"兵马俑、古城墙，带你走进拥有千年历史的十三朝帝王都。","CityId":10,"GCoord":{"Lat":34.2649879455566,"Lng":108.944267272949},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":30918644,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg05.c-ctrip.com/images/fd/tg/g2/M00/8A/E6/CghzgVWw31-AMvFuADP2NX0nDAY710_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100057,"DistrictType":"PROVINCE","Name":"陕西","EName":"Shanxi","CityId":0,"PinYin":"shan xi"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"xi an","IsOverseas":false,"Distance":0,"Score":28.3175473590398},{"Id":28,"DistrictType":"CITY","Name":"桂林","EName":"Guilin","Introduce":"奇峰、奇洞、美石，泛舟漓江上，骑游十里画廊，走进桂林，无异于走进一幅秀丽的山水画卷。","CityId":33,"GCoord":{"Lat":25.2735652923584,"Lng":110.290191650391},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":23519528,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg01.c-ctrip.com/images/fd/tg/g1/M07/7A/98/CghzfVWwtayAcRE2ABaIuxjYffE235_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100052,"DistrictType":"PROVINCE","Name":"广西","EName":"Guangxi","CityId":0,"PinYin":"guang xi"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"gui lin","IsOverseas":false,"Distance":0,"Score":28.3196042699531},{"Id":25,"DistrictType":"CITY","Name":"九寨沟","EName":"Jiuzhaigou","Introduce":"无忧无虑的人间仙境，这里是重归儿时的童话世界。","CityId":91,"GCoord":{"Lat":32.7820243835449,"Lng":103.909637451172},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":18506412,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg05.c-ctrip.com/images/tg/382/556/645/c2c7e37d147f4ab1b2555cb323ed1d15_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":744,"DistrictType":"CITY","Name":"阿坝","EName":"Aba","CityId":1838,"PinYin":"a ba"},{"Id":100009,"DistrictType":"PROVINCE","Name":"四川","EName":"Sichuan","CityId":0,"PinYin":"si chuan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"jiu zhai gou","IsOverseas":false,"Distance":0,"Score":23.1472156641007},{"Id":104,"DistrictType":"CITY","Name":"成都","EName":"Chengdu","Introduce":"巴蜀文化发源之地，邂逅古色古香的城市，感受闲适安逸的慢生活。","CityId":28,"GCoord":{"Lat":30.5545883178711,"Lng":104.06485748291},"BCoord":{"Lat":30.6646842956543,"Lng":104.071334838867},"CoverImageId":23102357,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg06.c-ctrip.com/images/fd/tg/g1/M00/81/B9/CghzfFWxFFeAGXtFADASxuV0mWk336_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100009,"DistrictType":"PROVINCE","Name":"四川","EName":"Sichuan","CityId":0,"PinYin":"si chuan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"cheng du","IsOverseas":false,"Distance":0,"Score":34.2272360581922},{"Id":36,"DistrictType":"CITY","Name":"拉萨","EName":"Lhasa","Introduce":"在雪域高原的蓝天白云下，看色拉寺辩经，去八廊街淘宝，把自己融入这浓郁的宗教和文艺氛围中。","CityId":41,"GCoord":{"Lat":29.6455535888672,"Lng":91.1408538818359},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":23645591,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg08.c-ctrip.com/images/fd/tg/g2/M01/89/52/CghzgFWwvhqAEOR_AAtiKBFENKs491_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100003,"DistrictType":"PROVINCE","Name":"西藏","EName":"Tibet","CityId":0,"PinYin":"xi zang"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"la sa","IsOverseas":false,"Distance":0,"Score":27.7535414668628},{"Id":19,"DistrictType":"SIGHTZONE","Name":"黄山","EName":"Huangshan","Introduce":"四绝：奇松、怪石、云海、温泉，被天下世人誉为\u201c天下第一奇山\u201d。by 乌龙茶0706\r\n","CityId":23,"GCoord":{"Lat":29.7146835327148,"Lng":118.337478637695},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":16055994,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg03.c-ctrip.com/images/tg/706/427/971/f30f03169a5a4152bf009722b3387f2f_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":120061,"DistrictType":"CITY","Name":"黄山市","EName":"Huangshan","CityId":23,"PinYin":"huang shan shi"},{"Id":100068,"DistrictType":"PROVINCE","Name":"安徽","EName":"Anhui","CityId":0,"PinYin":"an hui"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"huang shan","IsOverseas":false,"Distance":0,"Score":27.0863059793869},{"Id":11,"DistrictType":"CITY","Name":"苏州","EName":"Suzhou","Introduce":"寒山寺、虎丘塔、昆曲、茉莉花，苏州之美，在她温婉的一颦一笑，古典而悠远。","CityId":14,"GCoord":{"Lat":31.298885345459,"Lng":120.585319519043},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":53015133,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg06.c-ctrip.com/images/fd/tg/g4/M01/4A/BF/CggYHVXjzyeAOeymABuBTaHIgFc792_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100066,"DistrictType":"PROVINCE","Name":"江苏","EName":"Jiangsu","CityId":0,"PinYin":"jiang su"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"su zhou","IsOverseas":false,"Distance":0,"Score":33.8558593557544},{"Id":9,"DistrictType":"CITY","Name":"南京","EName":"Nanjing","Introduce":"玄武湖上看烟云，总统府里忆民国。早春漫步台城，深秋午后，何不中山路上看百年梧桐？","CityId":12,"GCoord":{"Lat":32.0602531433105,"Lng":118.796875},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":50513348,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg05.c-ctrip.com/images/fd/tg/g2/M07/73/79/Cghzf1W4jTWAR2qKABkK6h9okIY888_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100066,"DistrictType":"PROVINCE","Name":"江苏","EName":"Jiangsu","CityId":0,"PinYin":"jiang su"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"nan jing","IsOverseas":false,"Distance":0,"Score":33.4450770434623},{"Id":5,"DistrictType":"CITY","Name":"青岛","EName":"Qingdao","Introduce":"红瓦绿树，碧海蓝天，穿梭于欧亚风情的老建筑中，静静享受属于你的惬意时光。\r\n","CityId":7,"GCoord":{"Lat":36.0672187805176,"Lng":120.382507324219},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":8013187,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg09.c-ctrip.com/images/tg/511/832/531/29baac9cf4004c7ca0340151b257a18d_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100039,"DistrictType":"PROVINCE","Name":"山东","EName":"Shandong","CityId":0,"PinYin":"shan dong"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"qing dao","IsOverseas":false,"Distance":0,"Score":27.2195120042271},{"Id":29,"DistrictType":"CITY","Name":"昆明","EName":"Kunming","Introduce":"四季如春，鲜花点缀着这座温暖城市，睁开眼睛就是春天。","CityId":34,"GCoord":{"Lat":24.8802502576248,"Lng":102.832893371582},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":21212622,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg07.c-ctrip.com/images/fd/tg/g2/M06/87/AC/Cghzf1Wwr5GAGL9KABrPhtc6sCQ746_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100007,"DistrictType":"PROVINCE","Name":"云南","EName":"Yunnan","CityId":0,"PinYin":"yun nan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"kun ming","IsOverseas":false,"Distance":0,"Score":32.0104981187937},{"Id":702,"DistrictType":"CITY","Name":"阳朔","EName":"Yangshuo","Introduce":"十里画廊、古寨石城，骑着自行车，沿途风景缓缓倒退，心都是自由的！","CityId":871,"GCoord":{"Lat":24.8458633422852,"Lng":110.45499420166},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":17160946,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg03.c-ctrip.com/images/tg/753/185/420/6b16811eafd64fd89a80f590a2d574e4_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":28,"DistrictType":"CITY","Name":"桂林","EName":"Guilin","CityId":33,"PinYin":"gui lin"},{"Id":100052,"DistrictType":"PROVINCE","Name":"广西","EName":"Guangxi","CityId":0,"PinYin":"guang xi"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"yang shuo","IsOverseas":false,"Distance":0,"Score":23.2374373781713},{"Id":31,"DistrictType":"CITY","Name":"大理","EName":"Dali","Introduce":"下关风、上关花、苍山雪、洱海月，大理的秀美，在于感受世界的静谧体验。","CityId":36,"GCoord":{"Lat":25.6064853668213,"Lng":100.267639160156},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":15059748,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg05.c-ctrip.com/images/fd/tg/g1/M0A/79/E5/CghzfFWwqDiAFQtgAAhcsJYs-b0709_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100007,"DistrictType":"PROVINCE","Name":"云南","EName":"Yunnan","CityId":0,"PinYin":"yun nan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"da li","IsOverseas":false,"Distance":0,"Score":30.4312262032835},{"Id":23,"DistrictType":"CITY","Name":"张家界","EName":"Zhangjiajie","Introduce":"三千奇峰拨地而起，八百溪流蜿蜒曲折，奇峰美景让人称叹，品湘味土家菜又是别样滋味。\n","CityId":27,"GCoord":{"Lat":29.1165256500244,"Lng":110.479217529297},"BCoord":{"Lat":0,"Lng":0},"CoverImageId":11409192,"ImageSizeMap":[{"Key":"C_320_320","ImageUrl":"http://dimg01.c-ctrip.com/images/tg/312/436/791/150e2b82fab449ba8889cd956b147215_C_320_320.jpg"}],"InChina":true,"ParentDistrictPath":[{"Id":100053,"DistrictType":"PROVINCE","Name":"湖南","EName":"Hunan","CityId":0,"PinYin":"hu nan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}],"PinYin":"zhang jia jie","IsOverseas":false,"Distance":0,"Score":27.8702826135798}]
     * TotalCount : 20
     */

    private int TotalCount;
    /**
     * Id : 32
     * DistrictType : CITY
     * Name : 丽江
     * EName : Lijiang
     * Introduce : 潺潺溪水，拂面杨柳，悠扬古乐… 在丽江，感受一如既往的慵懒慢生活。
     * CityId : 37
     * GCoord : {"Lat":26.8767738342285,"Lng":100.230377197266}
     * BCoord : {"Lat":26.8827667236328,"Lng":100.236877441406}
     * CoverImageId : 20997907
     * ImageSizeMap : [{"Key":"C_320_320","ImageUrl":"http://dimg09.c-ctrip.com/images/tg/736/108/026/1298bb7b9e424f9a94a1d8101972eb94_C_320_320.jpg"}]
     * InChina : true
     * ParentDistrictPath : [{"Id":100007,"DistrictType":"PROVINCE","Name":"云南","EName":"Yunnan","CityId":0,"PinYin":"yun nan"},{"Id":110000,"DistrictType":"COUNTRY","Name":"中国","EName":"China","CityId":0,"PinYin":"zhong guo"},{"Id":120001,"DistrictType":"CONTINENT","Name":"亚洲","EName":"Asia","CityId":0,"PinYin":"ya zhou"}]
     * PinYin : li jiang
     * IsOverseas : false
     * Distance : 0
     * Score : 33.1657776260357
     */

    private List<ResultEntity> Result;

    public void setResponseStatus(ResponseStatusEntity ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public void setResult(List<ResultEntity> Result) {
        this.Result = Result;
    }

    public ResponseStatusEntity getResponseStatus() {
        return ResponseStatus;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<ResultEntity> getResult() {
        return Result;
    }

    public static class ResponseStatusEntity {
        private String Timestamp;
        private String Ack;
        private String Version;
        private List<?> Errors;
        /**
         * Id : CLOGGING_TRACE_ID
         * Value : 6545876997278036321
         */

        private List<ExtensionEntity> Extension;

        public void setTimestamp(String Timestamp) {
            this.Timestamp = Timestamp;
        }

        public void setAck(String Ack) {
            this.Ack = Ack;
        }

        public void setVersion(String Version) {
            this.Version = Version;
        }

        public void setErrors(List<?> Errors) {
            this.Errors = Errors;
        }

        public void setExtension(List<ExtensionEntity> Extension) {
            this.Extension = Extension;
        }

        public String getTimestamp() {
            return Timestamp;
        }

        public String getAck() {
            return Ack;
        }

        public String getVersion() {
            return Version;
        }

        public List<?> getErrors() {
            return Errors;
        }

        public List<ExtensionEntity> getExtension() {
            return Extension;
        }

        public static class ExtensionEntity {
            private String Id;
            private String Value;

            public void setId(String Id) {
                this.Id = Id;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }

            public String getId() {
                return Id;
            }

            public String getValue() {
                return Value;
            }
        }
    }

    public static class ResultEntity {
        private int Id;
        private String DistrictType;
        private String Name;
        private String EName;
        private String Introduce;
        private int CityId;
        /**
         * Lat : 26.8767738342285
         * Lng : 100.230377197266
         */

        private GCoordEntity GCoord;
        /**
         * Lat : 26.8827667236328
         * Lng : 100.236877441406
         */

        private BCoordEntity BCoord;
        private int CoverImageId;
        private boolean InChina;
        private String PinYin;
        private boolean IsOverseas;
        private int Distance;
        private double Score;
        /**
         * Key : C_320_320
         * ImageUrl : http://dimg09.c-ctrip.com/images/tg/736/108/026/1298bb7b9e424f9a94a1d8101972eb94_C_320_320.jpg
         */

        private List<ImageSizeMapEntity> ImageSizeMap;
        /**
         * Id : 100007
         * DistrictType : PROVINCE
         * Name : 云南
         * EName : Yunnan
         * CityId : 0
         * PinYin : yun nan
         */

        private List<ParentDistrictPathEntity> ParentDistrictPath;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setDistrictType(String DistrictType) {
            this.DistrictType = DistrictType;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setEName(String EName) {
            this.EName = EName;
        }

        public void setIntroduce(String Introduce) {
            this.Introduce = Introduce;
        }

        public void setCityId(int CityId) {
            this.CityId = CityId;
        }

        public void setGCoord(GCoordEntity GCoord) {
            this.GCoord = GCoord;
        }

        public void setBCoord(BCoordEntity BCoord) {
            this.BCoord = BCoord;
        }

        public void setCoverImageId(int CoverImageId) {
            this.CoverImageId = CoverImageId;
        }

        public void setInChina(boolean InChina) {
            this.InChina = InChina;
        }

        public void setPinYin(String PinYin) {
            this.PinYin = PinYin;
        }

        public void setIsOverseas(boolean IsOverseas) {
            this.IsOverseas = IsOverseas;
        }

        public void setDistance(int Distance) {
            this.Distance = Distance;
        }

        public void setScore(double Score) {
            this.Score = Score;
        }

        public void setImageSizeMap(List<ImageSizeMapEntity> ImageSizeMap) {
            this.ImageSizeMap = ImageSizeMap;
        }

        public void setParentDistrictPath(List<ParentDistrictPathEntity> ParentDistrictPath) {
            this.ParentDistrictPath = ParentDistrictPath;
        }

        public int getId() {
            return Id;
        }

        public String getDistrictType() {
            return DistrictType;
        }

        public String getName() {
            return Name;
        }

        public String getEName() {
            return EName;
        }

        public String getIntroduce() {
            return Introduce;
        }

        public int getCityId() {
            return CityId;
        }

        public GCoordEntity getGCoord() {
            return GCoord;
        }

        public BCoordEntity getBCoord() {
            return BCoord;
        }

        public int getCoverImageId() {
            return CoverImageId;
        }

        public boolean isInChina() {
            return InChina;
        }

        public String getPinYin() {
            return PinYin;
        }

        public boolean isIsOverseas() {
            return IsOverseas;
        }

        public int getDistance() {
            return Distance;
        }

        public double getScore() {
            return Score;
        }

        public List<ImageSizeMapEntity> getImageSizeMap() {
            return ImageSizeMap;
        }

        public List<ParentDistrictPathEntity> getParentDistrictPath() {
            return ParentDistrictPath;
        }

        public static class GCoordEntity {
            private double Lat;
            private double Lng;

            public void setLat(double Lat) {
                this.Lat = Lat;
            }

            public void setLng(double Lng) {
                this.Lng = Lng;
            }

            public double getLat() {
                return Lat;
            }

            public double getLng() {
                return Lng;
            }
        }

        public static class BCoordEntity {
            private double Lat;
            private double Lng;

            public void setLat(double Lat) {
                this.Lat = Lat;
            }

            public void setLng(double Lng) {
                this.Lng = Lng;
            }

            public double getLat() {
                return Lat;
            }

            public double getLng() {
                return Lng;
            }
        }

        public static class ImageSizeMapEntity {
            private String Key;
            private String ImageUrl;

            public void setKey(String Key) {
                this.Key = Key;
            }

            public void setImageUrl(String ImageUrl) {
                this.ImageUrl = ImageUrl;
            }

            public String getKey() {
                return Key;
            }

            public String getImageUrl() {
                return ImageUrl;
            }
        }

        public static class ParentDistrictPathEntity {
            private int Id;
            private String DistrictType;
            private String Name;
            private String EName;
            private int CityId;
            private String PinYin;

            public void setId(int Id) {
                this.Id = Id;
            }

            public void setDistrictType(String DistrictType) {
                this.DistrictType = DistrictType;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public void setEName(String EName) {
                this.EName = EName;
            }

            public void setCityId(int CityId) {
                this.CityId = CityId;
            }

            public void setPinYin(String PinYin) {
                this.PinYin = PinYin;
            }

            public int getId() {
                return Id;
            }

            public String getDistrictType() {
                return DistrictType;
            }

            public String getName() {
                return Name;
            }

            public String getEName() {
                return EName;
            }

            public int getCityId() {
                return CityId;
            }

            public String getPinYin() {
                return PinYin;
            }
        }
    }
}
