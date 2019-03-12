package template.model;


import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author xingkai.fan
 */
public class Generator {

    public static void generate(DbConfig dbconfig, String templateDir) {
        List<TableConfig> tconfigs = dbconfig.getTbconfigs();
        Connection connection = getConnection(dbconfig.getUrl(), dbconfig.getDriveClass(), dbconfig.getUser(), dbconfig.getPassword());
        DatabaseMetaData databaseMetaData = null;
        try {
            databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PackageAttribute packageAttribute = new PackageAttribute();
        for (TableConfig tc : tconfigs) {
            List<TableAttribute> tables = getTable(databaseMetaData, tc.getTableName());
            for (TableAttribute table : tables) {

                //增加实体类的包名属性，让所有的模版都可以用到实体类的包名
                table.setBeanPackage(tc.getModelConfig().getPackageName());
                //保存接口包名
                packageAttribute.setServicePackageName(tc.getServiceConfig().getPackageName());
                packageAttribute.setDaoMapperPackageName(tc.getDaoConfig().getPackageName());
                packageAttribute.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));

                Set<String> imports = new HashSet<>();
                table.setImportClasses(imports);
                handleColoumns(table, databaseMetaData, table.getTableName(), imports);
                List<PairConfig> pairs = tc.getPairConfigs();

                tc.getDaoConfig().setFileName(table.getBeanName() + "Mapper.java");
                tc.getXmlConfig().setFileName(table.getBeanName() + "Mapper.xml");
                tc.getModelConfig().setFileName(table.getBeanName() + ".java");
                tc.getServiceConfig().setFileName("I" + table.getBeanName() + "Service.java");
                tc.getServiceImplConfig().setFileName(table.getBeanName() + "Service.java");


                tc.getDaoConfig().setTemplateDir((tc.getDaoConfig().getTemplateDir() == null) ? templateDir : tc.getDaoConfig().getTemplateDir());
                tc.getXmlConfig().setTemplateDir((tc.getXmlConfig().getTemplateDir() == null) ? templateDir : tc.getXmlConfig().getTemplateDir());
                tc.getModelConfig().setTemplateDir((tc.getModelConfig().getTemplateDir() == null) ? templateDir : tc.getModelConfig().getTemplateDir());
                tc.getServiceConfig().setTemplateDir((tc.getServiceConfig().getTemplateDir() == null) ? templateDir : tc.getServiceConfig().getTemplateDir());
                tc.getServiceImplConfig().setTemplateDir((tc.getServiceImplConfig().getTemplateDir() == null) ? templateDir : tc.getServiceImplConfig().getTemplateDir());


                tc.getDaoConfig().setTemplate((tc.getDaoConfig().getTemplate() == null) ? "DaoMapper.ftl" : tc.getDaoConfig().getTemplate());
                tc.getXmlConfig().setTemplate((tc.getXmlConfig().getTemplate() == null) ? "DaoXml.ftl" : tc.getXmlConfig().getTemplate());
                tc.getModelConfig().setTemplate((tc.getModelConfig().getTemplate() == null) ? "Model.ftl" : tc.getModelConfig().getTemplate());
                tc.getServiceConfig().setTemplate((tc.getServiceConfig().getTemplate() == null) ? "Service.ftl" : tc.getServiceConfig().getTemplate());
                tc.getServiceImplConfig().setTemplate((tc.getServiceImplConfig().getTemplate() == null) ? "ServiceImpl.ftl" : tc.getServiceImplConfig().getTemplate());

                pairs.add(tc.getDaoConfig());
                pairs.add(tc.getXmlConfig());
                pairs.add(tc.getModelConfig());
                pairs.add(tc.getServiceConfig());
                pairs.add(tc.getServiceImplConfig());

                for (PairConfig pair : pairs) {
                    Map<String, Object> props = JSON.parseObject(JSON.toJSONString(tc), HashMap.class);
                    props.putAll(JSON.parseObject(JSON.toJSONString(table), HashMap.class));
                    props.putAll(JSON.parseObject(JSON.toJSONString(packageAttribute), HashMap.class));
                    props.put("packagename", pair.getPackageName());
                    TemplateManager.transTemplate(pair.getOutDir(), pair.getTemplateDir() == null ? templateDir : pair.getTemplateDir(), pair.getTemplate(), pair.getFileName(), props);
                }
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static Connection getConnection(String url, String driveClass, String user, String password) {

        Connection connection = null;
        try {
            Driver driver = (Driver) Class.forName(driveClass, true, Thread.currentThread().getContextClassLoader()).newInstance();
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);
            connection = driver.connect(url, props);
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static List<TableAttribute> getTable(DatabaseMetaData databaseMetaData, String tableName) {
        ResultSet rs;
        List<TableAttribute> tableAttributeList = new ArrayList<>();
        try {
            if (tableName == null){
                throw new RuntimeException("Please enter a table name--single table, multiple tables (comma-separated), * (full table).");
            }
            if (Objects.equals(tableName.trim(), "*")) {
                rs = databaseMetaData.getTables(null, null,
                        "%", new String[]{"TABLE"});
                getTableAttributes(rs, tableAttributeList);
                return tableAttributeList;
            }

            if (tableName.contains("，")){
                throw new RuntimeException("Please use English commas!");
            }
            //判断tableName中表名的个数
            String[] names = tableName.split(",");
            for (String name : names) {
                rs = databaseMetaData.getTables(null, null,
                        name.trim(), new String[]{"TABLE"});
                List<TableAttribute> tableAttributes = getTableAttributes(rs, tableAttributeList);
                assert tableAttributes != null;
                tableAttributeList.addAll(tableAttributes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableAttributeList;
    }

    private static List<TableAttribute> getTableAttributes(ResultSet rs, List<TableAttribute> tableAttributeList) {
        try {
            while (rs.next()) {
                TableAttribute table = new TableAttribute();
                String tableRemarks = rs.getString("REMARKS").replaceAll("\\r", "").replaceAll("\\n", "");
                table.setBeanName(tableNameToBeanName(rs.getString("TABLE_NAME")));
                table.setMapperName(toLowerCaseFirstOne(table.getBeanName()));
                table.setRemark(tableRemarks);
                table.setTableName(rs.getString("TABLE_NAME"));

                tableAttributeList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableAttributeList;
    }


    private static void handleColoumns(TableAttribute table, DatabaseMetaData databaseMetaData, String tableName, Set<String> imports) {

        List<ColumnAttribute> columns = new ArrayList<>();
        List<ColumnAttribute> primaryKeys = new ArrayList<>();

        try {
            Set<String> excludes = new HashSet<>();
            ResultSet rs = databaseMetaData.getPrimaryKeys(null, null, tableName);
            handleColumns(rs, primaryKeys, columns, excludes, true, null);
            rs = databaseMetaData.getColumns(null, tableName, tableName, null);
            handleColumns(rs, primaryKeys, columns, excludes, false, imports);
            table.setColumns(columns);
            table.setPrimaryKeys(primaryKeys);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void handleColumns(ResultSet rs, List<ColumnAttribute> primaryKeys, List<ColumnAttribute> columns, Set<String> excludes, boolean isPrimary, Set<String> imports) throws SQLException {

        while (rs.next()) {

            String columnName = rs.getString("COLUMN_NAME");
            if (isPrimary) {
                excludes.add(columnName);
            } else {
                String dataType = rs.getString("DATA_TYPE");
                String typeName = rs.getString("TYPE_NAME");
                String remark = rs.getString("REMARKS").replaceAll("\\r", "").replaceAll("\\n", "");
                String columnDef = rs.getString("COLUMN_DEF");
                String sqlDataType = rs.getString("SQL_DATA_TYPE");
                String columnSize = rs.getString("COLUMN_SIZE");
                String nullable = rs.getString("NULLABLE");

                ColumnAttribute cla = new ColumnAttribute();
                cla.setName(columnName);
                cla.setDefValue(columnDef);
                cla.setNullAble("0".equals(nullable) ? false : true);
                cla.setRemark(remark);
                if (columnSize == null) {
                    cla.setSize(0);
                } else {
                    cla.setSize(Integer.valueOf(columnSize));
                }
                cla.setType(translateColumnType(typeName));
                if (excludes.contains(columnName)) {
                    cla.setPrimaryKey(true);
                    primaryKeys.add(cla);
                } else {
                    cla.setPrimaryKey(false);
                    columns.add(cla);
                }

                BeanAttribute bean = new BeanAttribute();
                cla.setBean(bean);
                bean.setName(columnNameToPropertyName(columnName));
                bean.setRemark(remark);
                transType(typeName, cla.getSize(), bean, imports);
            }

        }
    }

    private static String translateColumnType(String type) {
        String result = type;
        switch (type) {
            case "DATETIME":
                result = "TIMESTAMP";
                break;
            case "INT":
                result = "BIGINT";
                break;
            default:
                break;
        }

        return result;
    }

    private static String columnNameToPropertyName(String columnName) {
        String result = "";
        String[] parts = columnName.split("_");

        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                result += parts[i];
            } else {
                result += parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
            }
        }
        return result;
    }

    private static String tableNameToBeanName(String columnName) {
        String result = "";
        String[] parts = columnName.split("_");

        for (int i = 0; i < parts.length; i++) {
            result += parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
        }
        return result;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    private static void transType(String columnType, int size, BeanAttribute bean, Set<String> imports) {
        String type;
        String simpleType;
        if ("INT".equals(columnType)) {
            if (size > 11) {
                type = "java.lang.Long";
                simpleType = "Long";
            } else {
                type = "java.lang.Integer";
                simpleType = "Integer";
            }
        } else if ("BIGINT".equals(columnType)) {
            type = "java.lang.Long";
            simpleType = "Long";
        } else if ("DATETIME".equals(columnType)) {
            type = "java.util.Date";
            simpleType = "Date";
        } else if ("DECIMAL".equals(columnType)) {
            type = "java.math.BigDecimal";
            simpleType = "BigDecimal";
        } else if ("BIT".equals(columnType)) {
            type = "java.lang.Boolean";
            simpleType = "Boolean";
        } else {
            type = "java.lang.String";
            simpleType = "String";
        }

        bean.setType(type);
        bean.setTypeSimpleName(simpleType);
        imports.add(type);
    }
}
