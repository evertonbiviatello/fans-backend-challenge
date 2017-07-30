package com.fans.challenge.domain.api;

public class Response {

	public ResponseCode status;

	public Response(ResponseCode status) {
		this.status = status;
	}

	public enum ResponseCode {
		STARTED, RUNNING, STOPPED
	}

}
