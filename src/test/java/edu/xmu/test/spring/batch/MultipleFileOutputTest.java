package edu.xmu.test.spring.batch;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.SimpleResourceSuffixCreator;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.google.common.collect.Lists;

import edu.xmu.test.spring.batch.model.Trade;

public class MultipleFileOutputTest {
}
