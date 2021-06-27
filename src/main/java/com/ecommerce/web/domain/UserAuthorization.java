package com.ecommerce.web.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ECOM_USER_AUTHORIZATIONS")
@Getter
@Setter
@NoArgsConstructor
public class UserAuthorization {
    @Id
    @SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
    @Column(name = "ID")
    Long id;

    @Column(name = "USER_ID")
    Long userId;

    @Column(name = "AUTH_DATE")
    LocalDateTime authDate;

    @Column(name = "TOKEN_ID")
    String tokenId;

    public UserAuthorization(Long userId, LocalDateTime authDate, String tokenId) {
        this.userId = userId;
        this.authDate = authDate;
        this.tokenId = tokenId;
    }
}
