package commons.page;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页主类
 * 
 * <pre>
 * 
 * 
 *  import java.util.ArrayList;
 *  import java.util.List;
 * 
 *  import javax.servlet.http.HttpServletRequest;
 * 
 *  import commons.page.model.DataModel;
 *  import commons.page.model.ListDataModel;
 * 
 *  public class TestPage {
 * 
 *  // 内存分页测试
 * 
 *  public static String se(HttpServletRequest request) {
 *  int len = 125;
 *  String[][] data = new String[len][6];
 *  for (int i = 0; i &lt; data.length; i++) {
 *  data[i] = new String[] { &quot;String列1-&quot; + i, &quot;String咧2-&quot; + i, &quot;String烈3-&quot; + i, &quot;String烈4-&quot; + i, &quot;String烈5-&quot; + i, &quot;String烈6-&quot; + i };
 *  }
 *  List&lt;Object[]&gt; d2 = new ArrayList&lt;Object[]&gt;();
 *  for (int i = 0; i &lt; len; i++) {
 *  Object[] o = new Object[] { &quot;list列1-&quot; + i, &quot;list咧2-&quot; + i, &quot;list烈3-&quot; + i, &quot;list烈4-&quot; + i, &quot;list烈5-&quot; + i, &quot;list烈6-&quot; + i };
 *  d2.add(o);
 *  }
 *  HtmlTable t = new HtmlTable();
 *  t.addAttribute(&quot;width&quot;, &quot;100%&quot;);
 *  t.addAttribute(&quot;border&quot;, &quot;1&quot;);
 *  // t.setData(data);//数组测试
 *  t.setData(d2);// List测试
 *  t.setHeader(&quot;标题1,标题3,标题4,标题6,标题5&quot;);
 *  t.setCellCallBack(new CellCallBack() {
 *  public HtmlRow doCell(Object data) {
 *  // 数组测试
 *  // String[] s = (String[] ) data;
 *  // HtmlRow row = TableUtil.createRow(new Object[] {
 *  // s[0],s[2],s[3],s[5],s[4]});
 * 
 *  // List测试
 *  Object[] s = (Object[]) data;
 *  HtmlRow row = TableUtil.createRow(new Object[] { s[0], s[2], s[3], s[5], s[4] });
 * 
 *  return row;
 *  }
 *  });
 * 
 *  PageDataTable p = new PageDataTable(request, 0);
 *  p.setPageSize(12);
 *  p.setTotalRows(len);
 *  p.setTable(t);
 *  return p.render();
 *  }
 * 
 *  // 查询分页测试
 * 
 *  public static String se2(HttpServletRequest request) {
 *  final int len = 125;
 *  HtmlTable t = new HtmlTable();
 *  t.setHeader(&quot;标题1,标题3,标题4,标题6,标题5&quot;);
 *  PageDataTable p = new PageDataTable(request, &quot;__my_2098_&quot;, 1);
 *  p.setPageSize(9);
 *  p.setTotalRows(len);
 *  p.setPageDataCallBack(new PageDataCallBack() {
 *  &#064;Override
 *  public DataModel doPage(int startRow, int maxRow) {
 *  List&lt;Object[]&gt; d2 = new ArrayList&lt;Object[]&gt;();
 *  // 下面为测试所用,模拟数据库查询,实际使用时将startRow,maxRow两个变量传入SQL语句查询数据库获取相应的记录
 *  int endRow = (startRow + maxRow) &gt; len ? len : (startRow + maxRow);
 *  for (int i = startRow; i &lt; endRow; i++) {
 *  Object[] o = new Object[] { &quot;list列1-&quot; + i, &quot;list咧2-&quot; + i, &quot;list烈3-&quot; + i, &quot;list烈4-&quot; + i, &quot;list烈5-&quot; + i, &quot;list烈6-&quot; + i };
 *  d2.add(o);
 *  }
 *  DataModel dataModel = new ListDataModel();
 *  dataModel.setWrappedData(d2);
 *  return dataModel;
 * 
 * 
 * </pre>
 * 
 * @author life
 * 
 */
public class PageDataTable {
	private HtmlTable table = new HtmlTable();// 分页表格
	private int operate = 0;// 操作,默认为第一页
	private int gopage = 1;// 转到页码
	private int cpage = 1;// 当前页
	private int rowsize = 13;// 默认13行
	private Pager pager = new Pager();// 分页操作类
	private int mode = 0;// 模式0:内存分页,1:数据库查询分页
	private PageDataCallBack callBack;// 适用于mode=1时(数据库查询分页),为HtmlTable提供DataModel数据模型回叫对象
	private String id = "";// 分页表格ID,默认值为2098,该ID将在一个页面使用多个分页表格时作为唯一标识,标记正在操作的分页表格,如果有重复,将不能正常工作
	private final static String DEFAULT_ID = "2098";
	private String[] pageText = new String[] { "首页", "上一页", "下一页", "末页" };// 设置分页文本的信息

	/**
	 * 以HttpServletRequest请求,分页表格ID,分页模式来构造
	 * 
	 * @param request
	 *            HttpServletRequest JSP页面:通常是从jsp页面传过来,
	 *            Struts2:在Action类中可以使用ServletActionContext.getRequest()来获取,
	 *            JSF:可以在ManagedBean中使用FacesContext 对象来获取 其他框架可以具体参考其文档
	 * @param id
	 *            分页表格ID
	 * @param mode
	 *            分页模式 0:内存分页,1:数据库查询分页
	 */
	public PageDataTable(HttpServletRequest request, String id, int mode) {
		this.init(request, id, mode);
	}

	/**
	 * 以HttpServletRequest请求, 默认ID,分页模式来构造,适用于每个页面只有一个分页表格的情况
	 * 
	 * @param request
	 *            HttpServletRequest 参考PageDataTable(HttpServletRequest request,
	 *            String id, int mode)构造器
	 * @param mode
	 *            分页模式 0:内存分页,1:数据库查询分页
	 */
	public PageDataTable(HttpServletRequest request, int mode) {
		this.init(request, DEFAULT_ID, mode);
	}

	/**
	 * 以HttpServletRequest请求, 分页表格ID,适用于内存分页的情况
	 * 
	 * @param request
	 *            HttpServletRequest 参考PageDataTable(HttpServletRequest request,
	 *            String id, int mode)构造器
	 * @param id
	 *            分页表格ID
	 */
	public PageDataTable(HttpServletRequest request, String id) {
		this.init(request, id, 0);
	}

	/**
	 * 
	 * @param request
	 *            HttpServletRequest 参考PageDataTable(HttpServletRequest request,
	 *            String id, int mode)构造器
	 */
	public PageDataTable(HttpServletRequest request) {
		this.init(request, DEFAULT_ID, 0);
	}

	/**
	 * 初始化请求,并应用请求值
	 * 
	 * @param request
	 *            HttpServletRequest 参考PageDataTable(HttpServletRequest request,
	 *            String id, int mode)构造器
	 * @param id
	 *            分页表格ID
	 * @param mode
	 *            分页模式 0:内存分页,1:数据库查询分页
	 */
	private void init(HttpServletRequest request, String id, int mode) {
		this.mode = mode;
		this.id = id;
		String op = request.getParameter("__OPERATE__" + this.id);
		String gopage = request.getParameter("__GOPAGE__" + this.id);
		String cpage = request.getParameter("__CPAGE__" + this.id);
		String maxsize = request.getParameter("__MAXROWSIZE__" + this.id);
		// System.out.println("===" +op+"---"+gopage+"---"+cpage);
		this.operate = op == null ? 0 : ("".equals(op) ? 4 : Integer
				.parseInt(op));
		this.gopage = gopage == null ? 1 : Integer.parseInt(gopage);
		this.cpage = cpage == null ? 1 : Integer.parseInt(cpage);
		this.rowsize = maxsize == null ? -1 : Integer.parseInt(maxsize);
	}

	/**
	 * 设置表格对象
	 * 
	 * @param table
	 */
	public void setTable(HtmlTable table) {
		this.table = table;
	}

	/**
	 * 设置每页显示的行数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pager.setPageSize(pageSize);
	}

	/**
	 * 设置总行数
	 * 
	 * @param totalRows
	 */
	public void setTotalRows(int totalRows) {
		this.pager.setTotalRows(totalRows);
	}

	/**
	 * 设置数据模型回叫对象 适用于mode=1时(数据库查询分页),为HtmlTable提供DataModel数据模型回叫对象, 例子:
	 * 
	 * <pre>
	 * PageDataTable p = new PageDataTable(request, &quot;__my_2098_&quot;, 1);
	 * p.setPageSize(9);
	 * p.setTotalRows(len);
	 * p.setPageDataCallBack(new PageDataCallBack() {
	 * 	&#064;Override
	 * 	public DataModel doPage(int startRow, int maxRow) {
	 * 		List&lt;Object[]&gt; d2 = new ArrayList&lt;Object[]&gt;();
	 * 		// 下面为测试所用,模拟数据库查询,实际使用时将startRow,maxRow两个变量传入SQL语句查询数据库获取相应的记录
	 * 		int endRow = (startRow + maxRow) &gt; len ? len : (startRow + maxRow);
	 * 		for (int i = startRow; i &lt; endRow; i++) {
	 * 			Object[] o = new Object[] { &quot;list列1-&quot; + i, &quot;list咧2-&quot; + i,
	 * 					&quot;list烈3-&quot; + i, &quot;list烈4-&quot; + i, &quot;list烈5-&quot; + i, &quot;list烈6-&quot; + i };
	 * 			d2.add(o);
	 * 		}
	 * 		DataModel dataModel = new ListDataModel();
	 * 		dataModel.setWrappedData(d2);
	 * 		return dataModel;
	 * 	}
	 * });
	 * </pre>
	 * 
	 * @param callBack
	 *            数据模型回叫对象
	 */
	public void setPageDataCallBack(PageDataCallBack callBack) {
		this.callBack = callBack;
	}

	/**
	 * 绘制为HTML文本
	 * 
	 * @return HTML文本
	 */
	public String render() {
		// 预分页操作处理
		this.process();
		// 内存分页设置其开始行索引为当前页开始索引,如果为数据库查询分页,则设置开始行索引为0
		if (this.mode == 0) {
			this.table.setStartRowIndex(this.pager.getStartRow());
		} else {
			this.table.setStartRowIndex(0);
		}
		// 设置表格最大显示行数
		this.table.setMaxRowNum(this.pager.getPageSize());
		// mode=1时,数据库查询分页设置分页表格DataModel对象,通常数据库查询也在此时执行
		if (this.callBack != null) {
			this.table.setDataModel(this.callBack.doPage(this.pager
					.getStartRow(), this.pager.getPageSize()));
		}
		this.table.addAttribute("id", "table" + this.DEFAULT_ID);
		// 分页表格界面组织,容器DIV
		HtmlElement div = new HtmlElement("div");
		div.addAttribute("class", "horse_data_page");
		div.addAttribute("id", "horse_" + this.id);
		// 分页表格DIV
		HtmlElement div1 = new HtmlElement("div");
		div1.addAttribute("class", "horse_page_table");
		div1.addChild(this.table);
		div.addChild(div1);
		// 分页工具条
		HtmlElement pageBar = new HtmlElement("Table");
		pageBar.addAttribute("width", "100%");
		HtmlElement row = new HtmlElement("tr");
		row.addChild(this.getPageInfo1());
		row.addChild(this.getPageInfo2());
		row.addChild(this.getPageInfo3());
		row.addChild(this.getPageInfo4());
		pageBar.addChild(row);
		div.addChild(pageBar);
		// 操作用到的脚本
		HtmlElement script = new HtmlElement("script");
		script.addAttribute("language", "javascript");
		StringBuffer sb = new StringBuffer();
		sb.append("function pageOperate");
		sb.append(this.id);
		sb.append("(value){var op=document.getElementById(\"__OPERATE__");
		sb.append(this.id);
		sb.append("\");	op.value=value;	op.form.submit();}");
		script.setValue(sb.toString());
		div.addChild(script);
		return div.toHtml();
	}

	/**
	 * 处理翻页操作
	 */
	private void process() {
		if (this.rowsize > 0) {
			this.pager.setPageSize(this.rowsize);
		}
		this.pager.setCurrentPage(this.cpage);
		if (this.operate == 0) {
			this.pager.first();
		} else if (this.operate == 1) {
			this.pager.previous();
		} else if (this.operate == 2) {
			this.pager.next();
		} else if (this.operate == 3) {
			this.pager.last();
		} else if (this.operate == 4) {
			this.gopage = (this.gopage > this.pager.getTotalPages()) ? this.pager
					.getTotalPages()
					: this.gopage;
			this.pager.setCurrentPage(this.gopage);
		}
	}

	/**
	 * 分页信息1,输出记录信息,总共m页，n条记录
	 * 
	 * @return
	 */
	private HtmlElement getPageInfo1() {
		HtmlElement cell1 = new HtmlElement("td");
		int st = this.pager.getStartRow() <= 0 ? 1 : this.pager.getStartRow();
		st++;
		int ed = (this.pager.getStartRow() + this.pager.getPageSize()) > this.pager
				.getTotalRows() ? this.pager.getTotalRows() : (this.pager
				.getStartRow() + this.pager.getPageSize());
		cell1.setValue("总共" + this.pager.getTotalPages() + "页，"
				+ this.pager.getTotalRows() + "条记录");
		return cell1;
	}

	/**
	 * 分页信息2,输出 首页 上一页 下一页 末页 翻页信息
	 * 
	 * @return
	 */
	private HtmlElement getPageInfo2() {
		HtmlElement cell = new HtmlElement("td");
		HtmlElement a1 = new HtmlElement("a");
		HtmlElement a2 = new HtmlElement("a");
		HtmlElement a3 = new HtmlElement("a");
		HtmlElement a4 = new HtmlElement("a");
		a1.addAttribute("class", "horse_a_paging");
		a2.addAttribute("class", "horse_a_paging");
		a3.addAttribute("class", "horse_a_paging");
		a4.addAttribute("class", "horse_a_paging");
		// if (this.pageText == null) {
		// a1.setValue(" 首页 ");
		// a2.setValue(" 上一页 ");
		// a3.setValue(" 下一页 ");
		// a4.setValue(" 末页 ");
		// } else {
		a1.setValue(this.pageText[0]);
		a2.setValue(this.pageText[1]);
		a3.setValue(this.pageText[2]);
		a4.setValue(this.pageText[3]);

		// }
		if (this.pager.getCurrentPage() > 1) {
			a1.addAttribute("href", "#");
			a1.addAttribute("onclick", "pageOperate" + this.id + "('0')");
			a2.addAttribute("href", "#");
			a2.addAttribute("onclick", "pageOperate" + this.id + "('1')");
		}
		if (this.pager.getTotalPages() > this.pager.getCurrentPage()) {
			a3.addAttribute("href", "#");
			a3.addAttribute("onclick", "pageOperate" + this.id + "('2')");
			a4.addAttribute("href", "#");
			a4.addAttribute("onclick", "pageOperate" + this.id + "('3')");
		}
		cell.addChild(a1);
		cell.addChild(a2);
		if (this.pager.getTotalPages() >= 10) {
			int p = this.pager.getTotalPages() / 10;
			int st = (this.pager.getCurrentPage() - 5) > 0 ? (this.pager
					.getCurrentPage() - 5) : 1;
			int ed = ((this.pager.getCurrentPage() + 5) <= p) ? (this.pager
					.getCurrentPage() + 5) : p;
			for (int i = st; i < ed; i++) {
				HtmlElement a = new HtmlElement("a");
				a.addAttribute("href", "#");
				a.addAttribute("onclick", "pageOperate('5')");
				cell.addChild(a);
			}
			// } else {
			// if (this.pager.getTotalPages() > 0) {
			// int p = this.pager.getTotalPages();
			// for (int i = 1; i < p; i++) {
			// HtmlA a = new HtmlA();
			// a.addAttribute("href", "#");
			// a.addAttribute("onclick", "pageOperate('5')");
			// cell.addChildren(a);
			// }
			// }
		}
		cell.addChild(a3);
		cell.addChild(a4);
		return cell;
	}

	/**
	 * 分页信息3 显示转到某一页
	 * 
	 * @return
	 */
	private HtmlElement getPageInfo3() {
		HtmlElement cell3 = new HtmlElement("td");
		HtmlElement input = new HtmlElement("input");
		input.addAttribute("id", "__GOPAGE__" + this.id);
		input.addAttribute("name", "__GOPAGE__" + this.id);
		input.addAttribute("type", "text");
		input.addAttribute("size", "4");//
		input.addAttribute("onKeyUp",
				"this.value=this.value.replace(/\\D/g,'')");
		input.addAttribute("value", this.pager.getCurrentPage() + "");
		cell3.setValue("第" + input.toHtml() + "页");
		HtmlElement b = new HtmlElement("button");
		b.addAttribute("onclick", "pageOperate" + this.id + "('4')");
		b.setValue("转到");
		cell3.addChild(b);
		return cell3;
	}

	/**
	 * 分页栏信息列4
	 * 
	 * @return
	 */
	private HtmlElement getPageInfo4() {
		HtmlElement cell4 = new HtmlElement("td");
		// 当前页隐藏域
		HtmlElement input1 = new HtmlElement("input");
		input1.addAttribute("id", "__CPAGE__" + this.id);
		input1.addAttribute("name", "__CPAGE__" + this.id);
		input1.addAttribute("type", "hidden");
		input1.addAttribute("value", this.pager.getCurrentPage() + "");
		// 操作隐藏域
		HtmlElement input2 = new HtmlElement("input");
		input2.addAttribute("id", "__OPERATE__" + this.id);
		input2.addAttribute("name", "__OPERATE__" + this.id);
		input2.addAttribute("type", "hidden");
		// 当前最大显示数据行
		HtmlElement input3 = new HtmlElement("input");
		input3.addAttribute("id", "__MAXROWSIZE__" + this.id);
		input3.addAttribute("name", "__MAXROWSIZE__" + this.id);
		input3.addAttribute("value", (this.rowsize == -1 ? this.pager
				.getPageSize() : this.rowsize)
				+ "");
		input3.addAttribute("type", "hidden");
		cell4.addChild(input1);
		cell4.addChild(input2);
		cell4.addChild(input3);
		return cell4;
	}

	/**
	 * 设置分页文本的翻页信息,如 : 首页 上一页 下一页 末页 new String[]{"首页","上一页","下一页", "末页"}
	 * 注意默认为:new String[]{"首页","上一页","下一页", "末页"}
	 * 
	 * @param pageText
	 */
	public void setPageText(String[] pageText) {
		this.pageText = pageText;
	}
}
