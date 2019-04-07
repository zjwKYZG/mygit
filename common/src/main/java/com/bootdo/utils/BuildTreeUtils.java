package com.bootdo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.bootdo.domain.Tree;

/**
 * 构建树格式数据工具类
 * 
 * @author created by zjw on 2019年1月22日 上午11:03:45
 */
public class BuildTreeUtils {

	public static <T> Tree<T> build(List<Tree<T>> nodes) {

		if (CollectionUtils.isEmpty(nodes)) {
			return null;
		}

		List<Tree<T>> topNodes = new ArrayList<>();
		for (Tree<T> children : nodes) {
			String parentId = children.getParentId();
			if (StringUtils.isBlank(parentId) || Objects.equals("0", parentId)) {
				topNodes.add(children);
				continue;
			}

			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (StringUtils.isNotBlank(id) && Objects.equals(id, parentId)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setHasChildren(true);
					continue;
				}
			}
		}

		Tree<T> root = new Tree<T>();
		if (CollectionUtils.isNotEmpty(topNodes) && topNodes.size() == 1) {
			root = topNodes.get(0);
		} else {
			root.setId("-1");
			root.setParentId("");
			root.setHasParent(false);
			root.setHasChildren(true);
			root.setChecked(true);
			root.setChildren(topNodes);
			root.setText("顶级节点");
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			root.setState(state);
		}
		return root;
	}

	public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {

		if (CollectionUtils.isEmpty(nodes)) {
			return null;
		}

		List<Tree<T>> topNodes = new ArrayList<Tree<T>>();
		for (Tree<T> children : nodes) {
			String parentId = children.getParentId();
			if (StringUtils.isBlank(parentId) || Objects.equals(parentId, idParam)) {
				topNodes.add(children);
				continue;
			}

			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (StringUtils.isNotBlank(id) && Objects.equals(id, parentId)) {
					parent.getChildren().add(children);
					children.setHasParent(true);
					parent.setHasChildren(true);
					continue;
				}
			}
		}
		return topNodes;
	}
}