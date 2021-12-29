package com.xyz.atmEmulator.bankServer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawDto {

    @Pattern(regexp = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|(?<mastercard>5[1-5][0-9]{14})|(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|(?<amex>3[47][0-9]{13})|(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$", message = "Invalid card number")
    @ApiModelProperty(notes = "Card number in visa, master card, discover, amex, diners, jcb formats WITHOUT - , . ; or any other seperator character", position = 1, required = true)
    private String card;

    @DecimalMin(value = "1.0", inclusive = true, message = "value should be greater than 1")
    @Digits(integer=10, fraction=0)
    private BigDecimal value;
}
