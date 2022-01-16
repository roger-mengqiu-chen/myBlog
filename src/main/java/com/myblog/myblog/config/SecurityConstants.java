package com.myblog.myblog.config;

public class SecurityConstants {

	public static final String SECRET = "oursecretkey";
    public static final long EXPIRATION_TIME = 864_00_000; // 1day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
