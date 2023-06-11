package zerobase.wifi.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zerobase.wifi.model.WifiInfoModel;
import zerobase.wifi.service.SqliteConnection;

public class WifiInfoDto {
	//전체DB 조회하는 부분
	public static ArrayList<WifiInfoModel> SelectAll() {
		    ArrayList<WifiInfoModel> dataList = new ArrayList<>();
		    Connection connection = null;
		    PreparedStatement preparedStatement = null;
		    ResultSet resultSet = null;

		    try {
		        // 데이터베이스 연결
		        connection = SqliteConnection.getConnect();

		        // SELECT 쿼리문 작성
		        String query = "SELECT * FROM WIPIInformation";

		        // PreparedStatement 객체 생성
		        preparedStatement = connection.prepareStatement(query);

		        // SELECT 문 실행
		        resultSet = preparedStatement.executeQuery();

		        // 결과 처리
		        while (resultSet.next()) {
		            WifiInfoModel wifiInfoModel = new WifiInfoModel();
		            wifiInfoModel.setX_SWIFI_MGR_NO(resultSet.getString("X_SWIFI_MGR_NO"));
		            wifiInfoModel.setX_SWIFI_WRDOFC(resultSet.getString("X_SWIFI_WRDOFC"));
		            wifiInfoModel.setX_SWIFI_MAIN_NM(resultSet.getString("X_SWIFI_MAIN_NM"));
		            wifiInfoModel.setX_SWIFI_ADRES1(resultSet.getString("X_SWIFI_ADRES1"));
		            wifiInfoModel.setX_SWIFI_ADRES2(resultSet.getString("X_SWIFI_ADRES2"));
		            wifiInfoModel.setX_SWIFI_INSTL_FLOOR(resultSet.getString("X_SWIFI_INSTL_FLOOR"));
		            wifiInfoModel.setX_SWIFI_INSTL_TY(resultSet.getString("X_SWIFI_INSTL_TY"));
		            wifiInfoModel.setX_SWIFI_INSTL_MBY(resultSet.getString("X_SWIFI_INSTL_MBY"));
		            wifiInfoModel.setX_SWIFI_SVC_SE(resultSet.getString("X_SWIFI_SVC_SE"));
		            wifiInfoModel.setX_SWIFI_CMCWR(resultSet.getString("X_SWIFI_CMCWR"));
		            wifiInfoModel.setX_SWIFI_CNSTC_YEAR(resultSet.getInt("X_SWIFI_CNSTC_YEAR"));
		            wifiInfoModel.setX_SWIFI_INOUT_DOOR(resultSet.getString("X_SWIFI_INOUT_DOOR"));
		            wifiInfoModel.setX_SWIFI_REMARS3(resultSet.getString("X_SWIFI_REMARS3"));
		            wifiInfoModel.setLAT(resultSet.getDouble("LAT"));
		            wifiInfoModel.setLNT(resultSet.getDouble("LNT"));
		            wifiInfoModel.setWORK_DTTM(resultSet.getString("WORK_DTTM"));

		            dataList.add(wifiInfoModel);
		        }
		    } catch (SQLException e) {
		        System.out.println("데이터베이스 연결 또는 쿼리 실행 중 오류가 발생했습니다. " + e.getMessage());
		    } finally {
		        // ResultSet, PreparedStatement 및 Connection 닫기
		        SqliteConnection.close(resultSet, preparedStatement, connection);
		    }

		    return dataList;
		}
	
	//DB삽입
	public void insert(WifiInfoModel wifiInfoModel) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// ResultSet은 select 부분에서 주로 사용하여 기입하지 않았다.
		try {
			// 데이터베이스 연결
			connection = SqliteConnection.getConnect();

			// INSERT 쿼리문 작성
			String query = "INSERT INTO WIPIInformation (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, "
					+ "X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, "
					+ "X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// PreparedStatement 객체 생성
			preparedStatement = connection.prepareStatement(query);

			// 파라미터 설정
			preparedStatement.setString(1, wifiInfoModel.getX_SWIFI_MGR_NO());
			preparedStatement.setString(2, wifiInfoModel.getX_SWIFI_WRDOFC());
			preparedStatement.setString(3, wifiInfoModel.getX_SWIFI_MAIN_NM());
			preparedStatement.setString(4, wifiInfoModel.getX_SWIFI_ADRES1());
			preparedStatement.setString(5, wifiInfoModel.getX_SWIFI_ADRES2());
			preparedStatement.setString(6, wifiInfoModel.getX_SWIFI_INSTL_FLOOR());
			preparedStatement.setString(7, wifiInfoModel.getX_SWIFI_INSTL_TY());
			preparedStatement.setString(8, wifiInfoModel.getX_SWIFI_INSTL_MBY());
			preparedStatement.setString(9, wifiInfoModel.getX_SWIFI_SVC_SE());
			preparedStatement.setString(10, wifiInfoModel.getX_SWIFI_CMCWR());
			preparedStatement.setInt(11, wifiInfoModel.getX_SWIFI_CNSTC_YEAR());
			preparedStatement.setString(12, wifiInfoModel.getX_SWIFI_INOUT_DOOR());
			preparedStatement.setString(13, wifiInfoModel.getX_SWIFI_REMARS3());
			preparedStatement.setDouble(14, wifiInfoModel.getLAT());
			preparedStatement.setDouble(15, wifiInfoModel.getLNT());
			preparedStatement.setString(16, wifiInfoModel.getWORK_DTTM().toString());

			// INSERT 문 실행
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("데이터베이스에 데이터가 성공적으로 삽입되었습니다.");
			} else {
				System.out.println("데이터 삽입에 실패했습니다.");
			}

		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 또는 쿼리 실행 중 오류가 발생했습니다. " + e.getMessage());
		} finally {
			// PreparedStatement 및 Connection 닫기
			SqliteConnection.close(null, preparedStatement, connection);
		}
	}

	// DB주어진 위도와 경도에 가장 가까운 와이파이 정보 20개를 조회
	public List<WifiInfoModel> selectByLocation(double LAT, double LNT) {
		List<WifiInfoModel> nearWifiInfo = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// 데이터베이스 연결
			connection = SqliteConnection.getConnect();

			// SELECT 쿼리문 작성
			String query = "SELECT *, " + "(LAT - ?) * (LAT - ?) + (LNT - ?) * (LNT - ?) AS distance "
					+ "FROM WIPIInformation " + "ORDER BY distance ASC " + "LIMIT 20";

			// PreparedStatement 객체 생성
			preparedStatement = connection.prepareStatement(query);

			// 파라미터 설정
			preparedStatement.setDouble(1, LAT);
			preparedStatement.setDouble(2, LAT);
			preparedStatement.setDouble(3, LNT);
			preparedStatement.setDouble(4, LNT);

			// 쿼리 실행
			resultSet = preparedStatement.executeQuery();

			// 결과 처리
			while (resultSet.next()) {
				WifiInfoModel wifiInfoModel = new WifiInfoModel();
				wifiInfoModel.setX_SWIFI_MGR_NO(resultSet.getString("X_SWIFI_MGR_NO"));
				wifiInfoModel.setX_SWIFI_WRDOFC(resultSet.getString("X_SWIFI_WRDOFC"));
				wifiInfoModel.setX_SWIFI_MAIN_NM(resultSet.getString("X_SWIFI_MAIN_NM"));
				wifiInfoModel.setX_SWIFI_ADRES1(resultSet.getString("X_SWIFI_ADRES1"));
				wifiInfoModel.setX_SWIFI_ADRES2(resultSet.getString("X_SWIFI_ADRES2"));
				wifiInfoModel.setX_SWIFI_INSTL_FLOOR(resultSet.getString("X_SWIFI_INSTL_FLOOR"));
				wifiInfoModel.setX_SWIFI_INSTL_TY(resultSet.getString("X_SWIFI_INSTL_TY"));
				wifiInfoModel.setX_SWIFI_INSTL_MBY(resultSet.getString("X_SWIFI_INSTL_MBY"));
				wifiInfoModel.setX_SWIFI_SVC_SE(resultSet.getString("X_SWIFI_SVC_SE"));
				wifiInfoModel.setX_SWIFI_CMCWR(resultSet.getString("X_SWIFI_CMCWR"));
				wifiInfoModel.setX_SWIFI_CNSTC_YEAR(resultSet.getInt("X_SWIFI_CNSTC_YEAR"));
				wifiInfoModel.setX_SWIFI_INOUT_DOOR(resultSet.getString("X_SWIFI_INOUT_DOOR"));
				wifiInfoModel.setX_SWIFI_REMARS3(resultSet.getString("X_SWIFI_REMARS3"));
				wifiInfoModel.setLAT(resultSet.getDouble("LAT"));
				wifiInfoModel.setLNT(resultSet.getDouble("LNT"));
				wifiInfoModel.setWORK_DTTM(resultSet.getString("WORK_DTTM"));

				// 리스트에 추가
				nearWifiInfo.add(wifiInfoModel);
			}
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 또는 쿼리 실행 중 오류가 발생했습니다. " + e.getMessage());
		} finally {
			// ResultSet, PreparedStatement 및 Connection 닫기
			SqliteConnection.close(resultSet, preparedStatement, connection);
		}

		return nearWifiInfo;
	}
}