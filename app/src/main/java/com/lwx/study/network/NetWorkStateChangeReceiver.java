package com.lwx.study.network;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 
 * @ClassName：NetWorkStateChangeReceiver
 * @Description：当手机的网络状态发生改变时，会发送广播提醒
 * @Author：KevinLee
 * @Date：2015-8-31
 * @Time:下午3:31:19
 * @备注：需要添加权限<uses-permission 
 *                            android:name="android.permission.ACCESS_NETWORK_STATE"
 *                            />
 */

public class NetWorkStateChangeReceiver extends BroadcastReceiver {

	// 网络状态变化前的状态，默认为无网络
	NetState preState = NetState.NET_NO;
	// 网络状态变化后的状态，默认为无网络
	NetState nowState = NetState.NET_NO;

	@Override
	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		// 如果网络状态改变了
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			// 获得网络连接服务
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = manager.getActiveNetworkInfo();
			if (ni != null && ni.isConnectedOrConnecting()) {
				int type = ni.getType();
				switch (type) {
				case ConnectivityManager.TYPE_WIFI:
					Toast.makeText(context, "Wifi已连接", Toast.LENGTH_LONG)
							.show();
					nowState = NetState.NET_WIFI;
					break;
				case ConnectivityManager.TYPE_MOBILE:
					switch (ni.getSubtype()) {
					case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
					case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
					case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN:
						Toast.makeText(context, "2G网络已连接", Toast.LENGTH_LONG)
								.show();
						nowState = NetState.NET_2G;
						break;
					case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
					case TelephonyManager.NETWORK_TYPE_HSPAP:
						Toast.makeText(context, "3G网络已连接", Toast.LENGTH_LONG)
								.show();
						nowState = NetState.NET_3G;
						break;
					case TelephonyManager.NETWORK_TYPE_LTE:
						Toast.makeText(context, "4G网络已连接", Toast.LENGTH_LONG)
								.show();
						nowState = NetState.NET_4G;
						break;
					default:
						Toast.makeText(context, "未知网络已连接", Toast.LENGTH_LONG)
								.show();
						nowState = NetState.NET_UNKNOWN;
					}
				default:
					break;
				}
			} else {
				Toast.makeText(context, "无网络已连接", Toast.LENGTH_LONG).show();
				nowState = NetState.NET_NO;
			}
			/**
			 * 如果网络状态变化前的状态是Wifi，变化后的状态是手机网络，弹出对话框提示
			 */
			if (preState.equals(NetState.NET_WIFI)
					&& (nowState.equals(NetState.NET_2G)
							|| nowState.equals(NetState.NET_3G) || nowState
								.equals(NetState.NET_4G))) {
				AlertDialog.Builder ab = new AlertDialog.Builder(context);
				ab.setMessage("Wifi网络连接断开，请连接...");
				ab.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								NetWorkUtil.openSetting(context);
								dialog.dismiss();
							}
						});
				ab.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				ab.create().show();
			} 
			/**
			 * 如果网络状态变化前的状态是Wifi、手机网络，变化后的状态是无网络，弹出对话框提示
			 */
			else if ((preState.equals(NetState.NET_WIFI)
					|| preState.equals(NetState.NET_2G)
					|| preState.equals(NetState.NET_3G) || preState
						.equals(NetState.NET_4G))
					&& nowState.equals(NetState.NET_NO)) {
				AlertDialog.Builder ab = new AlertDialog.Builder(context);
				ab.setMessage("网络连接断开，请检查网络");
				ab.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								NetWorkUtil.openSetting(context);
								dialog.dismiss();
							}
						});
				ab.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				ab.create().show();
			} else {
				preState = nowState;
			}
		}
	}
}
