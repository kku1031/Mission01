package zerobase.wifi.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class WifiInfoModel {
	
	private String X_SWIFI_MGR_NO;   // 관리번호: 와이파이 정보의 식별자 (기본키)
    private String X_SWIFI_WRDOFC;  // 자치구
    private String X_SWIFI_MAIN_NM;  // 와이파이명
    private String X_SWIFI_ADRES1;  // 도로명주소
    private String X_SWIFI_ADRES2;  // 상세주소
    private String X_SWIFI_INSTL_FLOOR;  // 설치위치
    private String X_SWIFI_INSTL_TY;  // 설치유형
    private String X_SWIFI_INSTL_MBY;  // 설치기관
    private String X_SWIFI_SVC_SE;  // 서비스구분
    private String X_SWIFI_CMCWR;  // 망종류
    private double LAT;  // X좌표
    private double LNT;  // Y좌표
    private int X_SWIFI_CNSTC_YEAR;  // 설치년도
    private String X_SWIFI_INOUT_DOOR;  // 실내외구분
    private String X_SWIFI_REMARS3;  // WIFI접속환경
    private Date WORK_DTTM;  // 작업일자
	
}


