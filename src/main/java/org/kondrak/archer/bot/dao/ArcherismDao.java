package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.dao.utils.DBOperation;
import org.kondrak.archer.bot.dao.utils.QueryExecutor;
import org.kondrak.archer.bot.dao.utils.StringParameter;
import org.kondrak.archer.bot.model.Archerism;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ArcherismDao {
    private final PGConnectionPoolDataSource ds;

    public ArcherismDao(PGConnectionPoolDataSource ds) {
        this.ds = ds;
    }

    public List<Archerism> getArcherisms() {
        String queryString = "SELECT trigger_tx, msg_tx FROM archerisms WHERE trigger_tx = '!archerism'";
        ResultSet resultSet = QueryExecutor.execute(ds, DBOperation.QUERY, queryString);

        try {
            List<Archerism> archerisms = new ArrayList<>();

            // TODO: refactor output processing to be more abstract so each query doesn't need to process its own distinct result set
            while(resultSet.next()) {
                String trigger = resultSet.getString(1);
                String text = resultSet.getString(2);
                archerisms.add(new Archerism(trigger, text));
            }

            return archerisms;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }
}
