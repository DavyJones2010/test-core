package edu.xmu.test.forkjoin;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * <p>
 * <a href=
 * "http://howtodoinjava.com/2014/05/27/forkjoin-framework-tutorial-forkjoinpool-example"
 * >Fork/Join Example</a>
 * </p>
 */
public class ForkJoinTest {
	@Test
	public void forkJoinTest() {
		ForkJoinPool pool = new ForkJoinPool();

		FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
		FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
		FolderProcessor documents = new FolderProcessor(
				"C:\\Documents And Settings", "log");
		pool.execute(system);
		pool.execute(apps);
		pool.execute(documents);

		do {
			System.out.printf("******************************************\n");
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n",
					pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n",
					pool.getQueuedTaskCount());
			System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
			System.out.printf("******************************************\n");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while ((!system.isDone()) || (!apps.isDone())
				|| (!documents.isDone()));
		pool.shutdown();

		List<String> results = system.join();
		System.out.printf("System: %d files found.\n", results.size());
		results = apps.join();
		System.out.printf("Apps: %d files found.\n", results.size());
		results = documents.join();
		System.out.printf("Documents: %d files found.\n", results.size());
	}

	public static class FolderProcessor extends RecursiveTask<List<String>> {
		private static final long serialVersionUID = 1L;
		private final String path;
		private final String extension;

		public FolderProcessor(String path, String extension) {
			super();
			this.path = path;
			this.extension = extension;
		}

		@Override
		protected List<String> compute() {
			List<String> list = Lists.newArrayList();
			List<FolderProcessor> processors = Lists.newArrayList();

			File file = new File(path);
			File[] content = file.listFiles();

			if (content != null) {
				for (File f : content) {
					if (f.isDirectory()) {
						FolderProcessor processor = new FolderProcessor(
								f.getAbsolutePath(), extension);
						processors.add(processor);
						processor.fork();
					} else if (checkFile(f.getName())) {
						list.add(f.getAbsolutePath());
					}
				}
			}
			addResultsFromTasks(processors, list);
			return list;
		}

		private void addResultsFromTasks(List<FolderProcessor> processors,
				List<String> list) {
			for (FolderProcessor processor : processors) {
				list.addAll(processor.join());
			}
		}

		private boolean checkFile(String name) {
			return name.endsWith(extension);
		}
	}

}