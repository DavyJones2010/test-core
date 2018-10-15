package edu.xmu.test.hadoop.hdfs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * Created by davywalker on 17/7/26.
 */
public class HdfsSample {
    public static void main(String[] args) throws IOException {
        System.setProperty("hadoop.home.dir", "/Users/davywalker/Softwares/hadoop-2.7.3");
        System.setProperty("HADOOP_USER_NAME", "ec2-user");
        readFile("/tmp/test.txt");
    }

    private static void readFile(String filePath) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create("hdfs://ec2-54-244-79-246.us-west-2.compute.amazonaws.com:8020"),
            conf);
        Path srcPath = new Path(filePath);
        FSDataInputStream in = fs.open(srcPath);
        IOUtils.copyBytes(in, System.out, 4096, false);
        IOUtils.closeStream(in);
    }
}
