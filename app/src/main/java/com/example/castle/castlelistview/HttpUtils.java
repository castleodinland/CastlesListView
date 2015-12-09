package com.example.castle.castlelistview;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import com.example.castle.castlelistview.SoundStructData.*;

/**
 * Created by castle on 2015/12/2.
 */
public class HttpUtils {

    final static String TAG = "HttpUtils";

    public HttpUtils() {

    }

    public static List<String> getCategoriesJson(String url_str, MainActivity thisa) {
        URL url;
        HttpURLConnection urlConn = null;
        InputStream is = null;
        String json_str;
        List<String> result_list = null;

        try {
            Log.i(TAG, "Castle: begin to link URL");

            url = new URL(url_str);

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");//if set GET request, do not set DoOutpu again
            //urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setConnectTimeout(8000);
            urlConn.setReadTimeout(5000);
            //os = urlConn.getOutputStream();
            Log.i(TAG, "Castle: Before connect.");

            int code = urlConn.getResponseCode();
            Log.i(TAG, "Castle: See RspCode" + code);
            if (code == 200 || code == 204) {
                //JSON need try also
                is = urlConn.getInputStream();
                Log.i(TAG, "Castle: GetInputStream.");
                json_str = new String(readAllBytes(is));
                //Create json here.
                Log.i(TAG, "Castle: After readall.");

                Log.i(TAG, "Castle: Good response from site.");
                JSONObject j_main = new JSONObject(json_str);
                int ret = j_main.getInt("ret");
                if (ret != -1) {
                    Log.i(TAG, "Castle: Good Ret.");
                    JSONArray jcg_array = j_main.getJSONArray("categories");
                    result_list = new ArrayList<String>();

                    for (int i = 0; i < jcg_array.length(); i++) {
                        //
                        JSONObject item = jcg_array.getJSONObject(i);
                        //int cover_url = item.getInt("cover_url");
                        //show in list
                        result_list.add(item.getInt("id") + " : " + item.getString("title"));
                        Log.i(TAG, "Castle: Add to list-->." + item.getInt("id") + " : " + item.getString("title"));
                    }
                } else {
                    Log.i(TAG, "Castle: Bad Ret.");
                    Toast.makeText(thisa, "Bad Ret", Toast.LENGTH_LONG).show();
                }
            } else {
                //bad response
                Log.i(TAG, "Castle: Bad response.");
                Toast.makeText(thisa, "Get 404", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
                Log.i(TAG, "Castle: Http finnally.");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return result_list;
    }

    public static List<String> getTagsJson(String url_str, TagsActivity thisa) {
        URL url;
        HttpURLConnection urlConn = null;
        InputStream is = null;
        String json_str;
        List<String> result_list = null;

        try {
            Log.i(TAG, "Castle: begin to link URL");

            url = new URL(url_str);

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");//if set GET request, do not set DoOutpu again
            //urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setConnectTimeout(8000);
            urlConn.setReadTimeout(5000);
            //os = urlConn.getOutputStream();
            Log.i(TAG, "Castle: Before connect.");

            int code = urlConn.getResponseCode();
            Log.i(TAG, "Castle: See RspCode" + code);
            if (code == 200 || code == 204) {
                //JSON need try also
                is = urlConn.getInputStream();
                Log.i(TAG, "Castle: GetInputStream.");
                json_str = new String(readAllBytes(is));
                //Create json here.
                Log.i(TAG, "Castle: After readall.");

                Log.i(TAG, "Castle: Good response from site.");
                JSONObject j_main = new JSONObject(json_str);
                int ret = j_main.getInt("ret");
                if (ret != -1) {
                    Log.i(TAG, "Castle: Good Ret.");
                    JSONArray jcg_array = j_main.getJSONArray("tags");
                    result_list = new ArrayList<String>();

                    for (int i = 0; i < jcg_array.length(); i++) {
                        //
                        JSONObject item = jcg_array.getJSONObject(i);
                        //int cover_url = item.getInt("cover_url");
                        //show in list
                        result_list.add(item.getString("name"));
                        Log.i(TAG, "Castle: Add to list-->." + i + " : " + item.getString("name"));
                    }
                } else {
                    Log.i(TAG, "Castle: Bad Ret.");
                    Toast.makeText(thisa, "Bad Ret", Toast.LENGTH_LONG).show();
                }
            } else {
                //bad response
                Log.i(TAG, "Castle: Bad response.");
                Toast.makeText(thisa, "Get 404", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
                Log.i(TAG, "Castle: Http finnally.");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return result_list;
    }

    public static List<AlbumsDatStt> getAlbumsJson(String url_str, AlbumsActivity thisa) {
        URL url;
        HttpURLConnection urlConn = null;
        InputStream is = null;
        String json_str;
        List<AlbumsDatStt> result_list = null;

        try {
            Log.i(TAG, "Castle: begin to link URL");

            url = new URL(url_str);

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");//if set GET request, do not set DoOutpu again
            //urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setConnectTimeout(8000);
            urlConn.setReadTimeout(5000);
            //os = urlConn.getOutputStream();
            Log.i(TAG, "Castle: Before connect.");

            int code = urlConn.getResponseCode();
            Log.i(TAG, "Castle: See RspCode" + code);
            if (code == 200 || code == 204) {
                //JSON need try also
                is = urlConn.getInputStream();
                Log.i(TAG, "Castle: GetInputStream.");
                json_str = new String(readAllBytes(is));
                //Create json here.
                Log.i(TAG, "Castle: After readall.");

                Log.i(TAG, "Castle: Good response from site.");
                JSONObject j_main = new JSONObject(json_str);
                int ret = j_main.getInt("ret");
                if (ret != -1) {
                    Log.i(TAG, "Castle: Good Ret.");
                    JSONArray jcg_array = j_main.getJSONArray("albums");
                    result_list = new ArrayList<AlbumsDatStt>();

                    for (int i = 0; i < jcg_array.length(); i++) {
                        //
                        JSONObject item = jcg_array.getJSONObject(i);
                        //int cover_url = item.getInt("cover_url");
                        //show in list
                        result_list.add(new AlbumsDatStt(item.getInt("id"),
                                item.getString("title"),
                                item.getInt("tracks_count")));
                        Log.i(TAG, "Add to list-->" + item.getInt("id") + ":"
                                + item.getString("title")
                                + "(" + item.getInt("tracks_count") + ")");
                    }
                } else {
                    Log.i(TAG, "Castle: Bad Ret.");
                    Toast.makeText(thisa, "Bad Ret", Toast.LENGTH_LONG).show();
                }
            } else {
                //bad response
                Log.i(TAG, "Castle: Bad response.");
                Toast.makeText(thisa, "Get 404", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
                Log.i(TAG, "Castle: Http finnally.");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return result_list;
    }


    public static List<TracksDatStt> getTracksJson(String url_str, TracksActivity thisa) {
        URL url;
        HttpURLConnection urlConn = null;
        InputStream is = null;
        String json_str;
        List<TracksDatStt> result_list = null;

        try {
            Log.i(TAG, "Castle: begin to link URL");

            url = new URL(url_str);

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");//if set GET request, do not set DoOutpu again
            //urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setConnectTimeout(8000);
            urlConn.setReadTimeout(5000);
            //os = urlConn.getOutputStream();
            Log.i(TAG, "Castle: Before connect.");

            int code = urlConn.getResponseCode();
            Log.i(TAG, "Castle: See RspCode" + code);
            if (code == 200 || code == 204) {
                //JSON need try also
                is = urlConn.getInputStream();
                Log.i(TAG, "Castle: GetInputStream.");
                json_str = new String(readAllBytes(is));
                //Create json here.
                Log.i(TAG, "Castle: After readall.");

                Log.i(TAG, "Castle: Good response from site.");
                //Log.i(TAG, json_str);
                JSONObject j_main = new JSONObject(json_str);
                int ret = j_main.getInt("ret");
                if (ret != -1) {
                    Log.i(TAG, "Castle: Good Ret for tracks.");
                    JSONObject jcg_albem = j_main.getJSONObject("album");
                    JSONArray jcg_array = jcg_albem.getJSONArray("tracks");

                    result_list = new ArrayList<TracksDatStt>();

                    for (int i = 0; i < jcg_array.length(); i++) {
                        //
                        JSONObject item = jcg_array.getJSONObject(i);
                        //int cover_url = item.getInt("cover_url");
                        //show in list
                        result_list.add(new TracksDatStt(item.getInt("id"),
                                item.getString("title"),
                                item.getString("duration"),
                                item.getString("cover_url_large")));
                        Log.i(TAG, "Add to list-->" + item.getInt("id") + ":"
                                + item.getString("title")
                                + "(" + item.getInt("duration") + ")");
                    }
                } else {
                    Log.i(TAG, "Castle: Bad Ret.");
                    Toast.makeText(thisa, "Bad Ret", Toast.LENGTH_LONG).show();
                }
            } else {
                //bad response
                Log.i(TAG, "Castle: Bad response.");
                Toast.makeText(thisa, "Get 404", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (urlConn != null) {
                    urlConn.disconnect();
                }
                Log.i(TAG, "Castle: Http finnally.");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return result_list;
    }

    public static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bytesBuf = new ByteArrayOutputStream(1024);
        int bytesRead = 0;
        byte[] readBuf = new byte[1024];
        while ((bytesRead = is.read(readBuf, 0, readBuf.length)) != -1) {
            bytesBuf.write(readBuf, 0, bytesRead);
        }
        return bytesBuf.toByteArray();
    }
}

