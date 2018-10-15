package edu.xmu.test.spring.batch.task;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class NoActionTasklet implements Tasklet {
	private static final Logger logger = Logger.getLogger(NoActionTasklet.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("NoActionTasklet.execute()");
		return RepeatStatus.FINISHED;
	}
}
