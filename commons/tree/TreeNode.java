package commons.tree;

public class TreeNode {
	private String id;
	private String name;
	private boolean isLeaf;
	private boolean isHasChkbox=true;//服务段过滤是否显示复选框 

	public boolean isHasChkbox() {
		return isHasChkbox;
	}

	public void setHasChkbox(boolean isHasChkbox) {
		this.isHasChkbox = isHasChkbox;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
}
