package com.ge.apm;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.ge.apm.dto.AssetDataRecord;
import com.ge.apm.ingestor.beans.TagElement;
import com.ge.apm.transformer.TimeSeriesTransformer;
import com.ge.apm.writer.CustomRESTWriter;



@Configuration
@EnableBatchProcessing
//@PropertySource("classpath:./batch.properties")
public class BatchConfiguration implements ApplicationContextAware {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private ApplicationContext applicationContext;
    
    @Autowired
	private BatchProperties batchConfiguration;
 
    @Autowired
	private RestProperties restConfig;

    
    

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<AssetDataRecord> reader() throws IOException {
       
    	FlatFileItemReader reader = new FlatFileItemReader();
    	//reader.setLinesToSkip(1);
    	Resource fileResinput = applicationContext.getResource("file:"+batchConfiguration.getInputFile());
    	
    	reader.setResource(fileResinput);
    	    	
    	DefaultLineMapper<AssetDataRecord> lineMapper = new DefaultLineMapper<AssetDataRecord>();
    	
    	
    	String[] fieldMapping={"readTime","assetID","siteName","blockId","position","unitId","tagId","value"};
    	
    	
    	DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    	tokenizer.setNames(fieldMapping);
    	BeanWrapperFieldSetMapper<AssetDataRecord> fieldSetMapper = new BeanWrapperFieldSetMapper();
    	fieldSetMapper.setTargetType(AssetDataRecord.class);
    	
    	
    	lineMapper.setLineTokenizer(tokenizer);
    	lineMapper.setFieldSetMapper(fieldSetMapper);
    	
    	reader.setLineMapper(lineMapper);
    	
    	
    	
    	
        return reader;
    }

  

    public ItemWriter<TagElement> writer() {
    	CustomRESTWriter<TagElement> writer = new CustomRESTWriter<>(restConfig);
    	//NextEraFlatFileWriter<List<AssetDataRecord>> writer = new NextEraFlatFileWriter<>();
    	//Resource fileResout = applicationContext.getResource("file:"+batchConfiguration.getOutputFile());
    	//writer.setResource(fileResout);
    	
    	
    	return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
   public Job importUserJob(JobCompletionNotificationListener listener) throws IOException {
        return jobBuilderFactory.get("importUserJob")
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() throws IOException {
        return 
        		stepBuilderFactory.get("step1")
                .<AssetDataRecord,TagElement> chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]

	private TimeSeriesTransformer processor() {
		return new TimeSeriesTransformer();
		//return null;
	}



	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;
		
	}
}
