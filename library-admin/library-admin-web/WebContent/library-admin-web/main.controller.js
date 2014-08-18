sap.ui.controller("library-admin-web.main", {
	onInit : function() {
		this.getView().setModel(new sap.ui.model.json.JSONModel(this.booksListServiceUrl), "bookModel");
		this.getView().setModel(new sap.ui.model.json.JSONModel(this.usersListServiceUrl), "userModel");
		this.getView().setModel(new sap.ui.model.json.JSONModel({book: {}}), "formModel");
	},

	addBook : function() {
		var book = this.getView().getModel("formModel").getProperty("/book");

		$.ajax({
		  type: "POST",
		  url: this.booksListServiceUrl,
		  data: JSON.stringify(book),
		  contentType: 'application/json; charset=UTF-8'
		}).done(this.successBookPost.bind(this)).fail(function() {alert("noooo");});
		
	},

	booksListServiceUrl: "services/Books",
	usersListServiceUrl: "services/Users",
	
	successBookPost: function() {
		sap.m.MessageBox.alert("Successfully added book");
		this.refreshModel("bookModel", this.booksListServiceUrl);
		this.getView().getModel("formModel").setProperty("/book", {});
	},
	
	successUserPost: function() {
		sap.m.MessageBox.alert("Successfully added user");
		this.refreshModel("userModel", this.booksListServiceUrl);
		this.getView().getModel("formModel").setProperty("/user", {});
	},
	
	refreshModel: function(sModelName, sModelUrl) {
		this.getView().getModel(sModelName).loadData(sModelUrl);
	},
	
	addUser: function() {
		var user = this.getView().getModel("formModel").getProperty("/user");

		$.ajax({
		  type: "POST",
		  url: this.usersListServiceUrl,
		  data: JSON.stringify(user),
		  contentType: 'application/json; charset=UTF-8'
		}).done(this.successUserPost.bind(this)).fail(function() {alert("noooo");});
	}
});