package template.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkai.fan
 */
public class TableConfig {

    private String tableName;

    private PairConfig daoConfig = new PairConfig("empty", "DaoMapper.ftl");

    private PairConfig xmlConfig = new PairConfig("empty", "DaoXml.ftl");

    private PairConfig modelConfig = new PairConfig("empty", "Model.ftl");

    private PairConfig serviceImplConfig = new PairConfig("empty", "ServiceImpl.ftl");

    private PairConfig serviceConfig = new PairConfig("empty", "Service.ftl");

    private List<PairConfig> pairConfigs = new ArrayList<>();

    public PairConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(PairConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public PairConfig getServiceImplConfig() {
        return serviceImplConfig;
    }

    public void setServiceImplConfig(PairConfig serviceImplConfig) {
        this.serviceImplConfig = serviceImplConfig;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<PairConfig> getPairConfigs() {
        return pairConfigs;
    }

    public void setPairConfigs(List<PairConfig> pairConfigs) {
        this.pairConfigs = pairConfigs;
    }

    public PairConfig getDaoConfig() {
        return daoConfig;
    }

    public void setDaoConfig(PairConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    public PairConfig getXmlConfig() {
        return xmlConfig;
    }

    public void setXmlConfig(PairConfig xmlConfig) {
        this.xmlConfig = xmlConfig;
    }

    public PairConfig getModelConfig() {
        return modelConfig;
    }

    public void setModelConfig(PairConfig modelConfig) {
        this.modelConfig = modelConfig;
    }
}
