package br.com.patterns.base;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(long amount, Currency currency) implements Comparable<Money> {

    private static final int[] CENTS = new int[]{1, 10, 100, 1000};

    public static Money dollars(double amount) {
        return new Money(amount, Currency.getInstance("USD"));
    }

    private Money(long amount, Money other) {
        this(amount, other.currency);
    }

    public Money(long amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(double amount, Currency currency) {
        this(Math.round(amount * centFactor(currency)), currency);
    }

    public Money(BigDecimal amount, Currency currency) {
        this(Math.round(amount.longValue() * centFactor(currency)), currency);
    }

    private static int centFactor(Currency currency) {
        return CENTS[currency.getDefaultFractionDigits()];
    }

    public Money[] allocate(int n) {
        Money lowResult = new Money(amount / n, this);
        Money highResult = new Money(lowResult.amount + 1, this);

        Money[] results = new Money[n];
        int remainder = (int) amount % n;
        for (int i = 0; i < remainder; i++) {
            results[i] = highResult;
        }
        for (int i = remainder; i < n; i++) {
            results[i] = lowResult;
        }

        return results;
    }

    public Money[] allocate(long[] ratios) {
        long total = 0;
        for (int i = 0; i < ratios.length; i++) {
            total += ratios[i];
        }

        long remainder = amount;
        Money[] results = new Money[ratios.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = new Money(amount * ratios[i] / total, this);
            remainder -= results[i].amount;
        }

        for (int i = 0; i < remainder; i++) {
            results[i] = new Money(results[i].amount + 1, this);
        }

        return results;
    }

    public Money add(Money other) {
        assertSameCurrencyAs(other);
        return new Money(amount + other.amount, this);
    }

    public Money subtract(Money other) {
        assertSameCurrencyAs(other);
        return new Money(amount - other.amount, this);
    }

    public Money multiply(double amount) {
        return multiply(new BigDecimal(amount));
    }

    public Money multiply(BigDecimal amount) {
        return multiply(amount, RoundingMode.HALF_EVEN);
    }

    public Money multiply(BigDecimal amount, RoundingMode roundingMode) {
        return new Money(BigDecimal.valueOf(amount(), currency.getDefaultFractionDigits())
            .multiply(amount, new MathContext(2, roundingMode)), currency);
    }

    public boolean greaterThan(Money other) {
        return compareTo(other) > 0;
    }

    @Override
    public int compareTo(Money other) {
        assertSameCurrencyAs(other);
        if (amount < other.amount) {
            return -1;
        } else if (amount == other.amount) {
            return 0;
        } else {
            return 1;
        }
    }

    private void assertSameCurrencyAs(Money arg) {
        if (currency.equals(arg.currency)) {
            throw new IllegalArgumentException("money math mismatch");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Money money) {
            return currency.equals(money.currency) && amount == money.amount;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (amount ^ (amount >>> 32));
    }
}
