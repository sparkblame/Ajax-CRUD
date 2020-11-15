package dao;

import tools.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResourceDao {
	public List<String> getUrlByUserName(String userName) {
		ArrayList<String> list = new ArrayList<String>();
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {

			// 3.创建语句
			String sql = "SELECT * FROM t_resource WHERE resourceId IN ";
			sql += "(SELECT resourceId FROM t_role_resource WHERE roleId IN (";
			sql += "SELECT roleId FROM t_user_role WHERE userName = ? ))";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, userName);
			// 4.执行语句
			ResultSet rs = pst.executeQuery();
			// 5.结果处理
			while (rs.next()) {

				list.add(rs.getString("url")); // 将对象存放于集合中
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6.关闭连接
			dbc.close();
		}

		return list;

	}
}
