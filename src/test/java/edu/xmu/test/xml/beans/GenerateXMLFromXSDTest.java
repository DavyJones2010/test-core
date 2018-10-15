package edu.xmu.test.xml.beans;

import java.io.File;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import com.google.common.collect.Lists;

import edu.xmu.test.xml.beans.ExpenseT;
import edu.xmu.test.xml.beans.ItemListT;
import edu.xmu.test.xml.beans.ItemT;
import edu.xmu.test.xml.beans.ObjectFactory;
import edu.xmu.test.xml.beans.UserT;

public class GenerateXMLFromXSDTest {
	@Test
	public void generateXMLFromXSDTest() throws JAXBException {
		ExpenseT expenseT = new ExpenseT();
		expenseT.user = new UserT();
		expenseT.user.userName = "Davy";
		expenseT.items = new ItemListT();
		ItemT item = new ItemT();
		item.itemName = "Item-A";
		item.purchasedOn = "Shop-A";
		item.amount = new BigDecimal("20.90");

		ItemT item2 = new ItemT();
		item2.itemName = "Item-B";
		item2.purchasedOn = "Shop-B";
		item2.amount = new BigDecimal("21.12");

		expenseT.items.item = Lists.newArrayList(item, item2);
		JAXBContext context = JAXBContext.newInstance(ExpenseT.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		ObjectFactory factory = new ObjectFactory();
		JAXBElement<ExpenseT> rootElement = factory
				.createExpenseReport(expenseT);
		marshaller.marshal(rootElement, new File(
				"src/test/resources/xml/expense_jaxb.xml"));
	}
}
