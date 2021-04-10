package br.com.patterns.domainlogic.domainmodel;

import br.com.patterns.base.Money;

import java.time.LocalDate;

public final class RevenueRecognition {

    private final Money amount;

    private final LocalDate date;

    public RevenueRecognition(Money amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public Money getAmount() {
        return amount;
    }

    public boolean isRecognizableBy(LocalDate asOf) {
        return asOf.isAfter(date) || asOf.equals(date);
    }
}
