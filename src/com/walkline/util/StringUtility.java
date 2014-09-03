package com.walkline.util;

public class StringUtility
{
	public static String[] split(String strString, String strDelimiter)
	{
		int iOccurrences = 0;
		int iIndexOfInnerString = 0;
		int iIndexOfDelimiter = 0;
		int iCounter = 0;

		if (strString == null) {throw new NullPointerException("Input string cannot be null.");}
		if (strDelimiter.length() <= 0 || strDelimiter == null) {throw new NullPointerException("Delimeter cannot be null or empty.");}

		//if (strString.startsWith(strDelimiter)) {strString = strString.substring(strDelimiter.length());}
		if (!strString.endsWith(strDelimiter)) {strString += strDelimiter;}

		while((iIndexOfDelimiter= strString.indexOf(strDelimiter,iIndexOfInnerString))!=-1)
		{
			iOccurrences += 1;
			iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();
		}

		String[] strArray = new String[iOccurrences];
		iIndexOfInnerString = 0;
		iIndexOfDelimiter = 0;

		while((iIndexOfDelimiter= strString.indexOf(strDelimiter,iIndexOfInnerString))!=-1)
		{
			strArray[iCounter] = strString.substring(iIndexOfInnerString, iIndexOfDelimiter);
			iIndexOfInnerString = iIndexOfDelimiter + strDelimiter.length();

			iCounter += 1;
		}

		return strArray;
	}

	public static String replace(String source, String pattern, String replacement)
	{	
		if (source == null) {return "";}

		StringBuffer sb = new StringBuffer();
		int idx = -1;
		int patIdx = 0;

		idx = source.indexOf(pattern, patIdx);
		while(idx!=-1)
		{
			sb.append(source.substring(patIdx, idx));
			sb.append(replacement);
			patIdx = idx + pattern.length();
			sb.append(source.substring(patIdx));

			idx = source.indexOf(pattern, patIdx);
		}

		return (sb.length() == 0 ? source : sb.toString());
	}

	public static String replaceAll(String source, String pattern, String replacement)
	{    
	    if (source == null) {return "";}

	    StringBuffer sb = new StringBuffer();
	    int idx = 0;
	    String workingSource = source;

	    while((idx=workingSource.indexOf(pattern, idx))!=-1)
	    {
	        sb.append(workingSource.substring(0, idx));
	        sb.append(replacement);
	        sb.append(workingSource.substring(idx + pattern.length()));

	        workingSource = sb.toString();
	        sb.delete(0, sb.length());
	        idx += replacement.length();
	    }

	    return workingSource;
	}
}