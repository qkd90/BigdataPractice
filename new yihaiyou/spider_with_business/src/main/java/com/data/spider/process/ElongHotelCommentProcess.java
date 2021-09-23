package com.data.spider.process;

import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentPhoto;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.Reviews;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.spider.service.DatataskService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.MakeBy;
import com.data.spider.util.BaseSpiderProcess;
import com.zuipin.util.SpringContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 高德交通信息（两点距离时间花费）
 */
public class ElongHotelCommentProcess extends BaseSpiderProcess {

    private CommentService commentService = SpringContextHolder.getBean("commentService");
    private HotelService hotelService = SpringContextHolder.getBean("hotelService");
    private final static DatataskService datataskService = SpringContextHolder.getBean("datataskService");
//    private static ObjectMapper om = new ObjectMapper();

    public ElongHotelCommentProcess(Datatask datatask) {
        super(datatask);
    }

    @Override
    public Datatask call() throws Exception {
        long id = Long.parseLong(datatask.getUrl());
        boolean b;
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
        Hotel scenicInfo = hotelService.get(id);

        Long elongId = scenicInfo.getTargetId();
        if (elongId == null || elongId == 0)
            return false;
        Date limit = null;
        try {
            limit = new SimpleDateFormat("yyyy-MM-dd").parse("2014-2-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Comment> comments = getCommentLimitTime(elongId, limit, id);
        for (Comment comment : comments) {
            System.out.println(comment.getCreateTime() + "\t" + comment.getNickName() + "\t" + comment.getContent());
            commentService.saveComment(comment);
        }
        return true;
    }

    private List<Comment> getCommentLimitTime(Long elongId, Date limit, long lvxbangId) {
        Random random = new Random();
        ElongHotelService elongHotelService = new ElongHotelService();
        List<Reviews.CommentsEntity> commmens = elongHotelService.getCommentLimitTime(elongId, limit);
        List<Comment> result = new ArrayList<Comment>();
        SimpleDateFormat dateFomater = new SimpleDateFormat("yyyy-MM-dd");
        for (Reviews.CommentsEntity commentEntity : commmens) {
            Comment comment = new Comment();
            comment.setNickName(commentEntity.getUserName());
            comment.setContent(commentEntity.getContent());
            try {
                comment.setCreateTime(dateFomater.parse(commentEntity.getCommentDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            comment.setTargetId(lvxbangId);
            comment.setType(ProductType.hotel);
            comment.setStatus(CommentStatus.NORMAL);

            List<CommentScore> scores = new ArrayList<CommentScore>();
            for (long i = 5; i < 9; i++) {
                CommentScore commentScore = new CommentScore();
                int score = random.nextInt(100);
                commentScore.setScore(score);
                CommentScoreType scoreType = new CommentScoreType();
                scoreType.setId(i);
                commentScore.setCommentScoreType(scoreType);
                scores.add(commentScore);
            }
            List<CommentPhoto> photos = new ArrayList<CommentPhoto>();
            for (String path : commentEntity.getLargeImagePath()) {
                CommentPhoto photo = new CommentPhoto();
                photo.setImagePath(path);
                //TODO:此处先注释掉,因为艺龙的图片有水印,暂时现存原始地址,等确定处理方法之后再修改.——2016.05.09
//                String address;
//                try {
//                    address = QiniuUtil.UploadToQiniu(path, "comment", String.valueOf(comment.getId()));
//                } catch (Exception e) {
//                    address = photo.getImagePath();
//                    e.printStackTrace();
//                }
//                photo.setImagePath(address);
                photos.add(photo);
            }
            comment.setCommentScores(scores);
            comment.setCommentPhotos(photos);
            result.add(comment);
        }
        return result;

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
