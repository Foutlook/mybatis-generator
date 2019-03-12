package ${packagename};

import ${beanPackage}.${beanName};
import javax.annotation.Resource;
import java.util.List;

import cn.fxkoutlook.common.IBaseDao;
import org.springframework.stereotype.Service;
import cn.fxkoutlook.common.BaseService;
<#if servicePackageName??>
import ${servicePackageName}.I${beanName}Service;
</#if>
<#if daoMapperPackageName??>
import ${daoMapperPackageName}.${beanName}Mapper;
</#if>

/**
* @author
* @date ${createTime}
*/
@Service
public class ${beanName}Service extends BaseService<${beanName}> implements I${beanName}Service {
    @Resource
    private ${beanName}Mapper ${mapperName}Mapper;

    @Override
    public IBaseDao<${beanName}> getDao() {
        return ${mapperName}Mapper;
    }

    @Override
    public int insertList(List<${beanName}> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return ${mapperName}Mapper.insertList(list);
    }
}