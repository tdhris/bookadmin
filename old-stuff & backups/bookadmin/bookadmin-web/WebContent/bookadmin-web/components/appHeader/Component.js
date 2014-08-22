jQuery.sap.require("sap.ui.core.UIComponent");
jQuery.sap.require("new sap.ui.commons.ApplicationHeader");
jQuery.sap.declare("components.appHeader.Component");

sap.ui.core.UIComponent.extend("components.appHeader.Component", {

    metadata : {
//        properties : {
//        	logoText: "string",
//        	displayLogoff: "boolean",
//        	displayWelcome: "boolean",
//        	userName: "string"
//        }
    	
    	
    }
});


components.appHeader.Component = function(){
    this.oAppHeader = ew sap.ui.commons.ApplicationHeader(this.createId("appHeader"));
    this.oAppHeader.setLogoText("Book Admin");
    this.oAppHeader.setDisplayWelcome(true);
    this.oAppHeader.setUserName("book-admin");
    this.oAppHeader.setDisplayLogoff(true);
    return this.oAppHeader;
    
};
