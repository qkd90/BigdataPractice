package com.zuipin.action.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.sqoop.Sqoop;
import org.apache.sqoop.tool.ImportTool;
import org.conan.mymahout.hdfs.HdfsDAO;
import org.conan.mymahout.hdfs.LineCallBack;
import org.conan.mymahout.recommendation.ItemCFHadoop;
import org.conan.mymahout.recommendation.book.BookEvaluator;
import org.conan.mymahout.recommendation.book.RecommendFactory;
import org.conan.mymahout.recommendation.book.RecommendFactory.SIMILARITY;

import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.pojo.VUserProduct;
import com.zuipin.service.PingguResultService;
import com.zuipin.service.UserProductService;
import com.zuipin.service.entity.PingguResult;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;

/**
 * @author hadoop
 */
// 1.导入需要分析关联的数据，包括member、orderInfo、orderDetail三张表
// 2.访问 makeFile 生成本地文件关联文件
// 3.生成算法评估pinggu
// 4.上传本地所生成的关联文件uploadFileToHDFS
// 5.使用评估完毕的算法，生成关联
public class MahoutAction extends JxmallAction {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4047117017643863522L;
	private final static Log	log					= LogFactory.getLog(MahoutAction.class);
	@Resource
	private DataSource			dataSource;
	@Resource
	private Configuration		hadoopConfiguration;
	@Resource
	private PropertiesManager	propertiesManager;
	@Resource
	private UserProductService	userProductService;
	@Resource
	private PingguResultService	pingguResultService;
	
	public Result itemSimilay() throws TasteException, IOException {
		// ItemSimilarityJob
		MySQLJDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, "vUserProducts", "userId", "itemId", "preference", null);
		BookEvaluator evaluator = new BookEvaluator();
		evaluator.userEuclidean(dataModel, 2);
		return text("success");
	}
	
	public Result sqoop() throws TasteException, IOException {
		// ItemSimilarityJob
		ImportTool importTool = new ImportTool();
		String url = propertiesManager.getString("database.url");
		String username = propertiesManager.getString("database.user");
		String pwd = propertiesManager.getString("database.password");
		String table = "vUserProducts";
		String day = DateUtils.getSysShortDate();
		String command = String.format("--connect %s --username %s --password %s --table %s -m 1 --target-dir /analysis/user/input/%s", url, username, pwd, table, day);
		Sqoop sqoop = new Sqoop(importTool);
		sqoop.setConf(hadoopConfiguration);
		Sqoop.runSqoop(sqoop, command.split(" "));
		return text("success");
	}
	
	public Result makeFile() throws TasteException, IOException {
		int pageSize = 10000;
		int pageIndex = 1;
		List<VUserProduct> userProducts = null;
		Boolean con = true;
		String tempPath = propertiesManager.getString("orderTempFile");
		BufferedWriter wr = new BufferedWriter(new FileWriter(tempPath, false));
		do {
			Page page = new Page(pageIndex, pageSize);
			userProducts = userProductService.find(page);
			if (userProducts != null) {
				makeLocalFile(userProducts, wr);
				if (userProducts.size() == pageSize) {
					pageIndex++;
				} else {
					break;
				}
			} else {
				break;
			}
		} while (con);
		wr.close();
		return text("success");
	}
	
	public Result pinggu() throws TasteException, IOException {
		String tempPath = propertiesManager.getString("orderTempFile");
		DataModel dataModel = RecommendFactory.buildDataModel(tempPath);
		List<PingguResult> list = new ArrayList<PingguResult>();
		int topNum = 2;
		for (int i = 2; i <= topNum; i++) {
			list.add(BookEvaluator.userEuclidean(dataModel, i));
			// // list.add(BookEvaluator.userLoglikelihood(dataModel, i));
			// list.add(BookEvaluator.userEuclideanNoPref(dataModel, i));
			// list.add(BookEvaluator.itemEuclidean(dataModel, i));
			// // list.add(BookEvaluator.itemLoglikelihood(dataModel, i));
			// list.add(BookEvaluator.itemEuclideanNoPref(dataModel, i));
		}
		pingguResultService.saveAll(list);
		return text("success");
	}
	
	public Result uploadFileToHDFS() throws IOException {
		String rootHDFS = propertiesManager.getString("hd.fs");
		String tuijian = propertiesManager.getString("tuijian");
		String tempPath = propertiesManager.getString("orderTempFile");
		HdfsDAO hdfs = new HdfsDAO(rootHDFS, HdfsDAO.config());
		File file = new File(tempPath);
		hdfs.copyFile(tempPath, tuijian + file.getName());
		return text("success");
	}
	
	private void uploadFileToHDFS(String file, String tuijian) {
		// TODO Auto-generated method stub
	}
	
	private String makeLocalFile(List<VUserProduct> userProducts, BufferedWriter wr) throws IOException {
		// TODO Auto-generated method stub
		for (VUserProduct vUserProduct : userProducts) {
			wr.write(String.format("%d,%d,%f", vUserProduct.getUserId(), vUserProduct.getItemId(), vUserProduct.getPreference()));
			wr.newLine();
		}
		return null;
	}
	
	public Result makeTuijian() throws TasteException, Exception {
		LineCallBack call = new LineCallBack() {
			
			@Override
			public Boolean call(String line) {
				try {
					String[] sp = line.split("\t");
					String userId = sp[0].trim();
					String itemsStr = sp[1].trim();
					itemsStr = itemsStr.substring(1, itemsStr.length() - 1);
					String[] items = itemsStr.split(",");
					System.out.println(String.format("%s:%s", userId, items.toString()));
				} catch (Exception e) {
					// TODO: handle exception
					
				}
				return false;
			}
		};
		ItemCFHadoop job = new ItemCFHadoop();
		String tuijianFile = propertiesManager.getString("tuijian");
		String hdfsRoot = propertiesManager.getString("hd.fs");
		job.tuijian(tuijianFile, hdfsRoot, RecommendFactory.clusterSimilarity(SIMILARITY.EUCLIDEAN).getClass().getName(), call);
		return text("success");
	}
}
