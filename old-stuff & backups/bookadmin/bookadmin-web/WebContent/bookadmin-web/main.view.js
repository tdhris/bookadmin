sap.ui.jsview("bookadmin-web.main", {

	getControllerName : function() {
		return "bookadmin-web.main";
	},

	createContent : function(oController) {
//		var oHeaderContainer = new sap.ui.core.ComponentContainer("headerCont",
//				{
//					name : "components.appHeader",
//				});
//		oHeaderContainer.placeAt("header");

		var oAppHeader = new sap.ui.commons.ApplicationHeader("appHeader");
		oAppHeader.setLogoText("Book Admin");
		oAppHeader.setDisplayWelcome(true);
		oAppHeader.setUserName("book-admin");
		oAppHeader.setDisplayLogoff(true);
		oAppHeader.placeAt("header");

		function getAddBookForm() {
			var oAddBookForm = new sap.ui.layout.form.SimpleForm("addbookform",
					{
						content : [
								new sap.ui.commons.Label({
									text : "Title"
								}),
								new sap.ui.commons.TextField(),
								new sap.ui.commons.Label({
									text : "Author"
								}),
								new sap.ui.commons.TextField(),
								new sap.ui.commons.Label({
									text : "Description"
								}),
								new sap.ui.commons.TextField(),
								new sap.ui.commons.Label({
									text : "Copies"
								}),
								new sap.ui.commons.TextField(),
								new sap.ui.commons.Button({
									id : 'addBookButtonId',
									text : "Submit Book",
									press : function() {
										oController.addNewBook(oAddBookForm
												.getContent());
									}
								}), ]
					})
			return oAddBookForm;
		}

		function getBooksTable() {
			var oTable = new sap.ui.table.Table({
				title : "Books",
				visibleRowCount : 7,
				selectionMode : sap.ui.table.SelectionMode.Single,
			});

			oTable.addColumn(new sap.ui.table.Column({
				label : new sap.ui.commons.Label({
					text : "Title"
				}),
				template : new sap.ui.commons.TextField().bindProperty("value",
						"Title"),
				sortProperty : "Title",
				filterProperty : "Title",
				width : "100px"
			}));

			oTable.addColumn(new sap.ui.table.Column({
				label : new sap.ui.commons.Label({
					text : "Author"
				}),
				template : new sap.ui.commons.TextField().bindProperty("value",
						"Author"),
				sortProperty : "Author",
				filterProperty : "Author",
				width : "200px"
			}));

			oTable.addColumn(new sap.ui.table.Column({
				label : new sap.ui.commons.Label({
					text : "Description"
				}),
				template : new sap.ui.commons.TextField().bindProperty("value",
						"Description"),
				sortProperty : "Description",
				filterProperty : "Description",
				width : "200px"
			}));

			oTable.addColumn(new sap.ui.table.Column({
				label : new sap.ui.commons.Label({
					text : "Copies"
				}),
				template : new sap.ui.commons.TextField().bindProperty("value",
						"Copies"),
				sortProperty : "Copies",
				filterProperty : "Copies",
				width : "200px"
			}));

			oTable.addColumn(new sap.ui.table.Column({
				label : new sap.ui.commons.Label({
					text : "Available Copies"
				}),
				template : new sap.ui.commons.TextField().bindProperty("value",
						"AvailableCopies"),
				sortProperty : "AvailableCopies",
				filterProperty : "AvailableCopies",
				width : "200px"
			}));

			oTable.bindRows("/Books");
			return oTable;
		}
		;

		var content = new sap.ui.commons.Panel();
		content.addContent(getBooksTable());

		var oListBooks = new sap.ui.ux3.NavigationItem({
			key : "listbooks",
			text : "List Books"
		});
		var oNavigationBar = new sap.ui.ux3.NavigationBar({
			items : [ new sap.ui.ux3.NavigationItem({
				key : "addbook",
				text : "Add Book"
			}), new sap.ui.ux3.NavigationItem({
				key : "listusers",
				text : "List Users"
			}), oListBooks, new sap.ui.ux3.NavigationItem({
				key : "adduser",
				text : "Add User"
			}), ],
			selectedItem : oListBooks,
			select : function(oEvent) {
				var key = oEvent.getParameter("item").getKey();
				content.removeAllContent();
				content.removeAllButtons();

				if (key == "listbooks") {
					content.addContent(getBooksTable());
				}

				else if (key == "addbook") {
					content.addContent(getAddBookForm());
				}
			}
		});

		oNavigationBar.placeAt("navigation");
		content.placeAt("content");
	},
});
