package zerobase.wifi.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zerobase.wifi.model.PosHistoryModel;
import zerobase.wifi.service.SqliteConnection;

public class PosHistoryDto {
	//LocationHistory DB연결 및 조회
	public List<PosHistoryModel> SelectPosHistory() {
        List<PosHistoryModel> historyList = new ArrayList<>();

        // 데이터베이스 연결 가져오기
        Connection connection = SqliteConnection.getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM LocationHistory";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PosHistoryModel history = new PosHistoryModel();
                history.setHISTORYID(resultSet.getInt("HISTORYID"));
                history.setLAT(resultSet.getDouble("LAT"));
                history.setLNT(resultSet.getDouble("LNT"));
                history.setTIMESTAMP(resultSet.getString("TIMESTAMP"));

                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // ResultSet, PreparedStatement, Connection 닫기
            SqliteConnection.close(resultSet, preparedStatement, connection);
        }

        return historyList;
    }

    // LocationHistory 테이블에 추가
    public void insertPosHistory(double LAT, double LNT, String TIMESTAMP) {
        // 데이터베이스 연결 가져오기
        Connection connection = SqliteConnection.getConnect();
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO LocationHistory (LAT, LNT, TIMESTAMP) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            PosHistoryModel posHistoryModel = new PosHistoryModel();
            posHistoryModel.setLAT(LAT);
            posHistoryModel.setLNT(LNT);
            posHistoryModel.setTIMESTAMP(TIMESTAMP);

            preparedStatement.setDouble(1, posHistoryModel.getLAT());
            preparedStatement.setDouble(2, posHistoryModel.getLNT());
            preparedStatement.setString(3, posHistoryModel.getTIMESTAMP());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // PreparedStatement, Connection 닫기
            SqliteConnection.close(null, preparedStatement, connection);
        }
    } 
    
 // LocationHistory 테이블에서 특정 히스토리 삭제
    public void deletePosHistory(int historyId) {
        // 데이터베이스 연결 가져오기
        Connection connection = SqliteConnection.getConnect();
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM LocationHistory WHERE HISTORYID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, historyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // PreparedStatement, Connection 닫기
            SqliteConnection.close(null, preparedStatement, connection);
        }
    }
    
    public static void main(String[] args) {
        PosHistoryDto posHistoryDto = new PosHistoryDto();

        double lat = 37.123456;  // 임의의 위도 값
        double lnt = 127.987654;  // 임의의 경도 값
        String timestamp = "2023-06-09 12:34:56";  // 임의의 타임스탬프 값

        // 위치 히스토리 추가
        posHistoryDto.insertPosHistory(lat, lnt, timestamp);

        System.out.println("위치 히스토리가 추가되었습니다.");
    }
}
