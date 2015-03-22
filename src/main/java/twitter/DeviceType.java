package twitter;

public class DeviceType {
	public static final String ANDROID = "Android";
	public static final String IPAD = "iPad";
	public static final String WINDOWS_PHONE = "Windows Phone";
	public static final String BLACKBERRY = "BlackBerryr";
	public static final String WEB = "Twitter Web Client";
	public static final String[] identifiableDeviceTypes = {ANDROID,IPAD,WINDOWS_PHONE,BLACKBERRY,WEB};
	private String deviceType = "";
	public DeviceType(String source) {
		for(String deviceType: identifiableDeviceTypes) {
			if(source.contains(deviceType)) {
				this.deviceType = deviceType;
				break;
			}
		}
	}

	public static DeviceType fromSource(String source) {
		return new DeviceType(source);
	}
	public String toString() {
		return deviceType;
	}
}
