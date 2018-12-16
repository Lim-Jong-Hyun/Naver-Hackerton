package com.example.demo.member.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.naver.api.security.client.MACManager;

@Service("apiCall")
public class ApiCall {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiCall.class);
	private static final String CAPTCHA_LIST_API = "http://dev.apis.naver.com/panoramaCaptcha/panorama/captcha/";
	private static final String CAPTCHA_IMAGE_API = "http://dev.apis.naver.com/panoramaCaptcha/panorama/thumbnail/";
	
	static {
		try {
			MACManager.initialize();
			Future<Long> future = MACManager.syncWithServerTimeByHttpAsync(30000, 30000);
			if (future != null) {
				future.get();
			}
		} catch (Exception e) {
			logger.error("MACManager initialize fail. " , e);
		}

	}
	
	private ApiCall() {
		
	}
	
	private static URI uriBuild(String httpUrl, Map<String, String> parameterMap) {
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    for (Entry<String, String> entry : parameterMap.entrySet()) {
	        params.add(entry.getKey(), entry.getValue());
	    }
		return UriComponentsBuilder.fromHttpUrl(httpUrl).queryParams(params).build().toUri();
	}
	
	private static String getString(String encryptedUrl) throws IOException {
		URL url = new URL(encryptedUrl);
		url.openConnection().setConnectTimeout(1000);
		url.openConnection().setReadTimeout(1000);

		try (InputStream is = url.openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8.name()))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}
	
	public static String getImageRandomList(String type, int limit) throws Exception {
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("limit", String.valueOf(limit));
		
		URI uri = uriBuild(CAPTCHA_LIST_API + type, parameterMap);
		String encryptedUrl = MACManager.getEncryptUrl(uri.toString());
		
		return getString(encryptedUrl);
	}
	
	public static BufferedImage getImage(int bzstNo, String panoTypeCd, int width, int height) throws Exception {
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("width", String.valueOf(width));
		parameterMap.put("height", String.valueOf(height));
		
		URI uri = uriBuild(CAPTCHA_IMAGE_API + String.valueOf(bzstNo) + "/" + panoTypeCd, parameterMap);
		String encryptedUrl = MACManager.getEncryptUrl(uri.toString());

		URL url = new URL(encryptedUrl);
		url.openConnection().setConnectTimeout(1000);
		url.openConnection().setReadTimeout(3000);
		BufferedImage img = null;
		
		try (InputStream in = url.openStream()) {
			img = ImageIO.read(in);
		}
		return img;
	}
	
	public static String getURL(int bzstNo, String panoTypeCd, int width, int height) throws Exception{
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("width", String.valueOf(width));
		parameterMap.put("height", String.valueOf(height));
		
		URI uri = uriBuild(CAPTCHA_IMAGE_API + String.valueOf(bzstNo) + "/" + panoTypeCd, parameterMap);
		String encryptedUrl = MACManager.getEncryptUrl(uri.toString());
		
		return encryptedUrl;
	}

}
