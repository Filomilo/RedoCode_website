package com.redocode.backend.database;

import com.redocode.backend.Messages.UtilContainers.Range;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class RangeNumberType implements UserType {
    /**
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, Object)
     */
    public Object assemble(Serializable cached, @SuppressWarnings("unused") Object owner) {
        return cached;
    }

    /**
     * @see org.hibernate.usertype.UserType#deepCopy(Object)
     */
    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }

        if (!(value instanceof java.util.Date)) {
            throw new UnsupportedOperationException("can't convert " + value.getClass());
        }
        Range range = (Range) value;
        return new Range(range.getMin(), range.getMax());
    }

    /**
     * @see org.hibernate.usertype.UserType#disassemble(Object)
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    /**
     * @see org.hibernate.usertype.UserType#equals(Object, Object)
     */
    public boolean equals(Object x, Object y) throws HibernateException {
        return x.equals(y);
    }

    /**
     * @see org.hibernate.usertype.UserType#hashCode(Object)
     */
    public int hashCode(Object value) throws HibernateException {
        return value.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {

    }

    /**
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable() {
        return true;
    }


    public Object nullSafeGet(ResultSet rs, String[] names, @SuppressWarnings("unused") Object owner)
            throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (value == null) {
            return null;
        }
        String[] parts = value.substring(1, value.length() - 1).split(",");
        Float start = parts[0].equals("") ? null : Float.parseFloat(parts[0]);
        Float end = parts[1].equals("") ? null : Float.parseFloat(parts[1]);
        return new Range(start, end);
    }

    public void nullSafeSet(PreparedStatement stmt, Object value, int index)
            throws HibernateException, SQLException {
        if (value == null) {
            stmt.setNull(index, Types.OTHER);
            return;
        }

        if (!(value instanceof Range)) {
            throw new UnsupportedOperationException("can't convert " + value.getClass());
        }
        Range range = (Range) value;
        String rangeStr = "[" + (range.getMin() == null ? "" : range.getMin()) + "," + (range.getMax() == null ? "" : range.getMax()) + ")";
        stmt.setObject(index, rangeStr, Types.OTHER);
    }

    public Object replace(Object original, @SuppressWarnings("unused") Object target, @SuppressWarnings("unused") Object owner) {
        return original;
    }

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @SuppressWarnings("unchecked")
    public Class returnedClass() {
        return RangeNumberType.class;
    }


    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }
}
