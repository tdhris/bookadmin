sap.ui.controller("library-admin-web.details", {
	onInit : function() {
		this.getView().setModel(new sap.ui.model.json.JSONModel(), "userModel");
		this.getView().setModel(new sap.ui.model.json.JSONModel(), "activeBooksOfUserModel");
		this.getView().setModel(new sap.ui.model.json.JSONModel(), "pastBooksOfUserModel");
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

	handleSelect : function(oEvent) {
		var selectedItem = oEvent.getParameter("selectedItem");
		if (selectedItem) {
			var bindingContext = selectedItem.getBindingContext("booksModel");
			var bookId = bindingContext.getProperty("id");
			var bookTitle = bindingContext.getProperty("title");
			var user = this.getView().getModel("userModel").getData();
			
			$.ajax({
				type : "PUT",
				url : this.loanServiceUrl + "/users/" + user.id + "/take-book/" + bookId,
				contentType : 'application/json; charset=UTF-8',
				error : function() {
					sap.m.MessageBox.alert("Failure: could not take book.");
				},
				success : function() {
					sap.m.MessageBox.alert(bookTitle + " has been added to " + user.username + "'s books");
				}
			});
		}
	},

	refreshModel : function(sModelName, sModelUrl) {
		this.getModel(sModelName).loadData(sModelUrl);
	}
});