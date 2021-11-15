package com.example.demo.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R {
	private int code;
	private Object data;
	private String msg;

	public static R success() {
		return new R(200, null, "");
	}


	public static R success(Object data) {
		return new R(200, data, "");
	}

	public static R fail(String msg) {
		return new R(200, null, msg);
	}
}
