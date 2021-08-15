package com.lmmmowi.redis.db.exception;

public class InvalidDbIndexException extends DbOperationException {

    public InvalidDbIndexException() {
        super("invalid db index");
    }
}
