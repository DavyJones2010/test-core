package edu.xmu.test.spring.batch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchApp {
	private static final Logger logger = Logger.getLogger(BatchApp.class);

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		//String[] springConfig = { "classpath:batch/context.xml" };
        //
		//ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
		//JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		//Job job = (Job) context.getBean("helloWorldJob");
        //
		//jobLauncher.run(job, new JobParameters());
		//logger.info("Job Done");
		//((AbstractApplicationContext) context).close();
	}
}
