package com.webcrawler.exception;

import java.io.Serial;

public class WebCrawlerException extends Exception {

	@Serial
	private static final long serialVersionUID = 2867213076468308426L;

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
