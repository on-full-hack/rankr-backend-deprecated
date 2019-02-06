package pl.on.full.hack.auth.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ranr_users")
@Data
public class RankrUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;
}
