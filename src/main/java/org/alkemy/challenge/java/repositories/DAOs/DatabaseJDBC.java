package org.alkemy.challenge.java.repositories.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseJDBC {

        //@Value("${jdbc.url.create.database}")
        private static String URL = "jdbc:mysql://localhost:3306/";
    
        //@Value("${spring.datasource.username}")
        private static String USER = "root";
    
        //@Value("${spring.datasource.password}")
        private static String PASSWORD = "admin";
    
        private static final String SQL = "CREATE DATABASE IF NOT EXISTS alkemy";
        
        public static void createDatabase(){
            try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()){
                if(statement.execute(SQL)){
                    System.out.println("[SUCCESS] Database successfull");
                }else{
                    System.out.println("[FAILURE] Database failure");
                }
            }catch(SQLException sqlException){
                sqlException.printStackTrace(System.out);
            }
        }
    
}
