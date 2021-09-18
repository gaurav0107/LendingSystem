package co.ledger;

import co.ledger.enums.InstructionType;
import co.ledger.exceptions.InvalidInstructionException;
import co.ledger.instructions.BalanceInstruction;
import co.ledger.instructions.Instruction;
import co.ledger.instructions.LoanInstruction;
import co.ledger.instructions.PaymentInstruction;
import co.ledger.util.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class InputProcessor {

    private String inputFile;
    public InputProcessor(String inputFile) {
        this.inputFile = inputFile;
    }

    public void process() throws IOException, InvalidInstructionException {
        File file = new File(this.inputFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        List<Instruction> instructions = new ArrayList<>();
        while ((st = br.readLine()) != null){
            String[] instructionCommand = StringUtil.getStringArray(st);
            switch (InstructionType.valueOf(instructionCommand[0])){
                case LOAN:
                    instructions.add(new LoanInstruction(instructionCommand));
                    break;
                case PAYMENT:
                    instructions.add(new PaymentInstruction(instructionCommand));
                    break;
                case BALANCE:
                    instructions.add(new BalanceInstruction(instructionCommand));
                    break;

            }
        }
        instructions.stream().forEach(instruction -> {
            try {
                instruction.processInstruction();
            } catch (InvalidInstructionException e) {
                e.printStackTrace();
            }
        });
    }
}
