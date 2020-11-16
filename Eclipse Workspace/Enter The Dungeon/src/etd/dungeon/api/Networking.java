package etd.dungeon.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Networking {
	
	public static String						BaseUrl		= "https://api.scarvite.de/etd";
	
	// Ich wei�, das ich nen boolean in nen string umwandel und dann wieder zur�ck, aber ohne das gehts irgendwie nicht
	
	private final static CloseableHttpClient	httpClient	= HttpClients.createDefault();
	
	public static boolean validatekey(String key) {
		HttpGet request = new HttpGet(BaseUrl + "/validatekey?key=" + key);
		try(CloseableHttpResponse response = httpClient.execute(request)) {
			System.out.println(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);
			if(entity != null) {
				String result;
				try {
					System.out.println("hier");
					result = EntityUtils.toString(entity);
					if(Boolean.parseBoolean(result) == true) {
						// In Speicherfile activated = true setzen
					}
					return Boolean.parseBoolean(result);
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
		return false;
	}
	
	public static boolean updateLeaderboard(String username, int score) {
		System.out.println("hier");
		HttpPost post = new HttpPost("https://api.scarvite.de/etd/updateleaderboard");
		/*
		 * List<NameValuePair> urlParameters = new ArrayList<>(); urlParameters.add(new BasicNameValuePair("username", username)); urlParameters.add(new BasicNameValuePair("score",
		 * Integer.toString(score))); urlParameters.add(new BasicNameValuePair("token", "33317-200-10-5-5-992")); try { post.setEntity(new UrlEncodedFormEntity(urlParameters)); } catch
		 * (UnsupportedEncodingException e2) { // TODO Auto-generated catch block e2.printStackTrace(); }
		 */
		try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
			System.out.println(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);
			if(entity != null) {
				try {
					JSONArray jsonarray = new JSONArray(EntityUtils.toString(entity));
					boolean result = jsonarray.getBoolean(0);
					if(result == false) {
						Popup.error(jsonarray.getString(1), "Error");
						return result;
					}
					return result;
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
		return false;
	}
	
	public static JSONArray getLeaderboard() {
		HttpGet request = new HttpGet(BaseUrl + "/getleaderboard");
		try(CloseableHttpResponse response = httpClient.execute(request)) {
			System.out.println(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);
			if(entity != null) {
				try {
					JSONArray jsonarray = new JSONArray(EntityUtils.toString(entity));
					System.out.println(jsonarray.getJSONObject(0));
					return jsonarray;
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
		return null;
	}
	
	public static String addUser(String Email, String Username, String Password) {
		String HashedPassword = " ";
		try {
			HashedPassword = hashPW(Password);
		}
		catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			System.exit(2);
		}
		if(HashedPassword == " ") {
			System.exit(2);
		}
		HttpPost post = new HttpPost(BaseUrl + "/addUser");
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("username", Username));
		urlParameters.add(new BasicNameValuePair("password", HashedPassword));
		urlParameters.add(new BasicNameValuePair("email", Email));
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		}
		catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
			System.out.println(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);
			if(entity != null) {
				try {
					JSONArray jsonarray = new JSONArray(EntityUtils.toString(entity));
					String answ = jsonarray.getString(1);
					System.out.println(answ);
					return answ;
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
		return null;
	}
	
	public static JSONObject login(String Email, String Password) {
		String HashedPassword = " ";
		try {
			HashedPassword = hashPW(Password);
		}
		catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(HashedPassword == " ") {
			System.exit(2);
		}
		HttpPost post = new HttpPost(BaseUrl + "/login");
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("email", Email));
		urlParameters.add(new BasicNameValuePair("password", HashedPassword));
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		}
		catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try(CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
			System.out.println(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);
			if(entity != null) {
				try {
					// jsonarray[0] = entweder true oder false, bei true is an [1] ein object bei false null
					JSONArray jsonarray = new JSONArray(EntityUtils.toString(entity));
					boolean found = jsonarray.getBoolean(0);
					if(found == true) {
						return jsonarray.getJSONObject(1);
					}
					else {
						String message = jsonarray.getString(1);
						Popup.error(message, "Error");
						return null;
					}
					// System.out.println(jsonarray.getJSONObject(1));
				}
				catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(1);
		return null;
	}
	
	public static String GenerateToken(int Score) {
		String token = " ";
		Random random = new Random();
		
		boolean fertig = true;
		
		while(fertig) {
			
			int value = random.nextInt(17);
			int value1 = random.nextInt(17);
			int value2 = random.nextInt(17);
			int value3 = random.nextInt(17);
			int value4 = random.nextInt(17);
			
			if(value + value1 + value2 + value3 + value4 == 17) {
				fertig = false;
				token = token + Integer.toString(value);
				token = token + Integer.toString(value1);
				token = token + Integer.toString(value2);
				token = token + Integer.toString(value3);
				token = token + Integer.toString(value4);
				token = token + "-";
				System.out.println(token);
				
			}
			
		}
		
		return token;
	}
	
	protected static String Tokengen(int Ziel, int lenght, int max) {
		
		int a = 0;
		while(a != Ziel) {
			Random random = new Random();
			int[] value = new int[lenght];
			
			for(int i = 0; i < value.length; i++) {
				value[i] = random.nextInt(max);
			}
			String strArray[] = new String[value.length];
			
			for(int i = 0; i < value.length; i++)
				strArray[i] = String.valueOf(value[i]);
			a = 0;
			for(int i = 0; i < value.length; i++) {
				a = a + value[i];
				
			}
			
			if(a == Ziel) {
				return Arrays.toString(strArray);
			}
		}
		return "Error";
	}
	
	private static String hashPW(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();
		String hashedPw = String.format("%064x", new BigInteger(1, digest));
		System.out.println(hashedPw);
		return hashedPw;
	}
	
}