package edu.xmu.test.javax.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class SearcherUtil {
	private Directory directory;
	private IndexReader reader;

	public SearcherUtil() {
		directory = new RAMDirectory();
	}

	public IndexSearcher getSearcher() {
		try {
			if (null == reader) {
				reader = IndexReader.open(directory);
			} else {
				IndexReader tempReader = IndexReader.openIfChanged(reader);
				if (null != tempReader) {
					reader.close();
					reader = tempReader;
				}
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new IndexSearcher(reader);
	}

	public void buildIndex(List<Student> studentList) {
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, new SimpleAnalyzer(Version.LUCENE_35));
		IndexWriter writer = null;
		Document doc = null;
		try {
			writer = new IndexWriter(directory, config);
			for (Student student : studentList) {
				doc = new Document();
				doc.add(new Field("id", student.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("name", student.getName(), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("password", student.getPassword(), Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("gender", student.getGender(), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new NumericField("score", Field.Store.YES, true).setIntValue(student.getScore()));

				writer.addDocument(doc);
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Precise query using TermQuery
	 * 
	 * @param field
	 * @param name
	 */
	public void searchByTerm(String fieldName, String fieldValue) {
		IndexSearcher searcher = getSearcher();
		Query query = new TermQuery(new Term(fieldName, fieldValue));

		try {
			TopDocs tds = searcher.search(query, 100);
			System.out.println("Total Hits: " + tds.totalHits);

			for (ScoreDoc sd : tds.scoreDocs) {
				Document document = searcher.doc(sd.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				searcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchByTermRange(String fieldName, String fieldValueStart, String fieldValueEnd, int resultSize) {
		IndexSearcher searcher = getSearcher();

		/**
		 * @param1 fieldName : field
		 * @param2 fieldValueStart : lowerTerm
		 * @param3 fieldValueEnd : upperTerm
		 * @param4 true : includeLower
		 * @param5 true : includeUpper
		 */
		Query query = new TermRangeQuery(fieldName, fieldValueStart, fieldValueEnd, true, true);
		try {
			TopDocs tds = searcher.search(query, resultSize);
			Document document = null;
			for (ScoreDoc doc : tds.scoreDocs) {
				document = searcher.doc(doc.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				searcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchByNumericRange(String fieldName, int fieldValueStart, int fieldValueEnd, int resultSize) {
		IndexSearcher searcher = getSearcher();

		Query query = NumericRangeQuery.newIntRange(fieldName, fieldValueStart, fieldValueEnd, true, true);

		try {
			TopDocs tds = searcher.search(query, resultSize);
			Document document = null;
			for (ScoreDoc scoreDoc : tds.scoreDocs) {
				document = searcher.doc(scoreDoc.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchByPrefix(String fieldName, String fieldValue, int resultSize) {
		IndexSearcher indexSearcher = getSearcher();

		Query query = new PrefixQuery(new Term(fieldName, fieldValue));

		try {
			TopDocs tds = indexSearcher.search(query, resultSize);
			Document document = null;
			for (ScoreDoc sd : tds.scoreDocs) {
				document = indexSearcher.doc(sd.doc);
				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchByWildcard(String fieldName, String fieldValue, int resultSize) {
		IndexSearcher indexSearcher = getSearcher();

		Query query = new WildcardQuery(new Term(fieldName, fieldValue));
		try {
			TopDocs tds = indexSearcher.search(query, resultSize);
			Document document = null;
			for (ScoreDoc sd : tds.scoreDocs) {
				document = indexSearcher.doc(sd.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchByBooleanQuery(int resultSize) {
		IndexSearcher indexSearcher = getSearcher();

		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(new TermQuery(new Term("gender", "male")), Occur.MUST);
		booleanQuery.add(new TermQuery(new Term("name", "Davy")), Occur.MUST);

		try {
			TopDocs tds = indexSearcher.search(booleanQuery, resultSize);
			Document document = null;
			for (ScoreDoc sd : tds.scoreDocs) {
				document = indexSearcher.doc(sd.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchByPhrase(String fieldName, String startFieldValue, String endFieldValue, int slop, int resultSize) {
		IndexSearcher indexSearcher = getSearcher();

		PhraseQuery query = new PhraseQuery();
		query.setSlop(slop);
		query.add(new Term(fieldName, startFieldValue));
		query.add(new Term(fieldName, endFieldValue));
		try {
			TopDocs tds = indexSearcher.search(query, resultSize);
			Document document = null;
			for (ScoreDoc sd : tds.scoreDocs) {
				document = indexSearcher.doc(sd.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchByFuzzy(String fieldName, String fieldValue, int resultSize) {
		IndexSearcher indexSearcher = getSearcher();

		Query query = new FuzzyQuery(new Term(fieldName, fieldValue));
		try {
			TopDocs tds = indexSearcher.search(query, resultSize);
			Document document = null;
			for (ScoreDoc sd : tds.scoreDocs) {
				document = indexSearcher.doc(sd.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				indexSearcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void searchByQueryParser(Query query, int resultSize) {
		IndexSearcher searcher = getSearcher();

		try {
			TopDocs tds = searcher.search(query, resultSize);
			Document document = null;
			for (ScoreDoc sd : tds.scoreDocs) {
				document = searcher.doc(sd.doc);

				System.out.println("id = " + document.get("id") + ", name = " + document.get("name") + ", password = "
						+ document.get("password") + ", gender = " + document.get("gender") + ", score = "
						+ document.get("score"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
