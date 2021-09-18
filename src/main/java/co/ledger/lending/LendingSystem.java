package co.ledger.lending;

import co.ledger.dto.Balance;
import co.ledger.exceptions.BalanceFetchException;
import co.ledger.exceptions.PaymentNotAllowedException;
import co.ledger.instructions.BalanceInstruction;
import co.ledger.instructions.LoanInstruction;
import co.ledger.instructions.PaymentInstruction;
import co.ledger.lending.contract.BalanceProcessor;
import co.ledger.lending.contract.LoanProcessor;
import co.ledger.lending.contract.PaymentProcessor;

import java.util.HashMap;
import java.util.Map;

public class LendingSystem implements PaymentProcessor, LoanProcessor, BalanceProcessor {

    private Map<String, Bank> bankMap = new HashMap<>();


    public static LendingSystem lendingSystemInstance;

    /*
     * Restricting with SingleTon Pattern,
     * There should be single instance of Lending system running
     * */
    private LendingSystem() {
    }

    public static LendingSystem getLendingSystemInstance() {
        if (lendingSystemInstance == null)
            lendingSystemInstance = new LendingSystem();
        return lendingSystemInstance;
    }

    @Override
    public Balance getBalance(BalanceInstruction balanceInstruction) throws BalanceFetchException {
        if(!bankMap.containsKey(balanceInstruction.getBankName())){
            throw new BalanceFetchException();
        }
        Balance balance = bankMap.get(balanceInstruction.getBankName()).getBalance(balanceInstruction);
        System.out.print(balanceInstruction.getBankName()+ " ");
        System.out.print(balanceInstruction.getUserName()+ " ");
        System.out.print(balance.getAmountPaid() + " ");
        System.out.println(balance.getRemainingTenure());
        return balance;
    }

    @Override
    public void processPayment(PaymentInstruction paymentInstruction) throws PaymentNotAllowedException {
        if (!bankMap.containsKey(paymentInstruction.getBankName())) {
            throw new PaymentNotAllowedException();
        }
        bankMap.get(paymentInstruction.getBankName()).processPayment(paymentInstruction);
    }

    @Override
    public void processLoan(LoanInstruction loanInstruction) {
        if (!bankMap.containsKey(loanInstruction.getBankName())) {
            Bank bank = new Bank(loanInstruction.getBankName());
            bankMap.put(loanInstruction.getBankName(), bank);
        }
        Bank bank = bankMap.get(loanInstruction.getBankName());
        bank.processLoan(loanInstruction);
    }

}

