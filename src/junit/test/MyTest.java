package junit.test;

import java.io.File;

import org.junit.Test;

import cn.fouad.commons.Point;
import cn.fouad.utils.ImageUtils;

public class MyTest {

	@Test
	public void testImageUtils(){
		try {
			//System.out.println(ImageUtils.getImages(new File(ImageUtils.IMAGE_FOLDER_NAME+File.separator+ImageUtils.PIECE_IMAGE_FOLDER_NAME), ImageUtils.IMAGE_TYPE).size());
			//System.out.println(ImageUtils.getBackgroundImageIcon().getIconHeight());
			ImageUtils.randomImages(ImageUtils.getImages(new File(ImageUtils.IMAGE_FOLDER_NAME +File.separator+ImageUtils.PIECE_IMAGE_FOLDER_NAME), ImageUtils.IMAGE_TYPE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPoint(){
		Point p1=new Point(3,3);
		Point p2=new Point(2,2);
		System.out.println(p1.inSameYLine(p2));
		System.out.println(p1.isRightDown(p2));
		System.out.println(p1.isRightUp(p2));
		System.out.println(p1.isLeftDown(p2));
	}
	
			
}
