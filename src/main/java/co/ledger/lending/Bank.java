package co.ledger.lending;

import co.ledger.dto.Balance;
import co.ledger.exceptions.PaymentNotAllowedException;
import co.ledger.instructions.BalanceInstruction;
import co.ledger.instructions.LoanInstruction;
import co.ledger.instructions.PaymentInstruction;
import co.ledger.lending.contract.BalanceProcessor;
import co.ledger.lending.contract.LoanProcessor;
import co.ledger.lending.contract.PaymentProcessor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Bank implements PaymentProcessor, LoanProcessor, BalanceProcessor {
    private String bankName;
    private Map<String, LoanAccount> userLedger;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.userLedger = new HashMap<>();
    }

    @Override
    public void processPayment(PaymentInstruction paymentInstruction) throws PaymentNotAllowedException {
        if(!userLedger.containsKey(paymentInstruction.getUserName())){
            throw new PaymentNotAllowedException();
        }
        userLedger.get(paymentInstruction.getUserName()).processPayment(paymentInstruction);
    }

    @Override
    public void processLoan(LoanInstruction loanInstruction) {
        LoanAccount loanAccount = new LoanAccount();
        loanAccount.processLoan(loanInstruction);
        userLedger.put(loanInstruction.getUserName(), loanAccount);
    }

    @Override
    public Balance getBalance(BalanceInstruction balanceInstruction) {
        return userLedger.get(balanceInstruction.getUserName()).getBalance(balanceInstruction);
    }
}
