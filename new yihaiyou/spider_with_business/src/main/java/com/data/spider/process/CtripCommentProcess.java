package com.data.spider.process;

import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.spider.service.DatataskService;
import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.MakeBy;
import com.data.spider.util.BaseSpiderProcess;
import com.zuipin.util.SpringContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 高德交通信息（两点距离时间花费）
 */
public class CtripCommentProcess extends BaseSpiderProcess {

    private CommentService commentService = SpringContextHolder.getBean("commentService");
    private ScenicInfoService scenicInfoService = SpringContextHolder.getBean("scenicInfoService");
    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
//    private static ObjectMapper om = new ObjectMapper();

    public CtripCommentProcess(Datatask datatask) {
        super(datatask);
    }

    @Override
    public Datatask call() throws Exception {
        long id = datatask.getId();

        boolean b = false;
        try {
            b = saveComments(id);
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
        if (b) {
            datatask.setStatus(DatataskStatus.SUCCESSED);
        } else {
            datatask.setStatus(DatataskStatus.FAILED);
        }
        if (datatask.getMadeby() == MakeBy.DB) {
            datataskService.updateTask(datatask);
        }
        return datatask;
    }

    private boolean saveComments(long id) {
        ScenicInfo scenicInfo = scenicInfoService.get(id);
        CtripService ctripService = new CtripService();
        Integer ctripId = scenicInfo.getCtripScenicId();
        if (ctripId == null || ctripId == 0)
            return false;
        Date limit = null;
        try {
            limit = new SimpleDateFormat("yyyy-MM-dd").parse("2014-2-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int poiId = CtripService.getScenicDetail(String.valueOf(ctripId)).getSightDetailAggregate().getPoiId();
        List<Comment> comments = ctripService.getCommentLimitTime(poiId, ctripId, limit, scenicInfo.getId());
        for (Comment comment : comments) {
            System.out.println(comment.getCreateTime() + "\t" + comment.getContent());
            commentService.saveComment(comment);
        }
        return true;
    }


    @Override
    public String getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Semaphore getMutex() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getXmlName() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {

    }

}
