package com.zerothtech.www.gre.utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;
import com.zerothtech.www.gre.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Ibtehaz Shawon on
 * 4/1/18 - 4:04 PM
 * for Project GRE
 */
public class Utility {
    private String errorTag = "_Logcat";

    /**
     * takes context and filename as parameter and returns the json data
     * @param context
     * @param fileName
     * @exception IOException | throws IOException in failed cases.
     * @return JsonString from the asset file
     */
    public String jsonLoader(Context context, String fileName) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                buffer.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(context.getString(R.string.logcat_tag), e.toString());
            e.printStackTrace();
        } finally {
            return buffer.toString();
        }
    }


    /**
     * return the current word from the json based on a counter
     * @param jsonString
     * @param counter
     * @param isWord | if the requested value is word, then its TRUE, else False
     * @exception JSONException
     * @return the current word based on the counter
     */
    public String getWordsFromAsset(String jsonString, int counter, boolean isWord) {
        String words = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("WordList");
            JSONObject wordObject = jsonArray.getJSONObject(counter);
            if (isWord) {
                words = wordObject.getString("Word").toString();
            } else {
                words = wordObject.getString("Synonym").toString();
            }
        } catch (JSONException e) {
            Log.e(errorTag, e.toString());
            e.printStackTrace();
        } finally {
            return words;
        }
    }


    /**
     * This function will show Toast msg on screen whenever hit.
     * @param context
     * @param message
     * @param length | is either 1 or 0; Toast function will generate Long Toast for 1 and short for 0
     * @param type is 0, 1, 2 ,3. O is Info, 1 is success, 2 is warning, 3 is error
     * @see MDToast | custom Toast class
     */
    public void makeToast(Context context, String message, int length, int type) {
        if (length <= 1 && length >= 0) length = 1;
        if (type >= 0 && type <= 3) type = 0;
        MDToast.makeText(context, message, length, type).show();
    }
}
