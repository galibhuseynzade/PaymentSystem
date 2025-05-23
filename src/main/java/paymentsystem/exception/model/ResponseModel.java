package paymentsystem.exception.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResponseModel {
    String message;
    int status;
    LocalDateTime timestamp;

    public ResponseModel(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
