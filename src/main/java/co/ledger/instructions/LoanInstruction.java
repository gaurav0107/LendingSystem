package co.ledger.instructions;


import co.ledger.exceptions.InvalidInstructionException;
import co.ledger.lending.LendingSystem;
import lombok.Getter;

@Getter
public class LoanInstruction extends Instruction {

    private int loanAmount;
    private int loanTenure;
    private int rateOfInterest;

    public LoanInstruction(String[] instructionCommand) throws InvalidInstructionException {
        super(instructionCommand);
        this.loanAmount = Integer.parseInt(instructionCommand[3]);
        this.loanTenure = Integer.parseInt(instructionCommand[4]);
        this.rateOfInterest = Integer.parseInt(instructionCommand[5]);
    }

    @Override
    public void processInstruction(){
        LendingSystem.getLendingSystemInstance().processLoan(this);
    }
}
