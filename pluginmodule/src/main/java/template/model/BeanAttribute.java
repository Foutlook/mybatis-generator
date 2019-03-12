package template.model;

/**
 * @author xingkai.fan
 */
public class BeanAttribute {

    private String name;

    private String typeSimpleName;

    private String type;

    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeSimpleName() {
        return typeSimpleName;
    }

    public void setTypeSimpleName(String typeSimpleName) {
        this.typeSimpleName = typeSimpleName;
    }
}
