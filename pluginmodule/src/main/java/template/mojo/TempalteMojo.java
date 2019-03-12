package template.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import template.model.DbConfig;
import template.model.Generator;
import template.model.TableConfig;

import java.util.List;

/**
 * @author xingkai.fan
 */
@Mojo( name = "tempalte", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class TempalteMojo extends AbstractMojo {

    private final static String templateDirPath = "/template";

    /**
     * 基础配置
     */
    @Parameter
    private String driveClass;
    @Parameter
    private String url;
    @Parameter
    private String user;
    @Parameter
    private String password;

    /**
     * 表配置
     */
    @Parameter
    private List<TableConfig> tableConfigs;

    @Parameter(defaultValue = "empty", required = false)
    private String templateDir;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        System.out.println("into the method");
        try{
            if("empty".equals(templateDir)){
                templateDir = System.getProperty("user.dir").replaceAll("\\\\","/") + templateDirPath;
            }

            DbConfig dbconfig = new DbConfig(url, user, password, driveClass);
            dbconfig.setTbconfigs(tableConfigs);
            Generator.generate(dbconfig, templateDir);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public String getDriveClass() {
        return driveClass;
    }

    public void setDriveClass(String driveClass) {
        this.driveClass = driveClass;
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

    public List<TableConfig> getTableConfigs() {
        return tableConfigs;
    }

    public void setTableConfigs(List<TableConfig> tableConfigs) {
        this.tableConfigs = tableConfigs;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

}
