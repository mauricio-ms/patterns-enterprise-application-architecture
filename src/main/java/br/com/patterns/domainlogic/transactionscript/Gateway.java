package br.com.patterns.domainlogic.transactionscript;

import br.com.patterns.base.Money;
import br.com.patterns.datasource.Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public record Gateway(Database db) {

    public void insertContract(long contractId, long productId,
                               Money revenue, LocalDate dateSigned) throws SQLException {
        try (var stmt = db.getCon().prepareStatement(INSERT_CONTRACT_STATEMENT)) {
            stmt.setLong(1, contractId);
            stmt.setLong(2, productId);
            stmt.setBigDecimal(3, revenue.amount());
            stmt.setDate(4, Date.valueOf(dateSigned));
            stmt.executeUpdate();
        }
    }

    private static final String INSERT_CONTRACT_STATEMENT = """
            INSERT INTO contracts VALUES (?, ?, ?, ?)
            """;

    public void insertProduct(long productId, String name, String type) throws SQLException {
        try (var stmt = db.getCon().prepareStatement(INSERT_PRODUCT_STATEMENT)) {
            stmt.setLong(1, productId);
            stmt.setString(2, name);
            stmt.setString(3, type);
            stmt.executeUpdate();
        }
    }

    private static final String INSERT_PRODUCT_STATEMENT = """
            INSERT INTO products VALUES (?, ?, ?)
            """;

    public ResultSet findRecognitionsFor(long contractId, LocalDate date) throws SQLException {
        var stmt = db.getCon().prepareStatement(FIND_RECOGNITIONS_STATEMENT);
        stmt.setLong(1, contractId);
        stmt.setDate(2, Date.valueOf(date));
        return stmt.executeQuery();
    }

    private static final String FIND_RECOGNITIONS_STATEMENT = """
            SELECT amount
            FROM revenue_recognitions
            WHERE contract = ? AND recognized_on <= ?
            """;

    public ResultSet findContract(long contractId) throws SQLException {
        var stmt = db.getCon().prepareStatement(FIND_CONTRACT_STATEMENT);
        stmt.setLong(1, contractId);
        return stmt.executeQuery();
    }

    private static final String FIND_CONTRACT_STATEMENT = """
            SELECT *
            FROM contracts c, products p
            WHERE c.id = ? AND c.product = p.id
            """;

    public void insertRecognition(long contractId, Money amount, LocalDate asOf) throws SQLException {
        try (var stmt = db.getCon().prepareStatement(INSERT_RECOGNITION_STATEMENT)) {
            stmt.setLong(1, contractId);
            stmt.setBigDecimal(2, amount.amount());
            stmt.setDate(3, Date.valueOf(asOf));
            stmt.executeUpdate();
        }
    }

    private static final String INSERT_RECOGNITION_STATEMENT = """
            INSERT INTO revenue_recognitions VALUES (?, ?, ?)
            """;
}
