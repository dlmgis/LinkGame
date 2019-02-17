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
 * 游戏初始化工作
 */
public class BeginListener extends MouseInputAdapter {
    private GamePanel gamePanel;
    private GameServiceImpl gameService;
    private JLabel pointLabel;
    private JLabel timeLabel;
    private GameConfiguration config;
    private Timer timer;
    private MainFrame mainFrame;

    /**
     * 游戏初始化
     * @param mainFrame 监听的窗口
     * @param gamePanel 游戏面板
     * @param gameService 游戏逻辑
     * @param pointLabel 面板
     * @param timeLabel 时间表
     * @param config 配置文件
     */
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

    /**
     * 按下 “开始” 按钮将面板切换到游戏面板，对游戏做初始化操作
     * @param e 鼠标事件
     */
    @Override
    public void mousePressed(MouseEvent e) {
        mainFrame.removeBackGroundPanel();
        mainFrame.add(gamePanel, BorderLayout.CENTER);
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.timer = new Timer();
        // 默认未选择任何图片
        gamePanel.setSelectPiece(null);
        // 游戏未结束，不显示结束图片
        gamePanel.setOverImage(null);
        // 得分为 0 分
        pointLabel.setText("0");
        // 从配置中读取剩余时间
        timeLabel.setText(String.valueOf(config.getLimitedTime()));
        gameService.start();
        // 计时开始
        GameTimer task = new GameTimer(this.gamePanel, this.config.getLimitedTime(),
                this.timeLabel);
        timer.schedule(task, 0, 1000);
        gamePanel.repaint();
    }
}
