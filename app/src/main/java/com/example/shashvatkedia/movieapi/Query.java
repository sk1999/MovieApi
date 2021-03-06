package com.example.shashvatkedia.movieapi;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;
import static com.example.shashvatkedia.movieapi.MainActivity.movie;
import static java.lang.System.in;

/**
 * Created by Shashvat Kedia on 08-09-2017.
 */

public class Query {

    private Query(){

    }

    public static ArrayList<MovieInfo> fetchData(String murl){
        URL url=createUrl(murl);
        String response = null;
        try{
            response=makerequest(url,0);
        }
        catch(IOException e){
            Log.e("#","Error IOException");
        }
        ArrayList<MovieInfo> info=extractjson(response);
        return info;
    }

    public static ArrayList<String> extraxtPosterPaths(String murl){
        URL url = createUrl(murl);
        String response = null;
        try{
            response = makerequest(url,1);
        }
        catch(IOException e){
            Log.e("#","Error IOException");
        }
        ArrayList<String> posterPaths = getPosterPaths(response);
        return posterPaths;
    }

    public static ArrayList<String> getPosterPaths(String response){
        if(TextUtils.isEmpty(response)){
            return null;
        }
        ArrayList<String> posterPaths = new ArrayList<String>();
        try{
            JSONObject object = new JSONObject(response);
            JSONArray posterArray = object.getJSONArray("posters");
            for(int i = 0;i <= posterArray.length()-1;i++){
                JSONObject posterPathObject = posterArray.getJSONObject(i);
                String posterPath = posterPathObject.getString("file_path");
                posterPaths.add(posterPath);
            }
            JSONArray backdropsArray = object.getJSONArray("backrops");
            for(int i = 0;i <= backdropsArray.length()-1;i++){
                JSONObject backdropsObject = backdropsArray.getJSONObject(i);
                String backdropsPath = backdropsObject.getString("file_path");
                posterPaths.add(backdropsPath);
            }
        }
        catch (JSONException e){
            Log.e("#","Error JSONException");
        }
        return posterPaths;
    }

    public static ArrayList<Reviews> extractReviewsList(String murl){
        URL url = createUrl(murl);
        String response = null;
        try{
            response = makerequest(url,0);
        }
        catch(IOException e){
            Log.e("#","Error IOException");
        }
        ArrayList<Reviews> reviewsList = getReviewsList(response);
        return reviewsList;
    }

    public static ArrayList<Reviews> getReviewsList(String response){
        if(TextUtils.isEmpty(response)){
            return null;
        }
        ArrayList<Reviews> reviewsList = new ArrayList<Reviews>();
        try{
            JSONObject object = new JSONObject(response);
            JSONArray reviewsArray = object.getJSONArray("results");
            for(int i = 0;i <= reviewsArray.length()-1;i++){
                JSONObject arrayObject = reviewsArray.getJSONObject(i);
                String id = arrayObject.getString("id");
                String author = arrayObject.getString("author");
                String url = arrayObject.getString("url");
                String content = arrayObject.getString("content");
                Reviews review = new Reviews(author,content,id,url);
                reviewsList.add(review);
            }
        }
        catch(JSONException e){
            Log.e("#","Error JSONException");
        }
        return reviewsList;
    }

    public static ArrayList<Movie_info.GenreObject> extractAvailableGenreList(String murl){
        URL url = createUrl(murl);
        String response = null;
        try{
            response = makerequest(url,0);
        }
        catch(IOException e){
            Log.e("#","Error IOException");
        }
        ArrayList<Movie_info.GenreObject> genreList = getGenreList(response);
        return genreList;
    }

    public static ArrayList<Movie_info.GenreObject> getGenreList(String response){
        if(TextUtils.isEmpty(response)){
            return null;
        }
        ArrayList<Movie_info.GenreObject> genreList = new ArrayList<Movie_info.GenreObject>();
        try{
            JSONObject obj = new JSONObject(response);
            JSONArray genreArray = obj.getJSONArray("genres");
            for(int i = 0;i <= genreArray.length()-1;i++){
                JSONObject arrayObject = genreArray.getJSONObject(i);
                int genreId = arrayObject.getInt("id");
                String genreName = arrayObject.getString("name");
                Movie_info.GenreObject genre = new Movie_info.GenreObject(genreName,genreId);
                genreList.add(genre);
            }
        }
        catch(JSONException e){
            Log.e("#","Error JSONException");
        }
        return genreList;
    }

    public static ExtendedMovieInfo findMovieInfo(String murl){
        URL url=createUrl(murl);
        String response=null;
        try{
            response=makerequest(url,0);
        }
        catch(IOException e){
            Log.e("#","IOException");
        }
        ExtendedMovieInfo info=extractMovieInfo(response);
        return info;
    }

    public static ExtendedCastInfo extractCastInfo(String murl){
    URL url=createUrl(murl);
        String response=null;
        try{
            response=makerequest(url,0);
        }
        catch(IOException e){
            Log.e("#","IOException");
        }
        ExtendedCastInfo info=getCastdetails(response);
        return info;
    }

    public static ExtendedCastInfo getCastdetails(String response){
     if(TextUtils.isEmpty(response)){
         return null;
     }
     ExtendedCastInfo info=null;
     try{
         JSONObject obj=new JSONObject(response);
         String b_day=obj.getString("birthday");
         String d_day=obj.getString("deathday");
         if(d_day==null){
             d_day="";
         }
         String name=obj.getString("name");
         String desc=obj.getString("biography");
         String p_ofb=obj.getString("place_of_birth");
         String path=obj.getString("profile_path");
         info=new ExtendedCastInfo(name,b_day,desc,path,p_ofb,d_day);
     }
     catch(JSONException e){
         Log.e("#","JSONException");
     }
     return info;
    }

    public static ArrayList<video_values> getVideos(String murl){
    URL url=createUrl(murl);
        String response=null;
        try{
            response=makerequest(url,0);
        }
        catch(IOException e){
            Log.e("#","IOException");
        }
        ArrayList<video_values> videos=extractVideos(response);
        return videos;
    }

    public static ArrayList<video_values> extractVideos(String response){
     if(TextUtils.isEmpty(response)){
         return null;
     }
     ArrayList<video_values> video=new ArrayList<video_values>();
        try{
            JSONObject obj=new JSONObject(response);
            JSONArray result=(JSONArray) obj.getJSONArray("results");
            for(int i=0;i<=result.length()-1;i++){
                JSONObject array_obj=(JSONObject) result.getJSONObject(i);
                String site=array_obj.getString("site");
                String type=array_obj.getString("type");
                String key=array_obj.getString("key");
                video_values values=new video_values(key,type,site);
                video.add(values);
            }
        }
        catch(JSONException e){
            Log.e("#","JSONException");
        }
        return video;
    }

    public static ArrayList<CastInfo> findCast(String murl){
        URL url=createUrl(murl);
        String response=null;
        try{
            response=makerequest(url,0);
        }
        catch(IOException e){
            Log.e("#","IOException");
        }
        ArrayList<CastInfo> cast=extractCast(response);
        return cast;
    }

    public static ArrayList<CastInfo> extractCast(String response){
        if(TextUtils.isEmpty(response)){
            return null;
        }
        ArrayList<CastInfo> cast=new ArrayList<CastInfo>();
        try{
            JSONObject obj=new JSONObject(response);
            JSONArray cast_info=(JSONArray) obj.getJSONArray("cast");
            for(int i=0;i<= cast_info.length();i++){
                JSONObject array_obj=(JSONObject) cast_info.getJSONObject(i);
                String character=array_obj.getString("character");
                String name=array_obj.getString("name");
                String path=array_obj.getString("profile_path");
                int id=array_obj.getInt("id");
                CastInfo info=new CastInfo(character,name,path,id);
                cast.add(info);
            }
        }
        catch(JSONException e){
            Log.e("#","JSONException");
        }
        return cast;
    }

    public static ExtendedMovieInfo extractMovieInfo(String response){
        if(TextUtils.isEmpty(response)){
            return null;
        }
        ExtendedMovieInfo info=null;
        try{
            JSONObject obj=new JSONObject(response);
            boolean age=obj.getBoolean("adult");
            String movie_age="",movie_name,movie_desc,movie_release,movie_poster;
            String[] movie_genres={"",""};
            int length=0,movie_id,movie_runtime;
            double movie_rating;
            if(age){
                movie_age="A";
            }
            else{
                movie_age="UA";
            }
            JSONArray genre=(JSONArray) obj.getJSONArray("genres");
            if(genre.length()>=2){
                length=2;
            }else{
                length=genre.length();
            }
            for(int i=0;i<=length-1;i++){
                JSONObject array_obj=(JSONObject) genre.getJSONObject(i);
                movie_genres[i]=array_obj.getString("name");
            }
            movie_id=obj.getInt("id");
            movie_name=obj.getString("original_title");
            movie_desc=obj.getString("overview");
            movie_rating=obj.getDouble("vote_average");
            movie_runtime=obj.getInt("runtime");
            movie_release=obj.getString("release_date");
            movie_poster=obj.getString("poster_path");
            info=new ExtendedMovieInfo(movie_name,movie_desc,movie_id,movie_runtime,movie_release,movie_genres[0],movie_genres[1],movie_age,movie_rating,movie_poster);
        }
        catch(JSONException e){
            Log.e("#","JSONException");
        }
        return info;
    }

    public static String makerequest(URL url,int type) throws IOException{
        String json="";
        if(url == null){
            return json;
        }
        HttpURLConnection connection = null;
        InputStream input = null;
        try{
            connection=(HttpURLConnection) url.openConnection();
            if(type == 0) {
                connection.setRequestMethod("GET");
            }
            else{
                connection.setRequestMethod("POST");
            }
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();
            if(connection.getResponseCode()==200){
                input=connection.getInputStream();
                json=read(input);
            }
            else{
                Log.e("#","Error Code"+connection.getResponseCode());
            }
        }
        catch(IOException e){
            Log.e("#","Error IOException");
        }
        finally{
            if(connection!=null){
                connection.disconnect();
            }
            if(input!=null){
                input.close();
            }
        }
        return json;
    }

    public static String read(InputStream input) throws IOException{
        StringBuilder build=new StringBuilder();
        if(input!=null){
            InputStreamReader inputread = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader buffer=new BufferedReader(inputread);
            String line=buffer.readLine();
            while(line != null){
                build.append(line);
                line=buffer.readLine();
            }
        }
        return build.toString();
    }

    public static ArrayList<MovieInfo> extractjson(String response){
        if(TextUtils.isEmpty(response)){
            return null;
        }
        ArrayList<MovieInfo> info=new ArrayList<>();
        try{
            JSONObject obj=new JSONObject(response);
            JSONArray mov=(JSONArray) obj.getJSONArray("results");
            for(int i=0;i<=mov.length()-1;i++){
                JSONObject curr=mov.getJSONObject(i);
                String name=curr.getString("original_title");
                String path=curr.getString("poster_path");
                long id=curr.getLong("id");
                MovieInfo movie=new MovieInfo(name,path,id);
                info.add(movie);
            }
        }
        catch(JSONException e){
            Log.e("#","Error JSONException"+e);
        }
        return info;
    }

    public static URL createUrl(String murl){
        URL url=null;
        try{
            url=new URL(murl);
        }
        catch(MalformedURLException e){
            Log.e("#","Malformed Error");
        }
        return url;
    }
}
