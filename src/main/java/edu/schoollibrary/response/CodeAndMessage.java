package edu.schoollibrary.response;

import lombok.Data;

@Data
public class CodeAndMessage {
  private String code;
  private String message;

  public CodeAndMessage(String code, String message){
    this.code = code;
    this.message = message;
  }
}
