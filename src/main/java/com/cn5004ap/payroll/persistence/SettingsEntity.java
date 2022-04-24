package com.cn5004ap.payroll.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "settings")
public class SettingsEntity
    extends BaseEntity
{
    @Column(name = "_key")
    private String _key;

    @Column(name = "_value")
    private String _value;

    public SettingsEntity()
    { }

    public SettingsEntity(String key, String value)
    {
        setKey(key);
        setValue(value);
    }

    public String getKey()
    {
        return _key;
    }

    public void setKey(String key)
    {
        this._key = key;
    }

    public String getValue()
    {
        return _value;
    }

    public void setValue(String value)
    {
        this._value = value;
    }

    @Override
    public String toString()
    {
        return "User [id=" + id + ", key=" + _key + ", value=" + _value + "]";
    }
}
