package com.busy.doctor;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class PostWithObject {

	static TimerTask splashTask;
	static ProgressDialog progressDialog;

	public static void getWebData(final String url,
			final List<NameValuePair> header, final Object params,
			final Handler callback, final Context act) throws IOException {

		if (!Utils.isNetAvailable(act)) {

			progressDialog = ProgressDialog.show(act, "Please Wait",
					"Loading...");
			final Message msg = Message.obtain();

			splashTask = new TimerTask() {

				public void run() {
					try {

						msg.what = 2;
						HttpPost httpPost = new HttpPost(url);
						HttpParams httpParameters = new BasicHttpParams();
						
						if (!header.isEmpty())

						{

							for (int i = 0; i < header.size(); i++) {

								httpPost.addHeader(header.get(i).getName(),
										header.get(i).getValue());

							}

						}

						DefaultHttpClient httpClient = new DefaultHttpClient();

						// passes the results to a string builder/entity
						StringEntity se = new StringEntity(params.toString());

						// sets the post request as the resulting string
						httpPost.setEntity(se);
						// sets a request header so the page receving the
						// request
						// will know what to do with it
						httpPost.setHeader("Accept", "application/json");
						httpPost.setHeader("Content-type", "application/json");

						// httpPost.setHeader("Accept-Language",
						// "en-US,en;q=0.5");
						// httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:16.0) Gecko/20100101 Firefox/16.0");
						//

						// httpPost.setEntity(new UrlEncodedFormEntity(params));

						HttpResponse response = httpClient.execute(httpPost);
						int httpRes = response.getStatusLine().getStatusCode();

						if (httpRes == 200) {

							HttpEntity httpEntity = response.getEntity();

							String con = EntityUtils.toString(httpEntity);
							 Log.d("Result", con);
							// System.out.println(con);
							// Utils.writeFileToInternalStorage(act, con);

							Bundle b = new Bundle();
							b.putString("text", con);
							msg.setData(b);
							callback.sendMessage(msg);
						}

						else {

							Log.d("Text", httpRes + "");
							msg.what = 1;
							callback.sendMessage(msg);
						}

					} catch (ConnectTimeoutException e) {
						msg.what = 1;
						callback.sendMessage(msg);
					} catch (SocketTimeoutException e) {
						msg.what = 1;
						callback.sendMessage(msg);
					} catch (ParseException e) {
						msg.what = 3;
						callback.sendMessage(msg);

					} catch (IOException e) {

						msg.what = 3;
						callback.sendMessage(msg);

					} finally {

						splashTask.cancel();
						progressDialog.dismiss();

					}

				}
			};
			// t.start();
			new Timer().schedule(splashTask, 0);
		} else {
			Toast.makeText(act, "No network", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
