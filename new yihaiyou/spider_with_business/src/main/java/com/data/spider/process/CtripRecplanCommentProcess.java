package com.data.spider.process;

import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
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
public class CtripRecplanCommentProcess extends BaseSpiderProcess {

    private CommentService commentService = SpringContextHolder.getBean("commentService");
    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
//    private static ObjectMapper om = new ObjectMapper();

    public CtripRecplanCommentProcess(Datatask datatask) {
        super(datatask);
    }

    @Override
    public Datatask call() throws Exception {
        long id = Long.parseLong(datatask.getUrl());
        int ctripId = Integer.parseInt(datatask.getInfo());

        boolean b;
        try {
            b = saveComments(id, ctripId);
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

    private boolean saveComments(long id, int ctripId) {
        CtripService ctripService = new CtripService();
        Date limit = null;
        try {
            limit = new SimpleDateFormat("yyyy-MM-dd").parse("2014-2-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Comment> comments = ctripService.getTravelReplyLimitTime(ctripId, limit, id);
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
