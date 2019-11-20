package com.seer.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

public class MySessionListener extends SessionListenerAdapter {

	public MySessionListener() {
	}
	
	@Override
	public void onStart(Session session) {
		
	}
	
	@Override
	public void onStop(Session session) {
		
	}
	
	@Override
	public void onExpiration(Session session) {
		
	}
}
