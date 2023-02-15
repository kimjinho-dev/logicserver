package com.example.project5.Common;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Apimanager {// 필요한가? DB?  redis?

    String urls="";
    String port="";
    String api="";

    public Apimanager(String urls, String port, String api) {
        this.urls = urls;
        this.port = port;
        this.api = api;
    }

    public String call_get(){
        URL url = null;
        String readLine = null;
        StringBuilder buffer = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        HttpURLConnection urlConnection = null;

        int connTimeout = 5000;
        int readTimeout = 3000;
        try
        {

            String temp= this.urls+this.port+this.api;
            url = new URL(temp);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setRequestProperty("Accept", "application/json;");

            buffer = new StringBuilder();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
                while((readLine = bufferedReader.readLine()) != null)
                {
                    buffer.append(readLine).append("\n");
                }
            }
            else
            {
                buffer.append("code : ");
                buffer.append(urlConnection.getResponseCode()).append("\n");
                buffer.append("message : ");
                buffer.append(urlConnection.getResponseMessage()).append("\n");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedWriter != null) { bufferedWriter.close(); }
                if (bufferedReader != null) { bufferedReader.close(); }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }


        return buffer.toString();

    }

    public String call_post(){

        StringBuilder buffer = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        HttpURLConnection urlConnection = null;
        String readLine = null;

        int connTimeout = 5000;
        int readTimeout = 3000;

        String sendData = "보낼 데이터";
        try
        {

            String temp= this.urls+this.port+this.api;
            URL url = new URL(temp);

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setRequestProperty("Content-Type", "application/json;");
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(true);

            outputStream = urlConnection.getOutputStream();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(sendData);
            bufferedWriter.flush();

            buffer = new StringBuilder();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
                while((readLine = bufferedReader.readLine()) != null)
                {
                    buffer.append(readLine).append("\n");
                }
            }
            else
            {
                buffer.append("\"code\" : \""+urlConnection.getResponseCode()+"\"");
                buffer.append(", \"message\" : \""+urlConnection.getResponseMessage()+"\"");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedWriter != null) { bufferedWriter.close(); }
                if (outputStream != null) { outputStream.close(); }
                if (bufferedReader != null) { bufferedReader.close(); }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }



        return buffer.toString();


    }





















}
