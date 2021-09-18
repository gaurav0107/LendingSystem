package co.ledger.instructions;

import co.ledger.exceptions.InvalidInstructionException;
import co.ledger.util.StringUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionTest {

    @Test
    void processInstruction() {
        Instruction instruction = null;
        try {
            instruction = new LoanInstruction(createInvalidInstruction());
            assert false;
        } catch (InvalidInstructionException e) {
            assert true;
        }

    }


    @Test
    void processInvalidInstruction() {
        Instruction instruction = null;
        try {
            instruction = new LoanInstruction(createInvalidInstructionSmall());
            assert false;
        } catch (InvalidInstructionException e) {
            assert true;
        }

    }

    private String [] createInvalidInstructionSmall(){
        return StringUtil.getStringArray("CREDIT_CARD");
    }

    private String [] createInvalidInstruction(){
        return StringUtil.getStringArray("CREDIT_CARD 1 2");
    }
}