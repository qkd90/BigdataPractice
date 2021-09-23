package com.data.data.hmly.service.outer;

import com.data.data.hmly.service.outer.dao.OuterQuestionCandidateDao;
import com.data.data.hmly.service.outer.dao.OuterQuestionDao;
import com.data.data.hmly.service.outer.entity.OuterQuestion;
import com.data.data.hmly.service.outer.entity.OuterQuestionCandidate;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caiys on 2016/11/29.
 */
@Service
public class OuterQuestionService {
    @Resource
    private OuterQuestionDao outerQuestionDao;
    @Resource
    private OuterQuestionCandidateDao outerQuestionCandidateDao;
    // 只做一次初始化
    private List<OuterQuestion> initQuestion = null;

    public List<OuterQuestion> getInitQuestion() {
        if (initQuestion == null) {
            initQuestion = listAll();
        }
        return initQuestion;
    }

    /**
     * 查询所有问题及关联的答案
     * @return
     */
    public List<OuterQuestion> listAll() {
        Criteria<OuterQuestion> criteriaQuestion = new Criteria<OuterQuestion>(OuterQuestion.class);
        List<OuterQuestion> questions = outerQuestionDao.findByCriteria(criteriaQuestion);

        Criteria<OuterQuestionCandidate> criteriaQuestionCandidate = new Criteria<OuterQuestionCandidate>(OuterQuestionCandidate.class);
        List<OuterQuestionCandidate> questionCandidates = outerQuestionCandidateDao.findByCriteria(criteriaQuestionCandidate);

        for (OuterQuestion q : questions) {
            List<OuterQuestionCandidate> candidates = new ArrayList<OuterQuestionCandidate>();
            for (OuterQuestionCandidate c : questionCandidates) {
                if (q.getId() == c.getQuestionId()) {
                    candidates.add(c);
                }
            }
            q.setQuestionCandidates(candidates);
        }
        return questions;
    }

}
