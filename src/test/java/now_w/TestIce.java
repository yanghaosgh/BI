package now_w;

import Ice.Communicator;
import Ice.ObjectPrx;
import TuiService.BannerIPrx;
import TuiService.BannerIPrxHelper;

public class TestIce {
	public static void main(String[] args) {
		Communicator ice = Ice.Util.initialize(new String[]{}, null);
		ObjectPrx proxy = ice.stringToProxy("Banner:default -h 192.168.1.66 -p 23001");

		BannerIPrx banner = BannerIPrxHelper.checkedCast(proxy);
		String info = banner.GetInfo(new String[] { "88", "900000" });
		System.out.println(info == null ? "-" : info);

		banner = null;
		ice.destroy();
	}
}
