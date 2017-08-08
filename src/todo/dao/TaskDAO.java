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
import todo.bean.MyTask;

/**
 *
 * @author computer
 */
public class TaskDAO extends BaseDAO{
    public static void insert(MyTask p1){
	Connection con=null;
	try {
		con =getCon();
		String sql = "insert into task " +
				" (cr_by ,cr_for, msg , priority, status, cr_date , due_dt) " +
				" values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, p1.getCreatedBy());
		st.setString(i++, p1.getCreatedFor());
		st.setString(i++, p1.getMsg());
		st.setInt(i++, p1.getPriority());
		st.setString(i++, p1.getStatus());
		st.setDate(i++, p1.getCreatedDate());
		st.setDate(i++, p1.getDueDate());
		st.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		closeCon(con);
	}
}
public static void update(MyTask p1){
	Connection con=null;
	try {
		con =getCon();
		String sql = "update task " +
		" set  cr_by=? ,cr_for=?, msg=? , priority=?, status=?, cr_date=? , due_dt=? " +
		" where id = ?  ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, p1.getCreatedBy());
		st.setString(i++, p1.getCreatedFor());
		st.setString(i++, p1.getMsg());
		st.setInt(i++, p1.getPriority());
		st.setString(i++, p1.getStatus());
		st.setDate(i++, p1.getCreatedDate());
		st.setDate(i++, p1.getDueDate());
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
		String sql = "delete from task " +
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

public static LinkedList<MyTask> search(String sc, String si, String uName){
	LinkedList<MyTask> res = new LinkedList<MyTask>();
	Connection con=null;
	try {
		con =getCon();
		String sql = "select * from task where " + sc + " like ? ";
                if(uName!=null && !"".equals(uName)){
                    sql = sql + " and cr_for = '"+uName +"'";
                }
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, "%"+si+"%");
		ResultSet rs = st.executeQuery();
		while(rs.next()){
			MyTask p1 = new MyTask();
			p1.setCreatedBy(rs.getString("cr_by"));
			p1.setCreatedDate(rs.getDate("cr_date"));
			p1.setCreatedFor(rs.getString("cr_for"));
			p1.setDueDate(rs.getDate("due_dt"));
			p1.setMsg(rs.getString("msg"));
			p1.setPriority(rs.getInt("priority"));
			p1.setStatus(rs.getString("status"));
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

public static MyTask findById(String id){
        MyTask res = null;
	Connection con=null;
	try {
		con =getCon();
		String sql = "select * from task where id = ? ";
		PreparedStatement st = con.prepareStatement(sql);
		int i = 1;
		st.setString(i++, id);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			MyTask p1 = new MyTask();
			p1.setCreatedBy(rs.getString("cr_by"));
			p1.setCreatedDate(rs.getDate("cr_date"));
			p1.setCreatedFor(rs.getString("cr_for"));
			p1.setDueDate(rs.getDate("due_dt"));
			p1.setMsg(rs.getString("msg"));
			p1.setPriority(rs.getInt("priority"));
			p1.setStatus(rs.getString("status"));
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
