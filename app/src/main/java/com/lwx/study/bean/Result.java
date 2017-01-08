package com.lwx.study.bean;

/**
 * @Description:接收数据的单一
 * @author：GaomingShuo
 * @date：${DATA} 10:56
 */
public class Result<T> {
	private String reason; // 响应码
	private String error_code; // 响应码描述
	private T result;// 具体的业务有不同的返回参数


	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResultG{" +
				"reason='" + reason + '\'' +
				", error_code='" + error_code + '\'' +
				", result=" + result +
				'}';
	}

}
