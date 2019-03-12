package template.model;

import freemarker.template.*;

import java.io.*;
import java.util.Map;

/**
 * @author xingkai.fan
 */
public class TemplateManager {

    public static void transTemplate(String outDirectory, String tempDirectory, String tempaltename, String filename, Map<String, Object> props) {
        //创建一个合适的Configration对象
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        if (outDirectory != null && props.get("packagename") != null) {
            try {
                configuration.setDirectoryForTemplateLoading(new File(tempDirectory));
                configuration.setObjectWrapper(new DefaultObjectWrapper());
                //这个一定要设置，不然在生成的页面中 会乱码
                configuration.setDefaultEncoding("UTF-8");
                configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                configuration.setLogTemplateExceptions(false);

                //获取或创建一个模版。
                Template template = configuration.getTemplate(tempaltename);
                //destination file
                String filePath = outDirectory.replaceAll("\\\\","/") + "/" + filename;
                File targetFile = new File(filePath);
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }
                try(FileOutputStream fos = new FileOutputStream(filePath, false);
                    Writer writer = new OutputStreamWriter(fos, "UTF-8")){

                    template.process(props, writer);
                }
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        }


    }
}
