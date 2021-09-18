package co.ledger;

import co.ledger.exceptions.InvalidInstructionException;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        InputProcessor inputProcessor = new InputProcessor(args[0]);
        try {
            inputProcessor.process();
        } catch (IOException | InvalidInstructionException e) {
            e.printStackTrace();
        }
    }
}