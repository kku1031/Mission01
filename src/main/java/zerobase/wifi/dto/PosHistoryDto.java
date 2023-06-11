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
	// LocationHistory DB 연결 및 조회
	public List<PosHistoryModel> SelectPosHistory() {
		// ArrayList 배열에 담은 이유
		// ArrayList는 동적으로 크기를 조절할 수 있으므로, 조회된 히스토리 개수에 상관없이 유연하게 처리
		// ArrayList는 데이터를 순차적으로 저장하며, 인덱스를 통해 접근할 수 있기 때문에, 조회된 히스토리를 순회하면서 필요한 작업을
		// 수행하기에 적합.
		// insertPosHistory() 메서드에서 새로운 히스토리를 추가할 때, ArrayList를 사용하면 간단하게 리스트의 끝에 요소를
		// 추가할 수 있다.
		List<PosHistoryModel> historyList = new ArrayList<>();

		// 데이터베이스 연결, 실행, 결과 조회
		Connection connection = SqliteConnection.getConnect(); // DB연결
		PreparedStatement preparedStatement = null; // SQL 문장을 실행하기 위해 미리 컴파일된 SQL 문장을 나타내는 PreparedStatement 객체를 선언
		ResultSet resultSet = null; // SQL 쿼리의 결과를 저장

		// try catch finally : 데이터베이스 연결 문제, 잘못된 SQL 문장, 데이터베이스 제약 조건 위배 등의 상황
		// 처리(SQLException)
		try {
			String query = "SELECT * FROM LocationHistory"; 				// LocationHistory 테이블에서 모든 데이터 조회
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				PosHistoryModel history = new PosHistoryModel(); 			// PosHistoryModel 객체 생성
				history.setHISTORYID(resultSet.getInt("HISTORYID")); 		// 결과에서 HISTORYID 가져와서 설정
				history.setLAT(resultSet.getDouble("LAT")); 				// 결과에서 LAT 가져와서 설정
				history.setLNT(resultSet.getDouble("LNT")); 				// 결과에서 LNT 가져와서 설정
				history.setTIMESTAMP(resultSet.getString("TIMESTAMP")); 	// 결과에서 TIMESTAMP 가져와서 설정

				historyList.add(history); // 리스트에 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// ResultSet, PreparedStatement, Connection 닫기
			SqliteConnection.close(resultSet, preparedStatement, connection);
		}

		return historyList; // 조회된 히스토리 리스트 반환
	}

	// LocationHistory 테이블에 추가
	public void insertPosHistory(double LAT, double LNT, String TIMESTAMP) {
		// 데이터베이스 연결 가져오기
		Connection connection = SqliteConnection.getConnect();
		PreparedStatement preparedStatement = null;

		// 초기 코드 : PostHistoryModel 객체를 새로 만든 후, 해당객체에 LAT, LNT, TIMESTAMP 값 설정 후,
		// PreparedStatement에 값 설정
		// 수정 : PostHistoryModel 객체 생성 X, 메서드의 파라미터 직접 사용
		try {
			String query = "INSERT INTO LocationHistory (LAT, LNT, TIMESTAMP) VALUES (?, ?, ?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1, LAT);
			preparedStatement.setDouble(2, LNT);
			preparedStatement.setString(3, TIMESTAMP);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// PreparedStatement, Connection 닫기
			SqliteConnection.close(null, preparedStatement, connection);
		}
	}

	// LocationHistory 테이블에서 특정 히스토리 삭제(ID값만 받아오면 넘버링에 따라 삭제가 정상적으로 진행되니까, LAT, LNT, TIMESTAMP를 받을 필요없음)
	public void deletePosHistory(int historyId) {
		// 데이터베이스 연결 가져오기
		Connection connection = SqliteConnection.getConnect();
		PreparedStatement deleteStatement = null;
		PreparedStatement updateStatement = null;

		try {
			// 히스토리 삭제
			String deleteQuery = "DELETE FROM LocationHistory WHERE HISTORYID = ?";
			deleteStatement = connection.prepareStatement(deleteQuery);
			deleteStatement.setInt(1, historyId);
			deleteStatement.executeUpdate();

			// (화면에서 HistoryID 넘버링 부분이 맞지않아 추가함)
			// 삭제된 히스토리 이후의 히스토리들의 번호 업데이트(HISTORYID가 삭제된 히스토리보다 큰 값들에 대해 - 1을 해주어 번호를 업데이트)
			String updateQuery = "UPDATE LocationHistory SET HISTORYID = HISTORYID - 1 WHERE HISTORYID > ?";
			updateStatement = connection.prepareStatement(updateQuery);
			updateStatement.setInt(1, historyId);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// PreparedStatement, Connection 닫기
			SqliteConnection.close(null, updateStatement, connection);
			SqliteConnection.close(null, deleteStatement, connection);
		}
	}
}
