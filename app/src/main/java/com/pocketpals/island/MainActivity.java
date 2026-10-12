package com.pocketpals.island;
import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
 SharedPreferences prefs; LinearLayout root; PalView preview; TextView status; int accent=0xff456b58; boolean pendingIsland;
 int dp(float v){return (int)(v*getResources().getDisplayMetrics().density+.5f);}
 public void onCreate(Bundle b){super.onCreate(b);prefs=getSharedPreferences("pals",0);getWindow().setStatusBarColor(0xfff7f6f0);getWindow().setNavigationBarColor(0xfff7f6f0);getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);show();}
 TextView text(String s,int size,int color){TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(color);v.setPadding(0,dp(8),0,dp(8));return v;}
 void title(String s){TextView t=text(s,20,0xff354a3e);t.setTypeface(null,Typeface.BOLD);root.addView(t);}
 void button(String s,Runnable r){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextSize(15);b.setTextColor(accent);root.addView(b,new LinearLayout.LayoutParams(-1,dp(52)));b.setOnClickListener(v->r.run());}
 void show(){ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);scroll.setOnApplyWindowInsetsListener((v,insets)->{v.setPadding(insets.getSystemWindowInsetLeft(),insets.getSystemWindowInsetTop(),insets.getSystemWindowInsetRight(),insets.getSystemWindowInsetBottom());return insets;});scroll.setBackgroundColor(0xfff7f6f0);root=new LinearLayout(this);root.setOrientation(1);root.setPadding(dp(22),dp(20),dp(22),dp(30));scroll.addView(root);setContentView(scroll);
 root.addView(text("A LITTLE FRIEND, EVERYWHERE",11,accent));TextView name=text("PocketPals Island",30,0xff314e40);name.setTypeface(null,Typeface.BOLD);root.addView(name);root.addView(text("Small paws. A happier phone.",15,0xff7a8177));
 preview=new PalView(this,false);GradientDrawable clip=new GradientDrawable();clip.setColor(Color.WHITE);clip.setCornerRadius(dp(24));preview.setBackground(clip);preview.setClipToOutline(true);root.addView(preview,new LinearLayout.LayoutParams(-1,dp(220)));
 status=text("",14,accent);root.addView(status);updateStatus();
 LinearLayout care=new LinearLayout(this);root.addView(care);String[] actions={"Feed","Play","Nap"};for(String action:actions){Button b=new Button(this);b.setText(action);b.setAllCaps(false);care.addView(b,new LinearLayout.LayoutParams(0,dp(50),1));b.setOnClickListener(v->{prefs.edit().putInt("hearts",prefs.getInt("hearts",0)+1).apply();preview.react(action.equals("Feed")?"YUM!":action.equals("Play")?"YAY!":"Zzz");updateStatus();});}
 title("Choose your companion");for(int i=0;i<PalView.NAMES.length;i+=2){LinearLayout row=new LinearLayout(this);root.addView(row);for(int j=i;j<i+2;j++){final int id=j;Button b=new Button(this);b.setText((prefs.getInt("pet",0)==j?"✓ ":"")+PalView.NAMES[j]);b.setAllCaps(false);b.setTextSize(13);row.addView(b,new LinearLayout.LayoutParams(0,dp(54),1));b.setOnClickListener(v->{prefs.edit().putInt("pet",id).apply();show();});}}
 title("Their little world");Spinner scenes=new Spinner(this);scenes.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,PalView.SCENES));scenes.setSelection(prefs.getInt("scene",0));root.addView(scenes,new LinearLayout.LayoutParams(-1,dp(48)));scenes.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){public void onNothingSelected(android.widget.AdapterView<?> a){} public void onItemSelected(android.widget.AdapterView<?> a,View v,int p,long id){prefs.edit().putInt("scene",p).apply();preview.invalidate();}});
 title("Take your pal along");PalView pill=new PalView(this,true);root.addView(pill,new LinearLayout.LayoutParams(-1,dp(65)));root.addView(text("A floating island above other apps. Drag to position it. Tap for a happy reaction.",13,0xff7a8177));
 button("Start floating island",()->{if(!Settings.canDrawOverlays(this)){pendingIsland=true;startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:"+getPackageName())));}else startIsland();});
 button("Stop floating island",()->{stopService(new Intent(this,IslandService.class));Toast.makeText(this,"Island stopped",0).show();});
 button("Set animated wallpaper",()->{try{Intent i=new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(this,PalWallpaper.class));startActivity(i);}catch(ActivityNotFoundException e){Toast.makeText(this,"Open your phone's live wallpaper picker and choose PocketPals Meadow",1).show();}});
 root.addView(text("Wallpaper choices are provided by your phone. Lock-screen live wallpaper may not be available on every device.\n\nOffline • No ads • No account required\nv1.0.0 · First test version",12,0xff85897e));
 }
 void updateStatus(){int hearts=prefs.getInt("hearts",0);status.setText(PalView.NAMES[prefs.getInt("pet",0)]+"   •   Level "+(1+hearts/10)+"   •   "+hearts+" hearts");}
 void startIsland(){if(Build.VERSION.SDK_INT>=33&&checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)!=android.content.pm.PackageManager.PERMISSION_GRANTED)requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS},5);try{startForegroundService(new Intent(this,IslandService.class));Toast.makeText(this,"Island started. Drag it to your preferred position.",0).show();}catch(RuntimeException e){Toast.makeText(this,"Could not start island. Check overlay permission and try again.",1).show();}}
 protected void onResume(){super.onResume();if(pendingIsland){pendingIsland=false;if(Settings.canDrawOverlays(this))startIsland();}}
}
