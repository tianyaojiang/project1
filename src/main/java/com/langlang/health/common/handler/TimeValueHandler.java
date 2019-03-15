package com.langlang.health.common.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by tyj on 2018/09/03.
 */
public class TimeValueHandler implements TypeHandler<String> {

    private SimpleDateFormat sd = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    @Override
    public String getResult(ResultSet rs, String str) throws SQLException {
        return sd.format(rs.getTimestamp(str));
    }

    @Override
    public String getResult(ResultSet arg0, int arg1) throws SQLException {
        return null;
    }

    @Override
    public String getResult(CallableStatement arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParameter(PreparedStatement arg0, int arg1, String arg2, JdbcType arg3) throws SQLException {

    }
}


