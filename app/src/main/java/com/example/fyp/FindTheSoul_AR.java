package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;

import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Random;

public class FindTheSoul_AR extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;
    private String uri = "https://github.com/CZSheng/3Dmodel/blob/main/Ghost.glb?raw=true";
    private String bulleturi = "https://github.com/CZSheng/3Dmodel/blob/main/Bullet.glb?raw=true";

    private Scene scene;
    private Camera camera;
    private ModelRenderable BulletModelRenderable;
    private android.graphics.Point point;
    String go_where;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_soul_ar);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            go_where = extras.getString("gamepage");
        }

        Display display = getWindowManager().getDefaultDisplay();
        //Display display = getDisplay();
        point = new Point();
        display.getRealSize(point);

        MyArFragment fragment = (MyArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_1);
        scene = fragment.getArSceneView().getScene();
        camera = scene.getCamera();

        setghost();
        buildbullet();

        Button talk = findViewById(R.id.talk);
        talk.setOnClickListener(view -> {
            shoot();
        });


    }

    private void setghost() {
        ModelRenderable.builder()
                .setSource(this, RenderableSource.builder().setSource(this,
                        Uri.parse(uri),
                        RenderableSource.SourceType.GLB)
                        .setScale(10f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(uri)
                .build()
                .thenAccept(modelRenderable1 -> {
                    int x,y,z;

                    for(int i = 0; i < 1; i++){
                        Node node = new Node();
                        node.setRenderable(modelRenderable1);


                        //randomly generate balloons on the scene
                        Random random = new Random();
                        x = random.nextInt(5);
                        y = -1;
                        z = random.nextInt(5);

                        z = -z;



                        Vector3 ghostposition = new Vector3( (float) x,  y,  (float) z);
                        node.setWorldPosition(ghostposition);
                        scene.addChild(node);

                    }
                });
    }

    private void buildbullet() {
        Texture
                .builder()
                .setSource(this, R.drawable.texture)
                .build()
                .thenAccept(texture -> {
                    MaterialFactory
                            .makeOpaqueWithTexture(this, texture)
                            .thenAccept(material -> {

                                BulletModelRenderable = ShapeFactory
                                        .makeSphere(0.001f,
                                                new Vector3(0f,0f, 0f), // centre of bullet
                                                material);
                            });
                });

    }

    private void shoot() {
        Ray ray = camera.screenPointToRay(point.x/2f, point.y/2f);
        Node node = new Node();
        node.setRenderable(BulletModelRenderable);
        scene.addChild(node);


        new Thread(()->{
            for(int i = 0; i<2000;i++){
                int finali = i;
                runOnUiThread(()->{
                    Vector3 v = ray.getPoint(finali*0.1f);
                    node.setWorldPosition(v);

                    Node nodecontact = scene.overlapTest(node);
                    if(nodecontact!= null){
                        scene.removeChild(nodecontact);
                        Talkdialog1 talkdialog1 = new Talkdialog1();
                        talkdialog1.setGo_to_where(go_where);
                        talkdialog1.show(getSupportFragmentManager(),"talk dialog 1");
                    }



                });
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            runOnUiThread(()->{
                scene.removeChild(node);
            });

        }).start();
    }


}