package com.mxyc.bi.mr;

import java.io.IOException;
import java.util.Date;

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
import com.mxyc.common.JsonUtils;

import Ice.Communicator;
import TuiService.BannerIPrx;
import TuiService.BannerIPrxHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Filter4Pay {

	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration(), Filter4Pay.class.getSimpleName());
		job.setJarByClass(Filter4Pay.class);

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
		private Communicator ice = Ice.Util.initialize(new String[] {}, null);
		private BannerIPrx bannerIPrx = null;
		private Text text = new Text();
		private Date date = new Date(0);
		private JSONObject json = null;
		private String sep = "\001";

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, LongWritable, Text>.Context context) throws IOException, InterruptedException {
			try {
				json = JSONObject.fromObject(value.toString());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			StringBuilder sb = new StringBuilder();

			long ts = json.getLong("ts");
			date.setTime(ts * 1000);

			sb.append(json.getString("user_id") + sep);
			sb.append(json.getString("source_id") + sep);
			sb.append(json.getString("goods_id") + sep);
			sb.append(json.getString("order_id") + sep);
			sb.append(json.getString("pay_order_id") + sep);
			sb.append(json.getString("pay_count") + sep);
			sb.append(json.getString("pay_price") + sep);
			sb.append(json.getString("pay_amount") + sep);
			sb.append(CommonUtils.createDate(CommonUtils.date_format[2], date, 0, 0, 0) + sep);
			sb.append(json.getString("title").replace(sep, "") + sep);
			sb.append(json.getString("url_img") + sep);

			JSONObject events = null;
			try {
				events = json.getJSONObject("events");
			} catch (Exception e) {
				e.printStackTrace();
			}

			String banner_id = JsonUtils.getValue(events, "banner_id", "");

			sb.append(banner_id + sep);
			sb.append(JsonUtils.getValue(events, "thread_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "topic_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "tuan_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "prefecture_id", "") + sep);

			sb.append(JsonUtils.getValue(events, "brand_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "business_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "cate_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "designer_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "detail_id", "") + sep);
			sb.append(JsonUtils.getValue(events, "source", "") + sep);

			sb.append(JsonUtils.getValue(events, "tab_1", "") + sep);
			sb.append(JsonUtils.getValue(events, "tab_2", "") + sep);
			sb.append(JsonUtils.getValue(events, "tab_3", "") + sep);
			sb.append(JsonUtils.getValue(events, "tab_4", "") + sep);

			if (bannerIPrx == null) {
				bannerIPrx = BannerIPrxHelper.checkedCast(ice.stringToProxy("Banner:default -h 192.168.1.66 -p 23001"));
			}

			String bannerInfo = null;
			if (!banner_id.equals("")) {
				bannerInfo = bannerIPrx.GetInfo(new String[] { banner_id });
			}

			if (bannerInfo != null && !bannerInfo.equals("null")) {
				JSONArray jsonArray = JSONArray.fromObject(bannerInfo);
				JSONObject bannerJson = (JSONObject) jsonArray.get(0);
				sb.append(JsonUtils.getValue(bannerJson, "banner_duration", "0"));
			} else {
				sb.append("0");
			}

			sb.append(sep + JsonUtils.getValue(events, "utm_source", ""));
			sb.append(sep + JsonUtils.getValue(events, "position", ""));
			sb.append(sep + JsonUtils.getValue(events, "tab_id", ""));

			text.set(sb.toString());
			context.write(key, text);
		}

		@Override
		protected void cleanup(Mapper<LongWritable, Text, LongWritable, Text>.Context context) throws IOException, InterruptedException {
			if (bannerIPrx != null) {
				bannerIPrx = null;
				ice.destroy();
			}

			super.cleanup(context);
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
