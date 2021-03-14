package br.com.patterns.domainlogic.transactionscript;

import br.com.patterns.base.Money;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public record RecognitionService(Gateway gateway) {

    public Money recognizedRevenue(long contractNumber, LocalDate asOf) {
        Money result = Money.dollars(0);

        try (ResultSet rs = gateway.findRecognitionsFor(contractNumber, asOf)) {
            while (rs.next()) {
                result = result.add(Money.dollars(rs.getBigDecimal("amount")));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating the Recognized Revenue.", e);
        }
    }

    public void calculateRevenueRecognitions(long contractNumber) {
        try (var contracts = gateway.findContract(contractNumber)) {
            contracts.next();

            Money totalRevenue = Money.dollars(contracts.getBigDecimal("revenue"));
            LocalDate recognitionDate = contracts.getDate("dateSigned").toLocalDate();
            String type = contracts.getString("type");

            if ("S".equals(type)) {
                Money[] allocation = totalRevenue.allocate(3);
                gateway.insertRecognition
                        (contractNumber, allocation[0], recognitionDate);
                gateway.insertRecognition
                        (contractNumber, allocation[1], recognitionDate.plusDays(60));
                gateway.insertRecognition
                        (contractNumber, allocation[2], recognitionDate.plusDays(90));
            } else if ("W".equals(type)) {
                gateway.insertRecognition
                        (contractNumber, totalRevenue, recognitionDate);
            } else if ("D".equals(type)) {
                Money[] allocation = totalRevenue.allocate(3);
                gateway.insertRecognition
                        (contractNumber, allocation[0], recognitionDate);
                gateway.insertRecognition
                        (contractNumber, allocation[1], recognitionDate.plusDays(30));
                gateway.insertRecognition
                        (contractNumber, allocation[2], recognitionDate.plusDays(60));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating the Revenue Recognitions.", e);
        }
    }
}
