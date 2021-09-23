package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.MemberDao;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysSite;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/27.
 */
@Service
public class MemberService {

	private final Logger logger = Logger.getLogger(MemberService.class);

	@Resource
	private MemberDao memberDao;

	public Member get(Long id) {
		Criteria<Member> criteria = new Criteria<Member>(Member.class);
		criteria.eq("id", id);
		return memberDao.findUniqueByCriteria(criteria);
	}

	public Member findByAccount(String account, SysSite sysSite) {
		Criteria<Member> criteria = new Criteria<Member>(Member.class);
		criteria.eq("account", account);
		criteria.eq("sysSite.id", sysSite.getId());
		return memberDao.findUniqueByCriteria(criteria);
	}

	public Member findByAccount(String account) {
		Criteria<Member> criteria = new Criteria<Member>(Member.class);
		criteria.eq("account", account);
		return memberDao.findUniqueByCriteria(criteria);
	}

	public Member findByUnionId(String unionId) {
		Criteria<Member> criteria = new Criteria<Member>(Member.class);
		criteria.eq("unionId", unionId);
//		criteria.eq("sysSite.id", sysSite.getId());
		List<Member> list = memberDao.findByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Member findByMobile(String mobile) {
		Criteria<Member> criteria = new Criteria<Member>(Member.class);
		criteria.eq("mobile", mobile);
		List<Member> list = memberDao.findByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

    public String findPwdById(Long id) {
        Criteria<Member> memberCriteria = new Criteria<Member>(Member.class);
        memberCriteria.eq("id", id);
        memberCriteria.setProjection(Projections.property("password"));
        return (String) memberDao.findUniqueValue(memberCriteria);
    }

	public List<Member> list(Member member, Page page, String... orderProperty) {
		Criteria<Member> criteria = createCriteria(member, orderProperty);
		if (page != null) {
			return memberDao.findByCriteria(criteria, page);
		}
		return memberDao.findByCriteria(criteria);
	}

	public Criteria<Member> createCriteria(Member member, String... orderProperty) {
		Criteria<Member> criteria = new Criteria<Member>(Member.class);
		if (orderProperty != null) {
			if (orderProperty.length > 1 && orderProperty[0] != null && orderProperty[1] != null) {
				criteria.orderBy(orderProperty[0], orderProperty[1]);
			} else if (orderProperty.length == 1 && orderProperty[0] != null) {
				criteria.orderBy(orderProperty[0], "desc");
			}
		}
		if (member == null) {
			return criteria;
		}

		if (!StringUtils.isBlank(member.getAccount())) {
			criteria.like("account", member.getAccount());
		}
		if (!StringUtils.isBlank(member.getMobile())) {
			criteria.like("mobile", member.getMobile());
		}
		if (!StringUtils.isBlank(member.getTelephone())) {
			criteria.like("telephone", member.getTelephone());
		}
		if (!StringUtils.isBlank(member.getAddress())) {
			criteria.like("address", member.getAddress());
		}
		if (!StringUtils.isBlank(member.getEmail())) {
			criteria.like("email", member.getEmail());
		}
		if (!StringUtils.isBlank(member.getUserName())) {
			criteria.like("userName", member.getUserName());
		}
        if (StringUtils.hasText(member.getNickName())) {
            criteria.like("nickName", member.getNickName());
        }
        if (member.getStatus() != null) {
			criteria.eq("status", member.getStatus());
		}
		if (member.getUserType() != null) {
			criteria.eq("userType", member.getUserType());
		}
        if (member.getGender() != null) {
            criteria.eq("gender", member.getGender());
        }
        if (member.getMinJifen() != null) {
            criteria.ge("jifen", member.getMinJifen());
        }
        if (member.getMaxJifen() != null) {
            criteria.le("jifen", member.getMaxJifen());
        }
        return criteria;
	}

	public boolean save(Member member) {
		try {
			member.setCreatedTime(new Date());
			member.setUpdateTime(new Date());
			memberDao.save(member);
		} catch (Exception e) {
			logger.error("注册失败", e);
			return false;
		}
		return true;
	}

	public boolean update(Member member) {
		try {
			memberDao.update(member);
		} catch (Exception e) {
			logger.error("更新member失败#" + member.getId(), e);
			return false;
		}
		return true;
	}
    public boolean saveOrUpdate(Member member) {
        try {
            memberDao.saveOrUpdate(member, member.getId());
        } catch (Exception e) {
            logger.error("更新/保存member失败#" + member.getId() == null ? "新建时" : member.getId(), e);
            return false;
        }
        return true;
    }
}
