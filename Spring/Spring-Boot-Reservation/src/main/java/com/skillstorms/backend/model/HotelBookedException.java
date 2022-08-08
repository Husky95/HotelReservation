package com.skillstorms.backend.model;

public class HotelBookedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5547519278033065124L;
	
	public HotelBookedException() {
		super();
	}
	
	public HotelBookedException(String message) {
		super(message);
	}

}
