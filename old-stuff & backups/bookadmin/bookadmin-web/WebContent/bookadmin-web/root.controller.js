sap.ui.controller("bookadmin-web.main", {

	onInit : function() {
		var sAppUrl = window.location.protocol + "//" + window.location.hostname
				+ (window.location.port ? ":" + window.location.port : "");
		var booksListODataServiceUrl = sAppUrl + "/bookadmin-web/bookadmin.svc";
		var usersListODataServiceUrls = sAppUrl + "/bookadmin-web/bookadmin.svc";

		var bookODataModel = new sap.ui.model.odata.ODataModel(booksListODataServiceUrl);
		var userODataModel = new sap.ui.model.odata.ODataModel(usersListODataServiceUrls);
		this.getView().setModel(bookODataModel, "bookModel");
		this.getView().setModel(userODataModel, "userModel");
	},

	addNewBook : function(sTitle, sAuthor, sDescription, sCopies) {
		var book = {};
		book.Title = sTitle;
		book.Author = sAuthor;
		book.Description = sDescription;
		book.Copies = sCopies;

		this.getView().getModel("bookModel").create("/Books", book);
	},

});