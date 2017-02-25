package org.kondrak.archer.bot.dao;

import org.kondrak.archer.bot.model.Archerism;
import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/24/2017.
 */
public class ArcherismDao {
    private final PGConnectionPoolDataSource ds;

    public ArcherismDao(PGConnectionPoolDataSource ds) {
        this.ds = ds;
    }

    public List<Archerism> getArcherisms() {
        PooledConnection pConn = null;
        try {
            pConn = ds.getPooledConnection();
            Connection conn = pConn.getConnection();
            PreparedStatement st = conn.prepareStatement(
                    "SELECT trigger_tx, msg_tx FROM archerisms WHERE trigger_tx = '!archerism'"
            );

            ResultSet s = st.executeQuery();
            List<Archerism> archerisms = new ArrayList<>();
            while(s.next()) {
                String trigger = s.getString(1);
                String text = s.getString(2);
                archerisms.add(new Archerism(trigger, text));
            }
            s.close();
            st.close();
            return archerisms;
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(pConn != null) {
                try {
                    pConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }
}
