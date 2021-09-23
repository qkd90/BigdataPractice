package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.enums.SysUnitImageType;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.nums.SettlementType;
import com.data.data.hmly.service.contract.entity.nums.ValuationModels;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitImage;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.user.util.SmsUtil;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/11/23.
 */
public class YhyUserAction extends FrameBaseAction {

    @Resource
    private MsgService msgService;
    private String telephone;
    private Map<String, Object> map = new HashMap<String, Object>();
    public static final String SMS_CODE_KEY = "sms_verification_code";


    private SysUnit company = new SysUnit();
    private SysUser user = new SysUser();



    @Resource
    private SysUnitService unitService;

    @Resource
    private SysUserService sysUserService;
    @Resource
    private ContractService contractService;



    public Result checkMsgCode() {
        String msgCode = (String) getParameter("msgCode");
        if (StringUtils.isNotBlank(msgCode)) {
            Integer realMsgCode = (Integer) getSession().getAttribute(SMS_CODE_KEY);
            if (realMsgCode == Integer.parseInt(msgCode)) {
                map.put("valid", true);
            } else {
                map.put("valid", false);
            }
        } else {
            map.put("valid", false);
        }
        return jsonResult(map);
    }

    public Result sendUpdateMobileMsg() {
        if (StringUtils.isNotBlank(telephone)) {
            SmsUtil.sendRegisterMessage(telephone);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }

    /**
     * 商户信息
     * @return
     */
    public Result getCompanyInfo() {

        user = sysUserService.findSysUserById(getLoginUser().getId());
        company = unitService.findSysUnitById(getCompanyUnit().getId());
        Contract contract = contractService.getContractByCompanyB(company);
        map.put("data", getTenantData(user, company, contract));
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));

    }

    /**
     * 拼装商户信息数据
     * @PARAM USER
     * @PARAM UNIT
     * @PARAM CONTRACT
     * @RETURN
     */
    public Map<String, Object> getTenantData(SysUser user, SysUnit unit, Contract contract) {
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("userId", user.getId());
        tempMap.put("account", user.getAccount());
        tempMap.put("password", user.getPassword());
        tempMap.put("mobile", user.getMobile());
        tempMap.put("userName", user.getUserName());
        if (contract != null) {
            tempMap.put("effectiveTime", DateUtils.format(contract.getEffectiveTime(), "yyyy-MM-dd"));
            tempMap.put("expirationTime", DateUtils.format(contract.getExpirationTime(), "yyyy-MM-dd"));
            if (contract.getValuationModels() == ValuationModels.commissionModel) {
                tempMap.put("valuationModels", "按" + contract.getValuationValue() + "%佣金模式");
            } else if (contract.getValuationModels() == ValuationModels.fixedModel) {
                tempMap.put("valuationModels", "按" + contract.getValuationValue() + "元模式");
            } else {
                tempMap.put("valuationModels", "按底价模式");
            }

            if (contract.getSettlementType() == SettlementType.month) {
                tempMap.put("settlementType", "每月" + contract.getSettlementValue() + "日");
            } else if (contract.getSettlementType() ==  SettlementType.week) {
                if (contract.getSettlementValue() == 1) {
                    tempMap.put("settlementType", "每周日");
                } else if (contract.getSettlementValue() == 2) {
                    tempMap.put("settlementType", "每周一");
                } else if (contract.getSettlementValue() == 3) {
                    tempMap.put("settlementType", "每周二");
                } else if (contract.getSettlementValue() == 4) {
                    tempMap.put("settlementType", "每周三");
                } else if (contract.getSettlementValue() == 5) {
                    tempMap.put("settlementType", "每周四");
                } else if (contract.getSettlementValue() == 6) {
                    tempMap.put("settlementType", "每周五");
                } else if (contract.getSettlementValue() == 7) {
                    tempMap.put("settlementType", "每周六");
                }
            } else {
                tempMap.put("settlementType", "T+" + contract.getSettlementValue() + "日");
            }
        } else {
            tempMap.put("effectiveTime", "请签约合同");
            tempMap.put("expirationTime", "请签约合同");
            tempMap.put("valuationModels", "请签约合同");
            tempMap.put("settlementType", "请签约合同");
        }
        if (unit.getSysUnitDetail() != null) {
            tempMap.put("legalPerson", unit.getSysUnitDetail().getLegalPerson());
            tempMap.put("legalIdCardNo", unit.getSysUnitDetail().getLegalIdCardNo());
            tempMap.put("mainBusiness", unit.getSysUnitDetail().getMainBusiness());
            tempMap.put("certificateType", unit.getSysUnitDetail().getCertificateType());
//            tempMap.put("passportNo", unit.getSysUnitDetail().getPassportNo());
        }
        if (unit.getSysUnitImages() != null && !unit.getSysUnitImages().isEmpty()) {
            List<String> idcardImgs = Lists.newArrayList();
            for (SysUnitImage image : unit.getSysUnitImages()) {
                if (image.getType() == SysUnitImageType.POSITIVE_IDCARD) {
                    tempMap.put("positiveIdcard", image.getPath());
                } else if (image.getType() == SysUnitImageType.OPPOSITIVE_IDCARD) {
                    tempMap.put("oppositiveIdcard", image.getPath());
                } else if (image.getType() == SysUnitImageType.BUSINESS_LICENSE) {
                    tempMap.put("businessLicenseImg", image.getPath());
                }
            }
            tempMap.put("idCardImg", idcardImgs);
        }

        return tempMap;

    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public SysUnit getCompany() {
        return company;
    }

    public void setCompany(SysUnit company) {
        this.company = company;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
}
