package Imuniku.src.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract class BaseMapper mendefinisikan kontrak
 * yang harus diimplementasikan oleh semua Mapper.
 *
 * Konsep PBO: Inheritance, Abstraction
 * Pola desain: Data Mapper (ORM sederhana)
 *
 * @param <T> Tipe entity yang di-mapping
 */
public abstract class BaseMapper<T> {

    /**
     * Mengkonversi satu baris ResultSet
     * menjadi objek Java.
     * Ini adalah inti dari ORM Data Mapper.
     *
     * @param rs ResultSet dari hasil query SQL
     * @return objek T yang sudah terisi data
     */
    public abstract T mapRow(ResultSet rs)
        throws SQLException;

    /**
     * Mengkonversi semua baris ResultSet
     * menjadi List objek Java.
     * Menggunakan JCF ArrayList.
     */
    public abstract List<T> mapRows(ResultSet rs)
        throws SQLException;
}