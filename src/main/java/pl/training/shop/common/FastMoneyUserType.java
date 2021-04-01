package pl.training.shop.common;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.javamoney.moneta.FastMoney;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class FastMoneyUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{"currency","amount"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{StringType.INSTANCE, LongType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int propertyIndex) throws HibernateException {
        if (component==null) {
            return null;
        }
        var money = (FastMoney)component;
        switch (propertyIndex) {
            case 0:
                return money.getCurrency().getCurrencyCode();
            case 1:
                return money.getNumber().numberValue(Long.class);
            default:
                throw new HibernateException("Invalid property index ["+propertyIndex+"]");
        }
    }

    @Override
    public void setPropertyValue(Object component, int propertyIndex, Object value) throws HibernateException {
        if (component==null) {
            return ;
        }
        throw new HibernateException("Called setPropertyValue on an immutable type {"+component.getClass()+"}");
    }

    @Override
    public Class<FastMoney> returnedClass() {
        return FastMoney.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return Objects.equals(o,o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        assert names.length ==2;
        FastMoney money= null;
        var currency = resultSet.getString(names[0]);
        if (!resultSet.wasNull()) {
            var amount = resultSet.getLong(names[1]);
            money = FastMoney.of(amount,currency);
        }
        return money;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value==null) {
            preparedStatement.setNull(property,StringType.INSTANCE.sqlType());
            preparedStatement.setNull(property+1, LongType.INSTANCE.sqlType());
        } else {
            FastMoney amount = (FastMoney) value;
            preparedStatement.setString(property,amount.getCurrency().getCurrencyCode());
            preparedStatement.setDouble(property+1,amount.getNumber().numberValue(Long.class));
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, SharedSessionContractImplementor sharedSessionContractImplementor, Object o2) throws HibernateException {
        return o;
    }
}
