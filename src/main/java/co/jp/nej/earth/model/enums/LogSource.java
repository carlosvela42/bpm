package co.jp.nej.earth.model.enums;

public enum LogSource {
    AUTHENTICATION("Authentication")
    ,ENCRYPTION_AND_DESCRYPTION("Encryption and descryption")
    ,LOG("Log")
    ,MASTER_DATA_MANAGEMENT("Master data management")
    ,PASSWORD_POLICY("Password Policy")
    ,AUTHORIZATION("Authorization")
    ,LOGIN_SCREEN("Login screen")
    ,LICENSE_REPORT_SCREEN("License Report screen")
    ,ROLE_MANAGEMENT_SCREEN("Role management screen")
    ,PROFILE_MANAGEMENT_SCREEN("Profile management screen")
    ,SITE_MANAGEMENT_SCREEN("Site management screen")
    ,DATA_DIRECTORY_MANAGEMENT_SCREEN("Data directory management screen")
    ,WORKSPACE_LIST_SCREEN("Workspace list screen")
    ,TEMPLATE_MANAGEMENT_SCREEN("Template management screen")
    ,WORKSPACE_DETAIL_SCREEN("Workspace detail screen")
    ,WORKITEM_SELECTION_SCREEN("WorkItem selection screen")
    ,DATA_EDITING_SCREEN("Data editing screen")
    ,IMAGEVIEWER("ImageViewer")
    ,TRAIL_LOG_SCREEN("Trail log screen")
    ,MENU_MASTER_MANAGEMENT_SCREEN("Menu master management screen")
    ,MENU_ACCESS_RIGHTS_MANAGEMENT_SCREEN("Menu access rights management screen")
    ,PROCESS_SERVICE_UPDATE("Process Service Update")
    ,THEMESCAN_IMPORT_SERVICE("Themescan import service")
    ,OPERATION_DATE_CHANGE_SERVICE("Operation Date change service")
    ;

    private String propertyName;
    LogSource(String name) {
        propertyName = name;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
