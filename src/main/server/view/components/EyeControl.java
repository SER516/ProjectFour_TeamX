package main.server.view.components;

import main.model.MessageContolBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Wrapper for user eye control
 */
public class EyeControl extends JPanel {

    private JComboBox<String> itemComboBox;
    private String currentItem;
    private final String btnActivateValue = "Activate";
    private final String btnStartValue = "Start";
    private final String btnStopValue = "Stop";
    private JButton btnSend;
    private JCheckBox chckbxAutoReset;
    private boolean isAutoReset = false;
    private boolean isStarted = false;
    private MessageContolBean messageContolBean;

    public EyeControl(String name, String[] items, MessageContolBean bean){

        messageContolBean = bean;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEADING,20,0));
        panel1.add(new JLabel(name));
        chckbxAutoReset = new JCheckBox("Auto Reset");
        chckbxAutoReset.addActionListener(new autoResetListener());
        btnSend = new JButton(btnActivateValue);
        btnSend.addActionListener(new buttonListener());
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
        itemComboBox = new JComboBox<>(items);
        itemComboBox.addActionListener(new comboboxListener());
        messageContolBean.setCurrentEyeItem(items[0]);
        panel2.add(itemComboBox);
        panel2.add(btnSend);
        panel2.add(chckbxAutoReset);

        add(panel1);
        add(panel2);

    }

    private class comboboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedItem = itemComboBox.getSelectedItem().toString();
            if(currentItem != null && selectedItem != currentItem){
                messageContolBean.setValue(currentItem, 0);
            }
            currentItem = selectedItem;
            messageContolBean.setValue(currentItem, 1);
            messageContolBean.setCurrentEyeItem(currentItem);
        }
    }

    private class autoResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox changedObj = (JCheckBox) e.getSource();
            isAutoReset = changedObj.isSelected();
            if(isAutoReset){
                btnSend.setText(btnStartValue);
            } else {
                btnSend.setText(btnActivateValue);
            }

            messageContolBean.setEyeAutoReset(isAutoReset);
        }
    }

    private class buttonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(isAutoReset && !isStarted){
                isStarted = true;
                btnSend.setText(btnStopValue);
                chckbxAutoReset.setEnabled(false);
                messageContolBean.setEyeActivated(isStarted);
            } else if(isAutoReset && isStarted){
                isStarted = false;
                btnSend.setText(btnStartValue);
                chckbxAutoReset.setEnabled(true);
                messageContolBean.setEyeActivated(isStarted);
            } else {
                messageContolBean.setEyeActivated(true);
            }
        }
    }


}
