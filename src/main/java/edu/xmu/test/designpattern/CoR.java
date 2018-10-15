package edu.xmu.test.designpattern;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * An insight impl for Filter/FilterChain in {@link javax.servlet.FilterChain}
 * and {@link javax.servlet.Filter}
 */
public class CoR {

	static interface FilterChain {
		void doFilter(String request, String response);

		void addFilter(Filter filter);

		void removeFilter(Filter filter);
	}

	static class FilterChainImpl implements FilterChain {
		List<Filter> filters;
		int currIndex = 0;

		public FilterChainImpl(List<Filter> filters) {
			this.filters = filters;
		}

		@Override
		public void doFilter(String request, String response) {
			// we have to lock filters when traversing the filter chain
			synchronized (filters) {
				if (currIndex < filters.size()) {
					filters.get(currIndex++).doFilter(request, response, this);
				}
			}
		}

		@Override
		public void addFilter(Filter filter) {
			synchronized (filters) {
				filters.add(filter);
			}
		}

		@Override
		public void removeFilter(Filter filter) {
			// things would get complicated when we add removeFilter func
			synchronized (filters) {
				filters.remove(filter);
			}
		}
	}

	static interface Filter {
		void doFilter(String request, String response, FilterChain chain);
	}

	static class FilterImpl implements Filter {
		String name;

		public FilterImpl(String name) {
			super();
			this.name = name;
		}

		@Override
		public void doFilter(String request, String response, FilterChain chain) {
			System.out.println("Before " + name + " request: " + request + ", response" + response);
			chain.doFilter(request, response);
			System.out.println("After " + name + " request: " + request + ", response" + response);
		}
	}

	public static void main(String[] args) {
		FilterChain chain = new FilterChainImpl(Lists.newArrayList(new FilterImpl("Filter_A"), new FilterImpl(
				"Filter_B")));
		chain.doFilter("Hallo", "Yes?");
	}
}
