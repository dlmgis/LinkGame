package cn.fouad.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputAdapter;

import cn.fouad.commons.GameConfiguration;
import cn.fouad.commons.Point;
import cn.fouad.listener.BeginListener;
import cn.fouad.listener.GameListener;
import cn.fouad.service.impl.GameServiceImpl;

/**
 * 主窗口
 */
public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel backgroundPanel = null;

    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "警告",
                    JOptionPane.WARNING_MESSAGE);
        }
        initialize();
        setVisible(true);
    }

    /**
     * 绘制背景图
     *
     * @param g 绘图
     */
    @Override
    public void printComponents(Graphics g) {
        try {
            g.drawImage(ImageIO.read(new File("images/bg_image.jpg")), 0, 0,
                    500, 500, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("serial")
    private JPanel createPanel(final ImageIcon imageIcon) {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(),
                        getHeight(), null);
            }

        };
    }

    private void initialize() {
        setTitle("连连看"); // 设置标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭时退出
        int DEFAULT_WIDTH = 830;
        int DEFAULT_HEIGHT = 600;
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); // 设置宽度和高度
        this.setLocationRelativeTo(null); // 居中
        setResizable(false); // 不可改变大小，不做大小自适应，写死
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG); // 设置样式
        setLayout(new BorderLayout(5, 0)); // 布局
        backgroundPanel = createPanel(new ImageIcon("images/bg_image.jpg")); // io操作读取图片
        add(backgroundPanel, BorderLayout.CENTER);
        // 配置
        final GameConfiguration config = new GameConfiguration(16, 10,
                new Point(50, 100), 10, 150); // 基础配置
        final GameServiceImpl gameService = new GameServiceImpl(config); // 核心业务逻辑
        // 构造游戏界面
        final GamePanel gamePanel = new GamePanel(gameService);
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(127, 174, 252));
        controlPanel.setBorder(new EtchedBorder());

        BoxLayout controlLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
        controlPanel.setLayout(controlLayout);
        add(controlPanel, BorderLayout.WEST);

        JPanel logoPanel = new JPanel();
        logoPanel.setBorder(new EtchedBorder());
        logoPanel.setBackground(new Color(127, 174, 252));
        Image logoImage = null;
        try {
            logoImage = ImageIO.read(new File("images/fouadLogo.gif"));
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoPanel.add(logoLabel);
        controlPanel.add(logoPanel);
        controlPanel.add(createBlankPanel());

        JPanel aLogoPanel = new JPanel();
        aLogoPanel.setBorder(new EtchedBorder());
        Image aLogoImage = null;
        try {
            aLogoImage = ImageIO.read(new File("images/logo.gif"));
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
        JLabel aLogoLable = new JLabel(new ImageIcon(aLogoImage));
        aLogoPanel.setBackground(new Color(127, 174, 252));
        aLogoPanel.add(aLogoLable);
        controlPanel.add(aLogoPanel);
        controlPanel.add(createBlankPanel());

        JPanel pointTextPanel = new JPanel();
        pointTextPanel.setBackground(new Color(169, 210, 254));
        pointTextPanel.setBorder(new EtchedBorder());
        JLabel pointTextLabel = new JLabel();
        pointTextLabel.setText("分数");
        pointTextPanel.add(pointTextLabel);
        controlPanel.add(pointTextPanel);

        JPanel pointPanel = new JPanel();
        pointPanel.setBorder(new EtchedBorder());
        pointPanel.setBackground(new Color(208, 223, 255));
        final JLabel pointLabel = new JLabel();
        pointLabel.setText("0");
        pointPanel.add(pointLabel);
        controlPanel.add(pointPanel);
        controlPanel.add(createBlankPanel());

        JPanel timeTextPanel = new JPanel();
        timeTextPanel.setBackground(new Color(169, 210, 254));
        timeTextPanel.setBorder(new EtchedBorder());
        JLabel timeTextLabel = new JLabel();
        timeTextLabel.setText("剩余时间");
        timeTextPanel.add(timeTextLabel);
        controlPanel.add(timeTextPanel);

        // 计时器
        JPanel timePanel = new JPanel();
        timePanel.setBorder(new EtchedBorder());
        timePanel.setBackground(new Color(208, 223, 255));
        JLabel timeLabel = new JLabel(); //
        timeLabel.setText("0   S");
        timePanel.add(timeLabel);
        controlPanel.add(timePanel);
        controlPanel.add(createBlankPanel());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setBackground(new Color(127, 174, 252));
        JButton beginButton = new JButton("开始");
        buttonsPanel.add(beginButton);
        JLabel blankLabel = new JLabel();
        blankLabel.setText("     ");
        buttonsPanel.add(blankLabel);

        BeginListener beginListener = new BeginListener(this, gamePanel,
                gameService, pointLabel, timeLabel, config);
        beginButton.addMouseListener(beginListener);
        GameListener gameListener = new GameListener(gameService, gamePanel,
                pointLabel, beginListener);
        gamePanel.addMouseListener(gameListener);

        JButton exitButton = new JButton("退出");
        exitButton.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        buttonsPanel.add(exitButton);
        controlPanel.add(buttonsPanel);
        controlPanel.add(createBlankPanel());
    }

    /**
     * 移除背景容器
     */
    public void removeBackGroundPanel() {
        this.remove(backgroundPanel);
    }

    private JPanel createBlankPanel() {
        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(new Color(127, 174, 252));
        JLabel blankLabel = new JLabel();
        blankLabel.setText("      ");
        blankPanel.add(blankLabel);
        return blankPanel;
    }

}
