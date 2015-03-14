package com.example.calvinnolan.group17smart_ghandroid;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 *
 * @author elgammaa
 */
public class WebHelper
{
    public static String encodeURL( String str )
    {
        try
        {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception _ignore)
        {
            return str;
        }
    }

    private static void encodeNumber( StringBuilder sb, int num )
    {
        num = num << 1;
        if (num < 0)
        {
            num = ~num;
        }
        while (num >= 0x20)
        {
            int nextValue = (0x20 | (num & 0x1f)) + 63;
            sb.append((char) (nextValue));
            num >>= 5;
        }
        num += 63;
        sb.append((char) (num));
    }

    public static String readString( InputStream inputStream ) throws IOException
    {
        String encoding = "UTF-8";
        InputStream in = new BufferedInputStream(inputStream, 4096);
        try
        {
            byte[] buffer = new byte[4096];
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int numRead;
            while ((numRead = in.read(buffer)) != -1)
            {
                output.write(buffer, 0, numRead);
            }
            return output.toString(encoding);
        } finally
        {
            in.close();
        }
    }


}