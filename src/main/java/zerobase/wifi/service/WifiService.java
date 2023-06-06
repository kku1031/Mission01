package zerobase.wifi.service;


import java.util.ArrayList;
import java.util.List;

import zerobase.wifi.dto.WifiInfoDto;
import zerobase.wifi.model.WifiInfoModel;

	public class WifiService extends SqliteConnection {

	    //와이파이 정보를 저장	     
		private boolean add(WifiInfoModel model) {
	        WifiInfoDto wifiInfoDto = new WifiInfoDto();
	        wifiInfoDto.insert(model);
	        // 저장 성공 여부에 따라 true 또는 false 반환
	        return true; // 저장 성공한 경우
	    }

		 // 와이파이 정보의 목록을 리턴
	    public List<WifiInfoModel> getList(WifiInfoModel wifiInfoModel) {
	        List<WifiInfoModel> wifiList = new ArrayList<>();
	        // 여기에 데이터베이스에서 Wi-Fi 정보를 조회하여 wifiList에 추가하는 로직을 작성하세요.
	        // parameter 객체를 사용하여 적절한 쿼리를 작성하고, 결과를 WifiInfoDto 형태로 변환하여 리스트에 추가하세요.

	        // 예시 코드: 임시로 더미 데이터 생성
	        WifiInfoModel wifi1 = new WifiInfoModel();
	        wifi1.setX_SWIFI_MGR_NO("1");
	        wifi1.setX_SWIFI_WRDOFC("강남구");
	        wifi1.setX_SWIFI_MAIN_NM("강남와이파이");
	        // 나머지 필드도 설정해주어야 함
	        wifiList.add(wifi1);

	        WifiInfoModel wifi2 = new WifiInfoModel();
	        wifi2.setX_SWIFI_MGR_NO("2");
	        wifi2.setX_SWIFI_WRDOFC("서초구");
	        wifi2.setX_SWIFI_MAIN_NM("서초와이파이");
	        // 나머지 필드도 설정해주어야 함
	        wifiList.add(wifi2);

	        // 구현한 코드로 수정하여 데이터베이스에서 목록을 조회하도록 하세요.

	        return wifiList;
	    }

		//오픈API에서 와이파이 정보를 가져오고,
		//그 내용을 데이터베이스에 저장하고,
		//최종적으로 저장한 개수를 리턴
	    
	    public int saveWifiInfo() {
	        throw new RuntimeException("구현해 주세요.");
	    }    	
	}

