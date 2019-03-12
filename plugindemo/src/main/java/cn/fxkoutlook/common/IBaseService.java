package cn.fxkoutlook.common;

import java.util.HashMap;
import java.util.List;

public interface IBaseService<T> {

    public int insert(T record);

    public int update(T record);

    public int delete(Long id);

    public T select(Long id);

    List<T> getsByMap(HashMap params);

    int getCountByMap(HashMap params);
}
