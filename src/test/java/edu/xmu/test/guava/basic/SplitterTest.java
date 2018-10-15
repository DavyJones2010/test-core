package edu.xmu.test.guava.basic;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

public class SplitterTest {

	@Test
	public void onTest() {
		String str = "Name:Davy;Age:24;Gender:Male";

		List<String> strList = Splitter.on(';').splitToList(str);
		System.out.println(strList);
	}

	@Test
	public void omitEmptyTest() {
		String str = "Name:Davy;Age:24;Gender:Male; ;;";

		List<String> strList = Splitter.on(';').omitEmptyStrings().trimResults().splitToList(str);
		System.out.println(strList);
	}

	@Test
	public void withKeyValueSeparatorTest() {
		String str = "Name:Davy;Age:24;Gender:Male; ;;";
		Map<String, String> map = Splitter.on(';').omitEmptyStrings().trimResults().withKeyValueSeparator(':').split(str);

		System.out.println(map);
	}

	@Test
	public void splitTest() {
		String str = "Name = Davy; Age = 24;; ; Gender = Male";
		Map<String, String> expectedMap = Maps.newLinkedHashMap();
		expectedMap.put("Name", "Davy");
		expectedMap.put("Age", "24");
		expectedMap.put("Gender", "Male");
		Map<String, String> generatedMap = Splitter.on(';').trimResults().omitEmptyStrings().withKeyValueSeparator(Splitter.on("=").trimResults()).split(str);
		assertEquals(expectedMap, generatedMap);
	}

	@Test
	public void splitTest2() {
		String str = "aggregate9QChange=0|aggregate9QChangePercent=1|itemName=FX|templateName=14A Summary|scenario=FRB - SAMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=2,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=FX|templateName=14A Summary|scenario=FRB - AMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=2,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Other Credit|templateName=14A Summary|scenario=FRB - SAMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=6,aggregate9QChange=0|itemName=Private Equity|scenario=FRB - SAMS|templateFrequency=A|submissionPeriod=1/1/2014|aggregate9QChangePercent=1|tab=Trading|item=7|explanationOfChange=11111111|templateName=14A Summary|ccarOrDfast=CCAR|categoryFor14A=Trading MTM Losses|classificationOfChange=Methodology,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Rates|templateName=14A Summary|scenario=FRB - SAMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=3,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Equity|templateName=14A Summary|scenario=FRB - SAMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=1,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Other Credit|templateName=14A Summary|scenario=FRB - AMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=6,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Commodities|templateName=14A Summary|scenario=FRB - SAMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=4,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Equity|templateName=14A Summary|scenario=FRB - AMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=1,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Rates|templateName=14A Summary|scenario=FRB - AMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=3,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Securitized Products|templateName=14A Summary|scenario=FRB - AMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=5,aggregate9QChange=0|aggregate9QChangePercent=1|itemName=Private Equity|templateName=14A Summary|scenario=FRB - AMS|ccarOrDfast=CCAR|templateFrequency=A|submissionPeriod=1/1/2014|categoryFor14A=Trading MTM Losses|tab=Trading|item=7";

		List<Map<String, String>> list = FluentIterable.from(Splitter.on(',').trimResults().split(str)).transform(new Function<String, Map<String, String>>() {
			public Map<String, String> apply(String input) {
				return Splitter.on('|').trimResults().withKeyValueSeparator('=').split(input);
			}
		}).toList();
		System.out.println(list);
	}
}
