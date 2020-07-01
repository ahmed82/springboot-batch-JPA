package com.rbs.licensemanagmentvendorbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.rbs.licensemanagmentvendorbatch.model.Customer;

@Configuration
@EnableBatchProcessing
//@ConfigurationProperties(prefix = "mssqldb1")
public class BatchConfiguration {

	
	//@Autowired
	//public JobBuilderFactory jobBuilderFactory;
	
	//@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	//@Autowired
	public DataSource dataSource;
	
	
	//@Value("${db1.mssql.user}")
    private String db1User;
	
	//@Value("${db1.mssql.pass}")
    private String db1Pass;

    //@Value("${db1.mssql.url}")
    private String db1Url;
	
	//@Bean
	public DataSource dataSource() {
		
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://10.129.13.50;databaseName=LEGAL-F");
		dataSource.setUsername("webuser");
		dataSource.setPassword("www2000");
		return dataSource;
	}
	/*
	//@Bean
	public JdbcCursorItemReader<User> reader(){
		JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<User>();
		reader.setDataSource(dataSource);
		reader.setSql("Select * From dbo.COMPLMGMT_st_ASSOCIATES");
		reader.setRowMapper(new UserRowMapper());
		return reader;
	}
	
	public UserItemProcessor  processor() {
		return new UserItemProcessor();
		
	}
	*/
	//@Bean
	public FlatFileItemWriter<User> writer(){
		FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
		writer.setResource(new ClassPathResource("user.csv"));
		writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
			setDelimiter(",");
			setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
				setNames(new String[] { "id", "firstName", "lastName","birthdate"});
			}});
		}});
		
		return writer;
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	
	/*
	 * pass the jobBuilderFactory in the method constructor  and disable the Autowired 
	 */
	@Bean
	public Job exportCustomerJob(JobBuilderFactory jobBuilderFactory) {
		return jobBuilderFactory.get("exportCustomerJob")
				.incrementer(new RunIdIncrementer())
				//.flow(step1())
				.start(step1())
				//.end()
				.build();
		
	}
	
	@Bean
	public FlatFileItemReader<Customer> flatFileItemReader(){
		FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new ClassPathResource("customer.csv"));
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return null;
	}
	private LineMapper<Customer> lineMapper() {
		DefaultLineMapper<Customer>	defaultLineMappe= new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setStrict(false);
		tokenizer.setNames(new String[] {"id","firstName","lastName","birthdate"});
		defaultLineMappe.setLineTokenizer(tokenizer);
return null;
	}

}
