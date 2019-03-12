package cn.fxkoutlook.common;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public abstract class BaseService<T> {

    private transient static Logger logger = LogManager.getLogger(Object.class);

    public abstract IBaseDao<T> getDao();

    public int delete(Long ukid) {
        try {
            return getDao().delete(ukid);
        } catch (Exception e) {
            logger.error("service database error {}", e);
            throw e;
        } finally {
            // sessionClose();
        }
    }

    public int insert(T record) {
        try {
            return getDao().insert(record);
        } catch (Exception e) {
            logger.error("service database error {}", e);
            throw e;
        } finally {
            // sessionClose();
        }
    }

    public int update(T record) {
        try {
            return getDao().update(record);
        } catch (Exception e) {
            logger.error("service database error {}", e);
            throw e;
        } finally {
            // sessionClose();
        }
    }

    public T select(Long id) {
        try {
            return getDao().select(id);
        } catch (Exception e) {
            logger.error("service database error {}", e);
            throw e;
        }
    }

    public List<T> getsByMap(HashMap map) {
        return getDao().getsByMap(map);
    }

    public int getCountByMap(HashMap map) {
        return getDao().getCountByMap(map);
    }

}
