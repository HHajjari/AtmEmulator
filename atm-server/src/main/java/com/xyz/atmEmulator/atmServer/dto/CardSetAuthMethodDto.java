package com.xyz.atmEmulator.atmServer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CardSetAuthMethodDto {

    @Pattern(regexp = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|(?<mastercard>5[1-5][0-9]{14})|(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|(?<amex>3[47][0-9]{13})|(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$", message = "Invalid card number")
    @ApiModelProperty(notes = "Card number in visa, master card, discover, amex, diners, jcb formats WITHOUT - , . ; or any other seperator character", position = 1, required = true)
    private String card;

    @ApiModelProperty(notes = "Authentication method in pin or finger print format", position = 2, required = true)
    private AuthMethod authMethod;
}
