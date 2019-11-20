package com.seer.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoTools
{
    static public Integer getInteger( ResultSet rs, String strColName ) throws SQLException
    {
        int nValue = rs.getInt( strColName );
        return ( rs.wasNull() ) ? null : new Integer( nValue );
    }
    
    static public Long getLong( ResultSet rs, String strColName ) throws SQLException
    {
        long nValue = rs.getLong( strColName );
        return ( rs.wasNull() ) ? null : new Long( nValue );
    }
    
    static public Short getShort( ResultSet rs, String strColName ) throws SQLException
    {
        short nValue = rs.getShort( strColName );
        return ( rs.wasNull() ) ? null : new Short( nValue );
    }
    
    static public Double getDouble( ResultSet rs, String strColName ) throws SQLException
    {
        double nValue = rs.getDouble( strColName );
        return ( rs.wasNull() ) ? null : new Double( nValue );
    }
    
    static public Float getFloat( ResultSet rs, String strColName ) throws SQLException
    {
        float nValue = rs.getFloat( strColName );
        return ( rs.wasNull() ) ? null : new Float( nValue );
    }
}