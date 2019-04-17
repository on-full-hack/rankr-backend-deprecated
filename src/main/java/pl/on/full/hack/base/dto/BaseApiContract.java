package pl.on.full.hack.base.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseApiContract<T> {

    private T specificContract;

    private String error;
}
