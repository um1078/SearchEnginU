package searchengine.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<FaultResponse> handle(ApiRequestException exception) {
        // Логируем ошибку
        log.error("API Error: {} - {}", exception.getMessage(),
                (exception.getFaultResponse() != null ? exception.getFaultResponse().getError() : "Unknown error"));

        // Если у исключения есть метод getStatus(), используем его.
        // Если нет — отдаем 400 Bad Request или 500 по умолчанию.
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(exception.getFaultResponse(), status);
    }
}