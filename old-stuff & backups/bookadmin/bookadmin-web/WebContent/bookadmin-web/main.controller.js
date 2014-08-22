sap.ui.controller("bookadmin-web.main", {

	onInit : function() {
		var oBooksModel = new sap.ui.model.json.JSONModel();
		oBooksModel.setData({
			Books : [ {
				Title : "",
				Author : "",
				Description : "",
				Copies : ""
			} ]
		});
		this.getView().setModel(oBooksModel);
	},

	addNewBook : function(sTitle, sAuthor, sDescription, sCopies) {
		var oBooksModel = new sap.ui.model.json.JSONModel();
		oBooksModel.setData({
			Books : [ {
				Title : sTitle,
				Author : sAuthor,
				Description : sDescription,
				Copies : sCopies
			} ]
		});
		this.getView().setModel(oBooksModel);
		oTable.unbindRows().bindRows("/Books");
	},

});