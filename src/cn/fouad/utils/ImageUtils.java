package cn.fouad.utils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import cn.fouad.exception.GameException;

/**
 * 图片常用操作工具类
 * 各种读取图片
 */
public class ImageUtils {
    public static String IMAGE_TYPE = ".gif"; // 图片类型
    public static String IMAGE_FOLDER_NAME = "images"; // 图片文件夹
    public static String PIECE_IMAGE_FOLDER_NAME = "pieces"; // 元素块所在文件夹

    /**
     * 判断两幅图片是否相同
     * @param imageA 图片A
     * @param imageB 图片B
     * @return 两幅图片是否相同
     */
    public static boolean equals(BufferedImage imageA, BufferedImage imageB) {
        return imageA.equals(imageB);
    }

    /**
     * 获得指定路径下所有图片列表
     * @param folder 图片路径
     * @param suffix 图片后缀
     * @return 图片列表
     * @throws IOException
     */
    public static List<BufferedImage> getImages(File folder, String suffix)
            throws IOException {
        File[] items = folder.listFiles();
        List<BufferedImage> result = new ArrayList<>();
        assert items != null; // 避免空指针
        for (File item : items) {
            if (item.getName().endsWith(suffix))
                result.add(ImageIO.read(item));
        }
        return result;
    }


    /**
     * 将图片列表打乱
     * @param orgImages 图片列表
     * @return 图片列表
     */
    public static List<BufferedImage> randomImages(List<BufferedImage> orgImages) {
        List<BufferedImage> result = new ArrayList<>();
        List<Integer> randomIndex = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < orgImages.size(); i++) {
            Integer index = random.nextInt(orgImages.size());
            if (randomIndex.contains(index)) {
                i--;
            } else {
                randomIndex.add(index);
            }
        }
        for (Integer randomIndex1 : randomIndex) {
            result.add(orgImages.get(randomIndex1));
        }
        return result;
    }


    /**
     * 从图片列表中取出指定数量的图片
     * @param orgImages 图片列表
     * @param size 数量
     * @return 图片列表
     */
    private static List<BufferedImage> getRandomImages(
            List<BufferedImage> orgImages, int size) {
        List<BufferedImage> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            result.add(orgImages.get(random.nextInt(orgImages.size())));
        }
        return result;
    }


    /**
     * 读取指定数量的图片
     * @param size 数量
     * @return 图片列表
     * @throws GameException 自定义异常 图片数量异常
     */
    public static List<BufferedImage> getPieceImages(int size)
            throws GameException {
        List<BufferedImage> result = new ArrayList<>();
        if (size % 2 != 0) {
            throw new GameException("图片数量异常 不是偶数" + size);
        }
        List<BufferedImage> images;
        try {
            images = getImages(new File(IMAGE_FOLDER_NAME + File.separator
                    + PIECE_IMAGE_FOLDER_NAME), IMAGE_TYPE);
            images = getRandomImages(images, size / 2);
            result.addAll(images);
            result.addAll(randomImages(images));
        } catch (IOException e) {
            throw new GameException("read images error:" + e.getMessage());
        }
        return result;
    }

    /**
     * @param pathname 路径
     * @return 图片
     * @throws IOException IO异常
     */
    public static BufferedImage getImage(String pathname) throws IOException {
        return ImageIO.read(new File(pathname));
    }

    public static ImageIcon getBackgroundImageIcon() throws GameException {
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    IMAGE_FOLDER_NAME + File.separator + "background_image"
                            + IMAGE_TYPE));
        } catch (Exception e) {
            throw new GameException("加载图片资源错误!");
        }
        return imageIcon;
    }

}
