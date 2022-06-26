package com.scoperetail.commons.freemarker;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FreemarkerUtilsTest {
  private static final String BASE_DIR = Paths.get("").toAbsolutePath().toString();
  private static final String TEMPLATE_DIR = BASE_DIR + "/src/test/templates";
  private static final String XML_DIR = BASE_DIR + "/src/test/xml/";
  private static final String JSON_DIR = BASE_DIR + "/src/test/json/";
  private FreemarkerUtils freemarkerUtils = FreemarkerUtils.getInstance(TEMPLATE_DIR);

  @Test
  void xmlToJson() throws IOException {
    String xml = new String(Files.readAllBytes(Paths.get(XML_DIR + "test1.xml")));
    String ouput = freemarkerUtils.xmlToString(xml, "test1.ftl");
    System.out.printf(ouput);
  }

  @Test
  void jsonToXml() throws IOException {
    String xml = new String(Files.readAllBytes(Paths.get(JSON_DIR + "test1.json")));
    String ouput = freemarkerUtils.jsonToString(xml, "test2.ftl");
    System.out.printf(ouput);
  }
}
