package edu.xmu.test.javaweb.jndi;

public class JNDIDemo {
	static interface Context {
		Object lookup(String name);
	}

	static class JdbcDatasourceContext implements Context {
		@Override
		public Object lookup(String name) {
			// dummy jdbc datasource
			return new Object();
		}
	}

	static interface ObjectFactory {
		Object getObjectInstance();
	}

	static class JdbcURLContextFactory implements ObjectFactory {
		@Override
		public Object getObjectInstance() {
			// dummy JDBC context
			return new JdbcDatasourceContext();
		}
	}

	static class InitContext implements Context {

		@Override
		public Object lookup(String name) {
			if (name.startsWith("jdbc:")) {
				ObjectFactory of = new JdbcURLContextFactory();
				Context subContext = (Context) of.getObjectInstance();
				return subContext.lookup(name.substring(5));
			} else {
				// our default impl
			}
			return null;
		}
	}

	public static void main(String[] args) {
		Context context = new InitContext();
		context.lookup("jdbc:mysql://localhost/test");
	}
}
