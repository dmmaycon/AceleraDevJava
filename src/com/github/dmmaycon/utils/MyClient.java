package com.github.dmmaycon.utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyClient{

	
	public static String getRequest(String url) throws IOException {		
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

	public static String sendRequest(String url, File file) throws IOException {		
		OkHttpClient client = new OkHttpClient();
		
		RequestBody fileBody = RequestBody.create(MediaType.parse("application/json"), file);
		MultipartBody.Builder builder = new MultipartBody
				.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("answer", "answer.json", fileBody);
	
		RequestBody requestBody = builder.build();
				
		
		Request request = new Request
				.Builder()
				.url(url)
				.post(requestBody)
				.build();
		
		try (Response response = client.newCall(request).execute()) {
			System.out.println(response.code());
			return response.body().string();
		}

	}
	
}
