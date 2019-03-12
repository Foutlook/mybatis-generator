package ${packagename};

import cn.fxkoutlook.common.IBaseDao;
import java.util.List;

import ${beanPackage}.${beanName};

/**
* @author
* @date ${createTime}
*/
public interface ${beanName}Mapper extends IBaseDao<${beanName}>{

    /**
     * @param list
     * @return int
     */
    int insertList(List<${beanName}> list);

}