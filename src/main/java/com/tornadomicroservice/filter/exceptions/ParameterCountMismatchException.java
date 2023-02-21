package com.tornadomicroservice.filter.exceptions;

public class ParameterCountMismatchException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5135569336979479020L;

	public ParameterCountMismatchException() {
        super("The number of the parameter you passed does not coincide with the number declared on the called method.");
    }
	
	public ParameterCountMismatchException(Throwable err) {
		super("The number of the parameter you passed does not coincide with the number declared on the called method.", err);
    }
}
