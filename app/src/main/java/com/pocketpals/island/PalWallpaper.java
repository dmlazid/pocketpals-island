package com.pocketpals.island;
import android.service.wallpaper.WallpaperService;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
public class PalWallpaper extends WallpaperService {
 public Engine onCreateEngine(){return new PalEngine();}
 class PalEngine extends Engine {
  Handler handler=new Handler(Looper.getMainLooper());boolean visible;long happyUntil;
  Runnable frame=new Runnable(){public void run(){draw();}};
  public void onCreate(SurfaceHolder holder){super.onCreate(holder);setTouchEventsEnabled(true);}
  public void onVisibilityChanged(boolean v){visible=v;handler.removeCallbacks(frame);if(v)draw();}
  public void onSurfaceChanged(SurfaceHolder holder,int format,int w,int h){super.onSurfaceChanged(holder,format,w,h);if(visible)draw();}
  public void onSurfaceDestroyed(SurfaceHolder holder){visible=false;handler.removeCallbacks(frame);super.onSurfaceDestroyed(holder);}
  public void onTouchEvent(MotionEvent e){if(e.getAction()==MotionEvent.ACTION_UP)happyUntil=android.os.SystemClock.uptimeMillis()+2000;super.onTouchEvent(e);}
  void draw(){handler.removeCallbacks(frame);if(!visible)return;SurfaceHolder sh=getSurfaceHolder();Canvas c=null;try{c=sh.lockCanvas();if(c!=null)PalView.drawScene(c,c.getWidth(),c.getHeight(),PalWallpaper.this,false,android.os.SystemClock.uptimeMillis()<happyUntil?"LOVE":"");}finally{if(c!=null)sh.unlockCanvasAndPost(c);}if(visible)handler.postDelayed(frame,66);}
  public void onDestroy(){visible=false;handler.removeCallbacks(frame);super.onDestroy();}
 }
}
