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
                history.setHistoryID(resultSet.getInt("HistoryID"));
                history.setLatitude(resultSet.getDouble("Latitude"));
                history.setLongitude(resultSet.getDouble("Longitude"));
                history.setTimestamp(resultSet.getString("Timestamp"));

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

	//LocationHistory DB연결 및 추가
	public void InsertPosHistory(double latitude, double longitude, String timestamp) {
	    // 데이터베이스 연결 가져오기
	    Connection connection = SqliteConnection.getConnect();
	    PreparedStatement preparedStatement = null;

	    try {
	        String query = "INSERT INTO LocationHistory (HistoryID, Latitude, Longitude, Timestamp) VALUES (?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(query);
	        
	        PosHistoryModel history = new PosHistoryModel();
	        history.setLatitude(latitude);
	        history.setLongitude(longitude);
	        history.setTimestamp(timestamp);

	        preparedStatement.setInt(1, history.getHistoryID()); // 히스토리 식별자 지정
	        preparedStatement.setDouble(2, history.getLatitude());
	        preparedStatement.setDouble(3, history.getLongitude());
	        preparedStatement.setString(4, history.getTimestamp());
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // PreparedStatement, Connection 닫기
	        SqliteConnection.close(null, preparedStatement, connection);
	    }
	}
}


