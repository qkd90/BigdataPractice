package com.hmlyinfo.app.soutu.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TimestampTypeHandler implements TypeHandler
{

	@Override
	public Object getResult(ResultSet rs, String arg1) throws SQLException {
		
		Long ts = rs.getLong(arg1);
		return ts;
	}

	@Override
	public Object getResult(CallableStatement rs, int arg1)
			throws SQLException {
		Long ts = rs.getLong(arg1);
		return ts;
	}

	@Override
	public void setParameter(PreparedStatement st, int arg1, Object arg2,
			JdbcType arg3) throws SQLException {
		st.setLong(arg1, (Long)arg2);
		
	}

}
