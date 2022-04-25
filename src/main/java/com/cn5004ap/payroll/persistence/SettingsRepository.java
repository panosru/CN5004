package com.cn5004ap.payroll.persistence;

public class SettingsRepository
    extends BaseRepository<SettingsEntity>
{
    private SettingsRepository()
    {
        super();
    }

    public SettingsEntity getSettingByKey(String key)
    {
        return findByX("_key", key);
    }
}
