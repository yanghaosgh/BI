package now_w;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.mxyc.common.Utils4Parse;

public class Main {
	public static void main(String[] args) throws Exception {

		List<String> list = FileUtils.readLines(new File("e:/tmp/t.txt"), "utf-8");
		System.out.println(list.size());
		for (String s : list) {
			String[] ss = s.split("\t");
			// System.out.print("\"" + ss[0] + "\"," );
		}

		list = FileUtils.readLines(new File("e:/tmp/helix_backup_2016-02-21"), "utf-8");
		System.out.println(list.size());
		String tmp = null;
		for (String s : list) {
			List<Map<String, String>> parse = Utils4Parse.parseLog4default(s);
			for (Map<String, String> map : parse) {
				tmp = map.get("cdate");
				System.out.println(tmp);
			}
		}

		list = FileUtils.readLines(new File("e:/tmp/r"), "utf-8");
		for (String s : list) {
			System.out.println(s);
			System.out.println(s.split("\t").length);
		}

	}
}
