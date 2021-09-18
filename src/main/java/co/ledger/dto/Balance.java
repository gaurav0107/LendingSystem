package co.ledger.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Balance {
    Integer amountPaid;
    Integer remainingTenure;
}
