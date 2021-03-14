package br.com.patterns.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void foemmelConundrum() {
        // Arrange
        long[] allocation = {3, 7};

        // Act
        Money[] result = Money.dollars(0.05).allocate(allocation);

        // Assert
        Assertions.assertEquals(Money.dollars(0.02), result[0]);
        Assertions.assertEquals(Money.dollars(0.03), result[1]);
    }
}