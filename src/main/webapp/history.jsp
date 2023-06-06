<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="zerobase.wifi.dto.PosHistoryDto"%>
<%@ page import="zerobase.wifi.model.PosHistoryModel"%>
<%@ page import="java.util.List"%>
 
<!DOCTYPE html>
<html>
<head>
<title>위치 정보 저장 예시</title>
<!-- 필요한 CSS 및 JavaScript 파일들을 로드 -->
<link href="${pageContext.request.contextPath}/res/css/main.css" rel="stylesheet" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/index.js"></script>
</head>
<body>
	<h1>위치 정보 저장 예시</h1>
	<%-- 위치 정보 목록을 가져오기 위해 PosHistoryDto 클래스 인스턴스화 --%>
	<%
		PosHistoryDto posHistoryDto = new PosHistoryDto();
	%>
	<%-- 위치 정보를 조회하여 locationList에 할당 --%>
	<%
		List<PosHistoryModel> locationList = posHistoryDto.SelectPosHistory();
	%>
	<table id="locationTable" class="table-list">
		<thead>
			<tr>
				<th>ID</th>
				<th>X 좌표</th>
				<th>Y 좌표</th>
				<th>조회 일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<%-- 위치 정보를 동적으로 추가할 행들 --%>
			<c:forEach var="posHistory" items="${locationList}">
				<tr>
					<td>${posHistory.historyID}</td>
					<td>${posHistory.latitude}</td>
					<td>${posHistory.longitude}</td>
					<td>${posHistory.timestamp}</td>
					<td>${posHistory.note}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script>
		function showLocationOnTable(id, latitude, longitude, timestamp, note) {
			// 위치 정보를 HTML 테이블에 표시
			var table = document.getElementById("locationTable");
			var tbody = table.querySelector("tbody");

			// 새로운 행 추가
			var row = document.createElement("tr");

			// ID 열
			var idCell = document.createElement("td");
			idCell.textContent = id;
			row.appendChild(idCell);

			// X 좌표 열
			var latitudeCell = document.createElement("td");
			latitudeCell.textContent = latitude;
			row.appendChild(latitudeCell);

			// Y 좌표 열
			var longitudeCell = document.createElement("td");
			longitudeCell.textContent = longitude;
			row.appendChild(longitudeCell);

			// 조회 일자 열
			var timestampCell = document.createElement("td");
			timestampCell.textContent = timestamp;
			row.appendChild(timestampCell);

			// 비고 열
			var noteCell = document.createElement("td");
			noteCell.textContent = note;
			row.appendChild(noteCell);

			tbody.appendChild(row);
		}
	</script>
</body>
</html>
