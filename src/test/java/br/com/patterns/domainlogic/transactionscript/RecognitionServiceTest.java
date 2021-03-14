package br.com.patterns.domainlogic.transactionscript;

import br.com.patterns.base.Money;
import br.com.patterns.datasource.Database;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;

class RecognitionServiceTest {

    private Gateway gateway;
    private RecognitionService service;

    @BeforeEach
    void beforeEach() {
        gateway = new Gateway(new Database());
        service = new RecognitionService(gateway);
    }

    @DisplayName("After contract a Spreadsheet")
    @Nested
    class AfterContractSpreadsheet {

        private final long contractId = 1;

        @BeforeEach
        void makeContract() throws SQLException {
            long productId = 1;
            gateway.insertProduct(productId, "spd-1", "S");

            Money revenue = Money.dollars(134);
            gateway.insertContract(contractId, productId, revenue, LocalDate.now());
        }

        @DisplayName("The immediate recognized revenue can be calculated")
        @Test
        void theImmediateRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(44.67);
            LocalDate asOf = LocalDate.now();

            // Act
            service.calculateRevenueRecognitions(contractId);
            Money recognizedRevenue = service.recognizedRevenue(contractId, asOf);

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
            service.calculateRevenueRecognitions(contractId);
            Money recognizedRevenue = service.recognizedRevenue(contractId, asOf);

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
            service.calculateRevenueRecognitions(contractId);
            Money recognizedRevenue = service.recognizedRevenue(contractId, asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }
    }

    @DisplayName("After contract a Word Processor")
    @Nested
    class AfterContractWordProcessor {

        private final long contractId = 1;

        @BeforeEach
        void makeContract() throws SQLException {
            long productId = 1;
            gateway.insertProduct(productId, "wp-1", "W");

            Money revenue = Money.dollars(256.55);
            gateway.insertContract(contractId, productId, revenue, LocalDate.now());
        }

        @DisplayName("The immediate recognized revenue can be calculated")
        @Test
        void theImmediateRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(256.55);
            LocalDate asOf = LocalDate.now();

            // Act
            service.calculateRevenueRecognitions(contractId);
            Money recognizedRevenue = service.recognizedRevenue(contractId, asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }
    }

    @DisplayName("After contract a Database")
    @Nested
    class AfterContractDatabase {

        private final long contractId = 1;

        @BeforeEach
        void makeContract() throws SQLException {
            long productId = 1;
            gateway.insertProduct(productId, "db-1", "D");

            Money revenue = Money.dollars(134);
            gateway.insertContract(contractId, productId, revenue, LocalDate.now());
        }

        @DisplayName("The immediate recognized revenue can be calculated")
        @Test
        void theImmediateRevenueRecognitionsCanBeCalculated() {
            // Arrange
            Money expectedRecognizedRevenue = Money.dollars(44.67);
            LocalDate asOf = LocalDate.now();

            // Act
            service.calculateRevenueRecognitions(contractId);
            Money recognizedRevenue = service.recognizedRevenue(contractId, asOf);

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
            service.calculateRevenueRecognitions(contractId);
            Money recognizedRevenue = service.recognizedRevenue(contractId, asOf);

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
            service.calculateRevenueRecognitions(contractId);
            Money recognizedRevenue = service.recognizedRevenue(contractId, asOf);

            // Assert
            Assertions.assertEquals(expectedRecognizedRevenue, recognizedRevenue);
        }
    }
}