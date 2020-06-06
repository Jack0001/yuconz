package sql.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersTableMethods {
	
	private String database;
	
	public UsersTableMethods(String database) 
	{
		this.database = database;
	}
    
    /**
     * Find user
     * @param capacity 
     */
    public String[] findUserByUsername(String username){
    	String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            // set the value
            pstmt.setString(1,username);
            //
            ResultSet rs  = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
//                System.out.println("User Found\t" + 
//                		rs.getString(1) +  "\t" + 
//                        rs.getString(2) + "\t" +
//                        rs.getString(3));
                String[] userDetails = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
                return userDetails;
            }            
        } catch (SQLException e) {
            System.out.println("Incorrect username, please contact HR");
        }
        String[] userDetails = {""};
        return userDetails;
    }
    
    /**
     * Insert a new row into the users table
     *
     * @param name
     * @param capacity
     */
    public void insert(String username, String password, String position) {
        String sql = "INSERT INTO users(username,password,position) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, username);
	            pstmt.setString(2, password);
	            pstmt.setString(3, position);
	            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Update data of a warehouse specified by the id
     *
     * @param id
     * @param name name of the warehouse
     * @param capacity capacity of the warehouse
     */
    public void update(String username, String position) {
        String sql = "UPDATE users SET position = ? "
                + "WHERE username = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, position);
            pstmt.setString(2, username);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
