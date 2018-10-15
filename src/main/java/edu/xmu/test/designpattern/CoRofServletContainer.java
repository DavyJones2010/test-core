package edu.xmu.test.designpattern;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

public class CoRofServletContainer {
	static interface Loader {
	}

	static class SimpleLoader implements Loader {
	}

	static interface Mapper {
		Container getContainer();

		void setContainer(Container container);

		Container map(String req, boolean update);
	}

	static class SimpleContextMapper implements Mapper {
		SimpleContext container;

		@Override
		public Container getContainer() {
			return container;
		}

		@Override
		public void setContainer(Container container) {
			if (!(container instanceof SimpleContext)) {
				throw new RuntimeException("Illegal container type: " + container.getClass().getSimpleName());
			}
			this.container = (SimpleContext) container;
		}

		@Override
		public Container map(String urlPattern, boolean update) {
			if ("Hello".equals(urlPattern)) {
				return new SimpleContext();
			} else {
				return null;
			}
		}
	}

	static interface Container {
		void setLoader(Loader loader);

		Loader getLoader();

		void addMapper(String urlPattern, String servletName);

		Mapper getMapper(String key);

		void invoke(String req, String resp);
	}

	static interface Context extends Container {
		void addChild(Context context);
	}

	static class SimpleContext implements Context, Pipeline {

		@Override
		public void setLoader(Loader loader) {
		}

		@Override
		public Loader getLoader() {
			return null;
		}

		@Override
		public void addMapper(String urlPattern, String servletName) {
		}

		@Override
		public Mapper getMapper(String urlPattern) {
			return null;
		}

		@Override
		public void addChild(Context context) {
		}

		@Override
		public void invoke(String req, String resp) {
			// TODO Auto-generated method stub

		}

		@Override
		public Valve getBasic() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setBasic(Valve valve) {
			// TODO Auto-generated method stub

		}

		@Override
		public List<Valve> getValves() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addValve(Valve valve) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeValve(Valve valve) {
			// TODO Auto-generated method stub

		}

	}

	static interface Wrapper extends Container {
		void setName(String name);

		void setServletClass(String className);
	}

	static class SimpleWrapper implements Wrapper {

		@Override
		public void setLoader(Loader loader) {

		}

		@Override
		public Loader getLoader() {
			return null;
		}

		@Override
		public void addMapper(String urlPattern, String servletName) {

		}

		@Override
		public Mapper getMapper(String key) {
			return null;
		}

		@Override
		public void invoke(String req, String resp) {

		}

		@Override
		public void setName(String name) {

		}

		@Override
		public void setServletClass(String className) {
		}

	}

	/**
	 * Just like {@link edu.xmu.test.designpattern.Observer.Subject}
	 */
	static interface Pipeline {
		void invoke(String req, String resp);

		Valve getBasic();

		void setBasic(Valve valve);

		List<Valve> getValves();

		void addValve(Valve valve);

		void removeValve(Valve valve);
	}

	static class SimplePipeline implements Pipeline {
		ValveContext valveContext;

		public SimplePipeline(ValveContext valveContext) {
			this.valveContext = valveContext;
		}

		@Override
		public void invoke(String req, String resp) {
			valveContext.invokeNext(req, resp);
		}

		@Override
		public Valve getBasic() {
			return valveContext.getBasic();
		}

		@Override
		public void setBasic(Valve valve) {
			valveContext.setBasic(valve);
		}

		@Override
		public List<Valve> getValves() {
			return valveContext.getValves();
		}

		@Override
		public void addValve(Valve valve) {
			valveContext.addValve(valve);
		}

		@Override
		public void removeValve(Valve valve) {
			valveContext.removeValve(valve);
		}

	}

	static interface ValveContext extends Pipeline {
		void invokeNext(String req, String resp);
	}

	static class StandardPipelineValveContext implements ValveContext {
		int stage = 0;
		List<Valve> valves = Lists.newArrayList();
		Valve basic;

		@Override
		public void invoke(String req, String resp) {
			throw new RuntimeException("Unsupported operation");
		}

		@Override
		public Valve getBasic() {
			return basic;
		}

		@Override
		public void setBasic(Valve valve) {
			basic = valve;
		}

		@Override
		public List<Valve> getValves() {
			return valves;
		}

		@Override
		public void addValve(Valve valve) {
			valves.add(valve);
		}

		@Override
		public void removeValve(Valve valve) {
			valves.remove(valve);
		}

		@Override
		public void invokeNext(String req, String resp) {
			int subscript = stage;
			stage++;

			if (subscript < valves.size()) {
				valves.get(subscript).invoke(req, resp, this);
			} else if ((subscript == valves.size()) && (null != basic)) {
				basic.invoke(req, resp, this);
			} else {
				throw new RuntimeException("standdardPipeline.noValve");
			}
		}
	}

	static interface Valve {
		void invoke(String req, String resp, ValveContext context);
	}

	/**
	 * Act as WrapperValve
	 */
	static class LoggerValve implements Valve {
		static final Logger logger = Logger.getLogger(LoggerValve.class);

		@Override
		public void invoke(String req, String resp, ValveContext context) {
			logger.info("req: " + req + ", resp: " + resp);
			context.invokeNext(req, resp);
		}
	}

	/**
	 * Act as WrapperValve
	 */
	static class ReverseValve implements Valve {
		static final Logger logger = Logger.getLogger(ReverseValve.class);

		@Override
		public void invoke(String req, String resp, ValveContext context) {
			logger.info("req: " + StringUtils.reverse(req) + ", resp: " + StringUtils.reverse(resp));
			context.invokeNext(StringUtils.reverse(req), StringUtils.reverse(resp));
		}
	}

	/**
	 * Act as BasicValve
	 */
	static class SimpleWrapperValve implements Valve {
		static final Logger logger = Logger.getLogger(SimpleWrapperValve.class);

		@Override
		public void invoke(String req, String resp, ValveContext context) {
			logger.info("endpoint, req: " + req + ", resp: " + resp + ", and Servlet.service() should be invoked here");
		}
	}

	public static void main(String[] args) {
		// TODO: finish
		Wrapper wrapper = new SimpleWrapper();
		wrapper.setName("Primitive");
		wrapper.setServletClass("PrimitiveServlet");

		Wrapper wrapper2 = new SimpleWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");

		SimpleContext simpleContext = new SimpleContext();
		// simpleContext.addChild(wrapper);
		// simpleContext.addChild(wrapper2);

		Valve simpleWrapperValve = new SimpleWrapperValve();
		Valve reverseValve = new ReverseValve();
		Valve loggerValve = new LoggerValve();
		simpleContext.addValve(reverseValve);
		simpleContext.addValve(loggerValve);
		simpleContext.setBasic(simpleWrapperValve);

		simpleContext.addMapper("/Primitive", "Primitive");
		simpleContext.addMapper("/Modern", "Modern");

		Container container = simpleContext.getMapper("/Primitive").map("Hello", false);
		container.invoke("Hello", "Yes");
		// ContainerBase container = new ContainerBase();
		// container.pipeline = simplePipeline;
		// container.invoke("Hello", "World");
	}
}
