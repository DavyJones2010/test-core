package edu.xmu.test.tddl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MainGUI {

	static Map<String, List<String>> template = Maps.newHashMap();
	static {
		template.put("idx_open_account_mobile", Lists.newArrayList("index_value", "domain_id"));
		template.put("idx_open_account_email", Lists.newArrayList("index_value", "domain_id"));
		template.put("idx_open_account_isv_account_id", Lists.newArrayList("index_value", "domain_id"));
		template.put("idx_open_account_login_id", Lists.newArrayList("index_value", "domain_id"));
		template.put("idx_open_account_open_id", Lists.newArrayList("index_value", "domain_id"));
		template.put("open_account_link", Lists.newArrayList("open_account_id", "domain_id"));
		template.put("idx_open_account_link_outerid", Lists.newArrayList("outer_id", "outer_platform", "domain_id"));
	}

	public static void main(String[] args) {
		NewFrame frame1 = new NewFrame();
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 一定要设置关闭
		frame1.setVisible(true);
	}

	static class NewFrame extends JFrame {
		private JButton button1;
		private JComboBox<String> comboBox;
		private List<JTextField> inputFields;
		private JTextArea area;

		public NewFrame() {
			super();
			this.getContentPane().setLayout(null);// 设置布局控制器

			area = getArea();
			this.add(area, null);// 添加文本框
			this.add(this.getButton(), null);// 添加按钮
			List<JLabel> labels = getLabels();
			for (JLabel jLabel : labels) {
				this.add(jLabel, null);// 添加标签
			}

			inputFields = getInputFields();
			for (JTextField jTextField : inputFields) {
				this.add(jTextField, null);// 添加文本框
			}
			this.add(this.getBox(), null);// 添加下拉列表框
			this.setTitle("Generate SQL!");// 设置窗口标题
			this.setBounds(100, 100, 570, 410);
		}

		/**
		 * 设置下拉列表框
		 * 
		 * @return
		 */
		private JComboBox<String> getBox() {
			if (comboBox == null) {
				comboBox = new JComboBox<String>();
				comboBox.setBounds(100, 49, 300, 27);
				for (Entry<String, List<String>> entry : template.entrySet()) {
					// 添加所有表名
					String tableName = entry.getKey();
					comboBox.addItem(tableName);
				}
				comboBox.addActionListener(new ComboxListener());// 为下拉列表框添加监听器类
			}
			return comboBox;
		}

		private class ComboxListener implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> o = (JComboBox<String>) e.getSource();
				Object selectedItem = o.getSelectedItem();
				System.out.println(selectedItem);
			}
		}

		/**
		 * 设置标签
		 * 
		 * @return 设置好的标签
		 */
		private List<JLabel> getLabels() {
			List<JLabel> labels = Lists.newArrayList();
			JLabel label = new JLabel();
			label.setBounds(10, 49 + 40 * 0, 150, 18);
			label.setText("TableName:");
			label.setToolTipText("JLabel");
			labels.add(label);

			label = new JLabel();
			label.setBounds(10, 49 + 40 * 1, 150, 18);
			label.setText("index_value:");
			label.setToolTipText("JLabel");
			labels.add(label);

			label = new JLabel();
			label.setBounds(10, 49 + 40 * 2, 150, 18);
			label.setText("domain_id:");
			label.setToolTipText("JLabel");
			labels.add(label);

			// label = new JLabel();
			// label.setBounds(10, 49 + 40 * 2, 150, 18);
			// label.setText("domain_id:");
			// label.setToolTipText("JLabel");
			// labels.add(label);

			return labels;
		}

		private List<JTextField> getInputFields() {
			List<JTextField> list = Lists.newArrayList();
			// JTextField textField = new JTextField();
			// textField.setBounds(100, 49 + 40 * 0, 150, 18);
			// list.add(textField);
			JTextField textField = new JTextField();
			textField.setBounds(100, 49 + 40 * 1, 150, 28);
			textField.setEditable(true);
			textField.setEnabled(true);
			list.add(textField);

			textField = new JTextField();
			textField.setBounds(100, 49 + 40 * 2, 150, 28);
			textField.setEditable(true);
			list.add(textField);
			return list;
		}

		/**
		 * 设置按钮
		 * 
		 * @return 设置好的按钮
		 */
		private JButton getButton() {
			if (button1 == null) {
				button1 = new JButton();
				button1.setBounds(403, 49, 171, 27);
				button1.setText("Generate SQL");
				button1.setToolTipText("OK");
				button1.addActionListener(new GenerateSQLListener());// 添加监听器类，其主要的响应都由监听器类的方法实现
			}
			return button1;
		}

		/**
		 * 监听器类实现ActionListener接口，主要实现actionPerformed方法
		 * 
		 */
		private class GenerateSQLListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String tablePrefix = (String) comboBox.getSelectedItem();
				List<String> columnNames = template.get(tablePrefix);
				List<String> columnValues = Lists.newArrayList();
				for (JTextField jTextField : inputFields) {
					columnValues.add(jTextField.getText());
				}
				String sql = RouterSample.generateSQL("open_account", tablePrefix, columnNames, columnValues);
				area.setText(sql);
			}
		}

		private JTextArea getArea() {
			JTextArea area = new JTextArea();
			area.setBounds(10, 49 + 40 * 3, 550, 200);
			return area;
		}
	}
}
