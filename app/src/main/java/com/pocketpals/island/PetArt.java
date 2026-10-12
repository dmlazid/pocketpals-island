package com.pocketpals.island;
import android.graphics.*;
/** Original layered pet artwork. Small canvas preserves the illustrated pixel edge. */
public final class PetArt {
 static final Paint p=new Paint(3);
 static void oval(Canvas c,int color,float x,float y,float w,float h){p.setColor(color);p.setStyle(Paint.Style.FILL);c.drawOval(x,y,x+w,y+h,p);}
 static void line(Canvas c,int color,float x,float y,float xx,float yy,float width){p.setColor(color);p.setStrokeWidth(width);p.setStrokeCap(Paint.Cap.ROUND);c.drawLine(x,y,xx,yy,p);}
 static void shape(Canvas c,int color,float... xy){Path path=new Path();path.moveTo(xy[0],xy[1]);for(int i=2;i<xy.length;i+=2)path.lineTo(xy[i],xy[i+1]);path.close();p.setColor(color);p.setStyle(Paint.Style.FILL);c.drawPath(path,p);}
 static final int INK=0xff624e46,CREAM=0xfffff4dd,PINK=0xffefaaa8;
 public static Bitmap bitmap(int type,double t,String mood){Bitmap b=Bitmap.createBitmap(96,96,Bitmap.Config.ARGB_8888);draw(new Canvas(b),type,t,mood);return b;}
 public static void draw(Canvas c,int type,double t,String mood){
 int fur=new int[]{CREAM,0xffe5ad68,0xffffda6c,0xfff6f1e6,0xffa4cc84,0xffba8b65}[type];int shade=new int[]{0xffe8d8ba,0xffc38b52,0xffe9b34e,0xffd8d8cf,0xff7ca65f,0xff996b4d}[type];
 boolean nap=mood.equals("Nap"),play=mood.equals("Play"),feed=mood.equals("Feed");float bob=(float)Math.sin(t*(play?8:3))*1.5f;
 c.save();c.translate(0,bob);float foot=(float)Math.sin(t*5)*2;
 oval(c,INK,24,69+foot,19,15);oval(c,INK,53,69-foot,19,15);oval(c,shade,27,70+foot,15,11);oval(c,shade,56,70-foot,13,11);
 oval(c,INK,23,43,50,37);oval(c,fur,26,44,44,33);oval(c,CREAM,36,51,26,25);
 if(type==0){oval(c,INK,25,1,17,42);oval(c,INK,55,0,17,42);oval(c,fur,28,4,11,37);oval(c,fur,58,3,11,38);oval(c,PINK,31,8,5,26);oval(c,PINK,61,7,5,27);}
 if(type==1){shape(c,INK,19,37,20,9,41,26);shape(c,INK,55,25,76,9,78,39);shape(c,fur,23,31,24,15,37,28);shape(c,fur,59,28,72,15,74,33);shape(c,PINK,26,28,26,20,34,28);shape(c,PINK,63,28,70,20,71,29);}
 if(type==3||type==5){oval(c,INK,15,17,24,24);oval(c,INK,58,17,24,24);if(type==5){oval(c,fur,19,21,16,16);oval(c,fur,62,21,16,16);oval(c,PINK,24,25,8,8);oval(c,PINK,65,25,8,8);}}
 if(type==2){shape(c,INK,40,26,37,13,45,16,51,8,54,25);shape(c,fur,43,27,41,17,47,21,50,14,51,28);}
 oval(c,INK,15,24,66,45);oval(c,fur,18,26,60,40);oval(c,shade,23,55,50,10);oval(c,CREAM,28,45,40,20);
 if(type==1){shape(c,shade,40,27,44,40,48,27);shape(c,shade,52,27,54,39,58,28);line(c,INK,15,47,25,49,1);line(c,INK,70,49,81,47,1);}
 if(type==4){oval(c,INK,21,17,21,23);oval(c,INK,54,17,21,23);oval(c,fur,24,19,16,20);oval(c,fur,56,19,16,20);}
 if(type==3){oval(c,INK,25,35,18,19);oval(c,INK,54,35,18,19);}
 float ey=type==4?28:42;boolean blink=nap||((int)(t*10)%51<2);int ec=type==3?CREAM:INK;
 if(blink){line(c,ec,31,ey+3,39,ey+3,2);line(c,ec,57,ey+3,65,ey+3,2);}else{oval(c,ec,32,ey,6,9);oval(c,ec,58,ey,6,9);oval(c,Color.WHITE,33,ey+1,2,3);oval(c,Color.WHITE,59,ey+1,2,3);}
 oval(c,PINK,23,50,11,6);oval(c,PINK,64,50,11,6);
 if(type==2){shape(c,0xffd58a38,42,51,54,51,48,58);}else{oval(c,INK,45,51,6,4);line(c,INK,48,55,48,58,1);line(c,INK,44,58,48,59,1);line(c,INK,48,59,52,57,1);}
 oval(c,INK,20,61,13,13);oval(c,fur,23,62,10,10);oval(c,INK,63,61,13,13);oval(c,fur,64,62,10,10);
 if(feed){shape(c,0xffd7763e,42,65,57,66,47,84);shape(c,0xff799957,43,65,40,58,48,61,54,57,54,65);line(c,0xfff4a359,45,70,51,72,2);}
 if(play){oval(c,0xffd789a6,68,74,18,18);line(c,0xfff5d5df,72,77,78,87,2);}
 if(nap){p.setColor(INK);p.setTextSize(12);p.setTypeface(Typeface.DEFAULT_BOLD);c.drawText("z Z",72,18,p);}
 c.restore();
 }
}
