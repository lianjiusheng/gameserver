package com.ljs.mg.gameserver.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ljs.mg.gameserver.entry.SkillInfo;
import com.ljs.mg.util.JSONUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(SkillInfo.class)
public class SkillInfoTypeHandler implements TypeHandler<SkillInfo> {
    @Override
    public void setParameter(PreparedStatement ps, int i, SkillInfo parameter, JdbcType jdbcType) throws SQLException {

        String s= null;
        try {
            s = JSONUtil.getJSONString(parameter);
        } catch (JsonProcessingException e) {

            throw new  SQLException(e);
        }
        ps.setString(i,s);
    }

    @Override
    public SkillInfo getResult(ResultSet rs, String columnName) throws SQLException {

        String s=rs.getString(columnName);
        try {
            return  JSONUtil.readObject(SkillInfo.class,s);
        } catch (IOException e) {
            throw new  SQLException(e);
        }
    }

    @Override
    public SkillInfo getResult(ResultSet rs, int columnIndex) throws SQLException {

        String s=rs.getString(columnIndex);
        try {
            return  JSONUtil.readObject(SkillInfo.class,s);
        } catch (IOException e) {
            throw new  SQLException(e);
        }
    }

    @Override
    public SkillInfo getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s=cs.getString(columnIndex);
        try {
            return  JSONUtil.readObject(SkillInfo.class,s);
        } catch (IOException e) {
            throw new  SQLException(e);
        }
    }
}
