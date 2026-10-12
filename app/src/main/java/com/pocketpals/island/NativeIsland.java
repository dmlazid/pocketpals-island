package com.pocketpals.island;
import android.app.*;import android.content.*;import android.graphics.*;import android.graphics.drawable.Icon;import android.net.Uri;import android.os.*;import android.provider.Settings;import org.json.*;
/** Uses Xiaomi's documented client notification payload under our own package identity. */
public final class NativeIsland {
 public static int protocol(Context c){return Settings.System.getInt(c.getContentResolver(),"notification_focus_protocol",0);}
 public static Boolean permission(Context c){try{Bundle args=new Bundle();args.putString("package",c.getPackageName());Bundle answer=c.getContentResolver().call(Uri.parse("content://miui.statusbar.notification.public"),"canShowFocus",null,args);return answer!=null&&answer.containsKey("canShowFocus")?answer.getBoolean("canShowFocus"):null;}catch(Exception e){return null;}}
 static JSONObject obj(Object... kv)throws JSONException{JSONObject j=new JSONObject();for(int i=0;i<kv.length;i+=2)j.put((String)kv[i],kv[i+1]);return j;}
 public static Notification notification(Context c,String mood,long end){
 NotificationManager nm=c.getSystemService(NotificationManager.class);nm.createNotificationChannel(new NotificationChannel("native_pet","Native pet session",NotificationManager.IMPORTANCE_DEFAULT));
 int pet=c.getSharedPreferences("pals",0).getInt("pet",0);String name=PalView.NAMES[pet].split(" · ")[0];
 Bitmap art=PetArt.bitmap(pet,android.os.SystemClock.uptimeMillis()/1000.0,mood);
 PendingIntent open=PendingIntent.getActivity(c,11,new Intent(c,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
 PendingIntent stop=PendingIntent.getService(c,12,new Intent(c,IslandService.class).setAction("STOP"),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
 PendingIntent feed=PendingIntent.getService(c,13,new Intent(c,IslandService.class).setAction("FEED"),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
 Notification.Action stopAction=new Notification.Action.Builder(Icon.createWithResource(c,R.drawable.icon),"Stop",stop).build();Notification.Action feedAction=new Notification.Action.Builder(Icon.createWithResource(c,R.drawable.icon),"Feed",feed).build();
 Bundle extras=new Bundle(),pics=new Bundle(),actions=new Bundle();pics.putParcelable("miui.focus.pic_pet",Icon.createWithBitmap(art));extras.putBundle("miui.focus.pics",pics);actions.putParcelable("miui.focus.action_stop",stopAction);actions.putParcelable("miui.focus.action_feed",feedAction);extras.putBundle("miui.focus.actions",actions);
 try{JSONObject pic=obj("type",1,"pic","miui.focus.pic_pet");JSONObject big=obj("imageTextInfoLeft",obj("type",1,"picInfo",pic,"textInfo",obj("title",name,"content",mood.isEmpty()?"Pet session":mood)));
 JSONObject island=obj("islandProperty",1,"islandTimeout",1800,"bigIslandArea",big,"smallIslandArea",obj("picInfo",pic));
 JSONObject param=obj("protocol",1,"business","pet_companion","updatable",true,"enableFloat",false,"islandFirstFloat",false,"timeout",30,"ticker",name,"tickerPic","miui.focus.pic_pet","param_island",island,"baseInfo",obj("type",2,"title",name,"content","30-minute pet session","pic","miui.focus.pic_pet"),"actions",new JSONArray().put(obj("action","miui.focus.action_feed")).put(obj("action","miui.focus.action_stop")));
 extras.putString("miui.focus.param",obj("param_v2",param).toString());}catch(JSONException e){throw new IllegalStateException(e);}
 return new Notification.Builder(c,"native_pet").setSmallIcon(R.drawable.icon).setLargeIcon(art).setContentTitle(name+" · pet session").setContentText("Native island requested · tap to open PocketPals").setContentIntent(open).setDeleteIntent(stop).setOngoing(true).setOnlyAlertOnce(true).setTimeoutAfter(Math.max(1000,end-System.currentTimeMillis())).addAction(feedAction).addAction(stopAction).addExtras(extras).build();
 }
}
