package com.gottaboy.iching.common.exception;

/**
 * 文件下载异常
 *
 * @author gottaboy
 */
public class FileDownloadException extends Exception {
    private static final long serialVersionUID = -4353976687870027960L;

    public FileDownloadException(String message) {
        super(message);
    }
}
