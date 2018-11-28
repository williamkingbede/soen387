package data;

import java.sql.SQLException;

public class SetUpDatabase {

	public static void main(String[] args) throws SQLException {
		
		DBTasks.createDatabase();
		DBTasks.createAllTables();
		//DBTasks.dropDatabase();
	}

}
