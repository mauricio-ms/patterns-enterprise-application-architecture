package br.com.patterns.domainlogic.domainmodel;

public final class Product {

    private final String name;

    private final RecognitionStrategy recognitionStrategy;

    public static Product newWordProcessor(String name) {
        return new Product(name, new CompleteRecognitionStrategy());
    }

    public static Product newSpreadsheet(String name) {
        return new Product(name, new ThreeWayRecognitionStrategy(60, 90));
    }

    public static Product newDatabase(String name) {
        return new Product(name, new ThreeWayRecognitionStrategy(30, 60));
    }

    private Product(String name, RecognitionStrategy recognitionStrategy) {
        this.name = name;
        this.recognitionStrategy = recognitionStrategy;
    }

    public void calculateRevenueRecognitions(Contract contract) {
        recognitionStrategy.calculateRevenueRecognitions(contract);
    }

    public String getName() {
        return name;
    }
}
