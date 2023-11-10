package book.sheep.manager.controller;

import book.sheep.manager.model.entity.Manager;
import book.sheep.manager.model.service.ManagerService;

public class ManagerController 
{
	private ManagerService managerService = new ManagerService();
	
	public Manager managerLogin(String id,String password) {
		Manager manager = null;
		try 
		{
			manager = managerService.managerLogin(id,password);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return manager;
	}

}
