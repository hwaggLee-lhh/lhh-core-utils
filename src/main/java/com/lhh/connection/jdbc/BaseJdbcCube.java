package com.lhh.connection.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mysql.jdbc.CommunicationsException;

/**
 * 基本的查询信息
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public abstract class BaseJdbcCube extends BaseJdbcConnect{

	public List<String> findList(String sql,String key){
		logger.info("find sql="+sql);
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString(key));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeResultSett(rs);
			closeStatement(stmt);
		}
		return list;
	}
	
	public void insert(String sql ){
		logger.info("insert sql="+sql);
		
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeStatement(stmt);
		}
	}
	
	public void update(String sql ){
		logger.info("update sql="+sql);
		
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeStatement(stmt);
		}
	}


	public Map<String, Object> findSimpleResult(String sql, List<Object> params)
			throws SQLException {

		logger.info("find simple sql="+sql);
		
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();// 返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		closeResultSett(resultSet);
		closeStatement(pstmt);
		return map;
	}
	
	public List<Map<String, Object>> findModeResult(String sql,
			List<Object> params) throws SQLException {
		logger.info("find mode sql="+sql);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}

		closeResultSett(resultSet);
		closeStatement(pstmt);
		return list;
	}
	

	/**<执行>1. 查询的SQL语句
	 * @param sql
	 * @return  返回ResultSet结果集。	 */
	public Object executeQureyOneObj(String sql) {
		ResultSet rs = null;
		Statement st = null;
		if (!SQLValidater.isNullSQL(sql))
			return rs;
		
		try {
			st = connection.createStatement();
			boolean flag = st.execute(sql);
			
			if (flag) {
				rs = st.getResultSet();
				rs.next();
			}
			return rs.getObject(1);
		} catch (CommunicationsException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}finally{
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {}
			}
		}
		return null;
	}
	/**<执行>1. 查询的SQL语句
	 * @param sql
	 * @return  返回结果集。	 */
	public List<Map<String, Object>> executeQurey(String sql) {
		ResultSet rs = null;
		Statement st = null;
		if (!SQLValidater.isNullSQL(sql))
			return null;
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		try {
			st = connection.createStatement();
			boolean flag = st.execute(sql);
			if (flag) {
				rs = st.getResultSet();
				ResultSetMetaData rsmd = rs.getMetaData();
				while(rs.next()) {
					Map<String, Object> result = new HashMap<String, Object>();
					for (int i = 1; i < rsmd.getColumnCount()+1; i++) {
						String label = rsmd.getColumnLabel(i);
						int type = rsmd.getColumnType(i);
						switch (type) {
						case Types.FLOAT:
							result.put(label, rs.getFloat(i));
							break;
						case Types.DATE:
							result.put(label, String.valueOf(rs.getDate(i)));
							break;
						case Types.NUMERIC:
							result.put(label, rs.getBigDecimal(i));
							break;
						case Types.DECIMAL:
							result.put(label, rs.getBigDecimal(i));
							break;
						case Types.CHAR:
							String str = rs.getString(i);
							if(StringUtils.isNotBlank(str)) {
								result.put(label, rs.getString(i).toCharArray());
							}
							break;
						default:
							result.put(label, rs.getString(i));
							break;
						}
					}
					resultList.add(result);
				}
				return resultList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {}
			}
		}
		return null;
	}
	/** <执行>3. 插入、更新的SQL语句
	 * @param sql
	 * @return boolean */
	public boolean executeSQL(String sql){
		Statement st = null;
		Connection conn = connection;
		try {
			conn.setAutoCommit(false);
			
			st = conn.createStatement();
			int flag = st.executeUpdate(sql);
			conn.commit();
			return flag>0;
		} catch (CommunicationsException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return false;
	}
}
