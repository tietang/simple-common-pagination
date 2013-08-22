package commons.page;

/**
 * table 工具类
 * 
 * @author life
 * 
 */
public class TableUtil {
	/**
	 * 以一个String数组或对象生成HTML 行
	 * 
	 * @param cells
	 *            String数组或对象
	 * @return HtmlRow HTML 行tr
	 */
	public static HtmlRow createRow(String... cells) {
		HtmlRow row = new HtmlRow();
		for (int i = 0; i < cells.length; i++) {
			HtmlElement cell = new HtmlElement("td");
			cell.setValue(cells[i] == null ? "&nbsp;" : cells[i]);
			row.addChild(cell);
		}
		return row;
	}

	/**
	 * 以一个Object数组或对象生成HTML 行
	 * 
	 * @param cells
	 *            Object数组或对象
	 * @return HtmlRow HTML 行tr
	 */
	public static HtmlRow createRow(Object... cells) {
		HtmlRow row = new HtmlRow();
		for (int i = 0; i < cells.length; i++) {
			HtmlElement cell = new HtmlElement("td");
			cell.setValue(cells[i] == null ? "&nbsp;" : cells[i].toString());
			row.addChild(cell);
		}
		return row;
	}
}
