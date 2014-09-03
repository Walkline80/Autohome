package com.walkline.app;

import net.rim.device.api.io.transport.TransportInfo;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class AutohomeAppConfig
{
	public static final String APP_TITLE = "Zhihu Daily for you";
	public static final String UNDERLINE = "\u0332";
	public static final String BBW_APPID = "58645271";

	public static final String queryTopicListRequestURL = "http://app.api.autohome.com.cn/autov4.1.5/club/jingxuantopic-a2-pm2-v4.1.1-c104-p$PAGECOUNT$-s20.html";
	public static final String queryExperienceDetailsRequestURL = "http://plugin.smzdm.com/plugin/api/mobile/v5/c_index.php?f=android&display_mode=2&fav=0&mod=get_experience_info&postID=$POSTID$&delay=1&filter=1&key=&clsid=0&mode=0&top=0";
	//public static String querySectionsRequestURL = "http://news-at.zhihu.com/api/3/sections";
	//public static String querySectionDetailsRequestURL = "http://news-at.zhihu.com/api/3/section/";  //后边填id
	//public static String queryLastStoriesRequestURL = "http://news-at.zhihu.com/api/3/stories/latest";
	//public static String queryBeforeStoriesRequestURL = "http://news-at.zhihu.com/api/3/stories/before/";
	//public static String queryStoryDetailsRequestURL = "http://news-at.zhihu.com/api/3/story/";
	//public static String queryThemesRequestURL = "http://news-at.zhihu.com/api/3/themes";
	//public static String queryThemesDetailsRequestURL = "http://news-at.zhihu.com/api/3/theme/";

	public static final Background bgColor_Gradient=BackgroundFactory.createLinearGradientBackground(Color.GRAY, Color.GRAY, Color.BLACK, Color.BLACK);

	public static final Border border_popup_Transparent=BorderFactory.createRoundedBorder(new XYEdges(16,16,16,16), Color.BLACK, 200, Border.STYLE_FILLED);
	public static final Background bg_popup_Transparent=BackgroundFactory.createSolidTransparentBackground(Color.BLACK, 200);

	public static int[] preferredTransportTypes = {
													TransportInfo.TRANSPORT_TCP_WIFI,
													TransportInfo.TRANSPORT_BIS_B,
													TransportInfo.TRANSPORT_TCP_CELLULAR,
													TransportInfo.TRANSPORT_WAP2
												  };
	public static int[] disallowedTransportTypes = {
													TransportInfo.TRANSPORT_MDS,
													TransportInfo.TRANSPORT_WAP
												   };

	public static final Font FONT_LIST_TITLE = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt), Ui.UNITS_pt);
	public static final Font FONT_STORY_TITLE = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt), Ui.UNITS_pt);
	public static final Font FONT_MAIN_TITLE = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt) - 1, Ui.UNITS_pt);
	public static final Font FONT_STORY_DETAIL_BITMAP_TITLE = Font.getDefault().derive(Font.BOLD, Font.getDefault().getHeight(Ui.UNITS_pt) + 3, Ui.UNITS_pt);
	//public static final Font FONT_STORY_DETAIL_QUESTION_TITLE = Font.getDefault().derive(Font.BOLD, Font.getDefault().getHeight(Ui.UNITS_pt) + 2, Ui.UNITS_pt);
	public static final Font FONT_STORY_DETAIL_IMAGE_SOURCE = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt) -2, Ui.UNITS_pt);

	public static final Font FONT_SECTION_TITLE = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt), Ui.UNITS_pt);
	public static final Font FONT_SECTION_DESCRIPTION = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt) - 2, Ui.UNITS_pt);

	public static final Font FONT_PANEVIEW_TITLE = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt) + 3, Ui.UNITS_pt);

	public static final Font FONT_ABOUT_TITLE = Font.getDefault().derive(Font.BOLD, Font.getDefault().getHeight(Ui.UNITS_pt)+2, Ui.UNITS_pt);
	public static final Font FONT_ABOUT_HEADLINE = Font.getDefault().derive(Font.BOLD | Font.ITALIC, Font.getDefault().getHeight(Ui.UNITS_pt), Ui.UNITS_pt);
	public static final Font FONT_ABOUT_SMALL = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt)-1, Ui.UNITS_pt);
	public static final Font FONT_ABOUT_LARGE = Font.getDefault().derive(Font.PLAIN, Font.getDefault().getHeight(Ui.UNITS_pt)+1, Ui.UNITS_pt);

	//SKU: 0x823252ddc046c845L (zhihu_daily_for_you_written_by_walkline_wang)
}