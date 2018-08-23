package com.crayon2f.validator.config;

import com.crayon2f.validator.beans.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.PropertyAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 通用异常处理返回
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, PropertyAccessException.class})
    @ResponseBody
    public RestResponse handleMethodArgumentNotValidException(Exception ex) {

        if (MethodArgumentNotValidException.class.isInstance(ex)) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = exception.getBindingResult();
            List<ObjectError> errors = bindingResult.getAllErrors();
            String message = StringUtils.EMPTY;
            if (CollectionUtils.isNotEmpty(errors)) {
                FieldError error = (FieldError) errors.get(0);
                message = String.format("[%s] %s", error.getField(), error.getDefaultMessage());
            }
            log.error(String.format("MethodArgumentNotValidException : %s", message));
            return RestResponse.fail(message);
        } else {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            log.error(String.format("TypeMismatchException : %s", ex.getMessage()));
            return RestResponse.fail(
                    String.format("[%s] TYPE ERROR : required type[%s]; current type[%s]",
                            exception.getName(),
                            exception.getRequiredType().getSimpleName(),
                            ClassUtils.getShortName(exception.getValue().getClass()))
            );
        }
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public RestResponse handleConstraintViolationException(ConstraintViolationException ex) {

        String message = StringUtils.EMPTY;
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            ConstraintViolation<?> violation = constraintViolations.iterator().next();
            String fieldName = Optional.ofNullable(violation.getPropertyPath().toString())
                    .filter(ths -> ths.contains("."))
                    .map(ths -> ths.substring(ths.indexOf(".") + 1))
                    .orElse(violation.getPropertyPath().toString());
            message = String.format("[%s] %s", fieldName, violation.getMessage());
        }
        log.error(String.format("ConstraintViolationException : %s", message));
        return RestResponse.fail(message);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestResponse handler(Exception ex) {

        log.error(String.format("exception : %s", ex.getMessage()));
        ex.printStackTrace();
        return RestResponse.fail("unknown error");
    }

}
