package com.pocketpals.island;
import android.app.*;
import android.content.*;
import android.graphics.PixelFormat;
import android.os.*;
import android.provider.Settings;
import android.view.*;

public class IslandService extends Service {
 boolean nativeMode; long nativeEnd; String mood=""; Handler handler=new Handler(Looper.getMainLooper()); Runnable nativeTick; WindowManager wm; PalView pal; WindowManager.LayoutParams lp; BroadcastReceiver screen;
 public IBinder onBind(Intent i){return null;}
 public int onStartCommand(Intent i,int flags,int id){if(i!=null&&"STOP".equals(i.getAction())){stopSelf();return START_NOT_STICKY;}if(i!=null&&"FEED".equals(i.getAction())&&nativeMode){mood="Feed";getSystemService(NotificationManager.class).notify(7,NativeIsland.notification(this,mood,nativeEnd));return START_NOT_STICKY;}if(i!=null&&"NATIVE".equals(i.getAction())){if(pal!=null){wm.removeView(pal);pal=null;}if(screen!=null){unregisterReceiver(screen);screen=null;}nativeMode=true;nativeEnd=System.currentTimeMillis()+30*60*1000L;startForeground(7,NativeIsland.notification(this,"",nativeEnd));handler.removeCallbacksAndMessages(null);nativeTick=()->{if(System.currentTimeMillis()>=nativeEnd){stopSelf();return;}getSystemService(NotificationManager.class).notify(7,NativeIsland.notification(this,"",nativeEnd));handler.postDelayed(nativeTick,10000);};handler.postDelayed(nativeTick,10000);return START_NOT_STICKY;}if(nativeMode){nativeMode=false;handler.removeCallbacksAndMessages(null);}if(!Settings.canDrawOverlays(this)){stopSelf();return START_NOT_STICKY;}if(pal!=null)return START_NOT_STICKY;
 NotificationManager nm=getSystemService(NotificationManager.class);nm.createNotificationChannel(new NotificationChannel("island","Floating companion",NotificationManager.IMPORTANCE_LOW));
 PendingIntent open=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
 PendingIntent stop=PendingIntent.getService(this,1,new Intent(this,IslandService.class).setAction("STOP"),PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
 Notification n=new Notification.Builder(this,"island").setSmallIcon(com.pocketpals.island.R.drawable.icon).setContentTitle("Your PocketPal is here").setContentText("Tap to customize, or Stop to hide your pal.").setContentIntent(open).setOngoing(true).addAction(new Notification.Action.Builder(null,"Stop",stop).build()).build();
 startForeground(7,n);wm=getSystemService(WindowManager.class);float d=getResources().getDisplayMetrics().density;
 lp=new WindowManager.LayoutParams((int)(142*d),(int)(34*d),WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,PixelFormat.TRANSLUCENT);lp.gravity=Gravity.TOP|Gravity.CENTER_HORIZONTAL;lp.flags|=WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN; if(Build.VERSION.SDK_INT>=28)lp.layoutInDisplayCutoutMode=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
 lp.x=getSharedPreferences("pals",0).getInt("islandX2",0);lp.y=getSharedPreferences("pals",0).getInt("islandY2",(int)(8*d));pal=new PalView(this,true);
 pal.setOnTouchListener(new View.OnTouchListener(){float sx,sy;int ox,oy;boolean moved;
 public boolean onTouch(View v,android.view.MotionEvent e){switch(e.getActionMasked()){
 case MotionEvent.ACTION_DOWN:sx=e.getRawX();sy=e.getRawY();ox=lp.x;oy=lp.y;moved=false;return true;
 case MotionEvent.ACTION_MOVE:float dx=e.getRawX()-sx,dy=e.getRawY()-sy;if(Math.abs(dx)+Math.abs(dy)>8*d)moved=true;int maxX=Math.max(0,(getResources().getDisplayMetrics().widthPixels-lp.width)/2);lp.x=Math.max(-maxX,Math.min(maxX,ox+(int)dx));lp.y=Math.max(0,Math.min(getResources().getDisplayMetrics().heightPixels-lp.height,oy+(int)dy));if(pal!=null)wm.updateViewLayout(pal,lp);return true;
 case MotionEvent.ACTION_UP:if(!moved)v.performClick();getSharedPreferences("pals",0).edit().putInt("islandX2",lp.x).putInt("islandY2",lp.y).apply();return true;
 case MotionEvent.ACTION_CANCEL:return true;
 }return false;}});
 try{wm.addView(pal,lp);}catch(RuntimeException ex){pal=null;stopSelf();return START_NOT_STICKY;}
 screen=new BroadcastReceiver(){public void onReceive(Context c,Intent in){if(pal!=null){pal.setVisibility(Intent.ACTION_SCREEN_OFF.equals(in.getAction())?View.GONE:View.VISIBLE);pal.invalidate();}}};IntentFilter f=new IntentFilter(Intent.ACTION_SCREEN_OFF);f.addAction(Intent.ACTION_SCREEN_ON);if(Build.VERSION.SDK_INT>=33)registerReceiver(screen,f,Context.RECEIVER_NOT_EXPORTED);else registerReceiver(screen,f);
 return START_NOT_STICKY;
 }
 public void onDestroy(){handler.removeCallbacksAndMessages(null);if(screen!=null)unregisterReceiver(screen);if(pal!=null){wm.removeView(pal);pal=null;}stopForeground(STOP_FOREGROUND_REMOVE);super.onDestroy();}
}
