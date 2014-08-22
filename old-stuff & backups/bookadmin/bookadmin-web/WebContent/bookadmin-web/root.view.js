sap.ui.jsview("bookadmin-web.root", {

	getControllerName : function() {
		return "bookadmin-web.root";
	},

	createContent : function(oController) {
		var login_view = sap.ui.view({
			id : "idlogin1",
			viewName : "bookadmin-web.login",
			type : sap.ui.core.mvc.ViewType.JS
		});
		
		return login_view;
	},
});
