package com.tornadomicroservice.filter.exceptions;

public class MethodNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5135569336979479020L;

	public MethodNotFoundException() {
        super("The method you're searching for does not exists");
    }
	
	public MethodNotFoundException(Throwable err) {
        super("The method you're searching for does not exists", err);
    }
}
