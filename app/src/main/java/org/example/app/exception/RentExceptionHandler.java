package org.example.app.exception;

import org.example.app.exception.schema.ErrorUserOrSubscriptionValidationSchema;
import org.example.app.exception.schema.ErrorUserOrSubscriptionValidationSchema.FieldMessageError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RentExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorUserOrSubscriptionValidationSchema> handleValidationExceptions(MethodArgumentNotValidException e) {

        List<FieldMessageError> errors = e.getBindingResult().getAllErrors().stream()
                .map(error -> new FieldMessageError(((FieldError) error).getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorUserOrSubscriptionValidationSchema response = new ErrorUserOrSubscriptionValidationSchema(
                "Ошибка валидации данных",
                LocalDateTime.now(),
                errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({UserNotFoundByIDException.class,
            SubscriptionNotFoundByIDException.class})
    public ResponseEntity<String> handleUserNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({UserExistsByEmailException.class,
            SubscriptionExistsByUserException.class})
    public ResponseEntity<String> handleUserExistsByEmailException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

}
