
package com.data.data.hmly.service.ctripflight.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ctrip_insurance_info")
public class CtripInsuranceInfo {

	
	private static final long serialVersionUID = -2996522995238348090L;
	@Id
	@Column(name = "id", length = 11)
	private Integer id;
    @Column(name = "productCode")
    private String productCode;
    @Column(name = "insuranceTypeIds" )
    private String insuranceTypeIds;
    @Column(name = "minInsuranceNumber" )
    private String minInsuranceNumber;
    @Column(name = "saleRemark"  )
    private String saleRemark;
}
