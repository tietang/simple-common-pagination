package commons.utils.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringTools {
	public static Object getBean(String bean) {
		ApplicationContext context = SpringTools.getContext();
		return context.getBean(bean);
	}

	public static ApplicationContext getContext() {
		return new FileSystemXmlApplicationContext(
				"WebContent/WEB-INF/spring/applicationContext.xml");
	}
}
