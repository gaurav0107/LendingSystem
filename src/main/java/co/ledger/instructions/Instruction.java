package co.ledger.instructions;

import co.ledger.enums.InstructionType;
import co.ledger.exceptions.InvalidInstructionException;


public abstract class Instruction {
    private InstructionType instructionType;
    private String bankName;
    private String userName;

    public Instruction(String[] instructionCommand) throws InvalidInstructionException {
        if(instructionCommand.length < 2){
            throw new InvalidInstructionException();
        }
        try{
            this.instructionType = InstructionType.valueOf(instructionCommand[0]);
        }catch (IllegalArgumentException e){
            throw new InvalidInstructionException();
        }
        this.bankName = instructionCommand[1];
        this.userName = instructionCommand[2];
    }


    public abstract void processInstruction() throws InvalidInstructionException;

    public String getBankName() {
        return bankName;
    }

    public String getUserName() {
        return userName;
    }
}
