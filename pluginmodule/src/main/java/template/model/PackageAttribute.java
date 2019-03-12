package template.model;

/**
 * @author xingkai.fan
 * @date 2019/1/23 18:43
 */
public class PackageAttribute {

    private String servicePackageName;

    private String daoMapperPackageName;

    private String createTime;

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public String getDaoMapperPackageName() {
        return daoMapperPackageName;
    }

    public void setDaoMapperPackageName(String daoMapperPackageName) {
        this.daoMapperPackageName = daoMapperPackageName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
