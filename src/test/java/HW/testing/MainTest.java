package HW.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void main() {
        // without it jacoco was dropping verify because it's also checked
        Assertions.assertDoesNotThrow(()->Main.main(null));
    }
}