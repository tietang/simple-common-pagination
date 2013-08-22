package commons.page;

/**
 * 单元格处理回叫接口
 * 
 * @author life
 * 
 */
public interface CellCallBack {
	/**
	 * 单元格处理
	 * 
	 * @param data
	 *            行数据,具体类型根据DataModel确定
	 * @return HtmlElement tr元素对象
	 */
	HtmlElement doCell(Object data);
}
