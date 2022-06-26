package com.scoperetail.commons.freemarker;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.Reader;

public class DomParser {
  public static Document parse(Reader xmlReader) throws DocumentException {
    SAXReader reader = new SAXReader();
    Document document = reader.read(xmlReader);
    return document;
  }

/*  public static void main(String[] args) throws Exception {
    Reader targetReader =
        new FileReader(
            new File("/Users/tushar/workspaces/OSS/commons-freemarker/src/test/xml/test1.xml"));
    final Document document = parse(targetReader);
    System.out.println(document.valueOf("//PurchaseOrder/CustomerName"));
    System.out.println(document.valueOf("//PurchaseOrder/@PurchaseOrderNumber"));
  }*/
}
