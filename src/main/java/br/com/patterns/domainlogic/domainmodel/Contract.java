package br.com.patterns.domainlogic.domainmodel;

import br.com.patterns.base.Money;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Contract {

    private final List<RevenueRecognition> revenueRecognitions = new ArrayList<>();

    private final Money revenue;

    private final LocalDate whenSigned;

    public Contract(Money revenue, LocalDate whenSigned) {
        this.revenue = revenue;
        this.whenSigned = whenSigned;
    }

    public Money recognizedRevenue(LocalDate asOf) {
        return revenueRecognitions.stream()
                .filter(r -> r.isRecognizableBy(asOf))
                .map(RevenueRecognition::getAmount)
                .reduce(Money.dollars(0), Money::add);
    }

    public void addRevenueRecognition(RevenueRecognition revenueRecognition) {
        revenueRecognitions.add(revenueRecognition);
    }

    public Money getRevenue() {
        return revenue;
    }

    public LocalDate getWhenSigned() {
        return whenSigned;
    }
}
