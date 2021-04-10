package br.com.patterns.domainlogic.domainmodel;

import br.com.patterns.base.Money;

import java.time.LocalDate;

public final class ThreeWayRecognitionStrategy implements RecognitionStrategy {

    private final int firstRecognitionOffset;

    private final int secondRecognitionOffset;

    public ThreeWayRecognitionStrategy(int firstRecognitionOffset, int secondRecognitionOffset) {
        this.firstRecognitionOffset = firstRecognitionOffset;
        this.secondRecognitionOffset = secondRecognitionOffset;
    }

    @Override
    public void calculateRevenueRecognitions(Contract contract) {
        Money[] allocation = contract.getRevenue().allocate(3);
        LocalDate whenSigned = contract.getWhenSigned();
        contract.addRevenueRecognition(new RevenueRecognition(
                allocation[0],
                whenSigned));
        contract.addRevenueRecognition(new RevenueRecognition(
                allocation[1],
                whenSigned.plusDays(firstRecognitionOffset)));
        contract.addRevenueRecognition(new RevenueRecognition(
                allocation[2],
                whenSigned.plusDays(secondRecognitionOffset)));
    }
}
