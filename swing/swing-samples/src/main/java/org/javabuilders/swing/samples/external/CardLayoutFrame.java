package org.javabuilders.swing.samples.external;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings({"serial","unused"})
public class CardLayoutFrame extends JFrame {

	BuildResult result;
	private CardLayout cards;
	private JPanel progressPanel;
	private JTabbedPane tabs;
	
	public CardLayoutFrame() {
		result = SwingJavaBuilder.build(this);
	}
	
	private void urlSubmit() {}
	private void outputFolderSubmit() {}
	private void exitApplication() {}
	private void randomStructureClicked() {}
	private void randomDataClicked() {}
	private void networkDownloadsClicked() {}
	private void noPVRClicked(){}
	private void noUPAClicked() {}
	private void loadSubmit() {}
	private void saveSubmit() {}
	private void newSubmit() {}
	private void editSubmit() {}
	private void deleteSubmit() {}
	
	private void showProgress() {
		cards.show(this.getContentPane(), progressPanel.getName());
	}
	private void showTabs() {
		cards.show(this.getContentPane(), tabs.getName());
	}

}
