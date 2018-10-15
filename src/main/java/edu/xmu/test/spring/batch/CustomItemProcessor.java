package edu.xmu.test.spring.batch;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;

import edu.xmu.test.spring.batch.model.Report;

public class CustomItemProcessor implements ItemProcessor<Report, Report>, StepExecutionListener, ChunkListener, ItemReadListener<Report>, ItemProcessListener<Report, Report>,
		ItemWriteListener<Report> {
	private static final Logger logger = Logger.getLogger(CustomItemProcessor.class);

	/**
	 * If null is returned, this item would not be passed to item writer
	 */
	@Override
	public Report process(Report item) throws Exception {
		logger.info("Processing..." + item);
		if ("Yang".equals(item.getStaffName())) {
			logger.info("Yang is filtered");
			return null;
		}
		return item;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("beforeStep");
	}

	/**
	 * ExitStatus is different from BatchStatus
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.info("afterStep");
		return new ExitStatus("ERROR");
	}

	@Override
	public void beforeChunk(ChunkContext context) {
		logger.info("beforeChunk");
	}

	/**
	 * It is called when chunk is committed, and is not called when rollback
	 * happens
	 */
	@Override
	public void afterChunk(ChunkContext context) {
		logger.info("afterChunk");
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		logger.info("afterChunkError");
	}

	@Override
	public void beforeProcess(Report item) {
		logger.info("beforeProcess");
	}

	@Override
	public void afterProcess(Report item, Report result) {
		logger.info("afterProcess, item: " + item + ", result: " + result);
	}

	@Override
	public void onProcessError(Report item, Exception e) {
		logger.info("onProcessError");
	}

	@Override
	public void beforeRead() {
		logger.info("beforeRead");
	}

	@Override
	public void afterRead(Report item) {
		logger.info("afterRead, item: " + item);
	}

	@Override
	public void onReadError(Exception ex) {
		logger.info("onReadError");
	}

	@Override
	public void beforeWrite(List<? extends Report> items) {
		logger.info("beforeWrite, items: " + items);
	}

	@Override
	public void afterWrite(List<? extends Report> items) {
		logger.info("afterWrite, items: " + items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Report> items) {
		logger.info("onWriteError");
	}

}