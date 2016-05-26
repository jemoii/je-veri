package me.voler.jeveri.util.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployUtil {
	private static final Logger Log = LoggerFactory.getLogger(DeployUtil.class);

	public Properties getResources(String propName) {
		InputStream inputStream = DeployUtil.class.getClassLoader().getResourceAsStream(buildPath(propName));
		Properties prop = new Properties();
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			Log.error(String.format("load properties error, %s", e.getMessage()));
			return new Properties();
		}

		return prop;
	}

	/**
	 * 默认情况下，依据{@link #PLATFORM PLATFORM}确定配置文件路径，可以覆盖该方法自定义确定路径的方法
	 * 
	 * @param propName
	 * @return 配置文件路径
	 */
	protected String buildPath(String propName) {
		String platform = PLATFORM.get(System.getProperty("os.name"));
		if (StringUtils.isEmpty(platform)) {
			throw new IllegalArgumentException("need to override buildPath or modify PLATFORM");
		}
		return platform + "/" + propName;
	}

	/** 使用{@code System.getProperty("os.name")}区分本地与线上机器 */
	public static HashMap<String, String> PLATFORM = new HashMap<String, String>();

	static {
		PLATFORM.put("Windows 8.1", "local");
		PLATFORM.put("Linux", "prod");
	}

}
