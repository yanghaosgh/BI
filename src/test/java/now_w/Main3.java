package now_w;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.mxyc.common.JsonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main3 {
	private Date date = new Date(0);

	public static void main(String[] args) throws IOException {

		List<String> list = FileUtils.readLines(new File("e:/tmp/pay_callback_log_2016-02-29.log"));
		int c = 0;
		for (String value : list) {

			JSONObject json = JSONObject.fromObject(value.toString());
			StringBuilder sb = new StringBuilder();

			System.out.println(c++);
			System.out.println(json.get("events") instanceof JSONArray);
			
			JSONObject events = null;
			try {
				events = json.getJSONObject("events");
			} catch (Exception e) {
			}

			String banner_id = JsonUtils.getValue(events, "banner_id", "");
			
		}

	}
}
