package com.lmmmowi.redis.db.exception;

public class WrongTypeOperationException extends DbOperationException {

    public WrongTypeOperationException() {
        super("WRONGTYPE Operation against a key holding the wrong kind of value");
    }
}
