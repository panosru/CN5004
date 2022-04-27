package com.cn5004ap.payroll.service;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.ISingleton;
import com.cn5004ap.payroll.common.Multiton;
import com.cn5004ap.payroll.persistence.EmployeeEntity;
import com.cn5004ap.payroll.persistence.EmployeeRepository;
import com.cn5004ap.payroll.persistence.SettingsEntity;
import com.cn5004ap.payroll.persistence.SettingsRepository;
import com.cn5004ap.payroll.persistence.UserEntity;
import com.cn5004ap.payroll.persistence.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoDataFeederService
    implements ISingleton
{
    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;

    private final SettingsRepository settingsRepository;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private boolean executed = false;

    private DemoDataFeederService()
    {
        // Prevent instantiation
        // Get repositories
        employeeRepository = Multiton.getInstance(EmployeeRepository.class);
        userRepository = Multiton.getInstance(UserRepository.class);
        settingsRepository = Multiton.getInstance(SettingsRepository.class);
    }

    public void execute()
    {
        SettingsEntity setting = settingsRepository.getSettingByKey("sampleDataLoaded");

        if (null != setting)
            executed = Boolean.parseBoolean(setting.getValue());

        try
        {
            if (!executed)
            {
                feedUsers();
                feedEmployees();
                feedSettings();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (!executed)
            {
                if (null == setting)
                    setting = settingsRepository.getSettingByKey("sampleDataLoaded");
                executed = true;
                setting.setValue(String.valueOf(executed));
                settingsRepository.save(setting);
            }
        }
    }

    public boolean sampleDataLoaded()
    {
        return executed;
    }

    private void feedUsers()
    {
        JSONArray jsonArray = readFromJSON("users");

        for (Object o : jsonArray)
        {
            JSONObject jsonObject = (JSONObject) o;

            userRepository.save(new UserEntity(
                (String) jsonObject.get("first_name"),
                (String) jsonObject.get("last_name"),
                (String) jsonObject.get("email"),
                (String) jsonObject.get("username"),
                (String) jsonObject.get("password")
            ));
        }
    }

    private void feedEmployees()
    {
        JSONArray jsonArray = readFromJSON("employees");

        for (Object o : jsonArray)
        {
            JSONObject jsonObject = (JSONObject) o;
            Date birth_date = null;
            Date employment_date = null;
            Date termination_date = null;

            try
            {
                birth_date = simpleDateFormat.parse((String) jsonObject.get("birth_date"));
                employment_date = simpleDateFormat.parse((String) jsonObject.get("employment_date"));
                if (null != jsonObject.get("termination_date"))
                    termination_date = simpleDateFormat.parse((String) jsonObject.get("termination_date"));
            }
            catch (java.text.ParseException e)
            {
                e.printStackTrace();
            }

            employeeRepository.save(new EmployeeEntity(
                (String) jsonObject.get("first_name"),
                (String) jsonObject.get("last_name"),
                (String) jsonObject.get("email"),
                (String) jsonObject.get("address"),
                (String) jsonObject.get("phone"),
                birth_date,
                (String) jsonObject.get("gender"),
                (String) jsonObject.get("iban"),
                (String) jsonObject.get("ssn"),
                (String) jsonObject.get("department"),
                (String) jsonObject.get("title"),
                ((Long)jsonObject.get("salary")).doubleValue(),
                (Boolean) jsonObject.get("active"),
                employment_date,
                termination_date
            ));
        }
    }

    private void feedSettings()
    {
        JSONArray jsonArray = readFromJSON("settings");

        for (Object o : jsonArray)
        {
            JSONObject jsonObject = (JSONObject) o;

            settingsRepository.save(new SettingsEntity(
                (String) jsonObject.get("key"),
                String.valueOf(jsonObject.get("value"))
            ));
        }
    }

    /**
     * Read JSON data.
     * @param jsonFile the JSON file
     * @return the JSON data
     */
    private JSONArray readFromJSON(String jsonFile)
    {
        JSONArray jsonArray = new JSONArray();

        try
        {
            JSONParser jsonParser = new JSONParser();

            jsonArray = (JSONArray) jsonParser.parse(
                new FileReader(App.getResource(String.format("data/%s.json", jsonFile)).getPath())
            );
        }
        catch (ParseException | IOException e)
        {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
