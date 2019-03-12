package ${packagename};

import cn.fxkoutlook.common.BaseEntity;
<#if importClasses??>
    <#list importClasses as ic>
import ${ic};
    </#list>
</#if>

/**
 * ${remark}
 * @author
 * @date ${createTime}
 */
public class ${beanName} extends BaseEntity {

<#if primaryKeys??>
    <#list primaryKeys as pk>
    /**
     * ${pk.remark}
     */
    private ${pk.bean.typeSimpleName} ${pk.bean.name};
    </#list>
</#if>

<#if columns??>
    <#list columns as column>
    /**
     * ${column.remark}
     */
    private ${column.bean.typeSimpleName} ${column.bean.name};
    </#list>
</#if>

<#if primaryKeys??>
<#list primaryKeys as pk>

    public void set${pk.bean.name?cap_first}(${pk.bean.typeSimpleName} ${pk.bean.name}){
        this.${pk.bean.name} = ${pk.bean.name};
    }

    public ${pk.bean.typeSimpleName} get${pk.bean.name?cap_first}(){
        return this.${pk.bean.name};
    }
</#list>
</#if>

<#if columns??>
<#list columns as column>

    public void set${column.bean.name?cap_first}(${column.bean.typeSimpleName} ${column.bean.name}){
        this.${column.bean.name} = ${column.bean.name};
    }

    public ${column.bean.typeSimpleName} get${column.bean.name?cap_first}(){
        return this.${column.bean.name};
    }
</#list>
</#if>
}