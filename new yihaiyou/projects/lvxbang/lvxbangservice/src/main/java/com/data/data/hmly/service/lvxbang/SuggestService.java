package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.search.SearchService;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guoshijie on 2015/12/18.
 */
@Service
public class SuggestService {

	private final Logger logger = Logger.getLogger(SuggestService.class);

	private static final String SCENIC = "scenic_info";
	private static final String DELICACY = "delicacy";
	private static final String HOTEL = "hotel";
	private static final String PLAN = "plan";
	private static final String RECOMMEND_PLAN = "recommend_plan";
	private static final String DESTINATION = "area";
    private static final String LINE = "line";
    private static final String CRUISE_SHIP = "cruise_ship";
    private static final String CORE_NAME = "products";

	private static final int PAGE_SIZE = 8;

	@Resource
	private SearchService searchService;
	@Resource
	private SolrIndexService solrIndexService;

	public List<SuggestionEntity> suggestAll(String name, int size) {
        List<SuggestionEntity> result = Lists.newArrayList();
        try {
            List<SpellCheckResponse.Suggestion> suggestions = searchService.querySugguest(CORE_NAME, name);
            for (SpellCheckResponse.Suggestion suggestion : suggestions) {
                result.addAll(Lists.transform(suggestion.getAlternatives(), new Function<String, SuggestionEntity>() {
                    @Override
                    public SuggestionEntity apply(String s) {
                        SuggestionEntity suggestion = new SuggestionEntity();
                        suggestion.setName(s);
                        return suggestion;
                    }
                }));
            }
        } catch (SolrServerException e) {
            logger.error("server error", e);
        }
        if (!result.isEmpty() && result.size() > size) {
            return result.subList(0, size);
        }
        return result;
	}

	public List<SuggestionEntity> suggestDestination(String name, Integer level, Integer page) {
		return suggest(name, DESTINATION, level, page);
	}

    public List<SuggestionEntity> suggestDestinationAndScenic(String name, Integer level, Integer pageSize) {
         int page = PAGE_SIZE;
        if (pageSize != null) {
            page = pageSize;
        }
        if (StringUtils.isBlank(name)) {
            return Lists.newArrayList();
        }

        try {
            String keywordToken = IKTokenUtils.token(name);
            StringBuilder sb = new StringBuilder();
            String[] arr = keywordToken.split(" +");
            sb.append("(");
            for (String s : arr) {
                sb.append("searchall:").append(s).append("*").append(" OR ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(")");
            sb.append(" AND (type:area OR type:scenic_info)");
            if (level != null) {
                sb.append(" AND level:").append(level);
            }
            return solrIndexService.suggest(sb.toString(), page);
        } catch (Exception e) {
            logger.error("tokenize failed", e);
            return Lists.newArrayList();
        }
    }

    public List<SuggestionEntity> suggestPlan(String name, Integer page) {
        return suggest(name, PLAN, null, page);
    }

	public List<SuggestionEntity> suggestRecommendPlan(String name, Integer page) {
		return suggest(name, RECOMMEND_PLAN, null, page);
	}

	public List<SuggestionEntity> suggestHotel(List<Long> cityIds, String name, Integer page) {
		return suggest(cityIds, name, HOTEL, null, page);
	}
    public List<SuggestionEntity> suggestHotel(String name, Integer page) {
		return suggest(name, HOTEL, null, page);
	}
    public List<SuggestionEntity> suggestLine(List<Long> cityIds, String name, Integer page) {
		return suggest(cityIds, name, LINE, null, page);
	}
    public List<SuggestionEntity> suggestLine(String name, Integer page) {
		return suggest(name, LINE, null, page);
    }

    public List<SuggestionEntity> suggestCruiseShip(String name, Integer page) {
        return suggest(name, CRUISE_SHIP, null, page);
    }

	public List<SuggestionEntity> suggestScenic(List<Long> cityIds, String name, Integer page) {
		return suggest(cityIds, name, SCENIC, null, page);
	}
    public List<SuggestionEntity> suggestScenic(String name, Integer page) {
		return suggest(name, SCENIC, null, page);
	}

	public List<SuggestionEntity> suggestDelicacy(String name, Integer page) {
		return suggest(name, DELICACY, null, page);
	}
    public List<SuggestionEntity> suggestDelicacy(List<Long> cityIds, String name, Integer page) {
		return suggest(cityIds, name, DELICACY, null, page);
	}

    public List<SuggestionEntity> suggest(String name, String type, Integer level, Integer pageSize) {
        int page = PAGE_SIZE;
        if (pageSize != null) {
            page = pageSize;
        }
        if (StringUtils.isBlank(name)) {
            return Lists.newArrayList();
        }

        try {
            String keywordToken = IKTokenUtils.token(name);
            StringBuilder sb = new StringBuilder();
            String[] arr = keywordToken.split(" +");
            sb.append("(");
            for (String s : arr) {
                sb.append("name:").append(s).append("*").append(" OR ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(")");
            sb.append(" AND type:").append(type);
            if (level != null) {
                sb.append(" AND level:").append(level);
            }
            return solrIndexService.suggest(sb.toString(), page);
        } catch (Exception e) {
            logger.error("tokenize failed", e);
            return Lists.newArrayList();
        }
    }
    public List<SuggestionEntity> suggest(List<Long> cityIds, String name, String type, Integer level, Integer pageSize) {
        int page = PAGE_SIZE;
        if (pageSize != null) {
            page = pageSize;
        }
        if (StringUtils.isBlank(name)) {
            return Lists.newArrayList();
        }

        try {
            String keywordToken = IKTokenUtils.token(name);
            StringBuilder sb = new StringBuilder();
            String[] arr = keywordToken.split(" +");
            sb.append("(");
            for (String s : arr) {
                sb.append("searchall:").append(s).append("*").append(" OR ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(")");
            sb.append(" AND type:").append(type);
            if (level != null) {
                sb.append(" AND level:").append(level);
            }
            if (!cityIds.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                builder.append(" AND (");
                for (Long cityId : cityIds) {
                    if (builder.length() > 8) {
                        builder.append(" OR ");
                    }
                    if (cityId % 10000 == 0) {
                        builder.append(String.format("cityId:[%d TO %d]", cityId, cityId + 9999));
                    } else if (cityId % 100 == 0) {
                        builder.append(String.format("cityId:[%d TO %d]", cityId, cityId + 99));
                    } else {
                        builder.append(String.format("cityId:%d", cityId));
                    }
                }
                builder.append(")").toString();
                sb.append(builder);
            }

            return solrIndexService.suggest(sb.toString(), page);
        } catch (Exception e) {
            logger.error("tokenize failed", e);
            return Lists.newArrayList();
        }
    }

	public List<SuggestionEntity> suggest(String name, Integer pageSize) {
		int page = PAGE_SIZE;
		if (pageSize != null) {
			page = pageSize;
		}
//		String query = "name:" + name;
//		return solrIndexService.suggest(query, page);

        try {
            String keywordToken = IKTokenUtils.token(name);
            StringBuilder sb = new StringBuilder();
            String[] arr = keywordToken.split(" +");
            sb.append("(");
            for (String s : arr) {
                sb.append("searchall:").append(s).append("*").append(" OR ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(")");
            return solrIndexService.suggest(sb.toString(), page);
        } catch (Exception e) {
            logger.error("tokenize failed", e);
            return Lists.newArrayList();
        }
	}

}
