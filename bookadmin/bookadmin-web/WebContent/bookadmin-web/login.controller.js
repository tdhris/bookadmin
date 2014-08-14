sap.ui.controller("bookadmin-web.login", {

	loadMainView : function() {
		var oMainView = sap.ui.getCore().byId("idmain1");
		if (!oMainView) {
			oMainView = sap.ui.view({
				type : sap.ui.core.mvc.ViewType.JS,
				id : "idmain1",
				viewName : "bookadmin-web.main"
			});

		}
		return oMainView;
	},
	
	isBookAdmin : function(sUsername, sPassword) {
		if(sUsername == "admin" && sPassword == "admin") {
			return true;
		} else {
			false;
		}
	}

});