package cn.fxkoutlook.common;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface IBaseDao<T> {

    public int insert(T record);

    public int update(T record);

    public int delete(Long record);

    public T select(Long id);

    List<T> getsByMap(@Param("map") HashMap params);

    int getCountByMap(@Param("map") HashMap params);

}
