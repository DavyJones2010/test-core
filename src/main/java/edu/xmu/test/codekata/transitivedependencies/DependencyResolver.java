package edu.xmu.test.codekata.transitivedependencies;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * {@link <a href="http://codekata.com/kata/kata18-transitive-dependencies/">Kata18 Transitive Dependencies</a>}
 *
 */
public class DependencyResolver {
	public void resolve(File fileToBeResolved) {
	}

	public void doResolve(Multimap<String, String> inputDependency) {
		Map<String, TreeNode> nodeMap = resolveTreeMap(inputDependency);
		Multimap<String, String> outputDependency = transformToMultimap(nodeMap);
		System.out.println(outputDependency);
	}

	Multimap<String, String> transformToMultimap(Map<String, TreeNode> nodeMap) {
		Multimap<String, String> outputDependency = ArrayListMultimap.create();
		for (Entry<String, TreeNode> nodeEntry : nodeMap.entrySet()) {
			String key = nodeEntry.getKey();
			for (TreeNode childNode : nodeEntry.getValue().listChilds()) {
				outputDependency.put(key, childNode.name);
			}
		}
		return outputDependency;
	}

	Map<String, TreeNode> resolveTreeMap(Multimap<String, String> inputDependency) {
		Map<String, TreeNode> nodeMap = Maps.newHashMap();
		for (String key : inputDependency.keys()) {
			TreeNode currNode = null;
			if (nodeMap.containsKey(key)) {
				currNode = nodeMap.get(key);
			} else {
				currNode = new TreeNode(key);
				nodeMap.put(key, currNode);
			}
			for (String val : inputDependency.get(key)) {
				TreeNode childNode = null;
				if (!nodeMap.containsKey(val)) {
					childNode = new TreeNode(val);
					nodeMap.put(val, childNode);
				} else {
					childNode = nodeMap.get(val);
				}
				currNode.addChild(childNode);
			}
		}
		return nodeMap;
	}

	private static final class TreeNode {
		Set<TreeNode> childs = Sets.newHashSet();
		String name;

		public void addChild(TreeNode childNode) {
			childs.add(childNode);
		}

		public Collection<TreeNode> listChilds() {
			Set<TreeNode> childs = Sets.newHashSet(this.childs);
			Preconditions.checkArgument(!childs.contains(this), "Circular reference found!");
			for (TreeNode child : childs) {
				Collection<TreeNode> subChild = child.listChilds();
				Preconditions.checkArgument(!subChild.contains(this), "Circular reference found!");
				childs.addAll(subChild);
			}
			return childs;
		}

		public TreeNode(String name) {
			super();
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TreeNode other = (TreeNode) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "TreeNode [name=" + name + "]";
		}

	}
}
