package com.techlabs.app.exception;

public class AllExceptions {
	
	public static class CustomerNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CustomerNotFoundException(String message) {
            super(message);
        }
    }

    public static class AccountNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class CustomerIsNotActiveException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CustomerIsNotActiveException(String message) {
            super(message);
        }
    }
    
    public static class AccountNumberWrongException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountNumberWrongException(String message) {
            super(message);
        }
    }
	
    
    public static class InsufficientFundsException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InsufficientFundsException(String message) {
            super(message);
        }
    }
    
    
    public static class UserNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UserNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class CustomerAlreadyAssignedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CustomerAlreadyAssignedException(String message) {
            super(message);
        }
    }
    
    public static class CustomerAlreadyDeletedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CustomerAlreadyDeletedException(String message) {
            super(message);
        }
    }
    
    public static class CustomerAlreadyActiveException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CustomerAlreadyActiveException(String message) {
            super(message);
        }
    }
    
    public static class AccountDeletedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountDeletedException(String message) {
            super(message);
        }
    }
    
    public static class AccountAlreadyActiveException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountAlreadyActiveException(String message) {
            super(message);
        }
    }
    
    
    public static class AccountInactiveException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public AccountInactiveException(String message) {
            super(message);
        }
    }
    
    public static class BankNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public BankNotFoundException(String message) {
            super(message);
        }
    }
    

}
