package net.etfbl.api;

import redis.clients.jedis.Jedis;

public class CredentialService {
	
	private static String LOCAL_HOST = "localhost";
		
	static {
		try (Jedis jedis = new Jedis(LOCAL_HOST)) {
			jedis.set("user1", "password1");
			jedis.set("user2", "password2");
			jedis.set("user3", "password3");
			jedis.set("user4", "password4");
			jedis.set("user5", "password5");
			jedis.set("user6", "password6");
			jedis.set("user7", "password7");
			jedis.set("user8", "password8");
			jedis.set("user9", "password9");
			jedis.set("user10", "password10");
		}
	}
	
	public String getPassword(String username) {
		String password = null;
		try (Jedis jedis = new Jedis(LOCAL_HOST)) {
			password = jedis.get(username);
		}
		return password;
	}
	
	public boolean addCredentials(String user,String password) {
		try (Jedis jedis = new Jedis(LOCAL_HOST)) {
			if(jedis.get(user) != null) {
				return false;
			}else {
				jedis.set(user,password);
				return true;
			}
		}
	}
	
	public boolean deleteCredentials(String user) {
		try (Jedis jedis = new Jedis(LOCAL_HOST)) {
			if(jedis.get(user) == null) {
				return false;
			}else {
				jedis.del(user);
				return true;
			}
		}
	}
	
	public boolean updateCredentials(String user,String password) {
		try (Jedis jedis = new Jedis(LOCAL_HOST)) {
			if(jedis.get(user) == null) {
				return false;
			}else {
				jedis.set(user, password);
				return true;
			}
		}
	}
	
}
