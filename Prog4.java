/*
 * Prog3.java -- A program that connects to an Oracle DB to access
 * tables that are queried given a users input.
 * To compile and execute this program on lectura:
 *
 *   Add the Postgres JDBC driver to your CLASSPATH environment variable:
 *
 *         export CLASSPATH=/opt/oracle/product/10.2.0/client/jdbc/lib/ojdbc14.jar:${CLASSPATH}
 *
 *     (or whatever shell variable set-up you need to perform to add the
 *     JAR file to your Java CLASSPATH)
 *
 *   Compile this file:
 *
 *         javac JDBC.java
 *
 *   Finally, run the program:
 *
 *         java JDBC <oracle username> <oracle password>
 *
 * Author:  Tanner Koch
 * Class: CS460
 * Assignment: Project 3
 * Instructor: Mccann, Yang
 * Prompt the user to choose from a menu of queries, then with a user's choice, execute the query
 * in Oracle.
 * 
 * I simply used a text editor to cleanse my files.
 */

import java.io.*;
import java.sql.*;                 // For access to the SQL interaction methods
import java.util.Scanner;

public class Prog4
{
    public static void main (String [] args)
    {

        final String oracleURL =   // Magic lectura -> aloe access spell
                        "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";


        String username = null,    // Oracle DBMS username
               password = null;    // Oracle DBMS password


        if (args.length == 2) {    // get username/password from cmd line args
            username = args[0];
            password = args[1];
        } else {
            System.out.println("\nUsage:  java JDBC <username> <password>\n"
                             + "    where <username> is your Oracle DBMS"
                             + " username,\n    and <password> is your Oracle"
                             + " password (not your system password).\n");
            System.exit(-1);
        }


            // load the (Oracle) JDBC driver by initializing its base
            // class, 'oracle.jdbc.OracleDriver'.

        try {

                Class.forName("oracle.jdbc.OracleDriver");

        } catch (ClassNotFoundException e) {

                System.err.println("*** ClassNotFoundException:  "
                    + "Error loading Oracle JDBC driver.  \n"
                    + "\tPerhaps the driver is not on the Classpath?");
                System.exit(-1);

        }


            // make and return a database connection to the user's
            // Oracle database

        Connection dbconn = null;

        try {
                dbconn = DriverManager.getConnection
                               (oracleURL,username,password);

        } catch (SQLException e) {

                System.err.println("*** SQLException:  "
                    + "Could not open JDBC connection.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);

        }


            // Send the query to the DBMS, and get and display the results
        int response = 0;
        int exceeds = 0; //will be used for query4
        Scanner s = new Scanner(System.in);
		while (response != -1) { //if the user enters -1, quit the program
			System.out.println("Choose from the menu.(-1 to quit)");
			System.out.println("1.How many Junior High (also known as Middle) schools are listed in the 2013 results?");
			System.out.println("2. One of the columns in the spreadsheets is titled Math Percent Falls Far Below."
					+ " What are the"
					+ " names of the schools (and their LEA names) that managed to produce a strictly decreasing sequence"
					+ " of this statistic over the four years? ");
			System.out.println("3. For each of the four subject areas (Math, Reading, Writing, and Science), list,"
					+ " in decreasing order, the top five schools based on Mean Scale Score. ");
			System.out.println("4. For the years 2010 and 2011, list the schools with a specified percentage that exceeds"
					+ " the passing math score ");
			response = s.nextInt();
			if(response == -1){
				break;
			}
			if(response == 1){ //choice 1
				queryOne(dbconn);
			}
			if(response == 2){ //choice 2
				queryTwo(dbconn);
			}
			if(response == 3){ //choice 3
				queryThree(dbconn);
			}
			if(response == 4){ //choice 4
				System.out.println("Enter a percentage: ");
				exceeds = s.nextInt();
				queryFour(dbconn, exceeds);
			}

		}
		s.close();

    }
    /*Method: queryFour(Connection, integer)
     * The purpose of this method is to execute the fourth query
     * from the menu in the main method, given a certain "exceeds" 
     * percentage parameter.  Once the query reaches the percentage,
     * the method will exit.
     */
	private static void queryFour(Connection dbconn, int exceeds) {
        Statement stmt = null;
        ResultSet answer = null;
        String query = "select math_exceeds, school_name"
        		+ " from tkoch.aims2010"
        		+ " union all"
        		+ " select math_exceeds, school_name"
        		+ " from tkoch.aims2011"
        		+ " order by math_exceeds desc"; //SQL query
        try {

            stmt = dbconn.createStatement();
            answer = stmt.executeQuery(query);

            if (answer != null) {

                System.out.println("\nThe results of the query are:\n");

                    // Use next() to advance cursor through the result
                    // tuples and print their attribute values
                int result;
				while (answer.next()) {
					result = answer.getInt("math_exceeds");
					if(result == 0){
						continue;
					}
					else if(result <= exceeds){
						break;
					}
					else{
						System.out.println("School: " + answer.getString("school_name") + " Score: " + result);
					}
				}
            }
            System.out.println();

                // Shut down the connection to the DBMS.

            stmt.close();  
            dbconn.close();

        } catch (SQLException e) {

                System.err.println("*** SQLException:  "
                    + "Could not fetch query results.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);

        }				
	}
    /*Method: queryFour(Connection)
     * The purpose of this method is to execute the third query
     * from the menu in the main method.  For all of the
     * mean scale scores, this will print the top 5 for each
     * subject.
     */
	private static void queryThree(Connection dbconn) {
	       Statement stmt = null;
	       Statement stmt2 = null;
	       Statement stmt3 = null;
	       Statement stmt4 = null;
	        ResultSet answer = null;
	        ResultSet answer2 = null;
	        ResultSet answer3 = null;
	        ResultSet answer4 = null;
	        String query = "select mean_math_scale_score, school_name"
	        		+ " from tkoch.aims2010"
	        		+ " union all"
	        		+ " select mean_math_scale_score, school_name"
	        		+ " from tkoch.aims2011"
	        		+ " union all"
	        		+ " select mean_math_scale_score, school_name"
	        		+ " from tkoch.aims2012"
	        		+ " union all"
	        		+ " select mean_math_scale_score, school_name"
	        		+ " from tkoch.aims2013"
	        		+ " order by mean_math_scale_score desc"; //query for math
	        String query2 = "select mean_reading_scale_score, school_name"
	        		+ " from tkoch.aims2010"
	        		+ " union all"
	        		+ " select mean_reading_scale_score, school_name"
	        		+ " from tkoch.aims2011"
	        		+ " union all"
	        		+ " select mean_reading_scale_score, school_name"
	        		+ " from tkoch.aims2012"
	        		+ " union all"
	        		+ " select mean_reading_scale_score, school_name"
	        		+ " from tkoch.aims2013"
	        		+ " order by mean_reading_scale_score desc"; //query for reading
	        String query3 = "select mean_writing_scale_score, school_name"
	        		+ " from tkoch.aims2010"
	        		+ " union all"
	        		+ " select mean_writing_scale_score, school_name"
	        		+ " from tkoch.aims2011"
	        		+ " union all"
	        		+ " select mean_writing_scale_score, school_name"
	        		+ " from tkoch.aims2012"
	        		+ " union all"
	        		+ " select mean_writing_scale_score, school_name"
	        		+ " from tkoch.aims2013"
	        		+ " order by mean_writing_scale_score desc"; //query for writing
	        String query4 = "select mean_science_scale_score, school_name"
	        		+ " from tkoch.aims2010"
	        		+ " union all"
	        		+ " select mean_science_scale_score, school_name"
	        		+ " from tkoch.aims2011"
	        		+ " union all"
	        		+ " select mean_science_scale_score, school_name"
	        		+ " from tkoch.aims2012"
	        		+ " union all"
	        		+ " select mean_science_scale_score, school_name"
	        		+ " from tkoch.aims2013"
	        		+ " order by mean_science_scale_score desc"; //query for science
	        try {

	            stmt = dbconn.createStatement();
	            stmt2 = dbconn.createStatement();
	            stmt3 = dbconn.createStatement();
	            stmt4 = dbconn.createStatement();

	            answer = stmt.executeQuery(query);
	            answer2 = stmt2.executeQuery(query2);
	            answer3 = stmt3.executeQuery(query3);
	            answer4 = stmt4.executeQuery(query4);

	            if (answer != null) {

	                System.out.println("\nThe results of the query are:\n");

	                    // Use next() to advance cursor through the result
	                    // tuples and print their attribute values
	                int result;
	                int count = 0;
					System.out.println("Top 5 math:");
					while (answer.next() && count < 5) {
						result = answer.getInt("mean_math_scale_score");
						if(result == 0){
							continue;
						}
						else{
							System.out.println("School: " + answer.getString("school_name") + " Score: " + result);
							count++;
						}
					}
	            }
	            System.out.println();

	            if (answer2 != null) {

	                    // Use next() to advance cursor through the result
	                    // tuples and print their attribute values
	                int result;
	                int count = 0;
					System.out.println("Top 5 reading:");
					while (answer2.next() && count < 5) {
						result = answer2.getInt("mean_reading_scale_score");
						if(result == 0){
							continue;
						}
						else{
							System.out.println("School: " + answer2.getString("school_name") + " Score: " + result);
							count++;
						}
					}
	            }
	            System.out.println();
	            if (answer3 != null) {

	                    // Use next() to advance cursor through the result
	                    // tuples and print their attribute values
	                int result;
	                int count = 0;
					System.out.println("Top 5 writing:");
					while (answer3.next() && count < 5) {
						result = answer3.getInt("mean_writing_scale_score");
						if(result == 0){
							continue;
						}
						else{
							System.out.println("School: " + answer3.getString("school_name") + " Score: " + result);
							count++;
						}
					}
	            }
	            System.out.println();
	            if (answer4 != null) {

	                    // Use next() to advance cursor through the result
	                    // tuples and print their attribute values
	                int result;
	                int count = 0;
					System.out.println("Top 5 science:");
					while (answer4.next() && count < 5) {
						result = answer4.getInt("mean_science_scale_score");
						if(result == 0){
							continue;
						}
						else{
							System.out.println("School: " + answer4.getString("school_name") + " Score: " + result);
							count++;
						}
					}
	            }
	            System.out.println();
	                // Shut down the connection to the DBMS.

	            stmt.close(); 
	            stmt2.close(); 
	            stmt3.close(); 
	            stmt4.close(); 
 
	            dbconn.close();

	        } catch (SQLException e) {

	                System.err.println("*** SQLException:  "
	                    + "Could not fetch query results.");
	                System.err.println("\tMessage:   " + e.getMessage());
	                System.err.println("\tSQLState:  " + e.getSQLState());
	                System.err.println("\tErrorCode: " + e.getErrorCode());
	                System.exit(-1);

	        }		
	}
    /*Method: queryFour(Connection)
     * The purpose of this method is to execute the second query
     * from the menu in the main method.  This will find all schools
     * whos farbelow scores decrease each year.
     */
	private static void queryTwo(Connection dbconn) {
        Statement stmt = null;
        ResultSet answer = null;
        String query3 = "drop view farbelow";
		String query = "create view farbelow "
				+ "(\"school_name\", \"lea_name\", \"2010\", \"2011\", \"2012\", \"2013\")"
						+ " as "
						+ "select first.school_name, first.lea_name,first.math_farbelow, second.math_farbelow, third.math_farbelow,fourth.math_farbelow "
						+ "from tkoch.aims2010 first, tkoch.aims2011 second, tkoch.aims2012 third, tkoch.aims2013 fourth "
						+ "where first.school_ctds=second.school_ctds and first.school_ctds=third.school_ctds and first.school_ctds=fourth.school_ctds";
		String query2 = "select * from farbelow";
        try {

            stmt = dbconn.createStatement();
            try{
            stmt.execute(query3);
            }
            catch(SQLException e){
            	System.out.println("Will create the view");
            }
            stmt.execute(query);
            answer = stmt.executeQuery(query2);

            if (answer != null) {

                System.out.println("\nThe results of the query are:\n");

                    // Get the data about the query result to learn
                    // the attribute names and use them as column headers

                ResultSetMetaData answermetadata = answer.getMetaData();

//                for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
//                    System.out.print(answermetadata.getColumnName(i) + "\t");
//                }
                System.out.println();

                    // Use next() to advance cursor through the result
                    // tuples and print their attribute values
                int ten;
                int eleven;
                int twelve;
                int thirteen;
				while (answer.next()) {
					ten = answer.getInt("2010");
					eleven = answer.getInt("2011");
					twelve = answer.getInt("2012");
					thirteen = answer.getInt("2013");
					if(ten > eleven && eleven > twelve && twelve > thirteen){
						System.out.println("" + answer.getString("school_name") + ", " + answer.getString("lea_name"));
					}
				}
            }
            System.out.println();

                // Shut down the connection to the DBMS.

            stmt.close();  
            dbconn.close();

        } catch (SQLException e) {

                System.err.println("*** SQLException:  "
                    + "Could not fetch query results.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);

        }		
	}		
    /*Method: queryFour(Connection)
     * The purpose of this method is to execute the first query
     * from the menu in the main method.  This will find all the
     * schools who are a junior high school.
     */
	private static void queryOne(Connection dbconn) {
        Statement stmt = null;
        ResultSet answer = null;
        String query = "select school_name from tkoch.aims2013";
        try {

            stmt = dbconn.createStatement();
            answer = stmt.executeQuery(query);

            if (answer != null) {

                System.out.println("\nThe results of the query are:\n");
                System.out.println();

                    // Use next() to advance cursor through the result
                    // tuples and print their attribute values
                String junior;
                int count = 0;
				while (answer.next()) {
					junior = answer.getString("school_name");
					junior = junior.toLowerCase();
					if (junior.contains("middle school") || junior.contains("junior high")
							|| junior.contains("jr high")) {
						count++;
					}
				}
                System.out.println("The count is: " + count);
            }
            System.out.println();

                // Shut down the connection to the DBMS.

            stmt.close();  
            dbconn.close();

        } catch (SQLException e) {

                System.err.println("*** SQLException:  "
                    + "Could not fetch query results.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);

        }		
	}
}