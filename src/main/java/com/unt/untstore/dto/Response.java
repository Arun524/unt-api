package com.unt.untstore.dto;

import lombok.Getter;
import lombok.Setter;

public class Response<T> {

	@Getter
	@Setter
	private boolean success;
	@Getter
	@Setter
	private String message;
	@Getter
	@Setter
	private T body;
	@Getter
	@Setter
	private T data;

	public Response() {
		this.setSuccess(true);
	}

	public Response(String message, T body) {
		this.message = message;
		this.body = body;
		this.success = true;
	}

	public Response(T body) {
		this.body = body;
		this.success = true;
	}

	public Response(boolean success, T body) {
		this.success = success;
		this.body = body;
	}

	public Response(String message, T body, boolean success) {
		this.message = message;
		this.body = body;
		this.success = success;
	}

}
