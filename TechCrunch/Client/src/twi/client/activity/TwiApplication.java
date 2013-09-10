package twi.client.activity;

import gueei.binding.Binder;
import android.app.Application;
import android.provider.Settings.Secure;

public class TwiApplication extends Application {

	@Override
	public void onCreate() {

		super.onCreate();
		deviceId = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);
		Binder.init(this);
	}

	private String deviceId;

	/**
	 * Get Device Unique Id
	 * 
	 * @return device id(string)
	 */
	public String getDeviceId() {
		return deviceId;
	}

	private int userIdentifier=111;

	/**
	 * @return Get User identifier Code
	 */
	public int getUserIdentifier() {
		return userIdentifier;
	}

	/**
	 * @param Set
	 *            User Identifier
	 */
	public void setUserIdentifier(int userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	private String profilePic;

	/**
	 * 
	 * @return profile picture url
	 */
	public String getUserProfilePic() {
		return profilePic;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setUserProfilePic(String value) {
		profilePic = value;
	}
	
	private String userName;
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String value)
	{
		userName = value;
	}

}
