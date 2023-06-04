package zerobase.wifi.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import zerobase.wifi.model.WifiInfoModel;
import zerobase.wifi.service.SqliteConnection;

public class WifiInfoDto {
    // 기존 코드 생략

	public void insert(WifiInfoModel wifiInfoModel) {
        // 여기에 데이터베이스에 insert하는 코드를 작성하세요.
        // 예를 들어, SQLiteConnection 클래스의 getConnect 메소드를 사용하여 데이터베이스에 연결하고 INSERT 문을 실행하는 코드를 작성하면 됩니다.
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        // ResultSet은 select 부분에서 주로 사용하여 기입하지 않았다.
        try {
            // 데이터베이스 연결
        	connection = SqliteConnection.getConnect();

            // INSERT 쿼리문 작성
            String query = "INSERT INTO WIPIInformation (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, " +
                    "X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, " +
                    "X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
}