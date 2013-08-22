package commons.page;

import java.math.*;

/**
 * 分页操作类,该类来自于互联网,做了部分修改,以适应需求
 * 
 * @author life
 * 
 */
public class Pager {
	private int totalRows; // 总行数
	private int pageSize = 13; // 每页显示的行数
	private int currentPage; // 当前页号
	private int totalPages; // 总页数
	private int startRow; // 当前页在数据库中的起始行

	public Pager() {
		currentPage = 1;
		startRow = 0;
	}

	private void process() {
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		if (this.currentPage <= 0) {
			currentPage = 1;
		}
		startRow = (currentPage - 1) * pageSize;

	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		this.process();
	}

	// public void setStartRow(int startRow) {
	// this.startRow = startRow;
	// }
	// public void setTotalPages(int totalPages) {
	// this.totalPages = totalPages;
	// }
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		this.process();
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.process();
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void first() {
		currentPage = 1;
		startRow = 0;
	}

	public void previous() {
		if (currentPage == 1) {
			return;
		}
		currentPage--;
		startRow = (currentPage - 1) * pageSize;
	}

	public void next() {
		// System.out.print("next:");
		if (currentPage < totalPages) {
			currentPage++;
		}
		startRow = (currentPage - 1) * pageSize;
	}

	public void last() {
		currentPage = totalPages;
		startRow = (currentPage - 1) * pageSize;
	}

	public void refresh(int _currentPage) {
		currentPage = _currentPage;
		if (currentPage > totalPages) {
			last();
		}
	}
	// 测试
	// public static void main(String[] args) {
	// int currentPage = 1;
	// String pagerMethod;
	// int totalRows = 23;
	// int pageSize = 13;
	// // 定义pager对象，用于传到页面
	// Pager pager = new Pager();
	// pager.setTotalRows(totalRows);
	// pager.setPageSize(pageSize);
	// // 如果当前页号为空，表示为首次查询该页
	// // 如果不为空，则刷新pager对象，输入当前页号等信息
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.first();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.previous();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.next();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.last();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.first();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.next();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.next();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.next();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.next();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.previous();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.previous();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.previous();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// pager.previous();
	// System.out.println("getTotalRows:" + pager.getTotalRows() + "
	// CurrentPage:" +
	// pager.getCurrentPage() + " StartRow :" + pager.getStartRow() + "
	// TotalPages :
	// "
	// + pager.getTotalPages());
	// }
}
