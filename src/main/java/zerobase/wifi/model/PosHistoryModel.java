package zerobase.wifi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PosHistoryModel {
 	
	 private int HISTORYID;       		// 히스토리 식별자 (기본키)
	 private double LAT;    		 	// 위치의 위도 정보 (실수형)
	 private double LNT;    			// 위치의 경도 정보 (실수형)
	 private String TIMESTAMP;    		// 조회한 시간 (텍스트)

}


