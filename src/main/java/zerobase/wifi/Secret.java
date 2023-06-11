package zerobase.wifi;

public class Secret {

	//인증키, DB아이디, DB 비밀번호와 같이 암호화가 필요한 경우에는 클래스를 따로 빼서 관리한다.
	//사용자 이름,비밀번호는 sqlite/sqlite3 패키지에서 지원되지 않으므로 여기선 구현하지 않았다. 
	//프로젝트에 .gitignore(파일)을 생성 - 경로를 지정(./Secret)하여 이클립스 깃 사용시 올라가지 않도록 한다. 
	
	public static final String KEY = "48597547786b6b753733536a69744b";
//	public static final String ID = "DB아이디";
//	public static final String PASSWORD = "DB비밀번호";
}
