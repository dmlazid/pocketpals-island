package com.pocketpals.island;
import android.content.*;import android.graphics.*;import android.view.*;
public class PalView extends View {
 public static final String[] NAMES={"Mochi · Bunny","Maple · Kitty","Pip · Chick","Boba · Panda","Clover · Frog","Biscuit · Bear"};
 public static final String[] SCENES={"Sunny meadow","Sakura garden","Moonlit pond"};
 boolean island;long reactionUntil;String reaction="";int fixedPet=-1;
 public PalView(Context c,boolean tiny){super(c);island=tiny;setContentDescription("Animated pet, tap to interact");setOnClickListener(v->react("Love"));}
 public void react(String s){reaction=s;reactionUntil=android.os.SystemClock.uptimeMillis()+5000;invalidate();}
 protected void onDraw(Canvas c){super.onDraw(c);if(fixedPet>=0){Bitmap b=PetArt.bitmap(fixedPet,android.os.SystemClock.uptimeMillis()/1000.0,"");Paint p=new Paint();float size=Math.min(getWidth(),getHeight());c.drawBitmap(b,null,new RectF((getWidth()-size)/2,0,(getWidth()+size)/2,size),p);b.recycle();}else drawScene(c,getWidth(),getHeight(),getContext(),island,reactionUntil>android.os.SystemClock.uptimeMillis()?reaction:"");if(isShown()&&hasWindowFocus())postInvalidateDelayed(83);else if(isShown()&&island)postInvalidateDelayed(125);}
 protected void onWindowVisibilityChanged(int v){super.onWindowVisibilityChanged(v);if(v==VISIBLE)invalidate();}
 static void rect(Canvas c,Paint p,int co,float x,float y,float w,float h){p.setColor(co);c.drawRect(x,y,x+w,y+h,p);}
 static void flower(Canvas c,Paint p,float x,float y,float s,int co){p.setColor(co);for(int i=0;i<5;i++){double a=i*Math.PI*2/5;c.drawCircle(x+(float)Math.cos(a)*s,y+(float)Math.sin(a)*s,s*.7f,p);}p.setColor(0xffffeaa4);c.drawCircle(x,y,s*.5f,p);}
 public static void drawScene(Canvas c,int w,int h,Context ctx,boolean tiny,String mood){
 Paint p=new Paint(3);android.content.SharedPreferences prefs=ctx.getSharedPreferences("pals",0);int type=prefs.getInt("pet",0),scene=prefs.getInt("scene",0);double t=android.os.SystemClock.uptimeMillis()/1000.0;
 if(tiny){p.setColor(Color.BLACK);c.drawRoundRect(0,0,w,h,h/2f,h/2f,p);}else{
 int sky=scene==2?0xff29364f:scene==1?0xffffe4e8:0xffc5e6f0;c.drawColor(sky);
 p.setColor(scene==2?0xffffedb8:0xfffff2b3);c.drawCircle(w*.79f,h*.19f,h*.055f,p);if(scene==2){p.setColor(sky);c.drawCircle(w*.80f,h*.175f,h*.05f,p);}
 for(int i=0;i<9;i++){float x=(float)((i*w*.19+t*2)%(w+70))-30,y=h*(.11f+(i%3)*.08f);p.setColor(scene==2?0xffd2d8df:0xfffffaf2);if(scene==2){c.drawCircle(x,y,i%2+1,p);}else{c.drawRoundRect(x,y,x+35,y+7,4,4,p);c.drawRoundRect(x+8,y-6,x+24,y+7,5,5,p);}}
 p.setColor(scene==2?0xff3c555d:0xffb0cfa0);c.drawOval(-w*.3f,h*.45f,w*.8f,h*1.1f,p);p.setColor(scene==2?0xff344b54:0xff9dbd89);c.drawOval(w*.3f,h*.44f,w*1.4f,h*1.2f,p);
 rect(c,p,scene==2?0xff3b6060:0xff91b775,0,h*.73f,w,h*.27f);
 for(int i=0;i<4;i++){float x=(i==0?.04f:i==1?.20f:i==2?.86f:1.01f)*w,y=h*.58f;rect(c,p,0xff8c7565,x,y-h*.15f,6,h*.26f);p.setColor(scene==1?0xffe7a5bd:scene==2?0xff456f67:0xff78a879);c.drawCircle(x+3,y-h*.17f,h*.09f,p);c.drawCircle(x-9,y-h*.12f,h*.07f,p);c.drawCircle(x+14,y-h*.12f,h*.07f,p);p.setColor(scene==1?0xfff7c5d6:scene==2?0xff548278:0xff9abe8b);c.drawCircle(x-5,y-h*.2f,h*.055f,p);}
 if(scene==2){p.setColor(0xff6c9da5);c.drawOval(w*.55f,h*.82f,w*.99f,h*.98f,p);for(int i=0;i<4;i++)rect(c,p,0xffaed4cc,w*(.62f+i*.065f),h*(.86f+(i%2)*.03f),20,2);}
 else{rect(c,p,0xffe8e1c7,w*.66f,h*.62f,w*.15f,5);rect(c,p,0xffc6b696,w*.67f,h*.65f,w*.13f,4);rect(c,p,0xff9d8c73,w*.68f,h*.66f,3,h*.07f);rect(c,p,0xff9d8c73,w*.78f,h*.66f,3,h*.07f);}
 for(int i=0;i<18;i++){float x=(i*79%Math.max(1,w)),y=h*(.78f+(i%4)*.05f);flower(c,p,x,y,2.3f,scene==2?0xffbed4a4:i%2==0?0xfffff1db:0xffedb2bb);}
 if(scene==1)for(int i=0;i<8;i++){float x=(float)((i*91+t*10)%w),y=(float)((i*41+t*6)%(h*.7));p.setColor(0xffe9a8bf);c.drawOval(x,y,x+5,y+3,p);}
 }
 float size=tiny?h*.93f:Math.min(w*.30f,h*.53f);float x=tiny?w*.23f:w*.5f+(float)Math.sin(t*.23)*w*.12f;float y=tiny?(h-size)/2:h*.76f-size*.80f;
 if(!tiny){p.setColor(0x225c5242);c.drawOval(x-size*.3f,y+size*.80f,x+size*.3f,y+size*.87f,p);}
 Bitmap pet=PetArt.bitmap(type,t,mood);p.setFilterBitmap(false);c.drawBitmap(pet,null,new RectF(x-size/2,y,x+size/2,y+size),p);pet.recycle();
 if(mood.equals("Love")){p.setColor(0xffec8fa6);p.setTextSize(size*.20f);c.drawText("♥",x+size*.35f,y+size*.25f,p);}
 }
}
