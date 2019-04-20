package pl.on.full.hack.base.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.on.full.hack.league.dto.LeagueDTO;

import java.util.function.Function;

@Data
@NoArgsConstructor
@CommonsLog
public class BaseApiContract<T> {

    private T specificContract;

    private String error;

    public static <T> ResponseEntity<BaseApiContract<T>> internalServerError(Exception e) {
        log.error(e.getMessage(), e);
        final BaseApiContract<T> responseBody = new BaseApiContract<>();
        responseBody.setError(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
