package com.lhh.image;

import java.awt.image.BufferedImage;

/**
 * 图片工具类
 * @author hwaggLee
 *
 */
public class LhhUtilsImage {

	/**
	 * 将身份证背景变成黑白照
	 * @param bi
	 * @return
	 */
	public static BufferedImage replaceICADWithWhiteColor(BufferedImage bi) {
		int[] rgb = new int[3];
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		/**
		 * 
		 * 遍历图片的像素，为处理图片上的杂色，所以要把指定像素上的颜色换成目标白色 用二层循环遍历长和宽上的每个像素
		 * 
		 */
		int hitCount = 0;
		int maxJGVlaue= 15;
		for (int i = minx; i < width - 1; i++) {
			for (int j = miny; j < height; j++) {

				
				/**
				 * 
				 * 得到指定像素（i,j)上的RGB值，
				 * 
				 */
				int pixel = bi.getRGB(i, j);
				int pixelNext = bi.getRGB(i + 1, j);
				/**
				 * 
				 * 分别进行位操作得到 r g b上的值
				 * 
				 */
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				
				
				if( j > 144 && j<153 ){
					/*if( i > 92 &&  i < 237){
						
					}*/
					//i的18个数字
					//if( isIDNumSpace(i, 92) ){
					//if ((Math.abs(rgb[0] - rgb[1]) < maxJGVlaue)) {
					if( i > 92 &&  i < 237){
						System.out.println(i+":"+j+";=======>"+rgb[0]+","+rgb[1]+","+rgb[2]);
						continue;
					}
				}
				
				/**
				 * 
				 * 进行换色操作，我这里是要换成白底，那么就判断图片中rgb值是否在范围内的像素
				 * 
				 */
				// 经过不断尝试，RGB数值相互间相差15以内的都基本上是灰色，
				// 对以身份证来说特别是介于73到78之间，还有大于100的部分RGB值都是干扰色，将它们一次性转变成白色
				//if ((Math.abs(rgb[0] - rgb[1]) < maxJGVlaue) && (Math.abs(rgb[0] - rgb[2]) < maxJGVlaue) && (Math.abs(rgb[1] - rgb[2]) < maxJGVlaue) && (((rgb[0] > 73) && (rgb[0] < 78)) || (rgb[0] > 100))) {
					// 进行换色操作,0xffffff是白色
					bi.setRGB(i, j, 0xffffff);
				//}
			}
		}
		return bi;
	}
	
	 /**
	  * 过滤身份证间隔之间的空白，每个间隔之间多少个空白符
	  * @param i
	  * @param number:起点位置
	  * @return
	  */
	private static boolean isIDNumSpace(int i ,int number){
		if( i > number && i < number+6 ){//1
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//2
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//3
			return true;
		}
		number +=8;
		if( i > number && i <  number+7  ){//4
			return true;
		}
		number +=9;
		if( i > number && i <  number+7  ){//5
			return true;
		}
		number +=7;
		if( i > number && i <  number+6  ){//6
			return true;
		}
		number +=9;
		if( i > number && i <  number+6  ){//7
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//8
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//9
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//10
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//11
			return true;
		}
		number +=9;
		if( i > number && i <  number+7  ){//12
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//13
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//14
			return true;
		}
		number +=9;
		if( i > number && i <  number+6  ){//15
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//16
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//17
			return true;
		}
		number +=8;
		if( i > number && i <  number+6  ){//18
			return true;
		}
		return false;
	}
}
