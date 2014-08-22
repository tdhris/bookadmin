sap.ui.jsview("bookadmin-web.login", {

	getControllerName : function() {
		return "bookadmin-web.login";
	},

	createContent : function(oController) {
		var oAppHeader = new sap.ui.commons.ApplicationHeader("appHeader");
		oAppHeader.setLogoText("Book Admin");
		oAppHeader.setDisplayWelcome(false);
		oAppHeader.setDisplayLogoff(false);
		oAppHeader.placeAt("header");

		var oLayout = new sap.ui.commons.layout.AbsoluteLayout({
			id: "loginLayout",
			width : "340px",
			height : "150px"
		});

		var oLabel = new sap.ui.commons.Label({
			text : "Username",
			maxLength : 30
		});
		var oNameInput = new sap.ui.commons.TextField({
			width : "190px"
		});
		oLabel.setLabelFor(oNameInput);
		oLayout.addContent(oLabel, {
			right : "248px",
			top : "20px"
		});
		oLayout.addContent(oNameInput, {
			left : "110px",
			top : "20px"
		});

		oLabel = new sap.ui.commons.Label({
			text : "Password",
			maxLength : 30
		});
		oPWInput = new sap.ui.commons.PasswordField({
			width : "190px"
		});
		oLabel.setLabelFor(oPWInput);
		oLayout.addContent(oLabel, {
			right : "248px",
			top : "62px"
		});
		oLayout.addContent(oPWInput, {
			left : "110px",
			top : "62px"
		});

		var oLoginButton = new sap.ui.commons.Button({
			text : "Login",
			width : "133px"
		});
		oLoginButton.attachPress(function() {
			var isLog = oController.isBookAdmin(oNameInput.getValue(), oPWInput.getValue());
			if (isLog) {
				oLayout.destroyContent();
				oLayout.destroyPositions();
				oController.loadMainView();
			} else {
				sap.ui.commons.MessageBox.alert("Wrong username/password combination");
			}
		});

		oLayout.addContent(oLoginButton, {
			left : "110px",
			top : "104px"
		});

		oLayout.placeAt("login");
	}

});
