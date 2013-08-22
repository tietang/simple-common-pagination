package commons.tree;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import commons.utils.string.CharTools;

public class TreeUtils {
	public static String getTrees(HttpServletRequest request, List<TreeNode> data) {
		String tree = "";
		// Thread.sleep(1000);
		String id = request.getParameter("benma_tree_id");
		String level1 = request.getParameter("benma_tree_level");
		String last = request.getParameter("benma_tree_isLast");
		String fun = request.getParameter("benma_tree_fun");
		String check = request.getParameter("benma_tree_check");
		String chbxName = request.getParameter("benma_tree_chbxName");
		String event = request.getParameter("benma_tree_event");
		String imgPath = request.getContextPath() + request.getParameter("benma_tree_imgPath");
		if (level1 == null) {
			return "";
		}
		int level = Integer.parseInt(level1);
		level++;
		String imgClick = fun + ".openTheNode(this,0)";
		String imgDbClick = fun + ".openTheNode(this,1)";
		String spanEvent = event + "=\"" + fun + ".openTheNode(this,0)\"";
		String spanClick = " onclick=\"" + fun + ".action(this)\"";
		String onmouseover = fun + ".mouseover(this)";
		String onmouseout = fun + ".mouseout(this)";
		String onclick = fun + ".selected(this)";

		String iimg = "0".equals(last) ? "I.gif" : "I.gif";
		int size = data.size();
		if (size > 0) {
			int i1 = 0;
			String s01 = "<img src=\"" + imgPath + "/T.gif\"/>";
			String s02 = "<img src=\"" + imgPath + "/L.gif\"/>";
			String s1 = "";
			String s2 = "";
			String f01 = "<img onclick=\"" + imgClick + "\" ondblclick=\"" + imgDbClick + "\" class=\"beanma_plus t\"  src=\"" + imgPath + "/Tplus.gif\"/>";
			String f02 = "<img onclick=\"" + imgClick + "\" ondblclick=\"" + imgDbClick + "\" class=\"beanma_plus l\" src=\"" + imgPath + "/Lplus.gif\"/>";
			String f1 = "";
			String f2 = "";

			for (int j = 0; j < level; j++) {
				if (j == 0) {
					s1 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
					s2 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
					f1 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
					f2 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
				} else if (j == (level - 1)) {
					s1 += "<img src=\"" + imgPath + "/T.gif\"/>";
					s2 += "<img src=\"" + imgPath + "/L.gif\"/>";
					f1 += "<img onclick=\"" + imgClick + "\" ondblclick=\"" + imgDbClick + "\" class=\"beanma_plus t\" src=\"" + imgPath + "/Tplus.gif\"/>";
					f2 += "<img onclick=\"" + imgClick + "\" ondblclick=\"" + imgDbClick + "\" class=\"beanma_plus l\" src=\"" + imgPath + "/Lplus.gif\"/>";
				} else {
					s1 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
					s2 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
					f1 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
					f2 += "<img src=\"" + imgPath + "/" + iimg + "\"/>";
				}
			}
			tree += "<ul class=\"benma_tree\" style=\"display: none;\">";
			for (TreeNode node : data) {
				if (node.isLeaf()) {
					tree += "<li > ";
					tree += "<div  onclick=\"" + onclick + "\"    onmouseover=\"" + onmouseover + "\" onmouseout=\"" + onmouseout + "\"   level=\"" + level
							+ "\"  rel=\"" + node.getId() + "\">";
					if (level == 1) {
						if (i1 == (size - 1)) tree += s02;
						else tree += s01;
					} else {
						if (i1 == (size - 1)) tree += s2;
						else tree += s1;
					}
					tree += "<span " + spanClick + " class=\"file\">" + node.getName() + "</span></div></li>";
					i1++;
				} else {
					tree += "<li class=\"directory collapsed\" seq=\"" + last + "\"   level=\"" + level + "\" rel=\"" + node.getId() + "\" >";
					tree += "<div onclick=\"" + onclick + "\"  onmouseover=\"" + onmouseover + "\" onmouseout=\"" + onmouseout + "\"  level=\"" + level
							+ "\" rel=\"" + node.getId() + "\">";
					if (level == 1) {
						if (i1 == (size - 1)) tree += f02;
						else tree += f01;
					} else {
						if (i1 == (size - 1)) tree += f2;
						else tree += f1;
					}
					tree += "<span  " + spanEvent + spanClick + " class=\"folder\">" + node.getName() + "</span></div></li>";
				}

			}
			tree += "</ul>";
		}
		return tree;
	}
}
