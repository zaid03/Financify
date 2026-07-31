module com.financify {
    requires javafx.controls;
    requires java.sql;

    requires org.xerial.sqlitejdbc;

    exports com.financify;
    exports com.financify.views;
    exports com.financify.models;
}