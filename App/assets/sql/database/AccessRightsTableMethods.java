package sql.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessRightsTableMethods {
	
	private String database;
	
	public AccessRightsTableMethods(String database) 
	{
		this.database = database;
	}
	
    public String hasRights(String position, String wantedRights){
    	String sql = "SELECT position, rights FROM access_rights WHERE position = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1, position);
            //
            ResultSet rs  = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()) {	
                        if(rs.getString(2).contains(wantedRights))
                        {
                        	System.out.println(rs.getString(2));
                        	return wantedRights;
                        }
            }
            return null;
        } catch (SQLException e) {
        	System.out.println("Incorrect username, please contact HR");
        }
        return null;
    }
    
    /**
     * log any attempt at authentication and store it in the databases authentication log
     * @param username - The username of the user who requested access
     * @param result - "Y" or "N" - The result of the authentication attempt with Y representing a success and N representing failure
     * @return True or false based on the result of the logging
     */
    public boolean logAttempt(String username, int result)
    {
   	
        //Initialise the sql statement to input the authentication attempt in to the database
    	String sql;
    	
    	sql = "INSERT INTO authenticationLog (username, date_time, result) VALUES (?,DATETIME(),?)";
		
    	try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setInt(2, result);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

    	return false;
    }
    
    /**
     * log any attempt at authorisation and store it in the datbases authorisation log
     * @param username - The username of the user who requested access
     * @param rights - The rights that were attempted to be used
     * @param result - The result of the attempted authorisation 
     * @return True or false based on the result of the logging
     */
    public boolean logAuthorisation(String username, String rights, String attemptedAction, int result) {
        //Initialise the sql statement to input the authentication attempt in to the database
    	String sql;
    	
    	sql = "INSERT INTO authorisationLog (username, auth_level, attempted_action, result, date_time) VALUES (?, ?, ?, ?, DATETIME())";
		
    	try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, rights);
			pstmt.setString(3, attemptedAction);
			pstmt.setInt(4, result);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

    	return false;
    }
    
    
    /**
     * Private Method
     * Connect to the database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
    	String url = "jdbc:sqlite:DataBase/" + database;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
