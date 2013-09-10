package twi.client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class RestBaseClass {

	private static final String BASE_URL = "";

	private static AsyncHttpClient objAsyncHttpClient = new AsyncHttpClient();

	public static final String GetRestURL() {
		return "http://10.11.0.133:9000";
	}

	public enum Status {
		Success, Fail
	}

	public enum RequestMethod {
		GET, POST
	}

	public static final String getFullURL(String relativePath) {
		return BASE_URL + relativePath;
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		objAsyncHttpClient.get(getFullURL(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		objAsyncHttpClient.post(getFullURL(url), params, responseHandler);
	}

	public static void put(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		objAsyncHttpClient.put(getFullURL(url), params, responseHandler);
	}

	private static final String convertStreamToString(InputStream inStream)
			throws IOException {
		if (inStream != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				inStream.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	public static final String ExecuteRequest(RequestMethod method,
			String content, String JSONIdentifier) throws Exception {

		HttpClient httpclient = new DefaultHttpClient();

		StringEntity se = new StringEntity(content);
		se.setContentEncoding("UTF-8");
		se.setContentType("application/json");

		String returnValue = "";

		switch (method) {
		case GET: {

			StringBuilder uri = new StringBuilder();
			uri.append(GetRestURL());
			uri.append("/");
			uri.append(content);

			HttpGet httpGet = new HttpGet(uri.toString());
			// HttpGet httpGet = new HttpGet(GetRestURL());
			try {
				// httpGet.setHeader("Content-Type", "application/json");
				// httpGet.setHeader("inputJsonObj", se.toString());
				HttpResponse getResponse = httpclient.execute(httpGet);
				HttpEntity getEntity = getResponse.getEntity();

				InputStream is = getEntity.getContent();
				returnValue = convertStreamToString(is);
				// EntityUtils.consume(entity1);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			finally {
				httpGet.abort();
			}
			break;
		}
		case POST: {
			HttpURLConnection conn = null;
			try{
			URL url = new URL(GetRestURL() + "/"
					+ content);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");



			
			OutputStream os = conn.getOutputStream();
			os.write(content.getBytes());
			os.flush();
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			//conn.disconnect();
			
			
			

		//	HttpPost httpPost = new HttpPost(GetRestURL() + "/"
		//			+ content);
			
		/**		httpPost.setHeader("Content-Type", "application/json");

				httpPost.setEntity(se);
				HttpResponse postResponse = httpclient.execute(httpPost);
				HttpEntity postEntity = postResponse.getEntity();
				InputStream is = postEntity.getContent();
				returnValue = convertStreamToString(is);*/
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				// Log.e("UnsupportedEncodingException", e.toString());
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				// Log.e("ClientProtocolException", e.toString());
			} catch (Exception e) {
				e.printStackTrace();
				// Log.e("IOException", e.toString());
			} finally {
				conn.disconnect();
			}

			break;

		}
		}
		return returnValue;
	}
}

