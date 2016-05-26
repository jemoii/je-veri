package me.voler.jeveri.core;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 根据运行环境加载classpath下不同路径的*.properties文件
 */
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	/**
	 * 根据运行环境加载classpath下不同路径的*.properties文件作为
	 * {@link org.springframework.core.io.support.PropertiesLoaderSupport#localProperties
	 * localProperties}
	 * 
	 * <pre class="code">
	 * {@code
	 * <property name="propFiles"> 
	 * 	<list> 
	 * 		<value>}${env}/jdbc.properties{@code</value>
	 * 	</list> 
	 * </property>}
	 * 
	 * 如上配置，在本地机器运行，默认将“${env}”替换为“local”，等价于
	 * 
	 * <pre class="code">
	 * {@code
	 * <property name="propFiles"> 
	 * 	<list> 
	 * 		<value>}classpath: local/jdbc.properties{@code</value>
	 * 	</list> 
	 * </property>}
	 * 
	 * @param propFiles
	 *            classpath下的*.properties文件
	 * 
	 * @see org.springframework.core.io.support.PropertiesLoaderSupport#setProperties
	 */
	public void setPropFiles(List<String> propFiles) {
		String env = System.getProperty("os.name").equals("Linux") ? "prod" : "local";

		Properties properties = new Properties();
		for (String propFile : propFiles) {
			propFile = StringUtils.replace(propFile, "${env}", env);
			Properties prop = new Properties();
			InputStream inputStream = CustomPropertyPlaceholderConfigurer.class.getClassLoader()
					.getResourceAsStream(propFile);
			try {
				prop.load(inputStream);
			} catch (IOException ex) {
				throw new BeanInitializationException("Could not load properties", ex);
			}
			properties.putAll(prop);
		}

		this.setProperties(properties);
	}

}
