package zerobase.wifi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class WifiInfoModel {
	
	private String X_SWIFI_MGR_NO;   		// 관리번호: 와이파이 정보의 식별자 (기본키)
    private String X_SWIFI_WRDOFC;  		// 자치구
    private String X_SWIFI_MAIN_NM;  		// 와이파이명
    private String X_SWIFI_ADRES1;  		// 도로명주소
    private String X_SWIFI_ADRES2;  		// 상세주소
    private String X_SWIFI_INSTL_FLOOR;  	// 설치위치
    private String X_SWIFI_INSTL_TY;  		// 설치유형
    private String X_SWIFI_INSTL_MBY;  		// 설치기관
    private String X_SWIFI_SVC_SE;  		// 서비스구분
    private String X_SWIFI_CMCWR;  			// 망종류
    private double LAT;  					// X좌표
    private double LNT;  					// Y좌표
    private int X_SWIFI_CNSTC_YEAR;  		// 설치년도
    private String X_SWIFI_INOUT_DOOR;  	// 실내외구분
    private String X_SWIFI_REMARS3;  		// WIFI접속환경
    private String WORK_DTTM;  				// 작업일자
	
    private double distance;
    
    //거리 계산 로직 - 이 로직은 따로 클래스를 구현하여 별도로 관리하면 좋을 것 같긴한데, 마땅한 부분이 없어서 Model에 처리.
    private static final double EARTH_RADIUS = 6371; // 지구 반지름 (단위: km)

    /**
     * 두 지점 간의 거리를 계산하여 반환합니다.
     *
     * @param lat1 첫 번째 지점의 위도
     * @param lon1 첫 번째 지점의 경도
     * @param lat2 두 번째 지점의 위도
     * @param lon2 두 번째 지점의 경도
     * @return 두 지점 간의 거리 (단위: km)
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}


