package pl.on.full.hack.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(exclude={"createdLeagues", "joinedLeagues"})
@ToString(exclude={"createdLeagues", "joinedLeagues"})
public class UserDTO {

    private String username;

    private String password;

    private List<Long> createdLeagues;

    private List<Long> joinedLeagues;
}
