
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
import java.sql.*; // For access to the SQL interaction methods
import java.util.Scanner;

public class Prog4 {
	public static void main(String[] args) {

		final String oracleURL = // Magic lectura -> aloe access spell
		"jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

		String username = null, // Oracle DBMS username
				password = null; // Oracle DBMS password

		if (args.length == 2) { // get username/password from cmd line args
			username = args[0];
			password = args[1];
		} else {
			System.out.println("\nUsage:  java JDBC <username> <password>\n"
					+ "    where <username> is your Oracle DBMS" + " username,\n    and <password> is your Oracle"
					+ " password (not your system password).\n");
			System.exit(-1);
		}

		// load the (Oracle) JDBC driver by initializing its base
		// class, 'oracle.jdbc.OracleDriver'.

		try {

			Class.forName("oracle.jdbc.OracleDriver");

		} catch (ClassNotFoundException e) {

			System.err.println("*** ClassNotFoundException:  " + "Error loading Oracle JDBC driver.  \n"
					+ "\tPerhaps the driver is not on the Classpath?");
			System.exit(-1);

		}

		// make and return a database connection to the user's
		// Oracle database

		Connection dbconn = null;

		try {
			dbconn = DriverManager.getConnection(oracleURL, username, password);

		} catch (SQLException e) {

			System.err.println("*** SQLException:  " + "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);

		}

		// Send the query to the DBMS, and get and display the results
		int response = 0;
		int exceeds = 0; // will be used for query4
		Scanner s = new Scanner(System.in);
		while (response != -1) { // if the user enters -1, quit the program
			System.out.println("Choose from the menu.(-1 to quit)");
			System.out.println("1.Test querying the mattseall.office table");

			response = s.nextInt();
			if (response == -1) {
				break;
			}
			if (response == 1) { // choice 1
				queryOne(dbconn);
			}

		}
		s.close();
	}

    /*Method: queryOne(Connection)
     * The purpose of this method is to execute the first query
     * from the menu in the main method.  This will find all the
     * schools who are a junior high school.
     */
	private static void queryOne(Connection dbconn) {
        Statement stmt = null;
        ResultSet answer = null;
        String query = "select * from mattseall.office";
        try {

            stmt = dbconn.createStatement();
            answer = stmt.executeQuery(query);

            if (answer != null) {

                System.out.println("\nThe results of the query are:\n");
                System.out.println();

                    // Use next() to advance cursor through the result
                    // tuples and print their attribute values
                String office;
                int officeNum;
                int count = 0;
				while (answer.next()) {
					officeNum = answer.getInt("office_id");
					System.out.print("" + officeNum + "\t");
					office = answer.getString("street");
					office = office.toLowerCase();
					System.out.print("" + office + "\t");
					office = answer.getString("postal");
					office = office.toLowerCase();
					System.out.print("" + office + "\t");
					office = answer.getString("city");
					office = office.toLowerCase();
					System.out.print("" + office + "\t");
					office = answer.getString("branch");
					office = office.toLowerCase();
					System.out.print("" + office + "\t");
					officeNum = answer.getInt("manager_id");
					System.out.print("" + officeNum + "\t");
					System.out.println();
					count++;
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