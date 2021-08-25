package com.redditclone.redditclone.exceptions;

public class SpringReddiException extends Throwable {
    public SpringReddiException(String exception_occurred_while_send_email) {

        super(exception_occurred_while_send_email);

    }
}
