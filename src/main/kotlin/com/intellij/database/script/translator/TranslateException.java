package com.intellij.database.script.translator;

public class TranslateException extends Exception {

  public TranslateException() {
    super();
  }

  public TranslateException(String message) {
    super(message);
  }

  public TranslateException(String message, Throwable cause) {
    super(message, cause);
  }

  public TranslateException(Throwable cause) {
    super(cause);
  }

  protected TranslateException(String message, Throwable cause,
                      boolean enableSuppression,
                      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
