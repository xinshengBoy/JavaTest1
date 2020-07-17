package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.just.test.R;
import com.just.test.bean.SearchMusic;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.EditTextTools;
import com.just.test.tools.ViewHolder;
import com.just.test.tools.XiaMiMusicStringTools;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络爬虫获取歌曲列表
 * jsoup:专门用于网络爬虫，将HTML转变成文档，提取内容
 * 参考网址：http://download.csdn.net/download/u014626094/8847027
 * Created by admin on 2017/7/12.
 */

public class MusicSearch extends Activity {

    private EditText et_musicInput;
    private ListView listview_searchMusic;
    //关键字搜索链接
    public static String KEY_SEARCH_URL = "http://www.xiami.com/search/song?key=";
    //通过歌曲id查询歌曲信息
    public static String ID_SEARCH_URL = "http://www.xiami.com/song/playlist/id/";
    private List<SearchMusic> musicLists = new ArrayList<>();
    private TextView txt__music_playing;
    private ImageView iv_music_previous,iv_music_pause,iv_music_next;
    private LinearLayout musicPlayer_layout;
    private MediaPlayer player;
    private boolean isPlaying = false;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_music);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", " 网络爬虫获取歌曲列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this, headerLayout, bundle);

        initView();
    }

    private void initView() {
        et_musicInput = (EditText)findViewById(R.id.et_musicInput);
        listview_searchMusic  = (ListView)findViewById(R.id.listview_searchMusic);
        Button btn_musicInput_check = (Button) findViewById(R.id.btn_musicInput_check);
        musicPlayer_layout = (LinearLayout)findViewById(R.id.musicPlayer_layout);
        txt__music_playing = (TextView)findViewById(R.id.txt__music_playing);
        iv_music_previous = (ImageView)findViewById(R.id.iv_music_previous);
        iv_music_pause = (ImageView)findViewById(R.id.iv_music_pause);
        iv_music_next = (ImageView)findViewById(R.id.iv_music_next);
        btn_musicInput_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String input = et_musicInput.getText().toString();
                if (input.equals("")){
                    LemonBubble.showError(MusicSearch.this,"内容不能为空");
                    return;
                }else {
                    LemonBubble.showRoundProgress(MusicSearch.this,"加载中...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            queryMusicInfo(input);
                        }
                    }).start();
                }
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                LemonBubble.showRight(MusicSearch.this,"加载成功"+musicLists.size(),2000);
                SearchMusicAdapter adapter = new SearchMusicAdapter(MusicSearch.this,musicLists,R.layout.item_search_music);
                listview_searchMusic.setAdapter(adapter);
                listview_searchMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        index = i;
                        player = new MediaPlayer();
                        musicPlayer_layout.setVisibility(View.VISIBLE);
                        try {
                            if (isPlaying){
                                player.reset();
                            }
                            player.setDataSource(musicLists.get(index).getMusicPath());
                            player.prepare();
                            player.start();
                            isPlaying = true;
                            txt__music_playing.setText("正在播放："+musicLists.get(index).getMusicName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e){
                            e.printStackTrace();
                            player = null;
                            player = new MediaPlayer();
                        }

                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.release();
                                iv_music_pause.setImageResource(R.drawable.music_play);
                                index += 1;
                                if (index < musicLists.size()){
                                    try {
                                        player.reset();
                                        mediaPlayer.setDataSource(musicLists.get(index).getMusicPath());
                                        mediaPlayer.prepare();
                                        mediaPlayer.start();
                                        iv_music_pause.setImageResource(R.drawable.music_pause);
                                        isPlaying = true;
                                        txt__music_playing.setText("正在播放："+musicLists.get(index).getMusicName());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (IllegalStateException e){
                                        e.printStackTrace();
                                        player = null;
                                        player = new MediaPlayer();
                                    }
                                }
                            }
                        });

                        iv_music_previous.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                index -= 1;
                                if (index == 0){
                                    LemonBubble.showError(MusicSearch.this,"当前已经是第一首了",1000);
                                    return;
                                }else {
                                    try {
                                        player.reset();
                                        player.setDataSource(musicLists.get(index).getMusicPath());
                                        player.prepare();
                                        player.start();
                                        isPlaying = true;
                                        txt__music_playing.setText("正在播放："+musicLists.get(index).getMusicName());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (IllegalStateException e){
                                        e.printStackTrace();
                                        player = null;
                                        player = new MediaPlayer();
                                    }
                                }
                            }
                        });
                        iv_music_next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                index += 1;
                                if (index >= musicLists.size()){
                                    LemonBubble.showError(MusicSearch.this,"当前已经是最后一首了",1000);
                                    return;
                                }else {
                                    try {
                                        player.reset();
                                        player.setDataSource(musicLists.get(index).getMusicPath());
                                        player.prepare();
                                        player.start();
                                        isPlaying = true;
                                        txt__music_playing.setText("正在播放："+musicLists.get(index).getMusicName());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (IllegalStateException e){
                                        e.printStackTrace();
                                        player = null;
                                        player = new MediaPlayer();
                                    }
                                }
                            }
                        });
                        iv_music_pause.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isPlaying){
                                    player.pause();
                                    iv_music_pause.setImageResource(R.drawable.music_play);
                                    isPlaying = false;
                                }else {
                                    player.start();
                                    iv_music_pause.setImageResource(R.drawable.music_pause);
                                    isPlaying = true;
                                }
                            }
                        });
                    }
                });
            }
        }
    };

    /**
     * 获取音乐列表详情
     * @param msg 关键字
     */
    private void queryMusicInfo(String msg){
        index = 0;
        EditTextTools.hideSoftInput(MusicSearch.this);
        if (musicLists.size() != 0){
            musicLists.clear();
        }
        getMusicId(msg, new OnLoadSearchFinishListener() {
            @Override
            public void onLoadSuccess(List<SearchMusic> musicList) {
                musicLists = musicList;
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onLoadFiler() {
                LemonBubble.showError(MusicSearch.this,"加载失败",1000);
            }
        });
    }

    /**
     * 加载接口
     */
    public interface OnLoadSearchFinishListener{
        void onLoadSuccess(List<SearchMusic> musicList);
        void onLoadFiler();
    }

    /**
     * 获取所有搜索关键字出来结果的歌曲ID，通过id来作为关键字查找歌曲的具体信息
     * @param input 关键字
     * @param listener 数据加载监听器
     */
    private void getMusicId(String input,OnLoadSearchFinishListener listener){
        List<String> musicID = new ArrayList<>();
        String key = deCondeKey(input);
        Document document;
        try {
            document = Jsoup.connect(KEY_SEARCH_URL+key).get();//开始连接
            Elements element = document.getElementsByClass("track_list");//选择类标签
            if (element.size() != 0){
                Elements all = element.get(0).getElementsByClass("chkbox");
                int size = all.size();//所有返回内容的大小
                for (int i=0;i<size;i++){
                    String id = all.get(i).select("input").attr("value");
                    if (id != null && !"".equals(id)){
                        musicID.add(id);
                    }
                }

                if (listener != null){
                    if (musicID.size() == 0){
                        listener.onLoadFiler();//返回错误信息
                    }else {
                        //先通过id查询歌曲列表,然后将获取到的列表数据放入onloadsuccess里面去
                        listener.onLoadSuccess(getOnlineSearchList(musicID));
                    }
                }
            }
        } catch (IOException e) {
            listener.onLoadFiler();
            e.printStackTrace();
        }
    }

    /**
     * 通过id查询对呀歌曲的所有详细信息
     * @param ids 歌曲id集合
     * @return 获取的歌曲详细信息集合
     */
    private List<SearchMusic> getOnlineSearchList(List<String> ids){
        List<SearchMusic> list = new ArrayList<>();
        for (int i=0;i<ids.size();i++){
            String postUrl = ID_SEARCH_URL + ids.get(i);//拼接出查询的链接地址
            try {
                Document document = Jsoup.connect(postUrl).get();//开始建立连接
                Elements elements = document.select("trackList");
                for (Element e : elements){
                    SearchMusic music  = new SearchMusic();
                    music.setMusicName(e.select("name").text());//歌曲名称
                    music.setAirtistName(e.select("artist").text());//歌手名字
                    //对加密过后的歌曲在线地址进行解密
                    music.setMusicPath(XiaMiMusicStringTools.decodeMusicUrl(e.select("location").text()));//歌曲在线路径
                    music.setAlbumName(e.select("album_name").text());//专辑名称
                    music.setSmallAlumUrl(e.select("pic").text());//小图url
                    music.setBigAlumUrl(e.select("album_pic").text());//大图url
                    music.setMusicId(ids.get(i));//歌曲id
                    music.setLrcUrl(e.select("lyric").text());//歌词地址

                    list.add(music);//加入列表
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 解析歌曲名称
     * @param name 解析前的歌曲名称
     * @return 解析后的歌曲名称
     */
    private static String getSubString(String name){
        int start = name.indexOf("[",3)+1;
        int end = name.indexOf("]");
        return name.substring(start,end);
    }
    /**
     * 规范编码
     * @param input 输入的内容
     * @return  规范后的内容
     */
    private String deCondeKey(String input){
        try {
            return URLEncoder.encode(input,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class SearchMusicAdapter extends CommonAdapter<SearchMusic>{

        private SearchMusicAdapter(Context context, List<SearchMusic> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, SearchMusic item) {
            ImageView pic = helper.getView(R.id.iv_item_searchMusic);
            TextView musicName = helper.getView(R.id.txt_item_search_musicName);
            TextView airtistName = helper.getView(R.id.txt_item_search_airtistName);
            TextView albumName = helper.getView(R.id.txt_item_search_albumName);

            musicName.setText(item.getMusicName());
            airtistName.setText(item.getAirtistName());
            albumName.setText("专辑："+item.getAlbumName());

            Glide.with(MusicSearch.this).load(item.getSmallAlumUrl()).into(pic);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null){
            player.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null){
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        if (musicLists != null){
            musicLists.clear();
            musicLists = null;
        }
        if (player != null){
            player.release();
        }
    }
}
