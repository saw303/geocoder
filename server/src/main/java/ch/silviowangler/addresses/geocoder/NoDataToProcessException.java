package ch.silviowangler.addresses.geocoder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Silvio Wangler
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoDataToProcessException extends RuntimeException
{
}
