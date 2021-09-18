package co.ledger.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum InstructionType {
    LOAN("LOAN"),
    PAYMENT("PAYMENT"),
    BALANCE("BALANCE");

    private final String name;

}
