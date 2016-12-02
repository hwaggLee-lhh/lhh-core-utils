/*     */ package com.lhh.format.cls;
/*     */ 
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class CreateBeanUtils
/*     */ {
/*  18 */   private String url = "jdbc:mysql://127.0.0.1:3306/dbname";
/*  19 */   private String user = "root";
/*  20 */   private String pwd = "123456";
/*     */ 
/*  22 */   private String sql = "";
/*  23 */   private String strsql = "select * from {tablename} limit 0,1";
/*  24 */   private String tablename = "";
/*  25 */   private String modelpath = "com.lhh.other";
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  29 */     CreateBeanUtils c = new CreateBeanUtils();
/*     */ 
/*  31 */     Set<String> setTableName = c.getTableNameByCon(c.getConnection());
/*  32 */     System.out.println(setTableName.size() + ":" + setTableName.size());
/*  33 */     for (String tableName : setTableName) {
/*  34 */       c.setTableName(tableName);
/*     */ 
/*  36 */       c.createBeanMethod2();
/*     */ 
/*  40 */       System.out.println("完成" + tableName);
/*     */     }
/*  42 */     System.out.println("生成成功！");
/*     */   }
/*     */ 
/*     */   public Set<String> getTableNameByCon(Connection con)
/*     */   {
/*  47 */     Set<String> set = new HashSet<String>();
/*     */     try {
/*  49 */       DatabaseMetaData meta = con.getMetaData();
/*  50 */       ResultSet rs = meta.getTables(null, null, null, 
/*  51 */         new String[] { "TABLE" });
/*  52 */       while (rs.next()) {
/*  53 */         set.add(rs.getString(3));
/*     */       }
/*  55 */       con.close();
/*     */     } catch (Exception e) {
/*     */       try {
/*  58 */         con.close();
/*     */       } catch (SQLException e1) {
/*  60 */         e1.printStackTrace();
/*     */       }
/*  62 */       e.printStackTrace();
/*     */     }
/*  64 */     return set;
/*     */   }
/*     */ 
/*     */   public void setTableName(String name)
/*     */   {
/*  77 */     this.tablename = name;
/*  78 */     this.sql = this.strsql.replace("{tablename}", this.tablename);
/*     */   }
/*     */ 
/*     */   public Connection getConnection()
/*     */   {
/*  84 */     Connection conn = null;
/*     */     try {
/*  86 */       Class.forName("com.mysql.jdbc.Driver");
/*  87 */       conn = DriverManager.getConnection(this.url, this.user, this.pwd);
/*     */     } catch (ClassNotFoundException e) {
/*  89 */       e.printStackTrace();
/*     */     } catch (SQLException e) {
/*  91 */       e.printStackTrace();
/*     */     }
/*  93 */     return conn;
/*     */   }
/*     */ 
/*     */   public void createManager() {
/*  97 */     String content = parseManager();
/*     */     try {
/*  99 */       FileWriter fw = new FileWriter(managertitle(initcapTitle(this.tablename)) + ".java");
/* 100 */       PrintWriter pw = new PrintWriter(fw);
/* 101 */       pw.println(content);
/* 102 */       pw.flush();
/* 103 */       pw.close();
/*     */     } catch (IOException e) {
/* 105 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String resourcetitle(String str) {
/* 110 */     char[] chars = new char[1];
/* 111 */     chars[0] = str.charAt(0);
/* 112 */     String temp = new String(chars);
/* 113 */     return str.replaceFirst(temp, temp.toLowerCase());
/*     */   }
/*     */ 
/*     */   public String managertitle(String str) {
/* 117 */     return str + "Manager";
/*     */   }
/*     */ 
/*     */   public void createService() {
/* 121 */     String content = parseService();
/*     */     try {
/* 123 */       FileWriter fw = new FileWriter(initcapTitle(this.tablename) + "Service.java");
/* 124 */       PrintWriter pw = new PrintWriter(fw);
/* 125 */       pw.println(content);
/* 126 */       pw.flush();
/* 127 */       pw.close();
/*     */     } catch (IOException e) {
/* 129 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void createServiceImpl() {
/* 134 */     String content = parseServiceImpl();
/*     */     try {
/* 136 */       FileWriter fw = new FileWriter(initcapTitle(this.tablename) + "ServiceImpl.java");
/* 137 */       PrintWriter pw = new PrintWriter(fw);
/* 138 */       pw.println(content);
/* 139 */       pw.flush();
/* 140 */       pw.close();
/*     */     } catch (IOException e) {
/* 142 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void createBeanMethod() {
/* 147 */     Connection conn = getConnection();
/* 148 */     PreparedStatement pstmt = null;
/* 149 */     ResultSetMetaData rsmd = null;
/*     */     try {
/* 151 */       pstmt = conn.prepareStatement(this.sql);
/* 152 */       rsmd = pstmt.getMetaData();
/* 153 */       int size = rsmd.getColumnCount();
/* 154 */       String[] colnames = new String[size];
/* 155 */       String[] colTypes = new String[size];
/* 156 */       int[] colSizes = new int[size];
/* 157 */       for (int i = 0; i < rsmd.getColumnCount(); i++) {
/* 158 */         colnames[i] = rsmd.getColumnName(i + 1);
/* 159 */         colTypes[i] = rsmd.getColumnTypeName(i + 1);
/* 160 */         colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
/*     */       }
/* 162 */       String content = parse(colnames, colTypes, colSizes);
/*     */       try {
/* 164 */         FileWriter fw = new FileWriter(initcapTitle(this.tablename) + "Model.java");
/* 165 */         PrintWriter pw = new PrintWriter(fw);
/* 166 */         pw.println(content);
/* 167 */         pw.flush();
/* 168 */         pw.close();
/*     */       } catch (IOException e) {
/* 170 */         e.printStackTrace();
/*     */       }
/*     */     } catch (SQLException e) {
/* 173 */       e.printStackTrace();
/*     */       try
/*     */       {
/* 176 */         pstmt.close();
/* 177 */         conn.close();
/*     */       } catch (SQLException e1) {
/* 179 */         e1.printStackTrace();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 176 */         pstmt.close();
/* 177 */         conn.close();
/*     */       } catch (SQLException e) {
/* 179 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void createBeanMethod2() {
/* 185 */     Connection conn = getConnection();
/* 186 */     PreparedStatement pstmt = null;
/* 187 */     ResultSetMetaData rsmd = null;
/*     */     try {
/* 189 */       pstmt = conn.prepareStatement(this.sql);
/* 190 */       rsmd = pstmt.getMetaData();
/* 191 */       int size = rsmd.getColumnCount();
/* 192 */       String[] colnames = new String[size];
/* 193 */       String[] colTypes = new String[size];
/* 194 */       int[] colSizes = new int[size];
/* 195 */       for (int i = 0; i < rsmd.getColumnCount(); i++) {
/* 196 */         colnames[i] = rsmd.getColumnName(i + 1);
/* 197 */         colTypes[i] = rsmd.getColumnTypeName(i + 1);
/* 198 */         colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
/*     */       }
/* 200 */       String content = parse2(colnames, colTypes, colSizes);
/*     */       try {
/* 202 */         FileWriter fw = new FileWriter("T" + initcapTitle(this.tablename) + ".java");
/* 203 */         PrintWriter pw = new PrintWriter(fw);
/* 204 */         pw.println(content);
/* 205 */         pw.flush();
/* 206 */         pw.close();
/*     */       } catch (IOException e) {
/* 208 */         e.printStackTrace();
/*     */       }
/*     */     } catch (SQLException e) {
/* 211 */       e.printStackTrace();
/*     */       try
/*     */       {
/* 214 */         pstmt.close();
/* 215 */         conn.close();
/*     */       } catch (SQLException e1) {
/* 217 */         e1.printStackTrace();
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 214 */         pstmt.close();
/* 215 */         conn.close();
/*     */       } catch (SQLException e) {
/* 217 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String parseManager() {
/* 223 */     StringBuffer sb = new StringBuffer();
/* 224 */     sb.append("package " + this.modelpath + ";\r\n");
/* 225 */     sb.append("\r\n");
/* 226 */     sb.append("import org.springframework.stereotype.Repository;\r\n");
/* 227 */     sb.append("import com.qinghuainvest.utils.BaseDBManager;\r\n");
/* 228 */     sb.append("import " + this.modelpath + ".model.");
/* 229 */     sb.append(initcapTitle(this.tablename) + "Model");
/* 230 */     sb.append(";\r\n");
/* 231 */     sb.append("\r\n");
/* 232 */     sb.append("@Repository(\"" + resourcetitle(initcapTitle(this.tablename)) + "Manager\")\r\n");
/* 233 */     sb.append("public class " + managertitle(initcapTitle(this.tablename)) + " extends BaseDBManager<" + initcapTitle(this.tablename) + "Model>{\r\n");
/* 234 */     sb.append("\r\n");
/* 235 */     sb.append("\t@Override\r\n");
/* 236 */     sb.append("\tpublic Class<" + initcapTitle(this.tablename) + "Model> getModelClass() {\r\n");
/* 237 */     sb.append("\t\treturn " + initcapTitle(this.tablename) + "Model.class;\r\n");
/* 238 */     sb.append("\t}\r\n");
/* 239 */     sb.append("\r\n");
/* 240 */     sb.append("\r\n");
/* 241 */     sb.append("}\r\n");
/* 242 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String parseService() {
/* 246 */     StringBuffer sb = new StringBuffer();
/* 247 */     sb.append("package " + this.modelpath + ";\r\n");
/* 248 */     sb.append("\r\n");
/*     */ 
/* 251 */     sb.append(";\r\n");
/* 252 */     sb.append("\r\n");
/* 253 */     sb.append("public interface " + initcapTitle(this.tablename) + "Service {\r\n");
/* 254 */     sb.append("\r\n");
/* 255 */     sb.append("\r\n");
/* 256 */     sb.append("\r\n");
/* 257 */     sb.append("}\r\n");
/* 258 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String parseServiceImpl() {
/* 262 */     StringBuffer sb = new StringBuffer();
/* 263 */     sb.append("package " + this.modelpath + ";\r\n");
/* 264 */     sb.append("\r\n");
/* 265 */     sb.append("import javax.annotation.Resource;\r\n");
/* 266 */     sb.append("import org.springframework.stereotype.Service;\r\n");
/* 267 */     sb.append("import " + this.modelpath + ".service.");
/* 268 */     sb.append(initcapTitle(this.tablename));
/* 269 */     sb.append("Service;\r\n");
/* 270 */     sb.append("import " + this.modelpath + ".manager.");
/* 271 */     sb.append(initcapTitle(this.tablename));
/* 272 */     sb.append("Manager;\r\n");
/*     */ 
/* 276 */     sb.append("\r\n");
/* 277 */     sb.append("@Service(\"" + resourcetitle(initcapTitle(this.tablename)) + "Service\")\r\n");
/* 278 */     sb.append("public class " + initcapTitle(this.tablename) + "ServiceImpl implements " + initcapTitle(this.tablename) + "Service{\r\n");
/* 279 */     sb.append("\r\n");
/* 280 */     sb.append("\t@Resource\r\n");
/* 281 */     sb.append("\tprivate " + initcapTitle(this.tablename) + "Manager " + resourcetitle(initcapTitle(this.tablename)) + "Manager;\r\n");
/* 282 */     sb.append("\r\n");
/* 283 */     sb.append("\r\n");
/* 284 */     sb.append("}\r\n");
/* 285 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String parse(String[] colNames, String[] colTypes, int[] colSizes)
/*     */   {
/* 292 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 294 */     sb.append("package " + this.modelpath + ".model;\r\n");
/* 295 */     sb.append("\r\n");
/* 296 */     sb.append("\timport java.math.BigDecimal;\r\n");
/* 297 */     sb.append("\timport java.util.Date;\r\n");
/* 298 */     sb.append("\timport java.sql.Timestamp;\r\n");
/* 299 */     sb.append("\timport javax.persistence.Column;\r\n");
/* 300 */     sb.append("\timport javax.persistence.Entity;\r\n");
/* 301 */     sb.append("\timport javax.persistence.GeneratedValue;\r\n");
/* 302 */     sb.append("\timport javax.persistence.Id;\r\n");
/* 303 */     sb.append("\timport javax.persistence.Table;\r\n");
/* 304 */     sb.append("\timport org.hibernate.annotations.GenericGenerator;\r\n");
/* 305 */     sb.append("\timport com.qinghuainvest.cmndd.util.hibernate.BaseModel;\r\n");
/* 306 */     sb.append("\r\n");
/* 307 */     sb.append("@Entity\r\n");
/* 308 */     sb.append("@Table(name=\"" + this.tablename + "\")\r\n");
/*     */ 
/* 310 */     sb.append("public class " + initcapTitle(this.tablename) + "Model {\r\n");
/* 311 */     sb.append("\r\n");
/* 312 */     sb.append("\tprivate static final long serialVersionUID = 1L;");
/* 313 */     sb.append("\r\n");
/* 314 */     sb.append("\t@Id\r\n");
/* 315 */     sb.append("\t@GeneratedValue(generator=\"paymentableGenerator\")\r\n");
/* 316 */     sb.append("\t@GenericGenerator(name=\"paymentableGenerator\",strategy=\"uuid\")\r\n");
/* 317 */     processAllAttrs(sb, colNames, colTypes, colSizes);
/* 318 */     processAllMethod(sb, colNames, colTypes, colSizes);
/* 319 */     sb.append("}\r\n");
/* 320 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private String parse2(String[] colNames, String[] colTypes, int[] colSizes)
/*     */   {
/* 328 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 330 */     sb.append("package " + this.modelpath + ".tmodel;\r\n");
/* 331 */     sb.append("\r\n");
/* 332 */     sb.append("\timport java.math.BigDecimal;\r\n");
/* 333 */     sb.append("\timport java.util.Date;\r\n");
/* 334 */     sb.append("\timport java.sql.Timestamp;\r\n");
/*     */ 
/* 336 */     sb.append("\r\n");
/*     */ 
/* 338 */     sb.append("public class T" + initcapTitle(this.tablename) + " {\r\n");
/* 339 */     sb.append("\r\n");
/* 340 */     for (int i = 0; i < colNames.length; i++) {
/* 341 */       sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + 
/* 343 */         colNames[i] + ";\r\n");
/*     */     }
/*     */ 
/* 346 */     for (int i = 0; i < colNames.length; i++) {
/* 347 */       sb.append("\tpublic void set" + initcap(colNames[i]) + "(" + 
/* 348 */         sqlType2JavaType(colTypes[i]) + " " + colNames[i] + 
/* 349 */         "){\r\n");
/* 350 */       sb.append("\t\tthis." + colNames[i] + "=" + colNames[i] + ";\r\n");
/* 351 */       sb.append("\t}\r\n");
/*     */ 
/* 353 */       sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + 
/* 354 */         initcap(colNames[i]) + "(){\r\n");
/* 355 */       sb.append("\t\treturn " + colNames[i] + ";\r\n");
/* 356 */       sb.append("\t}\r\n");
/*     */     }
/* 358 */     sb.append("}\r\n");
/* 359 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private void processAllMethod(StringBuffer sb, String[] colnames, String[] colTypes, int[] colSizes)
/*     */   {
/* 371 */     for (int i = 0; i < colnames.length; i++) {
/* 372 */       sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + 
/* 373 */         sqlType2JavaType(colTypes[i]) + " " + colnames[i] + 
/* 374 */         "){\r\n");
/* 375 */       sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
/* 376 */       sb.append("\t}\r\n");
/*     */ 
/* 378 */       sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + 
/* 379 */         initcap(colnames[i]) + "(){\r\n");
/* 380 */       sb.append("\t\treturn " + colnames[i] + ";\r\n");
/* 381 */       sb.append("\t}\r\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processAllAttrs(StringBuffer sb, String[] colnames, String[] colTypes, int[] colSizes)
/*     */   {
/* 395 */     for (int i = 0; i < colnames.length; i++) {
/* 396 */       sb.append("\t@Column(name=\"" + colnames[i] + "\")\r\n");
/* 397 */       sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + 
/* 399 */         colnames[i] + ";\r\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   private String initcap(String str)
/*     */   {
/* 409 */     char[] ch = str.toCharArray();
/* 410 */     if ((ch[0] >= 'a') && (ch[0] <= 'z')) {
/* 411 */       ch[0] = ((char)(ch[0] - ' '));
/*     */     }
/* 413 */     return new String(ch);
/*     */   }
/*     */ 
/*     */   private String initcapTitle(String str) {
/* 417 */     StringBuffer sbf = new StringBuffer();
/* 418 */     String[] arr = str.split("_");
/* 419 */     for (String string : arr) {
/* 420 */       char[] ch = string.toCharArray();
/* 421 */       if ((ch[0] >= 'a') && (ch[0] <= 'z')) {
/* 422 */         ch[0] = ((char)(ch[0] - ' '));
/*     */       }
/* 424 */       sbf.append(new String(ch));
/*     */     }
/* 426 */     return sbf.toString();
/*     */   }
/*     */ 
/*     */   private String sqlType2JavaType(String sqlType) {
/* 430 */     if (sqlType.equalsIgnoreCase("bit"))
/* 431 */       return "bool";
/* 432 */     if (sqlType.equalsIgnoreCase("tinyint"))
/* 433 */       return "byte";
/* 434 */     if (sqlType.equalsIgnoreCase("smallint"))
/* 435 */       return "short";
/* 436 */     if ((sqlType.equalsIgnoreCase("int")) || (sqlType.equalsIgnoreCase("INTEGER")))
/* 437 */       return "Integer";
/* 438 */     if (sqlType.equalsIgnoreCase("bigint"))
/* 439 */       return "Long";
/* 440 */     if (sqlType.equalsIgnoreCase("float"))
/*     */     {
/* 442 */       return "BigDecimal";
/* 443 */     }if ((sqlType.equalsIgnoreCase("decimal")) || 
/* 444 */       (sqlType.equalsIgnoreCase("numeric")) || 
/* 445 */       (sqlType.equalsIgnoreCase("real")))
/* 446 */       return "BigDecimal";
/* 447 */     if ((sqlType.equalsIgnoreCase("money")) || 
/* 448 */       (sqlType.equalsIgnoreCase("smallmoney")))
/*     */     {
/* 450 */       return "BigDecimal";
/* 451 */     }if ((sqlType.equalsIgnoreCase("varchar")) || 
/* 452 */       (sqlType.equalsIgnoreCase("char")) || 
/* 453 */       (sqlType.equalsIgnoreCase("nvarchar")) || 
/* 454 */       (sqlType.equalsIgnoreCase("nchar")))
/* 455 */       return "String";
/* 456 */     if (sqlType.equalsIgnoreCase("datetime")) {
/* 457 */       return "Date";
/*     */     }
/* 459 */     if (sqlType.equalsIgnoreCase("image"))
/*     */     {
/* 461 */       return "byte[]";
/* 462 */     }if (sqlType.equalsIgnoreCase("text"))
/*     */     {
/* 464 */       return "String";
/* 465 */     }if (sqlType.equalsIgnoreCase("date"))
/* 466 */       return "Date";
/* 467 */     if (sqlType.equalsIgnoreCase("timestamp"))
/* 468 */       return "Date";
/* 469 */     if ((sqlType.equalsIgnoreCase("LONGBLOB")) || (sqlType.equalsIgnoreCase("BLOB"))) {
/* 470 */       return "byte[]";
/*     */     }
/* 472 */     System.out.println(sqlType);
/* 473 */     return null;
/*     */   }
/*     */ }

/* Location:           D:\Program Files\maven\Respositories\maven\com\qinghuainvest\n3b_db\0.0.1-SNAPSHOT\n3b_db-0.0.1-SNAPSHOT\
 * Qualified Name:     com.qinghuainvest.n3bdb.utils.CreateBeanUtils
 * JD-Core Version:    0.6.2
 */