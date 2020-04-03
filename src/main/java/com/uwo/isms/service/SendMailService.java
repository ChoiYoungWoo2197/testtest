package com.uwo.isms.service;

public interface SendMailService {

	public void sendMail(String key, String eventCode, String title, String contents, String userKey);
}
