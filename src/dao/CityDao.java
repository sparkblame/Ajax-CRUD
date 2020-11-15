package dao;

import vo.City;
import vo.Province;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CityDao {
	public ArrayList<Province> queryProvince() {
		ArrayList<Province> list = new ArrayList<Province>();
		Connection con = null;
		try {
			Class.forName("");
			con = DriverManager.getConnection("", "", "");
			String sql = "select * from t_province";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Province province = new Province();
				province.setProvinceCode(rs.getString("provinceCode"));
				province.setProvinceName(rs.getString("provinceName"));
				list.add(province);
			}
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	public ArrayList<City> queryCity(String proviceCode) {
		ArrayList<City> list = new ArrayList<City>();

		return list;
	}

}
