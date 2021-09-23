package com.data.data.hmly.service.plan.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendPlanSearchRequest extends SolrSearchRequest {

	private String name;
	private List<Integer> priceRange = new ArrayList<Integer>();
    private List<Integer> monthRange = new ArrayList<Integer>();
    private List<Integer> dayRange = new ArrayList<Integer>();
    private List<Integer> costRange = new ArrayList<Integer>();
	private Long userId;
	private Long scenicId;
    private Long delicacyId;
    private Long hotelId;
    private Long trafficId;
	private String scenicName;
	private String delicacyName;
	private List<Long> cityIds = new ArrayList<Long>();
	private Integer scenics;
    private List<Long> labelIds = new ArrayList<Long>();
	private String orderColumn;
	private SolrQuery.ORDER orderType;
    private final SolrType type = SolrType.recommend_plan;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public List<Integer> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(List<Integer> priceRange) {
        this.priceRange = priceRange;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public Long getDelicacyId() {
		return delicacyId;
	}

	public void setDelicacyId(Long delicacyId) {
		this.delicacyId = delicacyId;
	}

	public String getDelicacyName() {
		return delicacyName;
	}

	public void setDelicacyName(String delicacyName) {
		this.delicacyName = delicacyName;
	}

    public List<Long> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<Long> cityIds) {
        this.cityIds = cityIds;
    }

    public List<Integer> getDayRange() {
        return dayRange;
    }

    public void setDayRange(List<Integer> dayRange) {
        this.dayRange = dayRange;
    }

    public Integer getScenics() {
		return scenics;
	}

	public void setScenics(Integer scenics) {
		this.scenics = scenics;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public SolrQuery.ORDER getOrderType() {
		return orderType == null ? SolrQuery.ORDER.desc : orderType;
	}

	public void setOrderType(SolrQuery.ORDER orderType) {
		this.orderType = orderType;
	}

    public List<Integer> getMonthRange() {
        return monthRange;
    }

    public void setMonthRange(List<Integer> monthRange) {
        this.monthRange = monthRange;
    }

    public List<Integer> getCostRange() {
        return costRange;
    }

    public void setCostRange(List<Integer> costRange) {
        this.costRange = costRange;
    }

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }

    public SolrType getType() {
        return type;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(Long trafficId) {
        this.trafficId = trafficId;
    }

    public String getSolrQueryStr() {
		StringBuilder builder = new StringBuilder();
		Set<String> set = new HashSet<String>();
        String keywordToken = "";
		if (StringUtils.isNotBlank(name)) {
            keywordToken = getKeywordToken(name);
            set.add(keywordToken);
        }
        if (!priceRange.isEmpty()) {
			if (priceRange.size() == 1) {
				set.add(String.format("cost:[%s TO %s]", priceRange.get(0), Integer.MAX_VALUE));
			} else if (priceRange.size() == 2) {
				set.add(String.format("cost:[%s TO %s]", priceRange.get(0), priceRange.get(1)));
			}
		}
        if (userId != null) {
			set.add(String.format("userId:%s", userId));
		}
		if (scenicId != null) {
			set.add(String.format("scenicIds:%s", scenicId));
		}
		if (scenicName != null) {
			set.add(String.format("scenicName:%s", scenicName));
		}
		if (delicacyId != null) {
			set.add(String.format("delicacyIds:%s", delicacyId));
		}
        if (hotelId != null) {
            set.add(String.format("hotelIds:%s", hotelId));
        }
        if (trafficId != null) {
            set.add(String.format("trafficIds:%s", trafficId));
        }
		if (delicacyName != null) {
			set.add(String.format("delicacyName:%s", delicacyName));
		}
		if (!dayRange.isEmpty()) {
            if (dayRange.size() == 1) {
                set.add(String.format("days:[%s TO %s]", dayRange.get(0), Integer.MAX_VALUE));
            } else if (dayRange.size() == 2) {
                set.add(String.format("days:[%s TO %s]", dayRange.get(0), dayRange.get(1)));
            }
		}
        if (!monthRange.isEmpty() && monthRange.size() == 2) {
            set.add(String.format("startMonth:[%s TO %s]", monthRange.get(0), monthRange.get(1)));
        }
        if (!costRange.isEmpty()) {
            if (costRange.size() == 1) {
                set.add(String.format("cost:[%s TO %s]", costRange.get(0), Integer.MAX_VALUE));
            } else if (costRange.size() == 2) {
                set.add(String.format("cost:[%s TO %s]", costRange.get(0), costRange.get(1)));
            }
        }
		if (scenics != null) {
			set.add(String.format("scenics:%s", scenics));
		}
		if (!labelIds.isEmpty()) {
			for (Long labelId : labelIds) {
				set.add(String.format("labelIds:%s", labelId));
			}
		}
        if (!cityIds.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Long cityId : cityIds) {
                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                sb.append("cityIds:").append(cityId);
            }
            set.add(sb.append(")").toString());
        }
        set.add(String.format("type:%s", type));
		if (set.isEmpty()) {
			return "*:*";
		}
		for (String param : set) {
			if (builder.length() > 0) {
				builder.append(" AND ");
			}
			builder.append(param);
		}
		return builder.toString();
	}
    private String getKeywordToken(String name) {
        String keywordToken = "";
        try {
            keywordToken = IKTokenUtils.token(name);
            StringBuilder tokenSb = new StringBuilder();
            String[] tokenArr = keywordToken.split(" +");
            tokenSb.append("(");
            for (String s : tokenArr) {
                tokenSb.append("name:").append(s).append(" OR ");
            }
            tokenSb.delete(tokenSb.length() - 4, tokenSb.length());
            tokenSb.append(")");
            keywordToken = tokenSb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keywordToken;
    }

    public Class getResultClass() {
		return RecommendPlanSolrEntity.class;
	}
}