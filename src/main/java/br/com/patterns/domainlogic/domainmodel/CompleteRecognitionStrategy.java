package br.com.patterns.domainlogic.domainmodel;

public final class CompleteRecognitionStrategy implements RecognitionStrategy {

    @Override
    public void calculateRevenueRecognitions(Contract contract) {
        contract.addRevenueRecognition(new RevenueRecognition(
                contract.getRevenue(),
                contract.getWhenSigned()));
    }
}
