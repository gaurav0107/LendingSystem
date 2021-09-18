package co.ledger.lending.contract;

import co.ledger.exceptions.PaymentNotAllowedException;
import co.ledger.instructions.PaymentInstruction;

public interface PaymentProcessor {
    void processPayment(PaymentInstruction paymentInstruction) throws PaymentNotAllowedException;
}
