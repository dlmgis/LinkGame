package cn.fouad.listener;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

import cn.fouad.UI.GamePanel;
import cn.fouad.UI.MainFrame;
import cn.fouad.commons.GameConfiguration;
import cn.fouad.service.impl.GameServiceImpl;
import cn.fouad.timer.GameTimer;


/**
 * 对“开始”按钮的事件监听
 */
public class BeginListener extends MouseInputAdapter {
    private GamePanel gamePanel;
    private GameServiceImpl gameService;
    private JLabel pointLabel;
    private JLabel timeLabel;
    private GameConfiguration config;
    private Timer timer;
    private MainFrame mainFrame;

    public BeginListener(MainFrame mainFrame, GamePanel gamePanel,
                         GameServiceImpl gameService, JLabel pointLabel, JLabel timeLabel,
                         GameConfiguration config) {
        this.gamePanel = gamePanel;
        this.gameService = gameService;
        this.pointLabel = pointLabel;
        this.timeLabel = timeLabel;
        this.config = config;
        this.mainFrame = mainFrame;
    }

    Timer getTimer() {
        return this.timer;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mainFrame.removeBackGroundPanel();
        mainFrame.add(gamePanel, BorderLayout.CENTER);
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.timer = new Timer();
        gamePanel.setSelectPiece(null);
        gamePanel.setOverImage(null);
        pointLabel.setText("0");
        timeLabel.setText(String.valueOf(config.getLimitedTime()));
        gameService.start();
        GameTimer task = new GameTimer(this.gamePanel, this.config.getLimitedTime(),
                this.timeLabel);
        timer.schedule(task, 0, 1000);
        gamePanel.repaint();
    }
}
