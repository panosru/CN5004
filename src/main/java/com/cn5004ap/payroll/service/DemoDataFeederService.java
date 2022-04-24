package com.cn5004ap.payroll.service;

import com.cn5004ap.payroll.App;
import com.cn5004ap.payroll.common.ISingleton;
import com.cn5004ap.payroll.common.Multiton;
import com.cn5004ap.payroll.persistence.EmployeeEntity;
import com.cn5004ap.payroll.persistence.EmployeeRepository;
import com.cn5004ap.payroll.persistence.UserEntity;
import com.cn5004ap.payroll.persistence.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class DemoDataFeederService
    implements ISingleton
{
    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;

    private boolean executed = false;

    private DemoDataFeederService()
    {
        // Prevent instantiation
        // Get repositories
        employeeRepository = Multiton.getInstance(EmployeeRepository.class);
        userRepository = Multiton.getInstance(UserRepository.class);
    }

    public void execute()
    {
        try
        {
            if (!executed)
            {
                feedUsers();
                feedEmployees();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            executed = true;
        }
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

            employeeRepository.save(new EmployeeEntity(
                (String) jsonObject.get("first_name"),
                (String) jsonObject.get("last_name"),
                (String) jsonObject.get("email"),
                (String) jsonObject.get("department"),
                (String) jsonObject.get("title"),
                ((Long)jsonObject.get("salary")).doubleValue()
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
