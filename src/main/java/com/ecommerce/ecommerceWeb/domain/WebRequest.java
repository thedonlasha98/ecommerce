package com.ecommerce.ecommerceWeb.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ECOM_WEB_REQUESTS")
public class WebRequest {
    @Id
    @SequenceGenerator(name = "ECOM_SEQ", sequenceName = "ECOM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECOM_SEQ")
    private Long id;

    @Column(name = "REQUEST_FORM")
    private String requestForm;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "INP_SYSDATE")
    private LocalDate inpSysdate;

}
