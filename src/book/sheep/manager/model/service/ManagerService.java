package book.sheep.manager.model.service;

import static book.sheep.common.JdbcTemplate.close;
import static book.sheep.common.JdbcTemplate.getConnection;

import java.sql.Connection;


import book.sheep.manager.model.dao.ManagerDao;
import book.sheep.manager.model.entity.Manager;

public class ManagerService 
{

	private ManagerDao managerDao = new ManagerDao();

	
	
	public Manager managerLogin(String id, String password) 
	{
		Connection conn = getConnection();
		Manager manager = managerDao.managerLogin(conn, id,password);
		close(conn);
		return manager;
	}
	
}
