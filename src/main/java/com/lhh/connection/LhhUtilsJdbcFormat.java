package com.lhh.connection;

/**
 * jdbc数据转化
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class LhhUtilsJdbcFormat {

	/**
	 * 数据类型转化JAVA
	 * @param sqlType：类型名称
	 * @return
	 */
	public static String toSqlToJava(String sqlType) {
		if( sqlType == null || sqlType.trim().length() == 0 ) return sqlType;
		sqlType = sqlType.toLowerCase();
		switch(sqlType){
			case "nvarchar":return "String";
			case "char":return "String";
			case "varchar":return "String";
			case "text":return "String";
			case "nchar":return "String";
			case "blob":return "byte[]";
			case "integer":return "Long";
			case "tinyint":return "Integer";
			case "smallint":return "Integer";
			case "mediumint":return "Integer";
			case "bit":return "Boolean";
			case "bigint":return "java.math.BigInteger";
			case "float":return "Fload";
			case "double":return "Double";
			case "decimal":return "java.math.BigDecimal";
			case "boolean":return "Boolean";
			case "id":return "Long";
			case "date":return "java.util.Date";
			case "datetime":return "java.util.Date";
			case "year":return "java.util.Date";
			case "time":return "java.sql.Time";
			case "timestamp":return "java.sql.Timestamp";
			case "numeric":return "java.math.BigDecimal";
			case "real":return "java.math.BigDecimal";
			case "money":return "Double";
			case "smallmoney":return "Double";
			case "image":return "byte[]";
			default:
				System.out.println("-----------------》转化失败：未发现的类型"+sqlType);
				break;
		}
		return sqlType;
	}
}
