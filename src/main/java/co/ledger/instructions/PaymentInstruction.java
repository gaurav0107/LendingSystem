package co.ledger.instructions;


import co.ledger.exceptions.InvalidInstructionException;
import co.ledger.exceptions.PaymentNotAllowedException;
import co.ledger.lending.LendingSystem;
import lombok.Getter;

@Getter
public class PaymentInstruction extends Instruction {

    private int lumpSumAmount;
    private int emiNo;

    public PaymentInstruction(String[] instructionCommand) throws InvalidInstructionException {
        super(instructionCommand);
        this.lumpSumAmount = Integer.parseInt(instructionCommand[3]);
        this.emiNo = Integer.parseInt(instructionCommand[4]);
    }

    @Override
    public void processInstruction() throws InvalidInstructionException {
        try {
            LendingSystem.getLendingSystemInstance().processPayment(this);
        } catch (PaymentNotAllowedException noAvailableLoanToMakePayments) {
            throw new InvalidInstructionException();
        }
    }
}
