<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ page import="java.util.List"%>
<%@ page import="zerobase.wifi.model.PosHistoryModel"%>
<%@ page import="zerobase.wifi.dto.PosHistoryDto"%>
<%@ page import="zerobase.wifi.dto.WifiInfoDto"%>
<%@ page import="zerobase.wifi.model.WifiInfoModel"%>
<%@ page import="java.text.DecimalFormat" %> <!-- 거리를 소수점 형식으로 표시하기 위해 추가 -->

<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link href="${contextPath}/res/css/main.css" rel="stylesheet" />
<script src="${contextPath}/res/js/index.js"></script>
</head>
<body>
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
		
		double LAT = 0.0;  // 초기화
		double LNT = 0.0;  // 초기화
		
		// 위도와 경도 입력 값 받아서 있으면 표시
		if (latInput != null && lntInput != null) {
		    LAT = Double.valueOf(latInput);  // X좌표
		    LNT = Double.valueOf(lntInput);  // Y좌표
		
		    //LocalHistory 생성
		    PosHistoryDto posHistoryDto = new PosHistoryDto();
		    String TIMESTAMP = String.valueOf(System.currentTimeMillis());  // 현재 시간을 문자열로 변환
		    posHistoryDto.insertPosHistory(LAT, LNT, TIMESTAMP);
		
		    WifiInfoDto wifiInfoDto = new WifiInfoDto();
		    // selectByLocation 메서드 호출
		    List<WifiInfoModel> wifiInfoList = wifiInfoDto.selectByLocation(LAT, LNT);
			
		    if (!wifiInfoList.isEmpty()) {
		        for (WifiInfoModel wifiInfoModel : wifiInfoList) {
		            // 거리 계산
		            double distance = WifiInfoModel.calculateDistance(LAT, LNT, wifiInfoModel.getLAT(), wifiInfoModel.getLNT());
		            DecimalFormat df = new DecimalFormat("#.##"); // 소수점 형식으로 표시 (두 자리까지)
		            wifiInfoModel.setDistance(Double.parseDouble(df.format(distance))); // wifiInfoModel에 거리 정보 저장
		%>
            <tr>
                
                <td><%= wifiInfoModel.getLAT() %></td>
                <td><%= wifiInfoModel.getLNT() %></td>
                <td><%= wifiInfoModel.getWORK_DTTM() %></td>
            </tr>
		<%
		        }
		    } else {
		%>
	
		<%
		    }
		}
		%>
		
	</table>	
</body>	
</html>
<script>
	//geolocation 위치 정보 가져오기	
	function showPosition() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				var LAT = position.coords.latitude;
				var LNT = position.coords.longitude;
				document.getElementById("latInput").value = LAT;
				document.getElementById("lntInput").value = LNT;
	
			}, function(error) {
				console.error("위치 정보를 가져오는데 실패했습니다.", error);
				alert("위치 정보를 가져오는데 실패했습니다. 브라우저 설정을 확인해주세요.");
			});
		} else {
			alert("해당 브라우저에서 지원하지 않습니다.");
		}
	}
	
	
	
</script>
