package aaron.mybatis.generator;

import java.text.MessageFormat;
import java.util.List;

import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtility;

public class CustomMyBatis3SimpleImpl extends IntrospectedTableMyBatis3Impl {
    /**
     * 修改生成的mapper xml文件中resultMap的id，原来为BaseResultMap，现修改为实体类名称
     */
    @Override
    public void setBaseResultMapId(String s) {
        // 实体类名称
        String objectName = fullyQualifiedTable.getDomainObjectName();
        // 将resultMap的id设置为实体类首字母小写
        objectName = objectName.substring(0, 1).toLowerCase() + objectName.substring(1);
        super.setBaseResultMapId(objectName);
    }

    /**
     * Mapper类的生成方法
     * 
     * @return
     */
    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (this.context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        } else {
            // targetProject
            String targetProject = this.context.getJavaModelGeneratorConfiguration().getTargetProject();
            String type = this.context.getJavaClientGeneratorConfiguration().getConfigurationType();
            Object javaGenerator;
            if ("XMLMAPPER".equalsIgnoreCase(type)) {
                javaGenerator = new MySimpleJavaClientGenerator(targetProject);
            } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) {
                javaGenerator = new MySimpleAnnotatedClientGenerator(targetProject);
            } else if ("MAPPER".equalsIgnoreCase(type)) {
                javaGenerator = new MySimpleJavaClientGenerator(targetProject);
            } else {
                javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
            }
            return (AbstractJavaClientGenerator) javaGenerator;
        }
    }

    /**
     * model类的生成方法
     * 
     * @param warnings
     * @param progressCallback
     */
    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        super.calculateJavaModelGenerators(warnings, progressCallback);
    }

    /**
     * XML的生成方法
     * 
     * @param javaClientGenerator
     * @param warnings
     * @param progressCallback
     */
    @Override
    protected String calculateMyBatis3XmlMapperFileName() {
        StringBuilder sb = new StringBuilder();
        if (StringUtility.stringHasValue(tableConfiguration.getMapperName())) {
            String mapperName = tableConfiguration.getMapperName();
            int ind = mapperName.lastIndexOf('.');
            if (ind != -1) {
                mapperName = mapperName.substring(ind + 1);
            }
            // 支持mapperName = "{0}Dao" 等用法
            System.out.println("mapperName:" + mapperName);
            sb.append(MessageFormat.format(mapperName, fullyQualifiedTable.getDomainObjectName()));
            sb.append(".xml"); //$NON-NLS-1$
        } else {
            sb.append(fullyQualifiedTable.getDomainObjectName());
            sb.append("Mapper.xml"); //$NON-NLS-1$
        }
        return sb.toString();
    }

}
