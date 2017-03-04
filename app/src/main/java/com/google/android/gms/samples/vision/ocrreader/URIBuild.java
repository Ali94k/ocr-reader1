package com.google.android.gms.samples.vision.ocrreader;

import android.net.Uri;

/**
 * Created by alike on 02.03.2017.
 */

public class URIBuild {
    public String build (String a)
    {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")

                //http://dictionary.cambridge.org/dictionary/english/willpower.xml
                .authority("dictionary.cambridge.org")
                .appendPath("dictionary")
                .appendPath("english")
                .appendPath( a.replace(' ', '_'))
        ;
        String urlsrt = new String();
        urlsrt = builder.build().toString();
        return urlsrt;

    }

}
