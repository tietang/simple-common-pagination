package commons.page;

public interface RowCallBack {

	/**
	 * 行补充处理,tr:预处理行,data:行数据
	 */
	HtmlRow doRow(HtmlRow tr, Object data);
}
