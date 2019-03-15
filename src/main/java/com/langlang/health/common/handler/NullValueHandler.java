package com.langlang.health.common.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tyj on 2018/09/03.
 */
public class NullValueHandler implements TypeHandler<String> {

    @Override
    public String getResult(ResultSet resultSet, String str) throws SQLException {
        String columnValue = resultSet.getString(str);
        if (StringUtils.isBlank(columnValue))
            return "";
        return columnValue;
    }

    @Override
    public String getResult(ResultSet resultSet, int index) throws SQLException {
        String columnValue = resultSet.getString(index);
        if (StringUtils.isBlank(columnValue))
            return "";
        return columnValue;
    }

    @Override
    public String getResult(CallableStatement cs, int index) throws SQLException {
        return null;
    }

    @Override
    public void setParameter(PreparedStatement pstmt, int index, String str, JdbcType type) throws SQLException {
    }

}


