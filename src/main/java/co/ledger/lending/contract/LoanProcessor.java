package co.ledger.lending.contract;

import co.ledger.instructions.LoanInstruction;

public interface LoanProcessor {
    void processLoan(LoanInstruction loanInstruction);
}
