package com.hmlyinfo.base.cache;

import com.hmlyinfo.app.soutu.base.properties.Config;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class XMemcachedImpl implements CacheProvider {

	private MemcachedClient memcachedClient;
	private static XMemcachedImpl instance;

	private XMemcachedImpl() {
	}

	public static CacheProvider getInstance() {
		if (instance == null) {
			String curl = Config.get("CACHE_SERVER");
			String cuser = Config.get("CACHE_SERVER_USERNAME");
			String cpwd = Config.get("CACHE_SERVER_PASSWORD");

			MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(curl));
			if (StringUtils.isNotBlank(cuser) && StringUtils.isNotBlank(cpwd)) {
				builder.addAuthInfo(AddrUtil.getOneAddress(curl), AuthInfo.typical(cuser, cpwd));
			}

			instance = new XMemcachedImpl();
			try {
				instance.setMemcachedClient(builder.build());
			} catch (Exception e) {
				e.printStackTrace();
				instance = null;
			}

		}
		return instance;
	}


	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public boolean add(String key, Object value) {

		try {
			return memcachedClient.add(key, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean add(String key, Object value, long expiry) {
		try {
			return memcachedClient.add(key, (int) expiry, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public long derc(String key, long value) {
		try {
			return memcachedClient.decr(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean delete(String key) {
		try {
			return memcachedClient.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public <T> T get(String key) {
		try {
			return memcachedClient.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> getMulti(String[] keys) {
		return null;
	}

	@Override
	public long incr(String key, long value) {
		try {
			return memcachedClient.incr(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public boolean replace(String key, Object value) {
		try {
			return memcachedClient.replace(key, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean replace(String key, Object value, long expiry) {
		try {
			return memcachedClient.replace(key, (int) expiry, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean set(String key, Object value) {
		try {
			return memcachedClient.set(key, 0, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean set(String key, Object value, long expiry) {
		try {
			return memcachedClient.set(key, (int) expiry, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
