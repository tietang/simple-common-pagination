package commons.utils.struts2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import commons.utils.dao.Page;

@SuppressWarnings( { "serial" })
public abstract class CRUDActionSupport<T> extends ActionSupport implements
		Preparable, ModelDriven<T> {
	/**
	 * redirect打开action默认页的view名称定义
	 */
	public static final String RELOAD = "reload";

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 内容辅助函数,获取list页面的内容
	 */
	public abstract Page<T> getPage();

	/**
	 * 内容辅助函数,在inputView与formAction获取或设定entity.
	 */
	public abstract T getModel();

	/**
	 * 内容辅助函数,在formAction中准备entity.
	 */
	public abstract void prepare() throws Exception;

	/**
	 * Action函数,默认execute函数指向list函数
	 */
	@Override
	public String execute() throws Exception {
		return list();
	}

	/**
	 * Action函数,显示列表.
	 */
	public abstract String list() throws Exception;

	/**
	 * Action函数,新增或修改entity.
	 */
	public abstract String save() throws Exception;

	/**
	 * Action函数,删除entity.
	 */
	public abstract String delete() throws Exception;

	/**
	 * 如果子类不支持规定的抽象方法，可调用此函数抛出异常.
	 */
	protected void methodNotSupport() throws Exception {
		throw new Exception("不支持此操作");
	}
}
