package zerobase.wifi.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonObject;

import zerobase.wifi.Secret;
import zerobase.wifi.dto.WifiInfoDto;
import zerobase.wifi.model.WifiInfoModel;

//서울시 공공 와이파이 정보를 제공하는 API를 호출하고, 결과를 처리하는 기능
public class WifiApiComponent {

	// 전체 데이터를 가져오기 위한 메소드
	public int getTotal() {
		int total = 0;
		int page = 1; // 시작 페이지 번호
		int startIndex = 1;
		int endIndex = 1000;
		boolean nextPage = true;

		try {
			while (nextPage) {
				StringBuilder url = new StringBuilder("http://openapi.seoul.go.kr:8088/");
				url.append(URLEncoder.encode(Secret.KEY, "UTF-8"));
				url.append("/json/TbPublicWifiInfo/");
				url.append(startIndex).append("/").append(endIndex);

				// API 호출을 위한 URL 객체 생성
				URL apiUrl = new URL(url.toString());

				// HTTP 연결 객체 생성
				HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();

				// GET 요청 설정
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");

				// BufferedReader를 사용하여 데이터를 읽어옴
				BufferedReader br;
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				} else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}

				StringBuilder sb = new StringBuilder();
				String line;

				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				br.close();
				conn.disconnect();

				String json = sb.toString();
				int count = parseJson(json); // JSON 데이터 파싱하여 개수를 반환받음
				total = startIndex + count; // 총개수

				nextPage = (count == 1000 ? true : false);

				if (nextPage) {
					startIndex = count * page + 1;
					endIndex = startIndex + 999;
				}
				page++;

			}
		} catch (Exception e) {
			System.out.println("API 호출에 실패했습니다. " + e.getMessage());
		}

		return total;
	}

	// 전달된 JSON 문자열을 Gson을 사용하여 처리하고, 파싱된 데이터를 gsonObject에 저장
	// 파싱된 데이터를 처리하여 개수를 반환하는 메소드
	
	
	public int parseJson(String json) {
		try {
			JSONParser parser = new JSONParser();

			// JSON 문자열을 JSONObject로 파싱
			JSONObject jsonObj = (JSONObject) parser.parse(json);

			// TbPublicWifiInfo 객체 가져오기
			JSONObject tbPublicWifiInfo = (JSONObject) jsonObj.get("TbPublicWifiInfo");

			// "row" 키의 값인 JSONArray 가져오기
			JSONArray rows = (JSONArray) tbPublicWifiInfo.get("row");

			// 전체 데이터 개수
			int totalCount = rows.size();
			System.out.println(totalCount);
			// Gson 객체 생성
			JsonObject gsonObject = new JsonObject();
			gsonObject.addProperty("totalCount", totalCount);

			// getTotal() 메소드 내의 데이터베이스 
			List<WifiInfoModel> existingData = WifiInfoDto.SelectAll();

			if (existingData.isEmpty()) {
				// 데이터베이스에 저장
				for (Object rowObj : rows) {

					JSONObject row = (JSONObject) rowObj;

					WifiInfoModel wifiInfoModel = new WifiInfoModel();

					wifiInfoModel.setX_SWIFI_MGR_NO((String) row.get("X_SWIFI_MGR_NO")); // 관리번호
					wifiInfoModel.setX_SWIFI_WRDOFC((String) row.get("X_SWIFI_WRDOFC")); // 자치구
					wifiInfoModel.setX_SWIFI_MAIN_NM((String) row.get("X_SWIFI_MAIN_NM")); // 와이파이명
					wifiInfoModel.setX_SWIFI_ADRES1((String) row.get("X_SWIFI_ADRES1")); // 도로명주소
					wifiInfoModel.setX_SWIFI_ADRES2((String) row.get("X_SWIFI_ADRES2")); // 상세주소
					wifiInfoModel.setX_SWIFI_INSTL_FLOOR((String) row.get("X_SWIFI_INSTL_FLOOR")); // 설치위치
					wifiInfoModel.setX_SWIFI_INSTL_TY((String) row.get("X_SWIFI_INSTL_TY")); // 설치유형
					wifiInfoModel.setX_SWIFI_INSTL_MBY((String) row.get("X_SWIFI_INSTL_MBY")); // 설치기관
					wifiInfoModel.setX_SWIFI_SVC_SE((String) row.get("X_SWIFI_SVC_SE")); // 서비스구분
					wifiInfoModel.setX_SWIFI_CMCWR((String) row.get("X_SWIFI_CMCWR")); // 망종류

					// 설치년도 처리(String으로 파싱된 부분을 int로 변환시켜서 처리) 
					String installYearStr = (String) row.get("X_SWIFI_CNSTC_YEAR");
					if (installYearStr != null && !installYearStr.isEmpty()) {
						try {
							int installYear = Integer.parseInt(installYearStr);
							wifiInfoModel.setX_SWIFI_CNSTC_YEAR(installYear);
					//NumberFormatException은 숫자로 변환할 수 없는 문자열을 숫자로 변환하려고 할 때 발생하는 예외
						} catch (NumberFormatException e) {
							System.out.println("설치년도 파싱에 실패했습니다. 설치년도: " + installYearStr);
						}
					}
					wifiInfoModel.setX_SWIFI_INOUT_DOOR((String) row.get("X_SWIFI_INOUT_DOOR")); // 실내외구분
					wifiInfoModel.setX_SWIFI_REMARS3((String) row.get("X_SWIFI_REMARS3")); // WIFI접속환경

					// LAT와 LNT 필드에 값 할당
					String latStr = (String) row.get("LAT");
					String lntStr = (String) row.get("LNT");
					if (latStr != null && lntStr != null) {
						try {
							double lat = Double.parseDouble(latStr);
							double lnt = Double.parseDouble(lntStr);
							wifiInfoModel.setLAT(lat);
							wifiInfoModel.setLNT(lnt);
						} catch (NumberFormatException e) {
							System.out.println("위도, 경도 파싱에 실패했습니다. LAT: " + latStr + ", LNT: " + lntStr);
						}
					}

					wifiInfoModel.setWORK_DTTM(row.get("WORK_DTTM").toString()); // 작업일시

					// WifiInfoDto 객체 생성 - WifiinfoDto 호출에서 데이터 저장.
					WifiInfoDto wifiInfoDto = new WifiInfoDto();
					wifiInfoDto.insert(wifiInfoModel);
				}
			}
			return totalCount;

		} catch (Exception e) {
			System.out.println("JSON 파싱에 실패했습니다. " + e.getMessage());
			return 0;
		}
	}
}