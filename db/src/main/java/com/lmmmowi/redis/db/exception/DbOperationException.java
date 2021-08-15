package com.lmmmowi.redis.db.exception;

public abstract class DbOperationException extends RuntimeException {

    DbOperationException(String message) {
        super(message);
    }
}
