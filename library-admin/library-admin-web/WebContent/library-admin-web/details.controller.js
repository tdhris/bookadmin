sap.ui.controller("library-admin-web.details", {

	onInit : function() {
		this.getView().setModel(new sap.ui.model.json.JSONModel(), "userModel");
	},

	onExit : function() {
		if (this._oDialog) {
			this._oDialog.destroy();
		}
	},

	booksListServiceUrl : "services/Books",
	loanServiceUrl : "services/Loans",

	goBack : function() {
		app.to("main1");
	},

	updateModel : function(event) {
		this.getView().getModel("userModel").setData(event.data);
		var user = this.getView().getModel("userModel").getData();
		var currentBookLoans = this.loanServiceUrl + "/users/" + user.id + "/current-loans";
		var returnedBookLoans = this.loanServiceUrl + "/users/" + user.id + "/returned-loans";
		this.getView().setModel(new sap.ui.model.json.JSONModel(currentBookLoans), "activeBookLoansOfUserModel");
		this.getView().setModel(new sap.ui.model.json.JSONModel(returnedBookLoans), "pastBookLoansOfUserModel");

	},

	showBookList : function(oEvent) {
		if (!this._oDialog) {
			this._oDialog = sap.ui.xmlfragment("library-admin-web.bookdialog", this.getView().getController());
		}
		this._oDialog.setModel(new sap.ui.model.json.JSONModel(this.booksListServiceUrl), "booksModel");
		jQuery.sap.syncStyleClass("sapUiSizeCompact", this.getView(), this._oDialog);
		this._oDialog.open();
	},

	handleSearch : function(oEvent) {
		var sValue = oEvent.getParameter("value");
		var oFilter = new sap.ui.model.Filter("Title", sap.ui.model.FilterOperator.Contains, sValue);
		var oBinding = oEvent.getSource().getBinding("items");
		oBinding.filter([oFilter]);
	},

	refreshModel : function(sModelName, sModelUrl) {
		this.getView().getModel(sModelName).loadData(sModelUrl);
	},

	returnBook : function(book) {
		var bookId = book.id;
		var bookTitle = book.title;
		var user = this.getView().getModel("userModel").getData();

		$.ajax({
			type : "PUT",
			url : this.loanServiceUrl + "/users/" + user.id + "/return-book/" + bookId,
			contentType : 'application/json; charset=UTF-8',
			error : this.loanBookError.bind(this),
			success : function(data, textStatus, jqXHR) {
				sap.m.MessageBox.alert(bookTitle + " has been returned");
				this.refreshModel("pastBookLoansOfUserModel", this.loanServiceUrl + "/users/" + user.id + "/returned-loans");
				this.refreshModel("activeBookLoansOfUserModel", this.loanServiceUrl + "/users/" + user.id + "/current-loans");
			}.bind(this)
		});
	},

	handleSelect : function(oEvent) {
		var selectedItem = oEvent.getParameter("selectedItem");
		if (selectedItem) {
			var book = selectedItem.getBindingContext("booksModel");
			var bookId = book.getProperty("id");
			var bookTitle = book.getProperty("title");
			var user = this.getView().getModel("userModel").getData();

			$.ajax({
				type : "PUT",
				url : this.loanServiceUrl + "/users/" + user.id + "/take-book/" + bookId,
				contentType : 'application/json; charset=UTF-8',
				error : this.loanBookError.bind(this),
				success : function(data, textStatus, jqXHR) {
					sap.m.MessageBox.alert(bookTitle + " has been added to " + user.username + "'s books");
					this.refreshModel("pastBookLoansOfUserModel", this.loanServiceUrl + "/users/" + user.id + "/returned-loans");
					this.refreshModel("activeBookLoansOfUserModel", this.loanServiceUrl + "/users/" + user.id + "/current-loans");
				}.bind(this)
			});
		}
	},

	loanBookError : function(jqXHR, textStatus, errorThrown) {
		sap.m.MessageBox.alert("Failure: " + (jqXHR.responseText || ""));
	},

	confirmReturn : function(oEvent) {
		var book = oEvent.getSource().getBindingContext("activeBookLoansOfUserModel").getProperty("book");
		var message = "Are you sure you want to return '" + book.title + "' by " + book.author;
		sap.m.MessageBox.confirm(message, {
			title : "Return",
			actions : [sap.m.MessageBox.Action.OK, sap.m.MessageBox.Action.CANCEL],
			onClose : function(choice) {
				if (choice == "OK") {
					this.returnBook(book);
				}
			}.bind(this),
			styleClass : ""
		});
	},
});