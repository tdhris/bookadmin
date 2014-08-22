sap.ui
		.controller(
				"library-admin-web.main",
				{
					onInit : function() {
						this.getView().setModel(new sap.ui.model.json.JSONModel(this.booksListServiceUrl), "bookModel");
						this.getView().setModel(new sap.ui.model.json.JSONModel(this.usersListServiceUrl), "userModel");
						this.getView().setModel(new sap.ui.model.json.JSONModel({
							book : {'title': "A Game of Thrones", 'author': "George Martin", 'description': 'sdsjkjasdksajdkasjdkjskdj sak', 'copies': "3"},
							user : {'username': "Lala", "facultyNumber": "42343"}
						}), "formModel");
					},

					getModel : function(sModelName) {
						return this.getView().getModel(sModelName);
					},

					addBook : function() {
						var book = this.getModel("formModel").getProperty("/book");
						
						if (this.isValidBook(book)) {
							$.ajax({
								type : "POST",
								url : this.booksListServiceUrl,
								data : JSON.stringify(book),
								contentType : 'application/json; charset=UTF-8',
								error : function() {
									sap.m.MessageBox.alert("Failure: could not add book.");
								},
								success : this.successBookPost.bind(this)
							});							
						} else {
							sap.m.MessageBox.alert("Error: please make sure title, author, count and description are valid");
						}
					},

					addUser : function() {
						var user = this.getModel("formModel").getProperty("/user");
						if (this.isValidUser(user)) {
							$.ajax({
								type : "POST",
								url : this.usersListServiceUrl,
								data : JSON.stringify(user),
								contentType : 'application/json; charset=UTF-8',
								error : function() {
									sap.m.MessageBox.alert("Failure: could not add user. Username/faculty number must be unique");
								},
								success : this.successUserPost.bind(this)
							});
						} else {
							sap.m.MessageBox
									.alert("Failure: username must start with a letter, contain only letters, digits and the symbol _. Faculty Number must contain 5 digits and not start with 0");
						}
					},
					
					showUserDetails: function(event) {
						var context = event.getSource().getBindingContext('userModel')
						var userId = context.getProperty('id');
						sap.ui.localResources("library-admin-web");
						app.to("details1", "slide", context.getObject());
					},

					isValidUser : function(oUser) {
						if (!(this.isValidUsername(oUser['username']))) {
							return false;
						} else if (!(this.isValidFacultyNumber(oUser['facultyNumber']))) {
							return false;
						}
						return true;
					},

					isValidBook : function(oBook) {
						if (!(this.isValidTitle(oBook['title']))) {
							alert('title');
							return false;
						} else if (!(this.isValidAuthorName(oBook['author']))) {
							alert('author');
							return false;
						} else if (!(this.isValidCopiesCount(oBook['copies']))) {
							return false;
						} else if (!(this.isValidDescription(oBook['description']))) {
							alert('descr');
							return false;
						}
						return true;
					},

					isValidTitle : function(sTitle) {
						return (/^[A-Z][-a-zA-Z ]{1,29}$/.test(sTitle));
					},

					isValidAuthorName : function(sAuthorName) {
						return (/^[A-Z][a-z]{1,15}( [A-Z][a-z.]{1,15}){1,3}$/.test(sAuthorName));
					},

					isValidDescription : function(sDescription) {
						return (/^.{10,300}$/.test(sDescription));
					},

					isValidCopiesCount : function(sCopiesCount) {
						return (/^[1-9][0-9]{0,4}$/.test(sCopiesCount));
					},

					isValidUsername : function(sUsername) {
						return (/^([a-zA-Z])([a-zA-Z0-9_]){2,14}$/.test(sUsername));
					},

					isValidFacultyNumber : function(sFacultyNumber) {
						return (/^[1-9][0-9]{4}$/.test(sFacultyNumber));
					},

					booksListServiceUrl : "services/Books",
					usersListServiceUrl : "services/Users",

					successBookPost : function() {
						sap.m.MessageBox.alert("Successfully added book");
						this.refreshModel("bookModel", this.booksListServiceUrl);
						this.resetProperty("/book");
					},

					successUserPost : function() {
						sap.m.MessageBox.alert("Successfully added user");
						this.refreshModel("userModel", this.usersListServiceUrl);
						this.resetProperty("/user");
					},

					resetProperty : function(sPropertyName) {
						this.getModel("formModel").setProperty(sPropertyName, {});
					},

					refreshModel : function(sModelName, sModelUrl) {
						this.getModel(sModelName).loadData(sModelUrl);
					}
				});