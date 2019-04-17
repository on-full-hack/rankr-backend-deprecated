package pl.on.full.hack.base.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BaseApiContract<T> {

    private T specificContract;

    private String error;
}
