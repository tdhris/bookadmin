sap.ui.controller("library-admin-web.login", {

	loginPress: function() {
		username = this.byId("usernameInput").getValue();
		password = this.byId("passwordInput").getValue();
		if (username == "admin" && password == "admin") {
			
		} else {
			sap.ui.commons.MessageBox.alert("Invalid username/password combination");
		}
	}
});