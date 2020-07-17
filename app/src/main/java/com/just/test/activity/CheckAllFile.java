package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.JSONDateKey;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;
import com.github.barteksc.pdfviewer.PDFView;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询sd卡所有文件相同类型的文件
 * 已经打开PDF文件
 */
public class CheckAllFile extends Activity {

	private EditText et_findAllType;
	private ListView list_checkAll;
	private ArrayList<Bundle> nameFile = new ArrayList<>();
	private TextView txt_checkAllFile_txt;
	private PDFView view_checkFile_pdfview;
	private String input;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_checkall);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "查找文件及打开PDF");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		initView();
	}

	private void initView(){
		et_findAllType = (EditText) findViewById(R.id.et_findAllType);
		Button btn_checkAll = (Button) findViewById(R.id.btn_checkAll);
		list_checkAll = (ListView) findViewById(R.id.list_checkAll);
		txt_checkAllFile_txt = (TextView)findViewById(R.id.txt_checkAllFile_txt);
		view_checkFile_pdfview = (PDFView)findViewById(R.id.view_checkFile_pdfview);

		btn_checkAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				input = et_findAllType.getText().toString();
				new CheckFileTask().execute(null,null);
			}
		});
	}

	/**
	 * 开启异步任务进行查询
	 */
	private class CheckFileTask extends AsyncTask<String,Void,String>{

		@Override
		protected void onPreExecute() {
			LemonBubble.showRoundProgress(CheckAllFile.this, "查询中");
		}

		@Override
		protected String doInBackground(String... strings) {
			checkFile(input);
			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			handler.sendEmptyMessage(1);
			LemonBubble.hide();
		}
	}

	private void checkFile(String inputType){
		if (inputType.equals("")) {
			LemonBubble.showError(CheckAllFile.this,"请输入文件类型",1000);
			return;
		}else if (!inputType.contains(".")) {
			LemonBubble.showError(CheckAllFile.this,"文件类型错误，请重新输入",1000);
			et_findAllType.setText("");
			return;
		}
		if (nameFile.size() > 0){
			nameFile.clear();
		}

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File path = Environment.getExternalStorageDirectory();//获取sd卡路径
			File [] files = path.listFiles();//获取sd卡所有目录列表
			try {
				getFileName(files,inputType);
			} catch (IOException e) {
				e.printStackTrace();
				LemonBubble.showError(CheckAllFile.this,"获取失败",1000);
			}

		}else {
			LemonBubble.showError(CheckAllFile.this,"SD卡不可用",1000);
		}
	}

	/**
	 * 获取文件的名称和大小
	 * @param files 文件列表
	 * @param type 文件类型
	 * @throws IOException
	 */
	private void getFileName(File[] files,String type) throws IOException {
		if (files != null) {//先判断目录结合是否为空，否则会报空指针
			for(File file : files){//便利所有文件夹和文件
				if (file.isDirectory()) {//文件夹
					getFileName(file.listFiles(),type);//继续往下一个文件夹深入查询
				}else {//文件
					String fileName = file.getName();//获取当前文件的文件名
					String filePath = file.getAbsolutePath();//路径
					if (fileName.endsWith(type)) {
						String fileSize = getFileSize(file);//获取文件的大小
						Bundle bundle = new Bundle();
						bundle.putString(JSONDateKey.FILENAME, fileName);
						bundle.putString(JSONDateKey.FILESIZE, fileSize);
						bundle.putString(JSONDateKey.FILEPATH, filePath);
						nameFile.add(bundle);
					}
				}
			}
		}
	}

	/**
	 * 转换文件的大小
	 * @param file 文件
	 * @throws IOException
	 */
	private String getFileSize(File file) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		long fileLength = inputStream.available();
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString;
		if (fileLength < 1024) {
			fileSizeString = df.format((double) fileLength) + "B";
		} else if (fileLength < 1048576) {
			fileSizeString = df.format((double) fileLength / 1024) + "K";
		} else if (fileLength < 1073741824) {
			fileSizeString = df.format((double) fileLength / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileLength / 1073741824) + "G";
		}

		return fileSizeString;
	}

	private String readTxtFile(String filePath){
		LemonBubble.showRoundProgress(CheckAllFile.this, "读取中");
		try {
			File urlFile = new File(filePath);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String str = "";
			String mimeTypeLine;
			while ((mimeTypeLine = br.readLine()) != null) {
				str = str+mimeTypeLine;
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				CheckAllAdapter adapter = new CheckAllAdapter(CheckAllFile.this, nameFile, R.layout.checkall_item);
				list_checkAll.setAdapter(adapter);
				list_checkAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						String path = nameFile.get(i).getString(JSONDateKey.FILEPATH);
						if (path != null && path.endsWith(".txt")){
							Message message = new Message();
							message.what = 2;
							Bundle bundle = new Bundle();
							bundle.putString("path",path);
							message.setData(bundle);
							handler.sendMessageDelayed(message,500);

						}else if (path != null && path.endsWith(".pdf")){
							LemonBubble.showRoundProgress(CheckAllFile.this, "读取中");
							File file = new File(path);
							view_checkFile_pdfview.fromFile(file)//加载路径
									.defaultPage(0)//默认打开的页面
									.swipeHorizontal(true)//是否横向滑动
									.enableAnnotationRendering(true)
									.enableDoubletap(true)//双击放大
									.load();//加载
							view_checkFile_pdfview.setVisibility(View.VISIBLE);
							txt_checkAllFile_txt.setVisibility(View.GONE);
							list_checkAll.setVisibility(View.GONE);
							LemonBubble.hide();
						}
					}
				});
			}else if (msg.what == 2){
				Bundle bundle = msg.getData();
				String result = readTxtFile(bundle.getString("path"));
				txt_checkAllFile_txt.setText(result);
				txt_checkAllFile_txt.setVisibility(View.VISIBLE);
				list_checkAll.setVisibility(View.GONE);
				LemonBubble.hide();
			}
		}
	};

	private class CheckAllAdapter extends CommonAdapter<Bundle>{

		private CheckAllAdapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Bundle item) {
			TextView txt_checkAllFileName = helper.getView(R.id.txt_checkAllFileName);
			TextView txt_checkAllFileSize = helper.getView(R.id.txt_checkAllFileSize);

			txt_checkAllFileName.setText(item.getString(JSONDateKey.FILENAME));
			txt_checkAllFileSize.setText(item.getString(JSONDateKey.FILESIZE));
		}

	}

	@Override
	public void onBackPressed() {
		if (txt_checkAllFile_txt.getVisibility() == View.VISIBLE){
			txt_checkAllFile_txt.setVisibility(View.GONE);
			txt_checkAllFile_txt.setText("");
			list_checkAll.setVisibility(View.VISIBLE);
			return;
		}else if (view_checkFile_pdfview.getVisibility() == View.VISIBLE){
			view_checkFile_pdfview.setVisibility(View.GONE);
			txt_checkAllFile_txt.setVisibility(View.GONE);
			list_checkAll.setVisibility(View.VISIBLE);
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LemonBubble.forceHide();
		if (nameFile != null){
			nameFile.clear();
			nameFile = null;
		}
	}
}
