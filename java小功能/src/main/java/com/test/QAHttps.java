package com.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;

/**
 *
 */
@Slf4j
public class QAHttps {
	//todo 这个是qa环境的https证书的密码，线上环境请使用线上的
	public static final String HTTP_PASSWORD = "$Zu6#34PH?";
	//申请的appkey
	public static final String CLIENT_ID = "AvIBuOzzkIUNqevScpqc";
	//申请的appecret
	public static final String CLIENT_SECRET = "b82e6ae6924f4d5fbbcecf87399b37fdwVy0e6lR";
	//授权模式 客户端模式：client_credential ；用户授权模式 authorization_code
	public static final String GRANT_TYPE = "authorization_code";
	//qa环境获取accessToken
	public static final String TOKEN_URL = "https://qa-internal.ymm56.com/open_platform/oauth2/access_token";

	public static void main(String[] args) {
		TransferRestTemplate();
	}
	@SneakyThrows
	public static String TransferRestTemplate() {
		//读取p12证书
		URL p12 = QAHttps.class.getResource("/client.p12");
		FileInputStream instream = new FileInputStream(p12.getFile());
		//获得keyStore
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(instream, HTTP_PASSWORD.toCharArray());
		// 信任自己的CA和所有自签名证书
		SSLContext sslcontext = SSLContextBuilder.create().loadKeyMaterial(keyStore, HTTP_PASSWORD.toCharArray()).build();
		// 只允许TLSv1协议
		HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, hostnameVerifier);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		LinkedMultiValueMap<String, String> parmar = new LinkedMultiValueMap<>();
		parmar.add("clientId", CLIENT_ID);
		parmar.add("clientSecret", CLIENT_SECRET);
		parmar.add("grantType", GRANT_TYPE);
		parmar.add("code", "code");
		HttpEntity<LinkedMultiValueMap<String, String>> requestEntity = new HttpEntity(parmar, requestHeaders);
		ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requestEntity, String.class);
		String sttr = response.getBody();
		System.out.println(sttr);
		return sttr;

	}
}

