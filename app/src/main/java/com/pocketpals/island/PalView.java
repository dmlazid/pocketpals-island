package com.pocketpals.island;
import android.content.*;
import android.graphics.*;
import android.view.*;

public class PalView extends View {
 public static final String[] NAMES={"Mochi · Bunny","Maple · Kitty","Pip · Chick","Boba · Panda","Clover · Frog","Biscuit · Bear"};
 public static final String[] SCENES={"Meadow","Blossom","Starlight"};
 final Paint p=new Paint(3); boolean island; long reactionUntil; String reaction="";
 public PalView(Context c,boolean tiny){super(c);island=tiny;setContentDescription("Animated pet. Tap to make your pet happy.");setOnClickListener(v->react("LOVE"));}
 public void react(String s){reaction=s;reactionUntil=android.os.SystemClock.uptimeMillis()+2400;invalidate();}
 protected void onDraw(Canvas c){super.onDraw(c);drawScene(c,getWidth(),getHeight(),getContext(),island,reactionUntil>android.os.SystemClock.uptimeMillis()?reaction:"");if(isShown())postInvalidateDelayed(66);}
 static void box(Canvas c,Paint p,int color,float x,float y,float w,float h){p.setColor(color);c.drawRect(x,y,x+w,y+h,p);}
 public static void drawScene(Canvas c,int w,int h,Context ctx,boolean tiny,String reaction){
  Paint p=new Paint(3); android.content.SharedPreferences pref=ctx.getSharedPreferences("pals",0);int type=pref.getInt("pet",0),scene=pref.getInt("scene",0);
  double t=android.os.SystemClock.uptimeMillis()/1000.0;
  if(tiny){p.setColor(0xff121818);c.drawRoundRect(0,0,w,h,h/2f,h/2f,p);}else{
   int sky=scene==2?0xff222c4c:scene==1?0xfff9e7ed:0xffd2e9e2;
   c.drawColor(sky);p.setColor(scene==2?0xfff9edc9:0xfffff4c6);c.drawCircle(w*.8f,h*.19f,h*.06f,p);
   for(int i=0;i<8;i++){float x=(float)((i*w/5.0+t*4)%(w+80))-40;float y=h*(.10f+(i%3)*.10f);box(c,p,scene==2?0xffc8cce5:0xfff8f8ef,x,y,24,5);}
   box(c,p,scene==2?0xff384b53:0xff90b59a,0,h*.70f,w,h*.3f);
   box(c,p,scene==2?0xff2b3e48:0xff779e80,0,h*.83f,w,h*.17f);
   for(int i=0;i<5;i++){float x=i*w/4f-12;box(c,p,0xff7a6959,x,h*.44f,8,h*.28f);int leaf=scene==1?0xffe9aabe:scene==2?0xff485d69:0xff7ba98b;box(c,p,leaf,x-14,h*.38f,36,h*.14f);box(c,p,leaf,x-6,h*.31f,22,h*.18f);}
   for(int i=0;i<14;i++){float x=i*w/13f+6,y=h*(.77f+(i%3)*.075f);box(c,p,0xffecdda6,x,y,4,4);}
  }
  float size=tiny?Math.min(h*.72f,48*ctx.getResources().getDisplayMetrics().density):Math.min(w*.28f,h*.40f);
  float walk=(float)Math.sin(t*.55);float x=w*.5f+walk*w*(tiny?.30f:.19f);float y=tiny?h*.64f:h*.73f;float hop=(float)Math.abs(Math.sin(t*(reaction.isEmpty()?3:7)))*size*(reaction.isEmpty()?.045f:.16f);
  p.setColor(0x22000000);c.drawOval(x-size*.4f,y+size*.24f,x+size*.4f,y+size*.36f,p);
  c.save();c.translate(x-size/2,y-size*.65f-hop);float s=size/20;c.scale(s,s);
  int fur=new int[]{0xfffff3df,0xffe5b277,0xffffdb75,0xfff9f4e8,0xffa6ca8c,0xffbd9478}[Math.max(0,Math.min(5,type))];int dark=0xff554a46,blush=0xffe9a6a4;
  box(c,p,dark,4,5,12,14);box(c,p,dark,2,7,16,9);box(c,p,fur,3,7,14,8);box(c,p,fur,5,5,10,13);
  if(type==0){box(c,p,dark,4,-3,4,10);box(c,p,dark,12,-3,4,10);box(c,p,fur,5,-2,2,9);box(c,p,fur,13,-2,2,9);box(c,p,blush,5,0,1,5);box(c,p,blush,13,0,1,5);}
  if(type==1){box(c,p,dark,2,1,5,6);box(c,p,dark,13,1,5,6);box(c,p,fur,3,2,3,6);box(c,p,fur,14,2,3,6);box(c,p,blush,4,3,1,3);box(c,p,blush,15,3,1,3);}
  if(type==3||type==5){box(c,p,type==3?dark:fur,1,2,5,5);box(c,p,type==3?dark:fur,14,2,5,5);}
  if(type==2){box(c,p,fur,8,2,3,5);box(c,p,0xffd39a50,6,18,3,2);box(c,p,0xffd39a50,12,18,3,2);}
  if(type==4){box(c,p,fur,3,3,5,5);box(c,p,fur,12,3,5,5);}
  if(type==3){box(c,p,dark,4,8,4,5);box(c,p,dark,12,8,4,5);}
  boolean blink=((int)(t*10)%47)<2;int eye=type==3?0xfffff9ec:dark;box(c,p,eye,6,9,2,blink?1:3);box(c,p,eye,12,9,2,blink?1:3);
  box(c,p,blush,3,12,3,2);box(c,p,blush,14,12,3,2);box(c,p,type==2?0xffd29145:dark,9,13,2,1);
  box(c,p,fur,3+(float)Math.sin(t*5),16,4,3);box(c,p,fur,13-(float)Math.sin(t*5),16,4,3);
  c.restore();
  if(!reaction.isEmpty()){p.setColor(tiny?0xfff0bdce:0xff65516a);p.setTextAlign(Paint.Align.CENTER);p.setTypeface(Typeface.create("sans-serif-medium",0));p.setTextSize(Math.max(12,size*.19f));c.drawText(reaction,x,Math.max(18,y-size*.85f),p);}
 }
}
