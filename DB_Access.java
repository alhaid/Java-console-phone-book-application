package assign;

import java.sql.*;
import java.util.ArrayList;


public class DB_Access {
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/test";
	private String uname = "root";
	private String upass = "";
	
	Connection con;
	Statement st;
	private ResultSet rs;
	
	
	//create the connection and the table.
	public DB_Access() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		con = DriverManager.getConnection(url, uname, upass);
		st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		String sql = "create table if not exists phoneBook(number varchar(15), name varchar(60), address varchar(60))";
		st.executeUpdate(sql);
	}
	
	
	//store the data.
	public boolean storeData (Data data){
		boolean success = true;
		String sql = "insert into phoneBook values (?, ?, ?)";	//prepare the sql.
		PreparedStatement pst;
		try {	
			pst = con.prepareStatement(sql);	//executing the sql.
			pst.setString(1, data.getNumber());
			pst.setString(2, data.getName());	//filling the table.
			pst.setString(3, data.getAddress());
			pst.executeUpdate();	//updating the table.
			rs = st.executeQuery("select * from phoneBook");
		} catch (SQLException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	
	
	//search for data
	public ArrayList<Data> searchData(String name){
		ArrayList<Data> list = new ArrayList<Data>();	//create a list to store all the retrieved data.
		String sql = "select * from phoneBook where name ='" + name + "'";
			ResultSet rs;
			try {
				rs = st.executeQuery(sql);	//execute the sql.
				while(rs.next()){
					Data data = new Data(rs.getString(1), rs.getString(2), rs.getString(3));
					list.add(data);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
	}
	
	
	
	//update data.
	public boolean updateData(Data data){
		boolean success = true;
		String sql = "update phoneBook set number = '" + data.getNumber() + 
				"', address = '"+ data.getAddress() +
				"'where name = '" +data.getName() + "'";
		try {
			int rs  = st.executeUpdate(sql);
			if (rs == 0){
				success = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	
	
	//search for a name
	public Data searchName(String name){
		Data data = null;
		String sql = "select * from phoneBook where name = '" + name + "'";
		try {
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				data = new Data(rs.getString(1),
				rs.getString(2),
				rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	
}
