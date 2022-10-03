package exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler which will be completed
 * @author ehsan
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Value(value = "${data.exception.invalidsurl}")
    private String invalidShortUrl;;
    @Value(value = "${data.exception.invalidurl}")
    private String invalidLongUrl;
    @Value(value = "${data.exception.unknown}")
    private String unknownError;
    
    @ExceptionHandler(InvalidShortUrlException.class)
    public ResponseEntity<String> invalidShortUrl(InvalidShortUrlException businessException) {
        return new ResponseEntity<>(invalidShortUrl, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidLongUrlException.class)
    public ResponseEntity<String> invalidLongUrl(InvalidLongUrlException exception) {
        return new ResponseEntity<>(invalidLongUrl, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> unknownProblem(Throwable th) {
        return new ResponseEntity<>(unknownError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    
    

}
