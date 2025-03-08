package backend.backendbase.exception;

import backend.backendbase.common.result.Result;
import backend.backendbase.common.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@AllArgsConstructor
@ResponseBody
@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result <Void> handleException(Exception exception) {
        exception.printStackTrace();
        log.error("Error Handling Exception, message = {}", exception.getMessage());
        return Result.failed();
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Result <Void> handleBadCredentialsException(BadCredentialsException exception) {
        log.error("Error Handling Bad Credentials, message = {}", exception.getMessage());
        return Result.failed(ResultCode.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Result <Void> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error("Error Handling DataIntegrityViolationException", exception);
        return Result.failed(ResultCode.ERROR_EXISTS);
    }

    @ExceptionHandler(value = { RateLimitExceededException.class })
    @ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
    public Result <Void> handleRateLimitExceededException(RateLimitExceededException exception) {
        log.error("Error Handling RateLimitExceededException, message = {}", exception.getMessage());
        return Result.failed(ResultCode.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(value = { RecordNotFoundException.class })
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Result <Void> handleRecordNotFoundException(RecordNotFoundException exception) {
        log.error("Error Handling RecordNotFoundException, message = {}", exception.getMessage());
        return Result.failed(ResultCode.NOT_FOUND);
    }
}
