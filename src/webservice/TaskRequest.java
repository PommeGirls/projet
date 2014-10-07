package webservice;


import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class TaskRequest extends RequestManager {

	private static final String URL_SECTION = 		"Task/";
	
	private static final String URL_POST_TASK = 			"RegisterTask";
	private static final String URL_UPDATE_TASK = 			"UpdateTask"; 
	private static final String URL_TASKS_USER = 			"GetTasksForUserId/";
//	private static final String URL_TASKS_FORTHCOMING = 	"GetTasksForthcoming/";
//	private static final String URL_TASKS_HISTORY = 		"GetTasksHistory/";
	private static final String URL_TASKS_SYNCHRONIZE = 		"SyncrhonizeTasksForUserId/";
	
	public TaskRequest(Context context) {
		super(context);
	}
	
	protected static String getUrl(String suffixUrl) {
		return getAbsoluteUrl() + suffixUrl;
	}
	
	public void getListTaskUser(Listener<JSONArray> successListener, ErrorListener errorListener, int userId) {
		String url = getUrl(URL_TASKS_USER) + userId;
		
		JsonArrayRequest request = new JsonArrayRequest(url, successListener, errorListener);
	    
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	
	public void getListTaskSynchronize(Listener<JSONArray> successListener, ErrorListener errorListener, int userId, int maxTaskId) {
		String url = getUrl(URL_TASKS_SYNCHRONIZE) + userId +"/"+ maxTaskId;
		
		JsonArrayRequest request = new JsonArrayRequest(url, successListener, errorListener);
	    
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	/*
	public void postNewTask(Listener<JSONObject> successListener, ErrorListener errorListener, Task task) {
		String url = getUrl(URL_POST_TASK);
		
		JSONObject jsonParams = TaskParser.parseTaskToJson(task);
		
		Log.v(TAG, "PARAMS = "+jsonParams);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonParams, successListener, errorListener);
		
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	
	public void postUpdateTask(Listener<JSONObject> successListener, ErrorListener errorListener, Task task) {
		String url = getUrl(URL_UPDATE_TASK);
		
		JSONObject jsonParams = TaskParser.parseTaskToJson(task);
		
		Log.v(TAG, "PARAMS = "+jsonParams);
		JsonObjectRequest request = new JsonObjectRequest(url, jsonParams, successListener, errorListener);
		
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	*/
}
