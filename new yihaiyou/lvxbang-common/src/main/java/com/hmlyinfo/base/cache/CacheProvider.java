package com.hmlyinfo.base.cache;

import java.util.Map;

public interface CacheProvider {
	/**
	 * 向服务端增加一个值，只有当指定的key不存在时，才会增加这个值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	boolean add(String key, Object value);

	/**
	 * 向服务端增加一个值，只有当指定的key不存在时，才会增加这个值， 过期时间是一个以当前时间为准的偏移量，如果设置为10，表示经过 10秒后客户端无法获取此数据
	 *
	 * @param key
	 * @param value
	 * @param expiry
	 * @return
	 */
	boolean add(String key, Object value, long expiry);

	/**
	 * 对指定的数据内容进行减去操作，如对key为aa，值为11的数据指定decr("aa",2) 则结果为9。
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	long derc(String key, long value);

	/**
	 * 删除一个指定的值
	 *
	 * @param key
	 * @return
	 */
	boolean delete(String key);

	/**
	 * 获取一个指定的值
	 *
	 * @param key
	 * @return
	 */
	<T> T get(String key);

	/**
	 * 查找多个key，如果结果不存在则返回空的Map
	 *
	 * @param keys
	 * @return
	 */
	Map<String, Object> getMulti(String[] keys);

	/**
	 * 对指定的数据内容进行增加操作，如对key为aa，值为11的数据指定incr("aa",2) 则结果为13。
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	long incr(String key, long value);

	/**
	 * 向服务端替换一个值，只有当指定的key存在时，才会替换这个值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	boolean replace(String key, Object value);

	/**
	 * 向服务端替换一个值，只有当指定的key存在时，才会替换这个值， 过期时间是一个以当前时间为准的偏移量，如果设置为10，表示经过 10秒后客户端无法获取此数据。
	 *
	 * @param key
	 * @param value
	 * @param expiry
	 * @return
	 */
	boolean replace(String key, Object value, long expiry);

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, Object value);

	/**
	 * 保存一个指定的值，不管指定的key在服务端是否存在都会被保存， 过期时间是一个以当前时间为准的偏移量，如果设置为10，表示经过 10秒后客户端无法获取此数据。
	 *
	 * @param key
	 * @param value
	 * @param expiry
	 * @return
	 */
	boolean set(String key, Object value, long expiry);
}
