package pe.cp.core.service.messages;

public class LoginRequest {
	private String loginName;
	private String password;
	
	public LoginRequest(String loginName, String password) {
		this.loginName = loginName;
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getPassword() {
		return password;
	}	
}
