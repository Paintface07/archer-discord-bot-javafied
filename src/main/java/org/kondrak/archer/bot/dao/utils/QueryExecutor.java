package org.kondrak.archer.bot.dao.utils;

import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nosferatu on 7/12/17.
 */
public final class QueryExecutor {

    private QueryExecutor() {
        // empty by design
    }

    public static ResultSet execute(PGConnectionPoolDataSource ds, DBOperation op, String query, Parameter... arguments) {
        if(arguments.length > 0) {
            PooledConnection pConn = null;
            try {
                pConn = ds.getPooledConnection();
                Connection conn = pConn.getConnection();
                System.out.println(query);
                PreparedStatement st = conn.prepareStatement(query);

                for (int a = 0; a < arguments.length; a++) {
                    arguments[a].set(a + 1, st);
                }

                if(op == DBOperation.QUERY) {
                    return st.executeQuery();
                } else if(op == DBOperation.INSERT || op == DBOperation.DELETE) {
                    st.execute();
                } else {
                    throw new RuntimeException("An unspecified DBOperation was specified");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (pConn != null) {
                    try {
                        pConn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
