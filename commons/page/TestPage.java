package commons.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import commons.page.model.DataModel;
import commons.page.model.ListDataModel;

public class TestPage {
	/**
	 * 内存分页测试
	 * 
	 * @param request
	 * @return
	 */
	public static String se(HttpServletRequest request) {
		int len = 125;
		String[][] data = new String[len][6];
		for (int i = 0; i < data.length; i++) {
			data[i] = new String[] { "String列1-" + i, "String咧2-" + i, "String烈3-" + i, "String烈4-" + i, "String烈5-" + i, "String烈6-" + i };
		}
		List<Object[]> d2 = new ArrayList<Object[]>();
		for (int i = 0; i < len; i++) {
			Object[] o = new Object[] { "list列1-" + i, "list咧2-" + i, "list烈3-" + i, "list烈4-" + i, "list烈5-" + i, "list烈6-" + i };
			d2.add(o);
		}
		HtmlTable t = new HtmlTable();
		t.addAttribute("width", "100%");
		t.addAttribute("border", "1");
		// t.setData(data);//数组测试
		t.setData(d2);// List测试
		t.setHeader("标题1,标题3,标题4,标题6,标题5");
		t.setCellCallBack(new CellCallBack() {
			public HtmlRow doCell(Object data) {
				// 数组测试
				// String[] s = (String[] ) data;
				// HtmlRow row = TableUtil.createRow(new Object[] {
				// s[0],s[2],s[3],s[5],s[4]});

				// List测试
				Object[] s = (Object[]) data;
				HtmlRow row = TableUtil.createRow(new Object[] { s[0], s[2], s[3], s[5], s[4] });

				return row;
			}
		});

		PageDataTable p = new PageDataTable(request, 0);
		p.setPageSize(12);
		p.setTotalRows(len);
		p.setTable(t);
		return p.render();
	}

	/**
	 * 查询分页测试
	 * 
	 * @param request
	 * @return
	 */
	public static String se2(HttpServletRequest request) {
		final int len = 125;
		HtmlTable t = new HtmlTable();
		//t.setHeader("标题1,标题3,标题4,标题6,标题5");
		PageDataTable p = new PageDataTable(request, "__my_2098_", 1);
		p.setPageSize(9);
		p.setTotalRows(len);
		p.setPageDataCallBack(new PageDataCallBack() {
 
			public DataModel doPage(int startRow, int maxRow) {
				List<Object[]> d2 = new ArrayList<Object[]>();
				// 下面为测试所用,模拟数据库查询,实际使用时将startRow,maxRow两个变量传入SQL语句查询数据库获取相应的记录
				int endRow = (startRow + maxRow) > len ? len : (startRow + maxRow);
				for (int i = startRow; i < endRow; i++) {
					Object[] o = new Object[] { "list列1-" + i, "list咧2-" + i, "list烈3-" + i, "list烈4-" + i, "list烈5-" + i, "list烈6-" + i };
					d2.add(o);
				}
				DataModel dataModel = new ListDataModel();
				dataModel.setWrappedData(d2);
				return dataModel;
			}
		});
		p.setTable(t);
		return p.render();
	}
	public static String se3(HttpServletRequest request) {
		int len = 125;
		String[][] data = new String[len][6];
		for (int i = 0; i < data.length; i++) {
			data[i] = new String[] { "String列1-" + i, "String咧2-" + i, "String烈3-" + i, "String烈4-" + i, "String烈5-" + i, "String烈6-" + i };
		}
		List<Object[]> d2 = new ArrayList<Object[]>();
		for (int i = 0; i < len; i++) {
			Object[] o = new Object[] { "list列1-" + i, "list咧2-" + i, "list烈3-" + i, "list烈4-" + i, "list烈5-" + i, "list烈6-" + i };
			d2.add(o);
		}
		HtmlTable t = new HtmlTable();
		t.addAttribute("width", "100%");
		t.addAttribute("border", "1");
		// t.setData(data);//数组测试
		//t.setData(d2);// List测试
		t.setHeader("标题1,标题3,标题4,标题6,标题5");
		t.setCellCallBack(new CellCallBack() {
			public HtmlRow doCell(Object data) {
				// 数组测试
				// String[] s = (String[] ) data;
				// HtmlRow row = TableUtil.createRow(new Object[] {
				// s[0],s[2],s[3],s[5],s[4]});

				// List测试
				Object[] s = (Object[]) data;
				HtmlRow row = TableUtil.createRow(new Object[] { s[0], s[2], s[3], s[5], s[4] });

				return row;
			}
		});

		PageDataTable p = new PageDataTable(request,"1009", 0);
	//	p.setPageSize(12);
		//p.setTotalRows(len);
		p.setTable(t);
		return p.render();
	}

}
