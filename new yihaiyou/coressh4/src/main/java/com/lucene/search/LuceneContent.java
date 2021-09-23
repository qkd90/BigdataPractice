package com.lucene.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.CachingCollector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.SearchGroup;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.search.grouping.term.TermFirstPassGroupingCollector;
import org.apache.lucene.search.grouping.term.TermSecondPassGroupingCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.zuipin.entity.VMallSearchProduct;
import com.zuipin.entity.VProductIndex;
import com.zuipin.model.PageVo;
import com.zuipin.model.SearchCatVO;
import com.zuipin.model.SearchStoreVO;
import com.zuipin.model.SearchVO;
import com.zuipin.util.StringUtils;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author:zhengry
 * @version Revision 2.0.0
 * @email:zryuan@xiangyu.cn
 * @see:
 * @创建日期：2013-5-2
 * @功能说明：lucene工具类
 */
public class LuceneContent {
	/**
	 * @param list
	 * @return
	 * @author:zhengry
	 * @email:zryuan@xiangyu.cn
	 * @创建日期:2013-4-22
	 * @功能说明：获得Lucene格式的Document
	 */
	public static Document createDocument(VProductIndex ic) {
		Document doc = new Document();
		doc.add(new Field(ID, ic.getId() + "", Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PRO_NAME, StringUtils.replaceNull(ic.getProName()), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field(BRAND_NAME, StringUtils.replaceNull(ic.getBrandName()), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field(CAT_NAME_X, StringUtils.replaceNull(ic.getCatNameX()), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field(CAT_ID_X, StringUtils.replaceNull(ic.getCatIdX() + ""), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(CAT_NAME_Y, StringUtils.replaceNull(ic.getCatNameY()), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field(CAT_ID_Y, StringUtils.replaceNull(ic.getCatIdY() + ""), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(CAT_NAME_Z, StringUtils.replaceNull(ic.getCatNameZ()), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field(CAT_ID_Z, StringUtils.replaceNull(ic.getCatIdZ() + ""), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(SALE_PRICE, StringUtils.replaceNull(ic.getSalePrice() + ""), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field(ON_SALE_DATE, DateTools.dateToString(ic.getOnSaleDate(), Resolution.DAY), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(NET_SALE_COUNT, StringUtils.replaceNull(ic.getNetSaleCount() + ""), Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field(COMM_COUNT, StringUtils.replaceNull(ic.getCommCount() + ""), Field.Store.NO, Field.Index.NOT_ANALYZED));
		if (StringUtils.isNotBlank(ic.getValueId())) {
			StringTokenizer tokenizer = new StringTokenizer(ic.getValueId(), ",");
			while (tokenizer.hasMoreTokens()) {
				String s = tokenizer.nextToken();
				doc.add(new Field(VALUE_ID, s, Field.Store.NO, Field.Index.NOT_ANALYZED));
			}
		}
		doc.add(new Field(MALL_KEY_WORDS, StringUtils.replaceNull(ic.getMallKeyWords()), Field.Store.NO, Field.Index.ANALYZED));
		return doc;
	}
	
	public static Query createQuery(SearchVO vo, Analyzer analyzer) throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		Query q;
		if (StringUtils.isNotBlank(vo.getKeyword())) {
			// q = MultiFieldQueryParser.parse(Version.LUCENE_36,
			// QueryParser.escape(vo.getKeyword()), QUERY_FIELD, QUERY_FLAGS,
			// analyzer);
			q = new QueryParser(Version.LUCENE_47, PRO_NAME, analyzer).parse(QueryParser.escape(vo.getKeyword()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotBlank(vo.getCid2())) {
			q = new TermQuery(new Term(CAT_ID_Y, vo.getCid2()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotBlank(vo.getCid3())) {
			q = new TermQuery(new Term(CAT_ID_Z, vo.getCid3()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotBlank(vo.getFilter())) {
			StringTokenizer kenizer = new StringTokenizer(vo.getFilter(), "&");
			String value = "";
			while (kenizer.hasMoreTokens()) {
				value = kenizer.nextToken();
				if (value.equals("0") || StringUtils.isBlank(value)) {
					continue;
				}
				q = new TermQuery(new Term(VALUE_ID, value));
				bq.add(q, BooleanClause.Occur.MUST);
			}
		}
		if (StringUtils.isNotBlank(vo.getAreaId())) {
			q = new TermQuery(new Term(AREA_ID, vo.getAreaId()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		return bq;
	}
	
	public static Query createQuery(String keyword, Analyzer analyzer) throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		Query q;
		if (StringUtils.isNotBlank(keyword)) {
			q = new QueryParser(Version.LUCENE_36, PRO_NAME, analyzer).parse(QueryParser.escape(keyword));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		return bq;
	}
	
	public static Sort sort(SearchVO vo) {
		if (StringUtils.isNotBlank(vo.getPsort())) {
			int sortType = Integer.valueOf(vo.getPsort());
			Sort sort = new Sort(new SortField[] { SortField.FIELD_SCORE, new SortField(NET_SALE_COUNT, SortField.Type.DOUBLE, true),
					new SortField(COMM_COUNT, SortField.Type.INT, true), new SortField(ON_SALE_DATE, SortField.Type.LONG, true), });
			switch (sortType) {
				case 1:// 销量
					sort = new Sort(new SortField(NET_SALE_COUNT, SortField.Type.DOUBLE, true));
					break;
				case 2: // 价格降序
					sort = new Sort(new SortField(SALE_PRICE, SortField.Type.DOUBLE, true));
					break;
				case 3:// 评论量
					sort = new Sort(new SortField(COMM_COUNT, SortField.Type.INT, true));
					break;
				case 4: // 上架时间
					sort = new Sort(new SortField(ON_SALE_DATE, SortField.Type.LONG, true));
					break;
				case 5: // 销量升序
					sort = new Sort(new SortField(NET_SALE_COUNT, SortField.Type.DOUBLE, false));
					break;
				case 6: // 价格升序
					sort = new Sort(new SortField(SALE_PRICE, SortField.Type.DOUBLE, false));
					break;
				case 7: // 评论量升序
					sort = new Sort(new SortField(COMM_COUNT, SortField.Type.INT, false));
					break;
				case 8: // 上架时间升序
					sort = new Sort(new SortField(ON_SALE_DATE, SortField.Type.LONG, false));
					break;
				default:
					break;
			}
			return sort;
		}
		return new Sort();
	}
	
	public static void delete(Long pid, IndexWriter writer) throws CorruptIndexException, IOException, ParseException {
		writer.deleteDocuments(new Term(ID, pid.toString()));
	}
	
	public static List<Long> getResultList(IndexSearcher searcher, TopDocs docs, PageVo<VMallSearchProduct> page, int pageNo, int pageSize) throws CorruptIndexException,
			IOException {
		List<Long> list = new ArrayList<Long>(pageSize);
		ScoreDoc[] hits = docs.scoreDocs;
		int endIndex = pageNo * pageSize;
		int len = hits.length;
		if (endIndex > len) {
			endIndex = len;
		}
		Document d = null;
		for (int i = (pageNo - 1) * pageSize; i < endIndex; i++) {
			d = searcher.doc(hits[i].doc);
			list.add(Long.valueOf(d.get(ID)));
		}
		return list;
	}
	
	public static void groupByCatY(IndexSearcher searcher, Query query, PageVo<VMallSearchProduct> page) {
		try {
			GroupDocs<BytesRef>[] gds = groupBy(CAT_ID_Y, searcher, query, DEFAULT_GROUP);
			if (gds == null)
				return;
			Document d = null;
			SearchCatVO searchCatVO = null;
			List<SearchCatVO> list = new ArrayList<SearchCatVO>();
			for (GroupDocs<BytesRef> gd : gds) {
				d = searcher.doc(gd.scoreDocs[0].doc);
				searchCatVO = new SearchCatVO();
				searchCatVO.setCatId(Long.valueOf(d.get(CAT_ID_Y)));
				searchCatVO.setCatName(d.get(CAT_NAME_Y));
				searchCatVO.setPcount(gd.totalHits);
				list.add(searchCatVO);
			}
			page.setSecSearchCatVOs(list);
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void groupByCatZ(IndexSearcher searcher, Query query, PageVo<VMallSearchProduct> page) {
		try {
			GroupDocs<BytesRef>[] gds = groupBy(CAT_ID_Z, searcher, query, DEFAULT_GROUP);
			if (gds == null)
				return;
			Document d = null;
			SearchCatVO searchCatVO = null;
			List<SearchCatVO> list = new ArrayList<SearchCatVO>();
			for (GroupDocs<BytesRef> gd : gds) {
				d = searcher.doc(gd.scoreDocs[0].doc);
				searchCatVO = new SearchCatVO();
				searchCatVO.setParentCatId(Long.valueOf(d.get(CAT_ID_Y)));
				searchCatVO.setCatId(Long.valueOf(d.get(CAT_ID_Z)));
				searchCatVO.setCatName(d.get(CAT_NAME_Z));
				searchCatVO.setPcount(gd.totalHits);
				list.add(searchCatVO);
			}
			page.setSubSearchCatVOs(list);
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static List<SearchCatVO> groupByCatZ(IndexSearcher searcher, Query query) {
		List<SearchCatVO> list = new ArrayList<SearchCatVO>();
		try {
			GroupDocs<BytesRef>[] gds = groupBy(CAT_ID_Z, searcher, query, SUGGEST_GROUP);
			if (gds == null)
				return list;
			Document d = null;
			SearchCatVO searchCatVO = null;
			for (GroupDocs<BytesRef> gd : gds) {
				d = searcher.doc(gd.scoreDocs[0].doc);
				searchCatVO = new SearchCatVO();
				searchCatVO.setCatId(Long.valueOf(d.get(CAT_ID_Z)));
				searchCatVO.setCatName(d.get(CAT_NAME_Z));
				searchCatVO.setPcount(gd.totalHits);
				list.add(searchCatVO);
			}
			return list;
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	public static void groupByStore(IndexSearcher searcher, Query query, PageVo<VMallSearchProduct> page) {
		try {
			GroupDocs<BytesRef>[] gds = groupBy(STORE_ID, searcher, query, DEFAULT_GROUP);
			if (gds == null)
				return;
			Document d = null;
			SearchStoreVO searchStoreVO = null;
			List<SearchStoreVO> list = new ArrayList<SearchStoreVO>();
			for (GroupDocs<BytesRef> gd : gds) {
				d = searcher.doc(gd.scoreDocs[0].doc);
				if (StringUtils.isNotBlank(d.get(STORE_ID)) && StringUtils.isNotBlank(d.get(MERCHANT_ID))) {
					searchStoreVO = new SearchStoreVO();
					searchStoreVO.setMerchantId(Long.valueOf(d.get(MERCHANT_ID)));
					searchStoreVO.setStoreId(Long.valueOf(d.get(STORE_ID)));
					searchStoreVO.setStoreName(d.get(STORE_NAME));
					searchStoreVO.setSubStoreName(d.get(SUB_STORE_NAME));
					list.add(searchStoreVO);
				}
			}
			page.setSearchStoreVOs(list);
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static GroupDocs<BytesRef>[] groupBy(String field, IndexSearcher searcher, Query query, int topNGroups) throws CorruptIndexException, IOException, ParseException {
		int groupOffset = 0;
		int maxDocsPerGroup = 200;
		int withinGroupOffset = 0;
		try {
			TermFirstPassGroupingCollector c1 = new TermFirstPassGroupingCollector(field, Sort.RELEVANCE, topNGroups);
			boolean cacheScores = true;
			double maxCacheRAMMB = 4.0;
			CachingCollector cachedCollector = CachingCollector.create(c1, cacheScores, maxCacheRAMMB);
			searcher.search(query, cachedCollector);
			Collection<SearchGroup<BytesRef>> topGroups = c1.getTopGroups(groupOffset, true);
			if (topGroups == null) {
				return null;
			}
			TermSecondPassGroupingCollector c2 = new TermSecondPassGroupingCollector(field, topGroups, Sort.RELEVANCE, Sort.RELEVANCE, maxDocsPerGroup, true, true, true);
			if (cachedCollector.isCached()) {
				cachedCollector.replay(c2);
			} else {
				searcher.search(query, c2);
			}
			TopGroups<BytesRef> tg = c2.getTopGroups(withinGroupOffset);
			return tg.groups;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static String highlighter(Query query, String prodName) throws IOException, InvalidTokenOffsetsException {
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter());
		return highlighter.getBestFragment(new IKAnalyzer(true), PRO_NAME, prodName);
	}
	
	private static Log			logger			= LogFactory.getLog(LuceneContent.class);
	private final static String	ID				= "id";
	private final static String	PRO_NAME		= "pro_name";
	private final static String	BRAND_NAME		= "brand_name";
	private final static String	CAT_NAME_X		= "cat_name_x";
	private final static String	CAT_ID_X		= "cat_id_x";
	private final static String	CAT_NAME_Y		= "cat_name_y";
	private final static String	CAT_ID_Y		= "cat_id_y";
	private final static String	CAT_NAME_Z		= "cat_name_z";
	private final static String	CAT_ID_Z		= "cat_id_z";
	private final static String	SALE_PRICE		= "sale_price";
	private final static String	ON_SALE_DATE	= "on_sale_date";
	private final static String	NET_SALE_COUNT	= "net_sale_count";
	private final static String	COMM_COUNT		= "comm_count";
	private final static String	VALUE_ID		= "value_id";
	private final static String	MALL_KEY_WORDS	= "mall_key_words";
	private final static String	MERCHANT_ID		= "merchant_id";
	private final static String	STORE_ID		= "store_id";
	private final static String	STORE_NAME		= "store_name";
	private final static String	SUB_STORE_NAME	= "sub_store_name";
	private final static String	AREA_ID			= "area_id";
	private final static String	AREA_NAME		= "area_name";
	// , BRAND_NAME, CAT_NAME_Z
	// private final static String[] QUERY_FIELD = {PRO_NAME };
	// , BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD
	// private final static BooleanClause.Occur[] QUERY_FLAGS =
	// {BooleanClause.Occur.SHOULD };
	private final static int	DEFAULT_GROUP	= 100;
	private final static int	SUGGEST_GROUP	= 2;
}
