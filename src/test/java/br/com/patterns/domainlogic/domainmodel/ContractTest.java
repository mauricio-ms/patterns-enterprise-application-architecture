package br.com.patterns.domainlogic.domainmodel;

import br.com.patterns.base.Money;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

class ContractTest {

    private Contract contract;

    private Product product;

    @DisplayName("After contract a Spreadsheet")
    @Nested
    class AfterContractSpreadsheet {

        @BeforeEach
        void makeContract() {
            Money revenue = Money.dollars(134);
            product = Product.newSpreadsheet("spd-1");
            contract = new Contract(revenue, LocalDate.now());
        }

        @DisplayName("The immediate recognized revenue can be calculated")
        @Test
        void theImmediateRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(44.67);
            LocalDate asOf = LocalDate.now();

            // Act
            product.calculateRevenueRecognitions(contract);
            Money recognizedRevenue = contract.recognizedRevenue(asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }

        @DisplayName("The next 60 days recognized revenue can be calculated")
        @Test
        void theNextTwoMonthsRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(89.34);
            LocalDate asOf = LocalDate.now().plusDays(60);

            // Act
            product.calculateRevenueRecognitions(contract);
            Money recognizedRevenue = contract.recognizedRevenue(asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }

        @DisplayName("The next 90 days recognized revenue can be calculated")
        @Test
        void theNextThreeMonthsRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(134);
            LocalDate asOf = LocalDate.now().plusDays(90);

            // Act
            product.calculateRevenueRecognitions(contract);
            Money recognizedRevenue = contract.recognizedRevenue(asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }
    }

    @DisplayName("After contract a Word Processor")
    @Nested
    class AfterContractWordProcessor {

        @BeforeEach
        void makeContract() {
            Money revenue = Money.dollars(256.55);
            product = Product.newWordProcessor("wp-1");
            contract = new Contract(revenue, LocalDate.now());
        }

        @DisplayName("The immediate recognized revenue can be calculated")
        @Test
        void theImmediateRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(256.55);
            LocalDate asOf = LocalDate.now();

            // Act
            product.calculateRevenueRecognitions(contract);
            Money recognizedRevenue = contract.recognizedRevenue(asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }
    }

    @DisplayName("After contract a Database")
    @Nested
    class AfterContractDatabase {

        @BeforeEach
        void makeContract() {
            Money revenue = Money.dollars(134);
            product = Product.newDatabase("db-1");
            contract = new Contract(revenue, LocalDate.now());
        }

        @DisplayName("The immediate recognized revenue can be calculated")
        @Test
        void theImmediateRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(44.67);
            LocalDate asOf = LocalDate.now();

            // Act
            product.calculateRevenueRecognitions(contract);
            Money recognizedRevenue = contract.recognizedRevenue(asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }

        @DisplayName("The next 30 days recognized revenue can be calculated")
        @Test
        void theNextMonthRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(89.34);
            LocalDate asOf = LocalDate.now().plusDays(30);

            // Act
            product.calculateRevenueRecognitions(contract);
            Money recognizedRevenue = contract.recognizedRevenue(asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }

        @DisplayName("The next 60 days recognized revenue can be calculated")
        @Test
        void theNextTwoMonthsRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(134);
            LocalDate asOf = LocalDate.now().plusDays(60);

            // Act
            product.calculateRevenueRecognitions(contract);
            Money recognizedRevenue = contract.recognizedRevenue(asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }
    }
}