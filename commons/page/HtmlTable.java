package commons.page;

import java.util.Calendar;
import java.util.List;

import commons.page.model.ArrayDataModel;
import commons.page.model.DataModel;
import commons.page.model.ListDataModel;

/**
 * HTML table 元素
 * 
 * <pre>
 * 
 * int len = 125;
 * String[][] data = new String[len][3];
 * for (int i = 0; i &lt; data.length; i++) {
 * 	data[i] = new String[] { &quot;列1-&quot; + i, &quot;咧2-&quot; + i, &quot;烈3-&quot; + i };
 * }
 * List&lt;Object[]&gt; d2 = new ArrayList&lt;Object[]&gt;();
 * for (int i = 0; i &lt; len; i++) {
 * 	Object[] o = new Object[] { &quot;list列1-&quot; + i, &quot;list咧2-&quot; + i, &quot;list烈3-&quot; + i };
 * 	d2.add(o);
 * }
 * HtmlTable t = new HtmlTable();
 * // t.setData(data);
 * t.setData(d2);
 * t.setHeader(&quot;标题1,标题2&quot;);
 * // t.setStartRowIndex(34);
 * t.setMaxRowNum(12);
 * System.out.println(t.toHtml());
 * </pre>
 * 
 * 实际使用例子可以参考PageDataTable
 * 
 * @author life
 * 
 */
public class HtmlTable extends HtmlElement {
	private HtmlElement thead = new HtmlElement("thead");
	private HtmlElement tbody = new HtmlElement("tbody");
	private HtmlElement tfoot = new HtmlElement("tfoot");
	private String[] header = null;
	private String[] footer = null;
	private DataModel dataModel = null;
	private int startRowIndex = -1;// 开始行索引
	private int maxRowNum = -1;// 最大行数
	private CellCallBack cellCallBack;// 单元格回叫
	private RowCallBack rowCallBack;// 行回叫
	private String noDataNote = "无数据!";// 无数据提示信息
	private int columnNum = 1;// 列数
	private boolean isRowMouseOver = true;
	private boolean isRowSelected = true;

	public HtmlTable() {
		super("table");
	}

	/**
	 * 设置单元格处理回叫对象
	 * 
	 * @param callBack
	 */
	public void setCellCallBack(CellCallBack cellCallBack) {
		this.cellCallBack = cellCallBack;
	}

	/**
	 * 设置每页显示最大行数
	 * 
	 * @param maxRowNum
	 */
	public void setMaxRowNum(int maxRowNum) {
		this.maxRowNum = maxRowNum;
	}

	/**
	 * 设置开始行索引
	 * 
	 * @param startRowIndex
	 */
	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	/**
	 * 以String数组设置表头
	 * 
	 * @param header
	 */
	public void setHeader(String[] header) {
		this.header = header;
	}

	/**
	 * 以逗号(,)分割的字符串设置表头
	 * 
	 * @param header
	 */
	public void setHeader(String header) {
		this.header = header.split(",");
	}

	/**
	 * 以String数组设置表页脚
	 * 
	 * @param footer
	 */
	public void setFooter(String[] footer) {
		this.footer = footer;
	}

	/**
	 * 以逗号(,)分割的字符串设置表页脚
	 * 
	 * @param footer
	 */
	public void setFooter(String footer) {
		this.footer = footer.split(",");
	}

	/**
	 * 设置所包装的DataModel
	 * 
	 * @param dataModel
	 */
	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * 设置数组数据
	 * 
	 * @param data
	 */
	public void setData(Object[][] data) {
		this.dataModel = new ArrayDataModel();
		this.dataModel.setWrappedData(data);
	}

	/**
	 * 设置List数据
	 * 
	 * @param data
	 */
	public void setData(List data) {
		this.dataModel = new ListDataModel();
		this.dataModel.setWrappedData(data);
	}

	public void setTbody(HtmlElement tbody) {
		this.tbody = tbody;
	}

	public void setTfoot(HtmlElement tfoot) {
		this.tfoot = tfoot;
	}

	public void setThead(HtmlElement thead) {
		this.thead = thead;
	}

	/**
	 * 生成HTML
	 */
	@Override
	public String toHtml() {

		this.process();
		return super.toHtml();
	}

	/**
	 * 预处理
	 */
	private void process() {
		// 处理表头,将表头添加到表格
		if (this.header != null) {
			this.thead.getChildren().clear();
			HtmlElement tr = new HtmlElement("tr");
			for (String header : this.header) {
				HtmlElement th = new HtmlElement("th");
				th.setText(header);
				tr.addChild(th);
			}
			this.thead.addChild(tr);
			this.addChild(this.thead);
			this.columnNum = this.header.length;
		}
		// 处理主题数据
		if (this.dataModel != null) {
			if (this.cellCallBack == null) {
				// 默认单元格数据处理
				this.cellCallBack = new CellCallBack() {
					public HtmlElement doCell(Object data) {
						HtmlRow tr = new HtmlRow();
						// 行数据为String数组
						if (data instanceof String[]) {
							String[] ds = (String[]) data;
							int len = ds.length;
							if (header == null) {
								columnNum = len;
							} else {
								if (len > header.length) {
									len = header.length;
								}
							}
							for (int i = 0; i < len; i++) {
								HtmlElement td = new HtmlElement("td");
								td.setText(ds[i] == null ? "" : ds[i]);
								tr.addChild(td);
							}
							// 行数据为Object数组
						} else if (data instanceof Object[]) {
							Object[] os = (Object[]) data;
							int len = os.length;
							if (header == null) {
								columnNum = len;
							} else {
								if (len > header.length) {
									len = header.length;
								}
							}
							for (int i = 0; i < len; i++) {
								HtmlElement td = new HtmlElement("td");
								td.setText(os[i] == null ? "" : os[i]
										.toString());
								tr.addChild(td);
							}
						}
						return tr;
					}
				};
			}
			int startRow = 0;
			int endRow = 0;
			// 处理需要输出的行
			int j = this.dataModel.getRowCount();
			if (this.startRowIndex > 0) {
				startRow = this.startRowIndex;
			}
			if (this.maxRowNum < 0) {
				endRow = this.dataModel.getRowCount();
			} else {
				endRow = startRow + this.maxRowNum;
			}
			if (endRow > j) {
				endRow = j;
			}
			// 清除
			this.tbody.getChildren().clear();

			for (int i = startRow; i < endRow; i++) {
				this.dataModel.setRowIndex(i);
				HtmlElement tr = this.cellCallBack.doCell(this.dataModel
						.getRowData());
				if (this.isRowMouseOver) {
					tr.addAttribute("onmouseover",
							"this.className='horse_page_table_tr_mouse'");
					tr.addAttribute("onmouseout", "this.className=''");
				}
				// onClick="this.className='horse_page_table_tr_selected'"
				if (this.isRowSelected) {
				//	tr.addAttribute("onclick", "dataGirdRowSelected(this)");
				}
				this.tbody.addChild(tr);
			}
			// 当前数据不够每页显示的行数,以空白行补充
			if (endRow - startRow < this.maxRowNum) {
				for (int i = 0; i < (this.maxRowNum - (endRow - startRow)); i++) {
					HtmlElement row = TableUtil
							.createRow(new String[columnNum]);
					if (this.isRowMouseOver) {
						row.addAttribute("onmouseover",
								"this.className='horse_page_table_tr_mouse'");
						row.addAttribute("onmouseout", "this.className=''");
					}
					this.tbody.addChild(row);

				}
			}
			this.addChild(this.tbody);
		}
		// 无主题数据
		if (this.dataModel == null || this.dataModel.getRowCount() < 1) {
			HtmlRow tr = new HtmlRow();
			HtmlElement td = new HtmlElement("td");
			td.setText(this.noDataNote);
			td.addAttribute("class", "no_data_note");
			td.addAttribute("colspan", "" + columnNum);
			tr.addChild(td);

			this.tbody.addChild(tr);
			this.addChild(this.tbody);
		}
		// 表尾处理
		if (this.footer != null) {
			this.tfoot.getChildren().clear();
			HtmlElement tr = new HtmlElement("tr");
			for (String footer : this.footer) {
				HtmlElement td = new HtmlElement("td");
				td.setText(footer);
				tr.addChild(td);
			}
			this.tfoot.addChild(tr);
			this.addChild(this.tfoot);
		}

	}

	/**
	 * 设置行回叫对象
	 * 
	 * @param rowCallBack
	 */
	public void setRowCallBack(RowCallBack rowCallBack) {
		this.rowCallBack = rowCallBack;
	}

	/**
	 * 设置无数据时的提示信息!
	 * 
	 * @param noDataNote
	 */
	public void setNoDataNote(String noDataNote) {
		this.noDataNote = noDataNote;
	}

	public void setRowMouseOver(boolean isRowMouseOver) {
		this.isRowMouseOver = isRowMouseOver;
	}

	public void setRowSelected(boolean isRowSelected) {
		this.isRowSelected = isRowSelected;
	}

}
