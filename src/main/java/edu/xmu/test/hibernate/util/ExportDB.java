package edu.xmu.test.hibernate.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class ExportDB {
	public static void main(String[] args) {
		Configuration config = new Configuration().configure("hibernate/hibernate.cfg.xml");
		SchemaExport export = new SchemaExport(config);
		export.create(true, true);
	}
}