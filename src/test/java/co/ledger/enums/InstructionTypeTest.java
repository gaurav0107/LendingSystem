package co.ledger.enums;
import org.junit.jupiter.api.Test;


class InstructionTypeTest {

    @Test
    void testEnum(){
        InstructionType.valueOf("LOAN").equals(InstructionType.LOAN);
    }
}