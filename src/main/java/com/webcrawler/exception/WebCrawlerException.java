package com.webcrawler.exception;

public class WebCrawlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3386079739292109462L;

	public WebCrawlerException() {
		super();
	}

	public WebCrawlerException(String message, Throwable cause, boolean enableSuppression,
							   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WebCrawlerException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebCrawlerException(String message) {
		super(message);
	}

	public WebCrawlerException(Throwable cause) {
		super(cause);
	}

}
