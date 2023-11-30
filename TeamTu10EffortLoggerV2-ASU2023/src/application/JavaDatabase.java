package application;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class JavaDatabase {

    private static final String url = "jdbc:mysql://72.201.76.31:3306/cse360";
    private static final String username = "root"; 
    private static final String password = "Danalex3";

    // This method will check the database to see if the provided username and password are correct
    public boolean isUserValid(String enteredUsername, String enteredPassword) {
    	try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a SQL query to check if the user credentials are valid
            String query = "SELECT COUNT(*) FROM login WHERE login_username = ? AND login_password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            //ENCRYPT/DECRYPT SETUP
            String KeyBytes = "jlAuflVWNIxvviCXDNXYPdWmyjuFjlpJ";
            SecretKey secretKey = new SecretKeySpec(KeyBytes.getBytes(), "AES");
            byte[] fixedIV = new byte[16];
            Arrays.fill(fixedIV, (byte) 0);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(fixedIV));

            //ENCRYPT
            byte[] encryptedUsernameBytes = cipher.doFinal(enteredUsername.getBytes());
            String encryptedUsername = Base64.getEncoder().encodeToString(encryptedUsernameBytes);
            
            byte[] encryptedPasswordBytes = cipher.doFinal(enteredPassword.getBytes());
            String encryptedPassword = Base64.getEncoder().encodeToString(encryptedPasswordBytes);
            
            statement.setString(1, encryptedUsername);
            statement.setString(2, encryptedPassword);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return true if count is 1, indicating valid credentials
            return count == 1;
        } catch (Exception error) {
            System.out.println(error);
        }

        // If an exception occurs or the check fails, return false
        return false;
    }
    
    public boolean doesUserExist(String userToCheck) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a SQL query to check if the user exists
            String query = "SELECT COUNT(*) FROM login WHERE login_username = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            //ENCRYPT/DECRYPT SETUP
            String KeyBytes = "jlAuflVWNIxvviCXDNXYPdWmyjuFjlpJ";
            SecretKey secretKey = new SecretKeySpec(KeyBytes.getBytes(), "AES");
            byte[] fixedIV = new byte[16];
            Arrays.fill(fixedIV, (byte) 0);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(fixedIV));

            //ENCRYPT
            byte[] encryptedUsernameBytes = cipher.doFinal(userToCheck.getBytes());
            String encryptedUsername = Base64.getEncoder().encodeToString(encryptedUsernameBytes);

            statement.setString(1, encryptedUsername);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return true if count > 0, indicating that the user exists
            return count > 0;
        } catch (Exception error) {
            System.out.println(error);
        }

        // If an exception occurs or the check fails, return false
        return false;
    }

    public void addUser(String userToCheck, String passToAdd) {
        //database insert for user and pass
          try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection connection = DriverManager.getConnection(url, username, password);

              String query1 = "insert into login(login_username, login_password) VALUES(?,?)";
              PreparedStatement insertion= connection.prepareStatement(query1);

              //ENCRYPT/DECRYPT SETUP
              String KeyBytes = "jlAuflVWNIxvviCXDNXYPdWmyjuFjlpJ";
              SecretKey secretKey = new SecretKeySpec(KeyBytes.getBytes(), "AES");
              byte[] fixedIV = new byte[16];
              Arrays.fill(fixedIV, (byte) 0);
              Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
              cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(fixedIV));

              //ENCRYPT
              byte[] encryptedUsernameBytes = cipher.doFinal(userToCheck.getBytes());
              String encryptedUsername = Base64.getEncoder().encodeToString(encryptedUsernameBytes);

              byte[] encryptedPasswordBytes = cipher.doFinal(passToAdd.getBytes());
              String encryptedPassword = Base64.getEncoder().encodeToString(encryptedPasswordBytes);

              insertion.setString(1, encryptedUsername);
              insertion.setString(2, encryptedPassword);
              insertion.executeUpdate();
          } catch(Exception error) {
              System.out.println(error);
          }
      }

    public void addBacklogItem(String projectName, String userStory, String keyWords) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to insert data into the "backlog" table with story_point set as -1
            String logQuery = "INSERT INTO backlog (project_name, user_story, key_words, story_point) VALUES (?, ?, ?, ?)";
            PreparedStatement insertLog = connection.prepareStatement(logQuery);

            // Set the values for the place holders in the query
            insertLog.setString(1, projectName);
            insertLog.setString(2, userStory);
            insertLog.setString(3, keyWords);
            insertLog.setString(4, "-1"); // Set the story_point to -1

            // Execute the query to insert the data
            insertLog.executeUpdate();

            // Close the database connection
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    
    public String getBacklogProjectName(int index) {
        String projectName = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to find the index of a specific project in the "backlog" table
            String query = "SELECT project_name FROM backlog";
            Statement statement = connection.createStatement();

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery(query);

            int currentIndex = 0;
            while (resultSet.next()) {
                if (index == currentIndex) {
                    projectName = resultSet.getString("project_name");
                    break; // Found the item, exit the loop
                }
                currentIndex++;
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
        return projectName;
    }

    public String getBacklogUserStory(int index) {
        String userStory = null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to find the index of a specific project in the "backlog" table
            String query = "SELECT user_story FROM backlog";
            Statement statement = connection.createStatement();

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery(query);

            int currentIndex = 0;
            while (resultSet.next()) {
            	if(index == currentIndex) {
                	userStory = resultSet.getString("user_story");
                    break; // Found the item, exit the loop
                }
                currentIndex++;
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
        return userStory;
    }

    public String getBacklogKeyWords(int index) {
        String keyWords = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to find the index of a specific project in the "backlog" table
            String query = "SELECT key_words FROM backlog";
            Statement statement = connection.createStatement();

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery(query);

            int currentIndex = 0;
            while (resultSet.next()) {
                if (index == currentIndex) {
                    keyWords = resultSet.getString("key_words");
                    break; // Found the item, exit the loop
                }
                currentIndex++;
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
        return keyWords;
    }

    public int getBacklogSize() {
        int backlogSize = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to count the number of records in the "backlog" table
            String query = "SELECT COUNT(*) FROM backlog";
            Statement statement = connection.createStatement();

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                backlogSize = resultSet.getInt(1);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
        return backlogSize;
    }

    public int getStoryPoint(int index) {
        int storyPoint = -1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve the story point for a specific index in the "backlog" table
            String query = "SELECT story_point FROM backlog WHERE project_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, index);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                storyPoint = resultSet.getInt("story_point");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
        return storyPoint;
    }

    public void setStoryPoint(String newStoryPoint, String projectName, String userStory) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to update the story point for a specific index in the "backlog" table
            String query = "UPDATE backlog SET story_point = ? WHERE project_name = ? AND user_story = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newStoryPoint);
            statement.setString(2, projectName);
            statement.setString(3, userStory);
            // Execute the query
            statement.executeUpdate();

            // Close the resources
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
    
    public void insertProject(String projectOption, String stepOption, String categoryOption, String choiceOption){

        String url = "jdbc:mysql://localhost:3306/cse360"; // sql server link
        String username = "root"; // username for the sql server
        String password = "Danalex3";// password for the sql server
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // run java to sql driver

            Connection connection = DriverManager.getConnection(url, username, password); // opens the connection to server

            String logQuery = "insert into logs(project_name,lifecycle_step,category,choice) VALUES(?,?,?,?)"; // SQL command template
            PreparedStatement insertLog = connection.prepareStatement(logQuery); // preparing SQL command parameters
            insertLog.setString(1, projectOption); // add project parameter
            insertLog.setString(2, stepOption); // add life cycle step parameter
            insertLog.setString(3, categoryOption); // add category parameter
            insertLog.setString(4, choiceOption); // add choice parameter
            insertLog.executeUpdate(); // update database
            connection.close();// close the connection to server
        }

        catch (Exception error) { // print exception error
            System.out.println(error);
        }
    }
    
    public List<LogEntry> fetchHistoricalLogs() {
        List<LogEntry> logEntries = new ArrayList<>();

        String url = "jdbc:mysql://72.201.76.31:3306/cse360"; // SQL server link
        String username = "root"; // User name for the SQL server
        String password = "Danalex3"; // password for the SQL server

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver for java to sql
            Connection connection = DriverManager.getConnection(url, username, password); // opens the connection to database
            
            String query = "SELECT entry_id, user_story, project_name, lifecycle_step, category, choice, time_counted FROM logs"; //11.28.23 Change Armaani
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet logData = preparedStatement.executeQuery();

            //Statement callStatement = connection.createStatement(); // database command prep
            //ResultSet logData = callStatement.executeQuery("select * from logs"); // command execution
            while (logData.next()) {
                int entryId = logData.getInt("entry_id"); //11.28.23 Change Armaani
                String storyOption = logData.getString("user_story");
                String projectName = logData.getString("project_name"); // Get project name
                String cycleStep = logData.getString("lifecycle_step"); // Get lifecycle step
                String categoryOption = logData.getString("category"); // Get category
                String choiceOption = logData.getString("choice"); // Get choice
                long timeTaken = logData.getLong("time_counted"); // Get time counted

                logEntries.add(new LogEntry(entryId, projectName, storyOption, cycleStep, categoryOption, choiceOption, timeTaken)); // add new dataStructure object into datalist //11.28.23 Change Armaani
            }
            connection.close(); // close the connection to database
        } catch (Exception e) { // print error
            System.out.println(e);
        }
        return logEntries;
    }
    
    public String getEntryStep(String projectName) {
    	String entryStep = "";
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve the story point for a specific index in the "backlog" table
            String query = "SELECT lifecycle_step FROM logs WHERE project_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entryStep = resultSet.getString("lifecycle_step");
            }

            // Close the resources
      
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
    	return entryStep;
    }
    
    public String getEntryCat(String projectName) {
    	String entryCat = "";
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve the story point for a specific index in the "backlog" table
            String query = "SELECT category FROM logs WHERE project_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entryCat = resultSet.getString("category");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
    	return entryCat;
    }
    
    public String getEntryChoice(String projectName) {
    	String entryChoice = "";
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve the story point for a specific index in the "backlog" table
            String query = "SELECT choice FROM logs WHERE project_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entryChoice = resultSet.getString("choice");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
    	return entryChoice;
    }
    
    public long getEntryTime(String projectName) {
    	long entryTime = 0;
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve the story point for a specific index in the "backlog" table
            String query = "SELECT time_counted FROM logs WHERE project_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entryTime = resultSet.getLong("time_counted");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
    	return entryTime;
    }
    
    public void createBacklog() {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(url, username, password);
        	
        	// Create a SQL query to insert data into the "backlog" table with story_point set as -1
            String logQuery = "CREATE TABLE IF NOT EXISTS backlog(project_name VARCHAR(40), user_story VARCHAR(1000), key_words VARCHAR(500), story_point VARCHAR(40))";
            PreparedStatement createBacklog = connection.prepareStatement(logQuery);

            // Execute the query to insert the data
            createBacklog.executeUpdate();

            // Close the database connection
            connection.close();
        } catch(Exception error) {
        	System.out.println(error);
        }
    }
    
    public void createLogs() {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection(url, username, password);
        	
        	// Create a SQL query to insert data into the "backlog" table with story_point set as -1
            String logQuery = "CREATE TABLE IF NOT EXISTS logs(project_name VARCHAR(40), lifecycle_step VARCHAR(40), category VARCHAR(40), choice VARCHAR(40), time_counted LONG)";
            PreparedStatement createLogs = connection.prepareStatement(logQuery);

            // Execute the query to insert the data
            createLogs.executeUpdate();

            // Close the database connection
            connection.close();
        } catch(Exception error) {
        	System.out.println(error);
        }
    }
    
    public void createLogin() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a SQL query to insert data into the "backlog" table with story_point set as -1
            String logQuery = "CREATE TABLE IF NOT EXISTS login(login_username VARCHAR(50) PRIMARY KEY, login_password VARCHAR(50))";
            PreparedStatement createLogin = connection.prepareStatement(logQuery);

            // Execute the query to insert the data
            createLogin.executeUpdate();

            // Close the database connection
            connection.close();
        } catch(Exception error) {
            System.out.println(error);
        }
    }
    
    public List<String> getBacklogProjects() {
        List<String> backlogProjects = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve the distinct project names from the "backlog" table
            String query = "SELECT DISTINCT project_name FROM backlog";
            Statement statement = connection.createStatement();

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String projectName = resultSet.getString("project_name");
                backlogProjects.add(projectName);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }

        return backlogProjects;
    }
    
    public void updateLogEntry(int entryId, String step, String category, String choice) {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "UPDATE logs SET lifecycle_step = ?, category = ?, choice = ? WHERE entryId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, step);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, choice);
            preparedStatement.setInt(4, entryId);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public List<String> getStepsForProject(String projectName) {
        List<String> steps = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT DISTINCT lifecycle_step FROM logs WHERE project_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, projectName);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    steps.add(resultSet.getString("lifecycle_step"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return steps;
    }

    public List<String> getCategoriesForProject(String projectName) {
        List<String> categories = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT DISTINCT category FROM logs WHERE project_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, projectName);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    categories.add(resultSet.getString("category"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return categories;
    }

    public List<String> getChoicesForProject(String projectName) {
        List<String> choices = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT DISTINCT choice FROM logs WHERE project_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, projectName);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    choices.add(resultSet.getString("choice"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return choices;
    }
    
    public List<String> getAllSteps() {
        List<String> steps = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT DISTINCT lifecycle_step FROM logs"; // Adjust the table and column names as necessary
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    steps.add(resultSet.getString("lifecycle_step"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return steps;
    }
    
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT DISTINCT category FROM logs"; // Adjust table and column names as needed
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    categories.add(resultSet.getString("category"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return categories;
    }
    
    public List<String> getAllChoices() {
        List<String> choices = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT DISTINCT choice FROM logs"; // Adjust table and column names as needed
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    choices.add(resultSet.getString("choice"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return choices;
    }
    
    public List<String> getChoicesForCategory(String category) {
        List<String> choices = new ArrayList<>();
        if ("Interruptions".equals(category)) {
            choices.addAll(Arrays.asList("Break", "Phone", "Teammate", "Visitor", "Other"));
        } else if ("Defects".equals(category)) {
            choices.addAll(Arrays.asList("Not Specified", "Documentation", "Syntax", "Build, Package", "Assignment", "Interface", "Checking", "Data", "Function", "System", "Environment"));
        } else {
            // Your existing database code to fetch choices for other categories
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT DISTINCT choice FROM logs WHERE category = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, category);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    choices.add(resultSet.getString("choice"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return choices;
    }
    public void insertLogs(String a, String story, String b, String c, String d, long e){
    	 String url = "jdbc:mysql://72.201.76.31:3306/cse360"; // sql server link
    	 String projectOption = a;
    	 String storyOption = story;
    	 String stepOption = b;
    	 String categoryOption = c;
    	 String choiceOption = d;
    	 long durationSeconds = e;
			String username = "root"; // username for the sql server
			String password = "Danalex3";// password for the sql server
			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // run java to sql driver

				Connection connection = DriverManager.getConnection(url, username, password); // opens the connection to server

				String logQuery = "insert into logs(project_name,user_story,lifecycle_step,category,choice,time_counted) VALUES(?,?,?,?,?,?)"; // SQL command template
				PreparedStatement insertLog = connection.prepareStatement(logQuery); // preparing SQL command parameters
				insertLog.setString(1, projectOption); // add project parameter
				insertLog.setString(2, storyOption);
				insertLog.setString(3, stepOption); // add life cycle step parameter
				insertLog.setString(4, categoryOption); // add category parameter
				insertLog.setString(5, choiceOption); // add choice parameter
				insertLog.setLong(6, durationSeconds);
				insertLog.executeUpdate(); // update database
				connection.close();// close the connection to server
			}

			catch (Exception error) { // print exception error
				System.out.println(error);
			}
    }
    
    public String getUserStory(String projectName) {
        String userStory = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve the story point for a specific index in the "backlog" table
            String query = "SELECT user_story FROM backlog WHERE project_name = ? AND story_point = -1";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userStory = resultSet.getString("user_story");
            }

            // Close the resources

            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
        return userStory;
    }
    
    public boolean projectGenerated(String projectName) {
        boolean isProjectGenerated = true; // Assume project is generated unless proven otherwise

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to check if any entry exists for the specified project name with story_point = -1
            String query = "SELECT 1 FROM backlog WHERE project_name = ? AND story_point = -1 LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);

            // Execute the query and check if any result is returned
            ResultSet resultSet = statement.executeQuery();
            isProjectGenerated = !resultSet.next(); // If no result is returned, set isProjectGenerated to true

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }

        return isProjectGenerated;
    }
    public String getKeyWords(String projectName, String userStory) {
        String keywords = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver

            Connection connection = DriverManager.getConnection(url, username, password); // Establish a database connection

            // Create a SQL query to retrieve keywords for a specific project and user story
            String query = "SELECT key_words FROM backlog WHERE project_name = ? AND user_story = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);
            statement.setString(2, userStory);

            // Execute the query and get the result
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Assuming "keyword" is the name of the column storing keywords
                keywords = resultSet.getString("key_words"); // You can modify this based on how you want to concatenate the keywords
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception error) {
            System.out.println(error);
        }
        return keywords;
    }
    
    public int getValue() {
        //this method will return the number the user sent to the dynamic table
        return 0;
    }

    public void sendValue(int shareNumber) {
        //this method will send the number the user sent to the dynamic table

    }

    public int averageValue() {
        //this method will get all the values from the other projects and put it into a average
        return 0;
    }
    
} // end of class
