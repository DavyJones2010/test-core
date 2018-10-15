package edu.xmu.test.spring.cache;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@link <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html">Cache Abstraction</a>}
 */
public class SpringCachingSupportSample {
	static class Book {

		private String isbn;
		private String title;

		public Book(String isbn, String title) {
			this.isbn = isbn;
			this.title = title;
		}

		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		@Override
		public String toString() {
			return "Book{" + "isbn='" + isbn + '\'' + ", title='" + title + '\'' + '}';
		}
	}

	static interface BookRepository {

		Book getByIsbn(String isbn);

	}

	static class SimpleBookRepository implements BookRepository {

		@Override
		public Book getByIsbn(String isbn) {
			simulateSlowService();
			return new Book(isbn, "Some book");
		}

		// Don't do this at home
		private void simulateSlowService() {
			try {
				long time = 5000L;
				Thread.sleep(time);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
		}

	}

	public static void main(String[] args) {
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/cache/spring-cache.xml");
		//BookRepository bookRepository = (BookRepository) context.getBean("bookRepository");
		//Book book = bookRepository.getByIsbn("1234567");
		//System.out.println(book);
		//// We do not need to wait for another 5 seconds, the book is returned directly from cache
		//book = bookRepository.getByIsbn("1234567");
		//System.out.println(book);
        //
		//context.close();
	}
}
