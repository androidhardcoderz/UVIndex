package com.uvindex.scottauman.backgrounds;

/**
 * Created by Scott on 11/23/2015.
 */
public class DownloadException extends Exception {

    public DownloadException(String message) {
        super(message);
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
