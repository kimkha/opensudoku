package org.moire.opensudoku.utils;

import java.util.List;

import org.moire.opensudoku.BuildConfig;
import org.moire.opensudoku.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AndroidUtils {
	private static AdRequest adRequest;

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 *
	 * @param context The application's environment.
	 * @param action  The Intent action to check for availability.
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
				packageManager.queryIntentActivities(intent,
						PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public static void setThemeFromPreferences(Context context) {
		SharedPreferences gameSettings = PreferenceManager.getDefaultSharedPreferences(context);
		String theme = gameSettings.getString("theme", "default");
        switch (theme) {
            case "paperi":
                context.setTheme(R.style.Theme_PaperI);
                break;
            case "paperii":
                context.setTheme(R.style.Theme_PaperII);
                break;
            case "light":
                context.setTheme(R.style.Theme_Light);
                break;
            case "paperlighti":
                context.setTheme(R.style.Theme_PaperLightI);
                break;
            case "paperlightii":
                context.setTheme(R.style.Theme_PaperLightII);
                break;
            default:
                context.setTheme(R.style.Theme_Default);
                break;
        }
	}

	/**
	 * Returns version code of OpenSudoku.
	 *
	 * @return
	 */
	public static int getAppVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns version name of OpenSudoku.
	 *
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@NonNull
	private static AdRequest newAdRequest() {
		AdRequest.Builder builder = new AdRequest.Builder();
		if (BuildConfig.DEBUG) {
			builder.addTestDevice("14636C6C8763E3CF9546AFE871A60ABF")
					.addTestDevice("3B00023B2A7F00EECDECCF293D910ED5");
		}
		return builder.build();
	}

	public static void showAds(@NonNull AdView adView) {
		if (adRequest == null) {
			adRequest = newAdRequest();
		}
		adView.loadAd(adRequest);
	}
}
