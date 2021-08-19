package com.rrm14.cliente.escola.matrizcurricular.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Response<T> extends RepresentationModel<Response<T>> {
	
	public Response() {
		this.timeStamp = System.currentTimeMillis();
	}
	
	private int statusCode;
	private T data;
	private long timeStamp;
}
