package ECUtils;
public class ECConst {
	public static String DB_NAME ="fx_todo";
	public static String DB_HOST="localhost";
	public static String DB_USER="root";
	public static String DB_PASS ="";
	public static String SQLS[] = 
	{
		"create table app_users (id INT NOT NULL AUTO_INCREMENT, u_name varchar(45), pass varchar(45),phone_no varchar(15), department varchar(45), role varchar(45), PRIMARY KEY (id))",	
		"create table task (id INT NOT NULL AUTO_INCREMENT, cr_by varchar(45),cr_for varchar(45), msg text, priority int, status varchar(45), cr_date datetime, due_dt datetime, PRIMARY KEY (id))",	
		"insert into app_users (u_name,phone_no, department,role, pass) values ('admin', 'admin', 'admin', 'admin', 'admin')",	
	};
}
