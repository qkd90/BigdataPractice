package org.apache.ibatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TimestampTypeHandler extends BaseTypeHandler
{

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException 
	{
		Timestamp sqlTimestamp = rs.getTimestamp(columnName);
	    if (sqlTimestamp != null) 
	    {
	      return sqlTimestamp.getTime();
	    }
	    return null;
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
	    if (sqlTimestamp != null) 
	    {
	      return sqlTimestamp.getTime();
	    }
	    return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException 
	{
		ps.setTimestamp(i, new Timestamp((Long)parameter));
	}
	
}
