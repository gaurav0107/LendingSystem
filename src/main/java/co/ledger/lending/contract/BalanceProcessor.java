package co.ledger.lending.contract;

import co.ledger.dto.Balance;
import co.ledger.exceptions.BalanceFetchException;
import co.ledger.instructions.BalanceInstruction;

public interface BalanceProcessor {
    Balance getBalance(BalanceInstruction balanceInstruction) throws BalanceFetchException;
}
