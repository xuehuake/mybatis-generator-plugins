package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LombokPlugin extends PluginAdapter {
    private Properties systemPro;

    public LombokPlugin() {
        super();
        systemPro = System.getProperties();
    }

    public boolean validate(List<String> list) {
        return true;
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("java.io.Serializable");
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("lombok.Builder");
        // topLevelClass.addImportedType("lombok.NoArgsConstructor");
        // topLevelClass.addImportedType("lombok.AllArgsConstructor");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@Builder");
        // topLevelClass.addAnnotation("@NoArgsConstructor");
        // topLevelClass.addAnnotation("@AllArgsConstructor");

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks());        
        topLevelClass.addJavaDocLine(" * @author " + systemPro.getProperty("user.name"));
        topLevelClass.addJavaDocLine(" * @date " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(new Date()));
        topLevelClass.addJavaDocLine(" */");

        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));        
        return true;
    }

    public boolean clientGenerated(Interface anInterface, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        anInterface.addJavaDocLine("/**");
        anInterface.addJavaDocLine(" * @author zhaoke");
        anInterface.addJavaDocLine(" * @date " + (new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")).format(new Date()));
        anInterface.addJavaDocLine(" */");
        return true;
    }

    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }
}
