package edu.xmu.test.xml.dom4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

/**
 * {@link <a href="http://blog.csdn.net/redarmy_chen/article/details/12969219">Dom4J Blog</a>}
 *
 */
public class Dom4JTest {
	@SuppressWarnings("unchecked")
	@Test
	public void readXMLTest() throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("src/test/resources/xml/wx_text_request.xml"));
		Element root = doc.getRootElement();
		System.out.println("Name: " + root.getName() + ", Text: " + root.getText());

		// Get all child elements named
		for (Element ele : (List<Element>) root.elements()) {
			System.out.println("Name: " + ele.getName() + ", Text: " + ele.getText());
		}

		// Get all child elements named "ToUserName"
		for (Element ele : (List<Element>) root.elements("ToUserName")) {
			System.out.println("Name: " + ele.getName() + ", Text: " + ele.getText());
		}

		// Get first element named "ToUserName"
		Element ele = root.element("ToUserName");
		System.out.println("Name: " + ele.getName() + ", Text: " + ele.getText());

		// Get attribute value
		Attribute attr = ele.attribute("id");
		System.out.println("AttributeName: " + attr.getName() + ", AttributeValue: " + attr.getValue());

		// How to get nextSibling?
	}

	@Test
	public void writeXMLTest() throws IOException {
		Document doc = DocumentHelper.createDocument();
		Element e = doc.addElement("xml");
		Element toUserNameEle = e.addElement("ToUserName");
		toUserNameEle.addCDATA("toUser");
		Element fromUserName = e.addElement("FromUserName");
		fromUserName.addCDATA("fromUser");
		Element createTime = e.addElement("CreateTime");
		createTime.addText("12345678");
		Element msgType = e.addElement("MsgType");
		msgType.addCDATA("text");
		Element content = e.addElement("Content");
		content.addCDATA("Hallo");

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setSuppressDeclaration(true);
		XMLWriter writer = new XMLWriter(new FileWriter("src/test/resources/xml/wx_text_resp.xml"), format);
		writer.write(doc);
		writer.close();
	}
}
