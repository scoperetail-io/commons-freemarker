import freemarker.template.*;

import java.util.*;
import java.io.*;

public class Test1 {

    private static String BASE_DIR = "/Users/tushar/workspaces/STRIIM/wakanda-commons/src/test/";
    private static Configuration cfg;

    private static void init() throws IOException {
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDirectoryForTemplateLoading(new File(BASE_DIR + "templates"));
        // Recommended settings for new projects:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    static {
        try {
            init();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) throws Exception {



        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map<String, Object> root = new HashMap<>();
//        root.put("user", "Big Joe");
        root.put(
                "doc",
                freemarker.ext.dom.NodeModel.parse(new File(BASE_DIR+"xml/test1.xml")));

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("test1.ftl");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        // Note: Depending on what `out` is, you may need to call `out.close()`.
        // This is usually the case for file output, but not for servlet output.
    }
}