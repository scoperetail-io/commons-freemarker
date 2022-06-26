package com.scoperetail.commons.freemarker;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerUtils {

  private Configuration cfg;
  private boolean isInit = false;

  // hide constructor
  private FreemarkerUtils() {
    // do nothing
  }

  public static final FreemarkerUtils getInstance(final String templateDir)
      throws ExceptionInInitializerError {
    final FreemarkerUtils instance = new FreemarkerUtils();
    try {
      instance.init(templateDir);
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
    return instance;
  }

  public static final FreemarkerUtils getInstance(final Configuration config) {
    final FreemarkerUtils instance = new FreemarkerUtils();
    instance.init(config);
    return instance;
  }

  private void init(final Configuration config) {
    if (!isInit) {
      /* ------------------------------------------------------------------------ */
      /* You should do this ONLY ONCE in the whole application life-cycle:        */

      /* Create and adjust the configuration singleton */
      cfg = config;
      isInit = true;
    }
  }

  public void init(final String templateDir) throws IOException {
    if (!isInit) {
      /* ------------------------------------------------------------------------ */
      /* You should do this ONLY ONCE in the whole application life-cycle:        */

      /* Create and adjust the configuration singleton */
      cfg = new Configuration(Configuration.VERSION_2_3_31);
      // Recommended settings for new projects:
      cfg.setDefaultEncoding("UTF-8");
      cfg.setDirectoryForTemplateLoading(new File(templateDir));
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      cfg.setLogTemplateExceptions(false);
      cfg.setWrapUncheckedExceptions(true);
      cfg.setFallbackOnNullLoopVariable(false);
      isInit = true;
    }
  }

  /**
   * Parse the XML using Apache Freemarker builtin api for XML processing. Refer to <a *
   * href="https://freemarker.apache.org/docs/xgui.html">XML Processing Guide</a> for details
   *
   * @param xml
   * @param templateFileName
   * @return String - Output after applying the FTL for given XML document
   */
  public String xmlToString(final String xml, final String templateFileName) {
    try {
      return sourceAsXml(xml, templateFileName);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    } catch (TemplateException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Parse the XML using Apache Freemarker builtin api for XML processing. Refer to <a
   * href="https://freemarker.apache.org/docs/xgui.html">XML Processing Guide</a> for details
   *
   * @param xml
   * @param templateFileName
   * @return
   * @throws IOException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws TemplateException
   */
  private String sourceAsXml(final String xml, final String templateFileName)
      throws IOException, ParserConfigurationException, SAXException, TemplateException {
    final Map<String, Object> root = new HashMap<>(1, 1);
    root.put("doc", freemarker.ext.dom.NodeModel.parse(new InputSource(new StringReader(xml))));
    return process(templateFileName, root);
  }

  /**
   * Converts a JSON to Map using jackson API. See tests for usage and FTL examples.
   *
   * @param json
   * @param templateFileName
   * @return
   */
  public String jsonToString(final String json, final String templateFileName) {
    try {
      return sourceAsJson(json, templateFileName);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (TemplateException e) {
      throw new RuntimeException(e);
    }
  }

  private String sourceAsJson(final String json, final String templateFileName)
      throws IOException, TemplateException {
    final Map<String, Object> root = new ObjectMapper().readValue(json, Map.class);
    return process(templateFileName, root);
  }

  private String process(final String templateFileName, final Map<String, Object> root)
      throws IOException, TemplateException {
    Template temp = cfg.getTemplate(templateFileName);
    final StringWriter out = new StringWriter();
    temp.process(root, out);
    return out.toString();
  }

  public Configuration getCfg() {
    return cfg;
  }

  public boolean isInit() {
    return isInit;
  }
}
