package com.mxyc.bi.mr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.mxyc.common.CommonUtils;
import com.mxyc.common.DataUtils;
import com.mxyc.common.Utils4Parse;

public class Filter4Logs {

	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration(), Filter4Logs.class.getSimpleName());
		job.setJarByClass(Filter4Logs.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		job.setInputFormatClass(TextInputFormat.class);

		job.setMapperClass(MyMapper.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);

		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		job.setNumReduceTasks(1);

		job.setOutputFormatClass(TextOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);

	}

	static class MyMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
		private Text text = new Text();
		private String tmp = null;

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, LongWritable, Text>.Context context) throws IOException, InterruptedException {
			List<Map<String, String>> list = Utils4Parse.parseLog4default(value.toString());
			if (!CommonUtils.listIsValid(list)) {
				return;
			}

			// 过滤不规则source_id eg:497e4a5e7d80c4dd5728b191195b7795
			for (Map<String, String> map : list) {
				tmp = map.get("source_id");
				if (tmp != null && tmp.length() > 20) {
					return;
				}

				text.set(DataUtils.toLoString(map, DataUtils.attrs_all));
				context.write(key, text);
			}

		}
	}

	static class MyReducer extends Reducer<LongWritable, Text, NullWritable, Text> {
		@Override
		protected void reduce(LongWritable k2, Iterable<Text> v2s, Reducer<LongWritable, Text, NullWritable, Text>.Context context) throws IOException, InterruptedException {
			for (Text text : v2s) {
				context.write(NullWritable.get(), text);
			}
		}
	}

}
