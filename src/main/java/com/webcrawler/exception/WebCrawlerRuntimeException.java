package com.webcrawler.exception;

public class WebCrawlerRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8329918596910755801L;

	public WebCrawlerRuntimeException() {
		super();
	}

	public WebCrawlerRuntimeException(String message, Throwable cause, boolean enableSuppression,
									  boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WebCrawlerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebCrawlerRuntimeException(String message) {
		super(message);
	}

	public WebCrawlerRuntimeException(Throwable cause) {
		super(cause);
	}

}
