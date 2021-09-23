package com.spark.service.hbase.dao;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.spark.service.hbase.pojo.HBaseEntity;
import com.zuipin.util.DateUtils;

public abstract class BaseRepository<T extends HBaseEntity> implements Serializable {
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 5486011570255127400L;
	private Class<T>				entityClass;
	private String					defaultDay			= "2000-01-01 00:00:00";
	private final static Log		log					= LogFactory.getLog(BaseRepository.class);
	private static SerializeConfig	mapping				= new SerializeConfig();
	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	
	@PostConstruct
	public void makeTable() throws IOException, DeserializationException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		HBaseAdmin admin = new HBaseAdmin(getConfiguration());
		String tableName = getPOJO().getSimpleName();
		if (!admin.tableExists(tableName)) {
			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
			HColumnDescriptor columnDescriptor = new HColumnDescriptor(Bytes.toBytes(getFamily()));
			tableDescriptor.addFamily(columnDescriptor);
			admin.createTable(tableDescriptor);
		}
		admin.close();
	}
	
	public String getFamily() {
		try {
			String family = (String) getPOJO().getMethod("getFamilyName").invoke(null);
			return family;
		} catch (Exception e) {
			return "family";
		}
	}
	
	public T add(final T t) {
		return getHbaseTemplate().execute(getPOJO().getSimpleName(), new TableCallback<T>() {
			public T doInTable(HTableInterface table) throws Throwable {
				Put p = makePut(t);
				table.put(p);
				return t;
			}
		});
	}
	
	private void batchAdd(final Collection<T> ts) throws IOException, IllegalAccessException {
		HTable table = new HTable(getConfiguration(), getPOJO().getSimpleName());
		table.setWriteBufferSize(1024 * 1024 * 2);
		table.setAutoFlush(false, false);
		int i = 1;
		for (T t : ts) {
			Put put = makePut(t);
			table.put(put);
			if (i++ % 25 == 0) {
				table.flushCommits();
			}
		}
		table.flushCommits();
		table.close();
	}
	
	private List<Put> makePuts(Collection<T> ts) throws IllegalAccessException {
		// TODO Auto-generated method stub
		List<Put> puts = new ArrayList<Put>();
		for (T t : ts) {
			Put p = makePut(t);
			puts.add(p);
		}
		return puts;
	}
	
	/**
	 * @param t
	 * @return
	 * @throws IllegalAccessException
	 */
	public Put makePut(T t) throws IllegalAccessException {
		Field id = getId(t.getClass());
		Put p = new Put(getFieldBytes(t, id));
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!field.isAnnotationPresent(Transient.class)) {
				p.add(Bytes.toBytes(getFamily()), Bytes.toBytes(field.getName()), getFieldBytes(t, field));
			}
		}
		return p;
	}
	
	public void addAll(Collection<T> ts) {
		try {
			if (!ts.isEmpty()) {
				batchAdd(ts);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		}
	}
	
	public T modify(final T t) {
		return add(t);
	}
	
	public T get(final String rowName) {
		return getHbaseTemplate().get(getPOJO().getSimpleName(), rowName, new RowMapper<T>() {
			@Override
			public T mapRow(Result result, int rowNum) throws Exception {
				return makeT(result);
			}
		});
	}
	
	public List<T> findAll() throws IOException {
		
		return getHbaseTemplate().find(getPOJO().getSimpleName(), getFamily(), new RowMapper<T>() {
			@Override
			public T mapRow(Result result, int rowNum) throws Exception {
				return makeT(result);
			}
		});
	}
	
	public List<T> findKey(Scan scan) throws IOException {
		return getHbaseTemplate().find(getPOJO().getSimpleName(), scan, new RowMapper<T>() {
			@Override
			public T mapRow(Result result, int rowNum) throws Exception {
				return makeT(result);
			}
		});
	}
	
	public T findUniqueKey(Scan scan) throws IOException {
		List<T> lists = getHbaseTemplate().find(getPOJO().getSimpleName(), scan, new RowMapper<T>() {
			@Override
			public T mapRow(Result result, int rowNum) throws Exception {
				return makeT(result);
			}
		});
		if (!lists.isEmpty()) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	public T makeT(Result result) throws InstantiationException, IllegalAccessException {
		Field[] fields = getPOJO().getDeclaredFields();
		T t = getPOJO().newInstance();
		for (Field field : fields) {
			if (!field.isAnnotationPresent(Transient.class)) {
				if (!field.isAnnotationPresent(Id.class)) {
					setFieldBytes(t, field, result.getValue(Bytes.toBytes(getFamily()), Bytes.toBytes(field.getName())));
				} else {
					setFieldBytes(t, field, result.getRow());
				}
			}
		}
		return t;
	}
	
	public static <T extends HBaseEntity> T makeFromResult(Result result, Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {
		Field[] fields = clazz.getDeclaredFields();
		T t = clazz.newInstance();
		for (Field field : fields) {
			field.setAccessible(true);
			// byte[] visiday = result.getValue(TotalVisitor.family_visitor, TotalVisitor.col_visiday);
			if (!field.isAnnotationPresent(Transient.class)) {
				if (!field.isAnnotationPresent(Id.class)) {
					setFieldBytes(t, field, result.getValue(Bytes.toBytes((String) clazz.getMethod("getFamilyName").invoke(t)), Bytes.toBytes(field.getName())));
				} else {
					setFieldBytes(t, field, result.getRow());
				}
			}
		}
		return t;
	}
	
	private byte[] getFieldBytes(final T t, Field field) throws IllegalAccessException {
		field.setAccessible(true);
		Object o = field.get(t);
		if (field.getType().isAssignableFrom(Double.class)) {
			Double d = o == null ? 0D : (Double) o;
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(Float.class)) {
			Float d = o == null ? 0f : (Float) o;
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(String.class)) {
			String d = o == null ? "" : String.valueOf(o).trim();
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(Integer.class)) {
			Integer d = o == null ? 0 : (Integer) o;
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(Boolean.class)) {
			Boolean d = o == null ? false : (Boolean) o;
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(Long.class)) {
			Long d = o == null ? 0L : (Long) o;
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(Short.class)) {
			Short d = o == null ? 0 : (Short) o;
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(Date.class)) {
			String d = o == null ? defaultDay : DateUtils.format((Date) o, "yyyy-MM-dd HH:mm:ss");
			return Bytes.toBytes(d);
		} else if (field.getType().isAssignableFrom(List.class)) {
			String d = null;
			if (o == null) {
				d = new JSONArray().toString();
			} else {
				d = JSON.toJSONString(o, mapping);
			}
			return Bytes.toBytes(d);
		}
		return null;
	}
	
	private static <T extends HBaseEntity> T setFieldBytes(T t, Field field, byte[] rowKey) throws IllegalAccessException {
		try {
			if (rowKey == null) {
				return t;
			}
			field.setAccessible(true);
			Object value = null;
			if (field.getType().isAssignableFrom(Double.class)) {
				value = new Double(Bytes.toDouble(rowKey));
			} else if (field.getType().isAssignableFrom(Float.class)) {
				value = new Float(Bytes.toFloat(rowKey));
			} else if (field.getType().isAssignableFrom(String.class)) {
				value = Bytes.toString(rowKey);
			} else if (field.getType().isAssignableFrom(Integer.class)) {
				value = new Integer(Bytes.toInt(rowKey));
			} else if (field.getType().isAssignableFrom(Boolean.class)) {
				value = new Boolean(Bytes.toBoolean(rowKey));
			} else if (field.getType().isAssignableFrom(Long.class)) {
				value = new Long(Bytes.toLong(rowKey));
			} else if (field.getType().isAssignableFrom(Short.class)) {
				value = new Short(Bytes.toShort(rowKey));
			} else if (field.getType().isAssignableFrom(Date.class)) {
				value = DateUtils.getDate(Bytes.toString(rowKey), "yyyy-MM-dd HH:mm:ss");
			} else if (field.getType().isAssignableFrom(List.class)) {
				String back = Bytes.toString(rowKey);
				JSONArray result = JSON.parseArray(back);
				Class clazz = (Class) ((ParameterizedType) (field.getGenericType())).getActualTypeArguments()[0];
				List<Object> lists = new ArrayList();
				for (int i = 0; i < result.size(); i++) {
					Object v = JSON.parseObject(result.getString(i), clazz);
					lists.add(v);
				}
				value = lists;
			}
			field.set(t, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return t;
	}
	
	protected abstract HbaseTemplate getHbaseTemplate();
	
	@SuppressWarnings("unchecked")
	private Class<T> getPOJO() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}
	
	private Field getId(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}
		return null;
	}
	
	public abstract Configuration getConfiguration();
	
}
