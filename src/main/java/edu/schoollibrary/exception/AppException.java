package edu.schoollibrary.exception;

public class AppException extends  RuntimeException{
  private String code;
  public AppException(String message, String code) {
    super(message);
    this.code = code;
  }

  public String getCode(){
    return this.code;
  }
}
