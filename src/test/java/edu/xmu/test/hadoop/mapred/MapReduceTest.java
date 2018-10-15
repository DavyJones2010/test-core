package edu.xmu.test.hadoop.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by davywalker on 17/8/1.
 */
public class MapReduceTest {
    //Configuration conf = new Configuration();
    //MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    //ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
    //MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;
    //
    //@Before
    //public void setUp() {
    //
    //    //测试mapreduce
    //    SMSCDRMapper mapper = new SMSCDRMapper();
    //    SMSCDRReducer reducer = new SMSCDRReducer();
    //    mapDriver = MapDriver.newMapDriver(mapper);
    //    reduceDriver = ReduceDriver.newReduceDriver(reducer);
    //    mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    //
    //    //测试配置参数
    //    mapDriver.setConfiguration(conf);
    //    conf.set("myParameter1", "20");
    //    conf.set("myParameter2", "23");
    //}
    //
    //@Test
    //public void testMapper() throws IOException {
    //    mapDriver.withInput(new LongWritable(), new Text(
    //        "655209;1;796764372490213;804422938115889;6"));
    //    mapDriver.withOutput(new Text("6"), new IntWritable(1));
    //    mapDriver.runTest();
    //}
    //
    //@Test
    //public void testReducer() throws IOException {
    //    List<IntWritable> values = new ArrayList<IntWritable>();
    //    values.add(new IntWritable(1));
    //    values.add(new IntWritable(1));
    //    reduceDriver.withInput(new Text("6"), values);
    //    reduceDriver.withOutput(new Text("6"), new IntWritable(2));
    //    reduceDriver.runTest();
    //}
    //
    //@Test
    //public void testMapperReducer() throws IOException {
    //    mapReduceDriver.withInput(new LongWritable(), new Text(
    //        "655209;1;796764372490213;804422938115889;6"));
    //    mapReduceDriver.withOutput(new Text("6"), new IntWritable(1));
    //}

}
