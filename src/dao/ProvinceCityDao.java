package dao;

import tools.DatabaseConnection;
import vo.City;
import vo.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProvinceCityDao {
	public ArrayList<Province> queryProvince(){
		ArrayList<Province> list=new ArrayList<Province>();
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {

			// 3.�������
			String sql = "select * from t_province ";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.ִ�����
			ResultSet rs = pst.executeQuery();
			// 5.�������
			while (rs.next()) {
				Province province = new Province();
				province.setProvinceCode(rs.getString("provinceCode"));
				province.setProvinceName(rs.getString("provinceName"));

				list.add(province); // ���������ڼ�����
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6.�ر�����
			dbc.close();
		}
		
		return list;
	}
	
	public ArrayList<City> queryCity(String provinceCode){
		ArrayList<City> list=new ArrayList<City>();
		DatabaseConnection dbc = new DatabaseConnection();
		Connection con = dbc.getConnection();
		try {

			// 3.�������
			String sql = "select * from t_city where provinceCode=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, provinceCode);
			// 4.ִ�����
			ResultSet rs = pst.executeQuery();
			// 5.�������
			while (rs.next()) {
				City city = new City();
				city.setCityCode(rs.getString("cityCode"));
				city.setProvinceCode(rs.getString("provinceCode"));
				city.setCityName(rs.getString("cityName"));

				list.add(city); // ���������ڼ�����
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
