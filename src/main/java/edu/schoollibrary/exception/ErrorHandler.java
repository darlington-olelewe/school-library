package edu.schoollibrary.exception;

import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.response.CodeAndMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {


  @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
  @ExceptionHandler(AppException.class)
  public AppResponse<CodeAndMessage> handleAppException(AppException e){

    CodeAndMessage codeAndMessage = new CodeAndMessage(e.getCode(),e.getMessage());

    AppResponse<CodeAndMessage> appResponse = new AppResponse<>();
    appResponse.setCode(e.getCode());
    appResponse.setMessage("Exception Occurred");
    appResponse.setData(codeAndMessage);

    return appResponse;
  }
}
