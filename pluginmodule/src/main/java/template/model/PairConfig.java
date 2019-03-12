package template.model;

/**
 * @author xingkai.fan
 */
public class PairConfig {

    private String packageName;

    private String fileName;

    private String templateDir;

    private String template;

    private String outDir;

    public PairConfig(){

    }

    public PairConfig(String packageName, String fileName, String templateDir, String template, String outDir){
        this.packageName = packageName;
        this.fileName = fileName;
        this.templateDir = templateDir;
        this.template = template;
        this.outDir = outDir;
    }

    public PairConfig(String templateDir, String template){
        this.templateDir = templateDir;
        this.template = template;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOutDir() {
        return outDir;
    }

    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }
}
