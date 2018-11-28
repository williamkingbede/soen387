package tests;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;

import data.DBTasks;
import data.UserRDG;
import domain.Challenge;
import domain.ChallengeDM;
import domain.ChallengeStatus;
import domain.user.User;
import domain.UserDM;
import domain.user.mapper.UserInputMapper;

public class DataSourceTest {
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		User u = null;
		try {
			u = UserInputMapper.find("jbhbhb", "kbhbdwlbclhw");
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
		}
		

	}

}
