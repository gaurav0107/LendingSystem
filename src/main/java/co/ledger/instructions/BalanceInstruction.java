package co.ledger.instructions;

import co.ledger.exceptions.BalanceFetchException;
import co.ledger.exceptions.InvalidInstructionException;
import co.ledger.lending.LendingSystem;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BalanceInstruction extends Instruction {

    private int emiNo;

    public BalanceInstruction(String[] instructionCommand) throws InvalidInstructionException {
        super(instructionCommand);
        this.emiNo = Integer.parseInt(instructionCommand[3]);
    }

    @Override
    public void processInstruction() throws InvalidInstructionException {
        try {
            LendingSystem.getLendingSystemInstance().getBalance(this);
        } catch (BalanceFetchException e) {
            throw new InvalidInstructionException();
        }
    }
}
