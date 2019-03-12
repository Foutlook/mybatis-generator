package template.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkai.fan
 */
public class DbConfig {


    private String url;

    private String user;

    private String password;

    private String driveClass;

    private List<TableConfig> tbconfigs = new ArrayList<>();

    public DbConfig(String url, String user, String password, String driveClass) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driveClass = driveClass;
    }

    public void addTbconfig(TableConfig tbconfig){
        tbconfigs.add(tbconfig);
    }

    public List<TableConfig> getTbconfigs() {
        return tbconfigs;
    }

    public void setTbconfigs(List<TableConfig> tbconfigs) {
        this.tbconfigs = tbconfigs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriveClass() {
        return driveClass;
    }

    public void setDriveClass(String driveClass) {
        this.driveClass = driveClass;
    }
}
