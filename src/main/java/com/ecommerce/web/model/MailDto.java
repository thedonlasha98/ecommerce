package com.ecommerce.web.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDto {
    @NotNull
    private String subject;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Min(10)
    private String body;

    private String fileAttach;

}