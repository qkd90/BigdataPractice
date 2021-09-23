package com.data.data.hmly.service.yhy;

import com.data.data.hmly.enums.SysUnitImageType;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.nums.SettlementType;
import com.data.data.hmly.service.contract.entity.nums.ValuationModels;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitImage;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.action.yhy.vo.CompanyInfoData;
import com.zuipin.util.QiniuUtil;
import org.springframework.stereotype.Service;

/**
 * Created by dy on 2016/12/28.
 */
@Service
public class YhyCompanyInfoService {


    public CompanyInfoData getCompanyInfo(SysUnit company, SysUser user, Contract contract) {
        CompanyInfoData companyInfoData = new CompanyInfoData();
        companyInfoData.setUserId(user.getId());
        companyInfoData.setAccount(user.getAccount());
        companyInfoData.setPassword(user.getPassword());
        companyInfoData.setMobile(user.getMobile());
        companyInfoData.setUserName(user.getUserName());
        companyInfoData.setStatus(company.getStatus());
        if (contract != null) {
            companyInfoData.setEffectiveTime(contract.getEffectiveTime());
            companyInfoData.setExpirationTime(contract.getExpirationTime());
            if (contract.getValuationModels() == ValuationModels.commissionModel) {
                companyInfoData.setValuationModels("按" + contract.getValuationValue() + "%佣金模式");
            } else if (contract.getValuationModels() == ValuationModels.fixedModel) {
                companyInfoData.setValuationModels("按" + contract.getValuationValue() + "元模式");
            } else {
                companyInfoData.setValuationModels("按底价模式");
            }

            if (contract.getSettlementType() == SettlementType.month) {
                companyInfoData.setSettlementType("每月" + contract.getSettlementValue() + "日");
            } else if (contract.getSettlementType() ==  SettlementType.week) {
                if (contract.getSettlementValue() == 1) {
                    companyInfoData.setSettlementType("每周日");
                } else if (contract.getSettlementValue() == 2) {
                    companyInfoData.setSettlementType("每周一");
                } else if (contract.getSettlementValue() == 3) {
                    companyInfoData.setSettlementType("每周二");
                } else if (contract.getSettlementValue() == 4) {
                    companyInfoData.setSettlementType("每周三");
                } else if (contract.getSettlementValue() == 5) {
                    companyInfoData.setSettlementType("每周四");
                } else if (contract.getSettlementValue() == 6) {
                    companyInfoData.setSettlementType("每周五");
                } else if (contract.getSettlementValue() == 7) {
                    companyInfoData.setSettlementType("每周六");
                }
            } else {
                companyInfoData.setSettlementType("T+" + contract.getSettlementValue() + "日");
            }
        } else {
            companyInfoData.setValuationModels("请签约合同");
            companyInfoData.setSettlementType("请签约合同");
        }

        if (company.getSysUnitDetail() != null) {
            companyInfoData.setLegalPerson(company.getSysUnitDetail().getLegalPerson());
            companyInfoData.setLegalIdCardNo(company.getSysUnitDetail().getLegalIdCardNo());
            companyInfoData.setMainBusiness(company.getSysUnitDetail().getMainBusiness());
            companyInfoData.setCertificateType(company.getSysUnitDetail().getCertificateType().toString());
//            companyInfoData.setPassportNo(company.getSysUnitDetail().getPassportNo());
        }
        if (company.getSysUnitImages() != null && !company.getSysUnitImages().isEmpty()) {
            for (SysUnitImage image : company.getSysUnitImages()) {
                if (image.getType() == SysUnitImageType.POSITIVE_IDCARD) {
                    companyInfoData.setPositiveIdcard(QiniuUtil.URL + image.getPath());
                } else if (image.getType() == SysUnitImageType.OPPOSITIVE_IDCARD) {
                    companyInfoData.setOppositiveIdcard(QiniuUtil.URL + image.getPath());
                } else if (image.getType() == SysUnitImageType.BUSINESS_LICENSE) {
                    companyInfoData.setBusinessLicenseImg(QiniuUtil.URL + image.getPath());
                } else if (image.getType() == SysUnitImageType.PARSSPORT) {
                    companyInfoData.setPassportImg(QiniuUtil.URL + image.getPath());
                }
            }
        }

        if (company.getQualificationList() != null && !company.getQualificationList().isEmpty()) {
            companyInfoData.setQualificationList(company.getQualificationList());
        }

        if (contract != null && !contract.getAppendicesList().isEmpty()) {
            companyInfoData.setAppendiceses(contract.getAppendicesList());
        }

        return companyInfoData;
    }
}
