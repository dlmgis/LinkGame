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
 */
public class ImageUtils {
	public static String IMAGE_TYPE = ".gif"; // 图片类型
	public static String IMAGE_FOLDER_NAME = "images"; // 图片文件夹
	public static String PIECE_IMAGE_FOLDER_NAME = "pieces"; // 元素块所在文件夹

	public static boolean equals(BufferedImage imageA, BufferedImage imageB) {
		return imageA.equals(imageB);
	}

	public static List<BufferedImage> getImages(File folder, String suffix)
			throws IOException {
		File[] items = folder.listFiles();
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		for (File item : items) {
			if (item.getName().endsWith(suffix))
				result.add(ImageIO.read(item));
		}
		return result;
	}

	
	public static List<BufferedImage> randomImages(List<BufferedImage> orgImages) {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		List<Integer> randomIndex = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < orgImages.size(); i++) {
			Integer index = random.nextInt(orgImages.size());
			if (randomIndex.contains(index)) {
				i--;
				continue;
			} else {
				randomIndex.add(index);
			}
		}
		for (int i = 0; i < randomIndex.size(); i++) {
			result.add(orgImages.get(randomIndex.get(i)));
		}
		return result;
	}

	
	public static List<BufferedImage> getRandomImages(
			List<BufferedImage> orgImages, int size) {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			result.add(orgImages.get(random.nextInt(orgImages.size())));
		}
		return result;
	}

	
	public static List<BufferedImage> getPieceImages(int size)
			throws GameException {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		if (size % 2 != 0) {
			throw new GameException("大小异常，图片边长不是偶数" + size);
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
