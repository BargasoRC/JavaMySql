/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamysql;

import java.sql.*;

/**
 *
 * @author 2ndyrGroupC
 */
public class Database {
    //STEP 1. Import required packages

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    
    public int getID(){
        String query = "SELECT MAX('accountID') FROM account";
        return Integer.parseInt((String.valueOf(connect(query))));
    }

    public ResultSet createAccount(String username, String password) {
        String query = "INSERT INTO account (username,password)"
                + "VALUES ('" + username + "','" + password + "')";
        return connect(query);
    }

    public void createPersonalInfo(String firstName, String middleName, String lastName, int age) {
        String query = "INSERT INTO personalInformation (firstName, middleName, "
                + "lastName, age) VALUES ("+getID()+",'"+ firstName + "','" + middleName + "','"
                + lastName +"','"+age+"')";
        connect(query);
    }
    
    public void createSchedule( String subject,int unit, String time, String day){
        String query = "INSERT INTO course (accountID,unit, subject, time, day)"
                + "VALUES ("+getID()+",'"+subject+"',"+unit+",'"+time+"','"+day+"')";
        connect(query);
    }
    
    public void updatePersonalInfo(int id, String firstName, String middleName, String lastName, int age){
        String query = "UPDATE personalInformation SET firstName='"+firstName
        + "',middleName='"+middleName+"',lastName='"+lastName+"', age="+age+"";
        connect(query);
    }
    
    public void updateSchedule(int id,String subject,int unit, String time, String day){
        String query = "UPDATE course SET subject='"+subject
        + "',unit="+unit+",time='"+time+"', day='"+day+"'";
        connect(query);
    }
    
    public void retrievePersonalInfo(){
        String query = "SELECT * FROM personalInformation";
        ResultSet connect = connect(query);
        try{
            System.out.printf("%s%10s%20s%20s%20s%20s","ID","ACCOUNT_ID","FIRST_NAME",
                    "MIDDLE_NAME","LAST_NAME","AGE");
            while(connect.next()){
                int id = connect.getInt("id");
                int accountID = connect.getInt("accountID");
                String firstName = connect.getString("firstName");
                String middleName = connect.getString("middleName");
                String lastName = connect.getString("lastName");
                int age = connect.getInt("age");
                
                System.out.printf("%d%10d%20s%20s%20s%20d",id,accountID,firstName
                ,middleName,lastName,age);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void retrieveSchedule(){
        String query = "SELECT * FROM course";
        ResultSet connect = connect(query);
        try{
            System.out.printf("%s%10s%20s%20s%20s%20s","ID","ACCOUNT_ID","SUBJECT",
                    "UNIT","TIME","DAY");
            while(connect.next()){
                int id = connect.getInt("id");
                int accountID = connect.getInt("accountID");
                String subject = connect.getString("subject");
                int unit = connect.getInt("unit");
                String time = connect.getString("time");
                String day = connect.getString("day");
                
                System.out.printf("%d%10d%20s%20d%20s%20s",id,accountID,subject
                ,unit,time,day);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public void deletePersonalInfo(int id){
        String query = "DELETE FROM personalInformation WHERE id="+id;
    }
    
    public void deleteSchedule(int id){
        String query = "DELETE FROM course WHERE id="+id;
    }

    public ResultSet connect(String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = query;
            rs = stmt.executeQuery(sql);
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return rs;
    }//end main
}//end FirstExample

