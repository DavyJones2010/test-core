package edu.xmu.test.javaweb.tomcat;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * 
 * Find class diagram in
 * {@link <a href="file:///${basedir}/src/main/resources/mooc/design-pattern/Proxy-Design-Pattern-PipelineValve.png">Proxy-Design-Pattern-PipelineValve.png</a>}
 */
public class ContainerPipelineValveSample {

	static interface Valve {
		void invoke(Object req, Object resp, ValveContext valveContext);
	}

	static class BasicValve implements Valve {
		final static Logger logger = Logger.getLogger(BasicValve.class);

		@Override
		public void invoke(Object req, Object resp, ValveContext valveContext) {
			/*
			 * In normal web container impl, it will create and invoke FilterChain according to web.xml, and invoke Servlet.service()
			 */
			logger.info("Basic valve invoked!");
		}
	}

	static class FilterValve implements Valve {
		final static Logger logger = Logger.getLogger(FilterValve.class);

		String name;

		public FilterValve(String string) {
			this.name = string;
		}

		@Override
		public void invoke(Object req, Object resp, ValveContext valveContext) {
			logger.info("Before filter valve" + this.toString() + " invoked!");
			valveContext.invokeNext(req, resp);
			logger.info("After filter valve" + this.toString() + " invoked!");
		}

		@Override
		public String toString() {
			return name;
		}
	}

	static interface Pipeline {
		Valve getBasic();

		void setBasic(Valve valve);

		List<Valve> getValves();

		void setValves(List<Valve> valve);

		void invoke(Object req, Object resp);
	}

	static class StandardPipeline implements Pipeline {
		List<Valve> valves;
		Valve basic;

		@Override
		public Valve getBasic() {
			return basic;
		}

		@Override
		public void setBasic(Valve valve) {
			this.basic = valve;
		}

		@Override
		public List<Valve> getValves() {
			return valves;
		}

		@Override
		public void setValves(List<Valve> valves) {
			this.valves = valves;
		}

		@Override
		public void invoke(Object req, Object resp) {
			ValveContext context = new ValveContext(basic, valves);
			context.invokeNext(req, resp);
		}
	}

	/**
	 * It uses proxy design pattern and delegates a Pipeline instance.
	 */
	static class WrapperContainer implements Pipeline {
		final static Logger logger = Logger.getLogger(WrapperContainer.class);

		Pipeline pipeline;

		public WrapperContainer(Pipeline pipeline) {
			super();
			this.pipeline = pipeline;
		}

		@Override
		public Valve getBasic() {
			logger.info("FireEvent: getBasic");
			return pipeline.getBasic();
		}

		@Override
		public void setBasic(Valve valve) {
			logger.info("FireEvent: setBasic");
			pipeline.setBasic(valve);
		}

		@Override
		public List<Valve> getValves() {
			logger.info("FireEvent: getValves");
			return pipeline.getValves();
		}

		@Override
		public void setValves(List<Valve> valve) {
			logger.info("FireEvent: setValves");
			pipeline.setValves(valve);
		}

		@Override
		public void invoke(Object req, Object resp) {
			logger.info("FireEvent: invoke");
			pipeline.invoke(req, resp);
		}

	}

	static class ValveContext {
		List<Valve> valves;
		Valve basic;
		int currentCursor = 0;

		public ValveContext(Valve basic, List<Valve> valves) {
			Preconditions.checkNotNull(basic, "Basic valve cannot be null!");
			this.basic = basic;
			this.valves = valves;
		}

		public void invokeNext(Object req, Object resp) {
			if (currentCursor < valves.size()) {
				Valve v = valves.get(currentCursor);
				currentCursor++;
				v.invoke(req, resp, this);
			} else {
				basic.invoke(req, resp, this);
			}
		}
	}

	public static void main(String[] args) {
		Pipeline p = new StandardPipeline();
		p.setBasic(new BasicValve());
		p.setValves(Lists.newArrayList(new FilterValve("A"), new FilterValve("B"), new FilterValve("C"), new FilterValve("D")));
		WrapperContainer c = new WrapperContainer(p);
		c.invoke(new Object(), new Object());
	}
}
