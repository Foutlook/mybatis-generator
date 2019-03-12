package template.model;

import java.util.List;
import java.util.Set;

/**
 * @author xingkai.fan
 */
public class TableAttribute {

    private String tableName;

    private String remark;

    private String beanName;

    private String mapperName;

    private String beanPackage;

    private List<ColumnAttribute> primaryKeys;

    private List<ColumnAttribute> columns;

    private Set<String> importClasses;

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getBeanPackage() {
        return beanPackage;
    }

    public void setBeanPackage(String beanPackage) {
        this.beanPackage = beanPackage;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ColumnAttribute> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnAttribute> columns) {
        this.columns = columns;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public List<ColumnAttribute> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<ColumnAttribute> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public Set<String> getImportClasses() {
        return importClasses;
    }

    public void setImportClasses(Set<String> importClasses) {
        this.importClasses = importClasses;
    }
}
