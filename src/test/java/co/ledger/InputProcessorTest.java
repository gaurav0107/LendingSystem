package co.ledger;

import co.ledger.exceptions.InvalidInstructionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InputProcessorTest {

    @Test
    void process() {
        InputProcessor inputProcessor = new InputProcessor("sampleInput");
        try {
            inputProcessor.process();
            assert true;
        } catch (IOException | InvalidInstructionException e) {
            assert false;
        }
    }
}