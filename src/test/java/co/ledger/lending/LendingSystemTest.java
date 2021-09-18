package co.ledger.lending;

import co.ledger.dto.Balance;
import co.ledger.exceptions.BalanceFetchException;
import co.ledger.exceptions.InvalidInstructionException;
import co.ledger.exceptions.PaymentNotAllowedException;
import co.ledger.instructions.BalanceInstruction;
import co.ledger.instructions.LoanInstruction;
import co.ledger.instructions.PaymentInstruction;
import co.ledger.util.StringUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class LendingSystemTest {

    @Test
    void getLendingSystemInstance() {
    }

    @Test
    void getBalance() {
        createLoan();
        BalanceInstruction balanceInstruction = createBalanceInstruction();
        Balance balance = null;
        try {
            balance = LendingSystem.getLendingSystemInstance().getBalance(balanceInstruction);
        } catch (BalanceFetchException e) {
            assert false;
        }
        assert balance.getRemainingTenure().equals(5);
        assert balance.getAmountPaid().equals(3094);
    }

    @Test
    void processPayment() {
        createLoan();
        try {
            LendingSystem.getLendingSystemInstance().processPayment(createPaymentInstruction());
            BalanceInstruction balanceInstruction = createBalanceInstruction();
            Balance balance = null;
            try {
                balance = LendingSystem.getLendingSystemInstance().getBalance(balanceInstruction);
                assert balance.getRemainingTenure().equals(4);
                assert balance.getAmountPaid().equals(3594);
            } catch (BalanceFetchException e) {
                assert false;
            }
        } catch (PaymentNotAllowedException e) {
            assert false;
        }
    }


    @Test
    void processLoan() {
        createLoan();
    }


    @Test
    void processPaymentForInvalidUser() {
        try {
            createLoan();
            LendingSystem.getLendingSystemInstance().processPayment(createPaymentInstructionForGaurav());
            assert false;
        } catch (PaymentNotAllowedException e) {
            assert true;
        }
    }

    @Test
    void processPaymentForInvalidBank() {
        try {
            createLoan();
            LendingSystem.getLendingSystemInstance().processPayment(createPaymentInstructionForHDBC());
            assert false;
        } catch (PaymentNotAllowedException e) {
            assert true;
        }
    }

    @Test
    void processPaymentForExtraAmount() {
        try {
            createLoan();
            LendingSystem.getLendingSystemInstance().processPayment(createPaymentInstructionForExtraAmount());
        } catch (PaymentNotAllowedException e) {
            assert true;
        }
    }

    @Test
    void processPaymentForMultipleAttempts() {
        try {
            createLoan();
            LendingSystem.getLendingSystemInstance().processPayment(createPaymentInstruction());
            LendingSystem.getLendingSystemInstance().processPayment(createPaymentInstruction());
            BalanceInstruction balanceInstruction = createBalanceInstruction();
            Balance balance = null;
            try {
                balance = LendingSystem.getLendingSystemInstance().getBalance(balanceInstruction);
                assert balance.getAmountPaid().equals(4094);
            } catch (BalanceFetchException e) {
                assert false;
            }
        } catch (PaymentNotAllowedException e) {
            assert true;
        }
    }

    @Test
    void processInvalidBalanceInstruction(){
        BalanceInstruction balanceInstruction = createBalanceInstruction();
        try {
            LendingSystem.getLendingSystemInstance().getBalance(balanceInstruction);
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void getBalanceException(){
        BalanceInstruction balanceInstruction = createBalanceInstruction();
        try {
            LendingSystem.getLendingSystemInstance().getBalance(balanceInstruction);
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void createLoanTest(){
        LoanAccount loanAccount = new LoanAccount();
        LoanInstruction loanInstruction = createLoanInstruction();
        loanAccount.processLoan(loanInstruction);
        assert loanAccount.getRateOfInterest().equals(loanInstruction.getRateOfInterest());
        assert loanAccount.getPrincipalAmount().equals(loanInstruction.getLoanAmount());
        assert loanAccount.getLoanTenure().equals(loanInstruction.getLoanTenure());
    }

    @Test
    void paymentNotAllowedException(){
        try {
            LendingSystem.getLendingSystemInstance().processPayment(createPaymentInstruction());
        } catch (Exception e) {
            assert true;
        }
    }

    private void createLoan(){
        LendingSystem.getLendingSystemInstance().processLoan(createLoanInstruction());
    }

    private LoanInstruction createLoanInstruction(){
        try {
            return new LoanInstruction(StringUtil.getStringArray("LOAN IDIDI Dale 5000 1 6"));
        } catch (InvalidInstructionException e) {
            assert false;
        }
        return null;
    }

    private BalanceInstruction createBalanceInstruction(){
        try {
            return new BalanceInstruction(StringUtil.getStringArray("BALANCE IDIDI Dale 7"));
        } catch (InvalidInstructionException e) {
            assert false;
        }
        return null;
    }

    private PaymentInstruction createPaymentInstruction(){
        try {
            return new PaymentInstruction(StringUtil.getStringArray("PAYMENT IDIDI Dale 500 6"));
        } catch (InvalidInstructionException e) {
            assert false;
        }
        return null;
    }

    private PaymentInstruction createPaymentInstructionForGaurav(){
        try {
            return new PaymentInstruction(StringUtil.getStringArray("PAYMENT IDIDI Gaurav 500 6"));
        } catch (InvalidInstructionException e) {
            assert false;
        }
        return null;
    }

    private PaymentInstruction createPaymentInstructionForHDBC(){
        try {
            return new PaymentInstruction(StringUtil.getStringArray("PAYMENT HDBC Dale 500 6"));
        } catch (InvalidInstructionException e) {
            assert false;
        }
        return null;
    }

    private PaymentInstruction createPaymentInstructionForExtraAmount(){
        try {
            return new PaymentInstruction(StringUtil.getStringArray("PAYMENT IDIDI Dale 500000 6"));
        } catch (InvalidInstructionException e) {
            assert false;
        }
        return null;
    }
}