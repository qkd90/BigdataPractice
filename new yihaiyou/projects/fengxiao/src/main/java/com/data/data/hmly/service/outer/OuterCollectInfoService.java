package com.data.data.hmly.service.outer;

import com.data.data.hmly.service.outer.dao.OuterCollectInfoDao;
import com.data.data.hmly.service.outer.dao.OuterParticipatorAnswerDao;
import com.data.data.hmly.service.outer.entity.OuterCollectInfo;
import com.data.data.hmly.service.outer.entity.OuterParticipatorAnswer;
import com.data.data.hmly.service.outer.entity.OuterQuestion;
import com.data.data.hmly.service.outer.entity.OuterQuestionCandidate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/11/29.
 */
@Service
public class OuterCollectInfoService {
    @Resource
    private OuterCollectInfoDao outerCollectInfoDao;
    @Resource
    private OuterParticipatorAnswerDao outerParticipatorAnswerDao;
    @Resource
    private OuterQuestionService outerQuestionService;

    /**
     * 列表显示页面：全对的进入名单，按照提交速度排名，前20名获奖
     * @return
     */
    public List<OuterCollectInfo> list() {
        Criteria<OuterCollectInfo> criteria = new Criteria<OuterCollectInfo>(OuterCollectInfo.class);
//        criteria.eq("accuracy", 100);
        criteria.eq("agree", true);
        criteria.orderBy("accuracy", "desc");
        criteria.orderBy("createTime", "asc");
        Page page = new Page(1, 20);
        List<OuterCollectInfo> infos = outerCollectInfoDao.findByCriteria(criteria, page);
        return infos;
    }

    /**
     * 检查是否已经存在
     * @return
     */
    public boolean check(String checkValue) {
        Criteria<OuterCollectInfo> criteria = new Criteria<OuterCollectInfo>(OuterCollectInfo.class);
        criteria.eq("phone", checkValue);
        criteria.setProjection(Projections.rowCount());
        Long count = (Long) outerCollectInfoDao.findUniqueValue(criteria);
        if (count != null && count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存问答信息，候选值标识“,”分隔
     */
    public void saveCollectInfo(OuterCollectInfo outerCollectInfo, String candidateIds) {
        outerCollectInfo.setCreateTime(new Date());
        outerCollectInfo.setAccuracy(0);
        outerCollectInfoDao.save(outerCollectInfo);

        String[] candidateIdArray = candidateIds.split(",");
        List<OuterParticipatorAnswer> answers = new ArrayList<OuterParticipatorAnswer>();
        List<OuterQuestion> questions = outerQuestionService.getInitQuestion();
        int accuracyTrue = 0;   // 回答正确题数
        for (OuterQuestion q : questions) {
            int candidateTrue = 0;  // 候选值正确数
            int answerTrue = 0;     // 回答正确数
            for (OuterQuestionCandidate c :q.getQuestionCandidates()) {
                for (String candidateId : candidateIdArray) {
                    if (Long.valueOf(candidateId) == c.getId()) {
                        OuterParticipatorAnswer answer = new OuterParticipatorAnswer();
                        answer.setCollectInfoId(outerCollectInfo.getId());
                        answer.setQuestionId(c.getQuestionId());
                        answer.setCandidateId(c.getId());
                        answer.setAnswerFlag(c.getAnswerFlag());
                        answers.add(answer);
                        if (c.getAnswerFlag()) {    // 回答正确数
                            answerTrue++;
                        }
                    }
                }
                if (c.getAnswerFlag()) {    // 候选值正确数
                    candidateTrue++;
                }
            }
            if (answerTrue != 0 && answerTrue == candidateTrue) {
                accuracyTrue++;
            }
        }
        int accuracy = 0;   // 正确率
        if (questions.size() > 0) {
            accuracy = (int) (accuracyTrue * 1.0 / questions.size() * 100);
        }
        outerCollectInfo.setAccuracy(accuracy);
        outerCollectInfoDao.update(outerCollectInfo);
        outerParticipatorAnswerDao.save(answers);
    }

}
