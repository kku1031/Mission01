<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ page import="java.util.List"%>
<%@ page import="zerobase.wifi.model.PosHistoryModel"%>
<%@ page import="zerobase.wifi.dto.PosHistoryDto"%>
<%@ page import="zerobase.wifi.dto.WifiInfoDto"%>
<%@ page import="zerobase.wifi.model.WifiInfoModel"%>

<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link href="${contextPath}/res/css/main.css" rel="stylesheet" />
<script src="${contextPath}/res/js/index.js"></script>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<div class="api-action">
		<a href="/">홈</a> | <a href="/history.jsp">위치 히스토리 목록</a> | <a
			href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
	</div>
	<table class="table-list">
		<tr>
			<th>ID</th>
			<th>X 좌표</th>
			<th>Y 좌표</th>
			<th>조회 일자</th>
			<th>비고</th>
		</tr>
		<%
		String latInput = request.getParameter("LAT");
		String lntInput = request.getParameter("LNT");

		double LAT = 0.0; // 초기화
		double LNT = 0.0; // 초기화

		if (latInput != null && lntInput != null) {
			LAT = Double.parseDouble(latInput);
			LNT = Double.parseDouble(lntInput);
		}

		// 현재 시간을 TIMESTAMP로 설정
		java.util.Date date = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String TIMESTAMP = sdf.format(date);

		// 위치 히스토리 조회
		PosHistoryDto posHistoryDto = new PosHistoryDto();
		List<PosHistoryModel> historyList = posHistoryDto.SelectPosHistory();

		if (!historyList.isEmpty()) {
		%>

		<%
		for (PosHistoryModel posHistoryModel : historyList) {
			int historyId = posHistoryModel.getHISTORYID();
			double historyLat = posHistoryModel.getLAT();
			double historyLnt = posHistoryModel.getLNT();
			String timestamp = posHistoryModel.getTIMESTAMP();
		%>
		<tr>
			<td><%=historyId%></td>
			<td><%=historyLat%></td>
			<td><%=historyLnt%></td>
			<td><%=timestamp%></td>
			<td style="text-align: center;">
				<form action="history.jsp" method="POST">
					<input type="hidden" name="deleteHistoryId" value="<%=historyId%>" />
					<button type="submit">삭제</button>
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	}
	%>
</body>
</html>
<%
// 삭제 요청 처리
String deleteHistoryId = request.getParameter("deleteHistoryId");
if (deleteHistoryId != null) {
	int historyId = Integer.parseInt(deleteHistoryId);

	// 위치 히스토리 삭제
	PosHistoryDto deleteHistoryDto = new PosHistoryDto();
	deleteHistoryDto.deletePosHistory(historyId);
}
%>

