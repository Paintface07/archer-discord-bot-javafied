package org.kondrak.archer.bot.dao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 11/19/2016.
 */
public class StatementExecuter {
//    PreparedStatement st = conn.prepareStatement(
//            "SELECT user_id " +
//                    "FROM \"ARCHER\".users " +
//                    "WHERE user_id = ?"
//    );
//    st.setString(1, userId);
//    ResultSet rs = st.executeQuery();

    public static ResultSet execute(Connection conn, String query, Object... arguments) {
        try {
            PreparedStatement st = conn.prepareStatement(query);

        } catch(SQLException ex) {
            System.out.println("Could not prepare/execute query: " + query);
            ex.printStackTrace();
        }
        return null;
    }
}
