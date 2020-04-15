package MainPackege.Config;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import MainPackege.model.Dinosaurio;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Bean
	public Job job(
			JobBuilderFactory jobBuilderFactory, 
			StepBuilderFactory stepBuilderFactory, 
			ItemReader<Dinosaurio> itemReader,
			ItemProcessor<Dinosaurio,Dinosaurio> itemProcessor,
			ItemWriter<Dinosaurio> itemWriter
			
			) {
		
		Step step = stepBuilderFactory.get("Step1")
				.<Dinosaurio, Dinosaurio>chunk(10)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		 return jobBuilderFactory.get("Dino-Load")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
	}

	@Bean
	public FlatFileItemReader<Dinosaurio> itemReader(@Value("${input}") Resource resource){
		
		FlatFileItemReader<Dinosaurio> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		
		return flatFileItemReader;   
		
	}

	@Bean
	public LineMapper<Dinosaurio> lineMapper() {
		
		DefaultLineMapper<Dinosaurio> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames(new String[] {"id","name","description","category"});
		
		BeanWrapperFieldSetMapper<Dinosaurio> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Dinosaurio.class);
		
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		return defaultLineMapper;
	}
}