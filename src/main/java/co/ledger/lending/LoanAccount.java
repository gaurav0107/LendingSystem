package co.ledger.lending;

import co.ledger.dto.Balance;
import co.ledger.exceptions.PaymentNotAllowedException;
import co.ledger.instructions.BalanceInstruction;
import co.ledger.instructions.LoanInstruction;
import co.ledger.instructions.PaymentInstruction;
import co.ledger.lending.contract.BalanceProcessor;
import co.ledger.lending.contract.LoanProcessor;
import co.ledger.lending.contract.PaymentProcessor;

import java.util.HashMap;
import java.util.Map;

public class LoanAccount implements PaymentProcessor, LoanProcessor, BalanceProcessor {

    private Integer rateOfInterest;
    private Integer loanTenure;
    private Integer principalAmount;
    private Map<Integer, Integer> paymentsMadePerMonth;


    @Override
    public void processPayment(PaymentInstruction paymentInstruction) throws PaymentNotAllowedException {
        addPayments(paymentInstruction.getLumpSumAmount(), paymentInstruction.getEmiNo());
    }

    @Override
    public Balance getBalance(BalanceInstruction balanceInstruction) {
        return Balance.builder()
                .amountPaid(getAmountPaid(balanceInstruction.getEmiNo()))
                .remainingTenure(getRemainingTenure(balanceInstruction.getEmiNo()))
                .build();
    }

    @Override
    public void processLoan(LoanInstruction loanInstruction) {
        paymentsMadePerMonth = new HashMap<>();
        this.principalAmount = loanInstruction.getLoanAmount();
        this.rateOfInterest = loanInstruction.getRateOfInterest();
        this.loanTenure = loanInstruction.getLoanTenure();
    }

    private int getPrePayments(int untilMonth) {
        int prepaidAmount = 0;
        for (Integer month : paymentsMadePerMonth.keySet()) {
            if (month <= untilMonth) {
                prepaidAmount += paymentsMadePerMonth.get(month);
            }
        }
        return prepaidAmount;
    }

    private int getAmountPaid(int untilMonth) {
        int amountPaid = 0;
        for (int month = 0; month < untilMonth; month++) {
            amountPaid += getEMI();
        }
        return amountPaid + getPrePayments(untilMonth);
    }

    private int getRemainingTenure(int untilMonth) {
        int amountPaid = getAmountPaid(untilMonth);
        int remainingAmount = getTotalLoanAmount() - amountPaid;
        return (int) Math.ceil(remainingAmount / (getEMI() * 1.0));
    }

    private void addPayments(Integer amount, Integer month) throws PaymentNotAllowedException {
        if (amount > (getTotalLoanAmount() - getAmountPaid(month))) {
            throw new PaymentNotAllowedException();
        }
        if (!paymentsMadePerMonth.containsKey(month)) {
            paymentsMadePerMonth.put(month, amount);
        } else {
            paymentsMadePerMonth.put(month, paymentsMadePerMonth.get(month) + amount);
        }
    }

    private Integer getTotalInterest() {
        return (int) Math.ceil((principalAmount * rateOfInterest * loanTenure) / 100.0);

    }

    private Integer getTotalLoanAmount() {
        return principalAmount + getTotalInterest();
    }

    private Integer getEMI() {
        return (int) Math.ceil(getTotalLoanAmount() / (loanTenure * 12.0));
    }

    public Integer getRateOfInterest() {
        return rateOfInterest;
    }

    public Integer getLoanTenure() {
        return loanTenure;
    }

    public Integer getPrincipalAmount() {
        return principalAmount;
    }
}
