/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.dao;

import ECUtils.BaseDAO;
import static ECUtils.BaseDAO.closeCon;
import static ECUtils.BaseDAO.getCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import todo.bean.AppUser;

/**
 *
 * @author computer
 */
public class UserDAO extends  BaseDAO{
    public static void insert(AppUser p1){
	Connection con=null;
	try {
		con =getCon();
		String sql = "insert into app_users " +
				" (u_name, phone_no, department, role, pass) " +
				" values (?, ?, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, p1.getUserName());
		st.setString(i++, p1.getPhoneNo());
		st.setString(i++, p1.getDepartment());
		st.setString(i++, p1.getRoll());
		st.setString(i++, p1.getPass());
		st.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
}
public static void update(AppUser p1){
	Connection con=null;
	try {
		con =getCon();
		String sql = "update app_users " +
		" set u_name =? , phone_no =? , department =?, role =?, pass=? " +
		" where id = ?  ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, p1.getUserName());
		st.setString(i++, p1.getPhoneNo());
		st.setString(i++, p1.getDepartment());
		st.setString(i++, p1.getRoll());
		st.setString(i++, p1.getPass());
		st.setString(i++, p1.getId());
		st.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
}
public static void delete(String id){
	Connection con=null;
	try {
		con =getCon();
		String sql = "delete from app_users " +
		" where id =? ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, id);
		st.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
}

public static LinkedList<AppUser> search(String sc, String si){
	LinkedList<AppUser> res = new LinkedList<AppUser>();
	Connection con=null;
	try {
		con =getCon();
		String sql = "select * from app_users where " + sc + " like ? ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, "%"+si+"%");
		ResultSet rs = st.executeQuery();
		while(rs.next()){
			AppUser p1 = new AppUser();
			p1.setDepartment(rs.getString("department"));
			p1.setPass(rs.getString("pass"));
			p1.setPhoneNo(rs.getString("phone_no"));
			p1.setRoll(rs.getString("role"));
			p1.setUserName(rs.getString("u_name"));
			p1.setId(rs.getString("id"));
			res.add(p1);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
	return res;
}


public static LinkedList<String> getUsers(){
	LinkedList<String> res = new LinkedList<String>();
	Connection con=null;
	try {
		con =getCon();
		String sql = "select distinct(u_name) from app_users ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		ResultSet rs = st.executeQuery();
		while(rs.next()){
			res.add(rs.getString("u_name"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
	return res;
}


public static AppUser findById(String id){
        AppUser res = null;
	Connection con=null;
	try {
		con =getCon();
		String sql = "select * from app_users where id = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, id);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			AppUser p1 = new AppUser();
			p1.setDepartment(rs.getString("department"));
			p1.setPass(rs.getString("pass"));
			p1.setPhoneNo(rs.getString("phone_no"));
			p1.setRoll(rs.getString("role"));
			p1.setUserName(rs.getString("u_name"));
			p1.setId(rs.getString("id"));
			res=p1;
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
	return res;
}

public static AppUser findByName(String name){
        AppUser res = null;
	Connection con=null;
	try {
		con =getCon();
		String sql = "select * from app_users where u_name = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, name);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			AppUser p1 = new AppUser();
			p1.setDepartment(rs.getString("department"));
			p1.setPass(rs.getString("pass"));
			p1.setPhoneNo(rs.getString("phone_no"));
			p1.setRoll(rs.getString("role"));
			p1.setUserName(rs.getString("u_name"));
			p1.setId(rs.getString("id"));
			res=p1;
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
	return res;
}

public static AppUser validate(String uname, String pass){
        AppUser res = null;
	Connection con=null;
	try {
		con =getCon();
		String sql = "select * from app_users where u_name  = ? and pass = ?  ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, uname);
		st.setString(i++, pass);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			AppUser p1 = new AppUser();
			p1.setDepartment(rs.getString("department"));
			p1.setPass(rs.getString("pass"));
			p1.setPhoneNo(rs.getString("phone_no"));
			p1.setRoll(rs.getString("role"));
			p1.setUserName(rs.getString("u_name"));
			p1.setId(rs.getString("id"));
			res=p1;
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
	return res;
}

}
