package cz.jeme.programu.stolujemeapi.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class StoluStatementWrapper implements StatementWrapper {
    private @Nullable PreparedStatement statement;
    private final @NotNull Set<Integer> occupied = new HashSet<>();
    private int next = 1;

    @Override
    public @NotNull StoluStatementWrapper wrap(final @NotNull PreparedStatement statement) {
        this.statement = statement;
        return this;
    }

    @Override
    public @NotNull PreparedStatement unwrap() {
        if (statement == null)
            throw new IllegalStateException("Trying to unwrap whilst not wrapping anything!");
        PreparedStatement pointer = statement;
        clear();
        return pointer;
    }

    @Override
    public @NotNull StoluStatementWrapper clear() {
        statement = null;
        occupied.clear();
        next = 1;
        return this;
    }

    private int index() {
        while (occupied.contains(next)) next++;
        next++;
        return next - 1;
    }

    private void occupy(final int parameterIndex) {
        occupied.add(parameterIndex);
    }

    @Override
    public boolean isWrapped() {
        return statement != null;
    }

    private @NotNull PreparedStatement statement() {
        if (statement == null)
            throw new IllegalStateException("Trying to set contents whilst not wrapping anything!");
        return statement;
    }

    @Override
    public @NotNull StoluStatementWrapper setNull(final int parameterIndex, final int sqlType) throws SQLException {
        statement().setNull(parameterIndex, sqlType);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setNull(final int sqlType) throws SQLException {
        return setNull(index(), sqlType);
    }

    @Override
    public @NotNull StoluStatementWrapper setBoolean(final int parameterIndex, final boolean b) throws SQLException {
        statement().setBoolean(parameterIndex, b);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setBoolean(final boolean b) throws SQLException {
        return setBoolean(index(), b);
    }


    @Override
    public @NotNull StoluStatementWrapper setByte(final int parameterIndex, final byte b) throws SQLException {
        statement().setByte(parameterIndex, b);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setByte(final byte b) throws SQLException {
        return setByte(index(), b);
    }


    @Override
    public @NotNull StoluStatementWrapper setShort(final int parameterIndex, final short s) throws SQLException {
        statement().setShort(parameterIndex, s);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setShort(final short s) throws SQLException {
        return setShort(index(), s);
    }


    @Override
    public @NotNull StoluStatementWrapper setInt(final int parameterIndex, final int i) throws SQLException {
        statement().setInt(parameterIndex, i);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setInt(final int i) throws SQLException {
        return setInt(index(), i);
    }


    @Override
    public @NotNull StoluStatementWrapper setLong(final int parameterIndex, final long l) throws SQLException {
        statement().setLong(parameterIndex, l);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setLong(final long l) throws SQLException {
        return setLong(index(), l);
    }


    @Override
    public @NotNull StoluStatementWrapper setFloat(final int parameterIndex, final float f) throws SQLException {
        statement().setFloat(parameterIndex, f);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setFloat(final float f) throws SQLException {
        return setFloat(index(), f);
    }


    @Override
    public @NotNull StoluStatementWrapper setDouble(final int parameterIndex, final double d) throws SQLException {
        statement().setDouble(parameterIndex, d);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setDouble(final double d) throws SQLException {
        return setDouble(index(), d);
    }


    @Override
    public @NotNull StoluStatementWrapper setBigDecimal(final int parameterIndex, final @NotNull BigDecimal bigDecimal) throws SQLException {
        statement().setBigDecimal(parameterIndex, bigDecimal);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setBigDecimal(final @NotNull BigDecimal bigDecimal) throws SQLException {
        return setBigDecimal(index(), bigDecimal);
    }


    @Override
    public @NotNull StoluStatementWrapper setString(final int parameterIndex, final @NotNull String string) throws SQLException {
        statement().setString(parameterIndex, string);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setString(final @NotNull String string) throws SQLException {
        return setString(index(), string);
    }


    @Override
    public @NotNull StoluStatementWrapper setBytes(final int parameterIndex, final byte @NotNull [] bytes) throws SQLException {
        statement().setBytes(parameterIndex, bytes);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setBytes(final byte @NotNull [] bytes) throws SQLException {
        return setBytes(index(), bytes);
    }


    @Override
    public @NotNull StoluStatementWrapper setDate(final int parameterIndex, final @NotNull Date date) throws SQLException {
        statement().setDate(parameterIndex, date);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setDate(final @NotNull Date date) throws SQLException {
        return setDate(index(), date);
    }


    @Override
    public @NotNull StoluStatementWrapper setTime(final int parameterIndex, final @NotNull Time time) throws SQLException {
        statement().setTime(parameterIndex, time);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setTime(final @NotNull Time time) throws SQLException {
        return setTime(index(), time);
    }


    @Override
    public @NotNull StoluStatementWrapper setTimestamp(final int parameterIndex, final @NotNull Timestamp timestamp) throws SQLException {
        statement().setTimestamp(parameterIndex, timestamp);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setTimestamp(final @NotNull Timestamp timestamp) throws SQLException {
        return setTimestamp(index(), timestamp);
    }


    @Override
    public @NotNull StoluStatementWrapper setAsciiStream(final int parameterIndex, final @NotNull InputStream inputStream, int length) throws SQLException {
        statement().setAsciiStream(parameterIndex, inputStream, length);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setAsciiStream(final @NotNull InputStream inputStream, int length) throws SQLException {
        return setAsciiStream(index(), inputStream, length);
    }


    @Override
    public @NotNull StoluStatementWrapper setBinaryStream(final int parameterIndex, final @NotNull InputStream inputStream, final int length) throws SQLException {
        statement().setBinaryStream(parameterIndex, inputStream, length);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setBinaryStream(final @NotNull InputStream inputStream, final int length) throws SQLException {
        return setBinaryStream(index(), inputStream, length);
    }


    @Override
    public @NotNull StoluStatementWrapper setObject(final int parameterIndex, final @NotNull Object object, final int targetSqlType) throws SQLException {
        statement().setObject(parameterIndex, object, targetSqlType);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setObject(final @NotNull Object object, final int targetSqlType) throws SQLException {
        return setObject(index(), object, targetSqlType);
    }

    @Override
    public @NotNull StoluStatementWrapper setObject(final int parameterIndex, final @NotNull Object object) throws SQLException {
        statement().setObject(parameterIndex, object);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setObject(final @NotNull Object object) throws SQLException {
        return setObject(index(), object);
    }


    @Override
    public @NotNull StoluStatementWrapper setCharacterStream(final int parameterIndex, final @NotNull Reader reader, final int length) throws SQLException {
        statement().setCharacterStream(parameterIndex, reader, length);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setCharacterStream(final @NotNull Reader reader, final int length) throws SQLException {
        return setCharacterStream(index(), reader, length);
    }


    @Override
    public @NotNull StoluStatementWrapper setRef(final int parameterIndex, final @NotNull Ref ref) throws SQLException {
        statement().setRef(parameterIndex, ref);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setRef(final @NotNull Ref ref) throws SQLException {
        return setRef(index(), ref);
    }


    @Override
    public @NotNull StoluStatementWrapper setBlob(final int parameterIndex, final @NotNull Blob blob) throws SQLException {
        statement().setBlob(parameterIndex, blob);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setBlob(final @NotNull Blob blob) throws SQLException {
        return setBlob(index(), blob);
    }


    @Override
    public @NotNull StoluStatementWrapper setClob(final int parameterIndex, final @NotNull Clob clob) throws SQLException {
        statement().setClob(parameterIndex, clob);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setClob(final @NotNull Clob clob) throws SQLException {
        return setClob(index(), clob);
    }


    @Override
    public @NotNull StoluStatementWrapper setArray(final int parameterIndex, final @NotNull Array array) throws SQLException {
        statement().setArray(parameterIndex, array);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setArray(final @NotNull Array array) throws SQLException {
        return setArray(index(), array);
    }

    @Override
    public @NotNull StoluStatementWrapper setDate(final int parameterIndex, final @NotNull Date date, final @NotNull Calendar calendar) throws SQLException {
        statement().setDate(parameterIndex, date, calendar);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setDate(final @NotNull Date date, final @NotNull Calendar calendar) throws SQLException {
        return setDate(index(), date, calendar);
    }

    @Override
    public @NotNull StoluStatementWrapper setTime(final int parameterIndex, final @NotNull Time time, final @NotNull Calendar calendar) throws SQLException {
        statement().setTime(parameterIndex, time, calendar);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setTime(final @NotNull Time time, final @NotNull Calendar calendar) throws SQLException {
        return setTime(index(), time, calendar);
    }

    @Override
    public @NotNull StoluStatementWrapper setTimestamp(final int parameterIndex, final @NotNull Timestamp timestamp, final @NotNull Calendar calendar) throws SQLException {
        statement().setTimestamp(parameterIndex, timestamp, calendar);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setTimestamp(final @NotNull Timestamp timestamp, final @NotNull Calendar calendar) throws SQLException {
        return setTimestamp(index(), timestamp, calendar);
    }

    @Override
    public @NotNull StoluStatementWrapper setNull(final int parameterIndex, final int sqlType, final @NotNull String typeName) throws SQLException {
        statement().setNull(parameterIndex, sqlType, typeName);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setNull(final int sqlType, final @NotNull String typeName) throws SQLException {
        return setNull(index(), sqlType, typeName);
    }


    @Override
    public @NotNull StoluStatementWrapper setURL(final int parameterIndex, final @NotNull URL url) throws SQLException {
        statement().setURL(parameterIndex, url);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setURL(final @NotNull URL url) throws SQLException {
        return setURL(index(), url);
    }


    @Override
    public @NotNull StoluStatementWrapper setRowId(final int parameterIndex, final @NotNull RowId rowId) throws SQLException {
        statement().setRowId(parameterIndex, rowId);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setRowId(final @NotNull RowId rowId) throws SQLException {
        return setRowId(index(), rowId);
    }


    @Override
    public @NotNull StoluStatementWrapper setNString(final int parameterIndex, final @NotNull String value) throws SQLException {
        statement().setNString(parameterIndex, value);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setNString(final @NotNull String value) throws SQLException {
        return setNString(index(), value);
    }


    @Override
    public @NotNull StoluStatementWrapper setNCharacterStream(final int parameterIndex, final @NotNull Reader value, long length) throws SQLException {
        statement().setNCharacterStream(parameterIndex, value, length);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setNCharacterStream(final @NotNull Reader value, long length) throws SQLException {
        return setNCharacterStream(index(), value, length);
    }


    @Override
    public @NotNull StoluStatementWrapper setNClob(final int parameterIndex, final @NotNull NClob value) throws SQLException {
        statement().setNClob(parameterIndex, value);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setNClob(final @NotNull NClob value) throws SQLException {
        return setNClob(index(), value);
    }

    @Override
    public @NotNull StoluStatementWrapper setClob(final int parameterIndex, final @NotNull Reader reader, final long length) throws SQLException {
        statement().setClob(parameterIndex, reader, length);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setClob(final @NotNull Reader reader, final long length) throws SQLException {
        return setClob(index(), reader, length);
    }

    @Override
    public @NotNull StoluStatementWrapper setBlob(final int parameterIndex, final @NotNull InputStream inputStream, final long length) throws SQLException {
        statement().setBlob(parameterIndex, inputStream, length);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setBlob(final @NotNull InputStream inputStream, final long length) throws SQLException {
        return setBlob(index(), inputStream, length);
    }

    @Override
    public @NotNull StoluStatementWrapper setNClob(final int parameterIndex, final @NotNull Reader reader, final long length) throws SQLException {
        statement().setNClob(parameterIndex, reader, length);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setNClob(final @NotNull Reader reader, final long length) throws SQLException {
        return setNClob(index(), reader, length);
    }


    @Override
    public @NotNull StoluStatementWrapper setSQLXML(final int parameterIndex, final @NotNull SQLXML xmlObject) throws SQLException {
        statement().setSQLXML(parameterIndex, xmlObject);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setSQLXML(final @NotNull SQLXML xmlObject) throws SQLException {
        return setSQLXML(index(), xmlObject);
    }


    @Override
    public @NotNull StoluStatementWrapper setObject(final int parameterIndex, final @NotNull Object object, final int targetSqlType, final int scaleOrLength) throws SQLException {
        statement().setObject(parameterIndex, object, targetSqlType, scaleOrLength);
        occupy(parameterIndex);
        return this;
    }


    @Override
    public @NotNull StoluStatementWrapper setObject(final @NotNull Object object, final int targetSqlType, final int scaleOrLength) throws SQLException {
        return setObject(index(), object, targetSqlType, scaleOrLength);
    }

    @Override
    public @NotNull StoluStatementWrapper setAsciiStream(final int parameterIndex, final @NotNull InputStream inputStream, final long length) throws SQLException {
        statement().setAsciiStream(parameterIndex, inputStream, length);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setAsciiStream(final @NotNull InputStream inputStream, final long length) throws SQLException {
        return setAsciiStream(index(), inputStream, length);
    }

    @Override
    public @NotNull StoluStatementWrapper setBinaryStream(final int parameterIndex, final @NotNull InputStream inputStream, final long length) throws SQLException {
        statement().setBinaryStream(parameterIndex, inputStream, length);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setBinaryStream(final @NotNull InputStream inputStream, final long length) throws SQLException {
        return setBinaryStream(index(), inputStream, length);
    }

    @Override
    public @NotNull StoluStatementWrapper setCharacterStream(final int parameterIndex, final @NotNull Reader reader, final long length) throws SQLException {
        statement().setCharacterStream(parameterIndex, reader, length);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setCharacterStream(final @NotNull Reader reader, final long length) throws SQLException {
        return setCharacterStream(index(), reader, length);
    }

    @Override
    public @NotNull StoluStatementWrapper setAsciiStream(final int parameterIndex, final @NotNull InputStream inputStream) throws SQLException {
        statement().setAsciiStream(parameterIndex, inputStream);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setAsciiStream(final @NotNull InputStream inputStream) throws SQLException {
        return setAsciiStream(index(), inputStream);
    }

    @Override
    public @NotNull StoluStatementWrapper setBinaryStream(final int parameterIndex, final @NotNull InputStream inputStream) throws SQLException {
        statement().setBinaryStream(parameterIndex, inputStream);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setBinaryStream(final @NotNull InputStream inputStream) throws SQLException {
        return setBinaryStream(index(), inputStream);
    }

    @Override
    public @NotNull StoluStatementWrapper setCharacterStream(final int parameterIndex, final @NotNull Reader reader) throws SQLException {
        statement().setCharacterStream(parameterIndex, reader);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setCharacterStream(final @NotNull Reader reader) throws SQLException {
        return setCharacterStream(index(), reader);
    }

    @Override
    public @NotNull StoluStatementWrapper setNCharacterStream(final int parameterIndex, final @NotNull Reader reader) throws SQLException {
        statement().setNCharacterStream(parameterIndex, reader);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setNCharacterStream(final @NotNull Reader reader) throws SQLException {
        return setNCharacterStream(index(), reader);
    }

    @Override
    public @NotNull StoluStatementWrapper setClob(final int parameterIndex, final @NotNull Reader reader) throws SQLException {
        statement().setClob(parameterIndex, reader);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setClob(final @NotNull Reader reader) throws SQLException {
        return setClob(index(), reader);
    }

    @Override
    public @NotNull StoluStatementWrapper setBlob(final int parameterIndex, final @NotNull InputStream inputStream) throws SQLException {
        statement().setBlob(parameterIndex, inputStream);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setBlob(final @NotNull InputStream inputStream) throws SQLException {
        return setBlob(index(), inputStream);
    }

    @Override
    public @NotNull StoluStatementWrapper setNClob(final int parameterIndex, final @NotNull Reader reader) throws SQLException {
        statement().setNClob(parameterIndex, reader);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setNClob(final @NotNull Reader reader) throws SQLException {
        return setNClob(index(), reader);
    }

    @Override
    public @NotNull StoluStatementWrapper setObject(final int parameterIndex,
                                                    final @NotNull Object object,
                                                    final @NotNull SQLType targetSqlType,
                                                    final int scaleOrLength) throws SQLException {
        statement().setObject(parameterIndex, object, targetSqlType, scaleOrLength);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setObject(final @NotNull Object object,
                                                    final @NotNull SQLType targetSqlType,
                                                    final int scaleOrLength) throws SQLException {
        return setObject(index(), object, targetSqlType, scaleOrLength);
    }

    @Override
    public @NotNull StoluStatementWrapper setObject(final int parameterIndex,
                                                    final @NotNull Object object,
                                                    final @NotNull SQLType targetSqlType) throws SQLException {
        statement().setObject(parameterIndex, object, targetSqlType);
        occupy(parameterIndex);
        return this;
    }

    @Override
    public @NotNull StoluStatementWrapper setObject(final @NotNull Object object,
                                                    final @NotNull SQLType targetSqlType) throws SQLException {
        return setObject(index(), object, targetSqlType);
    }
}
