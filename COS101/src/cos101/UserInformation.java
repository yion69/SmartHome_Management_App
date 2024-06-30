package cos101;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Asus
 */
public class UserInformation {
	String UserID = null;
	String UserPassword = null;
	
	UserInformation(String id, String pass){
		this.UserID = id;
		this.UserPassword = pass;
	}
	
	public String getUserID(){
		return UserID;
	}
	
	public String getUserPassword(){
		return UserPassword;
	}
}
