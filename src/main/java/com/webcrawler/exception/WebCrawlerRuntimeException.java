package com.webcrawler.exception;

import java.io.Serial;

public class WebCrawlerRuntimeException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -384745244998798241L;

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
