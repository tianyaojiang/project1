package com.langlang.health.common.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tyj on 2019/01/11.
 */
public class ToolKit {

    public static String getBillNumber(String type){
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet res = null;
        String procName = "call getBillNumber(?,?)";
        String result = "";
        try {

            stmt = conn.prepareCall(procName);
            stmt.setString(1, type);
            stmt.setInt(2, 14);
            stmt.execute();
            res = stmt.getResultSet();
            while(res.next()){
                result = res.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{

        }
        return result;
    }
}
