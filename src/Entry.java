import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Entry {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		// TODO LOAD JDBC PROPERTIES FROM FILE SYSTEM

		Properties props = new Properties();

		FileInputStream fis = new FileInputStream("dbDetails.properties");

		props.load(fis);

		// TODO LOAD AND REGISTER JDBC DRIVER
		// String driver = props.getProperty("jdbc.driver");
		// Class.forName(driver);

		// TODO : GET DATABASE CONNECTION USING JDBC URL
		String url = props.getProperty("jdbc.url");
		Connection dbConnection;
		dbConnection = DriverManager.getConnection(url);
		int rows = 0;
		System.out.println("Connection succesfull ? " + (dbConnection != null));

		/*Statement insertStatement = null;
		String insertQuery = props.getProperty("jdbc.query.insert");
		
		
		try {
			insertStatement = dbConnection.createStatement();
			rows = insertStatement.executeUpdate(insertQuery);
			System.out.println(rows + " record is(are) added successfully!");

		} finally {
			if (insertStatement != null)
				insertStatement.close();
		}*/
		
		
		try(Statement selectStatement = dbConnection.createStatement()){
			String selectQuery = props.getProperty("jdbc.query.read");
			
			ResultSet result;
			/*result = selectStatement.executeQuery(selectQuery);
			
			while(result.next()){
				System.out.println(result.getString(1));
			}*/
			
		}
		
		//PREPARED STATEMENT
		String insertQuery = props.getProperty("jdbc.query.insert");
		try(PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)){
			
			String msg = "Java 1.8",msg2 = "Java 1.7";
			
			insertStatement.setString(1, msg);
			insertStatement.setString(2, msg2);
			//rows = insertStatement.executeUpdate();
			
			System.out.println(rows + " records added");
			}
		
		
		//TODO on PERSON TABLE 
		
//		String createQuery = props.getProperty("jdbc.query.create");
//		Statement createStatement1 = dbConnection.createStatement();
//		
//		createStatement1.execute(createQuery);
		
		
		//INSERT INTO PERSON PREPARED STATEMENT
		String insertQueryP = props.getProperty("jdbc.query.insertPerson");
		try(PreparedStatement insertStatement = dbConnection.prepareStatement(insertQueryP)){
			
			String name = "Anand Singh",name2="Rohit",name3 = "Tushar";
			int age = 20,age2 =23,age3 = 24;
			
			insertStatement.setString(1, name);
			insertStatement.setInt(2, age);
			insertStatement.setString(3, name2);
			insertStatement.setInt(4, age2);
			insertStatement.setString(5, name3);
			insertStatement.setInt(6, age3);
			//rows = insertStatement.executeUpdate();
			
			System.out.println(rows + " records added");
			}
		
		//READ PERSON AND UPDATE IT USING RESULT SET
		try(Statement selectStatement = dbConnection.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE)){
			String selectQuery = props.getProperty("jdbc.query.readPerson");
			
			ResultSet result;
			result = selectStatement.executeQuery(selectQuery);
			while(result.next()){
				System.out.println("Name: "+result.getString(1)+"	Age: "+result.getInt(2));
			}
			
			result = selectStatement.executeQuery(selectQuery);
			while(result.next()){
				result.updateInt(2, (result.getInt(2)+3));
				result.updateRow();
				System.out.println("Modofied -- Name: "+result.getString(1)+"	Age: "+result.getInt(2));
			}
			
		}
		
		
	}
}
