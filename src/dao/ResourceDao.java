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

			// 3.�������
			String sql = "SELECT * FROM t_resource WHERE resourceId IN ";
			sql += "(SELECT resourceId FROM t_role_resource WHERE roleId IN (";
			sql += "SELECT roleId FROM t_user_role WHERE userName = ? ))";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, userName);
			// 4.ִ�����
			ResultSet rs = pst.executeQuery();
			// 5.�������
			while (rs.next()) {

				list.add(rs.getString("url")); // ���������ڼ�����
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6.�ر�����
			dbc.close();
		}

		return list;

	}
}
