package com.walkline.util;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;

public class GPATools
{
    /**
     * Resizes a bitmap with an alpha channel (transparency) without the artifacts introduced
     *   by <code>scaleInto()</code>.
     *
     * @param bmpSrc        Source Bitmap
     * @param nWidth        New Width
     * @param nHeight       New Height
     * @param nFilterType   Filter quality to use. Can be <code>Bitmap.FILTER_LANCZOS</code>,
     *                           <code>Bitmap.FILTER_BILINEAR</code> or
     *                           <code>Bitmap.FILTER_BOX</code>.
     * @param nAspectRatio  Specifies how the picture is resized. Can be
     *                           <code>Bitmap.SCALE_TO_FIT</code>,
     *                           <code>Bitmap.SCALE_TO_FILL</code> or
     *                           <code>Bitmap.SCALE_STRETCH</code>.
     * @return              The resized Bitmap in a new object.
     */
    public static Bitmap ResizeTransparentBitmap(Bitmap bmpSrc, int nWidth, int nHeight, int nFilterType, int nAspectRatio)
    {
        if (bmpSrc == null) {return null;}

        int nOriginWidth = bmpSrc.getWidth();
        int nOriginHeight = bmpSrc.getHeight();

        if (nWidth == nOriginWidth && nHeight == nOriginHeight) {return bmpSrc;}

        Bitmap bmpOrigin = new Bitmap(nOriginWidth, nOriginHeight);
        Graphics graph = Graphics.create(bmpOrigin);

        int[] aEmptyLine = new int[nWidth];
        for (int x = 0; x < nWidth; x++) aEmptyLine[x] = 0x00000000;

        Bitmap[] bmpScaled = new Bitmap[2];
        for (int i = 0; i < 2; i++)
        {
            //Draw the bitmap on a white background first, then on a black background
            graph.setColor((i == 0) ? Color.WHITE : Color.BLACK);
            graph.fillRect(0, 0, nOriginWidth, nOriginHeight);
            graph.drawBitmap(0, 0, nOriginWidth, nOriginHeight, bmpSrc, 0, 0);

            //Create a new bitmap with the desired size
            bmpScaled[i] = new Bitmap(nWidth, nHeight);
            if (nAspectRatio == Bitmap.SCALE_TO_FIT)
            {
                for (int y = 0; y < nHeight; y++) {bmpScaled[i].setARGB(aEmptyLine, 0, nWidth, 0, y, nWidth, 1);}
            }

            bmpOrigin.scaleInto(bmpScaled[i], nFilterType, nAspectRatio);
        }

        Bitmap bmpFinal = bmpScaled[0];
        int[][] aPixelLine = new int[2][nWidth];

        for (int y = 0; y < nHeight; y++)
        {
            bmpScaled[0].getARGB(aPixelLine[0], 0, nWidth, 0, y, nWidth, 1);
            bmpScaled[1].getARGB(aPixelLine[1], 0, nWidth, 0, y, nWidth, 1);

            for (int x = 0; x < nWidth; x++)
            {
                if(((aPixelLine[0][x] >> 24) & 0xff) == 0) {
                	aPixelLine[0][x] = 0x00000000;
                } else {
                    int nAlpha = ((aPixelLine[1][x] >> 16) & 0xff) - ((aPixelLine[0][x] >> 16) & 0xff) + 255;
                    if (nAlpha == 0) {
                    	aPixelLine[0][x] = 0x00000000; //Completely transparent
                    } else if(nAlpha >= 255) {
                    	aPixelLine[0][x] |= 0xff000000; //Completely opaque
                    } else {
                        //Compute the value of the each channel one by one
                        int nRed = ((aPixelLine[0][x] >> 16 ) & 0xff);
                        int nGreen = ((aPixelLine[0][x] >> 8 ) & 0xff);
                        int nBlue = (aPixelLine[0][x] & 0xff);

                        nRed = (int)(255 + (255.0 * ((double)(nRed-255)/(double)nAlpha)));
                        nGreen = (int)(255 + (255.0 * ((double)(nGreen-255)/(double)nAlpha)));
                        nBlue = (int)(255 + (255.0 * ((double)(nBlue-255)/(double)nAlpha)));

                        if (nRed < 0) nRed = 0;
                        if (nGreen < 0) nGreen = 0;
                        if (nBlue < 0) nBlue = 0;

                        aPixelLine[0][x] = nBlue | (nGreen<<8) | (nRed<<16) | (nAlpha<<24);
                    }
                }
            }

            bmpFinal.setARGB(aPixelLine[0], 0, nWidth, 0, y, nWidth, 1);
        }

        return bmpFinal;
    }
}