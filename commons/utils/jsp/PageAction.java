package commons.utils.jsp;

 

import com.opensymphony.xwork2.ActionSupport;

public class PageAction extends ActionSupport {

	private String cpage;
	private String total;
	private String url;

	public String getCpage() {
		return cpage;
	}

	public void setCpage(String cpage) {
		this.cpage = cpage;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
