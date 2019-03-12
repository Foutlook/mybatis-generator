package ${packagename};

import ${beanPackage}.${beanName};
import java.util.List;

import cn.fxkoutlook.common.IBaseService;

/**
 * @author
 * @date ${createTime}
 */
public interface I${beanName}Service extends IBaseService<${beanName}> {

    /**
    * @param list
    * @return int
    */
    int insertList(List<${beanName}> list);

}