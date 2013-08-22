package commons.page;

import commons.page.model.DataModel;

/**
 * 分页数据回叫接口
 * 
 * @author life
 * 
 */
public interface PageDataCallBack {
	/**
	 * 通过startRow,maxRow两个参数,传递给数据提供程序,来实现数据库分页
	 * 
	 * @param startRow
	 *            开始行
	 * @param maxRow
	 *            最大行
	 * @return 包装的DataModel子类
	 */
	DataModel doPage(int startRow, int maxRow);
}
