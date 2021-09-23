package com.data.data.hmly.service.fenxiao.aspect;

import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypepricedate;

//@Aspect
@Service
public class SysUserAspect {
//	@Resource
//	private SysUserService sysUserService;

//	@After("(execution (* com.data.data.hmly.service.dao.SysUserDao.save(..))) or (execution (* com.data.data.hmly.service.dao.SysUserDao.update(..)))")
//	@After("(execution (* com.data.data.hmly.service.dao.SysUserDao.save(..))) or (execution (* com.data.data.hmly.service.dao.SysUserDao.update(..)))")
//	public void addUserRelation(JoinPoint joinPoint) {
//		try {
//			Object[] objs = joinPoint.getArgs();
//			SysUser sysUser = (SysUser) objs[0];
//			if (sysUser.getUserType() != UserType.ScenicManage) {	// 排除景点管理员，如果底下有其他账号需修改
//				sysUserService.editUserRelation(sysUser);
//			}
//
////			lineService.doIndexLine(line);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/*@After("(execution (* com.data.data.hmly.service.dao.SysUserDao.update(..)))")
	public void updateUserRelation(JoinPoint joinPoint) {
		try {
			Object[] objs = joinPoint.getArgs();
			SysUser sysUser = (SysUser) objs[0];
			
			sysUserService.editUserRelation(sysUser);
			
//			lineService.doIndexLine(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

/*	@After("(execution (* com.data.data.hmly.service.line.dao.LinetypepricedateDao.save(..))) or (execution (* com.data.data.hmly.service.line.dao.LinetypepricedateDao.update(..)))")
	public void linetypepricedateVisits(JoinPoint joinPoint) {
		try {
			Object[] objs = joinPoint.getArgs();
			Linetypepricedate linetypepricedate = null;
			if (objs[0] instanceof List) {
				List<Linetypepricedate> list = (List<Linetypepricedate>) objs[0];
				linetypepricedate = list.get(0);
			} else {
				linetypepricedate = (Linetypepricedate) objs[0];
			}
			Line line = lineService.loadLine(linetypepricedate.getLineId());
			lineService.doIndexLine(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
